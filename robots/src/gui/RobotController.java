package gui;

import java.awt.*;

public class RobotController
{
    private static RobotController robotController = null;

    private RobotController() {}

    public static RobotController getInstance()
    {
        if (robotController == null)
            robotController = new RobotController();

        return robotController;
    }

    private RobotModel robotModel;
    private RobotView robotView;

    public void setRobotModel(RobotModel robotModel)
    {
        this.robotModel = robotModel;
    }
    public RobotModel getRobotModel()
    {
        return this.robotModel;
    }

    public void setRobotView(RobotView robotView)
    {
        this.robotView = robotView;
    }
    public RobotView getRobotView()
    {
        return this.robotView;
    }

    public double getPositionX()
    {
        return robotModel.getPositionX();
    }
    public void setPositionX(double positionX)
    {
        robotModel.setPositionX(positionX);
    }

    public double getPositionY()
    {
        return robotModel.getPositionY();
    }
    public void setPositionY(double positionY)
    {
        robotModel.setPositionY(positionY);
    }

    public double getDirection()
    {
        return robotModel.getDirection();
    }
    public void setDirection(double direction)
    {
        robotModel.setDirection(direction);
    }

    public void move(double velocity, double angularVelocity, double duration)
    {
        robotModel.move(velocity, angularVelocity, duration);
    }

    public void draw(Graphics2D g)
    {
        robotView.draw(g, robotModel);
    }

}