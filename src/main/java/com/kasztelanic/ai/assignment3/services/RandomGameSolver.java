package com.kasztelanic.ai.assignment3.services;

import java.util.Random;

import com.kasztelanic.ai.assignment3.model.Game;
import com.kasztelanic.ai.assignment3.model.enums.GameCellState;

public class RandomGameSolver extends AbstractGameSolver implements GameSolver {

    private Random random = new Random();

    public RandomGameSolver(Game game) {
        super(game);
    }

    @Override
    public void updateState() {
        System.out.println("Chuj dupa i kamieni kupa");
        System.out.println(game);
        game.addPlayer1Points(getPointsForPlayer1());
        game.changeTurn();
        if (!game.isPlayer1Turn() && !isEnd()) {
            randomTurn();
        }
    }

    private int getPointsForPlayer1() {
        return 5;
    }

    private boolean isEnd() {
        for (int i = 0; i < game.getBoardSize(); i++) {
            for (int j = 0; j < game.getBoardSize(); j++) {
                if (game.getGameCellState(i, j).equals(GameCellState.EMPTY)) {
                    return false;
                }
            }
        }
        game.endGame();
        return true;
    }

    private void randomTurn() {
        int randomRow = random.nextInt(game.getBoardSize());
        int randomCol = random.nextInt(game.getBoardSize());
        if (!game.getGameCellState(randomRow, randomCol).equals(GameCellState.EMPTY)) {
            randomTurn();
        } else {
            game.setGameCellState(randomRow, randomCol, GameCellState.Player2);
        }
    }
}
