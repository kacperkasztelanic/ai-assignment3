package com.kasztelanic.ai.assignment3.view.controllers;

import com.kasztelanic.ai.assignment3.model.GameSettings;
import com.kasztelanic.ai.assignment3.model.enums.PlayerType;
import com.kasztelanic.ai.assignment3.properties.AppProperties;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;

public class NewGameDialogController {

    @FXML
    private ComboBox<PlayerType> player1ComboBox;
    @FXML
    private ComboBox<PlayerType> player2ComboBox;
    @FXML
    private Spinner<Integer> treeDepthSpinner;
    @FXML
    private Spinner<Integer> boardSizeSpinner;
    @FXML
    private CheckBox alphaBetaCheckBox;

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> treeDepthSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                AppProperties.MIN_TREE_DEPTH, AppProperties.MAX_TREE_DEPTH, AppProperties.DEFAULT_TREE_DEPTH);
        TextFormatter<Integer> treeDepthSpinnerFormatter = new TextFormatter<>(
                treeDepthSpinnerValueFactory.getConverter(), treeDepthSpinnerValueFactory.getValue());
        treeDepthSpinner.getEditor().setTextFormatter(treeDepthSpinnerFormatter);
        treeDepthSpinnerValueFactory.valueProperty().bindBidirectional(treeDepthSpinnerFormatter.valueProperty());
        treeDepthSpinner.setValueFactory(treeDepthSpinnerValueFactory);

        SpinnerValueFactory<Integer> boardSizeSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                AppProperties.MIN_BOARD_SIZE, AppProperties.MAX_BOARD_SIZE, AppProperties.DEFAULT_BOARD_SIZE);
        TextFormatter<Integer> boardSizeSpinnerFormatter = new TextFormatter<>(
                boardSizeSpinnerValueFactory.getConverter(), boardSizeSpinnerValueFactory.getValue());
        boardSizeSpinner.getEditor().setTextFormatter(boardSizeSpinnerFormatter);
        boardSizeSpinnerValueFactory.valueProperty().bindBidirectional(boardSizeSpinnerFormatter.valueProperty());
        boardSizeSpinner.setValueFactory(boardSizeSpinnerValueFactory);

        player1ComboBox.getItems().setAll(PlayerType.values());
        player2ComboBox.getItems().setAll(PlayerType.values());
        player1ComboBox.getSelectionModel().select(0);
        player2ComboBox.getSelectionModel().select(0);
    }

    public GameSettings getGameSettings() {
        GameSettings settings = new GameSettings();
        settings.setBoardSize(boardSizeSpinner.getValue());
        settings.setTreeDepth(treeDepthSpinner.getValue());
        settings.setPlayer1(player1ComboBox.getValue());
        settings.setPlayer2(player2ComboBox.getValue());
        settings.setAlphaBetaPruning(alphaBetaCheckBox.isSelected());
        return settings;
    }
}
