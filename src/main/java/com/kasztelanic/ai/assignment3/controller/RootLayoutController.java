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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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

    private Game gameState = null;

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
                init();
                prepareBoard();
            } else {
                gameState = null;
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
    }

    private void init() {
        boardSizeLb.textProperty().bind(gameState.getBoardSizeProperty().asString());
        treeDepthLb.textProperty().bind(gameState.getTreeDepthProperty().asString());
        alphaBetaIndicator.visibleProperty().bind(gameState.getAlphaBetaPruningProperty());
        alphaBetaIndicator.managedProperty().bind(gameState.getAlphaBetaPruningProperty());
        player1Lb.textProperty().bind(gameState.getPlayer1TypeProperty().asString());
        player1PointsLb.textProperty().bind(gameState.getPlayer1PointsProperty().asString());
        player2Lb.textProperty().bind(gameState.getPlayer2TypeProperty().asString());
        player2PointsLb.textProperty().bind(gameState.getPlayer2PointsProperty().asString());
        turnLb.textProperty().bind(gameState.getPlayer1TurnProperty().asString());
    }

    private void prepareBoard() {
        GridPane board = new Board(gameState).getSkin();
        board.autosize();
        mainView.getChildren().add(board);
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
        boardSize = game.getBoardSize();
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
        if (!game.isEnd() && state.get() == State.EMPTY) {
            state.set(game.isPlayer1Turn() ? State.CROSS : State.NOUGHT);
            // game.boardUpdated();
            game.changeTurn();
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

    // private final ImageView imageView = new ImageView();
    private final Rectangle rectangle = new Rectangle();

    public SquareSkin(final Square square) {
        getStyleClass().add("square");

        rectangle.setWidth(500);
        rectangle.setHeight(500);
        rectangle.maxHeight(500);
        rectangle.setFill(Color.BLUE);

        // imageView.setMouseTransparent(true);
        // imageView.setFitHeight(100);
        // imageView.setFitWidth(100);
        // imageView.maxWidth(20);
        // imageView.maxHeight(20);
        // imageView.setPreserveRatio(true);
        // getChildren().setAll(imageView);
        // setPrefSize(crossImage.getHeight(), crossImage.getHeight());
        // setMaxSize(30, 30);

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
