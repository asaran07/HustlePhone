package com.example.vom;

import java.util.Arrays;

public class CoreGameManager implements CoreGameManagerContract, StateChangeListener {
    private GameStateManager gameStateManager;
    private ConversationNode currentNode;
    private CharacterDatabase characterDatabase;
    private Character currentCharacter;
    private Dialogue currentDialogue;


    public CoreGameManager(GameStateManager theGameStateManager, CharacterDatabase theCharacterDatabase) {
        setGameStateManager(theGameStateManager);
        gameStateManager.addStateChangeListener(this);
        characterDatabase = theCharacterDatabase;
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
        currentCharacter = characterDatabase.getCharacter("123");

        Dialogue dialogueA = new Dialogue("What's 2+2?");
        ConversationNode conversationNodeA = new DialogueNode(dialogueA);

        Dialogue dialogueA1 = new Dialogue("Wrong, how old are you?");
        Dialogue dialogueA2 = new Dialogue("Right, not a kid I see.");

        Dialogue dialogueB1 = new Dialogue("It doesn't matter, let us begin.");
        Dialogue dialogueB2 = new Dialogue("Well nothing we can do now..lets begin anyway.");

        ConversationNode conversationNodeB1 = new DialogueNode(dialogueA1);
        ConversationNode conversationNodeB2 = new DialogueNode(dialogueA2);

        Choice choiceA = new Choice("4", conversationNodeB2);
        Choice choiceB = new Choice("21", conversationNodeB1);

        ConversationNode conversationNodeB3 = new DialogueNode(dialogueB1);
        Choice choiceC1 = new Choice("You asked that question to confirm that?", conversationNodeB3);
        Choice choiceC2 = new Choice("Definitely not a kid haha..", conversationNodeB3);

        ConversationNode conversationNodeC1 = new DialogueNode(dialogueB2);
        Choice choiceC3 = new Choice("I'm old enough.", conversationNodeC1);
        Choice choiceC4 = new Choice("Why do you ask?", conversationNodeC1);

        ConversationNode conversationNodeB = new ChoiceNode();
        ((ChoiceNode) conversationNodeB).addChoice(choiceA);
        ((ChoiceNode) conversationNodeB).addChoice(choiceB);

        ((ChoiceNode) conversationNodeB3).addChoice(choiceC1);
        ((ChoiceNode) conversationNodeB3).addChoice(choiceC2);

        ((ChoiceNode) conversationNodeC1).addChoice(choiceC3);
        ((ChoiceNode) conversationNodeC1).addChoice(choiceC4);

        currentNode = conversationNodeA;

    }

    private void process() {

    }


}
