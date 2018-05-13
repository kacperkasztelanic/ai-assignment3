package com.ai.game.model.players;

import com.ai.game.model.Game;
import com.ai.game.model.Turn;
import com.ai.game.model.TurnManager;
import com.ai.game.model.enums.GameCellState;
import com.ai.game.model.enums.PlayerType;

public class HumanPlayer extends AbstractPlayer {

    public HumanPlayer(Game game, String name, PlayerType playerType, GameCellState gameCellState, String color,
            TurnManager turnManager) {
        super(game, name, playerType, gameCellState, color, turnManager);
    }

    @Override
    public void move(Turn turn) {
        game.setGameBoardCellValue(turn.getRow(), turn.getColumn(), gameCellState.get().toInt());
        turnManager.removePair(turn);
        updatePoints(turn);
    }
}
