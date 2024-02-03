package com.example.vom;

public class EmptyNode implements ConversationNode {
    @Override
    public String toString() {
        return "EmptyNode";
    }

    @Override
    public void setNextNode(ConversationNode nextNode) {

    }
}
