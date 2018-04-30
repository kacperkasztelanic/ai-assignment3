package com.kasztelanic.ai.assignment3.properties;

public interface AppProperties {

    static final String MAIN_WINDOW_TITLE = "Stratego";
    static final String NEW_GAME_DIALOG_TITLE = "New game";
    static final String ABOUT_ALERT_TITLE = "About";

    static final String ABOUT_ALERT_HEADER = "About";
    static final String ABOUT_ALERT_TEXT = "Developed as artificial intelligence assignment by:\nKacper Kasztelanic\nA.D. 2018";

    static final int DEFAULT_MAIN_WINDOW_WIDTH = 1366;
    static final int DEFAULT_MAIN_WINDOW_HEIGHT = 768;

    static final String STYLESHEET_PATH = "/style.css";
    static final String PLAYER1_COLOR = "#03A9F4";
    static final String PLAYER2_COLOR = "#76FF03";

    static final String ROOT_LAYOUT_FXML_PATH = "/views/RootLayout.fxml";
    static final String NEW_GAME_DIALOG_FXML_PATH = "/views/NewGameDialog.fxml";

    static final int MIN_TREE_DEPTH = 1;
    static final int MAX_TREE_DEPTH = 10;
    static final int DEFAULT_TREE_DEPTH = 3;
    static final int MIN_BOARD_SIZE = 1;
    static final int MAX_BOARD_SIZE = 100;
    static final int DEFAULT_BOARD_SIZE = 5;
}
