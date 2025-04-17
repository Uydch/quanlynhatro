/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bty.pojo;

/**
 *
 * @author User
 */
public class Khachthue {
    private int Makhach;
    private String HoTen;
    private String CMND;
    private String SDT;
    private String DiaChi;

    public Khachthue() {
    }

    public Khachthue(String HoTen, String CMND, String SDT, String DiaChi) {
        this.HoTen = HoTen;
        this.CMND = CMND;
        this.SDT = SDT;
        this.DiaChi = DiaChi;
    }

    public String getCMND() {
        return CMND;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public String getHoTen() {
        return HoTen;
    }

    public String getSDT() {
        return SDT;
    }

    public int getMakhach() {
        return Makhach;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public void setMakhach(int Makhach) {
        this.Makhach = Makhach;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

}
