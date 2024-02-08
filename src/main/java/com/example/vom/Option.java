package com.example.vom;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Option {
    private String text;
    @JsonProperty("next")
    private String nextDialogueID;
    private String action;

    public void setNextDialogueID(String nextDialogueID) {
        this.nextDialogueID = nextDialogueID;
    }

    public String getNextDialogueID() {
        return nextDialogueID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String theAction) {
        this.action = theAction;
    }

}
