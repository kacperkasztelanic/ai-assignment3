package com.kasztelanic.ai.assignment3;

public class IntPair implements Comparable<IntPair>{
	int fst;
	int snd;
	
	IntPair(int fst, int snd) {
		this.fst = fst;
		this.snd = snd;
	}
	
	boolean equals(IntPair other) {
		return fst == other.fst && snd == other.snd;
	}

	@Override
	public String toString() {
		return "IntPair [fst=" + fst + ", snd=" + snd + "]";
	}

	@Override
	public int compareTo(IntPair o) {
		if (fst > o.fst) {
	           return 1;
	       } else if (fst < o.fst){
	           return -1;
	       } else {
	           return 0;
	       }
	}
}
