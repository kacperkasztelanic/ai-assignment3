package com.ai.game.terminal;

public class MinMaxPlayer extends AbstractAIPlayer {
	
    MinMaxPlayer(int playerNumber, int[][] board, PairManager pairManager, int treeMaxDepth) {
        super(playerNumber, board, pairManager, treeMaxDepth);
    }
    
    protected void solveRec(int movesDone) {
    	solveRecMax(0, movesDone, depth);
    }
    
    private int solveRecMax(int value, int movesDone, int depth) {
        ++recCounter;
        int choosenPts = INT_MIN;
        for (int i = 0; i < avaliableMoves.size(); ++i) {
            IntPair moveIndexes = avaliableMoves.get(i);
            if (isMoveAvaliable(moveIndexes)) {
                board[moveIndexes.fst][moveIndexes.snd] = MOVE;
                int currentPts = calculatePts(moveIndexes.fst, moveIndexes.snd);
                int accumulatedPts;
                if (movesDone + 1 < movesToDo && depth > 1) {
                    accumulatedPts = solveRecMin(value + currentPts, movesDone + 1, depth - 1);
                } else {
                    accumulatedPts = value + currentPts;
                }
                if (accumulatedPts > choosenPts) {
                    choosenPts = accumulatedPts;
                    trySetMoveIndex(i, depth);
                }
                board[moveIndexes.fst][moveIndexes.snd] = EMPTY;
            }
        }
        if (depth == this.depth) {
            System.out.println("move choosen:" + choosenPts + "\t" + avaliableMoves.get(moveIndex));
        }
        return choosenPts;
    }
    
	private int solveRecMin(int value, int movesDone, int depth) {
        ++recCounter;
        int choosenPts = INT_MAX;
        for (int i = 0; i < avaliableMoves.size(); ++i) {
            IntPair moveIndexes = avaliableMoves.get(i);
            if (isMoveAvaliable(moveIndexes)) {
                board[moveIndexes.fst][moveIndexes.snd] = MOVE;
                int currentPts = -calculatePts(moveIndexes.fst, moveIndexes.snd);
                int accumulatedPts;
                if (movesDone + 1 < movesToDo && depth > 1) {
                    accumulatedPts = solveRecMax(value + currentPts, movesDone + 1, depth - 1);
                } else {
                    accumulatedPts = value + currentPts;
                }
                if (accumulatedPts < choosenPts) {
                    choosenPts = accumulatedPts;
                }
                board[moveIndexes.fst][moveIndexes.snd] = EMPTY;
            }
        }
        return choosenPts;
    }
}
