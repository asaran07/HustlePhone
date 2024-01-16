package com.example.vom;

import java.util.HashMap;

public class Player {
    private String name;
    private Choice choices;

    public Player(String theName) {
        this.name = theName;
    }

    public void setChoices(Choice choices) {
        this.choices = choices;
    }
}
