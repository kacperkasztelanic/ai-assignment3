package com.kasztelanic.ai.assignment3.view.controllers;

import java.io.IOException;
import java.util.Optional;

import com.kasztelanic.ai.assignment3.model.Game;
import com.kasztelanic.ai.assignment3.model.GameSettings;
import com.kasztelanic.ai.assignment3.properties.AppProperties;
import com.kasztelanic.ai.assignment3.view.views.GamePane;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class RootLayoutController {

    @FXML
    private MenuItem newGameMenuItem;
    @FXML
    private MenuItem quitMenuItem;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private AnchorPane mainView;
    @FXML
    private Label boardSizeLb;
    @FXML
    private Label treeDepthLb;
    @FXML
    private HBox alphaBetaIndicator;
    @FXML
    private Label player1Lb;
    @FXML
    private Label player2Lb;
    @FXML
    private Label player1PointsLb;
    @FXML
    private Label player2PointsLb;
    @FXML
    private Label turnLb;

    private Game game = null;

    @FXML
    public void onNewGame() {
        try {
            FXMLLoader dialogLoader = new FXMLLoader(getClass().getResource(AppProperties.NEW_GAME_DIALOG_FXML_PATH));
            DialogPane dialogPane = dialogLoader.load();
            NewGameDialogController controller = dialogLoader.getController();
            Dialog<GameSettings> dialog = new Dialog<>();
            dialog.setTitle(AppProperties.NEW_GAME_DIALOG_TITLE);
            dialog.setDialogPane(dialogPane);
            dialog.setResultConverter(dialogBtn -> {
                if (dialogBtn == ButtonType.OK) {
                    return controller.getGameSettings();
                }
                return null;
            });
            Optional<GameSettings> settings = dialog.showAndWait();
            GameSettings gameSettings = settings.orElse(null);
            if (gameSettings != null) {
                game = Game.newGame(gameSettings);
                init();
                prepareBoard();
            } else {
                game = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        boardSizeLb.setText(null);
        treeDepthLb.setText(null);
        alphaBetaIndicator.setVisible(false);
        alphaBetaIndicator.setManaged(false);
        player1Lb.setText(null);
        player2Lb.setText(null);
        player1PointsLb.setText(null);
        player2PointsLb.setText(null);
        turnLb.setText(null);
        mainView.getStyleClass().add("mainView");
    }

    private void init() {
        boardSizeLb.textProperty().bind(game.getBoardSizeProperty().asString());
        treeDepthLb.textProperty().bind(game.getTreeDepthProperty().asString());
        alphaBetaIndicator.visibleProperty().bind(game.getAlphaBetaPruningProperty());
        alphaBetaIndicator.managedProperty().bind(game.getAlphaBetaPruningProperty());
        player1Lb.textProperty().bind(game.getPlayer1TypeProperty().asString());
        player1PointsLb.textProperty().bind(game.getPlayer1PointsProperty().asString());
        player2Lb.textProperty().bind(game.getPlayer2TypeProperty().asString());
        player2PointsLb.textProperty().bind(game.getPlayer2PointsProperty().asString());
        turnLb.textProperty().bind(game.getPlayer1TurnProperty().asString());
        mainView.disableProperty().bind(game.getIsEndProperty());
        game.getIsEndProperty().addListener((o, ov, nv) -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game over");
            alert.setHeaderText("Game is over");
            alert.setContentText("Player x is the winner!");
            alert.show();
        });
    }

    private void prepareBoard() {
        GamePane pane = new GamePane(game);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        mainView.getChildren().setAll(pane);
    }

    @FXML
    public void onQuit() {
        Platform.exit();
    }

    @FXML
    public void onAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(AppProperties.ABOUT_ALERT_TITLE);
        alert.setHeaderText(AppProperties.ABOUT_ALERT_HEADER);
        alert.setContentText(AppProperties.ABOUT_ALERT_TEXT);
        alert.showAndWait();
    }
}
