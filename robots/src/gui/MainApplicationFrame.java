package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Observer;

import javax.swing.*;

import log.Logger;

public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    
    public MainApplicationFrame()
    {
        super("Робот");
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setContentPane(desktopPane);
        setJMenuBar(MenuBar.generateMenuBar(this));

        RobotModel robotModel = new RobotModel();
        RobotView robotView = new RobotView();
        RobotController robotController = RobotController.getInstance();
        robotController.setRobotModel(robotModel);
        robotController.setRobotView(robotView);

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        RobotInformationWindow robotInformationWindow = new RobotInformationWindow();
        addWindow(robotInformationWindow);

        robotModel.addObserver(robotInformationWindow);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                promptAboutWindowClosing();
            }
        });

        TryToReadFrameStatesFromFile();
    }

    private void TryToReadFrameStatesFromFile()
    {
        try
        {
            var frameStates = DesktopPaneState.ReadFromFile(desktopPane);
            DesktopPaneState.ModifyDesktopPaneState(desktopPane, frameStates);
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при чтении файла конфигурации: " + e + "\nПосле нажатия ОК вы сможете продолжить работу. Будет загружена конфигурация по умолчанию.",
                    "Ошибка при чтении файла конфигурации",
                    JOptionPane.WARNING_MESSAGE);
        }
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
            {
                var shouldExit = true;
                try
                {
                    DesktopPaneState.WriteToFile(desktopPane);
                }
                catch (IOException e)
                {
                    shouldExit = askAboutClosingAfterConfigWriteFailure(e);
                }
                if (shouldExit)
                    System.exit(0);
                break;
            }
        }
    }

    private boolean askAboutClosingAfterConfigWriteFailure(IOException e)
    {
        var message = "Ошибка при записи файла конфигурации: " + e + "\nВы можете завершить закрытие приложения или отменить его и попытаться устранить проблему с сохранением.";

        int userChoice = JOptionPane.showOptionDialog(null,
                message,
                "Ошибка при записи файла конфигурации",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                new String[] { "Отмена", "Закрыть" },
                "Отмена");

        switch (userChoice)
        {
            case 0:     //cancel
                return false;
            case 1:     //exit
                return true;
            default:
                return false;
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
