package com.example.vom;

import java.io.IOException;


public interface CoreGameManagerContract {
    Dialogue getCurrentDialogue(String dialogueID);
    boolean actionPresent();
    String getAction();
    void processSelectedOption(Option theSelectedOption) throws IOException;
    String getDialogueCallText();
    Option getOption1();
    Option getOption2();
    void loadDialogues(String filePath) throws IOException;
    void enterCall(String dialogueID) throws IOException;
    void dialNumber(String thePhoneNumber) throws IOException;
}
