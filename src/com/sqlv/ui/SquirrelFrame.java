package com.sqlv.ui;

import com.sqlv.Relvar;
import com.sqlv.api.util.IO;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacob Doiron
 * @since 10/27/2015
 */
public class SquirrelFrame extends JFrame {

    private String username;
    private String password;
    private Image icon;

    private boolean passSelected;

    private final JTabbedPane pane;

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
        this.pane = new JTabbedPane();
        if (icon == null) {
            System.out.println("acquiring squirrel icon");
            Class clazz = getClass();
            ClassLoader loader = clazz.getClassLoader();
            URL url = loader.getResource("./icons/squirrel.png");
            if (url != null) {
                icon = new ImageIcon(url).getImage();
            }
            URL u = loader.getResource("./School.sql");
            if (u != null) {
                try {
                    URI schoolUri = u.toURI();
                    File schoolDb = new File(schoolUri);
                    if (schoolDb.exists()) {
                        byte[] database = IO.readFile(schoolDb);
                        if (database != null) {
                            String text = new String(database);
                            loadDatabase(text);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        setPreferredSize(new Dimension(800, 480));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem login = new JMenuItem("Login");
        login.addActionListener(e -> {
            SquirrelLogin sql = new SquirrelLogin(this, this.username, this.password, this.passSelected);
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
        add(pane);
        pack();
        if (icon != null) {
            setIconImage(icon);
        }
        setLocationRelativeTo(null);
    }

    /**
     * Loads up the school database from the SQL file, appropriately setting the relvars up.
     *
     * @param text The String representation of the SQL file.
     */
    public void loadDatabase(String text) {
        String[] split = text.split("\\n");
        boolean found = false;
        List<Relvar> relvars = new ArrayList<>();
        Relvar relvar = null;
        for (String s : split) {
            if (s.contains("CREATE")) {
                String[] stripped = s.split("`");
                String table = stripped[1].split("`")[0];
                Relvar r = new Relvar(table);
                relvar = r;
                relvars.add(r);
                found = true;
            } else if (found) {
                if (s.contains("PRIMARY")) {
                    String[] stripped = s.split("`");
                    String key = stripped[1].split("`")[0];
                    relvar.setKey(key);
                    found = false;
                } else {
                    String[] stripped = s.split("`");
                    String attribute = stripped[1].split("`")[0];
                    relvar.addAttribute(attribute);
                }
            }
        }
        for (Relvar r : relvars) {
            System.out.println("TABLE: " + r.getTitle());
            JTable table = new JTable(new Object[0][0], r.getAttributes().toArray(new String[r.getAttributes().size()]));
            JScrollPane panel = new JScrollPane(table);
            Tab tab = new Tab(r.getTitle(), table);
            pane.addTab(tab.getTitle(), panel);
            for (String s : r.getAttributes()) {
                System.out.println("\tATTRIBUTE: " + s);
            }
            System.out.println("\tKEY: " + r.getKey());
        }
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
