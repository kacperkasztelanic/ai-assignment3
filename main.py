from python import loader
from python import printer

import matplotlib.pyplot as plt


def printTimesRec(filename):
    t1, t2, r1, r2 = loader.read(filename)
    printer.printTimes(filename, t1, t2)
    printer.printRecursions(filename, r1, r2)


def printFewTimesRec(filename):
    t1, t2, s1, s2, r1, r2 = loader.readFew(filename)
    printer.printFewTimes(filename, t1, t2, s1, s2, 1, 'move', 'time [ms]')
    printer.printFewTimesLog(filename, t1, t2, s1, s2, 1, 'move', 'time [ms]')
    printer.printRecursions(filename, r1, r2)


def printFstFewTimesRec(filename):
    t1, t2, s1, s2, r1, r2 = loader.readFew(filename)
    printer.printFewTimes(filename, t1, t2, s1, s2, 3, 'board size', 'time [ms]')
    printer.printFewTimesLog(filename, t1, t2, s1, s2, 3, 'board size', 'time [ms]')
    printer.printRecursions(filename, r1, r2)

def main():
    # printTimesRec('size_11_minmax_2_alpha-beta_3_pts_239_241')
    # printTimesRec('size_11_minmax_3_alpha-beta_3_pts_289_191')
    # printTimesRec('size_11_minmax_3_alpha-beta_4_pts_247_233')
    # printTimesRec('size_11_minmax_4_alpha-beta_4_pts_287_193')
    # printTimesRec('size_13_minmax_4_alpha-beta_5_pts_387_285')

    # printFewTimesRec('10_times_size_11_minmax_2_alpha-beta_3')
    printFewTimesRec('10_times_size_11_minmax_3_alpha-beta_4')
    printFewTimesRec('10_times_size_11_minmax_4_alpha-beta_4')
    printFewTimesRec('10_times_size_11_minmax_4_alpha-beta_5')

    # printFstFewTimesRec('fst_move_10_times_minmax_3_alpha-beta_3')
    # printFstFewTimesRec('fst_move_10_times_minmax_3_alpha-beta_4')
    # printFstFewTimesRec('fst_move_10_times_minmax_4_alpha-beta_4')
    # printFstFewTimesRec('fst_move_10_times_minmax_4_alpha-beta_5')

    plt.show()

if __name__ == "__main__":
    main()



