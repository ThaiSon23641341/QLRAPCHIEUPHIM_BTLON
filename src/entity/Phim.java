package entity;

public class Phim {
	private String maPhim;
	private String tenPhim;
	private String theloaiPhim;
	private String motaPhim;
	private int dgPhim;
	private String tenDaoDien;
	private String nsx;
	private int thoiluongPhim;
	private String duongdanPoster;

	public Phim() {
		this("", "", "", "", 0, "", "", 0,"");

	}

	public Phim(String maPhim, String tenPhim, String theloaiPhim, String motaPhim, int dgPhim, String tenDaoDien,
			String nsx,int thoiluongPHim ,String duongdanPoster) {
		this.maPhim = maPhim;
		this.tenPhim = tenPhim;
		this.theloaiPhim = theloaiPhim;
		this.motaPhim = motaPhim;
		this.dgPhim = dgPhim;
		this.tenDaoDien = tenDaoDien;
		this.nsx = nsx;
		this.thoiluongPhim = thoiluongPHim;
		this.duongdanPoster = duongdanPoster;
	}
	
	

	public int getThoiluongPhim() {
		return thoiluongPhim;
	}

	public void setThoiluongPhim(int thoiluongPhim) {
		this.thoiluongPhim = thoiluongPhim;
	}

	public String getMaPhim() {
		return maPhim;
	}

	public void setMaPhim(String maPhim) {
		this.maPhim = maPhim;
	}

	public String getTenPhim() {
		return tenPhim;
	}

	public void setTenPhim(String tenPhim) {
		this.tenPhim = tenPhim;
	}

	public String getTheloaiPhim() {
		return theloaiPhim;
	}

	public void setTheloaiPhim(String theloaiPhim) {
		this.theloaiPhim = theloaiPhim;
	}

	public String getMotaPhim() {
		return motaPhim;
	}

	public void setMotaPhim(String motaPhim) {
		this.motaPhim = motaPhim;
	}

	public int getDgPhim() {
		return dgPhim;
	}

	public void setDgPhim(int dgPhim) {
		if (dgPhim > 10 || dgPhim > 0) {
			throw new IllegalArgumentException("Đánh giá phải lớn hơn 0 và nhỏ hơn 5");
		}

		this.dgPhim = dgPhim;
	}

	public String getTenDaoDien() {
		return tenDaoDien;
	}

	public void setTenDaoDien(String tenDaoDien) {
		this.tenDaoDien = tenDaoDien;
	}

	public String getNsx() {
		return nsx;
	}

	public void setNsx(String nsx) {
		this.nsx = nsx;
	}

	public String getDuongdanPoster() {
		return duongdanPoster;
	}

	public void setDuongdanPoster(String duongdanPoster) {
		this.duongdanPoster = duongdanPoster;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Phim other = (Phim) obj;
		return maPhim != null && maPhim.equals(other.maPhim);
	}

	@Override
	public int hashCode() {
		return maPhim == null ? 0 : maPhim.hashCode();
	}
}
