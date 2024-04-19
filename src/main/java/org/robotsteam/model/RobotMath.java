package org.robotsteam.model;

import java.awt.geom.Point2D;

public class RobotMath {
    static final double maxVelocity = 0.1;
    static final double maxAngularVelocity = 0.001;
    private static final double maxDeviation = maxVelocity / maxAngularVelocity;

    public static double countAngularVelocity(Point2D target, RobotState robot) {
        double result = 0;
        double angleToTarget = angleTo(robot.getPosition(), target);
        double delta = asNormalizedRadians(angleToTarget - robot.getDirection());

        if (delta < Math.PI) result = maxAngularVelocity;
        if (delta > Math.PI) result = -maxAngularVelocity;
        if (canNotReachTarget(target, robot)) result = 0;

        return result;
    }

    public static Point2D countNewPosition(RobotState robot, double vel, double angVel, double t) {
        double d = robot.getDirection();
        double newX = robot.getX() + vel / angVel * (Math.sin(d + angVel * t) - Math.sin(d));
        double newY = robot.getY() - vel / angVel * (Math.cos(d + angVel * t) - Math.cos(d));

        if (!Double.isFinite(newX)) newX = robot.getX() + vel * t * Math.cos(d);
        if (!Double.isFinite(newY)) newY = robot.getY() + vel * t * Math.sin(d);

        return new Point2D.Double(newX, newY);
    }

    public static double applyLimits(double value, double min, double max) {
        if (value < min) return min;
        return Math.min(value, max);
    }

    public static double asNormalizedRadians(double angle) {
        while (angle < 0)
            angle += 2*Math.PI;

        while (angle >= 2*Math.PI)
            angle -= 2*Math.PI;

        return angle;
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(Point2D from, Point2D target) {
        double diffX = target.getX() - from.getX();
        double diffY = target.getY() - from.getY();

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private static boolean canNotReachTarget(Point2D target, RobotState robot) {
        double dx = target.getX() - robot.getX();
        double dy = target.getY() - robot.getY();

        double newDx = Math.cos(robot.getDirection()) * dx + Math.sin(robot.getDirection()) * dy;
        double newDY = Math.cos(robot.getDirection()) * dy - Math.sin(robot.getDirection()) * dx;

        return !(distance(newDx, newDY, 0, maxDeviation) > maxDeviation) ||
                !(distance(newDx, newDY + maxDeviation, 0, 0) > maxDeviation);
    }
}
