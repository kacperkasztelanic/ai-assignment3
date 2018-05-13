package com.ai.game.model.players;

import java.util.List;

import com.ai.game.model.Game;
import com.ai.game.model.Turn;
import com.ai.game.model.TurnManager;
import com.ai.game.model.enums.GameCellState;
import com.ai.game.model.enums.Heuristic;
import com.ai.game.model.enums.PlayerType;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

public abstract class AbstractAiPlayer extends AbstractPlayer {

    protected final ReadOnlyBooleanWrapper alphaBetaPruning;
    protected final ReadOnlyIntegerWrapper treeDepth;

    protected Heuristic heuristic;
    protected int[][] boardValues;

    protected List<Turn> avaliableMoves;
    protected int movesToDo;
    protected int moveIndex;

    public AbstractAiPlayer(Game game, String name, PlayerType playerType, GameCellState gameCellState, String color,
            TurnManager turnManager, boolean alphaBetaPruning, int treeDepth, Heuristic heuristic) {
        super(game, name, playerType, gameCellState, color, turnManager);
        this.alphaBetaPruning = new ReadOnlyBooleanWrapper(alphaBetaPruning);
        this.treeDepth = new ReadOnlyIntegerWrapper(treeDepth);
        this.heuristic = heuristic;
        boardValues = generateBoardValues();
        movesToDo = game.getBoardSize() * game.getBoardSize();
    }

    public ReadOnlyBooleanProperty getAlphaBetaPruningProperty() {
        return alphaBetaPruning.getReadOnlyProperty();
    }

    public boolean isAlphaBetaPruning() {
        return alphaBetaPruning.get();
    }

    public ReadOnlyIntegerProperty getTreeDepthProperty() {
        return treeDepth.getReadOnlyProperty();
    }

    public int getTreeDepth() {
        return treeDepth.get();
    }

    private int[][] generateBoardValues() {
        int[][] res = new int[game.getBoardSize()][game.getBoardSize()];
        int size = game.getBoardSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                res[i][j] = getCellValue(i, j);
            }
        }
        return res;
    }

    private int getCellValue(int i, int j) {
        int half = game.getBoardSize() / 2;
        if (heuristic == Heuristic.PREFER_BOARD_CENTER) {
            return game.getBoardSize() - Math.abs(half - i) - Math.abs(half - j);
        }
        if (heuristic == Heuristic.PREFER_BOARD_EDGES) {
            return Math.abs(half - i) + Math.abs(half - j);
        }
        return 0;
    }

    protected int calculatePtsWithPrediction(int y, int x) {
        int points = calculatePts(y, x);
        if (heuristic != Heuristic.DEFAULT) {
            points *= 100;
            points += boardValues[y][x];
        }
        return points;
    }

    @Override
    public void move(Turn turn) {
        Turn t = moveInternal();
        turnManager.removePair(turn);

        Platform.runLater(() -> {
            game.setGameCellState(t.getRow(), t.getColumn(), gameCellState.get());
            game.setGameBoardCellValue(t.getRow(), t.getColumn(), gameCellState.get().toInt());
            updatePoints(t);
            game.moveDone();
        });
    }

    protected abstract Turn moveInternal();
}
