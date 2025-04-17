/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bty.service;

import com.bty.pojo.JdbcUtils;
import com.bty.pojo.Phongtro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author User
 */
public class PhongtroServices {

    public List<Phongtro> getPhongtro(String kw) throws SQLException {
        List<Phongtro> results = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM phongtro";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE TenPhong LIKE concat('%', ?, '%') OR "
                        + "TrangThai LIKE concat('%', ?, '%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
                stm.setString(2, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Phongtro q = new Phongtro(rs.getInt("Maphong"),rs.getString("TenPhong"),
                        rs.getString("TrangThai"), rs.getInt("GiaThue"));
                results.add(q);
            }
        }
        return results;
    }

    public boolean kiemTraThongTin(String hoTen, String sdt, String cccd, String diaChi, String tenPhong) {
        return !hoTen.trim().isEmpty()
            && !sdt.trim().isEmpty()
            && !cccd.trim().isEmpty()
            && !diaChi.trim().isEmpty()
            && !tenPhong.trim().isEmpty();
    }
    
    public boolean kiemTraThongTinThemPhong(String tenPhonng, String giaThue) {
        return !tenPhonng.trim().isEmpty()
            && !giaThue.trim().isEmpty();
    }

    public void hienThiThongBaoThieuThongTin() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Thiếu thông tin");
        alert.setHeaderText(null);
        alert.setContentText("Vui lòng nhập đầy đủ thông tin!");
        alert.showAndWait();
    }

    public boolean kiemTraTrangThaiPhong(Phongtro phongtro) {
        return "Trống".equalsIgnoreCase(phongtro.getTrangThai());
    }

    public void hienThiThongBaoPhongDaCoNguoi() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Thông báo");
        alert.setContentText("Phòng này đã có người thuê, vui lòng chọn phòng khác!");
        alert.showAndWait();
    }

    public boolean themPhong(Phongtro p) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO phongtro (TenPhong, TrangThai,GiaThue)"
                    + " VALUES (?, ?, ?)");
            stm.setString(1, p.getTenPhong());
            stm.setString(2, "Trống");
            stm.setInt(3, p.getGiaThue());
        return stm.executeUpdate() > 0;
        }
    }
    
    public boolean xoaPhong(int Maphong) throws SQLException{
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareCall("DELETE FROM phongtro WHERE Maphong=?");
            stm.setInt(1, Maphong);
            return stm.executeUpdate() > 0;
        }
    }
}
