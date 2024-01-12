package com.example.vom;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import res.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class GameController implements Initializable, StateChangeListener {
    private GameStateManager gameStateManager;
    private UIAnimationPlayerContract uiAnimationPlayerContract;
    private CoreGameManagerContract coreGameManagerContract;

    @FXML private Pane replyPane;
    @FXML private Pane displayPane;
    @FXML private Pane convoPane;
    @FXML private Pane titleScreenPane;
    @FXML private Button startButton;
    @FXML private Label convoLabel;
    @FXML private Button replyButton1;
    @FXML private Button replyButton2;

    private ArrayList<Node> titleScreenUIGroup;
    private ArrayList<Node> inGameUIGroup;

    public GameController() {
        titleScreenUIGroup = new ArrayList<>();
        inGameUIGroup = new ArrayList<>();
    }
    public void setCoreGameManagerContract(CoreGameManagerContract theCoreGameManagerContract) {
        this.coreGameManagerContract = theCoreGameManagerContract;
    }
    public void setUiAnimationPlayerContract(UIAnimationPlayerContract theUIAnimationPlayerContract) {
        this.uiAnimationPlayerContract = theUIAnimationPlayerContract;
    }
    public void setGameStateManager(GameStateManager theGameStateManager) {
        this.gameStateManager = theGameStateManager;
        gameStateManager.addStateChangeListener(GameStateCategory.UI_UPDATE, this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleScreenUIGroup.addAll(Arrays.asList(startButton,titleScreenPane));
        inGameUIGroup.addAll(Arrays.asList(replyButton1,replyButton2,replyPane,displayPane,convoPane,convoLabel));
        setupButtonActions();
    }

    public void prepareInGameScreen() {
        inGameUIGroup.forEach(this::makeVisible);
        titleScreenPane.setVisible(false);
    }

    public void prepareTitleScreen() {
        titleScreenPane.setVisible(true);
        inGameUIGroup.forEach(this::makeInvisible);
    }

    public void makeVisible(final Node theNode) {
        theNode.setVisible(true);
    }

    private void makeInvisible(final Node theNode) {
        theNode.setVisible(true);
        theNode.setOpacity(R.visibility.INVISIBLE);
    }

    public void setupButtonActions() {
        startButton.setOnAction(actionEvent -> {
            gameStateManager.setCurrentGameState(GameState.IN_GAME);
            uiAnimationPlayerContract.startFadeAnimation(titleScreenPane, true, R.speed.NORMAL);
        });
    }

    @Override
    public void onStateChange(GameState theNewState) {
        switch (theNewState) {
            case TITLE_SCREEN -> prepareTitleScreen();
            case IN_GAME -> prepareInGameScreen();
        }
    }

}