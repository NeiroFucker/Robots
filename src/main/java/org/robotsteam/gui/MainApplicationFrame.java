package org.robotsteam.gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import org.robotsteam.log.Logger;

public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    
    public MainApplicationFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int inset = 50;
        setBounds(inset, inset,
            screenSize.width - 2*inset,
            screenSize.height - 2*inset
        );

        setContentPane(desktopPane);

        addWindow(initLogWindow());
        addWindow(initGameWindow());

        setJMenuBar(new MenuBar(this));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainApplicationFrame.this.confirmWindowClose();
            }
        });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    public void confirmWindowClose() {
        if (JOptionPane.showConfirmDialog(this,
                "Вы действительно хотите закрыть приложение?", "Закрыть?",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        ) == JOptionPane.YES_OPTION) { System.exit(0); }
    }

    protected LogWindow initLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());

        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);

        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");

        return logWindow;
    }

    protected GameWindow initGameWindow() {
        GameWindow gameWindow = new GameWindow();

        gameWindow.setLocation(300, 100);
        gameWindow.setSize(400,  400);

        return gameWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}
