package com.kasztelanic.ai.assignment3.controller;

import java.io.IOException;
import java.util.Optional;

import com.kasztelanic.ai.assignment3.Game;
import com.kasztelanic.ai.assignment3.GameSettings;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

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

    private Game gameState;

    @FXML
    public void initialize() {
        updateStatusBar();
    }

    @FXML
    public void onNewGame() {
        try {
            FXMLLoader dialogLoader = new FXMLLoader(getClass().getResource("/views/NewGameDialog.fxml"));
            DialogPane dialogPane = dialogLoader.load();
            NewGameDialogController controller = dialogLoader.getController();
            Dialog<GameSettings> dialog = new Dialog<>();
            dialog.setTitle("New game");
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
                gameState = Game.newGame(gameSettings);
                prepareBoard();
            } else {
                gameState = null;
            }
            updateStatusBar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareBoard() {
        GridPane board = new Board(gameState).getSkin();
        board.autosize();
        mainView.getChildren().add(board);
    }

    private void updateStatusBar() {
        if (gameState != null) {
            // boardSizeLb.textProperty().bind(observable);
            boardSizeLb.setText(String.valueOf(gameState.getGameSettings().getBoardSize()));
            treeDepthLb.setText(String.valueOf(gameState.getGameSettings().getTreeDepth()));
            alphaBetaIndicator.setManaged(gameState.getGameSettings().isAlphaBetaPruning());
            player1Lb.setText(gameState.getGameSettings().getPlayer1().toString());
            player2Lb.setText(gameState.getGameSettings().getPlayer2().toString());
            player1PointsLb.setText(String.valueOf(gameState.getPlayer1Points()));
            player2PointsLb.setText(String.valueOf(gameState.getPlayer2Points()));
            turnLb.setText(gameState.isPlayer1Turn() ? "Player1" : "Player2");
        } else {
            clearStatusBar();
        }
    }

    private void clearStatusBar() {
        boardSizeLb.setText(null);
        treeDepthLb.setText(null);
        alphaBetaIndicator.setManaged(false);
        player1Lb.setText(null);
        player2Lb.setText(null);
        player1PointsLb.setText(null);
        player2PointsLb.setText(null);
        turnLb.setText(null);
    }

    @FXML
    public void onQuit() {
        Platform.exit();
    }

    @FXML
    public void onAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("About");
        alert.setContentText("Developed as artificial intelligence assignment by:\nKacper Kasztelanic\nA.D. 2018");
        alert.showAndWait();
    }
}

class Board {

    private final BoardSkin skin;
    private final Square[][] squares;
    private final int boardSize;

    public Board(Game game) {
        boardSize = game.getGameSettings().getBoardSize();
        squares = new Square[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                squares[i][j] = new Square(game);
            }
        }
        skin = new BoardSkin(this);
    }

    public Square getSquare(int i, int j) {
        return squares[i][j];
    }

    public GridPane getSkin() {
        return skin;
    }

    public int getBoardSize() {
        return boardSize;
    }

}

class BoardSkin extends GridPane {
    public BoardSkin(Board board) {
        getStyleClass().add("board");
        int boardSize = board.getBoardSize();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                add(board.getSquare(i, j).getSkin(), i, j);
            }
        }
    }
}

class Square {
    enum State {
        EMPTY, NOUGHT, CROSS
    }

    private final SquareSkin skin;

    private ReadOnlyObjectWrapper<State> state = new ReadOnlyObjectWrapper<>(State.EMPTY);

    public ReadOnlyObjectProperty<State> stateProperty() {
        return state.getReadOnlyProperty();
    }

    public State getState() {
        return state.get();
    }

    private final Game game;

    public Square(Game game) {
        this.game = game;
        skin = new SquareSkin(this);
    }

    public void pressed() {
        if (!game.isGameOver() && state.get() == State.EMPTY) {
            state.set(game.isPlayer1Turn() ? State.CROSS : State.NOUGHT);
            game.boardUpdated();
            game.nextTurn();
        }
    }

    public StackPane getSkin() {
        return skin;
    }
}

class SquareSkin extends StackPane {
    static final Image noughtImage = new Image(
            "http://icons.iconarchive.com/icons/double-j-design/origami-colored-pencil/128/green-cd-icon.png");
    static final Image crossImage = new Image(
            "http://icons.iconarchive.com/icons/double-j-design/origami-colored-pencil/128/blue-cross-icon.png");

    private final ImageView imageView = new ImageView();

    public SquareSkin(final Square square) {
        getStyleClass().add("square");

        // imageView.setMouseTransparent(true);
        // imageView.setFitHeight(100);
        // imageView.setFitWidth(100);
        // imageView.maxWidth(20);
        // imageView.maxHeight(20);
        // imageView.setPreserveRatio(true);
        // getChildren().setAll(imageView);
        // setPrefSize(crossImage.getHeight(), crossImage.getHeight());
        setMaxSize(30, 30);

        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                square.pressed();
            }
        });

        square.stateProperty().addListener(new ChangeListener<Square.State>() {
            @Override
            public void changed(ObservableValue<? extends Square.State> observableValue, Square.State oldState,
                    Square.State state) {
                switch (state) {
                case EMPTY:
                    // imageView.setImage(null);

                    break;
                case NOUGHT:
                    // imageView.setImage(noughtImage);
                    break;
                case CROSS:
                    // imageView.setImage(crossImage);
                    break;
                }
            }
        });
    }
}
