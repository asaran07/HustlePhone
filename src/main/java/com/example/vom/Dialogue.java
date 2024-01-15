package com.example.vom;

import java.util.HashMap;
import java.util.Map;

public class Dialogue {

    private Map<String, String> responses;

    public Dialogue(String theKey, String theDialogue) {
        responses = new HashMap<>();
        responses.put(theKey, theDialogue);
    }

    public void addResponse(String playerAction, String characterResponse) {
        this.responses.put(playerAction, characterResponse);
    }
    public String getResponse(String playerAction) {
        return this.responses.get(playerAction);
    }
}
