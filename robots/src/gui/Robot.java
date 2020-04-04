package gui;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static gui.GameMath.*;

public final class Robot
{
    private Robot()
    {}

    private static volatile double _positionX = 100;
    public static double getPositionX()
    {
        return _positionX;
    }
    public static void setPositionX(double positionX)
    {
        _positionX = positionX;
    }

    private static volatile double _positionY = 100;
    public static double getPositionY()
    {
        return _positionY;
    }
    public static void setPositionY(double positionY)
    {
        _positionY = positionY;
    }

    private static volatile double _direction = 0;
    public static double getDirection()
    {
        return _direction;
    }
    public static void setDirection(double direction)
    {
        _direction = direction;
    }

    public static void move(double velocity, double angularVelocity, double duration)
    {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = getPositionX() + velocity / angularVelocity *
                (Math.sin(getDirection() + angularVelocity * duration) -
                        Math.sin(getDirection()));
        if (!Double.isFinite(newX))
        {
            newX = getPositionX() + velocity * duration * Math.cos(getDirection());
        }
        double newY = getPositionY() - velocity / angularVelocity *
                (Math.cos(getDirection() + angularVelocity * duration) -
                        Math.cos(getDirection()));
        if (!Double.isFinite(newY))
        {
            newY = getPositionY() + velocity * duration * Math.sin(getDirection());
        }
        setPositionX(newX);
        setPositionY(newY);
        double newDirection = asNormalizedRadians(getDirection() + angularVelocity * duration);
        setDirection(newDirection);
    }

    public static void draw(Graphics2D g, int x, int y, double direction)
    {
        int robotCenterX = round(getPositionX());
        int robotCenterY = round(getPositionY());
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        Oval.fill(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        Oval.draw(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        Oval.fill(g, robotCenterX  + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        Oval.draw(g, robotCenterX  + 10, robotCenterY, 5, 5);
    }
}
