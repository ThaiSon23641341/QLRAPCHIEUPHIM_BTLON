package GUI;

import javax.swing.*;

import entity.Ghe;
import entity.SuatChieu;

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
    private String ImgPath = "/resources/image.png";
    private JTextField popcorn; // Declare popcorn as an instance variable
    private JTextField drink;   // Declare drink as an instance variable
	private JPanel seatSelector;

    public SeatSelectionDialog(JFrame parent, String movieTitle, double pricePerSeat, String gioChieu,
            String durationText,String newpath, SuatChieu suatchieu) {
        super(parent, "Chọn Ghế Cho " + movieTitle, true);
        this.pricePerSeat = pricePerSeat;
        this.selectedSeats = new ArrayList<>();
        this.seatButtons = new ArrayList<>();
        getContentPane().setBackground(Color.WHITE);

        setSize(1000, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        
        // Thay bằng đường dẫn mới
        ImgPath = newpath;
  

        // === NEW: Tạo JPanel chính ===
         seatSelector = new JPanel(new BorderLayout());

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

        // for phát sinh cũ 
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                String seatNumber = (char) ('A' + i) + String.valueOf(j + 1);
//                JButton seatButton = new JButton(seatNumber);
//                seatButton.setPreferredSize(new Dimension(60, 40));
//                seatButton.setBackground(Color.GREEN);
//                seatButton.setForeground(Color.BLACK);
//                seatButton.setFocusPainted(false);
//
//                if (Math.random() < 0.2) {
//                    seatButton.setBackground(Color.RED);
//                    seatButton.setEnabled(false);
//                } else {
//                    seatButton.addActionListener(e -> toggleSeatSelection(seatButton, seatNumber));
//                }
//
//                seatPanel.add(seatButton);
//                seatButtons.add(seatButton);
//            }
//        }
        
        
        // phát sinh mới 
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Tạo mã ghế với định dạng A01, A02, ..., A10, B01, ..., H10
                String seatNumber = (char) ('A' + i) + String.format("%02d", j + 1);  // Sử dụng String.format để đảm bảo định dạng "A01", "A02", ..., "A10"

                JButton seatButton = new JButton(seatNumber);
                seatButton.setPreferredSize(new Dimension(60, 40));
                seatButton.setBackground(Color.GREEN);
                seatButton.setForeground(Color.BLACK);
                seatButton.setFocusPainted(false);

                // Đặt màu đỏ và không cho phép chọn nếu ghế đã được đặt
                if (Math.random() < 0.2) {
                    seatButton.setBackground(Color.RED);
                    seatButton.setEnabled(false);
                } else {
                    // Thêm hành động khi ghế được chọn
                    seatButton.addActionListener(e -> toggleSeatSelection(seatButton, seatNumber));
                }

                // Thêm ghế vào bảng
                seatPanel.add(seatButton);
                seatButtons.add(seatButton);
            }
        }
        
        //Gọi phương thức gán lại giá trị
        displaySeats(suatchieu.getDsGhe());

        // Info panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 0, 5));

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
        movieInfoPanel.setPreferredSize(new Dimension(300, 0));
        movieInfoPanel.setLayout(new BoxLayout(movieInfoPanel, BoxLayout.Y_AXIS));
        movieInfoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        movieInfoPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        JPanel thongTinPhim = new JPanel();
        thongTinPhim.setLayout(new BoxLayout(thongTinPhim, BoxLayout.Y_AXIS));
        

        ImageIcon originalIcon = new ImageIcon(getClass().getResource(ImgPath));
        Image scaledImage = originalIcon.getImage().getScaledInstance(200, 310, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JPanel movieImage = new JPanel();
        movieImage.add(new JLabel(resizedIcon));

        JPanel titleArea = new JPanel();
        titleArea.add(new JLabel("Tên phim: " + movieTitle));

        JPanel durationArea = new JPanel();
        durationArea.add(new JLabel("Suất chiếu:  " + durationText));

        JPanel gioChieuArea = new JPanel();
        gioChieuArea.add(new JLabel("Thời lượng: " + gioChieu));

        JPanel danhGia = new JPanel();
        danhGia.add(new JLabel("Đánh gía: 5/5"));

        JPanel dichVu = new JPanel();
        dichVu.setLayout(new BoxLayout(dichVu, BoxLayout.Y_AXIS));

        JPanel popcornPanel = new JPanel();
        popcorn = new JTextField("0", 15); 
        popcorn.setBorder(BorderFactory.createTitledBorder("Bắp Rang (2$): "));
        popcornPanel.add(popcorn);

        JPanel drinkPanel = new JPanel();
        drink = new JTextField("0", 15); 
        drink.setBorder(BorderFactory.createTitledBorder("Nước ngọt (1$): "));
        drinkPanel.add(drink);

        dichVu.add(popcornPanel);
        dichVu.add(drinkPanel);

        thongTinPhim.add(titleArea);

        thongTinPhim.add(movieImage);
        thongTinPhim.add(durationArea);
        thongTinPhim.add(gioChieuArea);
        thongTinPhim.add(Box.createHorizontalStrut(20)); // khoảng cách giữa title và ảnh
        thongTinPhim.add(danhGia);
        movieInfoPanel.add(Box.createVerticalStrut(30)); // khoảng cách giữa title và ảnh
        thongTinPhim.add(dichVu);

        movieInfoPanel.add(thongTinPhim);
        seatSelector.add(southPanel, BorderLayout.SOUTH);

        // === Cuối cùng add seatSelector vào JDialog ===
        add(seatSelector, BorderLayout.CENTER);
        add(movieInfoPanel, BorderLayout.WEST);
    }

    
    //Phương thức ráng giá trị mới cho ghế ở database
    private void displaySeats(List<Ghe> danhSachGhe) {
        // Lặp qua danh sách các ghế và cập nhật trạng thái của từng ghế
        for (Ghe ghe : danhSachGhe) {
        	
//        	lấy 3 ký tự cuối 
        	String seatNumber = ghe.getMaGhe().substring(ghe.getMaGhe().length() - 3);
        	
        	
            String trangThai = ghe.getTrangthaiGhe();  // Trạng thái ghế (Trống, Đã chọn, Đã đặt)
      
            	System.out.print( trangThai + ' ');
         
            // Tìm nút ghế tương ứng với mã ghế
            for (JButton seatButton : seatButtons) {
                if (seatButton.getText().equals(seatNumber)) {
                    if ("Trống".equals(trangThai)) {
                        seatButton.setBackground(Color.GREEN);
                        seatButton.setEnabled(true);
                    } else if ("Đã chọn".equals(trangThai)) {
                        seatButton.setBackground(Color.BLUE);
                        seatButton.setEnabled(true);
                    } else if ("Đã đặt".equals(trangThai)) {
                        seatButton.setBackground(Color.RED);
                        seatButton.setEnabled(false);
                    }
                    break;  // Thoát khỏi vòng lặp khi tìm được ghế
                }
            }
        }
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
        double total = selectedSeats.size() * pricePerSeat;
        int popcornQuantity = Integer.parseInt(popcorn.getText());
        int drinkQuantity = Integer.parseInt(drink.getText());
        if (popcornQuantity > 0) {
            total += (popcornQuantity * 2);
        }
        if (drinkQuantity > 0) {
            total += (drinkQuantity * 1);
        }
        return total;
    }

    public int getTotalSeats() {
        return 100;
    }

}
