import numpy as np
import pandas as pd

df = pd.read_csv('temperature.csv', header=None)

df[6] = np.where(df[6].isnull(), df[5], df[6])

df_filtered = df[(df[6] > 0) & (df[6] < 40)]

df_filtered.to_csv('temperature_filtered.csv' , index=False, header=False)




