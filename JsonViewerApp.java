import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonViewerApp extends JFrame {
    private JTable jsonTable;
    private String apiUrl;

    public JsonViewerApp() {
        promptForApiUrl();

        setTitle("JSON Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        jsonTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(jsonTable);

        JButton reloadButton = new JButton("Reload JSON");
        reloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchJsonData();
            }
        });

        // Create Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openJsonItem = new JMenuItem("Open JSON File");
        openJsonItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewJsonFile();
            }
        });
        fileMenu.add(openJsonItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(reloadButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);

        // Auto load JSON data on the first run
        fetchJsonData();
    }

    private void openNewJsonFile() {
        String newApiUrl = JOptionPane.showInputDialog(this, "Enter new API URL:", "Open JSON File", JOptionPane.QUESTION_MESSAGE);

        if (newApiUrl != null && !newApiUrl.trim().isEmpty()) {
            int option = JOptionPane.showConfirmDialog(this, "Do you want to overwrite current data?", "Open JSON File",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                // Overwrite current data
                apiUrl = newApiUrl;
                fetchJsonData();
            } else if (option == JOptionPane.NO_OPTION) {
                // Open in a new window
                new JsonViewerApp(newApiUrl).setVisible(true);
            }
            // If option is CANCEL_OPTION or closed the dialog, do nothing
        }
    }

    // Overloaded constructor for opening a new window with a different API URL
    private JsonViewerApp(String newApiUrl) {
        apiUrl = newApiUrl;
        setTitle("JSON Viewer - " + apiUrl);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);

        jsonTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(jsonTable);

        JButton reloadButton = new JButton("Reload JSON");
        reloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchJsonData();
            }
        });

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(reloadButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);

        // Auto load JSON data on the first run
        fetchJsonData();
    }

    private void promptForApiUrl() {
        while (true) {
            apiUrl = JOptionPane.showInputDialog(this, "Enter API URL:", "API URL", JOptionPane.QUESTION_MESSAGE);
            if (apiUrl == null) {
                // User canceled the input dialog, show an error message
                JOptionPane.showMessageDialog(this, "JSON data display requires input.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!apiUrl.trim().isEmpty()) {
                // Valid URL provided, break out of the loop
                break;
            }
        }
    }

    private void fetchJsonData() {
        if (apiUrl == null || apiUrl.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "JSON data display requires input.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                displayJsonData(response.toString());
            } else {
                JOptionPane.showMessageDialog(this, "Failed to fetch JSON. Response code: " + responseCode,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during JSON fetch: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayJsonData(String jsonData) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Key");
            model.addColumn("Value");

            // Parse JSON and add rows to the table model
            parseJson("", jsonData, model);

            jsonTable.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error parsing JSON data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void parseJson(String prefix, String json, DefaultTableModel model) {
        try {
            if (json.startsWith("{") && json.endsWith("}")) {
                json = json.substring(1, json.length() - 1).trim(); // Remove { and }

                String[] keyValuePairs = json.split(",");
                for (String pair : keyValuePairs) {
                    String[] entry = pair.split(":", 2);
                    String key = entry[0].trim();
                    String value = entry[1].trim();

                    String fullKey = prefix.isEmpty() ? key : prefix + "." + key;
                    model.addRow(new String[]{fullKey, value});

                    // Recursive call for nested objects
                    parseJson(fullKey, value, model);
                }
            } else if (json.startsWith("[") && json.endsWith("]")) {
                json = json.substring(1, json.length() - 1).trim(); // Remove [ and ]

                String[] elements = json.split(",");
                for (int i = 0; i < elements.length; i++) {
                    String element = elements[i].trim();

                    String fullKey = prefix + "[" + i + "]";
                    model.addRow(new String[]{fullKey, element});

                    // Recursive call for elements in the array
                    parseJson(fullKey, element, model);
                }
            }
        } catch (Exception e) {
            // Display an error message for any parsing exception
            model.addRow(new String[]{"Error", "Error parsing JSON data: " + e.getMessage()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JsonViewerApp().setVisible(true);
            }
        });
    }
}
