package Dao;

import java.sql.*;
import java.util.ArrayList;
import Connect.ConnectDB;
import entity.ChiTietHoaDon;

public class ChiTietHoaDon_Dao {

    public ChiTietHoaDon_Dao() {
    }

    // Lấy tất cả chi tiết hóa đơn từ cơ sở dữ liệu
    public ArrayList<ChiTietHoaDon> getAllChiTietHoaDon() {
        ArrayList<ChiTietHoaDon> dsChiTietHoaDon = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getCon();
            String sql = "SELECT * FROM ChiTietHoaDon";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String maChiTietHoaDon = rs.getString("maChiTietHoaDon");
                String maSuatChieu = rs.getString("maSuatChieu");
                String maGhe = rs.getString("maGhe");
                String tenPhim = rs.getString("tenPhim");
                String maPhim = rs.getString("maPhim");
                String maDichVuThem = rs.getString("maDichVuThem");
                int soLuongDichVu = rs.getInt("soLuongDichVu");
                int giaSuat = rs.getInt("giaSuat");
                int giaDichVu = rs.getInt("giaDichVu");
                int tongTien = rs.getInt("TongTien");

                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(maChiTietHoaDon, maSuatChieu, maGhe, tenPhim, maPhim, 
                        maDichVuThem, soLuongDichVu, giaSuat, giaDichVu, tongTien);
                dsChiTietHoaDon.add(chiTietHoaDon);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return dsChiTietHoaDon;
    }

    // Thêm chi tiết hóa đơn mới vào cơ sở dữ liệu
    public boolean themChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        boolean isAdded = false;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectDB.getCon();
            String sql = "INSERT INTO ChiTietHoaDon (maChiTietHoaDon, maSuatChieu, maGhe, tenPhim, maPhim, " +
                    "maDichVuThem, soLuongDichVu, giaSuat, giaDichVu, TongTien) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, chiTietHoaDon.getMaChiTietHoaDon());
            pstmt.setString(2, chiTietHoaDon.getMaSuatChieu());
            pstmt.setString(3, chiTietHoaDon.getMaGhe());
            pstmt.setString(4, chiTietHoaDon.getTenPhim());
            pstmt.setString(5, chiTietHoaDon.getMaPhim());
            pstmt.setString(6, chiTietHoaDon.getMaDichVuThem());
            pstmt.setInt(7, chiTietHoaDon.getSoLuongDichVu());
            pstmt.setInt(8, chiTietHoaDon.getGiaSuat());
            pstmt.setInt(9, chiTietHoaDon.getGiaDichVu());
            pstmt.setInt(10, chiTietHoaDon.getTongTien());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isAdded = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isAdded;
    }

    // Cập nhật thông tin chi tiết hóa đơn
    public boolean updateChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        boolean isUpdated = false;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectDB.getCon();
            String sql = "UPDATE ChiTietHoaDon SET maSuatChieu = ?, maGhe = ?, tenPhim = ?, maPhim = ?, " +
                    "maDichVuThem = ?, soLuongDichVu = ?, giaSuat = ?, giaDichVu = ?, TongTien = ? WHERE maChiTietHoaDon = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, chiTietHoaDon.getMaSuatChieu());
            pstmt.setString(2, chiTietHoaDon.getMaGhe());
            pstmt.setString(3, chiTietHoaDon.getTenPhim());
            pstmt.setString(4, chiTietHoaDon.getMaPhim());
            pstmt.setString(5, chiTietHoaDon.getMaDichVuThem());
            pstmt.setInt(6, chiTietHoaDon.getSoLuongDichVu());
            pstmt.setInt(7, chiTietHoaDon.getGiaSuat());
            pstmt.setInt(8, chiTietHoaDon.getGiaDichVu());
            pstmt.setInt(9, chiTietHoaDon.getTongTien());
            pstmt.setString(10, chiTietHoaDon.getMaChiTietHoaDon());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isUpdated = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isUpdated;
    }

    // Xóa chi tiết hóa đơn theo mã chi tiết hóa đơn
    public boolean deleteChiTietHoaDon(String maChiTietHoaDon) {
        boolean isDeleted = false;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectDB.getCon();
            String sql = "DELETE FROM ChiTietHoaDon WHERE maChiTietHoaDon = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, maChiTietHoaDon);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isDeleted = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isDeleted;
    }
}
