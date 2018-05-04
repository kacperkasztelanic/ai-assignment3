package com.kasztelanic.ai.assignment3.model.players;

import com.kasztelanic.ai.assignment3.dominik.PairManager;
import com.kasztelanic.ai.assignment3.model.Game;
import com.kasztelanic.ai.assignment3.model.enums.GameCellState;
import com.kasztelanic.ai.assignment3.model.enums.PlayerType;

public class PlayerFactory {

    public static Player getPlayer(Game game, String name, PlayerType playerType, GameCellState gameCellState,
            String color, PairManager pairManager, boolean alphaBetaPruning, int treeDepth) {
        if (playerType == PlayerType.Human) {
            return new Player(game, name, playerType, gameCellState, color, pairManager);
        }
        if (playerType == PlayerType.Computer1) {
            return new AiPlayer(game, name, playerType, gameCellState, color, pairManager, alphaBetaPruning, treeDepth);
        }
        if (playerType == PlayerType.Computer2) {
            return new RandomPlayer(game, name, playerType, gameCellState, color, pairManager, alphaBetaPruning,
                    treeDepth);
        }
        throw new UnsupportedOperationException("This playertype has not been implemented yet!");
    }
}
