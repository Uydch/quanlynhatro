/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bty.service;

import com.bty.pojo.Hopdong;
import com.bty.pojo.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class HopdongServices {

    public List<Hopdong> getHopdong(String kw) throws SQLException {
        List<Hopdong> results = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM hopdong";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE MaHopDong LIKE concat('%', ?, '%') OR "
                        + "TrangThai LIKE concat('%', ?, '%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
                stm.setString(2, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Hopdong q = new Hopdong(rs.getInt("MaHopDong"), rs.getInt("MaKhach"), rs.getInt("MaPhong"), rs.getDate("NgayBatDau").toLocalDate(),
                        rs.getInt("ThoiHan"), rs.getString("TrangThai"), rs.getInt("SoLuongNguoiThue"), rs.getInt("ChuKyThanhToan"),
                        rs.getDate("NgayDenHan"), rs.getDate("NgayDenHanHopDong").toLocalDate());
                results.add(q);
            }
        }
        return results;
    }

    public boolean giaHanHopDong(Hopdong h) throws SQLException {
        String sql = "UPDATE hopdong SET NgayDenHanHopDong = ?, TrangThai = ? WHERE MaHopDong = ?";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(h.getNgayDenHanHopDong()));
            stmt.setString(2, h.getTrangThai());
            stmt.setInt(3, h.getMaHopDong());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Hợp đồng đã được gia hạn.");
                return true;
            } else {
                System.out.println("Không tìm thấy hợp đồng để gia hạn.");
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi khi gia hạn hợp đồng: " + ex.getMessage());
            throw ex;
        }
    }

    public boolean updateNgayDenHan(int maHopDong, LocalDate newEndDate) throws SQLException {
        String sql = "UPDATE hopdong SET NgayDenHanHopDong = ?,TrangThai=? WHERE MaHopDong = ?";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(newEndDate));
            ps.setString(2, "Hiệu lực");
            ps.setInt(3, maHopDong);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Ngày hết hạn hợp đồng đã được cập nhật.");
                return true;
            } else {
                System.out.println("Không tìm thấy hợp đồng với mã: " + maHopDong);
                return false;
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi cập nhật ngày hết hạn hợp đồng: " + e.getMessage(), e);
        }
    }

    public double getTotalDuNo(int maHopDong) throws SQLException {
        double totalDuNo = 0;

        String sql = "SELECT SUM(duNo) AS totalDuNo FROM ThanhToan WHERE MaHopDong = ?";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maHopDong);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalDuNo = rs.getDouble("totalDuNo");
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi truy vấn tổng dư nợ cho hợp đồng " + maHopDong, e);
        }

        return totalDuNo;
    }

    public boolean updateTrangThaiTraPhong(int maHopDong, String trangThai) throws SQLException {
        String sql = "UPDATE hopdong SET TrangThai = ? WHERE MaHopDong = ?";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, trangThai);
            ps.setInt(2, maHopDong);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Trạng thái hợp đồng đã được cập nhật.");
                return true;
            } else {
                System.out.println("Không tìm thấy hợp đồng với mã: " + maHopDong);
                return false;
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi cập nhật trạng thái hợp đồng: " + e.getMessage(), e);
        }
    }

    public boolean updateTrangThaiPhong(int maPhong) throws SQLException {
        String sql = "UPDATE phongtro SET TrangThai = 'Trống' WHERE MaPhong = ?";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maPhong);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Trạng thái phòng đã được cập nhật thành 'Trống'.");
                return true;
            } else {
                System.out.println("Không tìm thấy phòng với mã: " + maPhong);
                return false;
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi cập nhật trạng thái phòng: " + e.getMessage(), e);
        }
    }

    public boolean checkUnpaidMonths(int maHopDong) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ThanhToan WHERE MaHopDong = ? AND TrangThai = 'Chưa thanh toán'";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maHopDong);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int unpaidMonths = rs.getInt(1);
                return unpaidMonths > 0; 
            }
        }
        return false;
    }
    
}
