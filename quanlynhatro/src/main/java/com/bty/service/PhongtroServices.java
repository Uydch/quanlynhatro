/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bty.service;

import com.bty.pojo.JdbcUtils;
import com.bty.pojo.MessageBox;
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
                Phongtro q = new Phongtro(rs.getInt("Maphong"), rs.getString("TenPhong"),
                        rs.getString("TrangThai"), rs.getInt("GiaThue"));
                results.add(q);
            }
        }
        return results;
    }

    public boolean kiemTraThongTin(String hoTen, String sdt, String cccd, String diaChi, String tenPhong) {
        if (hoTen.trim().isEmpty() || sdt.trim().isEmpty() || cccd.trim().isEmpty() || diaChi.trim().isEmpty() || tenPhong.trim().isEmpty()) {
            return false;
        }
        if (!kiemTraSdtHopLe(sdt)) {
            System.out.println("Số điện thoại không hợp lệ. Phải là chuỗi số từ 10 đến 11 ký tự.");
            return false;
        }

        if (!kiemTraCccdHopLe(cccd)) {
            System.out.println("CCCD không hợp lệ. Phải là chuỗi số 12 ký tự.");
            return false;
        }

        return true;
    }

    public boolean kiemTraThongTinThemPhong(String tenPhonng, String giaThue) {
        return !tenPhonng.trim().isEmpty()
                && !giaThue.trim().isEmpty();
    }

    public void hienThiThongBaoThieuThongTin() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Thiếu thông tin");
        alert.setHeaderText(null);
        alert.setContentText("Vui lòng nhập đầy đủ và chính xác thông tin!(sđt 10-12 số , cccd 12 số)");
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

    public boolean kiemTraSdtHopLe(String sdt) {
        return sdt.matches("\\d{10,11}");
    }

    public boolean kiemTraCccdHopLe(String cccd) {
        return cccd.matches("\\d{12}");
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

//    public boolean xoaPhong(int Maphong) throws SQLException {
//        try (Connection conn = JdbcUtils.getConn()) {
//            PreparedStatement stm = conn.prepareCall("DELETE FROM phongtro WHERE Maphong=?");
//            stm.setInt(1, Maphong);
//            return stm.executeUpdate() > 0;
//        }
//    }
    
    public boolean xoaPhong(int maPhong) throws SQLException {
    String sqlCheck = "SELECT TrangThai FROM phongtro WHERE MaPhong = ?";
    try (Connection conn = JdbcUtils.getConn(); PreparedStatement stmt = conn.prepareStatement(sqlCheck)) {
        stmt.setInt(1, maPhong);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            String trangThai = rs.getString("TrangThai");
            if ("Có người".equals(trangThai)) {
                MessageBox.getBox("Lỗi", "Không thể xóa phòng đang có người thuê!", Alert.AlertType.WARNING);
                return false; // Không xóa phòng
            }

            String sqlDelete = "DELETE FROM phongtro WHERE MaPhong = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(sqlDelete)) {
                deleteStmt.setInt(1, maPhong);
                int rowsAffected = deleteStmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Phòng đã được xóa thành công.");
                    return true; 
                } else {
                    System.out.println("Không tìm thấy phòng để xóa.");
                    return false; // Không tìm thấy phòng
                }
            }
        } else {
            MessageBox.getBox("Lỗi", "Không tìm thấy phòng trong cơ sở dữ liệu.", Alert.AlertType.ERROR);
            return false;
        }
    }
}
}
