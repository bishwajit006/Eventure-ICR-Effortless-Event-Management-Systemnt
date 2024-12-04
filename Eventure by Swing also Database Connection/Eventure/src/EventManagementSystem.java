// Import necessary libraries
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

class ICRManagementSystem {
    JFrame frame;
    DefaultTableModel tableModel;
    JTable eventTable;

    // Database credentials
    private final String DB_URL = "jdbc:mysql://localhost:3306/icr_management";
    private final String DB_USER = "root";  // Replace with your DB username
    private final String DB_PASS = "";      // Replace with your DB password

    Connection connection;

    public static void main(String[] args) {
        new ICRManagementSystem().initializeDatabase();
    }

    // Initialize database connection
    public void initializeDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Connected to the database.");
            showLoginForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
            System.exit(1);
        }
    }

    // Login Form
    public void showLoginForm() {
        frame = new JFrame("Login Form");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 1));

        // Username Panel
        JPanel userPanel = new JPanel(new FlowLayout());
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(20);
        userPanel.add(userLabel);
        userPanel.add(userField);

        // Password Panel
        JPanel passPanel = new JPanel(new FlowLayout());
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);
        passPanel.add(passLabel);
        passPanel.add(passField);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");
        buttonPanel.add(loginButton);
        buttonPanel.add(signUpButton);

        // Result Panel
        JPanel resultPanel = new JPanel(new FlowLayout());
        JLabel resultLabel = new JLabel("");
        resultPanel.add(resultLabel);

        // Add panels to the frame
        frame.add(userPanel);
        frame.add(passPanel);
        frame.add(buttonPanel);
        frame.add(resultPanel);

        // Login Button Action
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (validateLogin(username, password)) {
                resultLabel.setText("Login successful!");
                resultLabel.setForeground(Color.GREEN);
                frame.dispose();
                showWelcomePage("Welcome " + username);
            } else {
                resultLabel.setText("Invalid username or password.");
                resultLabel.setForeground(Color.RED);
            }
        });

        // Sign-Up Button Action
        signUpButton.addActionListener(e -> {
            frame.dispose();
            showSignUpForm();
        });

        frame.setVisible(true);
    }

    // Validate Login
    private boolean validateLogin(String username, String password) {
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }

    // Sign-Up Form
    public void showSignUpForm() {
        frame = new JFrame("Sign-Up Form");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(7, 1));

        JTextField firstNameField = new JTextField(15);
        JTextField lastNameField = new JTextField(15);
        JTextField usernameField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);
        JTextField emailField = new JTextField(20);

        frame.add(createFieldPanel("First Name:", firstNameField));
        frame.add(createFieldPanel("Last Name:", lastNameField));
        frame.add(createFieldPanel("Username:", usernameField));
        frame.add(createFieldPanel("Password:", passField));
        frame.add(createFieldPanel("Email:", emailField));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton signUpButton = new JButton("Sign Up");
        JButton loginButton = new JButton("Back to Login");
        buttonPanel.add(signUpButton);
        buttonPanel.add(loginButton);
        frame.add(buttonPanel);

        JPanel resultPanel = new JPanel(new FlowLayout());
        JLabel resultLabel = new JLabel("");
        resultPanel.add(resultLabel);
        frame.add(resultPanel);

        signUpButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String username = usernameField.getText();
            String password = new String(passField.getPassword());
            String email = emailField.getText();

            if (registerUser(firstName, lastName, username, password, email)) {
                resultLabel.setText("Sign-up successful!");
                resultLabel.setForeground(Color.GREEN);
            } else {
                resultLabel.setText("Sign-up failed. Try again.");
                resultLabel.setForeground(Color.RED);
            }
        });

        loginButton.addActionListener(e -> {
            frame.dispose();
            showLoginForm();
        });

        frame.setVisible(true);
    }

    // Register User
    private boolean registerUser(String firstName, String lastName, String username, String password, String email) {
        try {
            String query = "INSERT INTO users (first_name, last_name, username, password, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.setString(5, email);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }

    // Welcome Page
    public void showWelcomePage(String welcomeMessage) {
        frame = new JFrame("Welcome Page");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel(welcomeMessage, JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(welcomeLabel, BorderLayout.NORTH);

        JButton manageEventsButton = new JButton("Manage Events");
        manageEventsButton.addActionListener(e -> {
            frame.dispose();
            showEventManagementSystem();
        });

        frame.add(manageEventsButton, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    // Event Management System
    public void showEventManagementSystem() {
        frame = new JFrame("Event Management System");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = {"ID", "Name", "Description", "Date", "Location"};
        tableModel = new DefaultTableModel(columns, 0);
        eventTable = new JTable(tableModel);
        loadEvents();

        JScrollPane scrollPane = new JScrollPane(eventTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton addEventButton = new JButton("Add Event");
        JButton editEventButton = new JButton("Edit Event");
        JButton deleteEventButton = new JButton("Delete Event");

        addEventButton.addActionListener(e -> showEventCreateForm());
        editEventButton.addActionListener(e -> showEventEditForm());
        deleteEventButton.addActionListener(e -> deleteEvent());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addEventButton);
        buttonPanel.add(editEventButton);
        buttonPanel.add(deleteEventButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    // Load Events from Database
    private void loadEvents() {
        try {
            String query = "SELECT * FROM events";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                tableModel.addRow(new Object[]{
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDate("date"),
                        resultSet.getString("location")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading events: " + e.getMessage());
        }
    }

    // Event Create Form
    public void showEventCreateForm() {
        JTextField nameField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField locationField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descriptionField);
        inputPanel.add(new JLabel("Date (yyyy-MM-dd):"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Location:"));
        inputPanel.add(locationField);

        int option = JOptionPane.showConfirmDialog(frame, inputPanel, "Add Event", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String query = "INSERT INTO events (name, description, date, location) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, nameField.getText());
                statement.setString(2, descriptionField.getText());
                statement.setString(3, dateField.getText());
                statement.setString(4, locationField.getText());
                statement.executeUpdate();
                tableModel.setRowCount(0);
                loadEvents();
                JOptionPane.showMessageDialog(frame, "Event added successfully.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame, "Error adding event: " + e.getMessage());
            }
        }
    }

    // Event Edit Form
    public void showEventEditForm() {
        int selectedRow = eventTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an event to edit.");
            return;
        }

        int eventId = (int) tableModel.getValueAt(selectedRow, 0);

        JTextField nameField = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
        JTextField descriptionField = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
        JTextField dateField = new JTextField(tableModel.getValueAt(selectedRow, 3).toString());
        JTextField locationField = new JTextField((String) tableModel.getValueAt(selectedRow, 4));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descriptionField);
        inputPanel.add(new JLabel("Date (yyyy-MM-dd):"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Location:"));
        inputPanel.add(locationField);

        int option = JOptionPane.showConfirmDialog(frame, inputPanel, "Edit Event", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String query = "UPDATE events SET name = ?, description = ?, date = ?, location = ? WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, nameField.getText());
                statement.setString(2, descriptionField.getText());
                statement.setString(3, dateField.getText());
                statement.setString(4, locationField.getText());
                statement.setInt(5, eventId);
                statement.executeUpdate();
                tableModel.setRowCount(0);
                loadEvents();
                JOptionPane.showMessageDialog(frame, "Event updated successfully.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame, "Error updating event: " + e.getMessage());
            }
        }
    }

    // Delete Event
    public void deleteEvent() {
        int selectedRow = eventTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an event to delete.");
            return;
        }

        int eventId = (int) tableModel.getValueAt(selectedRow, 0);

        int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this event?", "Delete Event", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try {
                String query = "DELETE FROM events WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, eventId);
                statement.executeUpdate();
                tableModel.setRowCount(0);
                loadEvents();
                JOptionPane.showMessageDialog(frame, "Event deleted successfully.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame, "Error deleting event: " + e.getMessage());
            }
        }
    }

    private JPanel createFieldPanel(String label, JComponent field) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel(label));
        panel.add(field);
        return panel;
    }
}
