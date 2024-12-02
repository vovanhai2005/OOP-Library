package org.example.ooplibrary.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
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

    @FXML
    private ImageView addImageButton;

    @FXML
    private Button applyBtn;

    @FXML
    private Button closeBtn;

    @FXML
    private Text dobText;

    @FXML
    private Text emailText;

    @FXML
    private Text fullNameText;

    @FXML
    private Text genderText;

    @FXML
    private Text phoneNumberText;

    @FXML
    private Text updateInfoTitle;

    private String username;

    private String language;

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
            if (Fullname.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (language.equals("en")) {
                    alert.setTitle("Error");
                    alert.setHeaderText("Full Name is empty");
                    alert.setContentText("Please enter your full name");
                } else {
                    alert.setTitle("Lỗi");
                    alert.setHeaderText("Họ và tên trống");
                    alert.setContentText("Vui lòng nhập họ và tên của bạn");
                }
                alert.showAndWait();
                return;
            }
            if (DOB.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (language.equals("en")) {
                    alert.setTitle("Error");
                    alert.setHeaderText("Date of Birth is empty");
                    alert.setContentText("Please enter your date of birth");
                } else {
                    alert.setTitle("Lỗi");
                    alert.setHeaderText("Ngày sinh trống");
                    alert.setContentText("Vui lòng nhập ngày sinh của bạn");
                }
                alert.showAndWait();
                return;
            }
            if (email.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (language.equals("en")) {
                    alert.setTitle("Error");
                    alert.setHeaderText("Email is empty");
                    alert.setContentText("Please enter your email");
                } else {
                    alert.setTitle("Lỗi");
                    alert.setHeaderText("Email trống");
                    alert.setContentText("Vui lòng nhập email của bạn");
                }
                alert.showAndWait();
                return;
            }
            if (gender.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (language.equals("en")) {
                    alert.setTitle("Error");
                    alert.setHeaderText("Gender is empty");
                    alert.setContentText("Please choose your gender");
                } else {
                    alert.setTitle("Lỗi");
                    alert.setHeaderText("Giới tính trống");
                    alert.setContentText("Vui lòng chọn giới tính của bạn");
                }
                alert.showAndWait();
                return;
            }
            if (Phone.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (language.equals("en")) {
                    alert.setTitle("Error");
                    alert.setHeaderText("Phone Number is empty");
                    alert.setContentText("Please enter your phone number");
                } else {
                    alert.setTitle("Lỗi");
                    alert.setHeaderText("Số điện thoại trống");
                    alert.setContentText("Vui lòng nhập số điện thoại của bạn");
                }
                alert.showAndWait();
                return;
            }
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

            if (language.equals("en")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Update Information Successfully");
                alert.setContentText("Your information has been updated successfully");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText("Cập nhật thông tin thành công");
                alert.setContentText("Thông tin của bạn đã được cập nhật thành công");
                alert.showAndWait();
            }
            //hide the window
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exitUpdatePopUP(MouseEvent mouseEvent) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    public void setLanguageToEn() {
        language = "en";
        updateInfoTitle.setText("Update Information");
        fullNameText.setText("Full Name:");
        dobText.setText("DOB:");
        genderText.setText("Gender");
        emailText.setText("Email:");
        phoneNumberText.setText("Phone Number:");
        applyBtn.setText("Apply");
        closeBtn.setText("Close");

    }

    public void setLanguageToVi() {
        language = "vi";
        updateInfoTitle.setText("Cập nhật thông tin");
        fullNameText.setText("Họ và tên:");
        dobText.setText("Ngày sinh:");
        genderText.setText("Giới tính:");
        emailText.setText("Email:");
        phoneNumberText.setText("Số điện thoại:");
        applyBtn.setText("Áp dụng");
        closeBtn.setText("Đóng");

    }
}
