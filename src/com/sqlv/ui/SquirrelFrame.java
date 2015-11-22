package com.sqlv.ui;

import com.sqlv.Query;
import com.sqlv.Relvar;
import com.sqlv.api.util.ResourceLoader;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private final DateFormat formatter = new SimpleDateFormat("MM/dd/YYYY hh:mm:ss");

    /**
     * Constructs a SquirrelFrame and sets the user and pass fields.
     *
     * @param username     The username to be set.
     * @param password     The password to be set.
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
            ResourceLoader imgLoader = new ResourceLoader("icons/");
            InputStream stream = imgLoader.getStream("squirrel.png");
            byte[] b = imgLoader.readStream(stream);
            if (b != null) {
                icon = new ImageIcon(b).getImage();
            }
            ResourceLoader dbLoader = new ResourceLoader("db/");
            stream = dbLoader.getStream("School.sql");
            b = dbLoader.readStream(stream);
            if (b != null) {
                String text = new String(b);
                loadDatabase(text);
            }
            stream = dbLoader.getStream("School_query.sql");
            b = dbLoader.readStream(stream);
            if (b != null) {
                String text = new String(b);
                loadQueries(text);
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
        add(pane);
        add(panel);
        pack();
        setIconImage(icon);
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
            String[] attributes = r.getAttributes().toArray(new String[r.getAttributes().size()]);
            NonEditableModel model = new NonEditableModel(r.getData(), attributes);
            JTable table = new JTable(model);
            table.getTableHeader().setReorderingAllowed(false);
            JPopupMenu popup = createPopup(table, r.getTitle(), attributes);
            table.setComponentPopupMenu(popup);
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
     * Disables text input to the specified Container.
     *
     * @param container The container to disable JTextField input in.
     */
    private void disableInput(Container container) {
        Component[] components = container.getComponents();
        for (Component c : components) {
            if (c instanceof JTextField) {
                JTextField field = (JTextField) c;
                field.setEnabled(false);
                break;
            } else if (c instanceof Container) {
                disableInput((Container) c);
            }
        }
    }

    /**
     * Creates a popup menu for exporting the specified table.
     *
     * @param table      The table.
     * @param title      The title of the table.
     * @param attributes The attributes of the table.
     * @return The JPopupMenu of the table.
     */
    private JPopupMenu createPopup(JTable table, String title, String[] attributes) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem export = new JMenuItem("Export " + title);
        export.addActionListener(event -> {
            JFileChooser chooser = new JFileChooser();
            disableInput(chooser);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setCurrentDirectory(new File("."));
            int selected = chooser.showDialog(this, "Save directory");
            if (selected == JFileChooser.APPROVE_OPTION) {
                exportTable(table.getModel(), title, attributes, chooser.getSelectedFile().getAbsolutePath());
            }
        });
        popup.add(export);
        return popup;
    }

    /**
     * Exports the selected table into a text file and then opens it.
     *
     * @param model      The table model to export.
     * @param attributes The attributes of the table.
     * @param name       The name of the table.
     * @param path       The path to save to.
     */
    private void exportTable(TableModel model, String name, String[] attributes, String path) {
        String date = formatter.format(new Date());
        String fileName = path + "\\" + name + " " + date.replace("/", "-").replace(":", "-") + ".txt";
        File file = new File(fileName);
        try {
            if (file.createNewFile()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                for (int i = 0; i < attributes.length; i++) {
                    String val = attributes[i].replaceAll("<.*?>", "");
                    writer.write(i == attributes.length - 1 ? String.format("%16s %n", val)
                            : String.format("%16s \t", val));
                }
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        String val = model.getValueAt(i, j).toString();
                        writer.write(j == model.getColumnCount() - 1 ? String.format("%16s %n", val)
                                : String.format("%16s \t", val));
                    }
                }
                writer.close();
                Desktop desktop = Desktop.getDesktop();
                if (desktop != null) {
                    desktop.open(file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            String updated = s.replace(",", "").replace("=", "").replace(";", "");
            String[] separated = updated.split(" ");
            Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
            Matcher matcher = regex.matcher(updated);
            outer:
            for (String x : separated) {
                if (x.isEmpty()) {
                    continue;
                } else if (search) {
                    searchAttribute = x;
                    search = false;
                    found = true;
                    continue;
                } else if (found) {
                    while (matcher.find()) {
                        if (matcher.group(2) != null) {
                            String val = matcher.group(2);
                            searchValue.set(val);
                            break;
                        } else {
                            String val = matcher.group();
                            searchValue.set(val);
                        }
                    }
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
                runQuery(queries.get(queryBox.getSelectedIndex()));
            }
        });
        panel.add(queryBox);
        runQuery(queries.get(0));
    }

    /**
     * Runs the specified SQL query.
     *
     * @param query The query to run.
     */
    private void runQuery(Query query) {
        AtomicReference<JTable> table = new AtomicReference<>(new JTable());
        JTable results = queryTable(query.getAttributes(), query.getAttribute(), query.getValue(), query.getRelvar());
        JPopupMenu popup = createPopup(results, query.getRelvar().getTitle(),
                query.getAttributes().toArray(new String[query.getAttributes().size()]));
        results.setComponentPopupMenu(popup);
        table.set(results);
        JScrollPane panel = new JScrollPane(table.get());
        panel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        if (pane.getTabCount() == 4) {
            pane.removeTabAt(3);
        }
        pane.addTab("results", panel);
        pane.setSelectedIndex(3);
    }

    /**
     * Gets the specified attribute column from the specified relvar.
     *
     * @param attributes The attributes to get.
     * @param relvar     The relvar from which to get the attribute.
     * @return The JTable representation of the column.
     */
    private JTable queryTable(List<String> attributes, String searchAttribute, String value, Relvar relvar) {
        int[] columns = new int[attributes.size()];
        AtomicInteger index = new AtomicInteger(0);
        attributes.stream().forEachOrdered(a -> {
            a = a.equals(relvar.getKey()) ? "<html><b>" + a + "</b></html>" : a;
            attributes.set(index.get(), a);
            int idx = relvar.getAttributes().indexOf(a);
            columns[index.get()] = idx;
            index.set(index.get() + 1);
        });
        AtomicReference<JTable> table = new AtomicReference<>();
        tabs.forEach(t -> {
            if (t.getRelvar().equals(relvar)) {
                table.set(t.getTable());
            }
        });
        TableModel tableModel = table.get().getModel();
        int row = getRowIndex(value, tableModel);
        int rows = searchAttribute == null ? tableModel.getRowCount() : 1;
        String[][] output = new String[rows][columns.length];
        for (int i = 0; i < columns.length; i++) {
            int column = table.get().convertColumnIndexToModel(columns[i]);
            if (searchAttribute != null) {
                output[0][i] = tableModel.getValueAt(row, column).toString();
                continue;
            }
            for (int j = 0; j < rows; j++) {
                output[j][i] = tableModel.getValueAt(j, column).toString();
            }
        }
        NonEditableModel model = new NonEditableModel(output, attributes.toArray(new String[attributes.size()]));
        JTable t = new JTable(model);
        t.getTableHeader().setReorderingAllowed(false);
        return t;
    }

    /**
     * Gets the row index for the specified value within the table model.
     *
     * @param value The value to search for.
     * @param model The model to search in.
     * @return The index at which the value was found.
     */
    private int getRowIndex(String value, TableModel model) {
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                if (model.getValueAt(i, j).equals(value)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Gets the count of a given character within a String.
     *
     * @param c    The character to search for.
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
