package com.kasztelanic.ai.assignment3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/RootLayout.fxml"));

        Scene scene = new Scene(root, 1366, 768);
        scene.getStylesheets().add(getResource("/tictactoe-blueskin.css"));
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }

    private String getResource(String resourceName) {
        return getClass().getResource(resourceName).toExternalForm();
    }
}
