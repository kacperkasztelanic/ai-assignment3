package com.kasztelanic.ai.assignment3.model.players;

import java.util.List;

import com.kasztelanic.ai.assignment3.dominik.IntPair;
import com.kasztelanic.ai.assignment3.dominik.PairManager;
import com.kasztelanic.ai.assignment3.model.Game;
import com.kasztelanic.ai.assignment3.model.Turn;
import com.kasztelanic.ai.assignment3.model.enums.GameCellState;
import com.kasztelanic.ai.assignment3.model.enums.PlayerType;

public class AiPlayer extends AbstractAiPlayer {

    private List<IntPair> avaliableMoves;
    private int movesDone;
    private int movesToDo;
    private int[][] board = null;

    public AiPlayer(Game game, String name, PlayerType playerType, GameCellState gameCellState, String color,
            PairManager pairManager, boolean alphaBetaPruning, int treeDepth) {
        super(game, name, playerType, gameCellState, color, pairManager, alphaBetaPruning, treeDepth);
        movesDone = game.getBoardSize() * game.getBoardSize();
    }

    @Override
    protected Turn moveInternal() {
        return solve(movesDone);
    }

    private Turn solve(int movesDone) {
        this.board = game.getBoardStateInt();
        this.movesDone = movesDone;
        this.avaliableMoves = pairManager.getUnused();
        // IntPair result = solveRec(true, movesDone, depth);
        IntPair result = solveRecMax(movesDone, treeDepth.get());
        System.out.println(treeDepth.get());
        IntPair moveIndexes = avaliableMoves.get(result.snd);

        Turn turn = Turn.of(moveIndexes.fst, moveIndexes.snd);
        pairManager.removePair(moveIndexes);
        return turn;
    }

    private IntPair solveRecMax(int movesDone, int depth) {
        IntPair moveChoosen = null;
        IntPair currMove;
        int currentPts = 0;
        for (int i = 0; i < avaliableMoves.size(); i++) {
            currentPts = 0;
            IntPair moveIndexes = avaliableMoves.get(i);
            if (isMoveAvaliable(moveIndexes)) {
                board[moveIndexes.fst][moveIndexes.snd] = game.getCurrentPlayer().getGameCellState().toInt();
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
                board[moveIndexes.fst][moveIndexes.snd] = GameCellState.EMPTY.toInt();
            }
        }
        // if (movesDone == this.movesDone) {
        // debugPrint(results, avaliableMoves);
        // }
        // moveChoosen = results.stream().max((i,j) -> i.compareTo(j)).get();
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
                board[moveIndexes.fst][moveIndexes.snd] = game.getCurrentPlayer().getGameCellState().toInt();
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
                board[moveIndexes.fst][moveIndexes.snd] = GameCellState.EMPTY.toInt();
            }
        }
        return moveChoosen;
    }
}
