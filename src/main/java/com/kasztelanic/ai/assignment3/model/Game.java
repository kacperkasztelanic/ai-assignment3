package com.kasztelanic.ai.assignment3.model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.kasztelanic.ai.assignment3.model.enums.GameCellState;
import com.kasztelanic.ai.assignment3.model.enums.PlayerType;
import com.kasztelanic.ai.assignment3.properties.AppProperties;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.concurrent.Task;

public class Game {

    private ReadOnlyBooleanWrapper isEnd;
    private ReadOnlyBooleanWrapper isWaiting;
    private ReadOnlyIntegerWrapper boardSize;
    private ReadOnlyObjectWrapper<GameCellState>[][] gameCells;

    private Player player1;
    private Player player2;
    private Player winner;
    private ReadOnlyObjectWrapper<Player> currentPlayer;

    private int fieldsFilled;
    private int allFields;

    private ExecutorService executor = Executors.newCachedThreadPool();

    public static Game newGame(GameSettings gameSettings) {
        return new Game(gameSettings);
    }

    @SuppressWarnings("unchecked")
    private Game(GameSettings gameSettings) {
        boardSize = new ReadOnlyIntegerWrapper(gameSettings.getBoardSize());
        player1 = PlayerFactory.getPlayer(this, AppProperties.PLAYER1_NAME, gameSettings.getPlayer1Type(),
                AppProperties.PLAYER1_COLOR, gameSettings.isPlayer1AlphaBetaPruning(),
                gameSettings.getPlayer1TreeDepth());
        player2 = PlayerFactory.getPlayer(this, AppProperties.PLAYER2_NAME, gameSettings.getPlayer2Type(),
                AppProperties.PLAYER2_COLOR, gameSettings.isPlayer2AlphaBetaPruning(),
                gameSettings.getPlayer2TreeDepth());
        currentPlayer = new ReadOnlyObjectWrapper<>(player1);
        isWaiting = new ReadOnlyBooleanWrapper();
        isEnd = new ReadOnlyBooleanWrapper();
        gameCells = new ReadOnlyObjectWrapper[boardSize.get()][boardSize.get()];
        allFields = boardSize.get() * boardSize.get();
        for (int i = 0; i < boardSize.get(); i++) {
            for (int j = 0; j < boardSize.get(); j++) {
                // final int row = i;
                // final int col = j;
                gameCells[i][j] = new ReadOnlyObjectWrapper<>(GameCellState.EMPTY);
                gameCells[i][j].addListener((o, ov, nv) -> {
                    if (currentPlayer.get().getType() == PlayerType.Human) {
                        currentPlayer.get().move();
                        moveDone();
                    }
                });
            }
        }
    }

    public boolean nextMove() {
        if (currentPlayer.get().getType() != PlayerType.Human && !isEnd()) {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    currentPlayer.get().move();
                    return null;
                }

                @Override
                protected void running() {
                    isWaiting.set(true);
                }

                @Override
                protected void succeeded() {
                    isWaiting.set(false);
                }
            };
            executor.submit(task);
        }
        return !isEnd();
    }

    public void moveDone() {
        fieldsFilled++;
        checkEnd();
        if (!isEnd()) {
            changeTurn();
            nextMove();
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

    public ReadOnlyIntegerProperty getBoardSizeProperty() {
        return boardSize.getReadOnlyProperty();
    }

    public int getBoardSize() {
        return boardSize.get();
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

    public ReadOnlyBooleanProperty getIsWaitingProperty() {
        return isWaiting.getReadOnlyProperty();
    }

    public boolean isWaiting() {
        return isWaiting.get();
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
                + boardSize.get() + "]";
    }
}
