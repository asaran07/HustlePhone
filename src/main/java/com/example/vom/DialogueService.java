package com.example.vom;

import java.util.List;

public class DialogueService {
    private List<Option> options;
    private List<Option> actions;
    private String optionText1;
    private String optionText2;
    private String dialogueText;

    public void setOptions(List<Option> theOptionsList) {
        this.options = theOptionsList;
    }

    public void setActions(List<Option> theActionsList) {
        this.actions = theActionsList;
    }

    private void setOptionText1(String theOptionText) {
        this.optionText1 = theOptionText;
    }

    public void establishDialogueOptions(String dialogueID, DialogueManager dialogueManager) {
        setOptions(dialogueManager.getDialogue(dialogueID).getOptions());
        setActions(options.stream().filter(option -> option.getAction() != null).toList());
        this.dialogueText = dialogueManager.getDialogue(dialogueID).getText();
        setOptionTexts();
    }

    public String getDialogueText() {
        return dialogueText;
    }

    private void setOptionText2(String theOptionText) {
        this.optionText2 = theOptionText;
    }

    public List<Option> getOptions() {
        return options;
    }

    public List<Option> getActions() {
        return actions;
    }

    public void setOptionTexts() {
        if (options.get(0).getText() != null) {
            setOptionText1(options.get(0).getText());
        } else {
            System.out.println("Opt 1 text null");
        }
        if (options.get(1).getText() != null) {
            setOptionText2(options.get(1).getText());
        } else {
            System.out.println("Opt 2 text null");
        }
    }

    @Override
    public String toString() {
        return "DialogueService " + "[Options/Actions]"
                + "\nOption 1 Text=" + optionText1
                + "\nOption 2 Text=" + optionText2
                + "\nActions=" + getActions();
    }


}
