package com.example.vom;

import javafx.animation.Timeline;
import javafx.scene.layout.Pane;

public class DialogFlowManager {

    private int dialogState = 0;
    private Timeline dialogTimeline = new Timeline();

    public void advanceState(String theAction) {
        dialogState++;
        runCurrentState();
    }

    public void runCurrentState() {

    }


}
