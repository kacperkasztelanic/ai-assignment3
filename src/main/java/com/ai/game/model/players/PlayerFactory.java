package com.ai.game.model.players;

import com.ai.game.terminal.MovesManager;
import com.ai.game.model.Game;
import com.ai.game.model.enums.GameCellState;
import com.ai.game.model.enums.PlayerType;

public class PlayerFactory {

    public static Player getPlayer(Game game, String name, PlayerType playerType, GameCellState gameCellState,
            String color, MovesManager pairManager, boolean alphaBetaPruning, int treeDepth) {
        if (playerType == PlayerType.Human) {
            return new Player(game, name, playerType, gameCellState, color, pairManager);
        }
        if (playerType == PlayerType.Computer1) {
            return new MinMaxPlayer(game, name, playerType, gameCellState, color, pairManager, alphaBetaPruning, treeDepth);
        }
        if (playerType == PlayerType.Computer2) {
            return new RandomPlayer(game, name, playerType, gameCellState, color, pairManager, alphaBetaPruning,
                    treeDepth);
        }
        throw new UnsupportedOperationException("This playertype has not been implemented yet!");
    }
}
