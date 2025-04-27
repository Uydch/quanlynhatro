/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bty.quanlynhatro;

import com.bty.pojo.MessageBox;
import com.bty.pojo.Phongdenhan;
import com.bty.pojo.Phongtro;
import com.bty.pojo.Thanhtoan;
import static com.bty.quanlynhatro.Base.switchScene;
import com.bty.service.PhongtroServices;
import com.bty.service.ThanhtoanServices;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class ThanhtoanController implements Initializable {

    ThanhtoanServices t = new ThanhtoanServices();

    @FXML
    private TableView<Thanhtoan> tbPhongDenHan;
    @FXML
    private TextField txtKeyWord;
    @FXML
    private TextField txtTiendien;
    @FXML
    private TextField txtTiennuoc;
    @FXML
    private TextField txtPhiphat;

    public void loadTableViewPhongDenHan() {
        tbPhongDenHan.setEditable(true);

        TableColumn colTenphong = new TableColumn("Tên phòng");
        colTenphong.setCellValueFactory(new PropertyValueFactory("TenPhong"));
        colTenphong.setPrefWidth(100);

        TableColumn colHoten = new TableColumn("Họ và tên");
        colHoten.setCellValueFactory(new PropertyValueFactory("HoTenNguoiThue"));
        colHoten.setPrefWidth(200);

        TableColumn colGiathue = new TableColumn("Giá thuê");
        colGiathue.setCellValueFactory(new PropertyValueFactory("GiaThue"));
        colGiathue.setPrefWidth(120);

        TableColumn<Thanhtoan, Double> colTiendien = new TableColumn("Chỉ số điện");
        colTiendien.setCellValueFactory(new PropertyValueFactory("ChiSoDien"));
        colTiendien.setPrefWidth(120);
        colTiendien.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colTiendien.setOnEditCommit((var event) -> {
            colTiendien.setEditable(true);
            Thanhtoan p = event.getRowValue();
            try {
                double soKiDien = event.getNewValue();
                if (soKiDien < 0) {
                    MessageBox.getBox("Thông báo", "Chỉ số điện không thể là số âm!", Alert.AlertType.WARNING).showAndWait();
                    return;
                }
                t.capNhatTienDien(p.getMaThanhToan(), soKiDien);
                double soKiDienThangTruoc = t.getChiSoDienThangTruoc(p.getMaPhong());
                if (soKiDien < soKiDienThangTruoc) {
                    MessageBox.getBox("Thông báo", "Chỉ số điện mới không được nhỏ hơn chỉ số điện cũ ", Alert.AlertType.WARNING).showAndWait();
                    return;
                }
                this.loadTableData(null);
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Giá tiền điện không hợp lệ!");
            } catch (SQLException ex) {
                Logger.getLogger(ThanhtoanController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        TableColumn<Thanhtoan, Double> colTiennuoc = new TableColumn("Chỉ số nước");
        colTiennuoc.setCellValueFactory(new PropertyValueFactory("ChiSoNuoc"));
        colTiennuoc.setPrefWidth(120);
        colTiennuoc.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colTiennuoc.setOnEditCommit(event -> {
            colTiennuoc.setEditable(true);
            Thanhtoan p = event.getRowValue();
            try {
                double chiSoNuoc = event.getNewValue();
                if (chiSoNuoc < 0) {
                    MessageBox.getBox("Thông báo", "Chỉ số nước không thể là số âm!", Alert.AlertType.WARNING).showAndWait();
                    return;
                }
                t.capNhatTienNuoc(p.getMaThanhToan(), chiSoNuoc);
                double chiSoNuocThangTruoc = t.getChiSoNuocThangTruoc(p.getMaPhong());
                if (chiSoNuoc < chiSoNuocThangTruoc) {
                    MessageBox.getBox("Thông báo", "Chỉ số nước mới không được nhỏ hơn chỉ số nước cũ ", Alert.AlertType.WARNING).showAndWait();
                    return;
                }
                this.loadTableData(null);
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Giá tiền nước không hợp lệ!");
            } catch (SQLException ex) {
                Logger.getLogger(ThanhtoanController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        TableColumn<Thanhtoan, Double> colPhiphat = new TableColumn("Phí phạt");
        colPhiphat.setCellValueFactory(new PropertyValueFactory("PhiPhat"));
        colPhiphat.setPrefWidth(120);
        colPhiphat.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        TableColumn colTongtien = new TableColumn("Tổng tiền");
        colTongtien.setCellValueFactory(new PropertyValueFactory("TongTien"));
        colTongtien.setPrefWidth(130);

        TableColumn colNgayDenHan = new TableColumn("Ngày đến hạn");
        colNgayDenHan.setCellValueFactory(new PropertyValueFactory("NgayDenHan"));
        colNgayDenHan.setPrefWidth(100);

        TableColumn colDuNo = new TableColumn("Dư nợ");
        colDuNo.setCellValueFactory(new PropertyValueFactory("DuNo"));
        colDuNo.setPrefWidth(130);

        TableColumn colTrangThai = new TableColumn("Trạng thái");
        colTrangThai.setCellValueFactory(new PropertyValueFactory("TrangThaiThanhToan"));
        colTrangThai.setPrefWidth(130);

        TableColumn colThanhtoan = new TableColumn();
//        colThanhtoan.setPrefWidth();
        colThanhtoan.setCellFactory(r -> {
            Button btn = new Button("Thanh toán");
            btn.setOnAction(event -> {
                Alert a = MessageBox.getBox("Thanh toán", "Xác nhận thanh toán cho phòng?", Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) event.getSource();
                        TableCell c = (TableCell) b.getParent();
                        Thanhtoan p = (Thanhtoan) c.getTableRow().getItem();
                        ThanhtoanServices s = new ThanhtoanServices();
                        Optional<String> result = s.getThanhToanAmount(p.getTenPhong());
                        result.ifPresent(amount -> {
                            System.out.println("Số tiền thanh toán là: " + amount);

                            s.updateThanhToanStatus(p.getMaThanhToan(), "Đã thanh toán " + amount);
                            double soTienThanhToan = Double.parseDouble(amount);
                            if (soTienThanhToan < 0) {
                                MessageBox.getBox("Lỗi", "Số tiền thanh toán không thể là số âm!", Alert.AlertType.ERROR).showAndWait();
                                return;
                            }

                            if (soTienThanhToan > p.getTongTien()) {
                                MessageBox.getBox("Lỗi", "Số tiền thanh toán không thể lớn hơn tổng tiền!", Alert.AlertType.ERROR).showAndWait();
                                return;
                            }
                            double duNo = p.getTongTien() - soTienThanhToan;
                            p.setDuNo(duNo);
                            try {
                                t.updatePhongDenHanToThanhtoan(p);
                            } catch (SQLException ex) {
                                Logger.getLogger(ThanhtoanController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            try {
                                loadTableData(null);
                            } catch (SQLException ex) {
                                Logger.getLogger(ThanhtoanController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }
                });

            });

            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;
        });

        this.tbPhongDenHan.getColumns().addAll(colTenphong, colHoten, colGiathue, colTiendien, colTiennuoc, colPhiphat,
                colTongtien, colDuNo, colTrangThai, colNgayDenHan, colThanhtoan);
    }

    private boolean isValidInput(double value) {
        return value >= 0;
    }

    private void tienPhat() throws SQLException {
        double phiPhatMoiNgay = Double.parseDouble(txtPhiphat.getText());
        if (!isValidInput(phiPhatMoiNgay)) {
            MessageBox.getBox("Lỗi", "Phí phạt không thể là số âm!", Alert.AlertType.WARNING);
            return;
        }
        for (Thanhtoan p : tbPhongDenHan.getItems()) {
            LocalDate ngayHienTai = LocalDate.now();
            LocalDate ngayDenHan = p.getNgayDenHan();

            if (ngayHienTai.isAfter(ngayDenHan)) {
                long soNgayTre = ChronoUnit.DAYS.between(ngayDenHan, ngayHienTai);
                p.setPhiPhat(phiPhatMoiNgay * soNgayTre);
                t.updatePhongDenHanToThanhtoan(p);
            } else {
                p.setPhiPhat(0.0);
            }
        }
        tbPhongDenHan.refresh();
    }

    private double getSafeValue(Double value) {
        return (value != null) ? value : 0.0;
    }

    private void tongTien() throws SQLException {
        // Kiểm tra nếu giá tiền điện và nước nhập vào không phải số âm
        double giaTienDien = Double.parseDouble(txtTiendien.getText());
        double giaTienNuoc = Double.parseDouble(txtTiennuoc.getText());

        if (!isValidInput(giaTienDien) || !isValidInput(giaTienNuoc)) {
            MessageBox.getBox("Lỗi", "Giá tiền điện hoặc tiền nước không thể là số âm!", Alert.AlertType.WARNING);
            return;
        }

        for (Thanhtoan p : tbPhongDenHan.getItems()) {
            int giathue = p.getGiaThue();
            double soKiDienThangTruoc = t.getChiSoDienThangTruoc(p.getMaPhong());
            double tiendien = t.tinhTienDien(p.getChiSoDien(), soKiDienThangTruoc, giaTienDien);

            double chiSoNuocThangTruoc = t.getChiSoNuocThangTruoc(p.getMaPhong());
            double tiennuoc = t.tinhTienNuoc(p.getChiSoNuoc(), chiSoNuocThangTruoc, giaTienNuoc);

            double tienphat = (p.getPhiPhat() != null) ? p.getPhiPhat() : 0.0;
            double tongtien = t.tinhTongTien(giathue, tiendien, tiennuoc, tienphat);
            p.setTongTien(tongtien);

            t.updatePhongDenHanToThanhtoan(p);
        }
        tbPhongDenHan.refresh();

    }

    private void loadTableData(String kw) throws SQLException {
        this.t.savePhongDenHanToThanhtoan();
        this.tbPhongDenHan.setItems(FXCollections.observableList(t.loadPhongDenhan(kw)));
        this.tongTien();
        this.tienPhat();
    }

    public void back(ActionEvent event) {
        switchScene(event, "primary");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadTableViewPhongDenHan();
        try {
            this.loadTableData(null);
            this.tienPhat();
            this.tongTien();
        } catch (SQLException ex) {
            Logger.getLogger(ThanhtoanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
