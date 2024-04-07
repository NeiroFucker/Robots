package org.robotsteam.gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;

import javax.swing.*;

import org.robotsteam.gui.elements.GameWindow;
import org.robotsteam.gui.elements.LogWindow;
import org.robotsteam.gui.elements.MenuBar;
import org.robotsteam.AppLoader;
import org.robotsteam.gui.states.AppState;
import org.robotsteam.gui.states.FrameState;
import org.robotsteam.log.Logger;

import org.robotsteam.model.Robot;

public class MainApplicationFrame extends JFrame implements Serializable {
    private final LogWindow logWindow;
    private final GameWindow gameWindow;
    private final JDesktopPane desktopPane = new JDesktopPane();

    private MainApplicationFrame(FrameState logWindowState, FrameState gameWindowState) {
        int inset = 50;
        this.setLocation(new Point(inset, inset));
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        Robot robot = new Robot(10, 10);

        logWindow = initLogWindow(logWindowState);
        gameWindow = new GameWindow(gameWindowState, robot);
        addWindow(logWindow); addWindow(gameWindow);

        setContentPane(desktopPane);

        setJMenuBar(new MenuBar(this));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainApplicationFrame.this.confirmWindowClose();
            }
        });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    public MainApplicationFrame() {
        this(
                new FrameState(new Dimension(300, 800), new Point(10,10), false),
                new FrameState(new Dimension(400,  400), new Point(300, 100), false)
        );
    }

    public MainApplicationFrame(AppState state) {
        this(state.getLogWindowState(), state.getGameWindowState());
    }

    public void confirmWindowClose() {
        if (JOptionPane.showConfirmDialog(this,
                "Вы действительно хотите закрыть приложение?", "Закрыть?",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        ) == JOptionPane.YES_OPTION) { AppLoader.serializeApp(dumpState()); System.exit(0); }
    }

    private AppState dumpState() {
        return new AppState(gameWindow, logWindow);
    }

    protected LogWindow initLogWindow(FrameState state) {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource(), state);

        Logger.debug("Протокол работает");
        setMinimumSize(logWindow.getSize()); logWindow.pack();

        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}
