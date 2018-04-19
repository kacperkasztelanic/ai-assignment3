package com.kasztelanic.ai.assignment3.controller;

import com.kasztelanic.ai.assignment3.GameSettings;
import com.kasztelanic.ai.assignment3.enums.PlayerType;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class NewGameDialogController {

    private static final int MIN_TREE_DEPTH = 1;
    private static final int MAX_TREE_DEPTH = 10;
    private static final int DEFAULT_TREE_DEPTH = 3;
    private static final int MIN_BOARD_SIZE = 1;
    private static final int MAX_BOARD_SIZE = 10;
    private static final int DEFAULT_BOARD_SIZE = 5;

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
        treeDepthSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_TREE_DEPTH, MAX_TREE_DEPTH, DEFAULT_TREE_DEPTH));
        boardSizeSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_BOARD_SIZE, MAX_BOARD_SIZE, DEFAULT_BOARD_SIZE));
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
