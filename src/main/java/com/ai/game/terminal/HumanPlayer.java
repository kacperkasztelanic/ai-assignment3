package com.ai.game.terminal;

import java.util.Scanner;

public class HumanPlayer extends Player {
	
	private Scanner sc = new Scanner(System.in);
	
	HumanPlayer(int playerNumber, int[][] board, MovesManager pairManager) {
		super(playerNumber, board, pairManager);
	}

	public void move(int movesDone) {
		int i = sc.nextInt();
        int j = sc.nextInt();
        while(i >= size || j >= size || board[i][j] != EMPTY) {
        	System.out.println("again");
			i = sc.nextInt();
	        j = sc.nextInt();
		}
        board[i][j] = playerNumber;
        upadtePts(i, j);
        pairManager.removePair(new MovePair(i, j));
	}
}
