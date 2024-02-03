package com.example.vom;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Option {
    private String text;
    @JsonProperty("next")
    private String nextDialogueID;

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
}
