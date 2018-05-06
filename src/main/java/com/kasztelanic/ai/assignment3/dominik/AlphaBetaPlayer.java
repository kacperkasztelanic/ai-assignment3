package com.kasztelanic.ai.assignment3.dominik;

public class AlphaBetaPlayer extends AbstractAIPlayer {
	
    AlphaBetaPlayer(int playerNumber, int[][] board, PairManager pairManager, int treeMaxDepth) {
        super(playerNumber, board, pairManager, treeMaxDepth);
    }
    
    protected void solveRec(int movesDone) {
    	solveRecMax(0, movesDone, depth, INT_MIN, INT_MAX);
    }

    private int solveRecMax(int value, int movesDone, int depth, int alpha, int beta) {
        ++recCounter;
        int choosenPts = alpha;
        int accumulatedPts;
        int currentPts = 0;
        for (int i = 0; i < avaliableMoves.size(); i++) {
            IntPair moveIndexes = avaliableMoves.get(i);
            if (isMoveAvaliable(moveIndexes)) {
                board[moveIndexes.fst][moveIndexes.snd] = MOVE;
                currentPts = calculatePts(moveIndexes.fst, moveIndexes.snd);
                if (movesDone + 1 < movesToDo && depth > 1) {
                    accumulatedPts = solveRecMin(value + currentPts, movesDone + 1, depth - 1, choosenPts, beta);
                } else {
                    accumulatedPts = value + currentPts;
                }
                if (accumulatedPts > choosenPts) {
                    choosenPts = accumulatedPts;
                    trySetMoveIndex(i, depth);
                }
                board[moveIndexes.fst][moveIndexes.snd] = EMPTY;
            	if (choosenPts >= beta) {
					if (depth == this.depth) {
						System.out.println("move choosen:" + choosenPts + "\t" + avaliableMoves.get(moveIndex));
					}
            		return beta;
            	}
            }
        }
        if (depth == this.depth) {
            System.out.println("move choosen:" + choosenPts + "\t" + avaliableMoves.get(moveIndex));
        }
        return choosenPts;
    }

	private int solveRecMin(int value, int movesDone, int depth, int alpha, int beta) {
        ++recCounter;
        int choosenPts = beta;
        int accumulatedPts;
        int currentPts = 0;
        for (int i = 0; i < avaliableMoves.size(); i++) {
            IntPair moveIndexes = avaliableMoves.get(i);
            if (isMoveAvaliable(moveIndexes)) {
                board[moveIndexes.fst][moveIndexes.snd] = MOVE;
                currentPts = -calculatePts(moveIndexes.fst, moveIndexes.snd);
                if (movesDone + 1 < movesToDo && depth > 1) {
                    accumulatedPts = solveRecMax(value + currentPts, movesDone + 1, depth - 1, alpha, choosenPts);
                } else {
                    accumulatedPts = value + currentPts;
                }
                if (accumulatedPts < choosenPts) {
                    choosenPts = accumulatedPts;
                }
                board[moveIndexes.fst][moveIndexes.snd] = EMPTY;
            	if (choosenPts <= alpha) {
            		return alpha;
            	}
            }
        }
        return choosenPts;
    }
}
