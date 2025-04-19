package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame {
    private JTable movieTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, logoutButton;

    public AdminDashboard() {
        setTitle("Movies Management System - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        // Top panel with search and logout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(51, 122, 183));
        topPanel.setPreferredSize(new Dimension(getWidth(), 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setOpaque(false);
        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        JButton searchButton = new JButton("Search");
        styleButton(searchButton);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Logout button
        logoutButton = new JButton("Logout");
        styleButton(logoutButton);

        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);

        // Center panel with table
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Table
        String[] columns = {"ID", "Title", "Genre", "Duration", "Release Date", "Price"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        movieTable = new JTable(tableModel);
        movieTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        movieTable.getTableHeader().setReorderingAllowed(false);
        movieTable.setRowHeight(30);
        
        // Add some sample data
        addSampleData();

        JScrollPane scrollPane = new JScrollPane(movieTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        addButton = new JButton("Add Movie");
        editButton = new JButton("Edit Movie");
        deleteButton = new JButton("Delete Movie");

        styleButton(addButton);
        styleButton(editButton);
        styleButton(deleteButton);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add components to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Add action listeners
        addButton.addActionListener(e -> showAddMovieDialog());
        editButton.addActionListener(e -> showEditMovieDialog());
        deleteButton.addActionListener(e -> deleteSelectedMovie());
        logoutButton.addActionListener(e -> logout());
        searchButton.addActionListener(e -> searchMovies());

        add(mainPanel);
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(120, 35));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(51, 122, 183));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(40, 96, 144));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(51, 122, 183));
            }
        });
    }

    private void addSampleData() {
        String[][] sampleData = {
            {"1", "The Shawshank Redemption", "Drama", "2h 22m", "1994-09-23", "$9.99"},
            {"2", "The Godfather", "Crime", "2h 55m", "1972-03-24", "$8.99"},
            {"3", "The Dark Knight", "Action", "2h 32m", "2008-07-18", "$10.99"}
        };
        
        for (String[] row : sampleData) {
            tableModel.addRow(row);
        }
    }

    private void showAddMovieDialog() {
        JDialog dialog = new JDialog(this, "Add New Movie", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 20, 5, 20);

        JTextField titleField = new JTextField(20);
        JTextField genreField = new JTextField(20);
        JTextField durationField = new JTextField(20);
        JTextField releaseDateField = new JTextField(20);
        JTextField priceField = new JTextField(20);

        formPanel.add(new JLabel("Title:"), gbc);
        formPanel.add(titleField, gbc);
        formPanel.add(new JLabel("Genre:"), gbc);
        formPanel.add(genreField, gbc);
        formPanel.add(new JLabel("Duration:"), gbc);
        formPanel.add(durationField, gbc);
        formPanel.add(new JLabel("Release Date:"), gbc);
        formPanel.add(releaseDateField, gbc);
        formPanel.add(new JLabel("Price:"), gbc);
        formPanel.add(priceField, gbc);

        JButton saveButton = new JButton("Save");
        styleButton(saveButton);
        saveButton.addActionListener(e -> {
            // TODO: Implement save logic
            String[] newRow = {
                String.valueOf(tableModel.getRowCount() + 1),
                titleField.getText(),
                genreField.getText(),
                durationField.getText(),
                releaseDateField.getText(),
                priceField.getText()
            };
            tableModel.addRow(newRow);
            dialog.dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showEditMovieDialog() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a movie to edit",
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Edit Movie", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 20, 5, 20);

        JTextField titleField = new JTextField(tableModel.getValueAt(selectedRow, 1).toString(), 20);
        JTextField genreField = new JTextField(tableModel.getValueAt(selectedRow, 2).toString(), 20);
        JTextField durationField = new JTextField(tableModel.getValueAt(selectedRow, 3).toString(), 20);
        JTextField releaseDateField = new JTextField(tableModel.getValueAt(selectedRow, 4).toString(), 20);
        JTextField priceField = new JTextField(tableModel.getValueAt(selectedRow, 5).toString(), 20);

        formPanel.add(new JLabel("Title:"), gbc);
        formPanel.add(titleField, gbc);
        formPanel.add(new JLabel("Genre:"), gbc);
        formPanel.add(genreField, gbc);
        formPanel.add(new JLabel("Duration:"), gbc);
        formPanel.add(durationField, gbc);
        formPanel.add(new JLabel("Release Date:"), gbc);
        formPanel.add(releaseDateField, gbc);
        formPanel.add(new JLabel("Price:"), gbc);
        formPanel.add(priceField, gbc);

        JButton saveButton = new JButton("Save Changes");
        styleButton(saveButton);
        saveButton.addActionListener(e -> {
            // TODO: Implement save changes logic
            tableModel.setValueAt(titleField.getText(), selectedRow, 1);
            tableModel.setValueAt(genreField.getText(), selectedRow, 2);
            tableModel.setValueAt(durationField.getText(), selectedRow, 3);
            tableModel.setValueAt(releaseDateField.getText(), selectedRow, 4);
            tableModel.setValueAt(priceField.getText(), selectedRow, 5);
            dialog.dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void deleteSelectedMovie() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a movie to delete",
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this movie?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
        }
    }

    private void searchMovies() {
        String searchText = searchField.getText().toLowerCase();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        movieTable.setRowSorter(sorter);
        if (searchText.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        }
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
            this.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminDashboard().setVisible(true);
        });
    }
} 