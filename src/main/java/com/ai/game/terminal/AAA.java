package com.ai.game.terminal;

import java.util.Arrays;

public class AAA {

private int[][] boardValuesMiddleMin(int size) {
	int[][] arr = new int[size][size];
	int half = size / 2;
	for (int i = 0; i < size; i++) {
		for (int j = 0; j < size; j++) {
			arr[i][j] = Math.abs(half - i) + Math.abs(half - j);
		}
	}
	return arr;
}

	
	public static void main(String[] args) {
		AAA a = new AAA();
		System.out.println(Arrays.deepToString(a.boardValuesMiddleMin(5)));
		// TODO Auto-generated method stub

	}

}
