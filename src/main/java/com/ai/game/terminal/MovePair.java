package com.ai.game.terminal;

public class MovePair implements Comparable<MovePair> {

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
	public int compareTo(MovePair o) {
		if (fst > o.fst) {
	           return 1;
	       } else if (fst < o.fst){
	           return -1;
	       } else {
	           return 0;
	       }
	}
	
	
	
    @Override
    public String toString() {
        return String.format("(%d, %d)", fst, snd);
    }
}
