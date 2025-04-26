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
import static java.time.Clock.system;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class PhongtroServicesTest {

    private static PhongtroServices s;
    private static Connection conn;

    @BeforeAll
    public static void beforeAll() throws SQLException {
        s = new PhongtroServices();
        conn = JdbcUtils.getConn();
    }

    @AfterAll
    public static void aftereAll() throws SQLException {
        if (conn != null) {
            conn.close(); // Đảm bảo đóng kết nối khi kiểm thử hoàn tất
        }
    }

    @Test
    @DisplayName("Có keyword tồn tại")
    public void testGetPhongtroWithKeyword() throws SQLException {
        String keyword = "101"; // từ khóa khớp với 1 tên phòng hoặc trạng thái đã có trong DB test
        List<Phongtro> result = s.getPhongtro(keyword);

        assertNotNull(result);
        assertFalse(result.isEmpty(), "Phải có ít nhất một kết quả phù hợp với từ khóa");
        for (Phongtro p : result) {
            assertTrue(p.getTenPhong().contains(keyword) || p.getTrangThai().contains(keyword));
        }
    }

    @Test
    @DisplayName("keyword rỗng")
    public void testGetPhongtroWithEmptyKeyword() throws SQLException {
        List<Phongtro> result = s.getPhongtro("");
        assertNotNull(result);
        assertFalse(result.isEmpty(), "Danh sách không được rỗng nếu có dữ liệu trong bảng");
    }

    @Test
    @DisplayName("không cho thuê phòng có người")
    public void testKhongChoThuePhongDaCoNguoi() throws SQLException {
        Phongtro p = new Phongtro(1, "Phòng 101", "Đã thuê", 1500000);
        boolean ketQua = s.kiemTraTrangThaiPhong(p);
        assertFalse(ketQua, "Không được cho thuê phòng đã có người.");
    }

//    @Test
//    public void testThoiHanHopDongBang3Thang() {
//        int thoiHan = 3;
//        boolean hopLe = s.(thoiHan);
//        assertTrue(hopLe, "Hợp đồng thuê 3 tháng trở lên là hợp lệ.");
//    }
//
//    @Test
//    public void testThoiHanHopDongLonHon3Thang() {
//        int thoiHan = 6;
//        boolean hopLe = s.kiemTraThoiHanHopDong(thoiHan);
//        assertTrue(hopLe, "Hợp đồng thuê 6 tháng là hợp lệ.");
//    }
    @Test
    public void testKiemTraThongTin_HopLe() {
        String hoTen = "Nguyen Van A";
        String sdt = "0123456789";
        String cccd = "123456789012";
        String diaChi = "Hà Nội";
        String tenPhong = "Phòng 101";
        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong);
        assertTrue(result);
    }

    @Test
    public void testKiemTraThongTin_TieuDeHoTen() {
        String hoTen = "";
        String sdt = "0123456789";
        String cccd = "123456789012";
        String diaChi = "Hà Nội";
        String tenPhong = "Phòng 101";
        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong);
        assertFalse(result);
    }

    @Test
    public void testKiemTraThongTin_TieuDeSdt() {
        String hoTen = "Nguyen Van A";
        String sdt = "";
        String cccd = "123456789012";
        String diaChi = "Hà Nội";
        String tenPhong = "Phòng 101";
        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong);
        assertFalse(result);
    }

    @Test
    public void testKiemTraThongTin_TieuDeCccd() {
        String hoTen = "Nguyen Van A";
        String sdt = "0123456789";
        String cccd = "";
        String diaChi = "Hà Nội";
        String tenPhong = "Phòng 101";
        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong);

        assertFalse(result);
    }

    @Test
    public void testKiemTraThongTin_TieuDeDiaChi() {
        String hoTen = "Nguyen Van A";
        String sdt = "0123456789";
        String cccd = "123456789012";
        String diaChi = "";
        String tenPhong = "Phòng 101";

        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong);
        assertFalse(result);
    }

    @Test
    public void testKiemTraThongTin_TieuDeTenPhong() {
        String hoTen = "Nguyen Van A";
        String sdt = "0123456789";
        String cccd = "123456789012";
        String diaChi = "Hà Nội";
        String tenPhong = "";
        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong);

        assertFalse(result);
    }

    @Test
    public void testKiemTraThongTin_SoDienThoaiKhongDu1011() {
        String hoTen = "Nguyen Van A";
        String sdt = "0123456";
        String cccd = "123456789012";
        String diaChi = "Hà Nội";
        String tenPhong = "101";
        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong);

        assertFalse(result);
    }

    @Test
    public void testKiemTraThongTin_SoDienThoaiLaChu() {
        String hoTen = "Nguyen Van A";
        String sdt = "dhasdhkas";
        String cccd = "123456789012";
        String diaChi = "Hà Nội";
        String tenPhong = "101";
        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong);

        assertFalse(result);
    }

    @Test
    public void testKiemTraThongTin_CCCDKhongDu12() {
        String hoTen = "Nguyen Van A";
        String sdt = "1234567890";
        String cccd = "123456789";
        String diaChi = "Hà Nội";
        String tenPhong = "101";
        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong);
        assertFalse(result);
    }

    @Test
    public void testKiemTraThongTin_CCCDLaChu() {
        String hoTen = "Nguyen Van A";
        String sdt = "1234567890";
        String cccd = "jhsadk89787";
        String diaChi = "Hà Nội";
        String tenPhong = "101";
        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong);
        assertFalse(result);
    }

    @Test
    public void testKiemTraThongTinThemPhong_HopLe() {
        String tenPhong = "Phòng 202";
        String giaThue = "1000000";
        boolean result = s.kiemTraThongTinThemPhong(tenPhong, giaThue);
        assertTrue(result, "Thông tin hợp lệ nhưng phương thức trả về false");
    }

    @Test
    public void testKiemTraThongTinThemPhong_ThieuTenPhong() {
        String tenPhong = "";
        String giaThue = "1000000";
        boolean result = s.kiemTraThongTinThemPhong(tenPhong, giaThue);
        assertFalse(result, "Thông tin hợp lệ nhưng phương thức trả về false");
    }

    @Test
    public void testKiemTraThongTinThemPhong_ThieuGiaThue() {
        String tenPhong = "Phòng 202";
        String giaThue = "";
        boolean result = s.kiemTraThongTinThemPhong(tenPhong, giaThue);
        assertFalse(result, "Thông tin hợp lệ nhưng phương thức trả về false");
    }

    @Test
    public void testThemPhong_HopLe() throws SQLException {
        Phongtro p = new Phongtro();
        p.setTenPhong("Phòng 202");
        p.setGiaThue(1000000);
        boolean result = s.themPhong(p);

        assertTrue(result, "Phòng không được thêm thành công");

        String sql = "SELECT * FROM phongtro WHERE TenPhong = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getTenPhong());
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next(), "Không tìm thấy phòng trong cơ sở dữ liệu");
            assertEquals("Trống", rs.getString("TrangThai"), "Trang thái phòng không phải 'Trống'");
            assertEquals(p.getGiaThue(), rs.getInt("GiaThue"), "Giá thuê không đúng");
        }
    }

    @Test
    public void testThemPhong_TrungTenPhong() throws SQLException {
        Phongtro p = new Phongtro();
        p.setTenPhong("Phòng 202");
        p.setGiaThue(1000000);
        boolean result = s.themPhong(p);
        assertTrue(result, "Phòng không được thêm thành công");
        String sql = "SELECT * FROM phongtro WHERE TenPhong = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getTenPhong());
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next(), "Không tìm thấy phòng trong cơ sở dữ liệu");
            assertEquals("Trống", rs.getString("TrangThai"), "Trang thái phòng không phải 'Trống'");
            assertEquals(p.getGiaThue(), rs.getInt("GiaThue"), "Giá thuê không đúng");
        }
    }

    @Test
    public void testXoaPhong_HopLe() throws SQLException {
        int maPhong = 22;
        boolean result = s.xoaPhong(maPhong);
        assertTrue(result, "Phòng không được xóa thành công");
        String sql = "SELECT * FROM phongtro WHERE Maphong = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maPhong);
            ResultSet rs = stmt.executeQuery();
            assertFalse(rs.next(), "Phòng vẫn còn tồn tại trong cơ sở dữ liệu");
        }
    }
    
    @Test
    public void testXoaPhong_SaiMaPhong() throws SQLException {
        int maPhong = 100;
        boolean result = s.xoaPhong(maPhong);
        assertTrue(result, "Phòng không được xóa thành công");
        String sql = "SELECT * FROM phongtro WHERE Maphong = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maPhong);
            ResultSet rs = stmt.executeQuery();
            assertFalse(rs.next(), "Phòng vẫn còn tồn tại trong cơ sở dữ liệu");
        }
    }
}
