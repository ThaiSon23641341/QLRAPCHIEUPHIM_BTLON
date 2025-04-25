package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import entity.Booking;

public class BookingHistoryDialog extends JDialog {
    private JTable bookingTable;
    private DefaultTableModel tableModel;
    private List<Booking> bookings;
    
    public BookingHistoryDialog(JFrame parent) {
        super(parent, "Lịch sử đặt phim", true);
        
        setSize(800, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        // khởi tạo danh sách booking 
        bookings = new ArrayList<>();
        
        // Table
        String[] columns = {"Mã Đơn Đặt Hàng", "Phim", "Ghế", "Ngày", "Tổng Tiền", "Trạng Thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        bookingTable = new JTable(tableModel);
        bookingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookingTable.getTableHeader().setReorderingAllowed(false);
        bookingTable.setRowHeight(30);
        
        // Thêm đa ta mẫu
        addSampleData();
        
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Button xem chi tiết
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton viewDetailsButton = new JButton("Xem Chi Tiết");
        styleButton(viewDetailsButton);
        viewDetailsButton.addActionListener(e -> viewBookingDetails());
        
        JButton closeButton = new JButton("Đóng");
        styleButton(closeButton);
        closeButton.addActionListener(e -> dispose());
        
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(closeButton);
        
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    // css button 
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
        // thêm booking mẫu 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());
        
        addBooking("B001", "The Shawshank Redemption", "A1, A2, A3", currentDate, 29.97, "Đã Hoàn Tất",
                  "Nguyễn Văn A", "nguyenvana@email.com", "Tiền Mặt");
        addBooking("B002", "The Godfather", "B5, B6", currentDate, 17.98, "Đã Hoàn Tất",
                  "Trần Thị B", "tranthib@email.com", "Thẻ");
        addBooking("B003", "The Dark Knight", "C1, C2, C3, C4", currentDate, 43.96, "Đang Chờ",
                  "Lê Văn C", "levanc@email.com", "Tiền Mặt");
    }
    
    private void addBooking(String id, String movie, String seats, String date, double amount, String status,
                           String customerName, String customerEmail, String paymentMethod) {
        Booking booking = new Booking(id, movie, seats, date, amount, status, customerName, customerEmail, paymentMethod);
        bookings.add(booking);
        
        String[] row = {
            booking.getId(),
            booking.getMovie(),
            booking.getSeats(),
            booking.getDate(),
            String.format("$%.2f", booking.getAmount()),
            booking.getStatus()
        };
        
        tableModel.addRow(row);
    }
    
    private void viewBookingDetails() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn một đơn đặt hàng để xem chi tiết", 
                "Không Có Lựa Chọn", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String bookingId = tableModel.getValueAt(selectedRow, 0).toString();
        Booking booking = findBookingById(bookingId);
        
        if (booking != null) {
            JDialog detailsDialog = new JDialog(this, "Chi Tiết Đơn Đặt Hàng", true);
            detailsDialog.setSize(400, 400);
            detailsDialog.setLocationRelativeTo(this);
            detailsDialog.setLayout(new BorderLayout());
            
            JPanel detailsPanel = new JPanel(new GridLayout(9, 1, 5, 5));
            detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Thông tin đơn hàng
            detailsPanel.add(new JLabel("Mã Đơn Đặt Hàng: " + booking.getId()));
            detailsPanel.add(new JLabel("Phim: " + booking.getMovie()));
            detailsPanel.add(new JLabel("Ghế: " + booking.getSeats()));
            detailsPanel.add(new JLabel("Ngày: " + booking.getDate()));
            detailsPanel.add(new JLabel(String.format("Tổng Tiền: $%.2f", booking.getAmount())));
            detailsPanel.add(new JLabel("Trạng Thái: " + booking.getStatus()));
            
            // Thông tin khách hàng
            detailsPanel.add(new JLabel("Tên Khách Hàng: " + booking.getCustomerName()));
            detailsPanel.add(new JLabel("Email: " + booking.getCustomerEmail()));
            detailsPanel.add(new JLabel("Phương Thức Thanh Toán: " + booking.getPaymentMethod()));
            
            JButton closeButton = new JButton("Đóng");
            styleButton(closeButton);
            closeButton.addActionListener(e -> detailsDialog.dispose());
            
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(closeButton);
            
            detailsDialog.add(detailsPanel, BorderLayout.CENTER);
            detailsDialog.add(buttonPanel, BorderLayout.SOUTH);
            detailsDialog.setVisible(true);
        }
    }
    
    private Booking findBookingById(String id) {
        for (Booking booking : bookings) {
            if (booking.getId().equals(id)) {
                return booking;
            }
        }
        return null;
    }
    
    
} 