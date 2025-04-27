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
public class HopdongServicesTest {

    private static HopdongServices s;
    private static Connection conn;

    @BeforeAll
    public static void beforeAll() throws SQLException {
        s = new HopdongServices();
        conn = JdbcUtils.getConn();
    }

    @AfterAll
    public static void aftereAll() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    @Test
    @DisplayName("Kiểm tra lấy hợp đồng từ cơ sở dữ liệu")
    public void testGetHopdong() throws SQLException {
        String keyword = "2";
        List<Hopdong> result = s.getHopdong(keyword);

        assertNotNull(result, "Danh sách hợp đồng không được null");
        assertFalse(result.isEmpty(), "Danh sách hợp đồng không được rỗng");

        for (Hopdong h : result) {
            assertTrue(h.getMaHopDong() == 2 || h.getTrangThai().contains(keyword), "Mã phòng hoặc trạng thái không phù hợp");
        }
    }

    @Test
    @DisplayName("Kiểm tra gia hạn hợp đồng")
    public void testGiaHanHopDong() throws SQLException {
        Hopdong h = new Hopdong();
        h.setMaHopDong(1);
        h.setNgayDenHanHopDong(LocalDate.of(2025, 6, 1));
        h.setTrangThai("Có hiệu lực");
        boolean actual = s.giaHanHopDong(h);
        assertTrue(actual);
        try {
            String sql = "SELECT NgayDenHanHopDong, TrangThai FROM hopdong WHERE MaHopDong = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, h.getMaHopDong());
                ResultSet rs = stmt.executeQuery();
                assertTrue(rs.next(), "Không tìm thấy hợp đồng sau khi gia hạn");
                assertEquals(LocalDate.of(2025, 6, 1), rs.getDate("NgayDenHanHopDong").toLocalDate(), "Ngày hết hạn không đúng");
                assertEquals("Có hiệu lực", rs.getString("TrangThai"), "Trạng thái hợp đồng không đúng");
            }
        } catch (SQLException ex) {
            fail("Lỗi khi gia hạn hợp đồng: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("Kiểm tra gia hạn hợp đồng không tồn tại")
    public void testGiaHanHopDong_HopDongKhongTonTai() throws SQLException {
        Hopdong h = new Hopdong();
        h.setMaHopDong(9999); // ID hợp đồng không tồn tại
        h.setNgayDenHanHopDong(LocalDate.of(2025, 6, 1));
        h.setTrangThai("Có hiệu lực");

        boolean actual = s.giaHanHopDong(h);
        assertFalse(actual);
    }

    @Test
    @DisplayName("Kiểm tra cập nhật ngày hết hạn hợp đồng")
    public void testUpdateNgayDenHan() throws SQLException {
        int maHopDong = 1;
        LocalDate newEndDate = LocalDate.of(2025, 12, 31);

        s.updateNgayDenHan(maHopDong, newEndDate);

        String sql = "SELECT NgayDenHanHopDong FROM hopdong WHERE MaHopDong = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maHopDong);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                assertEquals(newEndDate, rs.getDate("NgayDenHanHopDong").toLocalDate(), "Ngày hết hạn không đúng");
            } else {
                fail("Không tìm thấy hợp đồng với mã " + maHopDong);
            }
        }
    }

    @Test
    @DisplayName("Kiểm tra tổng dư nợ của hợp đồng")
    public void testGetTotalDuNo() throws SQLException {
        int maHopDong = 1;
        double totalDuNo = s.getTotalDuNo(maHopDong);

        assertTrue(totalDuNo >= 0, "Tổng dư nợ phải lớn hơn hoặc bằng 0");
        assertEquals(2499450.0, totalDuNo, 0.01, "Tổng dư nợ không đúng");
    }

    @Test
    @DisplayName("Kiểm tra tổng dư nợ của hợp đồng là 0")
    public void testGetTotalDuNo_ZeroDuNo() throws SQLException {
        int maHopDong = 14;
        double totalDuNo = s.getTotalDuNo(maHopDong);

        assertEquals(0, totalDuNo);
    }

    @Test
    @DisplayName("Kiểm tra cập nhật trạng thái hợp đồng")
    public void testUpdateTrangThaiTraPhong() throws SQLException {
        int maHopDong = 1;
        String newStatus = "testcase";

        boolean actual = s.updateTrangThaiTraPhong(maHopDong, newStatus);
        assertTrue(actual);
        String sql = "SELECT TrangThai FROM hopdong WHERE MaHopDong = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maHopDong);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                assertEquals(newStatus, rs.getString("TrangThai"), "Trạng thái không đúng");
            } else {
                fail("Không tìm thấy hợp đồng với mã " + maHopDong);
            }
        }
    }

    @Test
    @DisplayName("Kiểm tra cập nhật trạng thái hợp đồng khi hợp đồng không tồn tại")
    public void testUpdateTrangThaiTraPhong_HopDongKhongTonTai() throws SQLException {
        int maHopDong = 9999;
        String newStatus = "Đã trả phòng";

        boolean actual = s.updateTrangThaiTraPhong(maHopDong, newStatus);
        assertFalse(actual);
    }
    

    @Test
    @DisplayName("Kiểm tra cập nhật trạng thái phòng")
    public void testUpdateTrangThaiPhong() throws SQLException {
        int maPhong = 101;
        String newStatus = "Trống";

        s.updateTrangThaiPhong(maPhong);

        String sql = "SELECT TrangThai FROM phongtro WHERE MaPhong = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maPhong);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                assertEquals(newStatus, rs.getString("TrangThai"), "Trạng thái phòng không đúng");
            } else {
                fail("Không tìm thấy phòng với mã " + maPhong);
            }
        }
    }

}
