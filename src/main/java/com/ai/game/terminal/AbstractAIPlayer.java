package com.ai.game.terminal;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAIPlayer extends Player {
	
	final static int INT_MAX = Integer.MAX_VALUE;
	final static int INT_MIN = Integer.MIN_VALUE;
	
    protected List<MovePair> avaliableMoves;
    protected int depth;
    protected int moveIndex;
    protected MoveOrderType movesOrderType;
    protected int[][] boardValues;
    
    protected int recCounter = 0;
    public  List<Long> moveTimes = new ArrayList<>();
    public List<Integer> recursions = new ArrayList<>();

    public AbstractAIPlayer(int playerNumber, int[][] board, PairManager pairManager, int treeMaxDepth) {
        super(playerNumber, board, pairManager);
        this.depth = treeMaxDepth;
        this.movesOrderType = MoveOrderType.Default;
        generateBoardValues();
    }
    
    public AbstractAIPlayer(int playerNumber, int[][] board, PairManager pairManager, int treeMaxDepth, MoveOrderType movesOrderType) {
        this(playerNumber, board, pairManager, treeMaxDepth);
        this.movesOrderType = movesOrderType;
        generateBoardValues();
    }

    private void generateBoardValues() {
    	if (movesOrderType == MoveOrderType.PreferMiddleOfBoard) {
    		boardValuesMiddleMax();
    	} else if (movesOrderType == MoveOrderType.PreferBordersOfBoard) {
    		boardValuesMiddleMin();
    	} else if (movesOrderType == MoveOrderType.Default) {
    		boardValues = new int[size][size];
    	}
    }
    
    private void boardValuesMiddleMin() {
    	boardValues(true);
    }

    private void boardValuesMiddleMax() {
    	boardValues(false);
    }
    
    private void boardValues(boolean isMin) {
    	boardValues = new int[size][size];
    	int half = size / 2;
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < size; j++) {
    			if (isMin) {
    				boardValues[i][j] = Math.abs(half - i) + Math.abs(half - j);
    			} else {
    				boardValues[i][j] = size - Math.abs(half - i) - Math.abs(half - j);
    			}
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
    	MovePair moveIndexes = avaliableMoves.get(moveIndex);
        board[moveIndexes.fst][moveIndexes.snd] = playerNumber;
        upadtePts(moveIndexes.fst, moveIndexes.snd);
        pairManager.removePair(moveIndexes);
    }
    
    protected void trySetMoveIndex(int moveIndex, int depth) {
    	if (depth == this.depth) {
    		this.moveIndex = moveIndex;
        }
	}
    
	protected int calculatePtsWithPrediction(int y, int x) {
		int points = calculatePts(y, x);
		if (movesOrderType.toInt() >= 1) {
			points *= 100;
			points += boardValues[y][x];
		}
		return points;
	}
	
	protected void printChoosenMove(int depth, int choosenPts) {
	    if (depth == this.depth) {
	        System.out.println("move choosen:" + choosenPts + "\t" + avaliableMoves.get(moveIndex));
	    }
	}
}
