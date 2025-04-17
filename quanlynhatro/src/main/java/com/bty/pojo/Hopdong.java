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
public class Hopdong {

    private int MaHopDong;
    private int MaKhach;
    private int MaPhong;
    private LocalDate NgayBatDau;
    private int ThoiHan;
    private String TrangThai;
    private int SoLuongNguoiThue;
    private int ChuKyThanhToan;
    private Date NgayDenHan;
    private LocalDate NgayDenHanHopDong;
    
    public Hopdong() {
    }

    public int getMaHopDong() {
        return MaHopDong;
    }

    public Date getNgayDenHan() {
        return NgayDenHan;
    }

    public void setNgayDenHanHopDong(LocalDate NgayDenHanHopDong) {
        this.NgayDenHanHopDong = NgayDenHanHopDong;
    }

    public LocalDate getNgayDenHanHopDong() {
        return NgayDenHanHopDong;
    }

    public void setNgayDenHan(Date NgayDenHan) {
        this.NgayDenHan = NgayDenHan;
    }

    public int getChuKyThanhToan() {
        return ChuKyThanhToan;
    }

    public void setChuKyThanhToan(int ChuKyThanhToan) {
        this.ChuKyThanhToan = ChuKyThanhToan;
    }
    
    public int getMaKhach() {
        return MaKhach;
    }

    public LocalDate getNgayBatDau() {
        return NgayBatDau;
    }

    public int getMaPhong() {
        return MaPhong;
    }

    public int getThoiHan() {
        return ThoiHan;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setMaHopDong(int MaHopDong) {
        this.MaHopDong = MaHopDong;
    }

    public void setMaKhach(int MaKhach) {
        this.MaKhach = MaKhach;
    }

    public void setMaPhong(int MaPhong) {
        this.MaPhong = MaPhong;
    }

    public void setNgayBatDau(LocalDate NgayBatDau) {
        this.NgayBatDau = NgayBatDau;
    }

    public void setThoiHan(int ThoiHan) {
        this.ThoiHan = ThoiHan;
    }

    public int getSoLuongNguoiThue() {
        return SoLuongNguoiThue;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public void setSoLuongNguoiThue(int SoLuongNguoiThue) {
        this.SoLuongNguoiThue = SoLuongNguoiThue;
    }
    
    
    public Hopdong(int MaHopDong,int MaKhach, int MaPhong, LocalDate NgayBatDau, int ThoiHan, String TrangThai,int SoLuongNguoiThue,
            int ChuKyThanhToan,Date NgayDenHan,LocalDate NgayDenHanHopDong) {
        this.MaHopDong=MaHopDong;
        this.MaKhach = MaKhach;
        this.MaPhong = MaPhong;
        this.NgayBatDau = NgayBatDau;
        this.ThoiHan = ThoiHan;
        this.TrangThai = TrangThai;
        this.SoLuongNguoiThue = SoLuongNguoiThue;
        this.ChuKyThanhToan = ChuKyThanhToan;
        this.NgayDenHan = NgayDenHan;
        this.NgayDenHanHopDong = NgayDenHanHopDong;
    }
}
