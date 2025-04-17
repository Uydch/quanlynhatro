/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bty.pojo;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author User
 */
public class Thanhtoan {

    private int MaThanhToan;
    private int SoTien;
    private Date NgayThanhToan;
    private int MaHopDong;
    private int MaPhong;
    private String TenPhong;
    private int GiaThue;
    private String HoTenNguoiThue;
    private LocalDate NgayDenHan;
    private Double PhiPhat;
    private Double ChiSoDien;
    private Double TienDien;
    private Double TienNuoc;
    private Double ChiSoNuoc;
    private Double TongTien;
    private String TrangThaiThanhToan;
    private Double DuNo;

    public Thanhtoan() {
    }

    public Thanhtoan(int MathanhToan, int MaPhong, int SoTien, Date NgayThanhToan, int MaHopDong, String TenPhong, int GiaThue, String HoTenNguoiThue, LocalDate NgayDenHan, Double PhiPhat, String TrangThaiThanhToan, Double DuNo, Double ChiSoDien, Double ChiSoNuoc) {
        this.MaThanhToan = MathanhToan;
        this.MaPhong = MaPhong;
        this.SoTien = SoTien;
        this.NgayThanhToan = NgayThanhToan;
        this.MaHopDong = MaHopDong;
        this.TenPhong = TenPhong;
        this.GiaThue = GiaThue;
        this.HoTenNguoiThue = HoTenNguoiThue;
        this.NgayDenHan = NgayDenHan;
        this.PhiPhat = PhiPhat;
        this.TrangThaiThanhToan = TrangThaiThanhToan;
        this.DuNo = DuNo;
        this.ChiSoDien = ChiSoDien;
        this.ChiSoNuoc = ChiSoNuoc;
    }

    public Double getDuNo() {
        return DuNo;
    }

    public Double getTienDien() {
        return TienDien;
    }

    public Double getTienNuoc() {
        return TienNuoc;
    }

    public void setTienDien(Double TienDien) {
        this.TienDien = TienDien;
    }

    public void setTienNuoc(Double TienNuoc) {
        this.TienNuoc = TienNuoc;
    }

    
    public int getGiaThue() {
        return GiaThue;
    }

    public String getHoTenNguoiThue() {
        return HoTenNguoiThue;
    }

    public int getMaHopDong() {
        return MaHopDong;
    }

    public int getMaThanhToan() {
        return MaThanhToan;
    }

    public LocalDate getNgayDenHan() {
        return NgayDenHan;
    }

    public Date getNgayThanhToan() {
        return NgayThanhToan;
    }

    public Double getPhiPhat() {
        return PhiPhat;
    }

    public int getSoTien() {
        return SoTien;
    }

    public String getTenPhong() {
        return TenPhong;
    }

    public String getTrangThaiThanhToan() {
        return TrangThaiThanhToan;
    }

    public void setDuNo(Double DuNo) {
        this.DuNo = DuNo;
    }

    public void setGiaThue(int GiaThue) {
        this.GiaThue = GiaThue;
    }

    public void setHoTenNguoiThue(String HoTenNguoiThue) {
        this.HoTenNguoiThue = HoTenNguoiThue;
    }

    public void setMaHopDong(int MaHopDong) {
        this.MaHopDong = MaHopDong;
    }

    public void setMaThanhToan(int MaThanhToan) {
        this.MaThanhToan = MaThanhToan;
    }

    public void setNgayDenHan(LocalDate NgayDenHan) {
        this.NgayDenHan = NgayDenHan;
    }

    public void setNgayThanhToan(Date NgayThanhToan) {
        this.NgayThanhToan = NgayThanhToan;
    }

    public void setPhiPhat(Double PhiPhat) {
        this.PhiPhat = PhiPhat;
    }

    public void setSoTien(int SoTien) {
        this.SoTien = SoTien;
    }

    public void setTenPhong(String TenPhong) {
        this.TenPhong = TenPhong;
    }

    public void setTrangThaiThanhToan(String TrangThaiThanhToan) {
        this.TrangThaiThanhToan = TrangThaiThanhToan;
    }

    public Double getChiSoDien() {
        return ChiSoDien;
    }

    public Double getChiSoNuoc() {
        return ChiSoNuoc;
    }

    public void setChiSoDien(Double ChiSoDien) {
        this.ChiSoDien = ChiSoDien;
    }

    public void setChiSoNuoc(Double ChiSoNuoc) {
        this.ChiSoNuoc = ChiSoNuoc;
    }

    public int getMaPhong() {
        return MaPhong;
    }

    public void setMaPhong(int MaPhong) {
        this.MaPhong = MaPhong;
    }

    public Double getTongTien() {
        return TongTien;
    }

    public void setTongTien(Double TongTien) {
        this.TongTien = TongTien;
    }

    public void setTienDien(double tongTienDien) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
