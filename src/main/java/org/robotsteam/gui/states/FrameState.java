package org.robotsteam.gui.states;

import java.awt.*;
import java.io.Serializable;

public class FrameState implements Serializable {
    private Dimension size;
    private Point location;
    private Boolean isMinimized;

    public FrameState() { }
    public FrameState(Dimension size, Point location, Boolean isMinimized) {
        this.size = size;
        this.location = location;
        this.isMinimized = isMinimized;
    }

    public Point getLocation() { return location; }

    public Boolean getMinimized() { return isMinimized; }

    public Dimension getSize() { return size; }
}
