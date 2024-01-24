package com.example.vom;

public class Choice {
    private String choice;
    private ConversationNode nextNode;

    public Choice(String theChoice, ConversationNode theNextNode) {
        this.choice = theChoice;
        this.nextNode = theNextNode;
    }


    public String getChoice() {
        return choice;
    }
    public void setNextNode(ConversationNode nextNode) {
        this.nextNode = nextNode;
    }
    public void setChoice(String choice) {
        this.choice = choice;
    }
    public ConversationNode getNextNode() {
        return nextNode;
    }


}
