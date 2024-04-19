package org.robotsteam.gui.elements;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.beans.PropertyVetoException;
import java.io.Serializable;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.robotsteam.gui.states.FrameState;
import org.robotsteam.log.LogChangeListener;
import org.robotsteam.log.LogEntry;
import org.robotsteam.log.LogWindowSource;

public class LogWindow extends JInternalFrame implements LogChangeListener, Serializable {
    private TextArea logContent;
    private LogWindowSource logSource;

    public LogWindow() { }

    public LogWindow(LogWindowSource logSource) {
        super("Протокол работы", true, true, true, true);
        this.logSource = logSource;
        this.logSource.registerListener(this);
        logContent = new TextArea("");
        logContent.setSize(200, 500);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack(); updateLogContent();
    }

    public LogWindow(LogWindowSource logSource, FrameState state) {
        this(logSource);

        this.setSize(state.getSize());
        this.setLocation(state.getLocation());

        try { this.setIcon(state.getMinimized()); }
        catch (PropertyVetoException e) { e.printStackTrace(System.out); }
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();

        for (LogEntry entry : logSource.all())
            content.append(entry.getMessage()).append("\n");

        logContent.setText(content.toString()); logContent.invalidate();
    }
    
    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }
}
