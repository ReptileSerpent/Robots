package gui;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Target
{
    private static volatile int _positionX = 150;
    public static int getPositionX()
    {
        return _positionX;
    }
    public static void setPositionX(int positionX)
    {
        _positionX = positionX;
    }

    private static volatile int _positionY = 100;
    public static int getPositionY()
    {
        return _positionY;
    }
    public static void setPositionY(int positionY)
    {
        _positionY = positionY;
    }

    public static void setPosition(Point p)
    {
        setPositionX(p.x);
        setPositionY(p.y);
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