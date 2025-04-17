package com.bty.quanlynhatro;

import static com.bty.quanlynhatro.Base.switchScene;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

public class PrimaryController {

    @FXML
    private RadioButton rdodk;
    @FXML
    private RadioButton rdott;
    @FXML
    private RadioButton rdohd;
    

//    @FXML
//    private void nextHandler(ActionEvent event) {
//        System.out.println("Next button clicked!");
//    }

//    @FXML
//    private void switchToSecondary() throws IOException {
//        App.setRoot("secondary");
//    }

    public void nextHandler(ActionEvent e) throws IOException{
        String fxmlFile = "";

        // Kiểm tra radio nào được chọn
        if (rdodk.isSelected()) {
            fxmlFile = "FXML Phongtro";  // Đăng ký thuê phòng
        } else if (rdott.isSelected()) {
            fxmlFile = "Thanhtoan";  // Thanh toán tiền trọ
        } else if (rdohd.isSelected()) {
            fxmlFile = "Hopdong";  // Quản lý hợp đồng
        }

        if (!fxmlFile.isEmpty()) {
            switchScene(e ,fxmlFile);
        }
    }

   
    }

