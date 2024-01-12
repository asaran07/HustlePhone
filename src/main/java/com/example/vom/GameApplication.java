package com.example.vom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {
    private GameStateManager gameStateManager;
    @Override
    public void start(Stage mainStage) throws IOException {
        gameStateManager = new GameStateManager();
        UIAnimationPlayer uiAnimationPlayer = new UIAnimationPlayer();
        UIManager uiManager = new UIManager(uiAnimationPlayer);


        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();

        GameController gameController = fxmlLoader.load();
        gameController.setGameStateManager(gameStateManager);
        gameController.setUIManager(uiManager);

        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);
        mainStage.setTitle("vom v0.0.1");
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}