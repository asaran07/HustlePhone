package com.example.vom;

public class CoreGameManager implements CoreGameManagerContract, StateChangeListener {
    private GameStateManager gameStateManager;

    public CoreGameManager(GameStateManager theGameStateManager) {
        setGameStateManager(theGameStateManager);
        gameStateManager.addStateChangeListener(this);
    }

    public void setGameStateManager(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void onStateChange(GameStateChangeEvent theEvent) {
        if (theEvent.gameState() == GameState.IN_GAME) {
            startNewGame();
        }
    }

    private void startNewGame() {
        Dialogue dialogue = new Dialogue("","hey kid");
        Character mike = new Character("Mike","123", dialogue);
        Player player = new Player("Player1");
    }

    private void startTutorial() {

    }


}
