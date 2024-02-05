package com.example.vom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {
    private static final String VERSION = "0.3.1";
    @Override
    public void start(Stage mainStage) throws IOException {
        GameStateManager gameStateManager = new GameStateManager();
        CharacterDatabase characterDatabase = new CharacterDatabase();
        CoreGameManagerContract coreGameManagerContract = new CoreGameManager(gameStateManager, characterDatabase);
        UIAnimationPlayerContract uiAnimationPlayerContract = new UIAnimationPlayer();


        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        GameController gameController = fxmlLoader.getController();
        gameController.setGameStateManager(gameStateManager);
        gameController.setUiAnimationPlayerContract(uiAnimationPlayerContract);
        gameController.setCoreGameManagerContract(coreGameManagerContract);

        gameStateManager.changeState(GameState.NOT_IN_GAME, UIState.ON_TITLE_SCREEN);
        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);
        mainStage.setTitle(VERSION);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}