package entity;

public class Ghe {

	private String maSuatChieu;
	private String maGhe ; 
	private String trangthaiGhe;
	
	
	public Ghe() {
		this("", "", "");
	}
	
	public Ghe(String maGhe, String maSuatChieu, String trangthaiGhe) {
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


	public String getTrangthaiGhe() {
		return trangthaiGhe;
	}


	public void setTrangthaiGhe(String trangthaiGhe) {
		this.trangthaiGhe = trangthaiGhe;
	} 
	
	
	
	
	

}
