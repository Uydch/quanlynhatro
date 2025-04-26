/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bty.quanlynhatro;

import com.bty.pojo.MessageBox;
import com.bty.pojo.Phongtro;
import static com.bty.quanlynhatro.Base.switchScene;
import com.bty.quanlynhatro.KyHDController;
import com.bty.service.KyHDServices;
import com.bty.service.PhongtroServices;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLPhongtroController implements Initializable {

    PhongtroServices s = new PhongtroServices();

    @FXML
    private TableView<Phongtro> tbPhongtro;
    @FXML
    private TextField txtKeyword;
    @FXML
    private TextField txtTenphong;
    @FXML
    private TextField txtHoTen;
    @FXML
    private TextField txtSDT;
    @FXML
    private TextField txtCCCD;
    @FXML
    private TextField txtDiaChi;
    @FXML
    private TextField addTenphong;
    @FXML
    private TextField addGiathue;

    public void loadTableViewPhongtro() {
        TableColumn colTenphong = new TableColumn("Tên phòng");
        colTenphong.setCellValueFactory(new PropertyValueFactory("TenPhong"));
        colTenphong.setPrefWidth(100);

        TableColumn colTrangthai = new TableColumn("Trạng thái");
        colTrangthai.setCellValueFactory(new PropertyValueFactory("TrangThai"));
        colTrangthai.setPrefWidth(100);

        TableColumn colGiathue = new TableColumn("Giá thuê");
        colGiathue.setCellValueFactory(new PropertyValueFactory("GiaThue"));
        colGiathue.setPrefWidth(100);

        TableColumn colDel = new TableColumn();
        colDel.setPrefWidth(80);
        colDel.setCellFactory(r -> {
            Button Btn = new Button("Xóa");
            Btn.setOnAction(event -> {
                Alert a = MessageBox.getBox("Xóa phòng", "Xác nhận xóa phòng", Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        PhongtroServices s = new PhongtroServices();
                        Button b = (Button) event.getSource();
                        TableCell Cell = (TableCell) b.getParent();
                        Phongtro p = (Phongtro) Cell.getTableRow().getItem();
                        try {
                            if (s.xoaPhong(p.getMaPhong())) {
                                MessageBox.getBox("Xóa phòng", "Xóa phòng thành công!", Alert.AlertType.INFORMATION).show();
                                this.loadTableData(null);
                            } else {
                                MessageBox.getBox("Xóa phòng", "Xóa phòng không thành công!", Alert.AlertType.WARNING).show();
                            }
                        } catch (SQLException ex) {
                            MessageBox.getBox("Xóa phòng", "Xóa phòng không thành công!Lỗi!", Alert.AlertType.WARNING).show();
                            Logger.getLogger(FXMLPhongtroController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            });

            TableCell c = new TableCell();
            c.setGraphic(Btn);
            return c;
        });

        this.tbPhongtro.getColumns().addAll(colTenphong, colTrangthai, colGiathue, colDel);
    }

    private void loadTableData(String kw) throws SQLException {
        this.tbPhongtro.setItems(FXCollections.observableList(s.getPhongtro(kw)));
    }

    private void kiemTraTrangThai() {
        this.tbPhongtro.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (s.kiemTraTrangThaiPhong(newSelection)) {
                    this.txtTenphong.setText(newSelection.getTenPhong());
                } else {
                    s.hienThiThongBaoPhongDaCoNguoi();
                    this.tbPhongtro.getSelectionModel().clearSelection();
                }
            }
        });
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadTableViewPhongtro();
        this.kiemTraTrangThai();
        this.txtTenphong.setEditable(false);

        try {
            this.loadTableData(null);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPhongtroController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.txtKeyword.textProperty().addListener((evt) -> {
            try {
                this.loadTableData(this.txtKeyword.getText());
            } catch (SQLException ex) {
                Logger.getLogger(FXMLPhongtroController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void DangkythueHandler(ActionEvent e) {
        if (!s.kiemTraThongTin(
                txtHoTen.getText(),
                txtSDT.getText(),
                txtCCCD.getText(),
                txtDiaChi.getText(),
                txtTenphong.getText())) {

            s.hienThiThongBaoThieuThongTin();
            return;
        }
        
        KyHDController controller = (KyHDController) switchScene(e, "KyHD");
        if (controller != null) {
            controller.setThongTinKhach(
                    txtHoTen.getText(),
                    txtCCCD.getText(),
                    txtSDT.getText(),
                    txtDiaChi.getText(),
                    txtTenphong.getText()
            );
        }
    }

    public void backToPhongtro(ActionEvent event) {
        switchScene(event, "primary");
    }

    public void themPhong(ActionEvent e) throws SQLException {
        Phongtro p = new Phongtro(this.addTenphong.getText(), "", Integer.parseInt(this.addGiathue.getText()));
        if (!s.kiemTraThongTinThemPhong(this.addTenphong.getText(), this.addGiathue.getText())) {
                MessageBox.getBox("Thêm phòng", "Thêm phòng không thành công!Vui lòng nhập đầy đủ thông tin!", Alert.AlertType.WARNING).show();
            return;
        }
        try {
            if (s.themPhong(p)) {
                MessageBox.getBox("Thêm phòng", "Thêm phòng thành công!", Alert.AlertType.INFORMATION).show();
                this.loadTableData(null);
            } else {
                MessageBox.getBox("Thêm phòng", "Thêm phòng không thành công!", Alert.AlertType.WARNING).show();
            }
        } catch (SQLException ex) {
            MessageBox.getBox("Xóa phòng", "Thêm phòng không thành công!Lỗi!", Alert.AlertType.WARNING).show();
            Logger.getLogger(FXMLPhongtroController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
