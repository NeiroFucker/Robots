package org.robotsteam.model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Observable;
import static org.robotsteam.model.RobotMath.*;

public class Robot extends Observable {
    private final Point2D position;
    private volatile double m_robotDirection = 0;

    public static final String ROBOT_MOVED = "robot moved";

    public Robot(int x, int y) {
        super();
        position = new Point2D.Double(x, y);
    }

    public double getM_robotPositionX() {
        return position.getX();
    }
    public double getM_robotPositionY() {
        return position.getY();
    }
    public double getM_robotDirection() {
        return m_robotDirection;
    }

    public void update(Point2D target) {
        if (position.distance(target) < 0.5) return;

        double angularVelocity = countAngularVelocity(position, target, m_robotDirection);
        moveRobot(maxVelocity, angularVelocity, 10);
        setChanged(); notifyObservers(ROBOT_MOVED); clearChanged();
    }

    public String info() {
        return String.format(
                "Position: (%f, %f) | Direction: %f",
                position.getX(), position.getY(), m_robotDirection
        );
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);

        double newX = position.getX() + velocity / angularVelocity *
                (Math.sin(m_robotDirection + angularVelocity * duration) - Math.sin(m_robotDirection));

        if (!Double.isFinite(newX)) {
            newX = position.getX() + velocity * duration * Math.cos(m_robotDirection);
        }

        double newY = position.getY() - velocity / angularVelocity *
                (Math.cos(m_robotDirection  + angularVelocity * duration) - Math.cos(m_robotDirection));
        if (!Double.isFinite(newY)) {
            newY = position.getY() + velocity * duration * Math.sin(m_robotDirection);
        }

        position.setLocation(newX, newY);
        double newDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);
        m_robotDirection = newDirection;
    }
}
