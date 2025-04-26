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
public class KyHDServicesTest {

    @Test
    public void test1() {
        KyHDServices k = new KyHDServices();
        int thoihan = 2;
        int hople = k.chuanHoaThoiHan(thoihan);
        assertEquals(thoihan, hople);
    }

    @Test
    public void test2() {
        KyHDServices k = new KyHDServices();
        int thoihan = 3;
        int hople = k.chuanHoaThoiHan(thoihan);
        assertEquals(thoihan, hople);
    }

    @Test
    public void test3() {
        KyHDServices k = new KyHDServices();
        int thoihan = 6;
        int hople = k.chuanHoaThoiHan(thoihan);
        assertEquals(thoihan, hople);
    }
    
    

}
