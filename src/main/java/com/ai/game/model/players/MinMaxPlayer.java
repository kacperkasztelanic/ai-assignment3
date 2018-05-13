package com.ai.game.model.players;

import com.ai.game.model.Game;
import com.ai.game.model.Turn;
import com.ai.game.model.TurnManager;
import com.ai.game.model.enums.GameCellState;
import com.ai.game.model.enums.Heuristic;
import com.ai.game.model.enums.PlayerType;

public class MinMaxPlayer extends AbstractAiPlayer {

    public MinMaxPlayer(Game game, String name, PlayerType playerType, GameCellState gameCellState, String color,
            TurnManager turnManager, boolean alphaBetaPruning, int treeDepth, Heuristic heuristic) {
        super(game, name, playerType, gameCellState, color, turnManager, alphaBetaPruning, treeDepth, heuristic);
    }

    int recCounter = 0;

    @Override
    protected Turn moveInternal() {
        this.avaliableMoves = turnManager.getUnused();

        solveRecMax(0, game.getMovesDone(), treeDepth.get());

        return avaliableMoves.get(moveIndex);
    }

    private int solveRecMax(int value, int movesDone, int depth) {
        ++recCounter;
        int choosenPts = Integer.MIN_VALUE;
        for (int i = 0; i < avaliableMoves.size(); ++i) {
            Turn turn = avaliableMoves.get(i);
            if (isMoveAvaliable(turn)) {
                game.setGameBoardCellValue(turn.getRow(), turn.getColumn(), gameCellState.get().toInt());
                int currentPts = calculatePtsWithPrediction(turn.getRow(), turn.getColumn());
                int accumulatedPts;
                if (movesDone + 1 < movesToDo && depth > 1) {
                    accumulatedPts = solveRecMin(value + currentPts, movesDone + 1, depth - 1);
                } else {
                    accumulatedPts = value + currentPts;
                }
                if (accumulatedPts > choosenPts) {
                    choosenPts = accumulatedPts;
                    if (depth == getTreeDepth()) {
                        moveIndex = i;
                    }
                }
                game.setGameBoardCellValue(turn.getRow(), turn.getColumn(), GameCellState.EMPTY.toInt());
            }
        }
        printChoosenMove(depth, choosenPts);
        return choosenPts;
    }

    private int solveRecMin(int value, int movesDone, int depth) {
        ++recCounter;
        int choosenPts = Integer.MAX_VALUE;
        for (int i = 0; i < avaliableMoves.size(); ++i) {
            Turn turn = avaliableMoves.get(i);
            if (isMoveAvaliable(turn)) {
                game.setGameBoardCellValue(turn.getRow(), turn.getColumn(), gameCellState.get().toInt());
                int currentPts = -calculatePtsWithPrediction(turn.getRow(), turn.getColumn());
                int accumulatedPts;
                if (movesDone + 1 < movesToDo && depth > 1) {
                    accumulatedPts = solveRecMax(value + currentPts, movesDone + 1, depth - 1);
                } else {
                    accumulatedPts = value + currentPts;
                }
                if (accumulatedPts < choosenPts) {
                    choosenPts = accumulatedPts;
                }
                game.setGameBoardCellValue(turn.getRow(), turn.getColumn(), GameCellState.EMPTY.toInt());
            }
        }
        return choosenPts;
    }
}
