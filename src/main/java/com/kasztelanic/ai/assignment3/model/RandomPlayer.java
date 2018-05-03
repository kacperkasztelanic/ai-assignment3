package com.kasztelanic.ai.assignment3.model;

import java.util.Random;

import com.kasztelanic.ai.assignment3.model.enums.GameCellState;
import com.kasztelanic.ai.assignment3.model.enums.PlayerType;

public class RandomPlayer extends AiPlayer {

    private Random random = new Random();

    public RandomPlayer(Game game, String name, PlayerType playerType, String color, boolean alphaBetaPruning,
            int treeDepth) {
        super(game, name, playerType, color, alphaBetaPruning, treeDepth);
    }

    @Override
    protected Turn moveInternal() {
        int randomRow = random.nextInt(game.getBoardSize());
        int randomCol = random.nextInt(game.getBoardSize());
        System.out.println("Trying: " + randomRow + ", " + randomCol);
        if (!game.getGameCellState(randomRow, randomCol).equals(GameCellState.EMPTY)) {
            return moveInternal();
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Turn.of(randomRow, randomCol);
        }
    }
}
