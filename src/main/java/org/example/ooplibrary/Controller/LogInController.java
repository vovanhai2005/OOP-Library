package org.example.ooplibrary.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.ooplibrary.Core.Main;


import java.io.IOException;

public class LogInController implements AbstractLanguageConfig {

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

    @FXML
    private Text signInText;

    @FXML
    private Label SignUp;

    @FXML
    private ImageView viBtn;

    @FXML
    private ImageView enBtn;

    @FXML
    private Label languageText;

    private String language;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void handleLoginAction(ActionEvent event) {

        String username = userName.getText();
        String pass = password.getText();
        if (username.isEmpty() || pass.isEmpty()) {
            if (language.equals("en")) {
                signInAlert.setText("Username or Password cannot be empty");
            }
            else {
                signInAlert.setText("Tên đăng nhập hoặc mật khẩu không thể để trống");
            }
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
            if (language.equals("en")) {
                signInAlert.setText("Incorrect password or username");
            }
            else {
                signInAlert.setText("Sai tên đăng nhập hoặc mật khẩu");
            }
            System.out.println("Login Failed");
        }
    }

    @FXML
    void handleSignUpAction(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/SignUp_View.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            SignUpController signUpController = loader.getController();
            if (language.equals("en")) {
                signUpController.setLanguageToEn();
            } else {
                signUpController.setLanguageToVi();
            }
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchToMainMenu(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/MainMenu_View.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            MainMenuController mainMenuController = loader.getController();
            if (language.equals("en")) {
                mainMenuController.setLanguageToEn();
            } else {
                mainMenuController.setLanguageToVi();
            }
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
            if (language.equals("en")) {
                userMainMenuController.setLanguageToEn();
            } else {
                userMainMenuController.setLanguageToVi();
            }
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLanguageToEn() {
        language = "en";
        languageText.setText("Language:");
        signInText.setText("Sign In");
        SignUp.setText("Sign Up");
        loginButton.setText("Login");
        userName.setPromptText("Username");
        password.setPromptText("Password");

    }

    public void setLanguageToVi() {
        language = "vi";
        languageText.setText("Ngôn ngữ:");
        signInText.setText("Đăng nhập");
        SignUp.setText("Đăng ký");
        loginButton.setText("Đăng nhập");
        userName.setPromptText("Tên đăng nhập");
        password.setPromptText("Mật khẩu");
    }


}