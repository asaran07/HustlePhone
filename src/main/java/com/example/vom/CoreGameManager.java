package com.example.vom;

public class CoreGameManager implements CoreGameManagerContract, StateChangeListener {
    private GameStateManager gameStateManager;
    private ConversationNode currentNode;

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
        Choice choices1 = new Choice("uhh.. i think so?", "loud and clear.");
        Dialogue dialogue1 = new Dialogue("mike", "heyy kid!!");
        ConversationNode empty = new EmptyNode();
        ConversationNode Cnode1 = new ChoiceNode(choices1, empty);
        ConversationNode Dnode1 = new DialogueNode(dialogue1, Cnode1);
        currentNode = Dnode1;

    }

    private void startTutorial() {

    }


}
