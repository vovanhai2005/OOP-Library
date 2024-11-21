package org.example.ooplibrary.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.ooplibrary.Core.Main;

import java.io.IOException;

public class LogInController {

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private Text status;

    @FXML
    private Text signInAlert;

    @FXML
    private TextField userName;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void handleLoginAction(ActionEvent event) {

        String username = userName.getText();
        String pass = password.getText();
        if (username.isEmpty() || pass.isEmpty()) {
            signInAlert.setText("Username or Password cannot be empty");
            return;
        }

        if (SQLController.checkPassword(username, pass)) {
            System.out.println("Success");
            if (SQLController.isAdmin(username)) {
                switchToMainMenu(event);
            } else {
                switchToUserMainMenu(event);
            }
            System.out.println("Switched to Main Menu");
        } else {
            signInAlert.setText("Incorrect password or username");
            System.out.println("Login Failed");
        }
    }

    @FXML
    void handleSignUpAction(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/org/example/ooplibrary/View/SignUp_View.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchToMainMenu(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/org/example/ooplibrary/View/MainMenu_View.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchToUserMainMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/UserMainMenu_View.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            UserMainMenuController userMainMenuController = loader.getController();
            userMainMenuController.setUsername(userName.getText());
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}