package com.kasztelanic.ai.assignment3.model;

import com.kasztelanic.ai.assignment3.model.enums.GameCellState;
import com.kasztelanic.ai.assignment3.model.enums.PlayerType;
import com.kasztelanic.ai.assignment3.properties.AppProperties;
import com.kasztelanic.ai.assignment3.services.GameSolver;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class Game {

    private ReadOnlyBooleanWrapper isEnd;
    private ReadOnlyIntegerWrapper boardSize;
    private ReadOnlyIntegerWrapper treeDepth;
    private ReadOnlyBooleanWrapper alphaBetaPruning;
    private ReadOnlyObjectWrapper<GameCellState>[][] gameCells;

    private Player player1;
    private Player player2;
    private Player winner;
    private ReadOnlyObjectWrapper<Player> currentPlayer;

    private GameSolver gameSolver;
    private int fieldsFilled;
    private int allFields;

    public static Game newGame(GameSettings gameSettings) {
        return new Game(gameSettings);
    }

    @SuppressWarnings("unchecked")
    private Game(GameSettings gameSettings) {
        boardSize = new ReadOnlyIntegerWrapper(gameSettings.getBoardSize());
        treeDepth = new ReadOnlyIntegerWrapper(gameSettings.getTreeDepth());
        alphaBetaPruning = new ReadOnlyBooleanWrapper(gameSettings.isAlphaBetaPruning());
        // player1 = new Player(this, AppProperties.PLAYER1_NAME,
        // gameSettings.getPlayer1(), AppProperties.PLAYER1_COLOR);
        // player2 = new Player(this, AppProperties.PLAYER2_NAME,
        // gameSettings.getPlayer2(), AppProperties.PLAYER2_COLOR);
        player1 = new RandomPlayer(this, AppProperties.PLAYER1_NAME, gameSettings.getPlayer1(),
                AppProperties.PLAYER1_COLOR);
        player2 = new RandomPlayer(this, AppProperties.PLAYER2_NAME, gameSettings.getPlayer2(),
                AppProperties.PLAYER2_COLOR);
        currentPlayer = new ReadOnlyObjectWrapper<>(player1);
        isEnd = new ReadOnlyBooleanWrapper(false);
        // gameSolver = GameSolverFactory.getSolver(this);
        gameCells = new ReadOnlyObjectWrapper[boardSize.get()][boardSize.get()];
        allFields = boardSize.get() * boardSize.get();
        for (int i = 0; i < boardSize.get(); i++) {
            for (int j = 0; j < boardSize.get(); j++) {
                final int row = i;
                final int col = j;
                gameCells[i][j] = new ReadOnlyObjectWrapper<>(GameCellState.EMPTY);
                gameCells[i][j].addListener((o, ov, nv) -> {
                    System.out.println("Change listener");
                    if (currentPlayer.get().getType() == PlayerType.Human) {
                        currentPlayer.get().move(row, col);
                        moveDone();
                    }

                    // System.out.println("Changed " + row + ", " + col);
                    // if (!isEnd()) {
                    // currentPlayer.get().move(row, col);
                    // System.out.println("Current player is: " + currentPlayer.get());
                    // changeTurn();
                    // fieldsFilled++;
                    // // currentPlayer.get().move(row, col);
                    // System.out.println("Current player is: " + currentPlayer.get());
                    // System.out.println("Fields filled: " + fieldsFilled);
                    // }
                    // checkEnd();
                });
            }
        }
    }

    public void autobegin() {
        // if (getFilled() == 0) {
        player1.move(0, 0);
        moveDone();
        // } else {
        // currentPlayer.get().move(0, 0);
        // moveDone();
        // }
    }

    public void moveDone() {
        fieldsFilled++;
        checkEnd();
        System.out.println("Filled: " + fieldsFilled + "|" + allFields);
        if (!isEnd()) {
            changeTurn();
            if (currentPlayer.get().getType() != PlayerType.Human) {
                currentPlayer.get().move(0, 0);
            }
        }
    }

    private void checkEnd() {
        if (fieldsFilled >= allFields) {
            if (player1.getPoints() > player2.getPoints()) {
                winner = player1;
            } else if (player2.getPoints() < player1.getPoints()) {
                winner = player2;
            }
            endGame();
        }
    }

    private int getFilled() {
        int res = 0;
        for (int i = 0; i < boardSize.get(); i++) {
            for (int j = 0; j < boardSize.get(); j++) {
                if (gameCells[i][j].get() != GameCellState.EMPTY) {
                    res++;
                }
            }
        }
        return res;
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

    public ReadOnlyBooleanProperty getIsEndProperty() {
        return isEnd.getReadOnlyProperty();
    }

    public boolean isEnd() {
        return isEnd.get();
    }

    public void endGame() {
        isEnd.set(true);
    }

    public ReadOnlyObjectProperty<Player> getCurrentPlayerProperty() {
        return currentPlayer.getReadOnlyProperty();
    }

    public Player getCurrentPlayer() {
        return currentPlayer.get();
    }

    public boolean isPlayer1Turn() {
        return currentPlayer.get() == player1;
    }

    public void changeTurn() {
        currentPlayer.set(currentPlayer.get() == player1 ? player2 : player1);
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getWinner() {
        return winner;
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

    public GameCellState[][] getBoardState() {
        GameCellState[][] boardState = new GameCellState[boardSize.get()][boardSize.get()];
        for (int i = 0; i < boardSize.get(); i++) {
            for (int j = 0; j < boardSize.get(); j++) {
                boardState[i][j] = gameCells[i][j].get();
            }
        }
        return boardState;
    }

    public int[][] getBoardStateInt() {
        int[][] boardState = new int[boardSize.get()][boardSize.get()];
        for (int i = 0; i < boardSize.get(); i++) {
            for (int j = 0; j < boardSize.get(); j++) {
                boardState[i][j] = gameCells[i][j].get().toInt();
            }
        }
        return boardState;
    }

    @Override
    public String toString() {
        return "GameState [player1Points=" + getPlayer1().getPoints() + ", player2Points=" + getPlayer2().getPoints()
                + ", isEnd=" + isEnd.get() + ", player1Turn=" + (currentPlayer.get() == player1) + ", boardSize="
                + boardSize.get() + ", treeDepth=" + treeDepth.get() + "]";
    }
}
