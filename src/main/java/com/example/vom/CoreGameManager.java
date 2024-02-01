package com.example.vom;
import org.luaj.vm2.Globals;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.Arrays;
import java.util.Scanner;

public class CoreGameManager implements CoreGameManagerContract, StateChangeListener {
    Globals globals = JsePlatform.standardGlobals();
    LuaValue script;
    private GameStateManager gameStateManager;
    private ConversationNode currentNode;
    private CharacterDatabase characterDatabase;
    private Character currentCharacter;
    private Dialogue currentDialogue;


    public CoreGameManager(GameStateManager theGameStateManager, CharacterDatabase theCharacterDatabase) {
        setGameStateManager(theGameStateManager);
        gameStateManager.addStateChangeListener(this);
        characterDatabase = theCharacterDatabase;
    }

    public void setGameStateManager(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void onStateChange(GameStateChangeEvent theEvent) {
        if (theEvent.gameState() == GameState.IN_GAME) {
            startNewGame();
        }
    }

    private void startNewGame() {
        this.script = globals.loadfile("LuaScripts/dialogueLua.lua").call();
        process("start_convo");
    }

    private void process(String nodeName) {
        LuaValue node = script.get(nodeName);
        System.out.println(node.get("test").toString());

        LuaValue options = node.get("options");
        if (!options.isnil()) {
            for (int i = 1; i <= options.length(); i++) {
                LuaValue option = options.get(i);
                System.out.println(i + " : " + option.get("text").toString());
            }
        }

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        LuaValue selectedOption = options.get(choice);
        if (!selectedOption.isnil()) {
            String nextNode = selectedOption.get("nextNode").toString();
            if (!nextNode.isEmpty()) {
                process(nextNode);
            }
        }

    }


}
