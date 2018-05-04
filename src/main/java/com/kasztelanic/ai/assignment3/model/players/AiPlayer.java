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
    private int movesToDo;

    public AiPlayer(Game game, String name, PlayerType playerType, GameCellState gameCellState, String color,
            PairManager pairManager, boolean alphaBetaPruning, int treeDepth) {
        super(game, name, playerType, gameCellState, color, pairManager, alphaBetaPruning, treeDepth);
        movesToDo = game.getBoardSize() * game.getBoardSize();
    }

    @Override
    protected Turn moveInternal() {
        return solve();
    }

    private Turn solve() {
        this.avaliableMoves = pairManager.getUnused();
        // IntPair result = solveRec(true, movesDone, depth);
        IntPair result = solveRecMax(game.getMovesDone(), treeDepth.get());
        // System.out.println("Depth:" + treeDepth.get());
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
        if (movesDone == game.getMovesDone()) {
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

    @SuppressWarnings("unused")
    private void debugPrint(List<IntPair> results, List<IntPair> moves) {
        for (IntPair e : results) {
            System.out.println("  " + e.fst + "\t" + moves.get(e.snd));
        }
    }
}
