package com.kasztelanic.ai.assignment3.view.views;

import com.kasztelanic.ai.assignment3.model.Game;
import com.kasztelanic.ai.assignment3.model.enums.GameCellState;
import com.kasztelanic.ai.assignment3.properties.AppProperties;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameCell extends Pane {

    private final Rectangle rect;
    private final ObjectProperty<GameCellState> state = new SimpleObjectProperty<>();

    public GameCell(Game game, int row, int col) {
        rect = new Rectangle();
        rect.setFill(Color.WHITE);
        rect.widthProperty().bind(widthProperty());
        rect.heightProperty().bind(heightProperty());
        state.bind(game.getGameCellStateProperty(row, col));
        state.addListener((o, ov, nv) -> rect.setFill(Color
                .web(nv.equals(GameCellState.Player1) ? AppProperties.PLAYER1_COLOR : AppProperties.PLAYER2_COLOR)));
        getChildren().setAll(rect);

        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (isEmpty() && !game.isEnd()) {
                    game.setGameCellState(row, col,
                            game.isPlayer1Turn() ? GameCellState.Player1 : GameCellState.Player2);
                }
            }
        });
        setOnMouseEntered(e -> {
            if (isEmpty()) {
                getStyleClass().add("hover");
                rect.setVisible(false);
            }
        });
        setOnMouseExited(e -> {
            getStyleClass().remove("hover");
            rect.setVisible(true);
        });
    }

    private boolean isEmpty() {
        return state.get().equals(GameCellState.EMPTY);
    }
}
