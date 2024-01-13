package com.example.vom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {
    @Override
    public void start(Stage mainStage) throws IOException {
        GameStateManager gameStateManager = new GameStateManager();
        CoreGameManagerContract coreGameManagerContract = new CoreGameManager(gameStateManager);
        UIAnimationPlayerContract uiAnimationPlayerContract = new UIAnimationPlayer();

        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        GameController gameController = fxmlLoader.getController();
        gameController.setGameStateManager(gameStateManager);
        gameController.setUiAnimationPlayerContract(uiAnimationPlayerContract);
        gameController.setCoreGameManagerContract(coreGameManagerContract);

        gameStateManager.changeState(GameState.TITLE_SCREEN, GameStateCategory.UI_UPDATE);
        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);
        mainStage.setTitle("vom v0.0.1");
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}