package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

import javax.swing.*;

import log.Logger;

public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    
    public MainApplicationFrame()
    {
        super("Робот");
        int inset = 50;
        Dimension screenSize = new Dimension(500, 500); //Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setContentPane(desktopPane);

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        setJMenuBar(MenuBar.generateMenuBar(this));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                promptAboutWindowClosing();
            }
        });
    }

    private void promptAboutWindowClosing()
    {
        int userChoice = JOptionPane.showOptionDialog(null,
                "Закрыть приложение?",
                "Подтверждение закрытия",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[] { "Отмена", "Закрыть" },
                "Отмена");

        switch (userChoice)
        {
            case 0:     //cancel
                break;
            case 1:     //exit
                System.exit(0);
                break;
        }
    }

    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}
