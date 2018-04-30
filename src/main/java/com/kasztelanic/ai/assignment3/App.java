package com.kasztelanic.ai.assignment3;

import com.kasztelanic.ai.assignment3.properties.AppProperties;

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
        Parent root = FXMLLoader.load(getClass().getResource(AppProperties.ROOT_LAYOUT_FXML_PATH));

        Scene scene = new Scene(root, 1366, 768);
        scene.getStylesheets().add(getResource(AppProperties.STYLESHEET_PATH));

        stage.setTitle(AppProperties.MAIN_WINDOW_TITLE);
        stage.setScene(scene);
        stage.show();
    }

    private String getResource(String resourceName) {
        return getClass().getResource(resourceName).toExternalForm();
    }
}
