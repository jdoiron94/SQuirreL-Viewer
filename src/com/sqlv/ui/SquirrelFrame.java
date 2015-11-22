package com.sqlv.ui;

import com.sqlv.Query;
import com.sqlv.Relvar;
import com.sqlv.api.util.IO;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Jacob Doiron
 * @since 10/27/2015
 */
public class SquirrelFrame extends JFrame {

    private String username;
    private String password;
    private Image icon;

    private boolean passSelected;

    private final JPanel panel;
    private final JTabbedPane pane;
    private final JComboBox<String> queryBox;
    private final List<Relvar> relvars;
    private final List<Query> queries;
    private final List<Tab> tabs;

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
        this.panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        this.pane = new JTabbedPane();
        this.queryBox = new JComboBox<>();
        this.relvars = new ArrayList<>();
        this.queries = new ArrayList<>();
        this.tabs = new ArrayList<>();
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
            URL u2 = loader.getResource("./School_query.sql");
            if (u2 != null) {
                try {
                    URI queryUri = u2.toURI();
                    File queries = new File(queryUri);
                    if (queries.exists()) {
                        byte[] bytes = IO.readFile(queries);
                        if (bytes != null) {
                            String text = new String(bytes);
                            loadQueries(text);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
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
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS));
        left.add(pane);
        left.add(panel);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
        add(left);
        pack();
        if (icon != null) {
            setIconImage(icon);
        }
        setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * Loads up the school database from the SQL file, appropriately setting the relvars up.
     *
     * @param text The String representation of the SQL file.
     */
    private void loadDatabase(String text) {
        String[] split = text.split("\\n");
        boolean found = false;
        List<String> attributes = new ArrayList<>();
        String title = null;
        Relvar relvar = null;
        for (String s : split) {
            if (s.contains("CREATE")) {
                String[] stripped = s.split("`");
                title = stripped[1].split("`")[0];
                attributes = new ArrayList<>();
                found = true;
            } else if (found) {
                if (s.contains("PRIMARY")) {
                    String[] stripped = s.split("`");
                    String key = stripped[1].split("`")[0];
                    relvar = new Relvar(title, key);
                    attributes.forEach(relvar::addAttribute);
                    relvars.add(relvar);
                    found = false;
                } else {
                    String[] stripped = s.split("`");
                    String attribute = stripped[1].split("`")[0];
                    if (attributes.size() == 0) {
                        attributes.add("<html><b>" + attribute + "</b></html>");
                        continue;
                    }
                    attributes.add(attribute);
                }
            } else if (s.contains("INSERT")) {
                String stripped = s.split("VALUES ")[1];
                String[] tuples = stripped.split("\\),");
                int tupleCount = count(')', stripped);
                String[][] data = new String[tupleCount][1];
                for (int i = 0; i < tupleCount; i++) {
                    String[] x = tuples[i].replace("(", "").replace(");", "").split(",");
                    for (int j = 0; j < x.length; j++) {
                        x[j] = x[j].replace("'", "");
                    }
                    data[i] = x;
                }
                if (relvar != null) {
                    relvar.setData(data);
                }
            }
        }
        setupDatabase();
    }

    /**
     * Sets up the JTable for each tab.
     */
    private void setupDatabase() {
        for (Relvar r : relvars) {
            NonEditableModel model = new NonEditableModel(r.getData(),
                    r.getAttributes().toArray(new String[r.getAttributes().size()]));
            JTable table = new JTable(model);
            table.getTableHeader().setReorderingAllowed(false);
            JScrollPane panel = new JScrollPane(table);
            panel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            panel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            Tab tab = new Tab(r.getTitle(), table, r);
            tabs.add(tab);
            pane.addTab(tab.getTitle(), panel);
        }
        pane.setPreferredSize(new Dimension(500, 200));
    }

    /**
     * Loads up the queries from the SQL file and then fills in the JComboBox.
     *
     * @param text The String representation of the SQL file.
     */
    private void loadQueries(String text) {
        String[] split = text.split("\\n");
        for (String s : split) {
            s = s.replaceAll("\\s+", " ").trim();
            if (s.isEmpty()) {
                continue;
            }
            boolean found = false;
            boolean search = false;
            String searchAttribute = null;
            String[] skips = {"select", "from"};
            List<String> attributes = new ArrayList<>();
            AtomicReference<String> table = new AtomicReference<>();
            AtomicReference<String> searchValue = new AtomicReference<>();
            String[] separated = s.replace(",", "").replace("'", "").replace(";", "").replace("=", "").split(" ");
            outer: for (String x : separated) {
                if (s.isEmpty()) {
                    continue;
                } else if (search) {
                    searchAttribute = x;
                    search = false;
                    found = true;
                    continue;
                } else if (found) {
                    searchValue.set(x);
                    break;
                }
                for (String sk : skips) {
                    if (x.equalsIgnoreCase(sk)) {
                        continue outer;
                    }
                }
                if (x.equalsIgnoreCase("where")) {
                    search = true;
                    continue;
                }
                relvars.forEach(r -> {
                    r.getAttributes().forEach(a -> {
                        if (x.equalsIgnoreCase(a.replaceAll("<.*?>", "")) && !attributes.contains(x)) {
                            attributes.add(x);
                        }
                    });
                    if (r.getTitle().equalsIgnoreCase(x)) {
                        table.set(r.getTitle());
                    }
                });
            }
            AtomicReference<Relvar> relvar = new AtomicReference<>();
            relvars.forEach(r -> {
                if (r.getTitle().equals(table.get())) {
                    relvar.set(r);
                }
            });
            queries.add(new Query(s, table.get(), searchAttribute, searchValue.get(), relvar.get(), attributes));
            System.out.println("Query added: " + s);
            System.out.println("\tTable: " + table);
            System.out.println("\tSearch attribute: " + searchAttribute);
            System.out.println("\tSearch value: " + searchValue.get());
            System.out.println("\tAttributes: " + Arrays.toString(attributes.toArray(new String[attributes.size()])));
        }
        setupQueries();
    }

    /**
     * Populates the JComboBox with the queries.
     */
    private void setupQueries() {
        queries.forEach(q -> queryBox.addItem(q.getQuery()));
        queryBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                System.out.println("state change");
                runQuery(queries.get(queryBox.getSelectedIndex()));
            }
        });
        panel.add(queryBox);
        runQuery(queries.get(2));
    }

    /**
     * Runs the specified SQL query.
     *
     * @param query The query to run.
     */
    private void runQuery(Query query) {
        AtomicReference<JTable> table = new AtomicReference<>(new JTable());
        if (query.getAttribute() == null) {
             table.set(getColumn(query.getAttributes(), query.getRelvar()));
        }
        JScrollPane panel = new JScrollPane(table.get());
        panel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        if (pane.getTabCount() == 4) {
            pane.remove(pane.getComponentAt(3));
        }
        pane.addTab("results", panel);
        pane.setSelectedIndex(3);
    }

    /**
     * Gets the specified attribute column from the specified relvar.
     *
     * @param attributes The attributes to get.
     * @param relvar The relvar from which to get the attribute.
     * @return The JTable representation of the column.
     */
    private JTable getColumn(List<String> attributes, Relvar relvar) {
        int[] columns = new int[attributes.size()];
        AtomicInteger index = new AtomicInteger(0);
        attributes.stream().forEachOrdered(a -> {
            System.out.println("KEY: " + relvar.getKey());
            a = a.equals(relvar.getKey()) ? "<html><b>" + a + "</b></html>" : a;
            attributes.set(index.get(), a);
            int idx = relvar.getAttributes().indexOf(a);
            columns[index.get()] = idx;
            System.out.println(a + " column found at index " + idx);
            index.set(index.get() + 1);
        });
        AtomicReference<JTable> table = new AtomicReference<>();
        tabs.forEach(t -> {
            if (t.getRelvar().equals(relvar)) {
                table.set(t.getTable());
            }
        });
        TableModel tableModel = table.get().getModel();
        int rows = tableModel.getRowCount();
        String[][] output = new String[rows][columns.length];
        for (int i = 0; i < columns.length; i++) {
            int column = table.get().convertColumnIndexToModel(columns[i]);
            for (int j = 0; j < rows; j++) {
                output[j][i] = tableModel.getValueAt(j, column).toString();
            }
        }
        NonEditableModel model = new NonEditableModel(output, attributes.toArray(new String[attributes.size()]));
        JTable t = new JTable(model);
        t.getTableHeader().setReorderingAllowed(false);
        System.out.println(t.getRowCount());
        return t;
    }

    /**
     * Gets the count of a given character within a String.
     *
     * @param c The character to search for.
     * @param text The text to query.
     * @return The count of the character within the String.
     */
    private int count(char c, String text) {
        int count = 0;
        char[] chars = text.toCharArray();
        for (char ch : chars) {
            if (ch == c) {
                count++;
            }
        }
        return count;
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
