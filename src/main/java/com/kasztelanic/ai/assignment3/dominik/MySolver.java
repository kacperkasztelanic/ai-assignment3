package com.kasztelanic.ai.assignment3.dominik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MySolver {
	
	final static int EMPTY = 0;
	final static int PLAYER1 = 1;
	final static int PLAYER2 = 2;
	final static int MOVE = 1;
	
	PairManager pairManager;
	int[][] board;
	int size;
	int depth;
	int points1 = 0;
	int points2 = 0;
	int tour = 0;
	int movesToDo;
	int movesDone = 0;

	public MySolver(int size, int depth) {
		board = new int[size][];
		this.depth = depth;
		this.size = size;
		for (int i=0; i<size; i++) {
			// default board value is 0, same as EMPTY
			board[i] = new int[size];
		}
		pairManager = new PairManager(size);
    	movesToDo = size * size;
	}
	
	private IntPair calculatePts(int y, int x, boolean isFstPlayerMove) {
		int player1Pts = 0;
		int player2Pts = 0;
		boolean isRow = true;
		for(int i=0; isRow && i<size; i++) {
			isRow = board[y][i] != EMPTY;
		}
		if (isRow){
			if(isFstPlayerMove){
				player1Pts += size;
			} else{
				player2Pts += size;
			}
		}
		boolean isColumn = true;
		for(int j=0; isColumn && j<size; j++) {
			isColumn = board[j][x] != EMPTY;
		}
		if (isColumn){
			if(isFstPlayerMove){
				player1Pts += size;
			} else{
				player2Pts += size;
			}
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
				if(isFstPlayerMove){
					player1Pts += pts;
				} else{
					player2Pts += pts;
				}
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
				if(isFstPlayerMove){
					player1Pts += pts;
				} else{
					player2Pts += pts;
				}
			}
		}
		return new IntPair(player1Pts, player2Pts);
	}
	
	private void __solve(boolean isFstPlayerMove) {
		ArrayList<IntPair> avaliableMoves = pairManager.getUnused();
		IntPair result = solveRec(avaliableMoves, isFstPlayerMove, 
				isFstPlayerMove, true, movesDone, depth);
		IntPair moveIndexes = avaliableMoves.get(result.snd);
		if (isFstPlayerMove) {
			board[moveIndexes.fst][moveIndexes.snd] = MySolver.PLAYER1;
		} else {
			board[moveIndexes.fst][moveIndexes.snd] = MySolver.PLAYER2;
		}
		upadtePts(moveIndexes.fst,moveIndexes.snd, isFstPlayerMove);
	}
	
	private IntPair solveRec(List<IntPair> avaliableMoves, boolean isFstPlayerPrediction, 
			boolean isFstPlayerMove, boolean isMaximalization, int movesDone, int depth) {
		if (movesDone < movesToDo && depth > 0) {
			List<IntPair> results = new ArrayList<>(avaliableMoves.size());
			for (int i = 0; i < avaliableMoves.size(); i++) {
				int currentPts = 0;
				IntPair moveIndexes = avaliableMoves.get(i);
				if (isMoveAvaliable(moveIndexes)) {
					board[moveIndexes.fst][moveIndexes.snd] = MOVE;
					IntPair pts = calculatePts(moveIndexes.fst, moveIndexes.snd, isFstPlayerMove);
					int newPts = pts.fst - pts.snd;
					if (isFstPlayerPrediction) {
						currentPts += newPts;
					} else {
						currentPts -= newPts;
					}
					IntPair lastChoosen = solveRec(avaliableMoves, isFstPlayerPrediction, !isFstPlayerMove,
							!isMaximalization, movesDone + 1, depth - 1);
					results.add(new IntPair(currentPts + lastChoosen.fst, i));
					board[moveIndexes.fst][moveIndexes.snd] = EMPTY;
				}
			}
//			if (movesDone == this.movesDone) {
//				debugPrint(results, avaliableMoves);
//			}
			IntPair moveChoosen;
			if (isMaximalization) {
				moveChoosen = results.stream().max((i,j) -> i.compareTo(j)).get();
			} else {
				moveChoosen = results.stream().min((i,j) -> i.compareTo(j)).get();
			}
			if (movesDone == this.movesDone) {
				System.out.println("move choosen:" + moveChoosen.fst + "\t" 
				+ avaliableMoves.get(moveChoosen.snd));
			}
			return moveChoosen;
		}else {
			return new IntPair(0, -1);
		}
	}
	
	private void debugPrint(List<IntPair> results, List<IntPair> moves) {
		for (IntPair e: results) {
			System.out.println("  " + e.fst + "\t"+ moves.get(e.snd));
		}
	}
	
	private boolean isMoveAvaliable(IntPair pair) {
		return board[pair.fst][pair.snd] == EMPTY;
	}
	
	private void aiMove() {
        if (movesDone < movesToDo) {
//            randomSolve();
        	__solve(false);
            movesDone++;
        }
	} 
	
	private void upadtePts(int i, int j, boolean isFstPlayerMove) {
        IntPair pts = calculatePts(i, j, isFstPlayerMove);
        points1 += pts.fst;
		points2 += pts.snd;
	}
	
	private void userMove(Scanner sc) {
		int i = sc.nextInt();
        int j = sc.nextInt();
        while(i >= size || j >= size || !isMoveAvaliable(new IntPair(i,j))) {
        	System.out.println("again");
			i = sc.nextInt();
	        j = sc.nextInt();
		}
        board[i][j] = MySolver.PLAYER1;
        pairManager.removePair(new IntPair(i, j));
        upadtePts(i, j, true);
        movesDone++;
	}
	
	public void checkInput() {
		
	}
//	private void randomSolve() {
//		IntPair pair = pairManager.drawPair();
//		board[pair.fst][pair.snd] = MySolver.PLAYER2;
//        calculatePts(pair.fst,pair.snd,2);
//	}
	
	public void run() {
    	Scanner sc = new Scanner(System.in);
    	printBoard();
    	while (movesDone < movesToDo) {
    		userMove(sc);
    		aiMove();
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
		System.out.println("P1: " + points1);
		System.out.println("P2: " + points2);
	}
	
    public static void main(String[] args) {
    	MySolver my = new MySolver(4, 4);
    	my.run();
    }
}
