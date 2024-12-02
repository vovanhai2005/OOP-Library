package org.example.ooplibrary.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.ooplibrary.Object.User;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class UserUpdateInfoController implements Initializable {

    @FXML
    private TextField Fullname;

    @FXML
    private DatePicker DOB;

    @FXML
    private TextField email;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private TextField Phone;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView bookImage;

    private String username;

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

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    public void handleUpdateInfo(MouseEvent mouseEvent) {
        try {
            byte[] bookImageBlob = SQLController.convertImageViewToBlob(bookImage);
            User user = SQLController.getUserInfoDataByUsername(username);
//            System.out.println(user.getFullName());
            user.setFullName(Fullname.getText());
            user.setDob(SQLController.getDateOfBirthAsString(DOB));
            user.setEmail(email.getText());
            user.setGender(gender.getValue());
            user.setPhoneNumber(Phone.getText());
            user.setImage(bookImageBlob);

            SQLController.updateUserInfo(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exitUpdatePopUP(MouseEvent mouseEvent) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }
}
