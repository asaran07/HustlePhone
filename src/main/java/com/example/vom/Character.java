package com.example.vom;

import java.util.ArrayList;
import java.util.List;

public class Character {
    private String name;
    private Dialogue dialogue;
    private ArrayList<Dialogue> dialogues;
    private String phoneNumber;

    public Character(String theName, String thePhoneNumber) {
        this.name = theName;
        this.phoneNumber = thePhoneNumber;
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

    // create a method to retrieve their wording based on the game state or scenario.

}
