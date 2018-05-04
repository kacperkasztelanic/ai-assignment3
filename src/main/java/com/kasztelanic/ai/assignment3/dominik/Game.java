package com.kasztelanic.ai.assignment3.dominik;

import java.util.Arrays;

public class Game {
	final static int EMPTY = 0;
	final static int PLAYER1 = 1;
	final static int PLAYER2 = 2;
	
	private int[][] board;
	private int size;
	private int depth = 1;
	private int tour = 0;
	private int movesToDo;
	private int movesDone = 0;
	private PairManager pairManager;
	private Player player1;
	private Player player2;

	public Game(int size, int depth) {
		board = new int[size][];
		this.size = size;
		this.depth = depth;
		for (int i=0; i<size; i++) {
			// default board value is 0, same as EMPTY
			board[i] = new int[size];
		}
		pairManager = new PairManager(size);
    	movesToDo = size * size;
    	setPlayers();
	}
	
	private void setPlayers() {
//		player1 = new HumanPlayer(1, board, pairManager);
//		player2 = new HumanPlayer(2, board, pairManager);
		player1 = new AIPlayer(1, board, pairManager, 2);
		player2 = new AIPlayer(2, board, pairManager, depth);
	}
	
	public void run() {
    	printBoard();
    	while (movesDone < movesToDo) {
    		player1.move(movesDone);
    		movesDone++;
    		if (movesDone < movesToDo) {
    			player2.move(movesDone);
        		movesDone++;
    		}
            ++tour;
    		printBoard();
    	}
    	System.out.println("OVER");
	}
	
	public void printBoard() {
		for (int[] row: board) {
			System.out.println(Arrays.toString(row));
		}
		System.out.println("Tour: " + tour);
		System.out.println("P1: " + player1.points);
		System.out.println("P2: " + player2.points);
	}
	
    public static void main(String[] args) {
    	Game game = new Game(17, 3);
    	game.run();
    }
}
