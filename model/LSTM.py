import math
import torch.optim as optim
from torch import nn
from torch.utils.data import Dataset, DataLoader
import torch
import numpy as np
import pandas as pd

device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

class LSTM(nn.Module):
    def __init__(self, input_size=1, hidden_size=51):
        super(LSTM, self).__init__()
        self.hidden_size = hidden_size
        self.lstm1 = nn.LSTMCell(input_size, hidden_size)
        self.lstm2 = nn.LSTMCell(hidden_size, hidden_size)
        self.linear = nn.Linear(hidden_size, 1)

    def forward(self, input, future = 0):
        outputs = []
        n_sample = input.size(0)
        h_t = torch.zeros(n_sample, self.hidden_size, dtype=torch.double)
        c_t = torch.zeros(n_sample, self.hidden_size, dtype=torch.double)
        h_t2 = torch.zeros(n_sample, self.hidden_size, dtype=torch.double)
        c_t2 = torch.zeros(n_sample, self.hidden_size, dtype=torch.double)

        for input_t in input:
            h_t, c_t = self.lstm1(input_t, (h_t, c_t))
            h_t2, c_t2 = self.lstm2(h_t, (h_t2, c_t2))
            output = self.linear(h_t2)
            outputs += [output]

        for i in range(future):# if we should predict the future
            h_t, c_t = self.lstm1(output, (h_t, c_t))
            h_t2, c_t2 = self.lstm2(h_t, (h_t2, c_t2))
            output = self.linear(h_t2)
            outputs += [output]
        outputs = torch.cat(outputs, dim=1)
        return outputs

class TempDataset(Dataset):
    def __init__(self, data, seq_prop=0.2):
        self.data = data
        self.data = torch.from_numpy(data)
        self.seq_prop = seq_prop
    
    def __len__(self):
        return len(self.data)*(1-self.seq_prop)

    def __getitem__(self, index):
        return self.data[index : index + int(len(self.data)*self.seq_prop)] , self.data[index : index + int(len(self.data)*self.seq_prop)]

#Load data
filename = "temperature_filtered.csv"
data = pd.read_csv(filename, index_col=None, header=None)
data = data[[2,6]]
train_data = data[:int(len(data)*0.8)].to_numpy()
test_data = data[int(len(data)*0.8):].to_numpy()

train_ds = TempDataset(train_data)
test_ds = TempDataset(test_data)

batch_size = 64
train_dataloader = DataLoader(train_ds, batch_size=batch_size,drop_last=True)
test_dataloader = DataLoader(test_ds, batch_size=batch_size,drop_last=True)

print(train_dataloader.shape)

#Initialize network
model = LSTM()

# Optimizer
criterion = nn.MSELoss()
# optimizer = optim.Adam(model.parameters(), lr=0.001)
optimizer = optim.LBFGS(model.parameters(), lr=0.001)

#Train network
def train(loader):
    for batch, (x,y) in enumerate(loader):
        x = x.to(device)
        y = y.to(device)

        scores = model(x.reshape(100, batch_size, 1))
        loss = criterion(scores.reshape(batch_size), y)
        
        #backward
        optimizer.zero_grad()
        loss.backward()

        #update
        optimizer.step()

        if batch == len(train_dataloader):
            loss = loss.item()
            print(f"Train loss: {loss}")

def test(loader):
    model.eval()
    for batch, (x,y) in enumerate(loader):
        x = x.to(device)
        y = y.to(device)

        scores = model(x.reshape(100, batch_size, 1))
        loss = criterion(scores.reshape(batch_size), y)

        if batch == len(train_dataloader):
            loss = loss.item()
            print(f"Train loss: {loss}")

num_epoch = 100
for epoch in range(num_epoch):
    print(f"Epoch {epoch} / {num_epoch}")
    train(train_dataloader)
    test(test_dataloader)
    