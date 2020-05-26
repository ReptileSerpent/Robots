package gui;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import static gui.GameMath.*;

public class GameVisualizer extends JPanel
{
    private RobotController robotController;

    private final Timer m_timer = initTimer();
    
    private static Timer initTimer() 
    {
        Timer timer = new Timer("events generator", true);
        return timer;
    }
    
    public GameVisualizer()
    {
        this.robotController = RobotController.getInstance();

        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onRedrawEvent();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onModelUpdateEvent();
            }
        }, 0, 10);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                Target.setPosition(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }
    
    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }

    private static double distance(double x1, double y1, double x2, double y2)
    {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }
    
    private static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;
        
        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }
    
    protected void onModelUpdateEvent()
    {
        double distance = distance(Target.getPositionX(), Target.getPositionY(),
                robotController.getPositionX(), robotController.getPositionY());
        if (distance < 0.5)
        {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = angleTo(robotController.getPositionX(), robotController.getPositionY(),
                Target.getPositionX(), Target.getPositionY());
        double angularVelocity = 0;
        if (angleToTarget > robotController.getDirection() + 0.01)      // accounting for direction error
        {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < robotController.getDirection() - 0.01)      // accounting for direction error
        {
            angularVelocity = -maxAngularVelocity;
        }

        robotController.move(velocity, angularVelocity, 10);
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g; 
        robotController.draw(g2d);
        Target.draw(g2d, Target.getPositionX(), Target.getPositionY());
    }
}
