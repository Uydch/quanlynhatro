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
    public void testGetPhongtroWithEmptyKeyword() throws SQLException {
        List<Phongtro> result = s.getPhongtro("");
        assertNotNull(result);
        assertFalse(result.isEmpty(), "Danh sách không được rỗng nếu có dữ liệu trong bảng");
    }
}
