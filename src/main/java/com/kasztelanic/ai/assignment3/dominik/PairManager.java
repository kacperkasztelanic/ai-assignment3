package com.kasztelanic.ai.assignment3.dominik;

import java.util.ArrayList;
import java.util.Random;

public class PairManager {
	
	int boardSide;
	IntPair[] arr;
	int capacity;
	int size;
	Random rand = new Random();
	
	public PairManager(int boardSide) {
		this.boardSide = boardSide;
		capacity = boardSide *  boardSide;
		arr = new IntPair[capacity];
		for (int i = 0; i < capacity; i++) {
			arr[i] = new IntPair(i / boardSide, i % boardSide);
		}
		size = capacity;
	}
	
	public boolean removePair(IntPair other) {
		boolean result = false;
		for (int i = 0; !result && i < size; i++) {
			result = arr[i].equals(other);
			if (result) {
				arr[i] = arr[--size];
			}
		}
		return result;
	}
	
//	IntPair drawPair() {
//		int index = rand.nextInt(size);
//		IntPair result = arr[index];
//		arr[index] = arr[--size];
//		return result;
//	}
	
	ArrayList<IntPair> getUnused() {
		ArrayList<IntPair> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			list.add(arr[i]);
		}
		return list;
	}
	
	@Override
	public String toString() {
		String res = "[";
		for (int i = 0; i < size; i++) {
			res += arr[i];
			res += '\n';
		}
		return res + "]";
	}
	
//	public static void main(String[] args) {
//		PairManager m = new PairManager(5);
//		System.out.println(m.removePair(new IntPair(1,1)));
//		System.out.println(m.removePair(new IntPair(1,1)));
//		System.out.println(m.drawPair());
//		System.out.println(m.toString());
//		System.out.println(m.getUnused().toString());
//		ArrayList<IntPair> list = m.getUnused();
//		IntPair temp = list.get(0);
//		list.set(0, list.get(12));
//		list.set(12, temp);
//		temp = list.get(22);
//		list.set(22, list.get(15));
//		list.set(15, temp);
//		IntPair pair = list.stream()
//                .max((i,j) -> i.compareTo(j))
//                .get();
//		System.out.println(pair);
//		IntPair pair2 = list.stream()
//                .min((i,j) -> i.compareTo(j))
//                .get();
//
//		System.out.println(pair2);		
//	}
}
