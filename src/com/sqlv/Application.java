package com.sqlv;

import com.sqlv.api.util.OperatingSystem;
import com.sqlv.ui.SquirrelFrame;
import com.sqlv.ui.SquirrelLogin;

import javax.swing.UIManager;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Jacob Doiron
 * @since 10/27/2015
 */
public class Application {

    public static Connection getConnection() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/StudentDatabase";
        String user = "root";
        String pass = "123456";
        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String... args) {
        //getConnection();
        if (OperatingSystem.getSystem() == OperatingSystem.MAC) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        } else {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                System.err.println("Could not set system look and feel.");
            }
        }
        SquirrelLogin login = new SquirrelLogin();
        login.setVisible(true);
        while (login.isVisible()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        EventQueue.invokeLater(() -> {
            SquirrelFrame frame = new SquirrelFrame(login.getUsername(), login.getPassword());
            frame.setVisible(true);
            System.out.println("user: " + login.getUsername() + ", pass: " + login.getPassword());
        });
    }
}
