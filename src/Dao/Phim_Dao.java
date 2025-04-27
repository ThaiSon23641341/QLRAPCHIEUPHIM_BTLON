package Dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Connect.ConnectDB;
import entity.Phim;

public class Phim_Dao {
	
	
	public Phim_Dao() {
		

	}
	//Lấy nhân Phim từ Database 
	public ArrayList<Phim> getalltbPhim(){
			ArrayList<Phim> dsPhim = new ArrayList<Phim>();
			
			try {
				ConnectDB.getInstance();
				Connection con = ConnectDB.getCon();
				// Tạo câu lệnh sql 
				String sql = "select "
						+ "p.maPhim, p.tenPhim, lp.TenLoai,p.moTa,p.danhGia,p.tenDaoDien,p.thoiLuongPhim,p.nhaSanXuatPhim,p.[linkposter] "
						+ "from Phim p join LoaiPhim lp on p.maTheLoai = lp.maTheLoai  ; ";
				
			Statement statement = con.createStatement();
			// Thực thi câu lệnh sql trả về đối tượng result 
			
			ResultSet rs = statement.executeQuery(sql);
			// Duyệt tgreen kết quả trả về 
			
			while(rs.next()) {
				String maPhim = rs.getString(1);
				String tenPhim = rs.getString(2);
				String loai = rs.getString(3);
				String mota = rs.getString(4);
				int danhGia = rs.getInt(5);
				String daodien = rs.getString(6);
				int thoiluongphim = rs.getInt(7);
				String nhasanxuat = rs.getString(8);
				String linkposter = rs.getString(9);
				Phim phim = new Phim(maPhim, tenPhim, loai, mota, danhGia, daodien, nhasanxuat, thoiluongphim, linkposter);
				dsPhim.add(phim);
			}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return dsPhim;
			
	}
	
	
	//Phương thức chèn Phim vào bảng Phim
	public boolean create(Phim phim){
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		
		PreparedStatement stmt = null ; 
		int n = 0 ;
				
				try {
					stmt = con.prepareStatement("INSERT INTO Phim (maPhim, tenPhim,maTheLoai,moTa,danhGia,tenDaoDien,nhaSanXuatPhim,thoiluongPhim)"
								+ "values(?, ?, ?, ?, ?, ?, ?,?)");
					stmt.setString(1, phim.getMaPhim());
					stmt.setString(2, phim.getTenPhim());
					// do bảng chỉ chứa cột thể loại nên sẽ xử lý trên thể loại để lấy đúng loại để chèn ra 
					Statement statement = con.createStatement();
					String sql = "select maTheLoai from LoaiPhim where TenLoai = " + "N'" + phim.getTheloaiPhim() + "'"; 
					ResultSet rs = statement.executeQuery(sql);
					if (rs.next()) {
					    String maloai = rs.getString(1);
					    stmt.setString(3, maloai);
					} else {
					    // Xử lý khi không tìm thấy thể loại
					    System.out.println("Không tìm thấy thể loại phim.");
					}
					//
					
					
					stmt.setString(4,phim.getMotaPhim());
					stmt.setInt(5, phim.getDgPhim());
					stmt.setString(6, phim.getTenDaoDien());
					stmt.setString(7, phim.getNsx());
					stmt.setInt(8, phim.getThoiluongPhim());
					
					  n = stmt.executeUpdate(); // Thực thi câu lệnh insert và gán giá trị cho n

					
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				finally {
					try {
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
		
		return n>0;
			
	}
}
