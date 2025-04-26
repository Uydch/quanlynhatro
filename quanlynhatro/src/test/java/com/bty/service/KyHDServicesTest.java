/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bty.service;

import com.bty.pojo.Hopdong;
import com.bty.pojo.JdbcUtils;
import com.bty.pojo.Khachthue;
import com.bty.pojo.Phongtro;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static java.time.Clock.system;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author User
 */
public class KyHDServicesTest {

    private static KyHDServices s;
    private static Connection conn;

    @BeforeAll
    public static void beforeAll() throws SQLException {
        s = new KyHDServices();
        conn = JdbcUtils.getConn();
    }

    @AfterAll
    public static void aftereAll() throws SQLException {
        if (conn != null) {
            conn.close(); // Đảm bảo đóng kết nối khi kiểm thử hoàn tất
        }
    }

    @Test
    public void testKiemTraThongTin_HopLe() {
        String hoTen = "Nguyen Van A";
        String sdt = "0123456789";
        String cccd = "123456789012";
        String diaChi = "Hà Nội";
        String tenPhong = "Phòng 101";
        String thoiHan = "12 tháng";
        String tienCoc = "1000000";
        String SoLuongNguoiThue = "2";
        boolean CB = true;
        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong, thoiHan, tienCoc, SoLuongNguoiThue, CB);
        assertTrue(result, "Thông tin hợp lệ nhưng phương thức trả về false");
    }

    @Test
    public void testKiemTraThongTin_TieuDeHoTen() {
        String hoTen = "";
        String sdt = "0123456789";
        String cccd = "123456789012";
        String diaChi = "Hà Nội";
        String tenPhong = "Phòng 101";
        String thoiHan = "12 tháng";
        String tienCoc = "1000000";
        String SoLuongNguoiThue = "2";
        boolean CB = true;

        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong, thoiHan, tienCoc, SoLuongNguoiThue, CB);

        assertFalse(result, "Phương thức không phát hiện thiếu họ tên");
    }

    @Test
    public void testKiemTraThongTin_TieuDeSdt() {
        String hoTen = "Nguyen Van A";
        String sdt = "";
        String cccd = "123456789012";
        String diaChi = "Hà Nội";
        String tenPhong = "Phòng 101";
        String thoiHan = "12 tháng";
        String tienCoc = "1000000";
        String SoLuongNguoiThue = "2";
        boolean CB = true;

        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong, thoiHan, tienCoc, SoLuongNguoiThue, CB);

        assertFalse(result, "Phương thức không phát hiện thiếu số điện thoại");
    }

    @Test
    public void testKiemTraThongTin_TieuDeCccd() {
        String hoTen = "Nguyen Van A";
        String sdt = "0123456789";
        String cccd = "";
        String diaChi = "Hà Nội";
        String tenPhong = "Phòng 101";
        String thoiHan = "12 tháng";
        String tienCoc = "1000000";
        String SoLuongNguoiThue = "2";
        boolean CB = true;

        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong, thoiHan, tienCoc, SoLuongNguoiThue, CB);

        assertFalse(result, "Phương thức không phát hiện thiếu CCCD");
    }

    @Test
    public void testKiemTraThongTin_TieuDeDiaChi() {
        String hoTen = "Nguyen Van A";
        String sdt = "0123456789";
        String cccd = "123456789012";
        String diaChi = "";
        String tenPhong = "Phòng 101";
        String thoiHan = "12 tháng";
        String tienCoc = "1000000";
        String SoLuongNguoiThue = "2";
        boolean CB = true;

        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong, thoiHan, tienCoc, SoLuongNguoiThue, CB);

        assertFalse(result, "Phương thức không phát hiện thiếu địa chỉ");
    }

    @Test
    public void testKiemTraThongTin_TieuDeTenPhong() {
        String hoTen = "Nguyen Van A";
        String sdt = "0123456789";
        String cccd = "123456789012";
        String diaChi = "Hà Nội";
        String tenPhong = "";
        String thoiHan = "12 tháng";
        String tienCoc = "1000000";
        String SoLuongNguoiThue = "2";
        boolean CB = true;

        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong, thoiHan, tienCoc, SoLuongNguoiThue, CB);

        assertFalse(result, "Phương thức không phát hiện thiếu tên phòng");
    }

    @Test
    public void testKiemTraThongTin_TieuDeThoiHan() {
        String hoTen = "Nguyen Van A";
        String sdt = "0123456789";
        String cccd = "123456789012";
        String diaChi = "Hà Nội";
        String tenPhong = "Phòng 101";
        String thoiHan = "";
        String tienCoc = "1000000";
        String SoLuongNguoiThue = "2";
        boolean CB = true;

        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong, thoiHan, tienCoc, SoLuongNguoiThue, CB);

        assertFalse(result, "Phương thức không phát hiện thiếu thời hạn");
    }

    @Test
    public void testKiemTraThongTin_TieuDeTienCoc() {
        String hoTen = "Nguyen Van A";
        String sdt = "0123456789";
        String cccd = "123456789012";
        String diaChi = "Hà Nội";
        String tenPhong = "Phòng 101";
        String thoiHan = "12 tháng";
        String tienCoc = "";
        String SoLuongNguoiThue = "2";
        boolean CB = true;

        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong, thoiHan, tienCoc, SoLuongNguoiThue, CB);

        assertFalse(result, "Phương thức không phát hiện thiếu tiền cọc");
    }

    @Test
    public void testKiemTraThongTin_TieuDeSoLuongNguoiThue() {
        String hoTen = "Nguyen Van A";
        String sdt = "0123456789";
        String cccd = "123456789012";
        String diaChi = "Hà Nội";
        String tenPhong = "Phòng 101";
        String thoiHan = "12 tháng";
        String tienCoc = "1000000";
        String SoLuongNguoiThue = "";
        boolean CB = true;

        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong, thoiHan, tienCoc, SoLuongNguoiThue, CB);

        assertFalse(result, "Phương thức không phát hiện thiếu số lượng người thuê");
    }

    @Test
    public void testKiemTraThongTin_TieuDeCheckBox() {
        String hoTen = "Nguyen Van A";
        String sdt = "0123456789";
        String cccd = "123456789012";
        String diaChi = "Hà Nội";
        String tenPhong = "Phòng 101";
        String thoiHan = "12 tháng";
        String tienCoc = "1000000";
        String SoLuongNguoiThue = "3";
        boolean CB = false;

        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong, thoiHan, tienCoc, SoLuongNguoiThue, CB);

        assertFalse(result, "Phương thức không phát hiện thiếu số lượng người thuê");
    }

    @Test
    public void testLuuKhachthue_HopLe() throws SQLException {
        Khachthue k = new Khachthue();
        k.setHoTen("Nguyen Van A");
        k.setCMND("123456789012");
        k.setSDT("0123456789");
        k.setDiaChi("Hà Nội");

        int id = s.luuKhachthue(k);

        assertTrue(id > 0, "Khách thuê không được lưu thành công");

        String sql = "SELECT * FROM khachthue WHERE MaKhach = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next(), "Không tìm thấy khách thuê trong cơ sở dữ liệu");
            assertEquals("Nguyen Van A", rs.getString("HoTen"), "Tên khách thuê không đúng");
            assertEquals("123456789012", rs.getString("CMND"), "CMND không đúng");
            assertEquals("0123456789", rs.getString("SDT"), "Số điện thoại không đúng");
            assertEquals("Hà Nội", rs.getString("DiaChi"), "Địa chỉ không đúng");
        }
    }

    @Test
    public void testLuuKhachthue_TieuDeHoTen() throws SQLException {
        Khachthue k = new Khachthue();
        k.setHoTen("");
        k.setCMND("761207341826");
        k.setSDT("8613972456");
        k.setDiaChi("Hà Nội");

        int id = s.luuKhachthue(k);

        assertEquals(-1, id, "Phương thức không phát hiện thiếu tên khách");
    }

    @Test
    public void testLuuKhachthue_TieuDeCMND() throws SQLException {
        Khachthue k = new Khachthue();
        k.setHoTen("trinh dang");
        k.setCMND("");
        k.setSDT("8613972456");
        k.setDiaChi("Hà Nội");

        int id = s.luuKhachthue(k);

        assertEquals(-1, id, "Phương thức không phát hiện thiếu tên khách");
    }

    @Test
    public void testLuuKhachthue_TieuDeSDT() throws SQLException {
        Khachthue k = new Khachthue();
        k.setHoTen("Nguyen Van A");
        k.setCMND("123456789012");
        k.setSDT("");
        k.setDiaChi("Hà Nội");

        int id = s.luuKhachthue(k);

        assertEquals(-1, id, "Phương thức không phát hiện thiếu số điện thoại");
    }

    @Test
    public void testLuuKhachthue_TieuDeDiaChi() throws SQLException {
        Khachthue k = new Khachthue();
        k.setHoTen("Nguyen Van A");
        k.setCMND("123456789012");
        k.setSDT("0123456789");
        k.setDiaChi("");

        int id = s.luuKhachthue(k);

        assertEquals(-1, id, "Phương thức không phát hiện thiếu địa chỉ");
    }

    @Test
    public void testLuuHopDong_ThanhCong() throws SQLException {
        Hopdong h = new Hopdong();
        h.setMaKhach(20);
        h.setMaPhong(12);
        h.setNgayBatDau(LocalDate.now());
        h.setThoiHan(12);
        h.setSoLuongNguoiThue(3);
        h.setChuKyThanhToan(1);
        h.setNgayDenHan(java.sql.Date.valueOf(h.getNgayBatDau().plusMonths(h.getChuKyThanhToan())));
        h.setNgayDenHanHopDong(LocalDate.now().plusMonths(h.getThoiHan()));

        // Gọi phương thức lưu hợp đồng
        boolean actual = s.luuHopDong(h);

        // Kiểm tra xem hợp đồng đã được lưu trong bảng hopdong chưa
        String sql = "SELECT * FROM hopdong WHERE MaKhach = ? AND MaPhong = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, h.getMaKhach());
            stmt.setInt(2, h.getMaPhong());
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next(), "Không tìm thấy hợp đồng trong cơ sở dữ liệu");
            assertEquals(h.getMaKhach(), rs.getInt("MaKhach"));
            assertEquals(h.getMaPhong(), rs.getInt("MaPhong"));
        }

        String sqlPhong = "SELECT TrangThai FROM phongtro WHERE MaPhong = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlPhong)) {
            stmt.setInt(1, h.getMaPhong());
            ResultSet rsPhong = stmt.executeQuery();
            assertTrue(rsPhong.next(), "Không tìm thấy phòng trong cơ sở dữ liệu");
            assertEquals("Có Người", rsPhong.getString("TrangThai"));
        }
    }

    @Test
    public void testLuuHopDong_ThongTinThieu() throws SQLException {
        Hopdong h = new Hopdong();
        h.setMaKhach(0); 
        h.setMaPhong(101); 
        h.setNgayBatDau(LocalDate.now());
        h.setThoiHan(12);
        h.setSoLuongNguoiThue(3);
        h.setChuKyThanhToan(1);
        h.setNgayDenHan(java.sql.Date.valueOf(h.getNgayBatDau().plusMonths(h.getChuKyThanhToan())));
        h.setNgayDenHanHopDong(LocalDate.now().plusMonths(h.getThoiHan()));

        try {
            s.luuHopDong(h);
            fail("Phải ném ngoại lệ khi thiếu thông tin hợp đồng");
        } catch (IllegalArgumentException e) {
            assertEquals("Thông tin hợp đồng không đầy đủ!", e.getMessage());
        }
    }

    @Test
    public void testThoiHan_NhoHon3() {
        KyHDServices k = new KyHDServices();
        int thoihan = 2;
        int hople = k.chuanHoaThoiHan(thoihan);
        assertEquals(thoihan, hople);
    }

    @Test
    public void testThoiHan_Bang3() {
        KyHDServices k = new KyHDServices();
        int thoihan = 3;
        int hople = k.chuanHoaThoiHan(thoihan);
        assertEquals(thoihan, hople);
    }

    @Test
    public void testThoiHan_LonHon3() {
        KyHDServices k = new KyHDServices();
        int thoihan = 6;
        int hople = k.chuanHoaThoiHan(thoihan);
        assertEquals(thoihan, hople);
    }

}
