/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bty.pojo;

/**
 *
 * @author User
 */
public class Phongtro {
    private int MaPhong;
    private String TenPhong;
    private String TrangThai;
    private int GiaThue;

    public Phongtro() {
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public String getTenPhong() {
        return TenPhong;
    }

    public int getGiaThue() {
        return GiaThue;
    }

    public int getMaPhong() {
        return MaPhong;
    }

    public void setGiaThue(int GiaThue) {
        this.GiaThue = GiaThue;
    }

    public void setMaPhong(int MaPhong) {
        this.MaPhong = MaPhong;
    }

    public void setTenPhong(String TenPhong) {
        this.TenPhong = TenPhong;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }
    
    

    public Phongtro(String TenPhong, String TrangThai, int GiaThue) {
        this.TenPhong = TenPhong;
        this.TrangThai = TrangThai;
        this.GiaThue = GiaThue;
    }

    public Phongtro(int Maphong,String TenPhong, String TrangThai, int GiaThue) {
        this.MaPhong = Maphong;
        this.TenPhong = TenPhong;
        this.TrangThai = TrangThai;
        this.GiaThue = GiaThue;
    }
     @Override
    public String toString() {
        return String.format("Phongtro {MaPhong=%d, TenPhong=%s, TrangThai=%s, GiaThue=%d}", 
                             MaPhong, TenPhong, TrangThai, GiaThue);
    }
}
