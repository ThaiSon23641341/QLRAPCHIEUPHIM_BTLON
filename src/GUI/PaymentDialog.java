package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class PaymentDialog extends JDialog {
    private JTextField nameField;
    private JTextField emailField;
    private JRadioButton radioCash, radioCredit;
    private JLabel totalAmountLabel;
    private double totalAmount;
    private List<String> selectedSeats;
    private String movieTitle;
    private boolean paymentSuccessful = false;
    
    public PaymentDialog(JFrame parent, String movieTitle, List<String> selectedSeats, double totalAmount) {
        super(parent, "Thanh Toán Cho " + movieTitle, true);
        this.movieTitle = movieTitle;
        this.selectedSeats = selectedSeats;
        this.totalAmount = totalAmount;
        
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        // form 
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 20, 5, 20);
        
        // Order summary
        JPanel summaryPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Tóm Tắt Đơn Hàng"));
        
        JLabel movieLabel = new JLabel("Phim: " + movieTitle);
        JLabel seatsLabel = new JLabel("Ghế: " + String.join(", ", selectedSeats));
        JLabel dateLabel = new JLabel("Ngày: " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        totalAmountLabel = new JLabel(String.format("Tổng Tiền: $%.2f", totalAmount));
        
        summaryPanel.add(movieLabel);
        summaryPanel.add(seatsLabel);
        summaryPanel.add(dateLabel);
        summaryPanel.add(totalAmountLabel);
        
        formPanel.add(summaryPanel, gbc);
        
        // Payment form
        JPanel paymentPanel = new JPanel(new GridBagLayout());
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Thông Tin Khách Hàng"));
        GridBagConstraints pgbc = new GridBagConstraints();
        pgbc.gridwidth = GridBagConstraints.REMAINDER;
        pgbc.fill = GridBagConstraints.HORIZONTAL;
        pgbc.insets = new Insets(5, 20, 5, 20);
        
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        
        paymentPanel.add(new JLabel("Họ Tên:"), pgbc);
        paymentPanel.add(nameField, pgbc);
        paymentPanel.add(new JLabel("Email:"), pgbc);
        paymentPanel.add(emailField, pgbc);

        radioCash = new JRadioButton("Tiền Mặt");
        radioCredit = new JRadioButton("Thẻ");
        ButtonGroup bg = new ButtonGroup();
        bg.add(radioCash);
        bg.add(radioCredit);
        
        JPanel paymentInfoPanel = new JPanel();
        JPanel paymentTitle = new JPanel();
        paymentTitle.add(new JLabel("Phương Thức Thanh Toán:"));
        JPanel paymentMethodPanel = new JPanel();
        paymentMethodPanel.add(radioCash);
        paymentMethodPanel.add(radioCredit);
        paymentInfoPanel.add(paymentTitle);
        paymentInfoPanel.add(paymentMethodPanel);
        
        paymentPanel.add(paymentInfoPanel, pgbc);
        
        formPanel.add(paymentPanel, gbc);
        
        // Button pay
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton payButton = new JButton("Thanh Toán");
        styleButton(payButton);
        payButton.addActionListener(e -> processPayment());
        
        JButton cancelButton = new JButton("Hủy");   
        styleButton(cancelButton);
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(payButton);
        buttonPanel.add(cancelButton);
        
        add(formPanel, BorderLayout.CENTER);
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
    
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    
    private boolean isValidName(String name) {
        return name != null && name.trim().length() >= 2 && name.matches("^[\\p{L} .'-]+$");
    }
    
    private void processPayment() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        
        // Kiểm tra input 
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng nhập họ tên", 
                "Lỗi Kiểm Tra", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!isValidName(name)) {
            JOptionPane.showMessageDialog(this, 
                "Họ tên không hợp lệ. Vui lòng nhập họ tên đúng định dạng", 
                "Lỗi Kiểm Tra", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng nhập email", 
                "Lỗi Kiểm Tra", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, 
                "Email không hợp lệ. Vui lòng nhập email đúng định dạng", 
                "Lỗi Kiểm Tra", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!radioCash.isSelected() && !radioCredit.isSelected()) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn phương thức thanh toán", 
                "Lỗi Kiểm Tra", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mô phỏng process thanh toán 
        JOptionPane.showMessageDialog(this, 
            "Đang xử lý thanh toán...", 
            "Thanh Toán", 
            JOptionPane.INFORMATION_MESSAGE);
        
        paymentSuccessful = true;
        
        JOptionPane.showMessageDialog(this, 
            "Thanh Toán Thành Công! Đơn Hàng Của Bạn Đã Được Xác Nhận.", 
            "Thanh Toán Thành Công", 
            JOptionPane.INFORMATION_MESSAGE);
        
        dispose();
    }
    
    public boolean isPaymentSuccessful() {
        return paymentSuccessful;
    }
} 