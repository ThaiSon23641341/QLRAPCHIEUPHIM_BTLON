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
        super(parent, "Select Seats for " + movieTitle, true);
        this.pricePerSeat = pricePerSeat;
        this.selectedSeats = new ArrayList<>();
        this.seatButtons = new ArrayList<>();

        setSize(800, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

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

        // seat
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String seatNumber = (char) ('A' + i) + String.valueOf(j + 1);
                JButton seatButton = new JButton(seatNumber);
                seatButton.setPreferredSize(new Dimension(60, 40));
                seatButton.setBackground(Color.GREEN);
                seatButton.setForeground(Color.BLACK);
                seatButton.setFocusPainted(false);

                // hiển thị những ghế đã được chọn mẫu
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

        // info panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // màu ghế cho mỗi status
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

        legendPanel.add(new JLabel("Available:"));
        legendPanel.add(availableLegend);
        legendPanel.add(new JLabel("Selected:"));
        legendPanel.add(selectedLegend);
        legendPanel.add(new JLabel("Booked:"));
        legendPanel.add(bookedLegend);

        // Label chọn ghế
        selectedSeatsLabel = new JLabel("Selected Seats: None");

        // lable tổng giá
        totalPriceLabel = new JLabel("Total Price: $0.00");

        infoPanel.add(legendPanel);
        infoPanel.add(selectedSeatsLabel);
        infoPanel.add(totalPriceLabel);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        confirmButton = new JButton("Confirm Booking");
        confirmButton.setEnabled(false);
        styleButton(confirmButton);
        confirmButton.addActionListener(e -> confirmBooking());

        JButton cancelButton = new JButton("Cancel");
        styleButton(cancelButton);
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        add(screenPanel, BorderLayout.NORTH);
        add(seatPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
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
            selectedSeatsLabel.setText("Selected Seats: None");
            confirmButton.setEnabled(false);
        } else {
            selectedSeatsLabel.setText("Selected Seats: " + String.join(", ", selectedSeats));
            confirmButton.setEnabled(true);
        }

        // Update tổng giá
        double totalPrice = selectedSeats.size() * pricePerSeat;
        totalPriceLabel.setText(String.format("Total Price: $%.2f", totalPrice));
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