package com.ai.game.terminal;

public abstract class Player {
	
	final static int EMPTY = 0;
	final static int MOVE = 1;
	
	protected int[][] board;
	protected int size;
	protected final int playerNumber;
	protected int points = 0;
	protected int movesToDo;
	protected PairManager pairManager;
	protected long simulationTime = 0;

	Player(int playerNumber, int[][] board, PairManager pairManager) {
		this.board = board;
		this.size = board.length;
		this.playerNumber = playerNumber;
		this.pairManager = pairManager;
		movesToDo = size * size;
	}
	
	abstract void move(int movesDone);
	
	protected void upadtePts(int i, int j) {
		points += calculatePts(i, j);
	}
	
	protected boolean isMoveAvaliable(MovePair pair) {
		return board[pair.fst][pair.snd] == EMPTY;
	}
	
	protected int calculatePts(int y, int x) {
		int points = 0;
		boolean isRow = true;
		for(int i=0; isRow && i<size; i++) {
			isRow = board[y][i] != EMPTY;
		}
		if (isRow){
			points += size;
		}
		boolean isColumn = true;
		for(int j=0; isColumn && j<size; j++) {
			isColumn = board[j][x] != EMPTY;
		}
		if (isColumn){
			points += size;
		}
		boolean isDiagonal1 = true;
		int temp = Math.min(y, x);
		int y2 = y - temp;
		int x2 = x - temp;
		while (isDiagonal1 && x2 < size && y2 < size) {
			isDiagonal1 = board[y2++][x2++] != EMPTY;
		}
		if (isDiagonal1){
			int pts = Math.min(y2, x2);
			if (pts > 1) {
				points += pts;
			}
		}
		boolean isDiagonal2 = true;
		int temp2 = Math.min(y, size - x - 1);
		int y3 = y - temp2;
		int x3 = x + temp2;
		while (isDiagonal2 && x3 > -1 && y3 < size) {
			isDiagonal2 = board[y3++][x3--] != EMPTY;
		}
		if (isDiagonal2){
			int pts = Math.min(y3, size - x3 - 1);
			if (pts > 1) {
				points += pts;
			}
		}
		return points;
	}

	public int getPoints() {
		return points;
	}
}
