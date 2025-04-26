/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bty.service;

import com.bty.pojo.JdbcUtils;
import com.bty.pojo.Hopdong;
import com.bty.pojo.Khachthue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author User
 */
public class KyHDServices {

//    public List<Hopdong> getHopdong(String kw) throws SQLException {
//        List<Hopdong> results = new ArrayList<>();
//        try (Connection conn = JdbcUtils.getConn()) {
//            String sql = "SELECT * FROM phongtro";
//            if (kw != null && !kw.isEmpty()) {
//                sql += " WHERE MaPhong LIKE concat('%', ?, '%') OR "
//                        + "TenPhong LIKE concat('%', ?, '%') OR "
//                        + "TrangThai LIKE concat('%', ?, '%')";
//            }
//            PreparedStatement stm = conn.prepareCall(sql);
//            if (kw != null && !kw.isEmpty()) {
//                stm.setString(1, kw);
//                stm.setString(2, kw);
//                stm.setString(3, kw);
//            }
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                Phongtro q = new Phongtro(rs.getString("MaPhong"), rs.getString("TenPhong"),
//                        rs.getString("TrangThai"), rs.getInt("GiaThue"));
//                results.add(q);
//            }
//        }
//        return results;
//    }
    //
    public void gioiHanTienCoc(TextField TienCoc) {
        TienCoc.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
            }
        });
    }

    public void gioiHanNguoiThue(TextField SoLuong) {
        SoLuong.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
            }
        });
//        SoLuong.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.isEmpty()) {
//                try {
//                    int value = Integer.parseInt(newValue);
//                    if (value < 1) {
//                        SoLuong.setText("3");
//                    }
//
//                } catch (NumberFormatException e) {
//                    SoLuong.setText(oldValue);
//                }
//            }
//        });
    }

    public boolean kiemTraThongTin(String hoTen, String sdt, String cccd,
            String diaChi, String tenPhong, String thoiHan, String tienCoc, String SoLuongNguoiThue, boolean CB) {
        return !hoTen.trim().isEmpty()
                && !sdt.trim().isEmpty()
                && !cccd.trim().isEmpty()
                && !diaChi.trim().isEmpty()
                && !thoiHan.trim().isEmpty()
                && !tienCoc.trim().isEmpty()
                && CB
                && !SoLuongNguoiThue.trim().isEmpty()
                && !tenPhong.trim().isEmpty();
    }

    public void hienThiThongBaoThieuThongTin() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Thiếu thông tin");
        alert.setHeaderText(null);
        alert.setContentText("Vui lòng nhập đầy đủ thông tin và đồng ý các điều khoản!");
        alert.showAndWait();
    }

    //
    public int luuKhachthue(Khachthue k) throws SQLException {
        if (k.getHoTen().trim().isEmpty() || k.getCMND().trim().isEmpty() || k.getSDT().trim().isEmpty() || k.getDiaChi().trim().isEmpty()) {
            // Nếu có trường nào trống, trả về -1 hoặc có thể ném ra ngoại lệ
            System.out.println("Thông tin khách thuê không đầy đủ!");
            return -1;  // Trả về -1 nếu thông tin không hợp lệ
        }
        String sql = "INSERT INTO khachthue (HoTen, CMND, SDT, DiaChi) VALUES (?, ?, ?, ?)";
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm1 = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm1.setString(1, k.getHoTen());
            stm1.setString(2, k.getCMND());
            stm1.setString(3, k.getSDT());
            stm1.setString(4, k.getDiaChi());

            stm1.executeUpdate();
            try (ResultSet rs = stm1.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    //
    public int maPhongTheoTen(String tenPhong) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm2 = conn.prepareStatement("SELECT MaPhong FROM phongtro WHERE TenPhong = ?");
            stm2.setString(1, tenPhong);

            try (ResultSet rs = stm2.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("MaPhong");
                }
            }
        }
        return -1;
    }

    //
    public boolean luuHopDong(Hopdong h) throws SQLException {
        if (h.getMaKhach() <= 0 || h.getMaPhong() <= 0 || h.getNgayBatDau() == null || h.getThoiHan() <= 0
                || h.getSoLuongNguoiThue() <= 0 || h.getChuKyThanhToan() <= 0 || h.getNgayDenHan() == null
                || h.getNgayDenHanHopDong() == null) {
            System.out.println("Thông tin hợp đồng không đầy đủ!");
            return false;
        }
        int r = 0;
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm3 = conn.prepareStatement("INSERT INTO hopdong (MaKhach, MaPhong,"
                    + " NgayBatDau,ThoiHan,TrangThai,SoLuongNguoiThue,ChuKyThanhToan,NgayDenHan,NgayDenHanHopDong) VALUES (?,?,?,?,?,?,?,?,?)");

            stm3.setInt(1, h.getMaKhach());
            stm3.setInt(2, h.getMaPhong());
            stm3.setDate(3, java.sql.Date.valueOf(h.getNgayBatDau()));
            stm3.setInt(4, h.getThoiHan());
            stm3.setString(5, "Có hiệu lực");
            stm3.setInt(6, h.getSoLuongNguoiThue());
            stm3.setInt(7, h.getChuKyThanhToan());
            stm3.setDate(8, h.getNgayDenHan());
            stm3.setDate(9, java.sql.Date.valueOf(h.getNgayDenHanHopDong()));
            stm3.executeUpdate();

            PreparedStatement stm4 = conn.prepareStatement("UPDATE phongtro SET TrangThai = ? WHERE MaPhong = ? ");
            stm4.setString(1, "Có Người");
            stm4.setInt(2, h.getMaPhong());
            stm4.executeUpdate();
            conn.commit();
            r++;
        }
        return r > 0;
    }

    public void thongBaoThuePhongThanhCong() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành Công!");
        alert.setContentText("Hợp đồng thuê phòng lưu thành công!!!");
        alert.showAndWait();
    }

    public int chuanHoaThoiHan(int thoiHan) {
        return thoiHan < 3 ? 3 : thoiHan;
    }
}
