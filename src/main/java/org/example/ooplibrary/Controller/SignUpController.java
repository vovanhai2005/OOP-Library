package org.example.ooplibrary.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class SignUpController {

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private static ComboBox<String> gender;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private TextField userName;

    @FXML
    private TextField fullName;

    @FXML
    private TextField email;

    @FXML
    private ImageView addImageButton;

    @FXML
    private ImageView avatarImage;

    @FXML
    private AnchorPane anchorPane;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        final ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female");
        gender.setItems(genderList);
    }

    @FXML
    void handleSignUpAction(ActionEvent event) {
        try {
            String username = userName.getText();
            String pass = password.getText();
            String confirmPass = confirmPassword.getText();
            System.err.println(dateOfBirth.getAccessibleText());
            if (username.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()
                    || dateOfBirth.getValue() == null || email.getText().isEmpty()
                    || fullName.getText().isEmpty())
                return;

            if (SQLController.checkSignUp(username, pass) && confirmPass.equals(pass)) {
                System.out.println("Success");
                SQLController.addUser(username, pass, fullName.getText(), SQLController.getDateOfBirthAsString(dateOfBirth), email.getText());
                switchToMainMenu(event);
                System.out.println("Switched to Main Menu");
            } else {
                System.out.println("SignUp Failed");
            }
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
