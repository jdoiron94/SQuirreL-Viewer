package com.sqlv.ui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jacob
 * @since 10/28/2015
 */
public class SquirrelLogin extends JFrame {

    private String username;
    private String password;

    /**
     * Constructs a login interface containing containers for username and password input.
     */
    public SquirrelLogin() {
        super("Squirrel details");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel top = new JPanel(new GridLayout(2, 2, 50, 15));
        JLabel userLabel = new JLabel("Username:");
        top.add(userLabel);
        JTextField userField = new JTextField();
        userField.setPreferredSize(new Dimension(100, 20));
        top.add(userField);
        JLabel passLabel = new JLabel("Password:");
        top.add(passLabel);
        JTextField passField = new JTextField();
        passField.setPreferredSize(new Dimension(100, 20));
        top.add(passField);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            username = userField.getText().trim();
            password = passField.getText().trim();
            dispose();
        });
        bottom.add(submit);
        JPanel master = new JPanel();
        master.setLayout(new BoxLayout(master, BoxLayout.Y_AXIS));
        master.add(Box.createVerticalStrut(15));
        master.add(top);
        master.add(Box.createVerticalStrut(15));
        master.add(bottom);
        master.add(Box.createVerticalStrut(15));
        add(Box.createHorizontalStrut(15));
        add(master, BorderLayout.NORTH);
        add(Box.createHorizontalStrut(15));
        pack();
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
