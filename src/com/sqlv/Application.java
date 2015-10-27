package com.sqlv;

import com.sqlv.api.util.OperatingSystem;
import com.sqlv.ui.SquirrelFrame;

import javax.swing.UIManager;
import java.awt.EventQueue;

/**
 * @author Jacob Doiron
 * @since 10/27/2015
 */
public class Application {

    public static void main(String... args) {
        EventQueue.invokeLater(() -> {
            if (OperatingSystem.getSystem() == OperatingSystem.MAC) {
                System.setProperty("apple.laf.useScreenMenuBar", "true");
            } else {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ignored) {
                    System.err.println("Could not set system look and feel.");
                }
            }
            new SquirrelFrame().setVisible(true);
        });
    }
}
