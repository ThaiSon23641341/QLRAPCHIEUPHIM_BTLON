package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SeatSelectionDialog extends JDialog {
    private JPanel seatPanel;
    private JLabel selectedSeatsLabel;
    private JLabel totalPriceLabel;
    private List<JButton> seatButtons;
    private List<String> selectedSeats;
    private double pricePerSeat;
    private int rows = 8;
    private int cols = 10;
    private JButton confirmButton;
    private boolean confirmed = false;

    public SeatSelectionDialog(JFrame parent, String movieTitle, double pricePerSeat) {
        super(parent, "Chọn Ghế Cho " + movieTitle, true);
        this.pricePerSeat = pricePerSeat;
        this.selectedSeats = new ArrayList<>();
        this.seatButtons = new ArrayList<>();
    
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
    
        // === NEW: Tạo JPanel chính ===
        JPanel seatSelector = new JPanel(new BorderLayout());
    
        // Panel screen
        JPanel screenPanel = new JPanel();
        screenPanel.setBackground(new Color(200, 200, 200));
        screenPanel.setPreferredSize(new Dimension(getWidth(), 50));
        screenPanel.setBorder(BorderFactory.createEtchedBorder());
        JLabel screenLabel = new JLabel("SCREEN");
        screenLabel.setFont(new Font("Arial", Font.BOLD, 20));
        screenPanel.add(screenLabel);
    
        // seat panel
        seatPanel = new JPanel(new GridLayout(rows, cols, 5, 5));
        seatPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String seatNumber = (char) ('A' + i) + String.valueOf(j + 1);
                JButton seatButton = new JButton(seatNumber);
                seatButton.setPreferredSize(new Dimension(60, 40));
                seatButton.setBackground(Color.GREEN);
                seatButton.setForeground(Color.BLACK);
                seatButton.setFocusPainted(false);
    
                if (Math.random() < 0.2) {
                    seatButton.setBackground(Color.RED);
                    seatButton.setEnabled(false);
                } else {
                    seatButton.addActionListener(e -> toggleSeatSelection(seatButton, seatNumber));
                }
    
                seatPanel.add(seatButton);
                seatButtons.add(seatButton);
            }
        }
    
        // Info panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        JPanel availableLegend = new JPanel();
        availableLegend.setBackground(Color.GREEN);
        availableLegend.setPreferredSize(new Dimension(20, 20));
        JPanel selectedLegend = new JPanel();
        selectedLegend.setBackground(Color.BLUE);
        selectedLegend.setPreferredSize(new Dimension(20, 20));
        JPanel bookedLegend = new JPanel();
        bookedLegend.setBackground(Color.RED);
        bookedLegend.setPreferredSize(new Dimension(20, 20));
    
        legendPanel.add(new JLabel("Còn Chỗ:"));
        legendPanel.add(availableLegend);
        legendPanel.add(new JLabel("Đã Chọn:"));
        legendPanel.add(selectedLegend);
        legendPanel.add(new JLabel("Đã Đặt:"));
        legendPanel.add(bookedLegend);
    
        selectedSeatsLabel = new JLabel("Đã Chọn: None");
        totalPriceLabel = new JLabel("Tổng Tiền: $0.00");
    
        infoPanel.add(legendPanel);
        infoPanel.add(selectedSeatsLabel);
        infoPanel.add(totalPriceLabel);
    
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        confirmButton = new JButton("Xác Nhận");
        confirmButton.setEnabled(false);
        styleButton(confirmButton);
        confirmButton.addActionListener(e -> confirmBooking());
    
        JButton cancelButton = new JButton("Hủy");
        styleButton(cancelButton);
        cancelButton.addActionListener(e -> dispose());
    
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
    
        // === Add các thành phần vào seatSelector ===
        seatSelector.add(screenPanel, BorderLayout.NORTH);
        seatSelector.add(seatPanel, BorderLayout.CENTER);
    
        // Tạo một panel chứa info + button để bỏ xuống SOUTH
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(infoPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        // Phần west
        JPanel movieInfoPanel = new JPanel();
        movieInfoPanel.setPreferredSize(new Dimension(200, 0));
        movieInfoPanel.setLayout(new BoxLayout(movieInfoPanel, BoxLayout.Y_AXIS));
        movieInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));
    
        JTextArea titleArea = new JTextArea("🎬 Phim: " + movieTitle);
        titleArea.setWrapStyleWord(true);
        titleArea.setLineWrap(true);
        titleArea.setEditable(false);
        titleArea.setFocusable(false);
        titleArea.setOpaque(false);
        titleArea.setBorder(null);
        titleArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleArea.setFont(new Font("Arial", Font.BOLD, 16));

        JComboBox<String> dichVu = new JComboBox<>(new String[] {
            "Nước Ngọt",
            "Bỏng Ngô",
            "Kẹo Bông Gòn",
            "Nước Ép",
            "Snack"
        });
        dichVu.setPreferredSize(new Dimension(150, 60));
        dichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        dichVu.setAlignmentX(Component.LEFT_ALIGNMENT);
        dichVu.setBorder(BorderFactory.createTitledBorder("Chọn Dịch Vụ: "));
        dichVu.setFocusable(false);


        movieInfoPanel.add(titleArea);
        movieInfoPanel.add(Box.createVerticalStrut(10));
        movieInfoPanel.add(dichVu);
        
        
        movieInfoPanel.add(Box.createVerticalStrut(10));
        movieInfoPanel.add(Box.createVerticalStrut(10));
    
    
        seatSelector.add(southPanel, BorderLayout.SOUTH);

    
        // === Cuối cùng add seatSelector vào JDialog ===
        add(seatSelector, BorderLayout.CENTER);
        add(movieInfoPanel, BorderLayout.WEST);
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

    // toggle chọn ghế 
    private void toggleSeatSelection(JButton seatButton, String seatNumber) {
        if (seatButton.getBackground() == Color.GREEN) {
            // chọn
            seatButton.setBackground(Color.BLUE);
            selectedSeats.add(seatNumber);
        } else if (seatButton.getBackground() == Color.BLUE) {
            // bỏ chọn
            seatButton.setBackground(Color.GREEN);
            selectedSeats.remove(seatNumber);
        }

        // Update ghế đã chọn
        if (selectedSeats.isEmpty()) {
            selectedSeatsLabel.setText("Đã Chọn: None");
            confirmButton.setEnabled(false);
        } else {
            selectedSeatsLabel.setText("Đã Chọn: " + String.join(", ", selectedSeats));
            confirmButton.setEnabled(true);
        }

        // Update tổng giá
        double totalPrice = selectedSeats.size() * pricePerSeat;
        totalPriceLabel.setText(String.format("Tổng Tiền: $%.2f", totalPrice));
    }

    private void confirmBooking() {
        confirmed = true;
        dispose();
    }

    public List<String> getSelectedSeats() {
        return selectedSeats;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public double getTotalPrice() {
        return selectedSeats.size() * pricePerSeat;
    }

    public int getTotalSeats() {
        return 100; 
    }
}