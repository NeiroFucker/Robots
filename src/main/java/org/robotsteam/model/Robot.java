package org.robotsteam.model;

import java.awt.*;
import java.util.Observable;
import static org.robotsteam.model.RobotMath.*;

public class Robot extends Observable {
    private volatile double m_robotPositionX;
    private volatile double m_robotPositionY;
    private volatile double m_robotDirection = 0;

    public static final String ROBOT_MOVED = "robot moved";

    public Robot(int x, int y) {
        super();
        m_robotPositionX = x;
        m_robotPositionY = y;
    }

    public double getM_robotPositionX() {
        return m_robotPositionX;
    }
    public double getM_robotPositionY() {
        return m_robotPositionY;
    }
    public double getM_robotDirection() {
        return m_robotDirection;
    }

    public void update(double m_targetPositionX, double m_targetPositionY) {
        double distance = distance(
                m_robotPositionX, m_robotPositionY,
                m_targetPositionX, m_targetPositionY
        );

        if (distance < 0.5) return;

        double angularVelocity = countAngularVelocity(
                m_robotPositionX, m_robotPositionY,
                m_targetPositionX, m_targetPositionY, m_robotDirection
        );
        moveRobot(maxVelocity, angularVelocity, 10);
        setChanged(); notifyObservers(ROBOT_MOVED); clearChanged();
    }

    public String info() {
        return String.format(
                "Position: (%f, %f) | Direction: %f",
                m_robotPositionX, m_robotPositionY, m_robotDirection
        );
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);

        double newX = m_robotPositionX + velocity / angularVelocity *
                (Math.sin(m_robotDirection + angularVelocity * duration) - Math.sin(m_robotDirection));

        if (!Double.isFinite(newX)) {
            newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);
        }

        double newY = m_robotPositionY - velocity / angularVelocity *
                (Math.cos(m_robotDirection  + angularVelocity * duration) - Math.cos(m_robotDirection));
        if (!Double.isFinite(newY)) {
            newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);
        }

        m_robotPositionX = newX; m_robotPositionY = newY;
        double newDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);
        m_robotDirection = newDirection;
    }
}
