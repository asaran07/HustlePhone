package com.example.vom;

import java.io.IOException;

public interface StateChangeListener {
    void onStateChange(final GameStateChangeEvent theEvent) throws IOException;
}
