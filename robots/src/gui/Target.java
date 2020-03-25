package gui;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Target
{
    public static volatile int m_positionX = 150;
    public static volatile int m_positionY = 100;

    public static void setPosition(Point p)
    {
        m_positionX = p.x;
        m_positionY = p.y;
    }

    public static void draw(Graphics2D g, int x, int y)
    {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        Oval.fill(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        Oval.draw(g, x, y, 5, 5);
    }
}