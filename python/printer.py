import matplotlib.pyplot as plt
import numpy as np

def printPoints(filename, times1, times2, is_log):
    T1 = np.shape(times1)[0]
    T2 = np.shape(times2)[0]
    xT1 = np.arange(T1) + 3
    xT2 = np.arange(T2) + 3
    fig, ax = plt.subplots()
    if is_log:
        ax.semilogy(xT1, times1, label='player1')
        ax.semilogy(xT2, times2, label='player2')
    else:
        ax.plot(xT1, times1, label='player1')
        ax.plot(xT2, times2, label='player2')
    ax.set(xlabel='board size', ylabel='points', title=filename)
    ax.legend()
    ax.grid()


def printTimes(filename, times1, times2):
    T1 = np.shape(times1)[0]
    T2 = np.shape(times2)[0]
    xT1 = np.arange(T1) + 1
    xT2 = np.arange(T2) + 1
    fig, ax = plt.subplots()
    ax.semilogy(xT1, times1, label='player1')
    ax.semilogy(xT2, times2, label='player2')
    ax.set(xlabel='move', ylabel='time [ms]', title=filename)
    ax.legend()
    ax.grid()


def printFewTimes(filename, times1, times2, std1, std2, start_size, xlabel, ylabel):
    T1 = np.shape(times1)[0]
    T2 = np.shape(times2)[0]
    xT1 = np.arange(T1) + start_size
    xT2 = np.arange(T2) + start_size
    fig, ax = plt.subplots()
    ax.plot(xT1, times1, label='player1')
    ax.plot(xT2, times2, label='player2')
    ax.set(xlabel=xlabel, ylabel=ylabel, title=filename)
    ax.errorbar(xT1, times1, yerr=std1, fmt='.', color='blue')
    ax.errorbar(xT2, times2, yerr=std2, fmt='.', color='orange')
    ax.legend()
    ax.grid()


def printFewTimesLog(filename, times1, times2, std1, std2, start_size, xlabel, ylabel):
    T1 = np.shape(times1)[0]
    T2 = np.shape(times2)[0]
    xT1 = np.arange(T1) + start_size
    xT2 = np.arange(T2) + start_size
    fig, ax = plt.subplots()
    ax.semilogy(xT1, times1, label='player1')
    ax.semilogy(xT2, times2, label='player2')
    ax.set(xlabel=xlabel, ylabel=ylabel, title=filename)
    ax.errorbar(xT1, times1, yerr=std1, fmt='.', color='blue')
    ax.errorbar(xT2, times2, yerr=std2, fmt='.', color='orange')
    ax.legend()
    ax.grid()

def printRecursions(filename, recursions1, recursions2):
    R1 = np.shape(recursions1)[0]
    R2 = np.shape(recursions2)[0]
    xR1 = np.arange(R1) + 1
    xR2 = np.arange(R2) + 1
    fig, ax = plt.subplots()
    ax.semilogy(xR1, recursions1, label='player1')
    ax.semilogy(xR2, recursions2, label='player2')
    ax.set(xlabel='move', ylabel='recursion size', title=filename)
    ax.legend()
    ax.grid()