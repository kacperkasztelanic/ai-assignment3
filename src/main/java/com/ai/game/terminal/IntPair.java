package com.ai.game.terminal;

public class IntPair implements Comparable<IntPair> {

    public final int fst;
    public final int snd;

    public static IntPair of(int fst, int snd) {
        return new IntPair(fst, snd);
    }

    public IntPair(int fst, int snd) {
        this.fst = fst;
        this.snd = snd;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + fst;
        result = prime * result + snd;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IntPair other = (IntPair) obj;
        if (fst != other.fst)
            return false;
        if (snd != other.snd)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", fst, snd);
    }

    @Override
    public int compareTo(IntPair o) {
        if (fst > o.fst) {
            return 1;
        } else if (fst < o.fst) {
            return -1;
        } else {
            return 0;
        }
    }
}
