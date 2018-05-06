package com.ai.game.model.players;

import java.util.Random;

import com.ai.game.terminal.PairManager;
import com.ai.game.model.Game;
import com.ai.game.model.Turn;
import com.ai.game.model.enums.GameCellState;
import com.ai.game.model.enums.PlayerType;

public class RandomPlayer extends AbstractAiPlayer {

    private Random random = new Random();

    public RandomPlayer(Game game, String name, PlayerType playerType, GameCellState gameCellState, String color,
            PairManager pairManager, boolean alphaBetaPruning, int treeDepth) {
        super(game, name, playerType, gameCellState, color, pairManager, alphaBetaPruning, treeDepth);
    }

    @Override
    protected Turn moveInternal() {
        int randomRow = random.nextInt(game.getBoardSize());
        int randomCol = random.nextInt(game.getBoardSize());
        System.out.println("Trying: " + randomRow + ", " + randomCol);
        if (!game.getGameCellState(randomRow, randomCol).equals(GameCellState.EMPTY)) {
            return moveInternal();
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Turn.of(randomRow, randomCol);
        }
    }
}
