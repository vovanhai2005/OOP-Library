package org.example.ooplibrary.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class UserUpdateInfoController implements Initializable {

    @FXML
    private TextField DOB;

    @FXML
    private TextField Phone;

    @FXML
    private ImageView addImageButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView bookImage;

    @FXML
    private TextField email;

    @FXML
    private ComboBox<String> gender;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female", "Lesbian", "Gay", "Bisexual", "Transgender", "Queer", "Intersex", "Asexual", "Other");
        gender.setItems(genderList);
    }

    @FXML
    public void chooseImage(MouseEvent mouseEvent) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            bookImage.setImage(image);
        }
    }

    @FXML
    public void handleUpdateInfo(MouseEvent mouseEvent) {

    }
}
