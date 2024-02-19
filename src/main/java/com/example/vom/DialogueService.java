package com.example.vom;

import java.util.List;
import java.util.Optional;

public class DialogueService {
    private List<Option> options;
    private List<Option> actions;
    private String dialogueText;
    private Option option1;
    private Option option2;


    public void setOptions(List<Option> theOptionsList) {
        this.options = theOptionsList;
    }

    public void setActions(List<Option> theActionsList) {
        this.actions = theActionsList;
    }

    private void setOption1(Option theOption) {
        this.option1 = theOption;
    }

    private void setOption2(Option theOption) {
        this.option2 = theOption;
    }

    public void establishDialogueOptions(String dialogueID, DialogueManager dialogueManager) {
        setOptions(dialogueManager.getDialogue(dialogueID).getOptions());
        setActions(options.stream().filter(option -> option.getAction() != null).toList());
        this.dialogueText = dialogueManager.getDialogue(dialogueID).getText();
        setOptions();
    }

    public String getDialogueText() {
        return dialogueText;
    }

    public Option getOption1() {
        return option1;
    }

    public Option getOption2() {
        return option2;
    }

    public List<Option> getOptions() {
        return options;
    }

    public List<Option> getActions() {
        return actions;
    }

    public void setOptions() {
        Optional.ofNullable(options.get(0))
                .ifPresentOrElse(
                        this::setOption1,
                        () -> System.out.println("opt-1 found null"));

        if (options.size() > 1) {
            Optional.ofNullable(options.get(1))
                    .ifPresentOrElse(
                            this::setOption2,
                            () -> System.out.println("opt-2 found null"));
        } else {
            this.option2.setText("");
        }
    }

    @Override
    public String toString() {
        return "DialogueService " + "[Options/Actions]"
                + "\nOption 1 Text=" + option1.getText()
                + "\nOption 2 Text=" + option2.getText()
                + "\nActions=" + getActions();
    }


}
