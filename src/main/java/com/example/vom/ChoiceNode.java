package com.example.vom;

import java.util.ArrayList;

public class ChoiceNode implements ConversationNode {
    private ArrayList<Choice> choices;

    private ConversationNode nextNode;

    public void addChoice(Choice choice) {
        choices.add(choice);
    }

    public void addChoices(ArrayList<Choice> theChoices) {
        choices.addAll(theChoices);
    }

    @Override
    public void setNextNode(ConversationNode nextNode) {

    }
}
