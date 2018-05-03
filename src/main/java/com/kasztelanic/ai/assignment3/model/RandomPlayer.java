package com.kasztelanic.ai.assignment3.model;

import java.util.Random;

import com.kasztelanic.ai.assignment3.model.enums.GameCellState;
import com.kasztelanic.ai.assignment3.model.enums.PlayerType;

import javafx.application.Platform;

public class RandomPlayer extends AiPlayer {

    private Random random = new Random();

    public RandomPlayer(Game game, String name, PlayerType playerType, String color, boolean alphaBetaPruning,
            int treeDepth) {
        super(game, name, playerType, color, alphaBetaPruning, treeDepth);
    }

    @Override()
    public void move() {
        randomTurn();
        Platform.runLater(() -> {
            updatePoints();
            game.moveDone();
        });
    }

    private void randomTurn() {
        int randomRow = random.nextInt(game.getBoardSize());
        int randomCol = random.nextInt(game.getBoardSize());
        System.out.println("Trying: " + randomRow + ", " + randomCol);
        if (!game.getGameCellState(randomRow, randomCol).equals(GameCellState.EMPTY)) {
            randomTurn();
        } else {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> game.setGameCellState(randomRow, randomCol,
                    game.isPlayer1Turn() ? GameCellState.Player1 : GameCellState.Player2));
        }

    }

}
