package com.example.vom;

public class ChoiceNode implements ConversationNode {
    private Choice choice;
    private ConversationNode nextNode;
    public ChoiceNode(Choice choice, ConversationNode nextNode) {
        this.choice = choice;
        this.nextNode = nextNode;
    }
}
