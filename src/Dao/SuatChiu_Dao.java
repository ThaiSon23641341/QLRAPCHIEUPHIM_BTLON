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

}
