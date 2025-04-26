/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bty.service;

import com.bty.pojo.Phongtro;
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

    @BeforeAll
    public static void beforeAll() {
        s = new PhongtroServices();
    }

    @AfterAll
    public static void aftereAll() {
        System.out.println("after");

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
        assertTrue(result, "Thông tin hợp lệ nhưng phương thức trả về false");
    }

    @Test
    public void testKiemTraThongTin_TieuDeHoTen() {
        String hoTen = "";
        String sdt = "0123456789";
        String cccd = "123456789012";
        String diaChi = "Hà Nội";
        String tenPhong = "Phòng 101";
        boolean result = s.kiemTraThongTin(hoTen, sdt, cccd, diaChi, tenPhong);

        // Kiểm tra kết quả trả về là false khi thiếu tên
        assertFalse(result, "Phương thức không phát hiện thiếu tên");
    }

}
