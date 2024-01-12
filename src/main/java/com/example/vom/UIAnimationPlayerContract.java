package com.example.vom;

import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.util.List;

public interface UIAnimationPlayerContract {
    void startFadeAnimation(final Node theNode, final boolean fadeOut, final double theSpeed);
    void startBlinkAnimation(final Node theNode, final double theSpeed, final int theTotalBlinks);
    void startTypewriterEffect(final String theText, Timeline theTimeline, Label theLabel);
    void startMultipleFadeAnimations(List<Node> nodesArray, final boolean fadeOut, final double speed);
}
