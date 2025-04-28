package entity;

public class HoaDon {
    private String maHoaDon;
    private String maChiTietHoaDon;
    private int tongTien;
    private String maKhachHang;

    // Constructor mặc định
    public HoaDon() {
        this("", "", 0, "");
    }

    // Constructor với tham số
    public HoaDon(String maHoaDon, String maChiTietHoaDon, int tongTien, String maKhachHang) {
        this.maHoaDon = maHoaDon;
        this.maChiTietHoaDon = maChiTietHoaDon;
        this.tongTien = tongTien;
        this.maKhachHang = maKhachHang;
    }

    // Getter và Setter
    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaChiTietHoaDon() {
        return maChiTietHoaDon;
    }

    public void setMaChiTietHoaDon(String maChiTietHoaDon) {
        this.maChiTietHoaDon = maChiTietHoaDon;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }
}
