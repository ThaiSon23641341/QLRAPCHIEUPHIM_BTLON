package entity;

import java.time.LocalDate;

public class KhachHang {
	private String  maKH; 
	private String  tenKH; 
	private String  maHD; 
	private LocalDate ngayThanhToan ; 
	private String emailKH;
	
	public KhachHang() {

		this("", "", "", LocalDate.now(), "");
	}
	
	public KhachHang(String maKH, String tenKH, String maHD, LocalDate ngayThanhToan, String emailKH) {
		super();
		this.maKH = maKH;
		this.tenKH = tenKH;
		this.maHD = maHD;
		this.ngayThanhToan = ngayThanhToan;
		this.emailKH = emailKH;
	}


	public String getMaKH() {
		return maKH;
	}


	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}


	public String getTenKH() {
		return tenKH;
	}


	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}


	public String getMaHD() {
		return maHD;
	}


	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}


	public LocalDate getNgayThanhToan() {
		return ngayThanhToan;
	}


	public void setNgayThanhToan(LocalDate ngayThanhToan) {
		this.ngayThanhToan = ngayThanhToan;
	}


	public String getEmailKH() {
		return emailKH;
	}


	public void setEmailKH(String emailKH) {
		this.emailKH = emailKH;
	}
	
	
	
	
	
	
}
