package entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class SuatChieu {
	private String maSuatChieu ; 
	private Phim  phim ;
	private int giaSuat; 
	private LocalDate ngayChieu ; 
	private int GioChieu ;
	private ArrayList<Ghe> dsGhe;
	
	
	public SuatChieu() {
		this("", new Phim() , 0, LocalDate.now(), 0, new ArrayList<Ghe>());
	}
	
	public SuatChieu(String maSuatChieu, Phim phim, int giaSuat, LocalDate ngayChieu, int gioChieu,
			ArrayList<Ghe> dsGhe) {
		super();
		this.maSuatChieu = maSuatChieu;
		this.phim = phim;
		this.giaSuat = giaSuat;
		this.ngayChieu = ngayChieu;
		GioChieu = gioChieu;
		this.dsGhe = dsGhe;
	}

	public String getMaSuatChieu() {
		return maSuatChieu;
	}

	public void setMaSuatChieu(String maSuatChieu) {
		this.maSuatChieu = maSuatChieu;
	}

	public Phim getPhim() {
		return phim;
	}

	public void setPhim(Phim phim) {
		this.phim = phim;
	}

	public int getGiaSuat() {
		return giaSuat;
	}

	public void setGiaSuat(int giaSuat) {
		this.giaSuat = giaSuat;
	}

	public LocalDate getNgayChieu() {
		return ngayChieu;
	}

	public void setNgayChieu(LocalDate ngayChieu) {
		this.ngayChieu = ngayChieu;
	}

	public int getGioChieu() {
		return GioChieu;
	}

	public void setGioChieu(int gioChieu) {
		GioChieu = gioChieu;
	}

	public ArrayList<Ghe> getDsGhe() {
		return dsGhe;
	}

	public void setDsGhe(ArrayList<Ghe> dsGhe) {
		this.dsGhe = dsGhe;
	} 
	
	 // equals chỉ so sánh theo mã suất chiếu
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SuatChieu other = (SuatChieu) obj;
        return maSuatChieu != null && maSuatChieu.equals(other.maSuatChieu);
    }

    @Override
    public int hashCode() {
        return maSuatChieu == null ? 0 : maSuatChieu.hashCode();
    }
	
	
	
	

}
