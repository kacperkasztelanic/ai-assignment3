package com.kasztelanic.ai.assignment3.dominik;

import java.util.List;

public class AlphaBetaPlayer extends Player {
	
	final static int INT_MAX = Integer.MAX_VALUE;
	final static int INT_MIN = Integer.MIN_VALUE;
	
    private List<IntPair> avaliableMoves;
    private int depth;
    private int movesDone;
    private int moveIndex;
    private int recCounter = 0;

    AlphaBetaPlayer(int playerNumber, int[][] board, PairManager pairManager, int treeMaxDepth) {
        super(playerNumber, board, pairManager);
        this.depth = treeMaxDepth;
    }

    @Override
    void move(int movesDone) {
        solve(movesDone);

    }

    private void solve(int movesDone) {
        this.movesDone = movesDone;
        this.avaliableMoves = pairManager.getUnused();
        recCounter = 0;
        solveRecMax(movesDone, depth, INT_MAX, INT_MIN);
        System.out.println("recursion size: " + recCounter);
        IntPair moveIndexes = avaliableMoves.get(moveIndex);
        board[moveIndexes.fst][moveIndexes.snd] = playerNumber;
        upadtePts(moveIndexes.fst, moveIndexes.snd);
        pairManager.removePair(moveIndexes);
    }

    private int solveRecMax(int movesDone, int depth, int alpha, int beta) {
        ++recCounter;
        Integer choosenPts = null;
        int accumulatedPts;
        int currentPts = 0;
        for (int i = 0; i < avaliableMoves.size(); i++) {
            currentPts = 0;
            IntPair moveIndexes = avaliableMoves.get(i);
            if (isMoveAvaliable(moveIndexes)) {
                board[moveIndexes.fst][moveIndexes.snd] = MOVE;
                currentPts += calculatePts(moveIndexes.fst, moveIndexes.snd);
                if (movesDone + 1 < movesToDo && depth > 1) {
                    accumulatedPts = currentPts + solveRecMin(movesDone + 1, depth - 1, INT_MAX, beta);
                } else {
                    accumulatedPts = currentPts;
                }
                if (choosenPts == null || accumulatedPts >= choosenPts) {
                    choosenPts = accumulatedPts;
                    beta = accumulatedPts;
                    trySetMoveIndex(i, movesDone);
                }
                board[moveIndexes.fst][moveIndexes.snd] = EMPTY;
            	if (choosenPts >= alpha) {
            		break;
            	}
            }
        }
        if (depth == this.depth) {
            System.out.println("move choosen:" + choosenPts + "\t" + avaliableMoves.get(moveIndex));
        }
        return choosenPts;
    }
    
    private void trySetMoveIndex(int moveIndex, int movesDone) {
    	if (this.movesDone == movesDone) {
    		this.moveIndex = moveIndex;
        }
	}

	private int solveRecMin(int movesDone, int depth, int alpha, int beta) {
        ++recCounter;
        Integer choosenPts = null;
        int accumulatedPts;
        int currentPts = 0;
        for (int i = 0; i < avaliableMoves.size(); i++) {
            currentPts = 0;
            IntPair moveIndexes = avaliableMoves.get(i);
            if (isMoveAvaliable(moveIndexes)) {
                board[moveIndexes.fst][moveIndexes.snd] = MOVE;
                currentPts -= calculatePts(moveIndexes.fst, moveIndexes.snd);
                if (movesDone + 1 < movesToDo && depth > 1) {
                    accumulatedPts = currentPts + solveRecMax(movesDone + 1, depth - 1, alpha, INT_MIN);
                } else {
                    accumulatedPts = currentPts;
                }
                if (choosenPts == null || accumulatedPts < choosenPts) {
                    choosenPts = accumulatedPts;
                    alpha = accumulatedPts;
                }
                board[moveIndexes.fst][moveIndexes.snd] = EMPTY;
            	if (choosenPts <= beta) {
            		break;
            	}
            }
        }
        return choosenPts;
    }
}
