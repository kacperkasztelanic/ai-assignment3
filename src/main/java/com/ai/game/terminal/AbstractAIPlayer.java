package com.ai.game.terminal;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAIPlayer extends Player {

    final static int INT_MAX = Integer.MAX_VALUE;
    final static int INT_MIN = Integer.MIN_VALUE;

    protected int depth;
    protected int moveIndex;
    protected List<MovePair> avaliableMoves;

    protected Strategy strategy;
    protected MoveOrderType movesOrderType;
    protected MoveIfSameValues movesIfSameValues;
    protected int[][] boardValues;

    protected int recCounter = 0;
    public List<Long> moveTimes = new ArrayList<>();
    public List<Integer> recursions = new ArrayList<>();

    public AbstractAIPlayer(int playerNumber, int[][] board, MovesManager pairManager, int treeMaxDepth) {
        super(playerNumber, board, pairManager);
        this.depth = treeMaxDepth;
        this.strategy = Strategy.MaximalizeDifferencePoints;
        this.movesOrderType = MoveOrderType.Default;
        this.movesIfSameValues = MoveIfSameValues.Default;
    }

    public AbstractAIPlayer buildStrategy(Strategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public AbstractAIPlayer buildOrderType(MoveOrderType movesOrderType) {
        this.movesOrderType = movesOrderType;
        generateBoardValues();
        return this;
    }

    public AbstractAIPlayer buildIfSameValuesType(MoveIfSameValues movesIfSameValues) {
        this.movesIfSameValues = movesIfSameValues;
        return this;
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
        if (movesIfSameValues == MoveIfSameValues.Default) {
            this.avaliableMoves = pairManager.getUnused();
        } else if (movesIfSameValues == MoveIfSameValues.Random) {
            this.avaliableMoves = pairManager.getRandomizedUnused();
        }
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

    protected void printChoosenMove(int depth, int choosenPts) {
        if (depth == this.depth) {
            System.out.println("move choosen:" + choosenPts + "\t" + avaliableMoves.get(moveIndex));
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

    protected int calculatePtsWithPredictionForCurrentPlayer(int y, int x) {
        int points = calculatePtsWithPrediction(y, x);
        if (strategy == Strategy.NotLastButOneInLine) {
            points += calculateLastButOne(y, x);
        }
        return points;
    }

    protected int calculateLastButOne(int y, int x) {
        int points = 0;
        int divideBy = 3;
        int row = 0;
        for (int i = 0; i < size; i++) {
            row += board[y][i] != EMPTY ? 0 : 1;
        }
        if (row == 1) {
            points -= Math.max(1, size / divideBy);
        }
        int column = 0;
        for (int j = 0; j < size; j++) {
            column += board[j][x] != EMPTY ? 0 : 1;
        }
        if (column == 1) {
            points -= Math.max(1, size / divideBy);
        }
        int diagonal1 = 0;
        int temp = Math.min(y, x);
        int y2 = y - temp;
        int x2 = x - temp;
        while (x2 < size && y2 < size) {
            diagonal1 += board[y2++][x2++] != EMPTY ? 0 : 1;
        }
        if (diagonal1 == 1) {
            int pts = Math.min(y2, x2) / 2;
            if (pts > 1) {
                points -= Math.max(1, pts / divideBy);
            }
        }
        int diagonal2 = 0;
        int temp2 = Math.min(y, size - x - 1);
        int y3 = y - temp2;
        int x3 = x + temp2;
        while (x3 > -1 && y3 < size) {
            diagonal2 += board[y3++][x3--] != EMPTY ? 0 : 1;
        }
        if (diagonal2 == 1) {
            int pts = Math.min(y3, size - x3 - 1) / 2;
            if (pts > 1) {
                points -= Math.max(1, pts / divideBy);
            }
        }
        return points;
    }
}
