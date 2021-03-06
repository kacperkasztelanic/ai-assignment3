package com.ai.game.view.controllers;

import java.io.IOException;
import java.util.Optional;

import com.ai.game.model.Game;
import com.ai.game.model.GameSettings;
import com.ai.game.model.enums.PlayerType;
import com.ai.game.model.players.AbstractAiPlayer;
import com.ai.game.properties.AppProperties;
import com.ai.game.view.views.GamePane;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    private Label player1Lb;
    @FXML
    private Label player2Lb;
    @FXML
    private Label player1PointsLb;
    @FXML
    private Label player2PointsLb;
    @FXML
    private HBox player1AiHBox;
    @FXML
    private Label player1TreeDepthLb;
    @FXML
    private HBox player1AlphaBetaIndicator;
    @FXML
    private HBox player2AiHBox;
    @FXML
    private Label player2TreeDepthLb;
    @FXML
    private HBox player2AlphaBetaIndicator;
    @FXML
    private Rectangle turnRectangle;
    @FXML
    private Button nextMoveBt;
    @FXML
    private VBox progressVBox;

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
        player1TreeDepthLb.setText(null);
        player2TreeDepthLb.setText(null);
        player1AiHBox.setVisible(false);
        player1AiHBox.setManaged(false);
        player2AiHBox.setVisible(false);
        player2AiHBox.setManaged(false);
        player1Lb.setText(null);
        player2Lb.setText(null);
        player1PointsLb.setText(null);
        player2PointsLb.setText(null);
        nextMoveBt.setVisible(false);
        progressVBox.setVisible(false);
        mainView.getStyleClass().add("mainView");
    }

    private void init() {
        boardSizeLb.textProperty().bind(game.getBoardSizeProperty().asString());
        player1AiHBox.visibleProperty().bind(game.getPlayer1().getTypeProperty().isEqualTo(PlayerType.HUMAN).not());
        player1AiHBox.managedProperty().bind(player1AiHBox.visibleProperty());
        player2AiHBox.visibleProperty().bind(game.getPlayer2().getTypeProperty().isEqualTo(PlayerType.HUMAN).not());
        player2AiHBox.managedProperty().bind(player2AiHBox.visibleProperty());
        if (game.getPlayer1().getType() != PlayerType.HUMAN) {
            player1TreeDepthLb.textProperty().bind(((AbstractAiPlayer) game.getPlayer1()).getTreeDepthProperty().asString());
            player1AlphaBetaIndicator.visibleProperty()
                    .bind(((AbstractAiPlayer) game.getPlayer1()).getAlphaBetaPruningProperty());
            player1AlphaBetaIndicator.managedProperty().bind(player1AlphaBetaIndicator.visibleProperty());
        }
        if (game.getPlayer2().getType() != PlayerType.HUMAN) {
            player2TreeDepthLb.textProperty().bind(((AbstractAiPlayer) game.getPlayer2()).getTreeDepthProperty().asString());
            player2AlphaBetaIndicator.visibleProperty()
                    .bind(((AbstractAiPlayer) game.getPlayer2()).getAlphaBetaPruningProperty());
            player2AlphaBetaIndicator.managedProperty().bind(player2AlphaBetaIndicator.visibleProperty());
        }
        player1Lb.textProperty().bind(game.getPlayer1().getTypeProperty().asString());
        player1PointsLb.textProperty().bind(game.getPlayer1().getPointsProperty().asString());
        player2Lb.textProperty().bind(game.getPlayer2().getTypeProperty().asString());
        player2PointsLb.textProperty().bind(game.getPlayer2().getPointsProperty().asString());
        mainView.disableProperty().bind(game.getIsEndProperty());
        BooleanProperty humanPlayer = new SimpleBooleanProperty(game.getCurrentPlayer().getType() == PlayerType.HUMAN);
        game.getCurrentPlayerProperty().addListener((o, ov, nv) -> humanPlayer.set(nv.getType() == PlayerType.HUMAN));
        nextMoveBt.visibleProperty()
                .bind(((game.getIsEndProperty().not()).and(game.getIsWaitingProperty().not())).and(humanPlayer.not()));
        turnRectangle.setFill(Color.web(game.getPlayer1().getColor()));
        game.getIsEndProperty().addListener((o, ov, nv) -> turnRectangle.setFill(Color.WHITE));
        game.getCurrentPlayerProperty().addListener((o, ov, nv) -> {
            turnRectangle.setFill(Color.web(nv.getColor()));
        });
        game.getIsEndProperty().addListener((o, ov, nv) -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game over");
            alert.setHeaderText("The game is over!");
            if (game.getWinner() != null) {
                alert.setContentText(String.format("The winner is %s!", game.getWinner()));
            } else {
                alert.setContentText(
                        String.format("It is a draw! Both players have %d points.", game.getPlayer1().getPoints()));
            }
            alert.show();
        });
        progressVBox.visibleProperty().bind(game.getIsWaitingProperty());
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

    @FXML
    public void onNextMove() {
        game.nextMove();
    }
}
