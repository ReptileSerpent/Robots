package gui;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static gui.GameMath.round;

public class RobotView
{
    public void draw(Graphics2D g, RobotModel robotModel)
    {
        int robotCenterX = round(robotModel.getPositionX());
        int robotCenterY = round(robotModel.getPositionY());
        AffineTransform t = AffineTransform.getRotateInstance(robotModel.getDirection(), robotCenterX, robotCenterY);
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
