package org.robotsteam.model;

import java.awt.geom.Point2D;
import java.util.Observable;
import static org.robotsteam.model.RobotMath.*;

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

    public void update(Point2D target) {
        if (robot.getPosition().distance(target) < 0.5) return;

        double angularVelocity = countAngularVelocity(robot.getPosition(), target, robot.getDirection());
        moveRobot(maxVelocity, angularVelocity, 10);
        setChanged(); notifyObservers(ROBOT_MOVED); clearChanged();
    }

    public String info() {
        return robot.info();
    }

    private void moveRobot(double velocity, double angularVel, double duration) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVel = applyLimits(angularVel, -maxAngularVelocity, maxAngularVelocity);

        double newX = robot.getX() + velocity / angularVel *
                (Math.sin(robot.getDirection() + angularVel * duration) - Math.sin(robot.getDirection()));
        if (!Double.isFinite(newX)) {
            newX = robot.getX() + velocity * duration * Math.cos(robot.getDirection());
        }

        double newY = robot.getY() - velocity / angularVel *
                (Math.cos(robot.getDirection()  + angularVel * duration) - Math.cos(robot.getDirection()));
        if (!Double.isFinite(newY)) {
            newY = robot.getY() + velocity * duration * Math.sin(robot.getDirection());
        }

        robot.setPosition(newX, newY);
        robot.setDirection(asNormalizedRadians(robot.getDirection() + angularVel * duration));
    }
}
