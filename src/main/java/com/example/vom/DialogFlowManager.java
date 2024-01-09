package com.example.vom;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import res.R;

public class DialogFlowManager {
    private int gameState = 0;
    private boolean isNewGame = false;


    public void advanceState(String theAction) {
        if (isNewGame) {
            processNewGameState(theAction);
        }
    }

    private void processNewGameState(String theAction) {
        switch (theAction) {
            case "0":
                gameState++;
                break;
            case R.introPlayerOptions.OPTIONS_1A:
                gameState = 5;
                break;
            case R.introPlayerOptions.OPTIONS_2, R.introPlayerOptions.OPTIONS_1B:
                gameState = 7;
                break;
        }
    }

    public String getGameStateMessage() {
        return switch (gameState) {
            case 1 -> R.introMessages.WELCOME_MSG;
            case 2 -> R.introMessages.REPLY_CONVO_MSG;
            case 3 -> R.introMessages.DISPLAY_PANEL_MSG;
            case 4 -> R.introMessages.TRY_BUTTON_MSG;
            case 5 -> R.introMessages.SMART_GUY_MSG;
            case 6 -> R.introMessages.NOT_SMART_MSG;
            case 7 -> "123";
            default -> "";
        };
    }

    public String[] getIntroPlayerOptions() {
        return switch (gameState) {
            case 4 -> new String[]{R.introPlayerOptions.OPTIONS_1A,
                    R.introPlayerOptions.OPTIONS_1B};
            case 5 -> new String[]{R.introPlayerOptions.OPTIONS_2,""};
            default -> new String[0];
        };
    }

    public void setNewGame(boolean newGame) {
        this.isNewGame = newGame;
    }

}
