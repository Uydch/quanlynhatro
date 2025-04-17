/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bty.pojo;

import javafx.scene.control.Alert;

/**
 *
 * @author User
 */
public class MessageBox {
    public static Alert getBox(String title,String Content,Alert.AlertType type){
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setContentText(Content);
        
        return a;
    }
}