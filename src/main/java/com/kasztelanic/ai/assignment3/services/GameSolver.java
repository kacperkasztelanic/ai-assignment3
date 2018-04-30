package com.kasztelanic.ai.assignment3.services;

import com.kasztelanic.ai.assignment3.model.Game;

public class GameSolver {

    private final Game game;

    public GameSolver(Game game) {
        this.game = game;
    }

    public void updateState() {
        System.out.println("Chuj dupa i kamieni kupa");
    }
}
