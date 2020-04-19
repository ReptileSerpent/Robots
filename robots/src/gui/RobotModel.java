package gui;

import javafx.util.Pair;

import java.util.Observable;

import static gui.GameMath.*;

public class RobotModel extends Observable
{
    private volatile double _positionX = 100;
    public double getPositionX()
    {
        return _positionX;
    }
    public void setPositionX(double positionX)
    {
        _positionX = positionX;
    }

    private volatile double _positionY = 100;
    public double getPositionY()
    {
        return _positionY;
    }
    public void setPositionY(double positionY)
    {
        _positionY = positionY;
    }

    private volatile double _direction = 0;
    public double getDirection()
    {
        return _direction;
    }
    public void setDirection(double direction)
    {
        _direction = direction;
    }

    public void move(double velocity, double angularVelocity, double duration)
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

        setChanged();
        notifyObservers(new Pair<Double, Double>(newX, newY));
    }
}
