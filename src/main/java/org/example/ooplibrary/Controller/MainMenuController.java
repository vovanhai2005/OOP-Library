package org.example.ooplibrary.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController {

    @FXML
    private Label dashboardBtn, bookListBtn, userListBtn, borrowBtn, returnBtn, logOutBtn;

    @FXML
    private AnchorPane container;

    private void showPane(String path) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(path));
            Scene scene = new Scene(pane);
            container.getChildren().clear();
            container.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
