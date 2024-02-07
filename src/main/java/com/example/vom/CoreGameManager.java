package com.example.vom;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CoreGameManager implements CoreGameManagerContract, StateChangeListener {

    private DialogueManager dialogueManager;
    private GameStateManager gameStateManager;
    private CharacterDatabase characterDatabase;
    private Character currentCharacter;
    private Dialogue currentDialogue;
    private boolean newGame;
    private static final String DEFAULT_CHARACTER = "Mike";


    public CoreGameManager(GameStateManager theGameStateManager, CharacterDatabase theCharacterDatabase) {
        setGameStateManager(theGameStateManager);
        gameStateManager.addStateChangeListener(this);
        characterDatabase = theCharacterDatabase;
    }

    public void setGameStateManager(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void onStateChange(GameStateChangeEvent theEvent) throws IOException {
        if (theEvent.gameState() == GameState.IN_GAME) {
            startNewGame();
        }
    }

    public Dialogue getCurrentDialogue(String dialogueID) {
        // this.currentDialogue = dialogueManager.getDialogues().get(dialogueID);
        return dialogueManager.getDialogues().get(dialogueID);
    }

    private void setDefaultCharacter() {
        currentCharacter = characterDatabase.getCharacter("123");
        currentCharacter.setDialogues(dialogueManager.getAllDialogues());
    }

    private String ascertainDialogueID () {
        if (newGame) {
            return DEFAULT_CHARACTER;
        }
        else {
            return "null";
        }
    }

    private void startNewGame() throws IOException {
        String dialogueID = ascertainDialogueID();
        loadDialogues("src/main/java/dialogues/dfile.json");
        setDefaultCharacter(); // to Mike, since it's a new game.
        enterCall(dialogueID);
    }

    private void enterCall(String dialogueID) throws IOException {
        gameStateManager.changeState(GameState.IN_CALL, UIState.NO_CHANGE);
        processDialogue(dialogueID);
    }

    private void processDialogue(String dialogueID) {
        List<Option> options = currentCharacter.getDialogue(dialogueID).getOptions();
        for (Option option : options) {
            System.out.println(option.getText());
        }
        String option1 = options.get(0).getText();
        String option2 = options.get(1).getText();
    }


    private void advanceCall() {

    }


    public void loadDialogues(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        dialogueManager = objectMapper.readValue(new File(filePath), new TypeReference<>() {});
    }
}