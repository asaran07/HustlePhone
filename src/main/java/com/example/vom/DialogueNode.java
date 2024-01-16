package com.example.vom;

public class DialogueNode implements ConversationNode {
    private Dialogue dialogue;
    private ConversationNode nextNode;
    public DialogueNode(Dialogue dialogue, ConversationNode nextNode) {
        this.dialogue = dialogue;
        this.nextNode = nextNode;
    }
}
