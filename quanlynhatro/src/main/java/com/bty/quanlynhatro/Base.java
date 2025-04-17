/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bty.quanlynhatro;

import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class Base {
    public static Object switchScene(ActionEvent event, String fxmlFile) {
    try {
        URL fxmlURL = Base.class.getResource(fxmlFile + ".fxml");
        System.out.println("🔍 Đường dẫn FXML: " + fxmlURL);
        
        if (fxmlURL == null) {
            System.out.println("❌ Lỗi: Không tìm thấy file " + fxmlFile + ".fxml");
            return null;
        }

        FXMLLoader loader = new FXMLLoader(fxmlURL);
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

        Object controller = loader.getController();
        System.out.println("🎯 Controller: " + controller);
        return controller;
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}

}

