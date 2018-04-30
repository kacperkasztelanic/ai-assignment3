package com.kasztelanic.ai.assignment3.services;

import com.kasztelanic.ai.assignment3.model.Game;
import com.kasztelanic.ai.assignment3.model.enums.PlayerType;

public class GameSolverFactory {

    public static GameSolver getSolver(Game game) {
        if (game.getPlayer2Type().equals(PlayerType.Human)) {
            return new HumanGameSolver(game);
        } else {
            return new RandomGameSolver(game);
        }
    }
}
