package entity;

public class ChiTietHoaDon {
    private String maChiTietHoaDon;
    private String maSuatChieu;
    private String maGhe;
    private String tenPhim;
    private String maPhim;
    private String maDichVuThem;
    private int soLuongDichVu;
    private int giaSuat;
    private int giaDichVu;
    private int tongTien;

    // Constructor mặc định
    public ChiTietHoaDon() {
        this("", "", "", "", "", "", 0, 0, 0, 0);
    }

    // Constructor với tham số
    public ChiTietHoaDon(String maChiTietHoaDon, String maSuatChieu, String maGhe, String tenPhim, String maPhim,
                         String maDichVuThem, int soLuongDichVu, int giaSuat, int giaDichVu, int tongTien) {
        this.maChiTietHoaDon = maChiTietHoaDon;
        this.maSuatChieu = maSuatChieu;
        this.maGhe = maGhe;
        this.tenPhim = tenPhim;
        this.maPhim = maPhim;
        this.maDichVuThem = maDichVuThem;
        this.soLuongDichVu = soLuongDichVu;
        this.giaSuat = giaSuat;
        this.giaDichVu = giaDichVu;
        this.tongTien = tongTien;
    }

    // Getter và Setter
    public String getMaChiTietHoaDon() {
        return maChiTietHoaDon;
    }

    public void setMaChiTietHoaDon(String maChiTietHoaDon) {
        this.maChiTietHoaDon = maChiTietHoaDon;
    }

    public String getMaSuatChieu() {
        return maSuatChieu;
    }

    public void setMaSuatChieu(String maSuatChieu) {
        this.maSuatChieu = maSuatChieu;
    }

    public String getMaGhe() {
        return maGhe;
    }

    public void setMaGhe(String maGhe) {
        this.maGhe = maGhe;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }

    public String getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(String maPhim) {
        this.maPhim = maPhim;
    }

    public String getMaDichVuThem() {
        return maDichVuThem;
    }

    public void setMaDichVuThem(String maDichVuThem) {
        this.maDichVuThem = maDichVuThem;
    }

    public int getSoLuongDichVu() {
        return soLuongDichVu;
    }

    public void setSoLuongDichVu(int soLuongDichVu) {
        this.soLuongDichVu = soLuongDichVu;
    }

    public int getGiaSuat() {
        return giaSuat;
    }

    public void setGiaSuat(int giaSuat) {
        this.giaSuat = giaSuat;
    }

    public int getGiaDichVu() {
        return giaDichVu;
    }

    public void setGiaDichVu(int giaDichVu) {
        this.giaDichVu = giaDichVu;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
