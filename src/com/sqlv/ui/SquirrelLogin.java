package com.sqlv.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

/**
 * @author Jacob
 * @since 10/28/2015
 */
public class SquirrelLogin extends JDialog {

    private int status;
    private boolean passSelected;

    private String username;
    private String password;

    private final JFrame owner;

    private static final String PASSWORD_MESSAGE = "Your password must contain at least one alphanumeric character or symbol.";
    private static final String USERNAME_MESSAGE = "Your username must contain at least one alphanumeric character or symbol.";

    private static final char PASS_SHOWING = (char) 0;
    private static final char PASS_HIDDEN = '\u25CF';

    private static final int STATUS_CLOSED = 1;

    private static Image icon;

    /**
     * /**
     * Constructs a login interface containing containers for username and password input.
     *
     * @param owner The frame which owns this object.
     * @param username The default username to set.
     * @param password The default password to set.
     * @param passSelected Whether or not to show the password.
     */
    public SquirrelLogin(JFrame owner, String username, String password, boolean passSelected) {
        this.owner = owner;
        this.username = username;
        this.password = password;
        this.passSelected = passSelected;
        if (icon == null) {
            System.out.println("acquiring lock icon");
            Class clazz = getClass();
            ClassLoader loader = clazz.getClassLoader();
            URL url = loader.getResource("./icons/lock.png");
            if (url != null) {
                icon = new ImageIcon(url).getImage();
            }
        }
        setTitle("Squirrel details");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel top = new JPanel(new GridLayout(2, 2, 50, 15));
        JLabel userLabel = new JLabel("Username:");
        top.add(userLabel);
        JTextField userField = new JTextField();
        if (username != null) {
            userField.setText(username);
        }
        userField.setPreferredSize(new Dimension(100, 20));
        top.add(userField);
        JLabel passLabel = new JLabel("Password:");
        top.add(passLabel);
        JPasswordField passField = new JPasswordField();
        if (password != null) {
            passField.setText(password);
        }
        passField.setPreferredSize(new Dimension(100, 20));
        top.add(passField);
        JPanel mid = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        JCheckBox show = new JCheckBox("Show password");
        show.setSelected(passSelected);
        if (passSelected) {
            passField.setEchoChar(PASS_SHOWING);
        }
        show.addActionListener(e -> passField.setEchoChar(show.isSelected() ? PASS_SHOWING : PASS_HIDDEN));
        mid.add(show);
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.LINE_AXIS));
        JButton submit = new JButton("Sign in");
        submit.addActionListener(e -> {
            this.username = userField.getText().replaceAll("\\s+", "");
            this.password = new String(passField.getPassword()).replaceAll("\\s+", "");
            this.passSelected = show.isSelected();
            if (this.username != null && !this.username.isEmpty()) {
                if (this.password != null && !this.password.isEmpty()) {
                    System.out.printf("user: %s, pass: %s\n", this.username, this.password);
                    dispose();
                } else {
                    this.password = null;
                    JOptionPane.showMessageDialog(this, PASSWORD_MESSAGE, "Password conflict", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                this.username = null;
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
        setModal(true);
        if (icon != null) {
            setIconImage(icon);
        }
        setResizable(false);
        setLocationRelativeTo(owner);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
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
     * @return <t>true</t> if the password is unmasked; otherwise, <t>false</t>.
     */
    public boolean isPassSelected() {
        return passSelected;
    }

    /**
     * @return <t>true</t> if the panel was closed; otherwise, <t>false</t>.
     */
    public boolean closed() {
        return status == STATUS_CLOSED;
    }
}
