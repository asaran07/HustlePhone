package com.example.vom;

import java.util.ArrayList;
import java.util.HashMap;

public class Character {
    private String name;
    private HashMap<String, ArrayList<Dialogue>> dialogueSet;
    private HashMap<String, Dialogue> dialogues;
    private String phoneNumber;

    public Character(String theName, String thePhoneNumber) {
        this.name = theName;
        this.phoneNumber = thePhoneNumber;
        dialogues = new HashMap<>();
        dialogueSet = new HashMap<>();
    }

    public void setDialogues(HashMap<String, Dialogue> dialogues) {
        this.dialogues = dialogues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public HashMap<String, ArrayList<Dialogue>> getDialogueSet() {
        return dialogueSet;
    }

    public void addDialogueSet(String key, Dialogue dialogue) {
        if (!dialogueSet.containsKey(key)) {
            dialogueSet.put(key, new ArrayList<>());
        }
        dialogueSet.get(key).add(dialogue);
    }

    public Dialogue getDialogue(String key) {
        return dialogues.get(key);
    }

    public void addDialogue(String key, Dialogue dialogue) {
        this.dialogues.put(key, dialogue);
    }



    // create a method to retrieve their wording based on the game state or scenario.

}
