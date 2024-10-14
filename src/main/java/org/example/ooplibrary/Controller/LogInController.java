package org.example.ooplibrary.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LogInController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    public void initialize() {
        // You can add initialization code here
    }

    public static void checkLogIn(String userName, String password) {
        String url = "jdbc:mysql://localhost:3306/admin";
        String username = "root";
        String Password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, Password);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select password from admin where username = \"" + userName + "\"");

            if (resultSet.next()) {
                if (resultSet.getString(1).equals(password)) {
                    System.out.println("Sucessfully Logged in");
                } else {
                    System.out.println("Incorrect Username or Password");
                }
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void handleLoginAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Add login authentication logic here
        // Example: Check if username and password are valid
        checkLogIn(username, password);

        // After authentication logic, you can proceed accordingly
    }
}