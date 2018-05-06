package com.kasztelanic.ai.assignment3.dominik;

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

    AbstractAIPlayer(int playerNumber, int[][] board, PairManager pairManager, int treeMaxDepth) {
        super(playerNumber, board, pairManager);
        this.depth = treeMaxDepth;
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
