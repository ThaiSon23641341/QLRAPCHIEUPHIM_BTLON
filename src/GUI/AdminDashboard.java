package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Connect.ConnectDB;
import Dao.Ghe_Dao;
import Dao.Phim_Dao;
import Dao.SuatChiu_Dao;
import entity.Ghe;
import entity.Phim;
import entity.SuatChieu;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboard extends JFrame implements ActionListener {
    private JTable movieTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, logoutButton, bookingHistoryButton;
	private ArrayList<Phim> dsPim;
	private Ghe_Dao dsGhe;
	private ArrayList<Ghe> dsghetrenapp;
	private ArrayList<SuatChieu> dsSuatChieuSauKhiAdd;
	private JButton saveButton;
	private JButton btnThemSuatChieu;

    public AdminDashboard() {
    	
    	
    	
	try {
			
			ConnectDB.getInstance().connect();;
		}catch ( SQLException e) {

		e.printStackTrace();
		}
    	
        setTitle("Rạp Phim STD - Trang Nhân Viên");
        
        
    	
        setTitle("Rạp Phim STD - Trang Quản Trị Viên");
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
        JLabel titleLable = new JLabel("Quản trị viên");
        titleLable.setFont(new Font("Arial", Font.BOLD, 24));
        titleLable.setForeground(Color.WHITE);
        titleLable.setBorder(new EmptyBorder(0, 130, 0, 0));

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
        String[] columns = { "ID", "Tên Phim", "Thể Loại", "Thời Lượng", "Suất Chiếu", "Giá Vé", "Trạng Thái" };
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
//        addSampleData();
        
        addDataSuatChieuTheoSQl();

        JScrollPane scrollPane = new JScrollPane(movieTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        
        
        addButton = new JButton("Thêm Phim");
        editButton = new JButton("Sửa Suất Chiếu");
        deleteButton = new JButton("Xóa Suất Chiếu");
        bookingHistoryButton = new JButton("Lịch Sử");
       btnThemSuatChieu = new JButton("Thêm Suất Chiếu");

       
       
        styleButton(addButton);
        styleButton(editButton);
        styleButton(deleteButton);
        styleButton(bookingHistoryButton);

        buttonPanel.add(btnThemSuatChieu);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(bookingHistoryButton);

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        
        //Khai báo sự kiện cho các nút
        addButton.addActionListener(e -> showAddMovieDialog());
        editButton.addActionListener(e -> showEditMovieDialog());
        deleteButton.addActionListener(e -> deleteSelectedMovie());
        logoutButton.addActionListener(e -> logout());
        searchButton.addActionListener(e -> searchMovies());
        bookingHistoryButton.addActionListener(e -> showBookingHistory());
        btnThemSuatChieu.addActionListener(this);
        add(mainPanel);
    }

    private void addDataSuatChieuTheoSQl() {
    	Phim_Dao dsPhim = new Phim_Dao();
		
   	 dsPim = dsPhim.getalltbPhim();
   		
   		 dsGhe = new Ghe_Dao(); 
   		 dsghetrenapp = dsGhe.getAllGhe(); 
   		
   		SuatChiu_Dao dsSuatChieu = new SuatChiu_Dao();
   		dsSuatChieuSauKhiAdd = dsSuatChieu.getallSuatChieu(dsPim, dsghetrenapp);
   		 DefaultTableModel model = (DefaultTableModel) movieTable.getModel();
   		 
//   		 "ID", "Tên Phim", "Thể Loại", "Thời Lượng", "Suất Chiếu", "Giá Vé", "Trạng Thái"
//   		 
   		    for (SuatChieu sc : dsSuatChieuSauKhiAdd) {
   		        model.addRow(new Object[]{
   		            sc.getMaSuatChieu(),
   		            sc.getPhim().getTenPhim(),
   		            sc.getPhim().getTheloaiPhim(),
   		            sc.getPhim().getThoiluongPhim(),
   		            sc.getGioChieu(),
   		            sc.getGiaSuat(),
   		            "Còn chổ"
   		           
   		          
   		        });
   		    }
   		
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
                { "1", "The Shawshank Redemption", "Hài Kịch", "2 giờ 22 phút", "19:00 - 21:22", "$9.99", "Còn Chỗ" },
                { "2", "The Godfather", "Tội Phạm", "2 giờ 55 phút", "20:00 - 22:55", "$8.99", "Còn Chỗ" },
                { "3", "The Dark Knight", "Hành Động", "2 giờ 32 phút", "21:00 - 23:32", "$10.99", "Còn Chỗ" }
        };

        for (String[] row : sampleData) {
            tableModel.addRow(row);
        }
    }

    // Dialog thêm phim
    private void showAddMovieDialog() {
        JDialog dialog = new JDialog(this, "Thêm Phim Mới", true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 20, 5, 20);

        JTextField IDField = new JTextField(50);
        JTextField titleField = new JTextField(50);
        JTextField genreField = new JTextField(50);
        JTextField moTaField = new JTextField(50);
        JTextField rateField = new JTextField(50);
        JTextField daoDienField = new JTextField(50);
        JTextField NSXField = new JTextField(50);
        JTextField durationField = new JTextField(50);
        JTextField posterField = new JTextField(50);


        
        
        formPanel.add(new JLabel("Mã Phim: "), gbc);
        formPanel.add(IDField, gbc);
        formPanel.add(new JLabel("Tên Phim:"), gbc);
        formPanel.add(titleField, gbc);
        formPanel.add(new JLabel("Thể Loại:"), gbc);
        formPanel.add(genreField, gbc);
        formPanel.add(new JLabel("Mô tả:"), gbc);
        formPanel.add(moTaField, gbc);
        formPanel.add(new JLabel("Điểm đánh giá:"), gbc);
        formPanel.add(rateField, gbc);
        formPanel.add(new JLabel("Tên Đạo Diễn"), gbc);
        formPanel.add(daoDienField, gbc);
        formPanel.add(new JLabel("Nhà Sản Xuất:"), gbc);
        formPanel.add(NSXField, gbc);
        formPanel.add(new JLabel("Thời Lượng:"), gbc);	
        formPanel.add(durationField, gbc);
//        formPanel.add(new JLabel("Link Poster:"), gbc);	
//        formPanel.add(posterField, gbc);


         saveButton = new JButton("Lưu");
        styleButton(saveButton);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Lấy dữ liệu từ các TextField
                String maPhim = IDField.getText().trim();
                String tenPhim = titleField.getText().trim();
                String theLoai = genreField.getText().trim();
                String moTa = moTaField.getText().trim();
                String danhGiaStr = rateField.getText().trim();
                String tenDaoDien = daoDienField.getText().trim();
                String nhaSanXuat = NSXField.getText().trim();
                String thoiLuongStr = durationField.getText().trim();
                String linkPoster = posterField.getText().trim();

                // 2. Validate dữ liệu đầu vào
                if (maPhim.isEmpty() || tenPhim.isEmpty() || theLoai.isEmpty() || moTa.isEmpty() ||
                    danhGiaStr.isEmpty() || tenDaoDien.isEmpty() || nhaSanXuat.isEmpty() || thoiLuongStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ tất cả các trường thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int danhGia = 0;
                int thoiLuong = 0;

                try {
                    danhGia = Integer.parseInt(danhGiaStr);
                    thoiLuong = Integer.parseInt(thoiLuongStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Điểm đánh giá và thời lượng phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (danhGia < 0 || danhGia > 10) {
                    JOptionPane.showMessageDialog(null, "Điểm đánh giá phải từ 0 đến 10!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (thoiLuong <= 0) {
                    JOptionPane.showMessageDialog(null, "Thời lượng phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                System.out.println("Đã click Save Button và bắt đầu lưu phim...");

                // 3. Tạo đối tượng Phim
                Phim phim = new Phim(maPhim, tenPhim, theLoai, moTa, danhGia, tenDaoDien, nhaSanXuat, thoiLuong, linkPoster);

                // 4. Gọi DAO để lưu vào database
                try {
                    Phim_Dao phim_dao = new Phim_Dao();
                    boolean success = phim_dao.create(phim);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Lưu phim thành công!");
                        // Optionally: clear form
                        IDField.setText("");
                        titleField.setText("");
                        genreField.setText("");
                        moTaField.setText("");
                        rateField.setText("");
                        daoDienField.setText("");
                        NSXField.setText("");
                        durationField.setText("");
                        posterField.setText("");
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Lưu phim thất bại. Vui lòng kiểm tra lại dữ liệu!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi lưu phim: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
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
            JOptionPane.showMessageDialog(this, "Vui lòng chọn suất chiếu để sửa",
                    "Không Có Lựa Chọn", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Sửa Suất Chiếu", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 20, 5, 20);

        // Lấy mã suất chiếu đang chọn
        String maSuatChieu = movieTable.getValueAt(selectedRow, 0).toString();  // Giả sử cột 0 chứa maSuatChieu
        
        // Lọc đối tượng SuatChieu từ danh sách dsSuatChieuSauKhiAdd
        SuatChieu suatChieu = dsSuatChieuSauKhiAdd.stream()
                .filter(sc -> sc.getMaSuatChieu().equals(maSuatChieu))  // Lọc theo mã suất chiếu
                .findFirst()  // Lấy suất chiếu đầu tiên tìm thấy
                .orElse(null);  // Trả về null nếu không tìm thấy

        if (suatChieu != null) {
            // Gán giá trị từ đối tượng suatChieu vào các JTextField
            JTextField maSuatChieuField = new JTextField(suatChieu.getMaSuatChieu(), 20);
            JTextField giaSuatField = new JTextField(String.valueOf(suatChieu.getGiaSuat()), 20);
            JTextField ngayChieuField = new JTextField(suatChieu.getNgayChieu().toString(), 20); // Giả sử ngayChieu là LocalDate
            JTextField gioChieuField = new JTextField(suatChieu.getGioChieu(), 20);
            JTextField maPhimField = new JTextField(suatChieu.getPhim().getMaPhim(), 20); // Giả sử Phim là một đối tượng với phương thức getMaPhim()

            formPanel.add(new JLabel("Mã Suất Chiếu:"), gbc);
            formPanel.add(maSuatChieuField, gbc);
            formPanel.add(new JLabel("Giá Suất Chiếu:"), gbc);
            formPanel.add(giaSuatField, gbc);
            formPanel.add(new JLabel("Ngày Chiếu:"), gbc);
            formPanel.add(ngayChieuField, gbc);
            formPanel.add(new JLabel("Giờ Chiếu:"), gbc);
            formPanel.add(gioChieuField, gbc);
            formPanel.add(new JLabel("Mã Phim:"), gbc);
            formPanel.add(maPhimField, gbc);

            JButton saveButton = new JButton("Lưu");
            styleButton(saveButton);
            saveButton.addActionListener(e -> {
            

                // Gọi hàm cập nhật dữ liệu vào cơ sở dữ liệu (ví dụ, SuatChieu_Dao)
                SuatChiu_Dao dao = new SuatChiu_Dao();
                boolean isUpdated = dao.updateSuatChieu(
                        maSuatChieuField.getText(), 
                        Integer.parseInt(giaSuatField.getText()), 
                        ngayChieuField.getText(), 
                        gioChieuField.getText(), 
                        maPhimField.getText()
                );

                if (isUpdated) {
                    JOptionPane.showMessageDialog(this, "Sửa suất chiếu thành công!");
                    DefaultTableModel tableModel = (DefaultTableModel) movieTable.getModel();
        	        tableModel.setRowCount(0);  
        	        
        	        addDataSuatChieuTheoSQl();
                } else {
                    JOptionPane.showMessageDialog(this, "Có lỗi khi sửa suất chiếu.", 
                                                  "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                dialog.dispose();
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(saveButton);

            dialog.add(formPanel, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy suất chiếu với mã đã chọn.", 
                                          "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // xóa phim
    private void deleteSelectedMovie() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phim để xóa",
                    "Không Có Lựa Chọn", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy mã suất chiếu của phim được chọn từ bảng
        String maSuatChieu = movieTable.getValueAt(selectedRow, 0).toString();  // Giả sử cột 0 chứa maSuatChieu
        
        // Xác nhận xóa
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa phim này và tất cả các ghế liên quan?",
                "Xác Nhận Xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Gọi phương thức xóa ghế theo mã suất chiếu
        	
        	SuatChiu_Dao daoSuatChieu =  new  SuatChiu_Dao();
        	Ghe_Dao gheDao = new Ghe_Dao();

        	// Xóa tất cả ghế liên quan đến mã suất chiếu trước
        	boolean isGheDeleted = gheDao.deleteGheByMaSuatChieu(maSuatChieu);

        	if (isGheDeleted) {
        	    // Xóa suất chiếu sau khi đã xóa ghế
        	    boolean deletesuatchieu = daoSuatChieu.deleteSuatChieu(maSuatChieu);
        	    
        	    if (deletesuatchieu) {
        	        JOptionPane.showMessageDialog(this, "Xóa suất chiếu và ghế thành công!");
        	        
        	        DefaultTableModel tableModel = (DefaultTableModel) movieTable.getModel();
        	        tableModel.setRowCount(0);  
        	        
        	        addDataSuatChieuTheoSQl();
        	    } else {
        	        JOptionPane.showMessageDialog(this, "Có lỗi khi xóa suất chiếu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        	    }
        	} else {
        	    JOptionPane.showMessageDialog(this, "Có lỗi khi xóa ghế!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

        new AdminDashboard().setVisible(true);

    }

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource() ; 
		if (o.equals(saveButton)) {
			xulyluu();
		}else if(o.equals(btnThemSuatChieu)) {
			ThemSuatChieu();
		}
		
	}

	private void ThemSuatChieu() {
	    JDialog dialog = new JDialog(this, "Thêm Suất Chiếu", true);
	    dialog.setSize(400, 300);
	    dialog.setLocationRelativeTo(this);
	    dialog.setLayout(new GridLayout(6, 2, 10, 10));

	    // Các thành phần giao diện
	    JLabel lblMaSuat = new JLabel("Mã Suất Chiếu:");
	    JTextField txtMaSuat = new JTextField();

	    JLabel lblGiaSuat = new JLabel("Giá Suất:");
	    JTextField txtGiaSuat = new JTextField();

	    JLabel lblNgayChieu = new JLabel("Ngày Chiếu (yyyy-MM-dd):");
	    JTextField txtNgayChieu = new JTextField();

	    JLabel lblGioChieu = new JLabel("Giờ Chiếu (HH:mm):");
	    JTextField txtGioChieu = new JTextField();

	    JLabel lblMaPhim = new JLabel("Mã Phim:");
	    JTextField txtMaPhim = new JTextField();

	    JButton btnLuu = new JButton("Lưu");
	    JButton btnHuy = new JButton("Hủy");

	    // Xử lý nút lưu (chưa thực hiện lưu vào database)
	    btnLuu.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String maSuatChieu = txtMaSuat.getText();
	            String giaSuatString = txtGiaSuat.getText();
	            String ngayChieu = txtNgayChieu.getText();
	            String gioChieu = txtGioChieu.getText();
	            String maPhim = txtMaPhim.getText();

	            // Chuyển giaSuat từ String sang int
	            int giaSuat = 0;
	            try {
	                giaSuat = Integer.parseInt(giaSuatString);
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(dialog, "Giá suất phải là một số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            // Gọi dao để thêm suất chiếu vào database
	            SuatChiu_Dao dao = new SuatChiu_Dao();
	            boolean isSuccess = dao.createSuatChieu(maSuatChieu, giaSuat, ngayChieu, gioChieu, maPhim);
	            //TỰ động phát sinh 80 cái ghế trong database đúng với SuatChieu 
	            Ghe_Dao daoghe = new Ghe_Dao() ; 
	            boolean isSuccess2 = daoghe.phatSinhGhe(maSuatChieu);
	            
	            
	            if (isSuccess) {
	                // Hiển thị thông báo lưu thành công
	                JOptionPane.showMessageDialog(dialog, "Lưu suất chiếu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                
	                // Gọi phương thức làm mới dữ liệu trong bảng
	                addDataSuatChieuTheoSQl();

	                // Đóng dialog sau khi lưu và làm mới bảng
	                dialog.dispose();
	            } else {
	                // Hiển thị thông báo nếu lưu thất bại
	                JOptionPane.showMessageDialog(dialog, "Lưu suất chiếu không thành công!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	            }
	            
	            if (isSuccess2) {
	                // Hiển thị thông báo lưu thành công
	                JOptionPane.showMessageDialog(dialog, "đã phát sinh thêm 80 ghế theo mã !", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                
	                // Gọi phương thức làm mới dữ liệu trong bảng
	                addDataSuatChieuTheoSQl();

	                // Đóng dialog sau khi lưu và làm mới bảng
	                dialog.dispose();
	            } else {
	                // Hiển thị thông báo nếu lưu thất bại
	                JOptionPane.showMessageDialog(dialog, "Lưu suất chiếu không thành công nên ko có ghế !", "Lỗi", JOptionPane.ERROR_MESSAGE);
	            }
	            
	            
	            
	        }
	    });

	    // Xử lý nút hủy
	    btnHuy.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            dialog.dispose();
	        }
	    });

	    // Thêm thành phần vào dialog
	    dialog.add(lblMaSuat);
	    dialog.add(txtMaSuat);
	    dialog.add(lblGiaSuat);
	    dialog.add(txtGiaSuat);
	    dialog.add(lblNgayChieu);
	    dialog.add(txtNgayChieu);
	    dialog.add(lblGioChieu);
	    dialog.add(txtGioChieu);
	    dialog.add(lblMaPhim);
	    dialog.add(txtMaPhim);
	    dialog.add(btnLuu);
	    dialog.add(btnHuy);

	    dialog.setVisible(true);
	}


	private void xulyluu() {
		
		
	}
}