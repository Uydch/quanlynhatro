/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bty.service;

import com.bty.pojo.Chisodiennuoc;
import com.bty.pojo.JdbcUtils;
import com.bty.pojo.Phongdenhan;
import com.bty.pojo.Phongtro;
import com.bty.pojo.Thanhtoan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.scene.control.TextInputDialog;

/**
 *
 * @author User
 */
public class ThanhtoanServices {

    public List<Phongdenhan> getPhongDenHan(String kw) throws SQLException {
        List<Phongdenhan> results = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT p.MaPhong, p.TenPhong, p.GiaThue,"
                    + "k.HoTen AS HoTenNguoiThue, h.NgayDenHan,h.MaHopDong "
                    + "FROM phongtro p "
                    + "JOIN hopdong h ON p.MaPhong = h.MaPhong "
                    + "JOIN khachthue k ON k.MaKhach = h.MaKhach"
                    + " WHERE p.TrangThai = ? AND CURDATE() >= DATE_SUB(h.NgayDenHan,INTERVAL 3 DAY)";

//        if (kw != null && !kw.isEmpty()) {
//            sql += " AND p.TenPhong LIKE ?";
//        }
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, "Có người");
//        if (kw != null && !kw.isEmpty()) {
//            stm.setString(2, "%" + kw + "%");
//        }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Phongdenhan p = new Phongdenhan(
                        rs.getInt("MaPhong"),
                        rs.getInt("MaHopDong"),
                        rs.getString("TenPhong"),
                        rs.getInt("GiaThue"),
                        rs.getString("HoTenNguoiThue"),
                        rs.getDate("NgayDenHan").toLocalDate()
                );
                results.add(p);
                capNhatNgayDenHan(p);
            }
        }
        return results;
    }

    public boolean savePhongDenHanToThanhtoan() throws SQLException {
        List<Phongdenhan> phongDenHanList = getPhongDenHan(null); // Lấy danh sách phòng đến hạn
        int recordsSaved = 0;
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "INSERT INTO thanhtoan ( MaHopDong,SoTien,"
                    + "NgayThanhToan,PhiPhat,DuNo, NgayDenHan) VALUES (?,?, ?, ?, ?, ?)";
            PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            String sql1 = "INSERT INTO chisodiennuoc ( MaPhong, TenPhong, NgayGhi,Mathanhtoan) VALUES (?, ?, ?, ?)";

            PreparedStatement stm1 = conn.prepareCall(sql1);

            for (Phongdenhan p : phongDenHanList) {
                stm.setInt(1, p.getMaHopDong());
                stm.setDouble(2, (p.getTongTien() != null) ? p.getTongTien() : 0.0);
                stm.setNull(3, java.sql.Types.DATE);
                stm.setDouble(4, (p.getPhiPhat() != null) ? p.getPhiPhat() : 0.0);
                stm.setDouble(5, (p.getDuNo() != null) ? p.getDuNo() : 0.0);
                if (p.getNgayDenHan() == null) {
                    stm.setNull(6, java.sql.Types.DATE);
                } else {
                    stm.setDate(6, java.sql.Date.valueOf(p.getNgayDenHan()));
                }
                stm.executeUpdate();
                ResultSet MaThanhToan = stm.getGeneratedKeys();
                int mathanhtoan = -1;
                if (MaThanhToan.next()) {
                    mathanhtoan = MaThanhToan.getInt(1);
                }

                stm1.setInt(1, p.getMaPhong());
                stm1.setString(2, p.getTenPhong());
                if (p.getNgayDenHan() == null) {
                    stm1.setNull(3, java.sql.Types.DATE);
                } else {
                    stm1.setDate(3, java.sql.Date.valueOf(p.getNgayDenHan()));
                }
                stm1.setInt(4, mathanhtoan);

                stm1.executeUpdate();
                recordsSaved++;
            }
        }
        return recordsSaved > 0;
    }

    public List<Thanhtoan> loadPhongDenhan(String kw) throws SQLException {
        List<Thanhtoan> results = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT h.MaPhong, p.TenPhong,p.GiaThue,c.ChiSoDien,c.ChiSoNuoc,"
                    + "       k.HoTen, "
                    + "       t.NgayDenHan, t.MaHopDong,"
                    + "       t.SoTien, t.NgayThanhToan,t.MaThanhToan, "
                    + "       t.TrangThai,"
                    + "       t.PhiPhat, t.DuNo "
                    + "FROM phongtro p "
                    + "JOIN hopdong h ON p.MaPhong = h.MaPhong  "
                    + "JOIN thanhtoan t ON t.MaHopDong = h.MaHopDong  "
                    + "JOIN khachthue k ON k.MaKhach = h.MaKhach  "
                    + "JOIN chisodiennuoc c ON c.MaThanhToan = t.MaThanhToan "
                    + "WHERE p.MaPhong = h.MaPhong;";

//            if (kw != null && !kw.isEmpty()) {
//                sql += " WHERE TenPhong LIKE concat('%', ?, '%') OR "
//                        + "TrangThai LIKE concat('%', ?, '%')";
//            }
            PreparedStatement stm = conn.prepareCall(sql);
//            if (kw != null && !kw.isEmpty()) {
//                stm.setString(1, kw);
//                stm.setString(2, kw);
//            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Thanhtoan t = new Thanhtoan(rs.getInt("MaThanhToan"), rs.getInt("MaPhong"), (int) rs.getDouble("SoTien"), rs.getDate("NgayThanhToan"),
                        rs.getInt("MaHopDong"), rs.getString("TenPhong"), rs.getInt("GiaThue"),
                        rs.getString("HoTen"), rs.getDate("NgayDenHan").toLocalDate(),
                        rs.getDouble("PhiPhat"), rs.getString("TrangThai"), rs.getDouble("DuNo"), rs.getDouble("ChiSoDien"),
                        rs.getDouble("ChiSoNuoc"));
                results.add(t);
            }
        }
        System.out.println("Dữ liệu lấy từ DB:");
        for (Thanhtoan t : results) {
            System.out.println(t.getTenPhong() + " - " + t.getSoTien());
        }
        return results;
    }

    public boolean updatePhongDenHanToThanhtoan(Thanhtoan p) throws SQLException {
        if (p.getTongTien() < 0.0 || p.getPhiPhat() < 0.0 || p.getDuNo() < 0.0) {
            return false;
        }
        int r;
        String sql = "UPDATE thanhtoan SET SoTien = ?, PhiPhat = ?,DuNo=? WHERE MaThanhToan = ? ";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setDouble(1, (p.getTongTien() != null) ? p.getTongTien() : 0.0);
            stm.setDouble(2, (p.getPhiPhat() != null) ? p.getPhiPhat() : 0.0);
            stm.setDouble(3, (p.getDuNo() != null) ? p.getDuNo() : 0.0);
            stm.setInt(4, p.getMaThanhToan());
            stm.addBatch();
            r = stm.executeUpdate();
        }
        return r > 0;
    }

    /**
     *
     * @param p
     * @throws SQLException
     */
    public boolean capNhatNgayDenHan(Phongdenhan p) throws SQLException {
        String sql = "UPDATE hopdong SET NgayDenHan = DATE_ADD(NgayDenHan, INTERVAL ChuKyThanhToan MONTH) WHERE MaHopDong = ?";
        int r;
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, p.getMaHopDong());
            r = stm.executeUpdate();
        }
        return r > 0;
    }

    public double getChiSoDienThangTruoc(int MaPhong) {
        double chiSoDienCu = 0.0;
        String sql = "SELECT ChiSoDien FROM chisodiennuoc "
                + "WHERE MaPhong = ? AND DATE_FORMAT(NgayGhi, '%Y-%m') = DATE_FORMAT(DATE_SUB(NgayGhi,INTERVAL 1 MONTH), '%Y-%m')  "
                + "ORDER BY NgayGhi DESC LIMIT 1";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, MaPhong);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                chiSoDienCu = rs.getDouble("ChiSoDien");
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi truy vấn CSDL: " + ex.getMessage());
        }
        return chiSoDienCu;
    }

    public double getChiSoNuocThangTruoc(int MaPhong) {
        double chiSoNuocCu = 0.0;
        String sql = "SELECT ChiSoNuoc FROM chisodiennuoc "
                + "WHERE MaPhong = ? AND DATE_FORMAT(NgayGhi, '%Y-%m') = DATE_FORMAT(DATE_SUB(NgayGhi,INTERVAL 1 MONTH), '%Y-%m') "
                + "ORDER BY NgayGhi DESC LIMIT 1"; // Lấy chỉ số điện mới nhất

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, MaPhong);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                chiSoNuocCu = rs.getDouble("ChiSoNuoc");
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi truy vấn CSDL: " + ex.getMessage());
        }
        return chiSoNuocCu;
    }

    public boolean capNhatTienDien(int maThanhToan, double ChiSoDien) throws SQLException {
        int r;
        if (ChiSoDien < 0) {
            // Nếu chỉ số nước là số âm, không cho phép cập nhật
            throw new IllegalArgumentException("Chỉ số nước không thể là số âm");
        }
        String sql = "UPDATE chisodiennuoc SET ChiSoDien = ? WHERE MaThanhToan = ?";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setDouble(1, ChiSoDien);
            stm.setInt(2, maThanhToan);

            r = stm.executeUpdate(); // Thực hiện UPDATE
        }
        return r > 0;
    }

    public boolean capNhatTienNuoc(int maThanhToan, double ChiSoNuoc) throws SQLException {
        int r;
        if (ChiSoNuoc < 0) {
            // Nếu chỉ số nước là số âm, không cho phép cập nhật
            throw new IllegalArgumentException("Chỉ số nước không thể là số âm");
        }
        String sql = "UPDATE chisodiennuoc SET ChiSoNuoc = ? WHERE MaThanhToan = ?";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setDouble(1, ChiSoNuoc);
            stm.setInt(2, maThanhToan);

            r = stm.executeUpdate();
        }
        return r > 0;
    }

    public Optional<String> getThanhToanAmount(String tenPhong) {
        // Tạo một hộp thoại nhập số tiền thanh toán
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nhập số tiền thanh toán");
        dialog.setHeaderText("Phòng: " + tenPhong);
        dialog.setContentText("Nhập số tiền:");
        return dialog.showAndWait();
    }

    public void updateThanhToanStatus(int MaThanhToan, String status) {
        String sql = "UPDATE thanhtoan SET TrangThai = ? WHERE MaThanhToan = ?";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, MaThanhToan);

            // Thực thi câu lệnh
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cập nhật trạng thái thanh toán thành công!");
            } else {
                System.out.println("Không tìm thấy mã thanh toán để cập nhật.");
            }
        } catch (SQLException e) {
        }
    }
    
    

    public double tinhTienDien(double chiSoDien, double chiSoDienThangTruoc, double giaDien) {
        if (chiSoDien < chiSoDienThangTruoc || chiSoDien < 0) {
            return -1; // Nếu chỉ số điện mới nhỏ hơn chỉ số điện cũ, trả về -1
        }
        return (chiSoDien - chiSoDienThangTruoc) * giaDien;
    }

    public double tinhTienNuoc(double chiSoNuoc, double chiSoNuocThangTruoc, double giaNuoc) {
        if (chiSoNuoc < chiSoNuocThangTruoc || chiSoNuoc < 0) {
            return -1; // Nếu chỉ số nước mới nhỏ hơn chỉ số nước cũ, trả về -1
        }
        return (chiSoNuoc - chiSoNuocThangTruoc) * giaNuoc;
    }

    public double tinhTienPhat(long soNgayTre, double phiPhatMoiNgay) {
        if (soNgayTre < 0 || phiPhatMoiNgay < 0) {
            return -1;
        }
        return soNgayTre * phiPhatMoiNgay;
    }

    public double tinhTongTien(double giaThue, double tienDien, double tienNuoc, double tienPhat) {
        if (giaThue < 0 || tienDien < 0 || tienNuoc < 0 || tienPhat < 0) {
            return -1;
        }
        return giaThue + tienDien + tienNuoc + tienPhat;
    }

}
