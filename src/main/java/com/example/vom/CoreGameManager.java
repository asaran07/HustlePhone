package com.example.vom;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CoreGameManager implements CoreGameManagerContract, StateChangeListener {

    private DialogueManager dialogueManager;
    private GameStateManager gameStateManager;
    private CharacterDatabase characterDatabase;
    private Character currentCharacter;
    private DialogueService dialogueService;
    private static final String DEFAULT_CHARACTER = "Mike";


    public CoreGameManager(GameStateManager theGameStateManager, CharacterDatabase theCharacterDatabase) {
        setGameStateManager(theGameStateManager);
        gameStateManager.addStateChangeListener(this);
        characterDatabase = theCharacterDatabase;
    }

    public void setGameStateManager(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    public Dialogue getCurrentDialogue(String dialogueID) {
        // this.currentDialogue = dialogueManager.getDialogues().get(dialogueID);
        return dialogueManager.getDialogue(dialogueID);
    }

    private void setDefaultCharacter() {
        currentCharacter = characterDatabase.getCharacter("123");
        currentCharacter.setDialogues(dialogueManager.getAllDialogues());
    }

    @Override
    public void onStateChange(GameStateChangeEvent theEvent) throws IOException {
        if (theEvent.gameState() == GameState.IN_GAME) {
            startNewGame();
        }
    }

    private void startNewGame() throws IOException {
        dialogueService = new DialogueService();
        String dialogueID = "start_convo";
        loadDialogues("src/main/java/dialogues/dfile.json");
        setDefaultCharacter();
        enterCall(dialogueID);
    }

    public void enterCall(String dialogueID) throws IOException {
        System.out.println("starting call");
        processCallDialogue(dialogueID);
        gameStateManager.changeState(GameState.IN_CALL, UIState.STARTING_CALL);
    }

    public void dialNumber(String thePhoneNumber) throws IOException {
        System.out.println("dialing..");
        String dialogueID;

        // if the phone number equals Mike's phone number.
        if (thePhoneNumber.equals("123")) {
            enterCall("start_convo2");
        }
    }



    private void processCallDialogue(String dialogueID) throws IOException {
        dialogueService.establishDialogueOptions(dialogueID, dialogueManager);
        gameStateManager.changeState(GameState.FORWARD_CALL, UIState.STARTING_CALL);
        if (getAction() != null) {
            if (getAction().equals("showCallMikeButton")) {
                exitCall();
            } else if (getAction().equals("end_call")) {
                exitCall();
            }
        }
    }

    public void loadDialogues(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        dialogueManager = objectMapper.readValue(new File(filePath), new TypeReference<>() {});
    }

    private void exitCall() throws IOException {
        System.out.println("ending call");
        gameStateManager.changeState(GameState.NOT_IN_CALL, UIState.ENDING_CALL);
    }


    /* Methods below will mainly be used by the controller */


    public void processSelectedOption(Option theSelectedOption) throws IOException {
        processCallDialogue(theSelectedOption.getNextDialogueID());
    }

    public boolean actionPresent() {
        return dialogueService.getActions() != null;
    }

    public String getAction() {
        if (actionPresent()) {
            return dialogueService.getActions().getFirst().getAction();
        }
        return null;
    }

    /**
     * Return the current dialogue text from Dialogue Service.
     *
     * @return String
     */
    public String getDialogueCallText() {
        return dialogueService.getDialogueText();
    }

    /**
     * Retrieves the first option in the current dialogue from the Dialogue Service.
     *
     * @return Option
     */
    public Option getOption1() {
        return dialogueService.getOption1();
    }

    /**
     * Retrieves the second option in the current dialogue from the Dialogue Service.
     *
     * @return Option
     */
    public Option getOption2() {
        return dialogueService.getOption2();
    }
}