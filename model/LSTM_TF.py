import pandas as pd
import numpy as np

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

from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import *
from tensorflow.keras.callbacks import ModelCheckpoint
from tensorflow.keras.losses import MeanSquaredError
from tensorflow.keras.metrics import RootMeanSquaredError
from tensorflow.keras.optimizers import Adam

model = Sequential()
model.add(InputLayer((5, 1)))
model.add(LSTM(64))
model.add(Dense(8, 'relu'))
model.add(Dense(1, 'linear'))

print(model.summary())

cp1 = ModelCheckpoint('modelLSTM/', save_best_only=True)
model.compile(loss=MeanSquaredError(), optimizer=Adam(learning_rate=0.0001), metrics=[RootMeanSquaredError()])
model.fit(X_train, y_train, epochs=10,callbacks=[cp1])

train_predictions = model.predict(X_train).flatten()
train_results = pd.DataFrame(data={'Train Predictions':train_predictions, 'Actuals':y_train})
print(f"Train results: {train_results}")

test_predictions = model.predict(X_test).flatten()
test_results = pd.DataFrame(data={'Test Predictions':test_predictions, 'Actuals':y_test})
print(f"Test results: {test_results}")
