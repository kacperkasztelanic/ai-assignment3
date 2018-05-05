package com.kasztelanic.ai.assignment3.dominik;

import java.util.List;

public class MinMaxPlayer extends Player {
    private List<IntPair> avaliableMoves;
    private int depth;
    private int movesDone;

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
        // IntPair result = solveRec(true, movesDone, depth);
        IntPair result = solveRecMax(movesDone, depth);
        IntPair moveIndexes = avaliableMoves.get(result.snd);
        board[moveIndexes.fst][moveIndexes.snd] = playerNumber;
        upadtePts(moveIndexes.fst, moveIndexes.snd);
        pairManager.removePair(moveIndexes);
    }

    // private IntPair solveRec(boolean isMaximalization, int movesDone, int depth)
    // {
    // List<IntPair> results = new ArrayList<>(avaliableMoves.size());
    // for (int i = 0; i < avaliableMoves.size(); i++) {
    // int currentPts = 0;
    // IntPair moveIndexes = avaliableMoves.get(i);
    // if (isMoveAvaliable(moveIndexes)) {
    // board[moveIndexes.fst][moveIndexes.snd] = MOVE;
    // int pts = calculatePts(moveIndexes.fst, moveIndexes.snd);
    // if (isMaximalization) {
    // currentPts += pts;
    // } else {
    // currentPts -= pts;
    // }
    // if (movesDone + 1 < movesToDo && depth > 1) {
    // IntPair lastChoosen = solveRec(!isMaximalization, movesDone + 1, depth - 1);
    // results.add(new IntPair(currentPts + lastChoosen.fst, i));
    // } else {
    // results.add(new IntPair(currentPts, i));
    // }
    // board[moveIndexes.fst][moveIndexes.snd] = EMPTY;
    // }
    // }
    //// if (movesDone == this.movesDone) {
    //// debugPrint(results, avaliableMoves);
    //// }
    // IntPair moveChoosen;
    // if (isMaximalization) {
    // moveChoosen = results.stream().max((i,j) -> i.compareTo(j)).get();
    // } else {
    // moveChoosen = results.stream().min((i,j) -> i.compareTo(j)).get();
    // }
    // if (movesDone == this.movesDone) {
    // System.out.println("move choosen:" + moveChoosen.fst + "\t"
    // + avaliableMoves.get(moveChoosen.snd));
    // }
    // return moveChoosen;
    // }

    private IntPair solveRecMax(int movesDone, int depth) {
        IntPair moveChoosen = null;
        IntPair currMove;
        int currentPts = 0;
        for (int i = 0; i < avaliableMoves.size(); i++) {
            currentPts = 0;
            IntPair moveIndexes = avaliableMoves.get(i);
            if (isMoveAvaliable(moveIndexes)) {
                board[moveIndexes.fst][moveIndexes.snd] = MOVE;
                int pts = calculatePts(moveIndexes.fst, moveIndexes.snd);
                currentPts += pts;
                if (movesDone + 1 < movesToDo && depth > 1) {
                    IntPair lastChoosen = solveRecMin(movesDone + 1, depth - 1);
                    currMove = new IntPair(currentPts + lastChoosen.fst, i);
                } else {
                    currMove = new IntPair(currentPts, i);
                }
                if (moveChoosen == null) {
                    moveChoosen = currMove;
                } else if (currMove.compareTo(moveChoosen) >= 0) {
                    moveChoosen = currMove;
                }
                board[moveIndexes.fst][moveIndexes.snd] = EMPTY;
            }
        }
        // if (movesDone == this.movesDone) {
        // debugPrint(results, avaliableMoves);
        // }
        // moveChoosen = results.stream().max((i, j) -> i.compareTo(j)).get();
        if (movesDone == this.movesDone) {
            System.out.println("move choosen:" + moveChoosen.fst + "\t" + avaliableMoves.get(moveChoosen.snd));
        }
        return moveChoosen;
    }

    private IntPair solveRecMin(int movesDone, int depth) {
        IntPair moveChoosen = null;
        IntPair currMove;
        int currentPts = 0;
        for (int i = 0; i < avaliableMoves.size(); i++) {
            currentPts = 0;
            IntPair moveIndexes = avaliableMoves.get(i);
            if (isMoveAvaliable(moveIndexes)) {
                board[moveIndexes.fst][moveIndexes.snd] = MOVE;
                int pts = calculatePts(moveIndexes.fst, moveIndexes.snd);
                currentPts -= pts;
                if (movesDone + 1 < movesToDo && depth > 1) {
                    IntPair lastChoosen = solveRecMax(movesDone + 1, depth - 1);
                    currMove = new IntPair(currentPts + lastChoosen.fst, i);
                } else {
                    currMove = new IntPair(currentPts, i);
                }
                if (moveChoosen == null) {
                    moveChoosen = currMove;
                } else if (currMove.compareTo(moveChoosen) < 0) {
                    moveChoosen = currMove;
                }
                board[moveIndexes.fst][moveIndexes.snd] = EMPTY;
            }
        }
        return moveChoosen;
    }

    @SuppressWarnings("unused")
    private void debugPrint(List<IntPair> results, List<IntPair> moves) {
        for (IntPair e : results) {
            System.out.println("  " + e.fst + "\t" + moves.get(e.snd));
        }
    }
}
