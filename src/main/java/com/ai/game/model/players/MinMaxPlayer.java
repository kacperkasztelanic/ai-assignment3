package com.ai.game.model.players;

import java.util.List;

import com.ai.game.terminal.MovePair;
import com.ai.game.terminal.PairManager;
import com.ai.game.model.Game;
import com.ai.game.model.Turn;
import com.ai.game.model.enums.GameCellState;
import com.ai.game.model.enums.PlayerType;

public class MinMaxPlayer extends AbstractAiPlayer {

    private List<MovePair> avaliableMoves;
    private int movesToDo;

    public MinMaxPlayer(Game game, String name, PlayerType playerType, GameCellState gameCellState, String color,
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
        MovePair result = solveRecMax(game.getMovesDone(), treeDepth.get());
        MovePair moveIndexes = avaliableMoves.get(result.snd);

        Turn turn = Turn.of(moveIndexes.fst, moveIndexes.snd);
        pairManager.removePair(moveIndexes);
        return turn;
    }

    private MovePair solveRecMax(int movesDone, int depth) {
        MovePair moveChoosen = null;
        MovePair currMove;
        int currentPts = 0;
        for (int i = 0; i < avaliableMoves.size(); i++) {
            currentPts = 0;
            MovePair moveIndexes = avaliableMoves.get(i);
            if (isMoveAvaliable(moveIndexes)) {
                game.board[moveIndexes.fst][moveIndexes.snd] = gameCellState.get().toInt();
                int pts = calculatePts(moveIndexes.fst, moveIndexes.snd);
                currentPts += pts;
                if (movesDone + 1 < movesToDo && depth > 1) {
                    MovePair lastChoosen = solveRecMin(movesDone + 1, depth - 1);
                    currMove = new MovePair(currentPts + lastChoosen.fst, i);
                } else {
                    currMove = new MovePair(currentPts, i);
                }
                if (moveChoosen == null) {
                    moveChoosen = currMove;
                } else if (currMove.compareTo(moveChoosen) >= 0) {
                    moveChoosen = currMove;
                }
                game.board[moveIndexes.fst][moveIndexes.snd] = GameCellState.EMPTY.toInt();
            }
        }
        return moveChoosen;
    }

    private MovePair solveRecMin(int movesDone, int depth) {
        MovePair moveChoosen = null;
        MovePair currMove;
        int currentPts = 0;
        for (int i = 0; i < avaliableMoves.size(); i++) {
            currentPts = 0;
            MovePair moveIndexes = avaliableMoves.get(i);
            if (isMoveAvaliable(moveIndexes)) {
                game.board[moveIndexes.fst][moveIndexes.snd] = gameCellState.get().toInt();
                int pts = calculatePts(moveIndexes.fst, moveIndexes.snd);
                currentPts -= pts;
                if (movesDone + 1 < movesToDo && depth > 1) {
                    MovePair lastChoosen = solveRecMax(movesDone + 1, depth - 1);
                    currMove = new MovePair(currentPts + lastChoosen.fst, i);
                } else {
                    currMove = new MovePair(currentPts, i);
                }
                if (moveChoosen == null) {
                    moveChoosen = currMove;
                } else if (currMove.compareTo(moveChoosen) < 0) {
                    moveChoosen = currMove;
                }
                game.board[moveIndexes.fst][moveIndexes.snd] = GameCellState.EMPTY.toInt();
            }
        }
        return moveChoosen;
    }
}
