package org.robotsteam.model;

import java.awt.geom.Point2D;

public class RobotState {
    private final Point2D position;
    private volatile double direction = 0;

    public RobotState(int x, int y) {
        position = new Point2D.Double(x, y);
    }

    public Point2D getPosition() {
        return position;
    }
    public double getDirection() {
        return direction;
    }
    public double getX() {
        return position.getX();
    }
    public double getY() { return position.getY(); }

    public void setDirection(double direction) {
        this.direction = direction;
    }
    public void setPosition(Point2D position) {
        this.position.setLocation(position.getX(), position.getY());
    }

    public String info() {
        return String.format(
                "Position: (%f, %f) | Direction: %f",
                position.getX(), position.getY(), direction
        );
    }
}
