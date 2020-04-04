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
    private final Timer m_timer = initTimer();
    
    private static Timer initTimer() 
    {
        Timer timer = new Timer("events generator", true);
        return timer;
    }
    
    public GameVisualizer() 
    {
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
                Robot.getPositionX(), Robot.getPositionY());
        if (distance < 0.5)
        {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = angleTo(Robot.getPositionX(), Robot.getPositionY(),
                Target.getPositionX(), Target.getPositionY());
        double angularVelocity = 0;
        if (angleToTarget > Robot.getDirection())
        {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < Robot.getDirection())
        {
            angularVelocity = -maxAngularVelocity;
        }
        
        Robot.move(velocity, angularVelocity, 10);
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g; 
        Robot.draw(g2d, round(Robot.getPositionX()), round(Robot.getPositionY()), Robot.getDirection());
        Target.draw(g2d, Target.getPositionX(), Target.getPositionY());
    }
}
