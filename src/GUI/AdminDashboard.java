package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminDashboard extends JFrame {
    private JTable movieTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, logoutButton, bookButton, bookingHistoryButton;
    private List<String> bookedSeats;

    public AdminDashboard() {
        setTitle("Movies Management System - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        // Search và logout panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(51, 122, 183));
        topPanel.setPreferredSize(new Dimension(getWidth(), 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Search 
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setOpaque(false);
        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        JButton searchButton = new JButton("Tìm kiếm");
        styleButton(searchButton);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Logout button
        logoutButton = new JButton("Đăng xuất");
        styleButton(logoutButton);

        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);

        // center Panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Table
        String[] columns = {"ID", "Tên Phim", "Thể Loại", "Thời Lượng", "Ngày Ra Mắt", "Giá Vé", "Trạng Thái"};
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
        
        // Thêm đa ta mẫu
        addSampleData();

        JScrollPane scrollPane = new JScrollPane(movieTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        addButton = new JButton("Thêm Phim");
        editButton = new JButton("Sửa Phim");
        deleteButton = new JButton("Xóa Phim");
        bookButton = new JButton("Đặt Vé");
        bookingHistoryButton = new JButton("Lịch Sử");

        styleButton(addButton);
        styleButton(editButton);
        styleButton(deleteButton);
        styleButton(bookButton);
        styleButton(bookingHistoryButton);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(bookButton);
        buttonPanel.add(bookingHistoryButton);

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> showAddMovieDialog());
        editButton.addActionListener(e -> showEditMovieDialog());
        deleteButton.addActionListener(e -> deleteSelectedMovie());
        logoutButton.addActionListener(e -> logout());
        searchButton.addActionListener(e -> searchMovies());
        bookButton.addActionListener(e -> bookSelectedMovie());
        bookingHistoryButton.addActionListener(e -> showBookingHistory());

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

    // đa ta mẫu 
    private void addSampleData() {
        String[][] sampleData = {
            {"1", "The Shawshank Redemption", "Hài Kịch", "2 giờ 22 phút", "1994-09-23", "$9.99", "Còn Chỗ"},
            {"2", "The Godfather", "Tội Phạm", "2 giờ 55 phút", "1972-03-24", "$8.99", "Còn Chỗ"},
            {"3", "The Dark Knight", "Hành Động", "2 giờ 32 phút", "2008-07-18", "$10.99", "Còn Chỗ"}
        };
        
        for (String[] row : sampleData) {
            tableModel.addRow(row);
        }
    }

    // Dialog thêm phim 
    private void showAddMovieDialog() {
        JDialog dialog = new JDialog(this, "Thêm Phim Mới", true);
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

        formPanel.add(new JLabel("Tên Phim:"), gbc);
        formPanel.add(titleField, gbc);
        formPanel.add(new JLabel("Thể Loại:"), gbc);
        formPanel.add(genreField, gbc);
        formPanel.add(new JLabel("Thời Lượng:"), gbc);
        formPanel.add(durationField, gbc);
        formPanel.add(new JLabel("Ngày Ra Mắt:"), gbc);
        formPanel.add(releaseDateField, gbc);
        formPanel.add(new JLabel("Giá Vé:"), gbc);
        formPanel.add(priceField, gbc);

        JButton saveButton = new JButton("Lưu");
        styleButton(saveButton);
        saveButton.addActionListener(e -> {
            // TODO: Implement save logic
            String[] newRow = {
                String.valueOf(tableModel.getRowCount() + 1),
                titleField.getText(),
                genreField.getText(),
                durationField.getText(),
                releaseDateField.getText(),
                priceField.getText(),
                "Còn Chỗ"
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

    // chỉnh sửa phim 
    private void showEditMovieDialog() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phim để sửa",
                "Không Có Lựa Chọn", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Sửa Phim", true);
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

        formPanel.add(new JLabel("Tên Phim:"), gbc);
        formPanel.add(titleField, gbc);
        formPanel.add(new JLabel("Thể Loại:"), gbc);
        formPanel.add(genreField, gbc);
        formPanel.add(new JLabel("Thời Lượng:"), gbc);
        formPanel.add(durationField, gbc);
        formPanel.add(new JLabel("Ngày Ra Mắt:"), gbc);
        formPanel.add(releaseDateField, gbc);
        formPanel.add(new JLabel("Giá Vé:"), gbc);
        formPanel.add(priceField, gbc);

        JButton saveButton = new JButton("Lưu");
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

    // xóa phim 
    private void deleteSelectedMovie() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phim để xóa",
                "Không Có Lựa Chọn", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn xóa phim này?",
            "Xác Nhận Xóa",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
        }
    }
    
    private void bookSelectedMovie() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phim để đặt vé",
                "Không Có Lựa Chọn", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String movieTitle = tableModel.getValueAt(selectedRow, 1).toString();
        String priceText = tableModel.getValueAt(selectedRow, 5).toString();
        double price = Double.parseDouble(priceText.replace("$", ""));
        
        // Hiển thị trang chọn ghế
        SeatSelectionDialog seatDialog = new SeatSelectionDialog(this, movieTitle, price);
        seatDialog.setVisible(true);
        
        if (seatDialog.isConfirmed() && !seatDialog.getSelectedSeats().isEmpty()) {
            List<String> selectedSeats = seatDialog.getSelectedSeats();
            double totalAmount = seatDialog.getTotalPrice();
            
            // Hiển thị thanh toán
            PaymentDialog paymentDialog = new PaymentDialog(this, movieTitle, selectedSeats, totalAmount);
            paymentDialog.setVisible(true);
            
            if (paymentDialog.isPaymentSuccessful()) {
                // Cập nhật ghế 
                if (bookedSeats == null) {
                    bookedSeats = new ArrayList<>();
                }
                bookedSeats.addAll(selectedSeats);

                // Kiểm tra ghế trong phòng chiếu
                int totalSeats = seatDialog.getTotalSeats(); 
                if (bookedSeats.size() >= totalSeats) {
                    tableModel.setValueAt("Đã Đặt", selectedRow, 6); 
                } else {
                    tableModel.setValueAt("Còn Chỗ", selectedRow, 6); 
                }
                
                JOptionPane.showMessageDialog(this, 
                    "Đặt Vé Thành Công! Phim: " + movieTitle + "\nGhế: " + String.join(", ", selectedSeats) + 
                    "\nTổng Tiền: $" + String.format("%.2f", totalAmount),
                    "Đặt Vé Thành Công", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    // hiển thị lịch sử booking
    private void showBookingHistory() {
        BookingHistoryDialog historyDialog = new BookingHistoryDialog(this);
        historyDialog.setVisible(true);
    }

    // tìm kiếm phim
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

    // đăng xuất 
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn đăng xuất?",
            "Xác Nhận Đăng Xuất",
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