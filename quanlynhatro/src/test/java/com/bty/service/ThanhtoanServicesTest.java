/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bty.service;

import com.bty.pojo.JdbcUtils;
import com.bty.pojo.Phongdenhan;
import com.bty.pojo.Phongtro;
import com.bty.pojo.Thanhtoan;
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
import java.time.LocalDate;
import java.util.ArrayList;

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

    @Test
    public void testGetPhongDenHan() throws SQLException {
        List<Phongdenhan> phongdenhans = s.getPhongDenHan(null);
        Phongdenhan phong1 = phongdenhans.get(0);
        assertEquals(2, phong1.getMaPhong(), "Mã phòng không đúng");
        assertEquals("Phòng 102", phong1.getTenPhong(), "Tên phòng không đúng");
        assertEquals("2025-04-26", phong1.getNgayDenHan(), "Chưa đến ngày đến hạn");
    }

    @Test
    public void testGetPhongDenHanLonHon3Ngay() throws SQLException {
        List<Phongdenhan> phongdenhans = s.getPhongDenHan(null);
        Phongdenhan phong1 = phongdenhans.get(0);
        assertEquals(2, phong1.getMaPhong(), "Mã phòng không đúng");
        assertEquals("Phòng 102", phong1.getTenPhong(), "Tên phòng không đúng");
        assertEquals("2025-05-01", phong1.getNgayDenHan(), "Chưa đến ngày đến hạn");
    }

    @Test
    public void testSavePhongDenHan() throws SQLException {
        boolean actual = s.savePhongDenHanToThanhtoan();
        assertTrue(actual);
        List<Phongdenhan> PhongDenHanList = new ArrayList<>();
        Phongdenhan pdh1 = new Phongdenhan(2, 1, "Phòng 102", 3200000, "Nguyễn Văn A", LocalDate.of(2025, 4, 21));
        PhongDenHanList.add(pdh1);

        String sqlThanhtoan = "SELECT * FROM thanhtoan WHERE MaHopDong = ? MaPhong=? NgayDenHan=?";
        PreparedStatement stmThanhtoan = conn.prepareStatement(sqlThanhtoan);
        stmThanhtoan.setInt(1, pdh1.getMaHopDong());
        stmThanhtoan.setInt(2, pdh1.getMaPhong());
        stmThanhtoan.setDate(3, java.sql.Date.valueOf(pdh1.getNgayDenHan()));
        ResultSet rsThanhtoan = stmThanhtoan.executeQuery();
        assertTrue(rsThanhtoan.next()); // Kiểm tra xem có bản ghi nào được tạo không
        assertEquals(pdh1.getMaHopDong(), rsThanhtoan.getInt("MaHopDong"));
    }

    @Test
    public void testSavePhongDenHanChuaToiNgay() throws SQLException {
        boolean actual = s.savePhongDenHanToThanhtoan();
        assertFalse(actual);
        List<Phongdenhan> PhongDenHanList = new ArrayList<>();
        Phongdenhan pdh1 = new Phongdenhan(2, 1, "Phòng 102", 3200000, "Nguyễn Văn A", LocalDate.of(2025, 5, 21));
        // ... thêm các Phongdenhan khác nếu cần
        PhongDenHanList.add(pdh1);

        String sqlThanhtoan = "SELECT * FROM thanhtoan WHERE MaHopDong = ? MaPhong=? NgayDenHan=?";
        PreparedStatement stmThanhtoan = conn.prepareStatement(sqlThanhtoan);
        stmThanhtoan.setInt(1, pdh1.getMaHopDong());
        stmThanhtoan.setInt(2, pdh1.getMaPhong());
        stmThanhtoan.setDate(3, java.sql.Date.valueOf(pdh1.getNgayDenHan()));
        ResultSet rsThanhtoan = stmThanhtoan.executeQuery();
        assertTrue(rsThanhtoan.next());
        assertEquals(pdh1.getMaHopDong(), rsThanhtoan.getInt("MaHopDong"));
    }

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

    @Test
    public void testTinhTienDien_SoLieuAm() {
        double result = s.tinhTienDien(-50, 100, 3000);
        assertEquals(-1, result, 0.001, "Chỉ số điện không thể là số âm");
    }

    // Test phương thức tính tiền nước với chỉ số nước thấp hơn tháng trước
    @Test
    public void testTinhTienNuoc_ChiSoNuocThapHonThangTruoc() {
        double result = s.tinhTienNuoc(85, 90, 1500);
        assertEquals(-1, result, 0.001);
    }

    @Test
    public void testTinhTienNuoc_SoLieuAm() {
        double result = s.tinhTienNuoc(50, -40, 2000);
        assertEquals(-1, result, 0.001, "Chỉ số nước không thể là số âm");
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
    public void testTinhTienPhat_Am() {
        // Trường hợp số ngày trễ thanh toán là âm
        double result = s.tinhTienPhat(-5, 10000);
        assertEquals(-1, result, 0.001, "Tiền phạt không thể tính khi số ngày trễ thanh toán âm");
    }

    @Test
    public void testTinhTienPhat_NegativeAmount() {
        // Trường hợp số tiền phạt tính ra là âm (ví dụ như số tiền phạt tính sai)
        double result = s.tinhTienPhat(5, -10000);
        assertEquals(-1, result, 0.001, "Số tiền phạt không thể là số âm");
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

    @Test
    public void testTinhTongTien_Am() {
        // Trường hợp tổng tiền bị âm (ví dụ: một trong các yếu tố như tiền điện, tiền nước, phạt là âm)
        double result = s.tinhTongTien(-5000.0, 60000.0, 15000.0, 50.0);  // Gia thue là âm
        assertEquals(-1, result, 0.001, "Tổng tiền không thể là số âm");

        result = s.tinhTongTien(5000.0, -60000.0, 15000.0, 50.0);  // Tiền điện là âm
        assertEquals(-1, result, 0.001, "Tổng tiền không thể là số âm");

        result = s.tinhTongTien(5000.0, 60000.0, -15000.0, 50.0);  // Tiền nước là âm
        assertEquals(-1, result, 0.001, "Tổng tiền không thể là số âm");

        result = s.tinhTongTien(5000.0, 60000.0, 15000.0, -50.0);  // Tiền phạt là âm
        assertEquals(-1, result, 0.001, "Tổng tiền không thể là số âm");
    }

    @Test
    public void testUpdatePhongDenHanToThanhToan() throws SQLException {
        Thanhtoan p = new Thanhtoan();
        p.setMaThanhToan(181);
        p.setTongTien(1200.0);
        p.setPhiPhat(75.0);
        p.setDuNo(50.0);
        try {
            boolean actual = s.updatePhongDenHanToThanhtoan(p);
            assertTrue(actual, "Cập nhật không thành công");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Đã xảy ra lỗi SQL: " + e.getMessage());
        }
        String selectSql = "SELECT SoTien, PhiPhat, DuNo FROM thanhtoan WHERE MaThanhToan = ?";
        try (PreparedStatement stmt = conn.prepareStatement(selectSql)) {
            stmt.setInt(1, p.getMaThanhToan());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double tongTien = (p.getTongTien() != null) ? p.getTongTien() : 0.0;
                double phiPhat = (p.getPhiPhat() != null) ? p.getPhiPhat() : 0.0;
                double duNo = (p.getDuNo() != null) ? p.getDuNo() : 0.0;
                assertEquals(50.0, duNo, "Dư nợ không khớp");
            } else {
                fail("Không tìm thấy bản ghi sau khi cập nhật");
            }
        }
    }

    @Test
    public void testUpdatePhongDenHanToThanhToan_SaiMaThanhToan() {
        try {
            // Tạo đối tượng Thanhtoan để kiểm thử
            Thanhtoan p = new Thanhtoan();
            p.setMaThanhToan(1);
            p.setTongTien(1200.0);
            p.setPhiPhat(75.0);
            p.setDuNo(50.0);

            boolean actual = s.updatePhongDenHanToThanhtoan(p);
            assertTrue(actual, "Cập nhật không thành công");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Đã xảy ra lỗi SQL: " + e.getMessage());
        }
    }

    @Test
    public void testUpdatePhongDenHanToThanhToan_TongTienSoAm() {
        try {
            // Tạo đối tượng Thanhtoan để kiểm thử
            Thanhtoan p = new Thanhtoan();
            p.setMaThanhToan(179);
            p.setTongTien(-1200.0);
            p.setPhiPhat(75.0);
            p.setDuNo(550.0);
            boolean actual = s.updatePhongDenHanToThanhtoan(p);
            assertTrue(actual, "Cập nhật không thành công");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Đã xảy ra lỗi SQL: " + e.getMessage());
        }

    }

    @Test
    public void testUpdatePhongDenHanToThanhToan_PhiPhatSoAm() {
        try {
            Thanhtoan p = new Thanhtoan();
            p.setMaThanhToan(179);
            p.setTongTien(1200.0);
            p.setPhiPhat(75.0);
            p.setPhiPhat(-550.0);

            boolean actual = s.updatePhongDenHanToThanhtoan(p);
            assertTrue(actual, "Cập nhật không thành công");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Đã xảy ra lỗi SQL: " + e.getMessage());
        }
    }

    @Test
    public void testUpdatePhongDenHanToThanhToan_DuNoSoAm() {
        try {
            Thanhtoan p = new Thanhtoan();
            p.setMaThanhToan(179);
            p.setTongTien(1200.0);
            p.setDuNo(-550.0);
            boolean actual = s.updatePhongDenHanToThanhtoan(p);
            assertTrue(actual, "Cập nhật không thành công");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Đã xảy ra lỗi SQL: " + e.getMessage());
        }
    }

    @Test
    public void testCapNhatNgayDenHan() throws SQLException {
        String sql = "SELECT NgayDenHan, ChuKyThanhToan FROM hopdong WHERE MaHopDong = ?";

        // Khai báo hai biến để lưu giá trị Ngày Đến Hạn và Chu Kỳ Thanh Toán
        LocalDate ngayDenHan = null;
        int chuKyThanhToan = 0;

        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, 1);  // Set MaHopDong vào PreparedStatement
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                ngayDenHan = rs.getDate("NgayDenHan").toLocalDate();  // Giả sử NgayDenHan là kiểu DATE trong CSDL
                chuKyThanhToan = rs.getInt("ChuKyThanhToan");
            }
            Phongdenhan p = new Phongdenhan();
            p.setMaHopDong(1);
            boolean actual = s.capNhatNgayDenHan(p);

            String sql1 = "SELECT NgayDenHan, ChuKyThanhToan FROM hopdong WHERE MaHopDong = ?";
            try (PreparedStatement stm1 = conn.prepareStatement(sql1)) {
                stm1.setInt(1, p.getMaHopDong());
                ResultSet rs1 = stm1.executeQuery();
                if (rs.next()) {
                    assertEquals(rs.getDate("NgayDenHan").toLocalDate(), ngayDenHan.plusMonths(chuKyThanhToan));
                }
                assertTrue(actual);

            }

        }
    }

    @Test
    public void testCapNhatTienDien_HopLe() throws SQLException {
        int maThanhToan = 179;
        double chiSoDien = 100.0;

        boolean actual = s.capNhatTienDien(maThanhToan, chiSoDien);
        assertTrue(actual);
    }

    @Test
    public void testCapNhatTienDien_SaiMaThanhToan() throws SQLException {
        int maThanhToan = 1;
        double chiSoDien = 100.0;

        boolean actual = s.capNhatTienDien(maThanhToan, chiSoDien);
        assertTrue(actual);
    }

    @Test
    public void testCapNhatTienDien_ChiSoDienAm() throws SQLException {
        int maThanhToan = 1;
        double chiSoDien = -100.0;

        boolean actual = s.capNhatTienDien(maThanhToan, chiSoDien);
        assertTrue(actual);
    }

    @Test
    public void testCapNhatTienNuoc_HopLe() throws SQLException {
        int maThanhToan = 179;
        double chiSoNuoc = 100.0;

        boolean actual = s.capNhatTienNuoc(maThanhToan, chiSoNuoc);
        assertTrue(actual);
    }

    @Test
    public void testCapNhatTienNuoc_SaiMaThanhToan() throws SQLException {
        int maThanhToan = 1;
        double chiSoNuoc = 100.0;

        boolean actual = s.capNhatTienDien(maThanhToan, chiSoNuoc);
        assertTrue(actual);
    }

    @Test
    public void testCapNhatTienNuoc_ChiSoNuocAm() throws SQLException {
        int maThanhToan = 1;
        double chiSoNuoc = -100.0;

        boolean actual = s.capNhatTienDien(maThanhToan, chiSoNuoc);
        assertTrue(actual);
    }

    @Test
    public void testUpdateThanhToanStatus() throws SQLException {
        int maThanhToan = 179; 
        String newStatus = "test status";
        boolean actual = s.updateThanhToanStatus(maThanhToan, newStatus);
        assertTrue(actual);
        String sql = "SELECT TrangThai FROM thanhtoan WHERE MaThanhToan = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maThanhToan);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String trangThai = rs.getString("TrangThai");
                assertEquals(newStatus, trangThai, "Trạng thái thanh toán không được cập nhật đúng");
            }
        }
    }
    
    @Test
    public void testUpdateThanhToanStatus_SaiMaThanhToan() throws SQLException {
        int maThanhToan = 1000; 
        String newStatus = "test status";
        boolean actual = s.updateThanhToanStatus(maThanhToan, newStatus);
        assertTrue(actual);
        String sql = "SELECT TrangThai FROM thanhtoan WHERE MaThanhToan = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maThanhToan);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String trangThai = rs.getString("TrangThai");
                assertEquals(newStatus, trangThai, "Trạng thái thanh toán không được cập nhật đúng");
            }
        }
    }
}
