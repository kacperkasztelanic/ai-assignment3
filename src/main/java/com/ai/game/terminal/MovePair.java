package com.ai.game.terminal;

public class MovePair {

    public final int fst;
    public final int snd;

    public static MovePair of(int fst, int snd) {
        return new MovePair(fst, snd);
    }

    public MovePair(int fst, int snd) {
        this.fst = fst;
        this.snd = snd;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MovePair other = (MovePair) obj;
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
}
