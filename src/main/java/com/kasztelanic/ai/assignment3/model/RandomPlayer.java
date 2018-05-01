package com.kasztelanic.ai.assignment3.model;

import java.util.Random;

import com.kasztelanic.ai.assignment3.model.enums.GameCellState;
import com.kasztelanic.ai.assignment3.model.enums.PlayerType;

public class RandomPlayer extends Player {

    private Random random = new Random();

    public RandomPlayer(Game game, String name, PlayerType playerType, String color) {
        super(game, name, playerType, color);
    }

    @Override()
    public void move(int row, int col) {
        System.out.println("Random move");
        randomTurn();
        // game.setGameCellState(game.getBoardSize() - 1, game.getBoardSize() - 1,
        // game.isPlayer1Turn() ? GameCellState.Player1 : GameCellState.Player2);
        updatePoints();
        game.moveDone();
    }

    private void randomTurn() {
        int randomRow = random.nextInt(game.getBoardSize());
        int randomCol = random.nextInt(game.getBoardSize());
        System.out.println("Trying: " + randomRow + ", " + randomCol);
        if (!game.getGameCellState(randomRow, randomCol).equals(GameCellState.EMPTY)) {
            randomTurn();
            // System.out.println("Not");
        } else {
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            game.setGameCellState(randomRow, randomCol,
                    game.isPlayer1Turn() ? GameCellState.Player1 : GameCellState.Player2);
        }

    }

}
