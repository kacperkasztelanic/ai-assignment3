package com.kasztelanic.ai.assignment3.services;

import com.kasztelanic.ai.assignment3.model.Game;

public abstract class AbstractGameSolver {

    protected final Game game;

    public AbstractGameSolver(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
