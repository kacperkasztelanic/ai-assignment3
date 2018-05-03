package com.kasztelanic.ai.assignment3.model.players;

import com.kasztelanic.ai.assignment3.model.Game;
import com.kasztelanic.ai.assignment3.model.enums.PlayerType;

public class PlayerFactory {

    public static Player getPlayer(Game game, String name, PlayerType playerType, String color,
            boolean alphaBetaPruning, int treeDepth) {
        if (playerType == PlayerType.Human) {
            return new Player(game, name, playerType, color);
        }
        if (playerType == PlayerType.Computer1) {
            return new RandomPlayer(game, name, playerType, color, alphaBetaPruning, treeDepth);
        }
        throw new UnsupportedOperationException("This playertype has not been implemented yet!");
    }
}
