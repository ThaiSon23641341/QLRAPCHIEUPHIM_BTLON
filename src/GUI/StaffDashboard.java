package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDashboard extends JFrame {
    private JTable movieTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton logoutButton, bookButton;
    private List<String> bookedSeats;

    public StaffDashboard() {
        setTitle("Rạp Phim STD - Trang Nhân Viên");
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

        // title
        JLabel titleLable = new JLabel("Nhân viên");
        titleLable.setFont(new Font("Arial", Font.BOLD, 24));
        titleLable.setForeground(Color.WHITE);
        titleLable.setBorder(new EmptyBorder(0, 160, 0, 0));

        // Logout button
        logoutButton = new JButton("Đăng xuất");
        styleButton(logoutButton);

        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(titleLable, BorderLayout.CENTER);
        topPanel.add(logoutButton, BorderLayout.EAST);

        // center Panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Table
        String[] columns = {"ID", "Tên Phim", "Thể Loại", "Thời Lượng", "Suất Chiếu", "Giá Vé", "Trạng Thái"};
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

        bookButton = new JButton("Đặt Vé");
        styleButton(bookButton);

        buttonPanel.add(bookButton);

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        logoutButton.addActionListener(e -> logout());
        searchButton.addActionListener(e -> searchMovies());
        bookButton.addActionListener(e -> bookSelectedMovie());

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
    
    private void bookSelectedMovie() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phim để đặt vé",
                "Không Có Lựa Chọn", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String movieTitle = tableModel.getValueAt(selectedRow, 1).toString();
        String priceText = tableModel.getValueAt(selectedRow, 5).toString();
        String durationText = tableModel.getValueAt(selectedRow, 3).toString();
        String gioChieu = tableModel.getValueAt(selectedRow, 4).toString();
        double price = Double.parseDouble(priceText.replace("$", ""));
        
        // Hiển thị trang chọn ghế
        SeatSelectionDialog seatDialog = new SeatSelectionDialog(this, movieTitle, price, durationText, gioChieu);
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
            new StaffDashboard().setVisible(true);
        });
    }
}