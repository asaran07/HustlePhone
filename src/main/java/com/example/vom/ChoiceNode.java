package com.example.vom;

import java.util.ArrayList;

public class ChoiceNode implements ConversationNode {
    private ArrayList<Option> options;

    private ConversationNode nextNode;

    public void addChoice(Option option) {
        options.add(option);
    }

    public void addChoices(ArrayList<Option> theOptions) {
        options.addAll(theOptions);
    }

    @Override
    public void setNextNode(ConversationNode nextNode) {

    }
}
