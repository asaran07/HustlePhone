package com.example.vom;

import java.util.ArrayList;
import java.util.List;

public class Character {
    private String name;
    private Dialogue dialogue;
    private String phoneNumber;
    private List<String> memory;

    public Character(String name, String phoneNumber) {
        this.name = name;
        this.dialogue = new Dialogue();
        this.phoneNumber = phoneNumber;
        this.memory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dialogue getDialogue() {
        return this.dialogue;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getMemory() {
        return memory;
    }

    public void addMemory(String event) {
        this.memory.add(event);
    }

    // create a method to retrieve their wording based on the game state or scenario.

}
