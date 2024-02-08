package com.example.vom;

import java.util.HashMap;
import java.util.Map;

public class DialogueManager {
    private HashMap<String, Dialogue> dialogues;
    public HashMap<String, Dialogue> getDialogues() {
        return dialogues;
    }
    public void setDialogues(Map<String, Dialogue> dialogues) {
        this.dialogues = (HashMap<String, Dialogue>) dialogues;
    }
    public HashMap<String, Dialogue> getAllDialogues() {
        return dialogues;
    }
    public Dialogue getDialogue(String dialogueId) {
        return dialogues.get(dialogueId);
    }
}
