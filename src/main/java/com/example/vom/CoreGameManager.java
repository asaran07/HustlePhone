package com.example.vom;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CoreGameManager implements CoreGameManagerContract, StateChangeListener {

    private DialogueManager dialogueManager;
    private GameStateManager gameStateManager;
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

    public Dialogue getCurrentDialogue(String dialogueID) {
        // this.currentDialogue = dialogueManager.getDialogues().get(dialogueID);
        return dialogueManager.getDialogues().get(dialogueID);
    }

    private void startNewGame() {

    }

    public void loadDialogues(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        dialogueManager = objectMapper.readValue(new File(filePath), new TypeReference<>() {});

    }
}