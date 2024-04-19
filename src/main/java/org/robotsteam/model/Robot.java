package org.robotsteam.model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Observable;
import static org.robotsteam.model.RobotMath.*;
import static org.robotsteam.model.RobotMath.asNormalizedRadians;

public class Robot extends Observable {
    private final RobotState robot;

    public static final String ROBOT_MOVED = "robot moved";

    public Robot(int x, int y) {
        super();
        robot = new RobotState(x, y);
    }

    public double getM_robotPositionX() {
        return robot.getX();
    }
    public double getM_robotPositionY() {
        return robot.getY();
    }
    public double getM_robotDirection() {
        return robot.getDirection();
    }

    public void update(Point2D target, Dimension bounds) {
        if (robot.getPosition().distance(target) < 0.5) return;

        double angularVelocity = countAngularVelocity(target, robot);
        moveRobot(maxVelocity, angularVelocity, 10, bounds);
        setChanged(); notifyObservers(ROBOT_MOVED); clearChanged();
    }

    public String info() {
        return robot.info();
    }

    private void moveRobot(double velocity, double angularVel, double t, Dimension bounds) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVel = applyLimits(angularVel, -maxAngularVelocity, maxAngularVelocity);

        double newDirection = robot.getDirection() + angularVel * t;
        Point2D newPos = countNewPosition(robot, velocity, angularVel, t);

        robot.setPosition(newPos); robot.setDirection(applyBounds(bounds, newDirection));
    }

    private double applyBounds(Dimension bounds, double direction) {
        if (robot.getX() < 0 || robot.getX() > bounds.width) {
            return asNormalizedRadians(Math.PI - direction);
        }

        if (robot.getY() < 0 || robot.getY() > bounds.height) {
            return asNormalizedRadians(-direction);
        }

        return direction;
    }
}
