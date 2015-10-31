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

    /**
     * Driver for SQuirreL Viewer.
     *
     * @param args Command line arguments to run.
     */
    public static void main(String... args) {
        if (OperatingSystem.getSystem() == OperatingSystem.MAC) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        } else {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                System.err.println("Could not set system look and feel.");
            }
        }
        EventQueue.invokeLater(() -> {
            SquirrelLogin login = new SquirrelLogin(null, null, null, false);
            login.setVisible(true);
            if (!login.closed()) {
                String username = login.getUsername();
                String password = login.getPassword();
                boolean showing = login.isPassSelected();
                SquirrelFrame frame = new SquirrelFrame(username, password, showing);
                frame.setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }
}
