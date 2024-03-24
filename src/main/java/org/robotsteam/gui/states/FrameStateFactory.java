package org.robotsteam.gui.states;

import javax.swing.*;
import java.awt.*;

public class FrameStateFactory {
    public static FrameState buildFromJInternalFrame(JInternalFrame frame) {
        Dimension size = frame.getSize();
        Boolean isMinimized = frame.isIcon();
        Point location = frame.getLocation();

        return new FrameState(size, location, isMinimized);
    }
}
