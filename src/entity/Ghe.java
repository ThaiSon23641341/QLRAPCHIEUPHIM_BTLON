package entity;

public class Ghe {

	private String maSuatChieu;
	private String maGhe ; 
	private boolean trangthaiGhe;
	
	
	public Ghe() {
		this("", "", true);
	}
	
	public Ghe(String maSuatChieu, String maGhe, boolean trangthaiGhe) {
		super();
		this.maSuatChieu = maSuatChieu;
		this.maGhe = maGhe;
		this.trangthaiGhe = trangthaiGhe;
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


	public boolean isTrangthaiGhe() {
		return trangthaiGhe;
	}


	public void setTrangthaiGhe(boolean trangthaiGhe) {
		this.trangthaiGhe = trangthaiGhe;
	} 
	
	
	
	
	

}
