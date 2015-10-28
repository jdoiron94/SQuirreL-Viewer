package com.sqlv.ui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Dimension;

/**
 * @author Jacob Doiron
 * @since 10/27/2015
 */
public class SquirrelFrame extends JFrame {

    private String user;
    private String pass;

    public SquirrelFrame(String user, String pass) {
        super("SQuirreL Viewer");
        this.user = user;
        this.pass = pass;
        setPreferredSize(new Dimension(800, 480));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * @return The entered username.
     */
    public String getUser() {
        return user;
    }

    /**
     * @return The entered password.
     */
    public String getPass() {
        return pass;
    }
}
