package com.kasztelanic.ai.assignment3.services;

import com.kasztelanic.ai.assignment3.model.Game;

public class HumanGameSolver extends AbstractGameSolver implements GameSolver {

    public HumanGameSolver(Game game) {
        super(game);
    }

    @Override
    public void updateState(int row, int col) {
        game.changeTurn();
    }
}
