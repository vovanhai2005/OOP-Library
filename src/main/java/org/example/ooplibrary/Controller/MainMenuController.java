package org.example.ooplibrary.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController {

    @FXML
    private HBox dashboardBtn, bookListBtn, userListBtn, borrowBtn, returnBtn;

    @FXML
    private Label logOutBtn;

    @FXML
    private AnchorPane container;

    private Stage stage;
    private Scene scene;
    private Parent root;



    private void switchToDocumentArchive(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/org/example/ooplibrary/View/DocumentArchive_View.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void switchToUserManagement(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/org/example/ooplibrary/View/UserManagement_View.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
