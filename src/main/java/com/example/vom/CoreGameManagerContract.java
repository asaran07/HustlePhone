package com.example.vom;

import java.io.IOException;

public interface CoreGameManagerContract {
    Dialogue getCurrentDialogue(String dialogueID);
    void loadDialogues(String filePath) throws IOException;
}
