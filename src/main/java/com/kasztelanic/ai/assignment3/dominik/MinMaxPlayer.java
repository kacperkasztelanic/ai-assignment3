package com.kasztelanic.ai.assignment3.dominik;

import java.util.List;

public class MinMaxPlayer extends Player {
    private List<IntPair> avaliableMoves;
    private int depth;
    private int moveIndex;
    private int recCounter = 0;

    MinMaxPlayer(int playerNumber, int[][] board, PairManager pairManager, int treeMaxDepth) {
        super(playerNumber, board, pairManager);
        this.depth = treeMaxDepth;
    }

    @Override
    void move(int movesDone) {
        solve(movesDone);

    }

    private void solve(int movesDone) {
        this.avaliableMoves = pairManager.getUnused();
        recCounter = 0;
        solveRecMax(movesDone, depth);
        System.out.println("recursion size: " + recCounter);
        IntPair moveIndexes = avaliableMoves.get(moveIndex);
        board[moveIndexes.fst][moveIndexes.snd] = playerNumber;
        upadtePts(moveIndexes.fst, moveIndexes.snd);
        pairManager.removePair(moveIndexes);
    }

    private int solveRecMax(int movesDone, int depth) {
        ++recCounter;
        Integer choosenPts = null;
        int accumulatedPts;
        int currentPts = 0;
        int mmm = -1;
        for (int i = 0; i < avaliableMoves.size(); ++i) {
            currentPts = 0;
            IntPair moveIndexes = avaliableMoves.get(i);
            if (isMoveAvaliable(moveIndexes)) {
                board[moveIndexes.fst][moveIndexes.snd] = MOVE;
                currentPts += calculatePts(moveIndexes.fst, moveIndexes.snd);
                if (movesDone + 1 < movesToDo && depth > 1) {
                    accumulatedPts = currentPts + solveRecMin(movesDone + 1, depth - 1);
                } else {
                    accumulatedPts = currentPts;
                }
                if (choosenPts == null || accumulatedPts >= choosenPts) {
                    choosenPts = accumulatedPts;
                    trySetMoveIndex(i, depth);
                }
                board[moveIndexes.fst][moveIndexes.snd] = EMPTY;
                mmm = i;
                debug(i, depth, choosenPts, avaliableMoves.size());
            }
        }
//        debug(mmm, depth, choosenPts, avaliableMoves.size());
        // if (movesDone == this.movesDone) {
        // debugPrint(results, avaliableMoves);
        // }
        if (depth == this.depth) {
            System.out.println("move choosen:" + choosenPts + "\t" + avaliableMoves.get(moveIndex));
        }
        return choosenPts;
    }
    
    private void trySetMoveIndex(int moveIndex, int depth) {
    	if (depth == this.depth) {
    		this.moveIndex = moveIndex;
        }
	}
    
    private void debug(int i, int depth, int choosenPts, int size){
    	if (size < 121 - 39) {
	        int temp = this.depth - depth;
	        String str ="";
	        for (int j=0; j<temp + 1; j++) {
	        	str += "" + temp + temp + temp;
	        }
//	        System.out.println(str + "move choosen:" + choosenPts + "\t" + avaliableMoves.get(i));
    	}
    }

	private int solveRecMin(int movesDone, int depth) {
        ++recCounter;
        Integer choosenPts = null;
        int accumulatedPts;
        int currentPts = 0;
        int mmm = -1;
        for (int i = 0; i < avaliableMoves.size(); ++i) {
            currentPts = 0;
            IntPair moveIndexes = avaliableMoves.get(i);
            if (isMoveAvaliable(moveIndexes)) {
                board[moveIndexes.fst][moveIndexes.snd] = MOVE;
                currentPts -= calculatePts(moveIndexes.fst, moveIndexes.snd);
                if (movesDone + 1 < movesToDo && depth > 1) {
                    accumulatedPts = currentPts + solveRecMax(movesDone + 1, depth - 1);
                } else {
                    accumulatedPts = currentPts;
                }
                if (choosenPts == null || accumulatedPts < choosenPts) {
                    choosenPts = accumulatedPts;
                }
                board[moveIndexes.fst][moveIndexes.snd] = EMPTY;
                mmm = i;
                debug(i, depth, choosenPts, avaliableMoves.size());
            }
        }
//        debug(mmm, depth, choosenPts, avaliableMoves.size());
        return choosenPts;
    }

    @SuppressWarnings("unused")
    private void debugPrint(List<IntPair> results, List<IntPair> moves) {
        for (IntPair e : results) {
            System.out.println("  " + e.fst + "\t" + moves.get(e.snd));
        }
    }
}
