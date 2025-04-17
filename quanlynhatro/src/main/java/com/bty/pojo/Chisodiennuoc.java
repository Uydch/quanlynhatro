/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bty.pojo;

/**
 *
 * @author User
 */
public class Chisodiennuoc {
    private int MaChiSo;
    private int MaPhong;
    private int TenPhong;
    private int NgayDenHan;
    private int MaThanhToan;
    private int ChiSoDien;
    private int ChiSoNuoc;
    
    public Chisodiennuoc() {
    }

    public int getChiSoDien() {
        return ChiSoDien;
    }

    public int getChiSoNuoc() {
        return ChiSoNuoc;
    }

    public int getMaChiSo() {
        return MaChiSo;
    }

    public int getMaThanhToan() {
        return MaThanhToan;
    }

    public void setChiSoDien(int ChiSoDien) {
        this.ChiSoDien = ChiSoDien;
    }

    public void setChiSoNuoc(int ChiSoNuoc) {
        this.ChiSoNuoc = ChiSoNuoc;
    }

    public void setMaChiSo(int MaChiSo) {
        this.MaChiSo = MaChiSo;
    }

    public void setMaThanhToan(int MaThanhToan) {
        this.MaThanhToan = MaThanhToan;
    }

    public Chisodiennuoc(int MaChiSo, int MaPhong, int TenPhong, int NgayDenHan, int MaThanhToan) {
        this.MaChiSo = MaChiSo;
        this.MaPhong = MaPhong;
        this.TenPhong = TenPhong;
        this.NgayDenHan = NgayDenHan;
        this.MaThanhToan = MaThanhToan;
    }

    
}
