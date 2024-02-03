package com.example.vom;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class DialogueManager {
    private Map<String, Dialogue> dialogues;
    public Map<String, Dialogue> getDialogues() {
        return dialogues;
    }
    public void setDialogues(Map<String, Dialogue> dialogues) {
        this.dialogues = dialogues;
    }

}
