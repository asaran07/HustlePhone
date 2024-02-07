package com.example.vom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CharacterDatabase {
    private HashMap<String, Character> characterList;

    public CharacterDatabase() {
        characterList = new HashMap<>();
        makeCharacters();
    }

    private void makeCharacters() {
        Character mike = new Character("Mike", "123");
        characterList.put(mike.getPhoneNumber(), mike);
    }

    public Character getCharacter(String phoneNumber) {
        return characterList.get(phoneNumber);
    }



}
