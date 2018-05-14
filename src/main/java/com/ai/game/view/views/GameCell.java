package com.ai.game.view.views;

import com.ai.game.model.Game;
import com.ai.game.model.enums.GameCellState;
import com.ai.game.model.enums.PlayerType;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameCell extends Pane {

    private final Rectangle rect;
    private final ObjectProperty<GameCellState> state = new SimpleObjectProperty<>();
    private final Game game;

    public GameCell(Game game, int row, int col) {
        this.game = game;
        rect = new Rectangle();
        rect.setFill(Color.WHITE);
        rect.widthProperty().bind(widthProperty());
        rect.heightProperty().bind(heightProperty());
        state.bind(game.getGameCellStateProperty(row, col));
        state.addListener((o, ov, nv) -> rect.setFill(
                Color.web(nv == GameCellState.PLAYER1 ? game.getPlayer1().getColor() : game.getPlayer2().getColor())));
        getChildren().setAll(rect);

        setOnMousePressed(e -> {
            if (isClickable()) {
                game.setGameCellState(row, col, game.isPlayer1Turn() ? GameCellState.PLAYER1 : GameCellState.PLAYER2);
            }
        });
        setOnMouseEntered(e -> {
            if (isClickable()) {
                getStyleClass().add("hover");
                rect.setVisible(false);
            }
        });
        setOnMouseExited(e -> {
            getStyleClass().remove("hover");
            rect.setVisible(true);
        });
    }

    private boolean isClickable() {
        return isEmpty() && isCurrentPlayerHuman() && !game.isEnd();
    }

    private boolean isEmpty() {
        return state.get() == GameCellState.EMPTY;
    }

    private boolean isCurrentPlayerHuman() {
        return game.getCurrentPlayer().getType() == PlayerType.HUMAN;
    }
}
