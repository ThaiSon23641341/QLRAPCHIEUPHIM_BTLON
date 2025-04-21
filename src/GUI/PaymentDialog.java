package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PaymentDialog extends JDialog {
    private JTextField cardNumberField;
    private JTextField cardHolderField;
    private JTextField expiryDateField;
    private JTextField cvvField;
    private JLabel totalAmountLabel;
    private double totalAmount;
    private List<String> selectedSeats;
    private String movieTitle;
    private boolean paymentSuccessful = false;
    private JRadioButton radioCash, radioCredit;
    
    public PaymentDialog(JFrame parent, String movieTitle, List<String> selectedSeats, double totalAmount) {
        super(parent, "Payment for " + movieTitle, true);
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
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Order Summary"));
        
        JLabel movieLabel = new JLabel("Movie: " + movieTitle);
        JLabel seatsLabel = new JLabel("Seats: " + String.join(", ", selectedSeats));
        JLabel dateLabel = new JLabel("Date: " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        totalAmountLabel = new JLabel(String.format("Total Amount: $%.2f", totalAmount));
        
        summaryPanel.add(movieLabel);
        summaryPanel.add(seatsLabel);
        summaryPanel.add(dateLabel);
        summaryPanel.add(totalAmountLabel);
        
        formPanel.add(summaryPanel, gbc);
        
        // Payment form
        JPanel paymentPanel = new JPanel(new GridBagLayout());
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Informations"));
        GridBagConstraints pgbc = new GridBagConstraints();
        pgbc.gridwidth = GridBagConstraints.REMAINDER;
        pgbc.fill = GridBagConstraints.HORIZONTAL;
        pgbc.insets = new Insets(5, 20, 5, 20);
        
        cardNumberField = new JTextField(20);
        cardHolderField = new JTextField(20);
        expiryDateField = new JTextField(10);
        cvvField = new JTextField(5);
        
        paymentPanel.add(new JLabel("Customer Name:"), pgbc);
        paymentPanel.add(cardNumberField, pgbc);
        paymentPanel.add(new JLabel("email:"), pgbc);
        paymentPanel.add(cardHolderField, pgbc);

        radioCash = new JRadioButton("Cash");
        radioCredit = new JRadioButton("Credit");
        ButtonGroup bg = new ButtonGroup();
        bg.add(radioCash);
        bg.add(radioCredit);
        
        JPanel paymentInfoPanel = new JPanel();
        JPanel paymentTitle = new JPanel();
        paymentTitle.add(new JLabel("Payment Method:"));
        JPanel paymentMethodPanel = new JPanel();
        paymentMethodPanel.add(radioCash);
        paymentMethodPanel.add(radioCredit);
        paymentInfoPanel.add(paymentTitle);
        paymentInfoPanel.add(paymentMethodPanel);
        
        paymentPanel.add(paymentInfoPanel, pgbc);
        
        formPanel.add(paymentPanel, gbc);
        
        // Button pay
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton payButton = new JButton("Pay Now");
        styleButton(payButton);
        payButton.addActionListener(e -> processPayment());
        
        JButton cancelButton = new JButton("Cancel");
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
    
    private void processPayment() {
        // Kiểm tra input 
        if (cardNumberField.getText().trim().isEmpty() || 
            cardHolderField.getText().trim().isEmpty() || 
            expiryDateField.getText().trim().isEmpty() || 
            cvvField.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Please fill in all payment details", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mô phỏng process thanh toán 
        JOptionPane.showMessageDialog(this, 
            "Processing payment...", 
            "Payment", 
            JOptionPane.INFORMATION_MESSAGE);
        
        paymentSuccessful = true;
        
        // Show success message
        JOptionPane.showMessageDialog(this, 
            "Payment successful! Your booking has been confirmed.", 
            "Payment Success", 
            JOptionPane.INFORMATION_MESSAGE);
        
        dispose();
    }
    
    // kiểm tra trạng thái thanh toán 
    public boolean isPaymentSuccessful() {
        return paymentSuccessful;
    }
} 