package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel titleLabel;
    private BufferedImage backgroundImage;

    public LoginForm() {
        setTitle("Rạp Phim STD - Trang Đăng Nhập");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();

                // Draw background image if available
                if (backgroundImage != null) {
                    g2d.drawImage(backgroundImage, 0, 5, w, h, null);
                }

            }
        };
        mainPanel.setLayout(new BorderLayout());
        try {
            backgroundImage = javax.imageio.ImageIO.read(getClass().getResource("/resources/LoginBackGround.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Title
        titleLabel = new JLabel("Rạp Phim STD", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(150, 0, 30, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Login
        JPanel loginPanel = new JPanel();
        loginPanel.setOpaque(false);
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 20, 5, 20);

        // Username
        JLabel usernameLabel = new JLabel("<html>&#128100;&nbsp;Tên&nbsp;Tài&nbsp;Khoản&nbsp;:&nbsp;</html>");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.insets = new Insets(5, 20, -10, 20); 
        loginPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        styleTextField(usernameField);
        gbc.insets = new Insets(0, 20, 5, 20);
        loginPanel.add(usernameField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("<html>&#128273;&nbsp;Mật&nbsp;Khẩu&nbsp;:&nbsp;</html>");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.insets = new Insets(10, 20, -10, 20);
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        styleTextField(passwordField);
        gbc.insets = new Insets(0, 20, 10, 20);
        loginPanel.add(passwordField, gbc);

        // Buttons đăng kí
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        loginButton = new JButton("Đăng Nhập");
        styleButton(loginButton);
        buttonPanel.add(loginButton);
        gbc.insets = new Insets(5, 20, 5, 20);
        loginPanel.add(buttonPanel, gbc);

        mainPanel.add(loginPanel, BorderLayout.CENTER);

        // Add action listeners
        loginButton.addActionListener(e -> handleLogin());

        add(mainPanel);
    }

    private void styleTextField(JTextField textField) {
        textField.setPreferredSize(new Dimension(300, 35));
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(120, 40));
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

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Xác thực admin
        if (username.equals("admin") && password.equals("admin")) {
            JOptionPane.showMessageDialog(this, "Đăng Nhập Thành Công!");
            openAdminDashboard();
        } else if (username.equals("staff") && password.equals("staff")) {
            JOptionPane.showMessageDialog(this, "Đăng Nhập Thành Công!");
            openStaffDashboard();
        } else {
            JOptionPane.showMessageDialog(this, "Tên Tài Khoản Hoặc Mật Khẩu Không Chính Xác!",
                    "Lỗi Đăng Nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    // mở trang admin
    private void openAdminDashboard() {
        AdminDashboard adminDashboard = new AdminDashboard();
        adminDashboard.setVisible(true);
        this.dispose();
    }

    // mở trang staff
    private void openStaffDashboard() {
        StaffDashboard staffDashboard = new StaffDashboard();
        staffDashboard.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {

        new LoginForm().setVisible(true);

    }
}