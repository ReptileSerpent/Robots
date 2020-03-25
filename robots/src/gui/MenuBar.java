package gui;

import log.Logger;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class MenuBar
{
    public static JMenuBar generateMenuBar(JFrame frame)
    {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(generateApplicationMenu(frame));
        menuBar.add(generateLookAndFeelMenu(frame));
        menuBar.add(generateTestMenu(frame));

        return menuBar;
    }

    private static JMenu generateApplicationMenu(JFrame frame)
    {
        JMenu applicationMenu = generateMenu("Приложение", "Управление приложением", KeyEvent.VK_A);

        applicationMenu.add(generateMenuItem("Закрыть", KeyEvent.VK_C, (event) ->
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING))));

        return applicationMenu;
    }

    private static JMenu generateLookAndFeelMenu(JFrame frame)
    {
        JMenu lookAndFeelMenu = generateMenu("Режим отображения", "Управление режимом отображения приложения", KeyEvent.VK_V);

        lookAndFeelMenu.add(generateMenuItem("Системная схема", KeyEvent.VK_S, (event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName(), frame);
            frame.invalidate();
        }));

        lookAndFeelMenu.add(generateMenuItem("Универсальная схема", KeyEvent.VK_S, (event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName(), frame);
            frame.invalidate();
        }));

        return lookAndFeelMenu;
    }

    private static JMenu generateTestMenu(JFrame frame)
    {
        JMenu testMenu = generateMenu("Тесты", "Тестовые команды", KeyEvent.VK_T);

        testMenu.add(generateMenuItem("Сообщение в лог", KeyEvent.VK_S, (event) -> {
            Logger.debug("Новая строка");
        }));

        return testMenu;
    }

    private static JMenu generateMenu(String title, String description, int mnemonic)
    {
        JMenu menu = new JMenu(title);
        menu.setMnemonic(mnemonic);
        menu.getAccessibleContext().setAccessibleDescription(description);

        return menu;
    }

    private static JMenuItem generateMenuItem(String title, int mnemonic, ActionListener actionListener)
    {
        JMenuItem menuItem = new JMenuItem(title, mnemonic);
        menuItem.addActionListener(actionListener);

        return menuItem;
    }

    private static void setLookAndFeel(String className, JFrame frame)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(frame);
        }
        catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }

}
