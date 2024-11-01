package org.example.ooplibrary.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private Label bookListBtn;

    @FXML
    private Label borrowBtn;

    @FXML
    private Label dashboardBtn;

    @FXML
    private Label logOutBtn;

    @FXML
    private Label returnBtn;

    @FXML
    private Label userListBtn;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void switchToBorrowDocumentView(MouseEvent event) {

    }

    @FXML
    void switchToDocumentArchiveView(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/org/example/ooplibrary/View/DocumentArchive_View.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToLoginView(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/org/example/ooplibrary/View/LogIn_View.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToReturnDocumentView(MouseEvent event) {

    }

    @FXML
    void switchToUserManagementView(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/org/example/ooplibrary/View/UserManagement_View.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToMainMenuView(MouseEvent event) {
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

}
