package com.example.vom;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import res.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {


    private Character activeCharacter;
    @FXML private Pane replyPane;
    @FXML private Pane displayPane;
    @FXML private Pane convoPane;
    @FXML private Pane titleScreenPane;
    @FXML private Button startButton;
    @FXML private Label convoLabel;
    @FXML private Button replyButton1;
    @FXML private Button replyButton2;

    private ArrayList<Node> titleScreenGroup;
    private GameStateManager gameStateManager;
    private UIManager uiManager;

    public GameController() {
        titleScreenGroup.addAll(Arrays.asList(startButton,titleScreenPane));
    }

    public void setGameStateManager(GameStateManager theGameStateManager) {
        this.gameStateManager = theGameStateManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupButtonActions();
    }

    public void updateUI() {
        switch (gameStateManager.getCurrentGameState()) {
            case TITLE_SCREEN -> prepareTitleScreen();
        }
    }

    public void prepareInGameScreen() {

    }

    public void prepareTitleScreen() {
        makeInvisible(replyButton1);
        makeInvisible(replyButton2);
        makeInvisible(displayPane);
        makeInvisible(convoPane);
        makeInvisible(replyPane);
        titleScreenPane.setVisible(true);
    }

    private void createCharacters() {
        Character Mike = new Character("Mike", "123");
        Mike.getDialogue().addResponse("Hello?", "Heyy Kid!");
    }

    public Character getActiveCharacter() {
        return this.activeCharacter;
    }

    public void setActiveCharacter(Character character) {
        this.activeCharacter = character;
    }

    public void toggleNodeVisibility(final Node node) {
        node.setVisible(!node.isVisible());
    }

    public void startBlinkAnimation(final Node theNode, final double theSpeed, final int theTotalBlinks) {
        Timeline blinkTimeline = new Timeline();
        for (int i = 0; i < theTotalBlinks; i++) {
            KeyFrame kf = new KeyFrame(Duration.seconds(i*theSpeed), e -> {
                theNode.setVisible(!theNode.isVisible());
            });
            blinkTimeline.getKeyFrames().add(kf);
        }
        blinkTimeline.play();
    }

    public void startMultipleFadeAnimations(List<Node> nodesArray, final boolean fadeOut, final double speed) {
        for (Node node : nodesArray) {
            uiManager.startFadeAnimation(node, fadeOut, speed);
        }
    }

    public void setupButtonActions() {
        startButton.setOnAction(actionEvent -> {
            uiManager.startFadeAnimation(titleScreenPane, true, R.speed.NORMAL);
        });
    }

    private void makeInvisible(final Node theNode) {
        theNode.setVisible(true);
        theNode.setOpacity(R.visibility.INVISIBLE);
    }
}