package org.robotsteam.gui.states;

import javax.swing.*;
import java.io.Serializable;

public class AppState implements Serializable {
    private FrameState gameWindowState;
    private FrameState logWindowState;

    public AppState() {}
    public AppState(JInternalFrame gameWindow, JInternalFrame logWindow) {
        logWindowState = FrameStateFactory.buildFromJInternalFrame(logWindow);
        gameWindowState = FrameStateFactory.buildFromJInternalFrame(gameWindow);
    }

    public FrameState getGameWindowState() { return gameWindowState; }

    public FrameState getLogWindowState() { return logWindowState; }
}
