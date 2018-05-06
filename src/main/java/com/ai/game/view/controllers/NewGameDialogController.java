package com.ai.game.view.controllers;

import com.ai.game.model.GameSettings;
import com.ai.game.model.enums.PlayerType;
import com.ai.game.properties.AppProperties;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;

public class NewGameDialogController {

    @FXML
    private ComboBox<PlayerType> player1ComboBox;
    @FXML
    private ComboBox<PlayerType> player2ComboBox;
    @FXML
    private Spinner<Integer> boardSizeSpinner;
    @FXML
    private GridPane player1AiPane;
    @FXML
    private Spinner<Integer> player1TreeDepthSpinner;
    @FXML
    private CheckBox player1AlphaBetaCheckBox;
    @FXML
    private GridPane player2AiPane;
    @FXML
    private Spinner<Integer> player2TreeDepthSpinner;
    @FXML
    private CheckBox player2AlphaBetaCheckBox;

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> boardSizeSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                AppProperties.MIN_BOARD_SIZE, AppProperties.MAX_BOARD_SIZE, AppProperties.DEFAULT_BOARD_SIZE);
        TextFormatter<Integer> boardSizeSpinnerFormatter = new TextFormatter<>(
                boardSizeSpinnerValueFactory.getConverter(), boardSizeSpinnerValueFactory.getValue());
        boardSizeSpinner.getEditor().setTextFormatter(boardSizeSpinnerFormatter);
        boardSizeSpinnerValueFactory.valueProperty().bindBidirectional(boardSizeSpinnerFormatter.valueProperty());
        boardSizeSpinner.setValueFactory(boardSizeSpinnerValueFactory);

        SpinnerValueFactory<Integer> player1TreeDepthSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                AppProperties.MIN_TREE_DEPTH, AppProperties.MAX_TREE_DEPTH, AppProperties.DEFAULT_TREE_DEPTH);
        TextFormatter<Integer> player1TreeDepthSpinnerFormatter = new TextFormatter<>(
                player1TreeDepthSpinnerValueFactory.getConverter(), player1TreeDepthSpinnerValueFactory.getValue());
        player1TreeDepthSpinner.getEditor().setTextFormatter(player1TreeDepthSpinnerFormatter);
        player1TreeDepthSpinnerValueFactory.valueProperty()
                .bindBidirectional(player1TreeDepthSpinnerFormatter.valueProperty());
        player1TreeDepthSpinner.setValueFactory(player1TreeDepthSpinnerValueFactory);

        SpinnerValueFactory<Integer> player2TreeDepthSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                AppProperties.MIN_TREE_DEPTH, AppProperties.MAX_TREE_DEPTH, AppProperties.DEFAULT_TREE_DEPTH);
        TextFormatter<Integer> player2TreeDepthSpinnerFormatter = new TextFormatter<>(
                player2TreeDepthSpinnerValueFactory.getConverter(), player2TreeDepthSpinnerValueFactory.getValue());
        player2TreeDepthSpinner.getEditor().setTextFormatter(player2TreeDepthSpinnerFormatter);
        player2TreeDepthSpinnerValueFactory.valueProperty()
                .bindBidirectional(player2TreeDepthSpinnerFormatter.valueProperty());
        player2TreeDepthSpinner.setValueFactory(player2TreeDepthSpinnerValueFactory);

        player1ComboBox.getItems().setAll(PlayerType.values());
        player2ComboBox.getItems().setAll(PlayerType.values());
        player1ComboBox.getSelectionModel().select(0);
        player2ComboBox.getSelectionModel().select(0);

        player1AiPane.disableProperty()
                .bind(player1ComboBox.getSelectionModel().selectedItemProperty().isEqualTo(PlayerType.Human));
        player2AiPane.disableProperty()
                .bind(player2ComboBox.getSelectionModel().selectedItemProperty().isEqualTo(PlayerType.Human));
    }

    public GameSettings getGameSettings() {
        GameSettings settings = new GameSettings();
        settings.setBoardSize(boardSizeSpinner.getValue());
        settings.setPlayer1TreeDepth(player1TreeDepthSpinner.getValue());
        settings.setPlayer2TreeDepth(player2TreeDepthSpinner.getValue());
        settings.setPlayer1Type(player1ComboBox.getValue());
        settings.setPlayer2Type(player2ComboBox.getValue());
        settings.setPlayer1AlphaBetaPruning(player1AlphaBetaCheckBox.isSelected());
        settings.setPlayer2AlphaBetaPruning(player2AlphaBetaCheckBox.isSelected());
        return settings;
    }
}
