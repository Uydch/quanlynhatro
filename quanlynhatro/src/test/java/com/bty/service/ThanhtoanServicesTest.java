/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bty.service;

import com.bty.pojo.JdbcUtils;
import com.bty.pojo.Phongdenhan;
import com.bty.pojo.Phongtro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static java.time.Clock.system;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class ThanhtoanServicesTest {

    private static ThanhtoanServices s;
    private static Connection conn;

    @BeforeAll
    public static void beforeAll() throws SQLException {
        s = new ThanhtoanServices();
        conn = JdbcUtils.getConn();
    }

    @AfterAll
    public static void aftereAll() throws SQLException {
        if (conn != null) {
            conn.close(); // Đảm bảo đóng kết nối khi kiểm thử hoàn tất
        }
    }

//    @Test
//    public void testGetPhongDenHan() throws SQLException {
//        // Chuẩn bị dữ liệu kiểm thử với phòng đến hạn
//        List<Phongdenhan> phongdenhans = s.getPhongDenHan(null);
//        Phongdenhan phong1 = phongdenhans.get(0);
//        assertEquals(2, phong1.getMaPhong(), "Mã phòng không đúng");
//        assertEquals("Phòng 102", phong1.getTenPhong(), "Tên phòng không đúng");
//         assertEquals("2025-04-26", phong1.getNgayDenHan(), "Chưa đến ngày đến hạn");
//    }
//
//    @Test
//    public void testGetPhongDenHanLonHon3Ngay() throws SQLException {
//        // Chuẩn bị dữ liệu kiểm thử với phòng đến hạn
//        List<Phongdenhan> phongdenhans = s.getPhongDenHan(null);
//        Phongdenhan phong1 = phongdenhans.get(0);
//        assertEquals(2, phong1.getMaPhong(), "Mã phòng không đúng");
//        assertEquals("Phòng 102", phong1.getTenPhong(), "Tên phòng không đúng");
//         assertEquals("2025-05-01", phong1.getNgayDenHan(), "Chưa đến ngày đến hạn");
//    }

//     @Test
//    public void testSavePhongDenHan() throws SQLException {
//        boolean actual = s.savePhongDenHanToThanhtoan();
//        assertTrue(actual);
//    }
    
    @Test
    public void testTinhTienDien() {
        double result = s.tinhTienDien(120, 100, 3000);
        assertEquals(60000, result, 0.001);
    }

    @Test
    public void testTinhTienDien_ChiSoDienThapHonThangTruoc() {
        double result = s.tinhTienDien(90, 100, 3000);
        assertEquals(-1, result, 0.001);
    }

    // Test phương thức tính tiền nước với chỉ số nước thấp hơn tháng trước
    @Test
    public void testTinhTienNuoc_ChiSoNuocThapHonThangTruoc() {
        double result = s.tinhTienNuoc(85, 90, 1500);
        assertEquals(-1, result, 0.001);
    }

    @Test
    public void testTinhTienNuoc() {
        double result = s.tinhTienNuoc(50, 40, 2000);
        assertEquals(20000, result, 0.001);
    }

    @Test
    public void testTinhTienPhat() {
        double result = s.tinhTienPhat(5, 10000);
        assertEquals(50000, result, 0.001);
    }

    @Test
    public void testTinhTongTien() {
        double giaThue = 5000.0;
        double tienDien = 60000.0;
        double tienNuoc = 15000.0;
        double tienPhat = 50.0;

        double result = s.tinhTongTien(giaThue, tienDien, tienNuoc, tienPhat);
        assertEquals(80050.0, result, 0.001);
    }

}
