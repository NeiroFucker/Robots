package org.robotsteam.gui.elements;

import org.robotsteam.gui.states.FrameState;
import org.robotsteam.model.Robot;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public class StateWindow extends JInternalFrame implements Serializable, Observer {
    private Robot robot;
    private JTextArea textField;

    public StateWindow() { }

    public StateWindow(Robot robot) {
        super("Окно состояния робота", true, true, true, true);

        JPanel panel = new JPanel(new BorderLayout());

        textField = new JTextArea();
        textField.setPreferredSize(new Dimension(100, 50));
        panel.add(textField, BorderLayout.CENTER);

        getContentPane().add(panel); pack();

        this.robot = robot;
        robot.addObserver(this);
    }

    public StateWindow(FrameState state, Robot robot) {
        this(robot);

        this.setSize(state.getSize());
        this.setLocation(state.getLocation());

        try { this.setIcon(state.getMinimized()); }
        catch (PropertyVetoException e) { e.printStackTrace(System.out); }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o.equals(robot)) {
            if (arg.equals(Robot.ROBOT_MOVED))
                onRobotMoved();
        }
    }

    private void onRobotMoved() {
        textField.setText(robot.info());
    }
}
