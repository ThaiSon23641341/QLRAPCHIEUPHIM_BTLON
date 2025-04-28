package Dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import Connect.ConnectDB;
import entity.KhachHang;

public class KhachHang_Dao {
    public KhachHang_Dao() {
    }

    // Lấy tất cả khách hàng từ cơ sở dữ liệu
    public ArrayList<KhachHang> getAllKhachHang() {
        ArrayList<KhachHang> dsKhachHang = new ArrayList<KhachHang>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getCon();
            String sql = "SELECT * FROM KhachHang";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String maKhachHang = rs.getString("maKhachHang");
                String tenKhachHang = rs.getString("tenKhachHang");
                String maHoaDon = rs.getString("maHoaDon");
                Date ngayThanhToan = rs.getDate("NgayThanhToan");
                String email = rs.getString("email");

                // Chuyển đổi từ java.sql.Date sang java.time.LocalDate
                LocalDate localDate = ngayThanhToan.toLocalDate();

                KhachHang khachHang = new KhachHang(maKhachHang, tenKhachHang, maHoaDon, localDate, email);
                dsKhachHang.add(khachHang);
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

        return dsKhachHang;
    }

    // Thêm khách hàng mới vào cơ sở dữ liệu
    public boolean themKhachHang(KhachHang khachHang) {
        boolean isAdded = false;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectDB.getCon();
            String sql = "INSERT INTO KhachHang (maKhachHang, tenKhachHang, maHoaDon, NgayThanhToan, email) VALUES (?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, khachHang.getMaKH());
            pstmt.setString(2, khachHang.getTenKH());
            pstmt.setString(3, khachHang.getMaHD());

            // Chuyển từ LocalDate sang java.sql.Date
            pstmt.setDate(4, java.sql.Date.valueOf(khachHang.getNgayThanhToan()));
            pstmt.setString(5, khachHang.getEmailKH());

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

    // Cập nhật thông tin khách hàng
    public boolean updateKhachHang(KhachHang khachHang) {
        boolean isUpdated = false;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectDB.getCon();
            String sql = "UPDATE KhachHang SET tenKhachHang = ?, maHoaDon = ?, NgayThanhToan = ?, email = ? WHERE maKhachHang = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, khachHang.getTenKH());
            pstmt.setString(2, khachHang.getMaHD());

            // Chuyển từ LocalDate sang java.sql.Date
            pstmt.setDate(3, java.sql.Date.valueOf(khachHang.getNgayThanhToan()));
            pstmt.setString(4, khachHang.getEmailKH());
            pstmt.setString(5, khachHang.getMaKH());

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

    // Xóa khách hàng theo mã khách hàng
    public boolean deleteKhachHang(String maKhachHang) {
        boolean isDeleted = false;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectDB.getCon();
            String sql = "DELETE FROM KhachHang WHERE maKhachHang = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, maKhachHang);

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

    // Tìm kiếm khách hàng theo mã khách hàng
    public KhachHang getKhachHangByMaKhachHang(String maKhachHang) {
        KhachHang khachHang = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getCon();
            String sql = "SELECT * FROM KhachHang WHERE maKhachHang = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, maKhachHang);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String tenKhachHang = rs.getString("tenKhachHang");
                String maHoaDon = rs.getString("maHoaDon");
                Date ngayThanhToan = rs.getDate("NgayThanhToan");
                String email = rs.getString("email");

                // Chuyển đổi từ java.sql.Date sang java.time.LocalDate
                  LocalDate localDate = ngayThanhToan.toLocalDate();

                khachHang = new KhachHang(maKhachHang, tenKhachHang, maHoaDon, localDate, email);
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

        return khachHang;
    }
}
