package com.kasztelanic.ai.assignment3.view.views;

import com.kasztelanic.ai.assignment3.model.Game;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class GamePane extends HBox {

    public GamePane(Game game) {
        final VBox vBox = new VBox();
        vBox.alignmentProperty().set(Pos.CENTER);
        alignmentProperty().set(Pos.CENTER);

        final GridPane gridPane = new GridPane();
        int gap = suggestGap(game.getBoardSize());
        gridPane.setVgap(gap);
        gridPane.setHgap(gap);
        final NumberBinding binding = Bindings.min(widthProperty(), heightProperty());
        gridPane.setMinSize(200, 200);

        vBox.prefWidthProperty().bind(binding);
        vBox.prefHeightProperty().bind(binding);
        vBox.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
        vBox.setFillWidth(true);
        VBox.setVgrow(gridPane, Priority.ALWAYS);
        for (int i = 0; i < game.getBoardSize(); i++) {
            final ColumnConstraints columnConstraints = new ColumnConstraints(Control.USE_PREF_SIZE,
                    Control.USE_COMPUTED_SIZE, Double.MAX_VALUE);
            columnConstraints.setHgrow(Priority.SOMETIMES);
            gridPane.getColumnConstraints().add(columnConstraints);

            final RowConstraints rowConstraints = new RowConstraints(Control.USE_PREF_SIZE, Control.USE_COMPUTED_SIZE,
                    Double.MAX_VALUE);
            rowConstraints.setVgrow(Priority.SOMETIMES);
            gridPane.getRowConstraints().add(rowConstraints);
        }
        vBox.getChildren().add(gridPane);
        getChildren().add(vBox);
        HBox.setHgrow(this, Priority.ALWAYS);
        for (int i = 0; i < game.getBoardSize(); i++) {
            for (int j = 0; j < game.getBoardSize(); j++) {
                final Pane child = new GameCell(game, i, j);
                GridPane.setRowIndex(child, i);
                GridPane.setColumnIndex(child, j);
                gridPane.getChildren().add(child);
            }
        }
    }

    private int suggestGap(int boardSize) {
        if (boardSize >= 30) {
            return 1;
        }
        if (boardSize > 10) {
            return 2;
        }
        return 3;
    }
}