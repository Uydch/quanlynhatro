/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bty.quanlynhatro;

import com.bty.pojo.Hopdong;
import com.bty.pojo.Khachthue;
import static com.bty.quanlynhatro.Base.switchScene;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.bty.service.KyHDServices;
import com.bty.service.PhongtroServices;
import java.sql.SQLException;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author User
 */
public class KyHDController implements Initializable {

    @FXML
    private Label txtHoTen;
    @FXML
    private Label txtCCCD;
    @FXML
    private Label txtSDT;
    @FXML
    private Label txtDiaChi;
    @FXML
    private Label txtTenphong;
    @FXML
    private Label txtMaHD;
    @FXML
    private DatePicker NgayLap;
    @FXML
    private TextField ThoiHan;
    @FXML
    private TextField TienCoc;
    @FXML
    private TextField SoLuongNguoiThue;
    @FXML
    private TextField ChuKyThanhToan;
    @FXML
    private CheckBox CB;

//    @FXML private TextArea txtDieuKhoan;
    public void setThongTinKhach(String hoTen, String cccd, String sdt, String diaChi, String tenPhong) {
        txtHoTen.setText(hoTen);
        txtCCCD.setText(cccd);
        txtSDT.setText(sdt);
        txtDiaChi.setText(diaChi);
        txtTenphong.setText(tenPhong);
        NgayLap.setValue(LocalDate.now());
    }

    private void thoiHanThue() {
        ThoiHan.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
            }
        });

        // Tự động chuẩn hóa giá trị nếu < 3
        ThoiHan.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    int value = Integer.parseInt(newValue);
                    KyHDServices k =new KyHDServices();
                    int valueChuan = k.chuanHoaThoiHan(value);
                    if (value != valueChuan) {
                        ThoiHan.setText(String.valueOf(valueChuan));
                    }
                } catch (NumberFormatException e) {
                    ThoiHan.setText(oldValue);
                }
            }
        });
    }

    private void tienCoc() {
        KyHDServices s = new KyHDServices();
        s.gioiHanTienCoc(TienCoc);
    }

    private void gioiHanNguoiThue() {
        KyHDServices s = new KyHDServices();
        s.gioiHanNguoiThue(SoLuongNguoiThue);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.tienCoc();
        this.thoiHanThue();
        this.gioiHanNguoiThue();
    }

    public void XacNhanthueHandler(ActionEvent e) throws SQLException {
        KyHDServices s = new KyHDServices();

        if (!s.kiemTraThongTin(
                txtHoTen.getText(),
                txtSDT.getText(),
                txtCCCD.getText(),
                txtDiaChi.getText(),
                txtTenphong.getText(),
                ThoiHan.getText(),
                TienCoc.getText(),
                SoLuongNguoiThue.getText(),
                CB.isSelected()
        )) {
            s.hienThiThongBaoThieuThongTin();
            return;
        }

        Khachthue k = new Khachthue(this.txtHoTen.getText(),
                this.txtCCCD.getText(), this.txtSDT.getText(), this.txtDiaChi.getText());
        Hopdong h = new Hopdong(0, s.luuKhachthue(k), s.maPhongTheoTen(this.txtTenphong.getText()),
                (this.NgayLap.getValue() != null) ? this.NgayLap.getValue() : null,
                Integer.parseInt(this.ThoiHan.getText()), "", Integer.parseInt(this.SoLuongNguoiThue.getText()),
                Integer.parseInt(this.ChuKyThanhToan.getText()),
                (this.NgayLap.getValue() != null) ? java.sql.Date.valueOf(this.NgayLap.getValue().plusMonths(Integer.parseInt(this.ChuKyThanhToan.getText()))) : null,
                (this.NgayLap.getValue() != null) ? this.NgayLap.getValue().plusMonths(Integer.parseInt(this.ThoiHan.getText())) : null);
        s.luuHopDong(h);
        s.thongBaoThuePhongThanhCong();
        Base.switchScene(e, "primary");
    }

    public void backToPhongtro(ActionEvent event) {
        switchScene(event, "FXML Phongtro");
    }

}
