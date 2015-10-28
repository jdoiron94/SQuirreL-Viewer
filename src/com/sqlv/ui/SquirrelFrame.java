package com.sqlv.ui;

import javax.swing.*;
import java.awt.Dimension;

/**
 * @author Jacob Doiron
 * @since 10/27/2015
 */
public class SquirrelFrame extends JFrame {

    private String user;
    private String pass;

    /**
     * Constructs a SquirrelFrame and sets the user and pass fields.
     * @param user The username to be set.
     * @param pass The password to be set.
     */
    public SquirrelFrame(String user, String pass) {
        super("SQuirreL Viewer");
        this.user = user;
        this.pass = pass;
        setPreferredSize(new Dimension(800, 480));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem login = new JMenuItem("Login");
        login.addActionListener(e -> {
            SquirrelLogin sql = new SquirrelLogin();
            sql.setVisible(true);
        });
        file.add(login);
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        file.add(exit);
        bar.add(file);
        setJMenuBar(bar);
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
