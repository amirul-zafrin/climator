import math
from msilib import sequence
import pandas as pd
import numpy as np
from tqdm.notebook import tqdm
import pytorch_lightning as pl

import torch
import torch.autograd as autograd
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
from torch.utils.data import Dataset, DataLoader
from collections import defaultdict

# Loading data

data = pd.read_csv('temperature_filtered.csv', header=None)

df = pd.DataFrame()
df["unix"] = data[2]
df["temp"] = data[6]

train_size = int(len(df) * 0.8)

train_df, test_df = df[:train_size], df[train_size+1:]
print(f"train: {train_df.shape}; test: {test_df.shape}")

def create_sequence(input_data: pd.DataFrame, target_column, sequence_length):
    sequence = []
    data_size = len(input_data)

    for i in tqdm(range(data_size - sequence_length)):
        sequence = input_data[i:i+sequence_length]

        label_position = i + sequence_length
        label = input_data.iloc[label_position][target_column]

        sequence.append((sequence, label))

    return sequence

SEQUENCE_LENGTH = 120;
train_sequence = create_sequence(train_df, "temp", SEQUENCE_LENGTH)
test_sequence = create_sequence(test_df, "temp", SEQUENCE_LENGTH)

print(train_sequence[0][0])



# class TempDataSet(Dataset):

#     def __init__(self, sequences):
#         self.sequences = sequences

#     def __len__(self):
#         return len(self.sequences)

#     def __getitem__(self, idx):
#         sequence, label = self.sequences[idx]
#         return dict(
#             sequence=torch.Tensor(sequence.to_numpu()),
#             label=torch.tensor(label).float()
#         )

# class TempDataModule(pl.LightningDataModule):

#     def __init__(self, train_sequences, test_sequences, batchs_size=10):
#         super().__init__()
#         self.train_sequences = train_sequences
#         self.test_sequences = test_sequences
#         self.batchs_size = batchs_size

#     def setup(self):
#         self.train_dataset = TempDataSet(self.train_sequences)
#         self.test_dataset = TempDataSet(self.test_sequences)

#     def train_dataloader(self):
#         return DataLoader(
#             self.train_dataset, batch_size=self.batchs_size,
#             shuffle=False, num_workers=2)
    
#     def val_dataloader(self):
#         return DataLoader(
#             self.test_dataset,
#             batch_size=1,
#             shuffle=False, num_workers=1
#         )

#     def test_dataloader(self):
#         return DataLoader(
#             self.test_dataset,
#             batch_size=1,
#             shuffle=False, num_workers=1
#         )

# N_EPOCHS = 8
# BATCH_SIZE = 64

# data_module = TempDataModule(train_sequences, test_sequences, batchs_size=BATCH_SIZE)
# data_module.setup()

# train_dataset = TempDataSet(train_sequences)

# for item in train_dataset:
#     print(item["sequence"].shape)
#     print(item["label"].shape)
#     print(item["label"])
#     break
