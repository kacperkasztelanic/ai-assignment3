package com.kasztelanic.ai.assignment3.model;

import com.kasztelanic.ai.assignment3.model.enums.GameCellState;
import com.kasztelanic.ai.assignment3.model.enums.PlayerType;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

public abstract class AiPlayer extends Player {

    protected final ReadOnlyBooleanWrapper alphaBetaPruning;
    protected final ReadOnlyIntegerWrapper treeDepth;

    public AiPlayer(Game game, String name, PlayerType playerType, String color, boolean alphaBetaPruning,
            int treeDepth) {
        super(game, name, playerType, color);
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
    public void move() {
        Turn turn = moveInternal();

        Platform.runLater(() -> {
            game.setGameCellState(turn.getRow(), turn.getColumn(),
                    game.isPlayer1Turn() ? GameCellState.Player1 : GameCellState.Player2);
            updatePoints();
            game.moveDone();
        });
    }

    protected abstract Turn moveInternal();
}
