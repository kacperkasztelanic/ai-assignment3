package com.kasztelanic.ai.assignment3.model;

import com.kasztelanic.ai.assignment3.model.enums.GameCellState;
import com.kasztelanic.ai.assignment3.model.enums.PlayerType;
import com.kasztelanic.ai.assignment3.services.GameSolver;
import com.kasztelanic.ai.assignment3.services.GameSolverFactory;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Game {

    private ReadOnlyIntegerWrapper player1Points;
    private ReadOnlyIntegerWrapper player2Points;
    private ReadOnlyBooleanWrapper isEnd;
    private ReadOnlyIntegerWrapper boardSize;
    private ReadOnlyIntegerWrapper treeDepth;
    private ReadOnlyBooleanWrapper alphaBetaPruning;
    private ReadOnlyObjectWrapper<PlayerType> player1Type;
    private ReadOnlyObjectWrapper<PlayerType> player2Type;
    private ReadOnlyBooleanWrapper player1Turn;
    private ReadOnlyObjectWrapper<GameCellState>[][] gameCells;

    private GameSolver gameSolver;

    public static Game newGame(GameSettings gameSettings) {
        return new Game(gameSettings);
    }

    @SuppressWarnings("unchecked")
    private Game(GameSettings gameSettings) {
        boardSize = new ReadOnlyIntegerWrapper(gameSettings.getBoardSize());
        treeDepth = new ReadOnlyIntegerWrapper(gameSettings.getTreeDepth());
        alphaBetaPruning = new ReadOnlyBooleanWrapper(gameSettings.isAlphaBetaPruning());
        player1Type = new ReadOnlyObjectWrapper<>(gameSettings.getPlayer1());
        player2Type = new ReadOnlyObjectWrapper<>(gameSettings.getPlayer2());
        player1Turn = new ReadOnlyBooleanWrapper(true);
        player1Points = new ReadOnlyIntegerWrapper();
        player2Points = new ReadOnlyIntegerWrapper();
        isEnd = new ReadOnlyBooleanWrapper(false);
        gameSolver = GameSolverFactory.getSolver(this);
        gameCells = new ReadOnlyObjectWrapper[boardSize.get()][boardSize.get()];
        for (int i = 0; i < boardSize.get(); i++) {
            for (int j = 0; j < boardSize.get(); j++) {
                final int ci = i;
                final int cj = j;
                gameCells[i][j] = new ReadOnlyObjectWrapper<>(GameCellState.EMPTY);
                // gameCells[i][j].addListener((o, ov, nv) -> gameSolver.updateState());
                gameCells[i][j].addListener(new ChangeListener<GameCellState>() {
                    @Override
                    public void changed(ObservableValue<? extends GameCellState> observable, GameCellState oldValue,
                            GameCellState newValue) {
                        gameSolver.updateState();
                    }
                });
            }
        }
    }

    public ReadOnlyIntegerProperty getBoardSizeProperty() {
        return boardSize.getReadOnlyProperty();
    }

    public int getBoardSize() {
        return boardSize.get();
    }

    public ReadOnlyIntegerProperty getTreeDepthProperty() {
        return treeDepth.getReadOnlyProperty();
    }

    public int getTreeDepth() {
        return treeDepth.get();
    }

    public ReadOnlyBooleanProperty getAlphaBetaPruningProperty() {
        return alphaBetaPruning.getReadOnlyProperty();
    }

    public boolean isAlphaBetaPruning() {
        return alphaBetaPruning.get();
    }

    public ReadOnlyObjectProperty<PlayerType> getPlayer1TypeProperty() {
        return player1Type.getReadOnlyProperty();
    }

    public PlayerType getPlayer1Type() {
        return player1Type.get();
    }

    public ReadOnlyObjectProperty<PlayerType> getPlayer2TypeProperty() {
        return player2Type.getReadOnlyProperty();
    }

    public PlayerType getPlayer2Type() {
        return player2Type.get();
    }

    public ReadOnlyIntegerProperty getPlayer1PointsProperty() {
        return player1Points.getReadOnlyProperty();
    }

    public int getPlayer1Points() {
        return player1Points.get();
    }

    public void addPlayer1Points(int points) {
        player1Points.set(player1Points.get() + points);
    }

    public ReadOnlyIntegerProperty getPlayer2PointsProperty() {
        return player2Points.getReadOnlyProperty();
    }

    public int getPlayer2Points() {
        return player2Points.get();
    }

    public void addPlayer2Points(int points) {
        player2Points.set(player2Points.get() + points);
    }

    public ReadOnlyBooleanProperty getIsEndProperty() {
        return isEnd.getReadOnlyProperty();
    }

    public boolean isEnd() {
        return isEnd.get();
    }

    public void endGame() {
        isEnd.set(true);
    }

    public ReadOnlyBooleanProperty getPlayer1TurnProperty() {
        return player1Turn.getReadOnlyProperty();
    }

    public boolean isPlayer1Turn() {
        return player1Turn.get();
    }

    public void changeTurn() {
        player1Turn.set(!player1Turn.get());
    }

    public ReadOnlyObjectProperty<GameCellState> getGameCellStateProperty(int row, int col) {
        return gameCells[row][col].getReadOnlyProperty();
    }

    public GameCellState getGameCellState(int row, int col) {
        return gameCells[row][col].get();
    }

    public void setGameCellState(int row, int col, GameCellState state) {
        if (gameCells[row][col].get() == GameCellState.EMPTY) {
            gameCells[row][col].set(state);
        }
    }

    @Override
    public String toString() {
        return "GameState [player1Points=" + player1Points.get() + ", player2Points=" + player2Points.get() + ", isEnd="
                + isEnd.get() + ", player1Turn=" + player1Turn.get() + ", boardSize=" + boardSize.get() + ", treeDepth="
                + treeDepth.get() + "]";
    }
}
