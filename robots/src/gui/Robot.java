package gui;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static gui.GameMath.*;

public class Robot
{
    public static volatile double m_positionX = 100;
    public static volatile double m_positionY = 100;
    public static volatile double m_direction = 0;

    public static void move(double velocity, double angularVelocity, double duration)
    {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = m_positionX + velocity / angularVelocity *
                (Math.sin(m_direction + angularVelocity * duration) -
                        Math.sin(m_direction));
        if (!Double.isFinite(newX))
        {
            newX = m_positionX + velocity * duration * Math.cos(m_direction);
        }
        double newY = m_positionY - velocity / angularVelocity *
                (Math.cos(m_direction + angularVelocity * duration) -
                        Math.cos(m_direction));
        if (!Double.isFinite(newY))
        {
            newY = m_positionY + velocity * duration * Math.sin(m_direction);
        }
        m_positionX = newX;
        m_positionY = newY;
        double newDirection = asNormalizedRadians(m_direction + angularVelocity * duration);
        m_direction = newDirection;
    }

    public static void draw(Graphics2D g, int x, int y, double direction)
    {
        int robotCenterX = round(m_positionX);
        int robotCenterY = round(m_positionY);
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
