package com.ai.game.model.players;

import com.ai.game.model.Game;
import com.ai.game.model.TurnManager;
import com.ai.game.model.enums.GameCellState;
import com.ai.game.model.enums.Heuristic;
import com.ai.game.model.enums.PlayerType;
import com.ai.game.model.enums.SameValuesBehavior;
import com.ai.game.model.enums.Strategy;

public class PlayerFactory {

    public static AbstractPlayer getPlayer(Game game, String name, PlayerType playerType, GameCellState gameCellState,
            String color, TurnManager turnManager, boolean alphaBetaPruning, int treeDepth) {
        switch (playerType) {
        case HUMAN:
            return new HumanPlayer(game, name, playerType, gameCellState, color, turnManager);
        case MAX_POINTS_GAP_AND_CENTER:
            if (alphaBetaPruning) {
                return new AlphaBetaPlayer(game, name, playerType, gameCellState, color, turnManager, alphaBetaPruning,
                        treeDepth, Heuristic.PREFER_BOARD_CENTER, Strategy.MAXIMIZE_POINTS_GAP,
                        SameValuesBehavior.DEFAULT);
            } else {
                return new MinMaxPlayer(game, name, playerType, gameCellState, color, turnManager, alphaBetaPruning,
                        treeDepth, Heuristic.PREFER_BOARD_CENTER, Strategy.MAXIMIZE_POINTS_GAP,
                        SameValuesBehavior.DEFAULT);
            }
        case MAX_POINTS_GAP_AND_EDGES:
            if (alphaBetaPruning) {
                return new AlphaBetaPlayer(game, name, playerType, gameCellState, color, turnManager, alphaBetaPruning,
                        treeDepth, Heuristic.PREFER_BOARD_EDGES, Strategy.MAXIMIZE_POINTS_GAP,
                        SameValuesBehavior.DEFAULT);
            } else {
                return new MinMaxPlayer(game, name, playerType, gameCellState, color, turnManager, alphaBetaPruning,
                        treeDepth, Heuristic.PREFER_BOARD_EDGES, Strategy.MAXIMIZE_POINTS_GAP,
                        SameValuesBehavior.DEFAULT);
            }
        case NOT_LAST_BUT_ONE_IN_LINE_AND_RSEQ:
            if (alphaBetaPruning) {
                return new AlphaBetaPlayer(game, name, playerType, gameCellState, color, turnManager, alphaBetaPruning,
                        treeDepth, Heuristic.DEFAULT, Strategy.NOT_LAST_BUT_ONE_IN_LINE, SameValuesBehavior.RANDOM);
            } else {
                return new MinMaxPlayer(game, name, playerType, gameCellState, color, turnManager, alphaBetaPruning,
                        treeDepth, Heuristic.DEFAULT, Strategy.NOT_LAST_BUT_ONE_IN_LINE, SameValuesBehavior.RANDOM);
            }
        default:
            throw new UnsupportedOperationException("This playertype has not been implemented yet!");
        }
    }
}
