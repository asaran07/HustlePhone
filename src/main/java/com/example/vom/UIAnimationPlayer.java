package com.example.vom;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.util.Duration;
import res.R;

import java.util.List;

public class UIAnimationPlayer implements UIAnimationPlayerContract {

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

    @Override
    public void startBlinkAnimation(Node theNode, double theSpeed, int theTotalBlinks) {
        Timeline blinkTimeline = new Timeline();
        for (int i = 0; i < theTotalBlinks; i++) {
            KeyFrame kf = new KeyFrame(Duration.seconds(i*theSpeed), e -> {
                theNode.setVisible(!theNode.isVisible());
            });
            blinkTimeline.getKeyFrames().add(kf);
        }
        blinkTimeline.play();
    }

    public void startTypewriterEffect(final String theText, Timeline theTimeline, Label theLabel) {
        StringBuilder sb = new StringBuilder();
        Timeline typewriterTimeline = new Timeline();
        for (int i = 0; i < theText.length(); i++) {
            char nextLetter = theText.charAt(i);
            KeyFrame kf = new KeyFrame(Duration.seconds(i * 0.07), e -> {
                sb.append(nextLetter);
                theLabel.setText(sb.toString());
            });
            typewriterTimeline.getKeyFrames().add(kf);
        }
        typewriterTimeline.play();
    }

    @Override
    public void startMultipleFadeAnimations(List<Node> nodesArray, boolean fadeOut, double speed) {
        for (Node node : nodesArray) {
            startFadeAnimation(node, fadeOut, speed);
        }
    }


}
