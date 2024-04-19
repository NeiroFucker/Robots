package org.robotsteam.model;


public class RobotMath {
    static final double maxVelocity = 0.1;
    static final double maxAngularVelocity = 0.001;
    private static final double maxDeviation = maxVelocity / maxAngularVelocity;

    public static double countAngularVelocity(double curX, double curY, double targetX, double targetY, double direction) {
        double result = 0;
        double angleToTarget = angleTo(curX, curY, targetX, targetY);
        double delta = asNormalizedRadians(angleToTarget - direction);

        if (delta < Math.PI) result = maxAngularVelocity;
        if (delta > Math.PI) result = -maxAngularVelocity;
        if (canNotReachTarget(curX, curY, targetX, targetY, direction)) result = 0;

        return result;
    }

    private static boolean canNotReachTarget(double curX, double curY, double targetX, double targetY, double direction) {
        double dx = targetX - curX;
        double dy = targetY - curY;

        double newDx = Math.cos(direction) * dx + Math.sin(direction) * dy;
        double newDY = Math.cos(direction) * dy - Math.sin(direction) * dx;

        return !(distance(newDx, newDY, 0, maxDeviation) > maxDeviation) ||
               !(distance(newDx, newDY + maxDeviation, 0, 0) > maxDeviation);
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

    public static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }
}
