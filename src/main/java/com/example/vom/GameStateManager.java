package com.example.vom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameStateManager {

    private GameState currentGameState;
    private UIState currentUIState;
    private List<StateChangeListener> listeners = new ArrayList<>();

    public void changeState(GameState theGameState, UIState theUIState) throws IOException {
        currentGameState = theGameState;
        currentUIState = theUIState;
        GameStateChangeEvent event = new GameStateChangeEvent(theGameState, theUIState);
        notifyStateChange(event);
    }

    private void notifyStateChange(GameStateChangeEvent theEvent) throws IOException {
        for (StateChangeListener listener : listeners) {
            listener.onStateChange(theEvent);
        }
    }

    public void addStateChangeListener(StateChangeListener theListener) {
        listeners.add(theListener);
    }

}
