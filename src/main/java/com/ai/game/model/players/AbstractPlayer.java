package com.ai.game.model.players;

import com.ai.game.model.Game;
import com.ai.game.model.Turn;
import com.ai.game.model.TurnManager;
import com.ai.game.model.enums.GameCellState;
import com.ai.game.model.enums.PlayerType;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

public abstract class AbstractPlayer {

    protected final Game game;
    protected final String name;
    protected final ReadOnlyObjectWrapper<PlayerType> type;
    protected final ReadOnlyObjectWrapper<GameCellState> gameCellState;
    protected final ReadOnlyIntegerWrapper points;
    protected final String color;
    protected final TurnManager turnManager;

    public AbstractPlayer(Game game, String name, PlayerType playerType, GameCellState gameCellState, String color,
            TurnManager turnManager) {
        this.game = game;
        this.name = name;
        this.type = new ReadOnlyObjectWrapper<>(playerType);
        this.gameCellState = new ReadOnlyObjectWrapper<>(gameCellState);
        this.color = color;
        this.turnManager = turnManager;
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

    protected boolean isMoveAvaliable(Turn turn) {
        return game.isGameBoardCellEmpty(turn.getRow(), turn.getColumn());
    }

    protected void updatePoints(Turn turn) {
        addPoints(calculatePts(turn.getRow(), turn.getColumn()));
    }

    protected int calculatePts(int y, int x) {
        int points = 0;
        boolean isRow = true;
        for (int i = 0; isRow && i < game.getBoardSize(); i++) {
            isRow = game.board[y][i] != 0;
        }
        if (isRow) {
            points += game.getBoardSize();
        }
        boolean isColumn = true;
        for (int j = 0; isColumn && j < game.getBoardSize(); j++) {
            isColumn = game.board[j][x] != 0;
        }
        if (isColumn) {
            points += game.getBoardSize();
        }
        boolean isDiagonal1 = true;
        int temp = Math.min(y, x);
        int y2 = y - temp;
        int x2 = x - temp;
        while (isDiagonal1 && x2 < game.getBoardSize() && y2 < game.getBoardSize()) {
            isDiagonal1 = game.board[y2++][x2++] != 0;
        }
        if (isDiagonal1) {
            int pts = Math.min(y2, x2);
            if (pts > 1) {
                points += pts;
            }
        }
        boolean isDiagonal2 = true;
        int temp2 = Math.min(y, game.getBoardSize() - x - 1);
        int y3 = y - temp2;
        int x3 = x + temp2;
        while (isDiagonal2 && x3 > -1 && y3 < game.getBoardSize()) {
            isDiagonal2 = game.board[y3++][x3--] != 0;
        }
        if (isDiagonal2) {
            int pts = Math.min(y3, game.getBoardSize() - x3 - 1);
            if (pts > 1) {
                points += pts;
            }
        }
        return points;
    }

    // protected int calculatePts(int y, int x) {
    // int points = 0;
    // boolean isRow = true;
    // int size = game.getBoardSize();
    // for (int i = 0; isRow && i < size; i++) {
    // isRow = !game.isGameBoardCellEmpty(y, i);
    // }
    // if (isRow) {
    // points += size;
    // }
    // boolean isColumn = true;
    // for (int j = 0; isColumn && j < size; j++) {
    // isColumn = !game.isGameBoardCellEmpty(j, x);
    // }
    // if (isColumn) {
    // points += size;
    // }
    // boolean isDiagonal1 = true;
    // int temp = Math.min(y, x);
    // int y2 = y - temp;
    // int x2 = x - temp;
    // while (isDiagonal1 && x2 < size && y2 < size) {
    // isDiagonal1 = !game.isGameBoardCellEmpty(y2++, x2++);
    // }
    // if (isDiagonal1) {
    // int pts = Math.min(y2, x2);
    // if (pts > 1) {
    // points += pts;
    // }
    // }
    // boolean isDiagonal2 = true;
    // int temp2 = Math.min(y, size - x - 1);
    // int y3 = y - temp2;
    // int x3 = x + temp2;
    // while (isDiagonal2 && x3 > -1 && y3 < size) {
    // isDiagonal1 = !game.isGameBoardCellEmpty(y3++, x3--);
    // }
    // if (isDiagonal2) {
    // int pts = Math.min(y3, size - x3 - 1);
    // if (pts > 1) {
    // points += pts;
    // }
    // }
    // return points;
    // }

    public abstract void move(Turn turn);
}
