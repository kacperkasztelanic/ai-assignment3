package com.ai.game.terminal;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAIPlayer extends Player {
	
	final static int INT_MAX = Integer.MAX_VALUE;
	final static int INT_MIN = Integer.MIN_VALUE;
	
    protected List<IntPair> avaliableMoves;
    protected int depth;
    protected int moveIndex;
    protected int recCounter = 0;
    public  List<Long> moveTimes = new ArrayList<>();
    public List<Integer> recursions = new ArrayList<>();
    protected int prediction;
    protected int[][] boardValues;

    AbstractAIPlayer(int playerNumber, int[][] board, PairManager pairManager, int treeMaxDepth) {
        super(playerNumber, board, pairManager);
        this.depth = treeMaxDepth;
        this.prediction = 0;
        generateBoardValues();
    }
    
    AbstractAIPlayer(int playerNumber, int[][] board, PairManager pairManager, int treeMaxDepth, int prediction) {
        this(playerNumber, board, pairManager, treeMaxDepth);
        this.prediction = prediction;
        generateBoardValues();
    }

    private void generateBoardValues() {
    	if (prediction == 1) {
    		boardValuesMiddleMax();
    	} else if (prediction == 2) {
    		boardValuesMiddleMin();
    	} else if (prediction == 0) {
    		boardValues = new int[size][size];
    	}
    }
    
    private void boardValuesMiddleMin() {
    	boardValues = new int[size][size];
    	int half = size / 2;
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < size; j++) {
    			boardValues[i][j] = Math.abs(half - i) + Math.abs(half - j);
    		}
    	}
    }

    private void boardValuesMiddleMax() {
    	boardValues = new int[size][size];
    	int half = size / 2;
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < size; j++) {
    			boardValues[i][j] = size - Math.abs(half - i) - Math.abs(half - j);
    		}
    	}
    }
    
    @Override
    public void move(int movesDone) {
        long startTime = System.currentTimeMillis();
        solve(movesDone);
        long stopTime = System.currentTimeMillis();
        moveTimes.add(stopTime - startTime);
        recursions.add(recCounter);
        updateBoard(movesDone);
    }
    
    protected void solve(int movesDone) {
        this.avaliableMoves = pairManager.getUnused();
        recCounter = 0;
        solveRec(movesDone);
        System.out.println("recursion size: " + recCounter);
    }
    
    protected abstract void solveRec(int movesDone);
    
    private void updateBoard(int movesDone) {
    	IntPair moveIndexes = avaliableMoves.get(moveIndex);
        board[moveIndexes.fst][moveIndexes.snd] = playerNumber;
        upadtePts(moveIndexes.fst, moveIndexes.snd);
        pairManager.removePair(moveIndexes);
    }
    
    protected void trySetMoveIndex(int moveIndex, int depth) {
    	if (depth == this.depth) {
    		this.moveIndex = moveIndex;
        }
	}

    @SuppressWarnings("unused")
    private void debugPrint(List<IntPair> results, List<IntPair> moves) {
        for (IntPair e : results) {
            System.out.println("  " + e.fst + "\t" + moves.get(e.snd));
        }
    }
    
    
}
