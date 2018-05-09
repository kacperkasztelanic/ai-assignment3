package com.ai.game.terminal;

public class AlphaBetaPlayer extends AbstractAIPlayer {
	
    AlphaBetaPlayer(int playerNumber, int[][] board, PairManager pairManager, int treeMaxDepth) {
        super(playerNumber, board, pairManager, treeMaxDepth);
    }
    
    AlphaBetaPlayer(int playerNumber, int[][] board, PairManager pairManager, int treeMaxDepth, int prediction) {
        super(playerNumber, board, pairManager, treeMaxDepth, prediction);
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
                currentPts = calculatePtsWithPrediction(moveIndexes.fst, moveIndexes.snd);
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
                currentPts = -calculatePtsWithPrediction(moveIndexes.fst, moveIndexes.snd);
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
	
	protected int calculatePtsWithPrediction(int y, int x) {
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
		if (prediction >= 1) {
			points *= 100;
			points += boardValues[y][x];
		}
		return points;
	}
}
