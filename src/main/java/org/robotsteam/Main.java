package org.robotsteam;

import org.robotsteam.gui.MainApplicationFrame;

import java.awt.Frame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main
{
    public static void main(String[] args) {
        try {
            UIManager.put("OptionPane.yesButtonText", "Да");
            UIManager.put("OptionPane.noButtonText", "Нет");
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) { e.printStackTrace(System.out); }

        SwingUtilities.invokeLater(() -> {
            MainApplicationFrame frame = new MainApplicationFrame();
            frame.pack(); frame.setVisible(true);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);}
        );
    }
}
