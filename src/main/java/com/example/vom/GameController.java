package com.example.vom;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import res.R;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Supplier;


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
    @FXML private TextField phNumberField;
    @FXML private Button dialButton;
    @FXML private Pane infoCallPane;
    @FXML private Circle callStatusCircle;

    /* UI Elements Group */
    private ArrayList<Node> titleScreenUIGroup;
    private ArrayList<Node> inGameUIGroup;
    private ArrayList<Node> infoCallGroup;
    private Timeline dialogueTimeline = new Timeline();
    private FadeTransition fadeTransition = new FadeTransition();

    /**
     * Constructor, initializes UI elements groups.
     */
    public GameController() {
        titleScreenUIGroup = new ArrayList<>();
        inGameUIGroup = new ArrayList<>();
        infoCallGroup = new ArrayList<>();
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
        infoCallGroup.addAll(Arrays.asList(phNumberField, dialButton, callStatusCircle, infoCallPane));
    }

    /**
     * Prepares the in-game screen by making UI elements visible and starting fade animations.
     */
    public void prepareInGameScreen() throws IOException {
        inGameUIGroup.forEach(this::makeVisible);
        uiAnimationPlayerContract.startMultipleFadeAnimations(Arrays
                .asList(replyPane, convoPane, convoLabel), false, R.speed.NORMAL);
        callMikeButton.setVisible(false);
        infoCallGroup.forEach(this::makeInvisible);
        disableDialing();
    }

    private void showCallMikeButton() {
        makeInvisible(callMikeButton);
        uiAnimationPlayerContract.startFadeAnimation(callMikeButton, false, R.speed.SLOW);
    }

    /**
     * Prepares the title screen by setting the visibility of the title screen pane to true
     * and making all in-game UI elements invisible.
     */
    public void prepareTitleScreen() {
        titleScreenPane.setVisible(true);
        inGameUIGroup.forEach(this::makeInvisible);
        infoCallGroup.forEach(this::makeInvisible);
        callMikeButton.setVisible(false);
    }

    /**
     * Makes the given node visible by setting its visibility property to true.
     *
     * @param theNode The node to make visible
     */
    public void makeVisible(final Node theNode) {
        theNode.setVisible(true);
        theNode.setOpacity(R.visibility.VISIBLE);
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
            case UIState.STARTING_CALL -> processDialogue();
            case UIState.ENDING_CALL -> handleCallEnding();
        }
    }

    private void processDialogue() {

        animateCallStatusCircle();
        infoCallGroup.forEach(this::makeVisible);

        // First we need to get the dialogue text from CoreGameManager to display on the conversation pane.
        String dialogueText = coreGameManagerContract.getDialogueCallText();

        // Then we animate the text on the conversation pane
        convoLabel.setText("");
        uiAnimationPlayerContract.startTypewriterEffect(dialogueText, dialogueTimeline = new Timeline(), convoLabel);

        // Get Option 1 and Option 2 from CoreGameManager
        Option option1 = coreGameManagerContract.getOption1();
        Option option2 = coreGameManagerContract.getOption2();

        // Display buttons if option texts exist
        handleButtonsAppearance(option1, option2);

        // Trigger actions if required
        if (coreGameManagerContract.actionPresent() && !coreGameManagerContract.getAction().isEmpty()) {
            triggerAction(coreGameManagerContract.getAction());
        }

        // Send the selected option back to CoreGameManager using the button's on-action property.
        handleSelectedOptions(option1, option2);
    }

    private void handleButtonsAppearance(Option option1, Option option2) {
        configureButtonAppearance(replyButton1, option1, coreGameManagerContract::getOption1);
        configureButtonAppearance(replyButton2, option2, coreGameManagerContract::getOption2);
    }

    private void handleSelectedOptions(Option option1, Option option2) {
        replyButton1.setOnAction(e -> processOptionAction(option1));
        replyButton2.setOnAction(e -> processOptionAction(option2));
    }

    private void processOptionAction(Option option) {
        try {
            coreGameManagerContract.processSelectedOption(option);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void configureButtonAppearance(Button replyButton, Option option, Supplier<Option> optionSupplier) {
        if (!option.getText().isEmpty()) {
            uiAnimationPlayerContract.startFadeAnimation(replyButton, false, R.speed.NORMAL);
            replyButton.setText(optionSupplier.get().getText());
        } else {
            uiAnimationPlayerContract.startFadeAnimation(replyButton, true, R.speed.NORMAL);
        }
    }

    private void triggerAction(String action) {
        System.out.println("processing action");
        switch (action) {
            case "showCallMikeButton":
                //showCallMikeButton();
                //setCallMikeButtonAction();
                enableDialing();
                break;
        }
    }

    private void setCallMikeButtonAction() {
        callMikeButton.setOnAction(e -> {
            try {
                coreGameManagerContract.enterCall("start_convo2");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void enableDialing() {
        phNumberField.setDisable(false);
        dialButton.setDisable(false);
    }

    private void disableDialing() {
        phNumberField.setDisable(true);
        dialButton.setDisable(true);
    }

    private void animateCallStatusCircle() {
        callStatusCircle.setFill(Color.GREEN);
        fadeTransition.setDuration(Duration.seconds(1));
        fadeTransition.setNode(callStatusCircle);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Timeline.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
    }

    private void stopCallStatusCircleAnimation() {
        if (fadeTransition != null) {
            fadeTransition.stop();
        }
    }

    private void handleCallEnding() {
        stopCallStatusCircleAnimation();
        callStatusCircle.setFill(Color.GRAY);

        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(4), e -> {
            convoLabel.setText("");
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void dialButtonAction() {
        String phoneNumber = phNumberField.getText();
        try {
            coreGameManagerContract.dialNumber(phoneNumber);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }








}