package Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Connect.ConnectDB;
import entity.Phim;
import entity.TheLoai;

public class TheLoai_Dao {
	
	public TheLoai_Dao() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<TheLoai> getallTheloai(){
		ArrayList<TheLoai> dsTheLoai = new ArrayList<TheLoai>() ; 
	
		
		
		try {
			Connection con = ConnectDB.getCon(); 
			// Tạo câu lệnh Sql 
			String sql = "select * from LoaiPhim ";
			
		Statement statement = con.createStatement();
		// Thực thi câu lệnh sql trả về đối tượng result 
		
		ResultSet rs = statement.executeQuery(sql);
		// Duyệt hết kết quả trả về 
		while(rs.next()) {
			String maloai = rs.getString(1);
			String tenloai = rs.getString(2);
			TheLoai theloai = new TheLoai(maloai, tenloai);
			dsTheLoai.add(theloai);
		}
			
		} catch (SQLException e) {
			
		}
		
		return dsTheLoai;		
	}
	
	public boolean createTheLoai(TheLoai theLoai) {
	    try {
	        Connection con = ConnectDB.getCon();
	        Statement statement = con.createStatement();
	        
	        String sql = "INSERT INTO LoaiPhim (MaLoai, TenLoai) VALUES ('" 
	                + theLoai.getMaTheLoai() + "', '" 
	                + theLoai.getTenTheLoai() + "')";
	        
	        int rowsInserted = statement.executeUpdate(sql);
	        
	        return rowsInserted > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	

}
