import math
import torch.optim as optim
from torch import nn
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

#Load data
data = pd.read_csv('temperature_filtered.csv', header=None)

data = data[6]
train_data = data[:int(len(data)*0.8)]
test_data = data[int(len(data)*0.8):]

def df_to_XY(df, window_size = 5):
    df_as_np = df.to_numpy()
    x = []
    y = []

    for i in range(len(df_as_np) - window_size):
        row = [[a] for a in df_as_np[i:i+window_size]]
        x.append(row)
        label = df_as_np[i+window_size]
        y.append(label)

    return np.array(x), np.array(y)


X, y = df_to_XY(data)

split_ratio = round(len(X)*0.8)

X_train, y_train = X[:split_ratio], y[:split_ratio]
X_test, y_test = X[split_ratio:], y[split_ratio:]

train_input = torch.from_numpy(X_train)
train_target = torch.from_numpy(y_train)
test_input = torch.from_numpy(X_test)
test_target = torch.from_numpy(y_test)
#Initialize network
model = LSTM(input_size=5)

# Optimizer
criterion = nn.MSELoss()
optimizer = optim.Adam(model.parameters(), lr=0.001)

# Train network
for epoch in range(10):
    # forward
    scores = model(train_input)
    loss = criterion(scores, train_target)

    #backward
    optimizer.zero_grad()
    loss.backward()

    #update
    optimizer.step()

    with torch.no_grad():
        scores = model(test_input)
        loss = criterion(scores, test_target)
        print("test loss:", loss.item())

        y = scores.detach().numpy()

