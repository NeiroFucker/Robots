package org.robotsteam.gui.states;

import javax.swing.*;
import java.io.Serializable;

public class AppState implements Serializable {
    private FrameState gameWindowState;
    private FrameState logWindowState;
    private FrameState stateWindowState;

    public AppState() {}
    public AppState(JInternalFrame gameWindow, JInternalFrame logWindow, JInternalFrame stateWindow) {
        logWindowState = FrameStateFactory.buildFromJInternalFrame(logWindow);
        gameWindowState = FrameStateFactory.buildFromJInternalFrame(gameWindow);
        stateWindowState = FrameStateFactory.buildFromJInternalFrame(stateWindow);
    }

    public FrameState getGameWindowState() { return gameWindowState; }

    public FrameState getLogWindowState() { return logWindowState; }

    public FrameState getStateWindowState() { return stateWindowState; }
}
