package com.kasztelanic.ai.assignment3.model.players;

import com.kasztelanic.ai.assignment3.dominik.IntPair;
import com.kasztelanic.ai.assignment3.dominik.PairManager;
import com.kasztelanic.ai.assignment3.model.Game;
import com.kasztelanic.ai.assignment3.model.Turn;
import com.kasztelanic.ai.assignment3.model.enums.GameCellState;
import com.kasztelanic.ai.assignment3.model.enums.PlayerType;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class Player {

    protected final Game game;
    protected final String name;
    protected final ReadOnlyObjectWrapper<PlayerType> type;
    protected final ReadOnlyObjectWrapper<GameCellState> gameCellState;
    protected final ReadOnlyIntegerWrapper points;
    protected final String color;
    protected final PairManager pairManager;
    protected int[][] board = null;

    public Player(Game game, String name, PlayerType playerType, GameCellState gameCellState, String color,
            PairManager pairManager) {
        this.game = game;
        this.name = name;
        this.type = new ReadOnlyObjectWrapper<>(playerType);
        this.gameCellState = new ReadOnlyObjectWrapper<>(gameCellState);
        this.color = color;
        this.pairManager = pairManager;
        this.points = new ReadOnlyIntegerWrapper();
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public ReadOnlyObjectProperty<PlayerType> getTypeProperty() {
        return type.getReadOnlyProperty();
    }

    public PlayerType getType() {
        return type.get();
    }

    public ReadOnlyObjectProperty<GameCellState> getGameCellStateProperty() {
        return gameCellState.getReadOnlyProperty();
    }

    public GameCellState getGameCellState() {
        return gameCellState.get();
    }

    public ReadOnlyIntegerProperty getPointsProperty() {
        return points.getReadOnlyProperty();
    }

    public int getPoints() {
        return points.get();
    }

    public void addPoints(int p) {
        points.set(points.get() + p);
    }

    @Override
    public String toString() {
        return name;
    }

    protected boolean isMoveAvaliable(IntPair pair) {
        return game.getGameCellState(pair.fst, pair.snd) == GameCellState.EMPTY;
    }

    public void move(Turn turn) {
        board = game.getBoardStateInt();
        updatePoints(turn);
    }

    protected void updatePoints(Turn turn) {
        addPoints(calculatePts(turn.getRow(), turn.getColumn()));
    }

    protected int calculatePts(int y, int x) {
        int points = 0;
        boolean isRow = true;
        int size = game.getBoardSize();
        for (int i = 0; isRow && i < size; i++) {
            isRow = board[y][i] != GameCellState.EMPTY.toInt();
        }
        if (isRow) {
            points += size;
        }
        boolean isColumn = true;
        for (int j = 0; isColumn && j < size; j++) {
            isColumn = board[j][x] != GameCellState.EMPTY.toInt();
        }
        if (isColumn) {
            points += size;
        }
        boolean isDiagonal1 = true;
        int temp = Math.min(y, x);
        int y2 = y - temp;
        int x2 = x - temp;
        while (isDiagonal1 && x2 < size && y2 < size) {
            isDiagonal1 = board[y2++][x2++] != GameCellState.EMPTY.toInt();
        }
        if (isDiagonal1) {
            int pts = Math.min(y2, x2);
            if (pts > 1) {
                points += pts;
            }
        }
        boolean isDiagonal2 = true;
        int temp2 = Math.min(y, size - x - 1);
        int y3 = y - temp2;
        int x3 = x + temp2;
        while (isDiagonal2 && x3 > -1 && y3 < size) {
            isDiagonal2 = board[y3++][x3--] != GameCellState.EMPTY.toInt();
        }
        if (isDiagonal2) {
            int pts = Math.min(y3, size - x3 - 1);
            if (pts > 1) {
                points += pts;
            }
        }
        return points;
    }
}
