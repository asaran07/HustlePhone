package com.example.vom;

public class GameStateManager {
    private GameState currentGameState;
    public GameStateManager() {
        currentGameState = GameState.TITLE_SCREEN;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(GameState theGameState) {
        this.currentGameState = theGameState;
    }
}
