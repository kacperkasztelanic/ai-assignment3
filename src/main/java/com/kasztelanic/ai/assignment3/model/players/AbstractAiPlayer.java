package com.kasztelanic.ai.assignment3.model.players;

import com.kasztelanic.ai.assignment3.dominik.PairManager;
import com.kasztelanic.ai.assignment3.model.Game;
import com.kasztelanic.ai.assignment3.model.Turn;
import com.kasztelanic.ai.assignment3.model.enums.GameCellState;
import com.kasztelanic.ai.assignment3.model.enums.PlayerType;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

public abstract class AbstractAiPlayer extends Player {

    protected final ReadOnlyBooleanWrapper alphaBetaPruning;
    protected final ReadOnlyIntegerWrapper treeDepth;

    public AbstractAiPlayer(Game game, String name, PlayerType playerType, GameCellState gameCellState, String color,
            PairManager pairManager, boolean alphaBetaPruning, int treeDepth) {
        super(game, name, playerType, gameCellState, color, pairManager);
        this.alphaBetaPruning = new ReadOnlyBooleanWrapper(alphaBetaPruning);
        this.treeDepth = new ReadOnlyIntegerWrapper(treeDepth);
    }

    public ReadOnlyBooleanProperty getAlphaBetaPruningProperty() {
        return alphaBetaPruning.getReadOnlyProperty();
    }

    public boolean isAlphaBetaPruning() {
        return alphaBetaPruning.get();
    }

    public ReadOnlyIntegerProperty getTreeDepthProperty() {
        return treeDepth.getReadOnlyProperty();
    }

    public int getTreeDepth() {
        return treeDepth.get();
    }

    @Override
    public void move(Turn turn) {
        Turn t = moveInternal();

        Platform.runLater(() -> {
            game.setGameCellState(t.getRow(), t.getColumn(),
                    game.isPlayer1Turn() ? GameCellState.Player1 : GameCellState.Player2);
            game.board[t.getRow()][t.getColumn()] = game.isPlayer1Turn() ? 1 : 2;
            updatePoints(t);
            game.moveDone();
        });
    }

    protected abstract Turn moveInternal();
}
