package com.ssu.artemiy_dobrynin.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/screen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        scene.getStylesheets().add("/style.css");

        primaryStage.setTitle("Snake by StrangerWS");
        primaryStage.setScene(scene);
        primaryStage.resizableProperty().set(false);
        primaryStage.show();
    }
}
