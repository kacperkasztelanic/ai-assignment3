package com.kasztelanic.ai.assignment3.dominik;

import java.util.List;

public class MinMaxPlayer extends Player {
    private List<IntPair> avaliableMoves;
    private int depth;
    private int movesDone;
    private int moveIndex;

    MinMaxPlayer(int playerNumber, int[][] board, PairManager pairManager, int treeMaxDepth) {
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
        solveRecMax(movesDone, depth);
        IntPair moveIndexes = avaliableMoves.get(moveIndex);
        board[moveIndexes.fst][moveIndexes.snd] = playerNumber;
        upadtePts(moveIndexes.fst, moveIndexes.snd);
        pairManager.removePair(moveIndexes);
    }

    private int solveRecMax(int movesDone, int depth) {
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
                    accumulatedPts = currentPts + solveRecMin(movesDone + 1, depth - 1);
                } else {
                    accumulatedPts = currentPts;
                }
                if (choosenPts == null || accumulatedPts >= choosenPts) {
                    choosenPts = accumulatedPts;
                    trySetMoveIndex(i, movesDone);
                }
                board[moveIndexes.fst][moveIndexes.snd] = EMPTY;
            }
        }
        // if (movesDone == this.movesDone) {
        // debugPrint(results, avaliableMoves);
        // }
        if (movesDone == this.movesDone) {
            System.out.println("move choosen:" + choosenPts + "\t" + avaliableMoves.get(moveIndex));
        }
        return choosenPts;
    }
    
    private void trySetMoveIndex(int moveIndex, int movesDone) {
    	if (this.movesDone == movesDone) {
    		this.moveIndex = moveIndex;
        }
	}

	private int solveRecMin(int movesDone, int depth) {
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
                    accumulatedPts = currentPts + solveRecMax(movesDone + 1, depth - 1);
                } else {
                    accumulatedPts = currentPts;
                }
                if (choosenPts == null || accumulatedPts < choosenPts) {
                    choosenPts = accumulatedPts;

                }
                board[moveIndexes.fst][moveIndexes.snd] = EMPTY;
            }
        }
        return choosenPts;
    }

    @SuppressWarnings("unused")
    private void debugPrint(List<IntPair> results, List<IntPair> moves) {
        for (IntPair e : results) {
            System.out.println("  " + e.fst + "\t" + moves.get(e.snd));
        }
    }
}
