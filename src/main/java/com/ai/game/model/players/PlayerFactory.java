package com.ai.game.model.players;

import com.ai.game.model.Game;
import com.ai.game.model.TurnManager;
import com.ai.game.model.enums.GameCellState;
import com.ai.game.model.enums.Heuristic;
import com.ai.game.model.enums.PlayerType;

public class PlayerFactory {

    public static AbstractPlayer getPlayer(Game game, String name, PlayerType playerType, GameCellState gameCellState,
            String color, TurnManager turnManager, boolean alphaBetaPruning, int treeDepth) {
        if (playerType == PlayerType.HUMAN) {
            return new HumanPlayer(game, name, playerType, gameCellState, color, turnManager);
        }
        if (playerType == PlayerType.MAX_POINT_GAP_AND_CENTER) {
            if (alphaBetaPruning) {
                return new AlphaBetaPlayer(game, name, playerType, gameCellState, color, turnManager, alphaBetaPruning,
                        treeDepth, Heuristic.PREFER_BOARD_CENTER);
            } else {
                return new MinMaxPlayer(game, name, playerType, gameCellState, color, turnManager, alphaBetaPruning,
                        treeDepth, Heuristic.PREFER_BOARD_CENTER);
            }
        }
        if (playerType == PlayerType.MAX_POINT_GAP_AND_EDGES) {
            if (alphaBetaPruning) {
                return new AlphaBetaPlayer(game, name, playerType, gameCellState, color, turnManager, alphaBetaPruning,
                        treeDepth, Heuristic.PREFER_BOARD_EDGES);
            } else {
                return new MinMaxPlayer(game, name, playerType, gameCellState, color, turnManager, alphaBetaPruning,
                        treeDepth, Heuristic.PREFER_BOARD_EDGES);
            }
        }
        if (playerType == PlayerType.MAX_POINT_GAP_AND_SEQ) {
            if (alphaBetaPruning) {
                return new AlphaBetaPlayer(game, name, playerType, gameCellState, color, turnManager, alphaBetaPruning,
                        treeDepth, Heuristic.DEFAULT);
            } else {
                return new MinMaxPlayer(game, name, playerType, gameCellState, color, turnManager, alphaBetaPruning,
                        treeDepth, Heuristic.DEFAULT);
            }
        }
        if (playerType == PlayerType.RANDOM) {
            return new RandomPlayer(game, name, playerType, gameCellState, color, turnManager);
        }
        throw new UnsupportedOperationException("This playertype has not been implemented yet!");
    }
}
