package com.example.vom;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
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

    private List<Character> characters;
    private Character activeCharacter;
    @FXML private Pane replyPane;
    @FXML private Pane displayPane;
    @FXML private Pane convoPane;
    @FXML private Pane titleScreenPane;
    @FXML private Button startButton;
    @FXML private Label convoLabel;
    @FXML private Button replyButton1;
    @FXML private Button replyButton2;

    private DialogFlowManager dialogFlowManager = new DialogFlowManager();
    private final Timeline introTimeline = new Timeline();

    public GameController() {
        this.characters = new ArrayList<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preparePanes();
        prepareButtons();
        initActions();
        initKeyframes();
        createCharacters();
    }

    private void createCharacters() {
        Character Mike = new Character("Mike", "123");
        Mike.getDialogue().addResponse("Hello?", "Heyy Kid!");
        this.characters.add(Mike);
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

    public void startFadeAnimation(final Node theNode, final boolean fadeOut, final double theSpeed) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(theSpeed), theNode);
        if (fadeOut) {
            fadeTransition.setFromValue(R.visibility.VISIBLE);
            fadeTransition.setToValue(R.visibility.INVISIBLE);
        } else {
            fadeTransition.setFromValue(R.visibility.INVISIBLE);
            fadeTransition.setToValue(R.visibility.VISIBLE);
        }
        fadeTransition.play();
    }

    public void startMultipleFadeAnimations(List<Node> nodesArray, final boolean fadeOut, final double speed) {
        for (Node node : nodesArray) {
            startFadeAnimation(node, fadeOut, speed);
        }
    }

    public void initActions() {
        startButton.setOnAction(actionEvent -> {
            startFadeAnimation(titleScreenPane, true, R.speed.NORMAL);
            introTimeline.play();
        });
    }

    private void makeInvisible(final Node theNode) {
        theNode.setVisible(true);
        theNode.setOpacity(R.visibility.INVISIBLE);
    }

    private void prepareButtons() {
        makeInvisible(replyButton1);
        makeInvisible(replyButton2);
    }

    private void preparePanes() {
        makeInvisible(displayPane);
        makeInvisible(convoPane);
        makeInvisible(replyPane);
        titleScreenPane.setVisible(true);
    }

    public void initKeyframes() {
        introTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> startMultipleFadeAnimations(Arrays
                .asList(displayPane, replyPane, convoPane), false, R.speed.NORMAL)));
    }

    public void startTypewriterEffect(final String theText, Timeline theTimeline) {
        StringBuilder sb = new StringBuilder();
        Timeline typewriterTimeline = new Timeline();
        for (int i = 0; i < theText.length(); i++) {
            char nextLetter = theText.charAt(i);
            KeyFrame kf = new KeyFrame(Duration.seconds(i * 0.07), e -> {
                sb.append(nextLetter);
                convoLabel.setText(sb.toString());
            });
            typewriterTimeline.getKeyFrames().add(kf);
        }
        typewriterTimeline.play();
    }

}