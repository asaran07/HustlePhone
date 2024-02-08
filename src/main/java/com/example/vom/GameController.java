package com.example.vom;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import res.R;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


/**
 * Controller for managing the UI and game states.
 * Acts as an observer for GameState changes.
 */
public class GameController implements Initializable, StateChangeListener {

    /* Manager for all GameStates */
    private GameStateManager gameStateManager;

    /* Player Contract for UI Animations */
    private UIAnimationPlayerContract uiAnimationPlayerContract;

    /* Manager Contract for Core Game Management */
    private CoreGameManagerContract coreGameManagerContract;

    /* FXML Components used in UI */
    @FXML private Pane replyPane;
    @FXML private Pane convoPane;
    @FXML private Pane titleScreenPane;
    @FXML private Button startButton;
    @FXML private Label convoLabel;
    @FXML private Button replyButton1;
    @FXML private Button replyButton2;
    @FXML private Button callMikeButton;

    /* UI Elements Group */
    private ArrayList<Node> titleScreenUIGroup;
    private ArrayList<Node> inGameUIGroup;
    private Timeline dialogueTimeline = new Timeline();

    /**
     * Constructor, initializes UI elements groups.
     */
    public GameController() {
        titleScreenUIGroup = new ArrayList<>();
        inGameUIGroup = new ArrayList<>();
    }

    /**
     * Sets the CoreGameManagerContract for the GameController.
     *
     * @param theCoreGameManagerContract The CoreGameManagerContract to be set.
     */
    public void setCoreGameManagerContract(CoreGameManagerContract theCoreGameManagerContract) {
        this.coreGameManagerContract = theCoreGameManagerContract;
    }

    /**
     * Sets the UIAnimationPlayerContract for the GameController.
     *
     * @param theUIAnimationPlayerContract The UIAnimationPlayerContract to be set.
     */
    public void setUiAnimationPlayerContract(UIAnimationPlayerContract theUIAnimationPlayerContract) {
        this.uiAnimationPlayerContract = theUIAnimationPlayerContract;
    }

    /**
     * Sets the GameStateManager for the GameController.
     *
     * @param theGameStateManager The GameStateManager to be set.
     */
    public void setGameStateManager(GameStateManager theGameStateManager) {
        this.gameStateManager = theGameStateManager;
        gameStateManager.addStateChangeListener(this);
    }

    /**
     * Initializes the GameController by adding UI elements to different groups and setting up button actions.
     *
     * @param url            The URL of the FXML document.
     * @param resourceBundle The Resource Bundle of the FXML document.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleScreenUIGroup.addAll(Arrays.asList(startButton,titleScreenPane));
        inGameUIGroup.addAll(Arrays.asList(replyButton1,replyButton2,replyPane,convoPane,convoLabel));
        setupButtonActions();
    }

    /**
     * Prepares the in-game screen by making UI elements visible and starting fade animations.
     */
    public void prepareInGameScreen() throws IOException {
        inGameUIGroup.forEach(this::makeVisible);
        uiAnimationPlayerContract.startMultipleFadeAnimations(Arrays
                .asList(replyPane, convoPane, convoLabel), false, R.speed.NORMAL);
        //processDialogueText("start_convo");
        //gameStateManager.changeState(GameState.IN_CALL, UIState.NO_CHANGE);
        callMikeButton.setVisible(false);
    }

    public void processDialogueText(String dialogueID) throws IOException {
        //System.out.println("processing dialogue");
        Dialogue currentDialogue = displayDialogue(dialogueID);
        List<Option> options = currentDialogue.getOptions();
        List<Option> displayableOptions = options.stream()
                .filter(option -> option.getText() != null
                        && !option.getText().isEmpty()).collect(Collectors.toList());

        if (!displayableOptions.isEmpty()) {
            replyButton1.setText(displayableOptions.getFirst().getText());
            makeVisible(replyButton1);
            uiAnimationPlayerContract.startFadeAnimation(replyButton1, false, R.speed.NORMAL);
            replyButton1.setOnAction(e -> {
                try {
                    processDialogueText(displayableOptions.getFirst().getNextDialogueID());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        } else {
            makeInvisible(replyButton1);
        }
        if (displayableOptions.size() > 1) {
            replyButton2.setText(displayableOptions.get(1).getText());
            makeVisible(replyButton2);
            uiAnimationPlayerContract.startFadeAnimation(replyButton2, false, R.speed.NORMAL);
            replyButton2.setOnAction(e -> {
                try {
                    processDialogueText(displayableOptions.get(1).getNextDialogueID());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        } else {
            makeInvisible(replyButton2);
        }

        if (options.isEmpty() || displayableOptions.isEmpty()) {
            endConversation();
        }
        if (options.getFirst().getAction() != null) {
            triggerAction(options.getFirst().getAction());
        }

    }

    private Dialogue displayDialogue(String dialogueID) {
        System.out.println("displaying dialogue");
        Dialogue currentDialogue = coreGameManagerContract.getCurrentDialogue(dialogueID);
        uiAnimationPlayerContract.startTypewriterEffect(
                currentDialogue.getText(), dialogueTimeline, convoLabel);
        return currentDialogue;
    }

    private void triggerAction(String action) {
        System.out.println("triggering action");
        switch (action) {
            case "showCallMikeButton":
                showCallMikeButton();
                break;

        }
    }

    private void showCallMikeButton() {
        makeInvisible(callMikeButton);
        uiAnimationPlayerContract.startFadeAnimation(callMikeButton, false, R.speed.SLOW);
    }

    public void callMikeButtonAction() throws IOException {
        System.out.println("showing call mike button");
        processDialogueText("start_convo2");
    }

    private void endConversation() throws IOException {
        System.out.println("ending conversation");
        gameStateManager.changeState(GameState.NOT_IN_CALL, UIState.NO_CHANGE);
    }

    /**
     * Prepares the title screen by setting the visibility of the title screen pane to true
     * and making all in-game UI elements invisible.
     */
    public void prepareTitleScreen() {
        titleScreenPane.setVisible(true);
        inGameUIGroup.forEach(this::makeInvisible);
    }

    /**
     * Makes the given node visible by setting its visibility property to true.
     *
     * @param theNode The node to make visible
     */
    public void makeVisible(final Node theNode) {
        theNode.setVisible(true);
    }

    /**
     * Makes the given Node invisible by setting its visibility to true
     * and its opacity to R.visibility.INVISIBLE
     *
     * @param theNode The Node to make invisible
     */
    private void makeInvisible(final Node theNode) {
        theNode.setVisible(true);
        theNode.setOpacity(R.visibility.INVISIBLE);
    }

    /**
     * Sets up the button actions for the game.
     */
    public void setupButtonActions() {
        startButton.setOnAction(actionEvent -> {
            uiAnimationPlayerContract.startFadeAnimation(titleScreenPane, true, R.speed.NORMAL);
            try {
                gameStateManager.changeState(GameState.IN_GAME, UIState.ON_GAME_SCREEN);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Handles the change of GameState to perform necessary UI updates.
     *
     * @param theEvent The event carrying  the new GameState.
     */
    @Override
    public void onStateChange(GameStateChangeEvent theEvent) throws IOException {
        switch (theEvent.uiState()) {
            case UIState.ON_TITLE_SCREEN -> prepareTitleScreen();
            case UIState.ON_GAME_SCREEN -> prepareInGameScreen();
            case UIState.PROCESS_DIALOGUE -> processDialogueText("dialogue_id");
        }
    }

    private void processDialogue() {

    }

}