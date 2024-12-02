package org.example.ooplibrary.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.ooplibrary.Core.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable, AbstractLanguageConfig {

    @FXML
    private ImageView addImageButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView avatarImage;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private Text dobAlert;

    @FXML
    private TextField email;

    @FXML
    private Text emailAlert;

    @FXML
    private TextField fullName;

    @FXML
    private Text fullNameAlert;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private Text genderAlert;

    @FXML
    private Text instruction1;

    @FXML
    private Text instruction2;

    @FXML
    private Text instructionTitleText;

    @FXML
    private Label languageText;

    @FXML
    private PasswordField password;

    @FXML
    private Text passwordAlert;

    @FXML
    private Text personalInfoText;

    @FXML
    private Text phoneNoAlert;

    @FXML
    private TextField phoneNumber;

    @FXML
    private Label signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Text signUpText;

    @FXML
    private TextField userName;

    @FXML
    private Text usernameAlert;


    private String language;


    private Stage stage;
    private Scene scene;
    private Parent root;

    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female", "Lesbian", "Gay", "Bisexual", "Transgender", "Queer", "Intersex", "Asexual", "Other");
        gender.setItems(genderList);
    }

    @FXML
    void handleSignUpAction(ActionEvent event) {
        try {
            String username = userName.getText();
            String pass = password.getText();
            System.err.println(dateOfBirth.getAccessibleText());
            usernameAlert.setText("");
            passwordAlert.setText("");
            dobAlert.setText("");
            emailAlert.setText("");
            fullNameAlert.setText("");
            genderAlert.setText("");
            phoneNoAlert.setText("");
            boolean invalidInput = false;
            if (username.isEmpty()) {
                if (language.equals("en")) {
                    usernameAlert.setText("Username cannot be empty!");
                } else {
                    usernameAlert.setText("Tên đăng nhập không thể để trống!");
                }
                invalidInput = true;
            } else if (!SQLController.checkSignUp(username)) {
                if (language.equals("en")) {
                    usernameAlert.setText("Username already exists!");
                } else {
                    usernameAlert.setText("Tên đăng nhập đã tồn tại!");
                }
                invalidInput = true;
            }
            if (pass.isEmpty()) {
                if (language.equals("en")) {
                    passwordAlert.setText("Password cannot be empty!");
                } else {
                    passwordAlert.setText("Mật khẩu không thể để trống!");
                }
                invalidInput = true;
            }
            if (dateOfBirth.getValue() == null) {
                if (language.equals("en")) {
                    dobAlert.setText("Date of Birth cannot be empty!");
                } else {
                    dobAlert.setText("Ngày sinh không thể để trống!");
                }
                invalidInput = true;
            }
            if (email.getText().isEmpty()) {
                if (language.equals("en")) {
                    emailAlert.setText("Email cannot be empty!");
                } else {
                    emailAlert.setText("Email không thể để trống!");
                }
                invalidInput = true;
            }
            if (fullName.getText().isEmpty()) {
                if (language.equals("en")) {
                    fullNameAlert.setText("Full Name cannot be empty!");
                } else {
                    fullNameAlert.setText("Họ và Tên không thể để trống!");
                }
                invalidInput = true;
            }
            if (phoneNumber.getText().isEmpty()) {
                if (language.equals("en")) {
                    phoneNoAlert.setText("Phone Number cannot be empty!");
                } else {
                    phoneNoAlert.setText("Số điện thoại không thể để trống!");
                }
                invalidInput = true;
            }
            if (gender.getValue() == null) {
                if (language.equals("en")) {
                    genderAlert.setText("Gender cannot be empty!");
                }
                else {
                    genderAlert.setText("Giới tính không thể để trống!");
                }
                invalidInput = true;
            }

            if (invalidInput) {
                return;
            }

            if (SQLController.getUserInfoDataByUsername(username) != null) {
                if (language.equals("en")) {
                    usernameAlert.setText("Username already exists!");
                } else {
                    usernameAlert.setText("Tên đăng nhập đã tồn tại!");
                }
                return;
            }

            System.out.println("Success");
            SQLController.addUser(username, pass, fullName.getText(), SQLController.getDateOfBirthAsString(dateOfBirth), email.getText(), phoneNumber.getText(), SQLController.convertImageViewToBlob(avatarImage), gender.getValue());
            switchToUserMainMenu(event);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToSignInView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/LogIn_View.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            LogInController logInController = loader.getController();
            if (language.equals("en")) {
                logInController.setLanguageToEn();
            } else {
                logInController.setLanguageToVi();
            }
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void chooseImage(MouseEvent event) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        if (language.equals("en")) {
            fileChooser.setTitle("Open Resource File");
        } else {
            fileChooser.setTitle("Mở File Ảnh");
        }

        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            avatarImage.setImage(image);
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
        personalInfoText.setText("Personal Information");
        instructionTitleText.setText("Instructions:");
        instruction1.setText("- Every user's information must be filled and must not be left blanked.");
        instruction2.setText("- Username should not contain full name or other personal information.");
        dateOfBirth.setPromptText("Date of Birth");
        email.setPromptText("Email");
        gender.setPromptText("Gender");
        phoneNumber.setPromptText("Phone No.");
        signUpText.setText("Sign Up");
        signInButton.setText("Sign In");
        fullName.setPromptText("Full Name");
        userName.setPromptText("Username");
        password.setPromptText("Password");
        signUpButton.setText("Sign Up");

    }

    public void setLanguageToVi() {
        language = "vi";
        languageText.setText("Ngôn ngữ:");
        personalInfoText.setText("Thông Tin Cá Nhân");
        instructionTitleText.setText("Hướng dẫn:");
        instruction1.setText("- Mọi thông tin của người dùng phải được điền và không được bỏ trống.");
        instruction2.setText("- Tên người dùng không nên chứa tên đầy đủ hoặc thông tin cá nhân khác.");
        dateOfBirth.setPromptText("Ngày Sinh");
        email.setPromptText("Email");
        gender.setPromptText("Giới Tính");
        phoneNumber.setPromptText("Số Điện Thoại");
        signUpText.setText("Đăng Ký");
        signInButton.setText("Đăng Nhập");
        fullName.setPromptText("Họ và Tên Đầy Đủ");
        userName.setPromptText("Tên Đăng Nhập");
        password.setPromptText("Mật Khẩu");
        signUpButton.setText("Đăng Ký");

    }

}
