package gui;

public class GameMath
{
    public static final double maxVelocity = 0.1;
    public static final double maxAngularVelocity = 0.001;

    public static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    public static double asNormalizedRadians(double angle)
    {
        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle >= 2*Math.PI)
        {
            angle -= 2*Math.PI;
        }
        return angle;
    }

    public static int round(double value)
    {
        return (int)(value + 0.5);
    }
}
