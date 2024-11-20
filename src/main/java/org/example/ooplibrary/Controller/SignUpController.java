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

public class SignUpController implements Initializable {

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private TextField userName;

    @FXML
    private TextField fullName;

    @FXML
    private TextField email;

    @FXML
    private TextField phoneNumber;

    @FXML
    private ImageView addImageButton;

    @FXML
    private ImageView avatarImage;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Text dobAlert;

    @FXML
    private Text emailAlert;

    @FXML
    private Text fullNameAlert;

    @FXML
    private Text genderAlert;

    @FXML
    private Text phoneNoAlert;

    @FXML
    private Text usernameAlert;

    @FXML
    private Text passwordAlert;


    private Stage stage;
    private Scene scene;
    private Parent root;

    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female");
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
                usernameAlert.setText("Username cannot be empty!");
                invalidInput = true;
            } else if (!SQLController.checkSignUp(username)) {
                usernameAlert.setText("Username already exists!");
                invalidInput = true;
            }
            if (pass.isEmpty()) {
                passwordAlert.setText("Password cannot be empty!");
                invalidInput = true;
            }
            if (dateOfBirth.getValue() == null) {
                dobAlert.setText("Date of Birth cannot be empty!");
                invalidInput = true;
            }
            if (email.getText().isEmpty()) {
                emailAlert.setText("Email cannot be empty!");
                invalidInput = true;
            }
            if (fullName.getText().isEmpty()) {
                fullNameAlert.setText("Full Name cannot be empty!");
                invalidInput = true;
            }
            if (phoneNumber.getText().isEmpty()) {
                phoneNoAlert.setText("Phone Number cannot be empty!");
                invalidInput = true;
            }
            if (gender.getValue() == null) {
                genderAlert.setText("Gender cannot be empty!");
                invalidInput = true;
            }

            if (invalidInput) {
                return;
            }


            System.out.println("Success");
            SQLController.addUser(username, pass, fullName.getText(), SQLController.getDateOfBirthAsString(dateOfBirth), email.getText(), phoneNumber.getText(), SQLController.convertImageViewToBlob(avatarImage));
            switchToMainMenu(event);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToSignInView(MouseEvent event) {
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
    public void chooseImage(MouseEvent event) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            avatarImage.setImage(image);
        }
    }

    private void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/org/example/ooplibrary/View/MainMenu_View.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
