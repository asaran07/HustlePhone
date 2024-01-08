package com.example.vom;

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
    @FXML private boolean checkForContinue;

    private final Timeline introTimeline = new Timeline();
    private final Timeline introTextTimeline = new Timeline();

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
        Character Bob = new Character("Bob", "123");

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
        replyButton1.setOnAction(actionEvent -> {
            if (checkForContinue) {
                introTextTimeline.play();
            }
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
        introTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), e -> playIntro()));
    }

    public void startTypewriterEffect(final String theText) {
        StringBuilder sb = new StringBuilder();
        Timeline tl = new Timeline();
        for (int i = 0; i < theText.length(); i++) {
            char nextLetter = theText.charAt(i);
            KeyFrame kf = new KeyFrame(Duration.seconds(i * 0.07), e -> {
                sb.append(nextLetter);
                convoLabel.setText(sb.toString());
            });
            tl.getKeyFrames().add(kf);
        }
        tl.play();
    }

    public void playIntro() {
        introTextTimeline.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(1), e -> startTypewriterEffect("welcome to phone game.")),

                new KeyFrame(Duration.seconds(4), e -> {
                    startTypewriterEffect("this is your main display panel.");
                    startBlinkAnimation(displayPane, R.speed.FAST, 6);
                }),

                new KeyFrame(Duration.seconds(7), e -> {
                    System.out.println("7");
                    startTypewriterEffect("this is where you'll reply to a conversation.");
                    startBlinkAnimation(replyPane, R.speed.FAST, 6);
                }),

                new KeyFrame(Duration.seconds(12), e -> {
                    System.out.println("pausing timeline");
                    startTypewriterEffect("lets try it.");
                    replyButton1.setText("nah, I don't trust you.");
                    replyButton2.setText("okay let's begin!");
                    startFadeAnimation(replyButton1, false, R.speed.NORMAL);
                    startFadeAnimation(replyButton2, false, R.speed.NORMAL);
                    checkForContinue = true;
                    introTextTimeline.pause();
                }),

                new KeyFrame(Duration.seconds(12.1), e -> {
                    startTypewriterEffect("smart guy huh");
                    startFadeAnimation(replyButton1, true, R.speed.SLOW);
                    startFadeAnimation(replyButton2, true, R.speed.SLOW);
                }),

                new KeyFrame(Duration.seconds(14), e -> {
                    startTypewriterEffect("not so smart now are you?");
                    replyButton1.setText("i would like to apologize.");
                    startFadeAnimation(replyButton1, false, R.speed.SLOW);
                })
                );

        introTextTimeline.play();
    }


}