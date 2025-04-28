package entity;

public class DichVu {
    private String maDichVu;
    private String tenDichVu;
    private int giaDichVu;

    // Constructor mặc định
    public DichVu() {
        this("", "", 0);
    }

    // Constructor với tham số
    public DichVu(String maDichVu, String tenDichVu, int giaDichVu) {
        this.maDichVu = maDichVu;
        this.tenDichVu = tenDichVu;
        this.giaDichVu = giaDichVu;
    }

    // Getter và Setter
    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public int getGiaDichVu() {
        return giaDichVu;
    }

    public void setGiaDichVu(int giaDichVu) {
        this.giaDichVu = giaDichVu;
    }
}

