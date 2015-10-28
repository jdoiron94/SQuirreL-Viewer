package com.sqlv.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Jacob
 * @since 10/28/2015
 */
public class SquirrelLogin extends JFrame {

    private int status;

    private String username;
    private String password;

    public static final String PASSWORD_MESSAGE = "Your password must contain at least one alphanumeric character or symbol.";
    public static final String USERNAME_MESSAGE = "Your username must contain at least one alphanumeric character or symbol.";

    public static char PASS_SHOWING = (char) 0;
    public static char PASS_HIDDEN = '\u25CF';

    public static int STATUS_CLOSED = 1;

    /**
     * Constructs a login interface containing containers for username and password input.
     */
    public SquirrelLogin() {
        super("Squirrel details");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel top = new JPanel(new GridLayout(2, 2, 50, 15));
        JLabel userLabel = new JLabel("Username:");
        top.add(userLabel);
        JTextField userField = new JTextField();
        userField.setPreferredSize(new Dimension(100, 20));
        top.add(userField);
        JLabel passLabel = new JLabel("Password:");
        top.add(passLabel);
        JPasswordField passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(100, 20));
        top.add(passField);
        JPanel mid = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        JCheckBox show = new JCheckBox("Show password");
        show.addActionListener(e -> passField.setEchoChar(show.isSelected() ? PASS_SHOWING : PASS_HIDDEN));
        mid.add(show);
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.LINE_AXIS));
        JButton submit = new JButton("Sign in");
        submit.addActionListener(e -> {
            username = userField.getText();
            char[] p = passField.getPassword();
            if (username != null && !username.isEmpty()) {
                username = username.trim();
                if (p != null && p.length > 0) {
                    password = new String(p).trim();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, PASSWORD_MESSAGE, "Password conflict", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, USERNAME_MESSAGE, "Username conflict", JOptionPane.WARNING_MESSAGE);
            }
        });
        bottom.add(Box.createHorizontalGlue());
        bottom.add(submit);
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> {
            status = STATUS_CLOSED;
            dispose();
        });
        bottom.add(Box.createHorizontalStrut(15));
        bottom.add(cancel);
        JPanel master = new JPanel();
        master.setLayout(new BoxLayout(master, BoxLayout.PAGE_AXIS));
        master.add(Box.createVerticalStrut(15));
        master.add(top);
        master.add(Box.createVerticalStrut(15));
        master.add(mid);
        master.add(Box.createVerticalStrut(15));
        master.add(bottom);
        master.add(Box.createVerticalStrut(15));
        add(Box.createHorizontalStrut(15));
        add(master, BorderLayout.NORTH);
        add(Box.createHorizontalStrut(15));
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                status = STATUS_CLOSED;
            }
        });
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

    /**
     * @return <t>true</t> if the panel was closed; otherwise, <t>false</t>.
     */
    public boolean closed() {
        return status == STATUS_CLOSED;
    }
}
