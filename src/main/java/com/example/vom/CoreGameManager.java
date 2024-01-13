package com.example.vom;

public class CoreGameManager implements CoreGameManagerContract, StateChangeListener {
    private GameStateManager gameStateManager;

    public CoreGameManager(GameStateManager theGameStateManager) {
        setGameStateManager(theGameStateManager);
        gameStateManager.addStateChangeListener(GameStateCategory.GAME_PROGRESS, this);
    }

    public void setGameStateManager(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void onStateChange(GameState theNewState) {
        switch (theNewState) {
            case NEW_GAME -> startNewGame();
        }
    }

    private void startNewGame() {
        gameStateManager.changeState(GameState.IN_GAME, GameStateCategory.UI_UPDATE);
    }


}
