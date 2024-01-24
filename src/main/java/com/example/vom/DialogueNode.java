package com.example.vom;

public class DialogueNode implements ConversationNode {
    private Dialogue dialogue;
    private ConversationNode nextNode;
    public DialogueNode(Dialogue dialogue) {
        this.dialogue = dialogue;
    }
    public Dialogue getDialogue() {
        return dialogue;
    }

    public void setNextNode(ConversationNode nextNode) {
        this.nextNode = nextNode;
    }
}
