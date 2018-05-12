import csv
import numpy as np

FILES = '\\files\\'

CSV = '.csv'

FOLDER = FILES

times1 = []
times2 = []
recursions1 = []
recursions2 = []

def read(filename):
    times1 = []
    times2 = []
    recursions1 = []
    recursions2 = []
    with open("./" + FOLDER + filename + CSV, 'rt') as csvfile:
        counter = 0
        data_reader = csv.reader(csvfile, delimiter=',')
        for row in data_reader:
            if counter % 4 == 0:
                times1 = row
            elif counter % 4 == 1:
                times2 = row
            elif counter % 4 == 2:
                recursions1 = row
            elif counter % 4 == 3:
                recursions2 = row
            counter += 1
    return np.asarray(times1), np.asarray(times2), np.asarray(recursions1), np.asarray(recursions2)


def readFew(filename):
    times1 = []
    times2 = []
    recursions1 = []
    recursions2 = []
    with open("./" + FOLDER + filename + CSV, 'rt') as csvfile:
        counter = 0
        data_reader = csv.reader(csvfile, delimiter=',')
        for row in data_reader:
            if counter > 1:
                if counter % 4 == 0:
                    times1.append(row)
                elif counter % 4 == 1:
                    times2.append(row)
                elif counter == 2:
                    recursions1 = row
                elif counter == 3:
                    recursions2 = row
            counter += 1
    times1 = np.asarray(times1).astype(np.float)
    times2 = np.asarray(times2).astype(np.float)
    std_times1 = np.std(times1, axis=0)
    std_times2 = np.std(times2, axis=0)
    avg_times1 = np.sum(times1, axis=0) / np.shape(times1)[1]
    avg_times2 = np.sum(times2, axis=0) / np.shape(times2)[1]
    return avg_times1, avg_times2, std_times1, std_times2, np.asarray(recursions1), np.asarray(recursions2)
