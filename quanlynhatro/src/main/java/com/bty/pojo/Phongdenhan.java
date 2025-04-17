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
public class Phongdenhan {
    private int MaPhong;
    private int MaHopDong;
    private String TenPhong;
    private int GiaThue;
    private String HoTenNguoiThue;
    private LocalDate NgayDenHan;
    private Double PhiPhat;
    private Double TongTien;
    private String TrangThaiThanhToan;
    private Double DuNo;

    public Phongdenhan() {
    }
    
    public Phongdenhan(int MaPhong,int MaHopDong, String TenPhong, int GiaThue, String HoTenNguoiThue, LocalDate NgayDenHan) {
        this.MaPhong = MaPhong;
        this.TenPhong = TenPhong;
        this.GiaThue = GiaThue;
        this.HoTenNguoiThue = HoTenNguoiThue;
        this.NgayDenHan = NgayDenHan;
        this.MaHopDong = MaHopDong;
//        this.TongTien = TongTien;
//        this.TrangThaiThanhToan = TrangThaiThanhToan;
    }

    public Double getDuNo() {
        return DuNo;
    }

    public void setMaHopDong(int MaHopDong) {
        this.MaHopDong = MaHopDong;
    }

    public int getMaHopDong() {
        return MaHopDong;
    }

    public void setDuNo(Double DuNo) {
        this.DuNo = DuNo;
    }
    
    public int getGiaThue() {
        return GiaThue;
    }
    
    public String getHoTenNguoiThue() {
        return HoTenNguoiThue;
    }

    public int getMaPhong() {
        return MaPhong;
    }

    public LocalDate getNgayDenHan() {
        return NgayDenHan;
    }

    public Double getPhiPhat() {
        return PhiPhat;
    }

    public String getTenPhong() {
        return TenPhong;
    }

    public Double getTongTien() {
        return TongTien;
    }

    public String getTrangThaiThanhToan() {
        return TrangThaiThanhToan;
    }

    public void setGiaThue(int GiaThue) {
        this.GiaThue = GiaThue;
    }

    public void setHoTenNguoiThue(String HoTenNguoiThue) {
        this.HoTenNguoiThue = HoTenNguoiThue;
    }

    public void setMaPhong(int MaPhong) {
        this.MaPhong = MaPhong;
    }

    public void setNgayDenHan(LocalDate NgayDenHan) {
        this.NgayDenHan = NgayDenHan;
    }

    public void setPhiPhat(Double PhiPhat) {
        this.PhiPhat = PhiPhat;
    }

    public void setTenPhong(String TenPhong) {
        this.TenPhong = TenPhong;
    }

    public void setTongTien(Double TongTien) {
        this.TongTien = TongTien;
    }

    public void setTrangThaiThanhToan(String TrangThaiThanhToan) {
        this.TrangThaiThanhToan = TrangThaiThanhToan;
    }

    public void setTiennuoc(Double newValue) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
