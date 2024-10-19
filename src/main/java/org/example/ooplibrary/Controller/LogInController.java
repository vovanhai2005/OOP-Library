package org.example.ooplibrary.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.sql.*;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LogInController {

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private Text status;

    @FXML
    private TextField userName;

    @FXML
    void handleLoginAction(ActionEvent event) {
        String username = userName.getText();
        String pass = password.getText();
        if (checkPassword(username, pass)) {
            System.out.println("Success");
        } else {
            System.out.println("Login Failed");
        }
    }

    @FXML
    private boolean checkPassword(String userName, String Password) {
        String url = "jdbc:mysql://localhost:3306/admin";
        String username = "root";
        String password = "";

        System.out.println("username: " + userName);
        System.out.println("password: " + Password);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url , username, password);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select password from admin_info where username=\"" + userName+"\"");
            if (resultSet.next()) {
                if (resultSet.getString(1).equals(Password)) {
                    return true;
                }
                return false;
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
