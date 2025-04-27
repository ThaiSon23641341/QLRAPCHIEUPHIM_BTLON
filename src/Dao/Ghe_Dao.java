package Dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Connect.ConnectDB;
import entity.Ghe;

public class Ghe_Dao {
	public Ghe_Dao() {
		
	}
	
	
	//Lấy hết ghế trên DataBase 
	// lấy toàn bộ Ghế trên Database
	public ArrayList<Ghe> getAllGhe() {
	    ArrayList<Ghe> dsghe = new ArrayList<Ghe>();
	    try {
	        ConnectDB.getInstance(); 
	        Connection con = ConnectDB.getCon();
	        String sql = "SELECT * FROM Ghe ORDER BY maGhe ASC";
	        Statement statement = con.createStatement();
	        // Thực thi câu lệnh SQL trả về đối tượng ResultSet
	        ResultSet rs = statement.executeQuery(sql);
	        
	        while (rs.next()) {
	            String maGhe = rs.getString(1);
	            String maSuatChieu = rs.getString(2);
	            String trangThai = rs.getString(3);
	            Ghe ghe = new Ghe(maGhe, maSuatChieu, trangThai);
	            dsghe.add(ghe);
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return dsghe;
	}

	
	// lấy hết Ghế trên Database dựa trên mã Suất chiếu 
	public ArrayList<Ghe> getallGheTheoMaSuatChieu(String maSuatChieu){
		ArrayList<Ghe> dsghe = new ArrayList<Ghe>();
		try {
			ConnectDB.getInstance(); 
			Connection con = ConnectDB.getCon();
			String sql = "select * from Ghe Where maSuatChieu ='"
					+ maSuatChieu + "'" + "order by maGhe asc";
			Statement statement =  con.createStatement();
			//Thực thi câu lệnh Sql trả về đối tương resultSet.
		ResultSet rs = statement.executeQuery(sql);
		
		while(rs.next()) {
			String maGhe = rs.getString(1);
			String trangThai = rs.getString(3);
			Ghe ghe = new Ghe(maGhe,maSuatChieu,trangThai);
			dsghe.add(ghe);
		}
			
		} catch ( SQLException e) {
			e.printStackTrace();
		}
		
		return dsghe;
	}
	
	
	public boolean updateTrangThaiGhe(String maGhe, String trangThaiMoi) {
	    boolean isUpdated = false;
	    Statement stmt = null;
	    try {
	        ConnectDB.getInstance();
	        Connection con = ConnectDB.getCon();
	        
	        // Câu lệnh SQL để cập nhật trạng thái ghế
	        String sql = "UPDATE Ghe SET TrangThai = '" + trangThaiMoi + "' WHERE maGhe = '" + maGhe + "' AND TrangThai = 'Trống'";
	        
	        // Tạo Statement và thực thi câu lệnh SQL
	        stmt = con.createStatement();
	        
	        // Thực thi câu lệnh UPDATE
	        int rowsAffected = stmt.executeUpdate(sql);
	        
	        if (rowsAffected > 0) {
	            isUpdated = true;  // Cập nhật thành công
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return isUpdated;
	}
	

	 public boolean phatSinhGhe(String maSuatChieu) {
	        boolean isSuccess = false;
	        Statement stmt = null;

	        try {
	            // Kết nối đến cơ sở dữ liệu
	            ConnectDB.getInstance();
	            Connection con = ConnectDB.getCon();
	            
	            // Gọi SQL Procedure bằng EXEC sử dụng Statement
	            String sql = "EXEC PhatSinhGhe '" + maSuatChieu + "'";
	            stmt = con.createStatement();
	            
	            // Thực thi câu lệnh
	            int rowsAffected = stmt.executeUpdate(sql);
	            
	            if (rowsAffected >= 0) {
	                isSuccess = true;  // Thành công nếu có ít nhất 1 dòng bị ảnh hưởng
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (stmt != null) {
	                    stmt.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        
	        return isSuccess;
	    }


}





