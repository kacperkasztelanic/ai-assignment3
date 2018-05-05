package com.kasztelanic.ai.assignment3.dominik;

public class IntFourTuple implements Comparable<IntFourTuple> {

    public final int fst;
    public final int snd;
    public final int thd;
    public final int fourth; 

    public static IntFourTuple of(int fst, int snd, int thd, int fourth) {
        return new IntFourTuple(fst, snd, thd, fourth);
    }

    public IntFourTuple(int fst, int snd, int thd, int fourth) {
        this.fst = fst;
        this.snd = snd;
        this.thd = thd;
        this.fourth = fourth;
    }

    @Override
    public int compareTo(IntFourTuple o) {
        if (fst > o.fst) {
            return 1;
        } else if (fst < o.fst) {
            return -1;
        } else {
            return 0;
        }
    }

	@Override
	public String toString() {
		return "IntFourTuple [fst=" + fst + ", snd=" + snd + ", thd=" + thd + ", fourth=" + fourth + "]";
	}
}
