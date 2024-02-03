package com.example.vom;

public class Player {
    private String name;
    private Option choices;

    public Player(String theName) {
        this.name = theName;
    }

    public void setChoices(Option choices) {
        this.choices = choices;
    }
}
