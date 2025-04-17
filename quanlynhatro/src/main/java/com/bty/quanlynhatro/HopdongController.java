/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bty.quanlynhatro;

import com.bty.pojo.Hopdong;
import com.bty.pojo.MessageBox;
import com.bty.service.HopdongServices;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author User
 */
public class HopdongController implements Initializable {

    HopdongServices h = new HopdongServices();
    @FXML
    private TableView<Hopdong> tbHopDong;

    public void loadTableViewHopDong() {
        TableColumn colMaPhong = new TableColumn("Mã phòng");
        colMaPhong.setCellValueFactory(new PropertyValueFactory("MaPhong"));
        colMaPhong.setPrefWidth(100);

        TableColumn colNgayBatDau = new TableColumn("Ngày bắt đầu");
        colNgayBatDau.setCellValueFactory(new PropertyValueFactory("NgayBatDau"));
        colNgayBatDau.setPrefWidth(100);

        TableColumn colThoiHan = new TableColumn("Thời Hạn");
        colThoiHan.setCellValueFactory(new PropertyValueFactory("ThoiHan"));
        colThoiHan.setPrefWidth(100);

        TableColumn colTrangThai = new TableColumn("Trạng thái");
        colTrangThai.setCellValueFactory(new PropertyValueFactory("TrangThai"));
        colTrangThai.setPrefWidth(100);

        TableColumn colNgaydenhanHopDong = new TableColumn("Ngày đến hạn");
        colNgaydenhanHopDong.setCellValueFactory(new PropertyValueFactory("NgayDenHanHopDong"));
        colNgaydenhanHopDong.setPrefWidth(100);

        TableColumn colGiaHan = new TableColumn();
        colGiaHan.setPrefWidth(80);
        colGiaHan.setCellFactory(r -> {
            Button Btn = new Button("Gia hạn hợp đồng");
            Btn.setOnAction(event -> {
                Button b = (Button) event.getSource();
                TableCell c = (TableCell) b.getParent();
                Hopdong h = (Hopdong) c.getTableRow().getItem();

                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Nhập số tháng gia hạn");
                dialog.setHeaderText("Nhập số tháng gia hạn cho hợp đồng:");
                dialog.setContentText("Số tháng gia hạn:");

                dialog.showAndWait().ifPresent(input -> {
                    try {
                        int monthsToExtend = Integer.parseInt(input); // Số tháng gia hạn

                        // Lấy ngày hiện tại và ngày hết hạn của hợp đồng
                        LocalDate currentDate = LocalDate.now();
                        LocalDate endDate = h.getNgayDenHanHopDong(); // Giả sử hợp đồng có ngày đến hạn (LocalDate)

                        LocalDate newEndDate;
                        if ("Hết hạn".equals(h.getTrangThai())) {
                            // Nếu hợp đồng đã hết hạn, cộng thêm số tháng vào ngày hiện tại
                            newEndDate = currentDate.plusMonths(monthsToExtend);
                        } else {
                            // Nếu hợp đồng còn hiệu lực, cộng thêm số tháng vào ngày đến hạn
                            newEndDate = endDate.plusMonths(monthsToExtend);
                        }

                        // Cập nhật ngày hết hạn mới vào cơ sở dữ liệu
                        HopdongServices s = new HopdongServices();
                        s.updateNgayDenHan(h.getMaHopDong(), newEndDate);

                        // Thông báo cập nhật thành công
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ngày hết hạn hợp đồng đã được gia hạn đến: " + newEndDate, ButtonType.OK);
                        alert.showAndWait();

                        // Làm mới bảng dữ liệu (nếu cần)
                        loadTableData(null);

                    } catch (NumberFormatException ex) {
                        // Xử lý khi người dùng nhập không phải số
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng nhập số tháng hợp lệ.", ButtonType.OK);
                        alert.showAndWait();
                    } catch (SQLException ex) {
                        Logger.getLogger(HopdongController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            });

            TableCell<Hopdong, Void> cell = new TableCell<>();
            cell.setGraphic(Btn);
            return cell;
        });

        TableColumn colTraPhong = new TableColumn();
        colTraPhong.setPrefWidth(80);
        colTraPhong.setCellFactory(r -> {
            Button Btn = new Button("Trả phòng");
            Btn.setOnAction(event -> {
                Button b = (Button) event.getSource();
                TableCell c = (TableCell) b.getParent();
                Hopdong h = (Hopdong) c.getTableRow().getItem();
                try {
                    HopdongServices s = new HopdongServices();
                    double totalDuNo = s.getTotalDuNo(h.getMaHopDong());

                    if (totalDuNo > 0) {
                        // Nếu còn dư nợ, không cho phép trả phòng
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Cảnh báo");
                        alert.setHeaderText("Không thể trả phòng");
                        alert.setContentText("Hợp đồng này còn dư nợ, vui lòng thanh toán trước khi trả phòng.");
                        alert.showAndWait();
                    } else {
                        // Nếu không có dư nợ, hiển thị hộp thoại xác nhận trả phòng
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Xác nhận trả phòng");
                        alert.setHeaderText("Bạn có chắc chắn muốn trả phòng này?");
                        alert.setContentText("Khi trả phòng, hợp đồng sẽ được đánh dấu là đã trả phòng.");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            // Cập nhật trạng thái hợp đồng thành "Đã trả phòng"
                            s.updateTrangThaiTraPhong(h.getMaHopDong(), "Đã trả phòng");
                            s.updateTrangThaiPhong(h.getMaPhong());
                            // Có thể thực hiện cập nhật bảng hoặc giao diện sau khi trả phòng thành công
                            System.out.println("Trả phòng thành công. Hợp đồng đã được cập nhật.");
                        } else {
                            System.out.println("Trả phòng bị hủy.");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Xử lý lỗi
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText("Lỗi khi xử lý trả phòng");
                    alert.setContentText("Đã xảy ra lỗi trong quá trình trả phòng. Vui lòng thử lại.");
                    alert.showAndWait();
                }
            });

            TableCell c = new TableCell();
            c.setGraphic(Btn);
            return c;
        });

        this.tbHopDong.getColumns().addAll(colMaPhong, colNgayBatDau, colThoiHan, colNgaydenhanHopDong, colTrangThai, colGiaHan, colTraPhong);
    }

    private void loadTableData(String kw) throws SQLException {
        this.tbHopDong.setItems(FXCollections.observableList(h.getHopdong(kw)));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadTableViewHopDong();
        try {
            // TODO
            this.loadTableData(null);
        } catch (SQLException ex) {
            Logger.getLogger(HopdongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
