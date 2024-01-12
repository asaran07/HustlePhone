package com.example.vom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameStateManager {

    private GameState currentGameState;
    private Map<GameStateCategory, List<StateChangeListener>> catagorizedListeners = new HashMap<>();
    public GameStateManager() {

    }

    public void changeState(GameState theNewState, GameStateCategory theCategory) {
        this.currentGameState = theNewState;
        processGameState();
        notifyStateChange(theNewState, theCategory);
    }

    private void processGameState() {

    }

    public void addStateChangeListener(final GameStateCategory theStateCategory, StateChangeListener theStateChangeListener) {
        catagorizedListeners.computeIfAbsent(theStateCategory, k -> new ArrayList<>()).add(theStateChangeListener);
    }

    private void notifyStateChange(final GameState theNewState, final GameStateCategory theStateCategory) {
        List<StateChangeListener> listeners = catagorizedListeners.get(theStateCategory);
        if (listeners != null) {
            for (StateChangeListener listener : listeners) {
                listener.onStateChange(theNewState);
            }
        }
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(final GameState theNewGameState) {
        this.currentGameState = theNewGameState;
    }
}
