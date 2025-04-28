package Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import Connect.ConnectDB;
import entity.Ghe;
import entity.Phim;
import entity.SuatChieu;

public class SuatChiu_Dao {
	
	public SuatChiu_Dao() {

	}
	//lấy bảng suất chiếu từ db bảng SuatChieu dựa theo trùy vào ds Phim và ds Ghế 
	public ArrayList<SuatChieu> getallSuatChieu(ArrayList<Phim> dsPhim, ArrayList<Ghe> dsGhe){
	    ArrayList<SuatChieu> dsSuatChieu = new ArrayList<SuatChieu>();
	    
	    try {
	        ConnectDB.getInstance();
	        Connection con = ConnectDB.getCon();
	        
	        String sql = "SELECT * FROM SuatChieu";
	        Statement statement = con.createStatement();
	        ResultSet rs = statement.executeQuery(sql);
	        
	        while (rs.next()) {
	            String maSuatChieu = rs.getString(1);
	            int giaSuat = rs.getInt(2);
	            LocalDate ngayChieu = rs.getTimestamp("ngayChieu").toLocalDateTime().toLocalDate();
	            String gioChieu = rs.getString("gioChieu");
	            String maPhim = rs.getString("maPhim");
	            
	            // Tìm phim bằng Stream
	            Phim phim = dsPhim.stream()
	                              .filter(p -> p.getMaPhim().equals(maPhim))
	                              .findFirst()
	                              .orElse(null);
	            
	            //Tìm ghế đúng với suất chiếu bằng Stream
	            ArrayList<Ghe> gheTheoSuatChieu = dsGhe.stream()
	            	    .filter(ghe -> ghe.getMaSuatChieu().equals(maSuatChieu))
	            	    .collect(Collectors.toCollection(ArrayList::new));
	            
	            // Nếu tìm thấy phim thì tạo SuatChieu
	            if (phim != null) {
	               SuatChieu suatChieu = new SuatChieu(maSuatChieu, phim, giaSuat,ngayChieu,gioChieu, dsGhe);
	                dsSuatChieu.add(suatChieu);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return dsSuatChieu;
	}
	
	
	 // Phương thức thêm suất chiếu vào database với các tham số từ JTextField
	public boolean createSuatChieu(String maSuatChieu, int giaSuat, String ngayChieu, String gioChieu, String maPhim) {
	    boolean isSuccess = false;

	    try {
	        // Kết nối đến cơ sở dữ liệu
	        ConnectDB.getInstance();
	        Connection con = ConnectDB.getCon();

	        // Câu lệnh SQL để chèn một suất chiếu mới (Sử dụng String concatenation)
	        String sql = "INSERT INTO SuatChieu (maSuatChieu, giaSuat, ngayChieu, gioChieu, maPhim) "
	                + "VALUES ('" + maSuatChieu + "', " + giaSuat + ", '" + ngayChieu + "', '" + gioChieu + "', '" + maPhim + "')";

	        // Tạo Statement và thực thi câu lệnh SQL
	        Statement stmt = con.createStatement();
	        int result = stmt.executeUpdate(sql);

	        // Kiểm tra nếu việc thêm thành công
	        if (result > 0) {
	            isSuccess = true;
	        }

	        // Đóng kết nối
	        stmt.close();
//	        con.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return isSuccess;
	}

//	/ Thêm phương thức xóa suất chiếu
	public boolean deleteSuatChieu(String maSuatChieu) {
	    boolean isSuccess = false;

	    try {
	        // Kết nối đến cơ sở dữ liệu
	        ConnectDB.getInstance();
	        Connection con = ConnectDB.getCon();

	        // Câu lệnh SQL để xóa suất chiếu
	        String sql = "DELETE FROM SuatChieu WHERE maSuatChieu = '" + maSuatChieu + "'";

	        // Tạo Statement và thực thi câu lệnh SQL
	        Statement stmt = con.createStatement();
	        int result = stmt.executeUpdate(sql);

	        // Kiểm tra nếu việc xóa thành công
	        if (result > 0) {
	            isSuccess = true;
	        }

	        // Đóng kết nối
	        stmt.close();
	        // con.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return isSuccess;
	}
	
	
	public boolean updateSuatChieu(String maSuatChieu, int giaSuat, String ngayChieu, String gioChieu, String maPhim) {
        boolean isUpdated = false;
        Statement stmt = null;
        
        // Kết nối đến cơ sở dữ liệu
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getCon();
            
            // Câu lệnh SQL để cập nhật thông tin suất chiếu
            String sql = "UPDATE SuatChieu SET giaSuat = " + giaSuat + 
                         ", ngayChieu = '" + ngayChieu + 
                         "', gioChieu = '" + gioChieu + 
                         "', maPhim = '" + maPhim + 
                         "' WHERE maSuatChieu = '" + maSuatChieu + "'";
            
            // Tạo Statement và thực thi câu lệnh SQL
            stmt = con.createStatement();
            
            // Thực thi câu lệnh UPDATE
            int rowsAffected = stmt.executeUpdate(sql);
            
            if (rowsAffected > 0) {
                isUpdated = true;  // Cập nhật thành công nếu ít nhất 1 dòng bị ảnh hưởng
            }

            // Đóng statement
            stmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isUpdated;
    }
	
	
}
