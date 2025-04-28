package Dao;

import java.sql.*;
import java.util.ArrayList;
import Connect.ConnectDB;
import entity.HoaDon;

public class HoaDon_Dao {

    public HoaDon_Dao() {
    }

    // Lấy tất cả hóa đơn từ cơ sở dữ liệu
    public ArrayList<HoaDon> getAllHoaDon() {
        ArrayList<HoaDon> dsHoaDon = new ArrayList<HoaDon>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getCon();
            String sql = "SELECT * FROM HoaDon";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String maHoaDon = rs.getString("maHoadon");
                String maChiTietHoaDon = rs.getString("maChiTietHoaDon");
                int tongTien = rs.getInt("Tongtien");
                String maKhachHang = rs.getString("maKhachHang");

                HoaDon hoaDon = new HoaDon(maHoaDon, maChiTietHoaDon, tongTien, maKhachHang);
                dsHoaDon.add(hoaDon);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return dsHoaDon;
    }

    // Thêm hóa đơn mới vào cơ sở dữ liệu
    public boolean themHoaDon(HoaDon hoaDon) {
        boolean isAdded = false;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectDB.getCon();
            String sql = "INSERT INTO HoaDon (maHoadon, maChiTietHoaDon, Tongtien, maKhachHang) VALUES (?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, hoaDon.getMaHoaDon());
            pstmt.setString(2, hoaDon.getMaChiTietHoaDon());
            pstmt.setInt(3, hoaDon.getTongTien());
            pstmt.setString(4, hoaDon.getMaKhachHang());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isAdded = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isAdded;
    }

    // Cập nhật thông tin hóa đơn
    public boolean updateHoaDon(HoaDon hoaDon) {
        boolean isUpdated = false;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectDB.getCon();
            String sql = "UPDATE HoaDon SET maChiTietHoaDon = ?, Tongtien = ?, maKhachHang = ? WHERE maHoadon = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, hoaDon.getMaChiTietHoaDon());
            pstmt.setInt(2, hoaDon.getTongTien());
            pstmt.setString(3, hoaDon.getMaKhachHang());
            pstmt.setString(4, hoaDon.getMaHoaDon());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isUpdated = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isUpdated;
    }

    // Xóa hóa đơn theo mã hóa đơn
    public boolean deleteHoaDon(String maHoaDon) {
        boolean isDeleted = false;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectDB.getCon();
            String sql = "DELETE FROM HoaDon WHERE maHoadon = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, maHoaDon);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isDeleted = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isDeleted;
    }
}
