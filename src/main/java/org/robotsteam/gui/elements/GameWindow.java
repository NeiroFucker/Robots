package org.robotsteam.gui.elements;

import org.robotsteam.gui.states.FrameState;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import java.io.Serializable;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame implements Serializable {
    private final GameVisualizer m_visualizer;
    public GameWindow() {
        super("Игровое поле", true, true, true, true);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public GameWindow(FrameState state) {
        this();

        this.setSize(state.getSize());
        this.setLocation(state.getLocation());

        try { this.setIcon(state.getMinimized()); }
        catch (PropertyVetoException e) { e.printStackTrace(System.out); }
    }
}
