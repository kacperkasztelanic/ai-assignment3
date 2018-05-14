package com.ai.game.model.players;

import java.util.List;

import com.ai.game.model.Game;
import com.ai.game.model.Turn;
import com.ai.game.model.TurnManager;
import com.ai.game.model.enums.GameCellState;
import com.ai.game.model.enums.Heuristic;
import com.ai.game.model.enums.PlayerType;
import com.ai.game.model.enums.SameValuesBehavior;
import com.ai.game.model.enums.Strategy;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

public abstract class AbstractAiPlayer extends AbstractPlayer {

    protected final ReadOnlyBooleanWrapper alphaBetaPruning;
    protected final ReadOnlyIntegerWrapper treeDepth;

    protected final Heuristic heuristic;
    protected final Strategy strategy;
    protected final SameValuesBehavior sameValuesBehavior;
    protected int[][] boardValues;

    protected List<Turn> avaliableMoves;
    protected int movesToDo;
    protected int moveIndex;

    public AbstractAiPlayer(Game game, String name, PlayerType playerType, GameCellState gameCellState, String color,
            TurnManager turnManager, boolean alphaBetaPruning, int treeDepth, Heuristic heuristic, Strategy strategy,
            SameValuesBehavior sameValuesBehavior) {
        super(game, name, playerType, gameCellState, color, turnManager);
        this.alphaBetaPruning = new ReadOnlyBooleanWrapper(alphaBetaPruning);
        this.treeDepth = new ReadOnlyIntegerWrapper(treeDepth);
        this.heuristic = heuristic;
        this.strategy = strategy;
        this.sameValuesBehavior = sameValuesBehavior;
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

    protected int calculatePtsWithPredictionForCurrentPlayer(int y, int x) {
        int points = calculatePtsWithPrediction(y, x);
        if (strategy == Strategy.NOT_LAST_BUT_ONE_IN_LINE) {
            points += calculateLastButOne(y, x);
        }
        return points;
    }

    protected int calculateLastButOne(int y, int x) {
        int points = 0;
        int divideBy = 3;
        int row = 0;
        int size = game.getBoardSize();
        for (int i = 0; i < size; i++) {
            row += game.isGameBoardCellEmpty(y, i) ? 1 : 0;
        }
        if (row == 1) {
            points -= Math.max(1, size / divideBy);
        }
        int column = 0;
        for (int j = 0; j < size; j++) {
            column += game.isGameBoardCellEmpty(j, x) ? 1 : 0;
        }
        if (column == 1) {
            points -= Math.max(1, size / divideBy);
        }
        int diagonal1 = 0;
        int temp = Math.min(y, x);
        int y2 = y - temp;
        int x2 = x - temp;
        while (x2 < size && y2 < size) {
            diagonal1 = game.isGameBoardCellEmpty(y2++, x2++) ? 1 : 0;
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
            diagonal2 += game.isGameBoardCellEmpty(y3++, x3--) ? 1 : 0;
        }
        if (diagonal2 == 1) {
            int pts = Math.min(y3, size - x3 - 1) / 2;
            if (pts > 1) {
                points -= Math.max(1, pts / divideBy);
            }
        }
        return points;
    }

    @Override
    public void move(Turn turn) {
        if (sameValuesBehavior == SameValuesBehavior.DEFAULT) {
            this.avaliableMoves = turnManager.getUnused();
        } else if (sameValuesBehavior == SameValuesBehavior.RANDOM) {
            this.avaliableMoves = turnManager.getRandomizedUnused();
        }
        Turn t = moveInternal();
        turnManager.removePair(t);

        Platform.runLater(() -> {
            game.setGameCellState(t.getRow(), t.getColumn(), gameCellState.get());
            game.setGameBoardCellValue(t.getRow(), t.getColumn(), gameCellState.get().toInt());
            updatePoints(t);
            game.moveDone();
        });
    }

    protected abstract Turn moveInternal();
}
