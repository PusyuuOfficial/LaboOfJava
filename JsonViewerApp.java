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
import java.util.Iterator;

public class JsonViewerApp extends JFrame {
    private JTable jsonTable;

    public JsonViewerApp() {
        setTitle("JSON Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        jsonTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(jsonTable);

        JButton fetchButton = new JButton("Fetch JSON");
        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchJsonData();
            }
        });

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(fetchButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void fetchJsonData() {
        String apiUrl = "https://21emon.wjg.jp/contact_form/data.json";

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
