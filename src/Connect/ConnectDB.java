package Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static Connection con = null; 
    private static ConnectDB instance = new ConnectDB();
    
    // Lấy kết nối
    public static Connection getCon() {
        if (con == null) {
            try {
                getInstance().connect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return con;
    }

    // Kết nối tới cơ sở dữ liệu
    public void connect() throws SQLException {
        if (con == null) {
            String url = "jdbc:sqlserver://localhost:1433;databasename=QuanLyRapPhim";  // Đảm bảo đúng URL
            String user = "sa";
            String password ="sapassword";
            
            try {
                con = DriverManager.getConnection(url,user, password);
                System.out.println("Kết nối thành công!");
            } catch (SQLException e) {
                System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
                throw e;
            }
        }
    }

    // Ngắt kết nối
    public void disconnect() {
        if (con != null) {
            try {
                con.close();
               
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Lấy instance của ConnectDB (Singleton)
    public static ConnectDB getInstance() {
        return instance;
    }
}
