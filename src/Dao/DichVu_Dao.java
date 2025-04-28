package Dao;

import java.sql.*;
import java.util.ArrayList;
import Connect.ConnectDB;
import entity.DichVu;

public class DichVu_Dao {

    public DichVu_Dao() {
    }

    // Lấy tất cả dịch vụ từ cơ sở dữ liệu
    public ArrayList<DichVu> getAllDichVu() {
        ArrayList<DichVu> dsDichVu = new ArrayList<DichVu>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getCon();
            String sql = "SELECT * FROM DichVu";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String maDichVu = rs.getString("maDichVu");
                String tenDichVu = rs.getString("tenDichVu");
                int giaDichVu = rs.getInt("giaDichVu");

                DichVu dichVu = new DichVu(maDichVu, tenDichVu, giaDichVu);
                dsDichVu.add(dichVu);
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
        return dsDichVu;
    }

    // Thêm dịch vụ mới vào cơ sở dữ liệu
    public boolean themDichVu(DichVu dichVu) {
        boolean isAdded = false;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectDB.getCon();
            String sql = "INSERT INTO DichVu (maDichVu, tenDichVu, giaDichVu) VALUES (?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dichVu.getMaDichVu());
            pstmt.setString(2, dichVu.getTenDichVu());
            pstmt.setInt(3, dichVu.getGiaDichVu());

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

    // Cập nhật thông tin dịch vụ
    public boolean updateDichVu(DichVu dichVu) {
        boolean isUpdated = false;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectDB.getCon();
            String sql = "UPDATE DichVu SET tenDichVu = ?, giaDichVu = ? WHERE maDichVu = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dichVu.getTenDichVu());
            pstmt.setInt(2, dichVu.getGiaDichVu());
            pstmt.setString(3, dichVu.getMaDichVu());

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
}
