package com.sqlv.ui;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Image;
import java.net.URL;

/**
 * @author Jacob Doiron
 * @since 10/27/2015
 */
public class SquirrelFrame extends JFrame {

    private String username;
    private String password;
    private Image icon;

    private boolean passSelected;

    /**
     * Constructs a SquirrelFrame and sets the user and pass fields.
     *
     * @param username The username to be set.
     * @param password The password to be set.
     * @param passSelected Whether or not to show the password.
     */
    public SquirrelFrame(String username, String password, boolean passSelected) {
        super("SQuirreL Viewer");
        this.username = username;
        this.password = password;
        this.passSelected = passSelected;
        if (icon == null) {
            System.out.println("acquiring squirrel icon");
            Class clazz = getClass();
            ClassLoader loader = clazz.getClassLoader();
            URL url = loader.getResource("./icons/squirrel.png");
            if (url != null) {
                icon = new ImageIcon(url).getImage();
            }
        }
        setPreferredSize(new Dimension(800, 480));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem login = new JMenuItem("Login");
        login.addActionListener(e -> {
            SquirrelLogin sql = new SquirrelLogin(this.username, this.password, this.passSelected);
            sql.setVisible(true);
            this.username = sql.getUsername();
            this.password = sql.getPassword();
            this.passSelected = sql.isPassSelected();
        });
        file.add(login);
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        file.add(exit);
        bar.add(file);
        setJMenuBar(bar);
        pack();
        if (icon != null) {
            setIconImage(icon);
        }
        setLocationRelativeTo(null);
    }

    /**
     * @return The entered username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return The entered password.
     */
    public String getPassword() {
        return password;
    }
}
