package org.example.ooplibrary.Controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.ooplibrary.Object.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserSettingController implements Initializable {

    @FXML
    private Text alertMess;

    @FXML
    private Button applyBtn;

    @FXML
    private ComboBox<?> language;

    @FXML
    private PasswordField newPassword1;

    @FXML
    private PasswordField newPassword2;

    @FXML
    private PasswordField oldPassword;

    @FXML
    private ComboBox<?> uiMode;



    protected String password;
    protected String username;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alertMess.setText("");
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @FXML
    public void handleChanges(MouseEvent event) {
        if (!oldPassword.getText().isEmpty() || !newPassword1.getText().isEmpty() || !newPassword2.getText().isEmpty()) {
            if (oldPassword.getText().isEmpty()) {
                alertMess.setText("Old password cannot be empty");
                return;

            } else if (oldPassword.getText().equals(password)) {
                if (newPassword1.getText().isEmpty() || newPassword2.getText().isEmpty()) {
                    alertMess.setText("Please enter both new password fields");
                    return;
                } else if (!newPassword1.getText().equals(newPassword2.getText())) {
                    alertMess.setText("Incorrect re-entered password");
                    return;
                } else if (newPassword1.getText().equals(oldPassword.getText())) {
                    alertMess.setText("New password cannot be the same as the old password");
                    return;
                }
                else {
                    alertMess.setText("Password changed successfully");
                    SQLController.updateUserPassword(username, newPassword1.getText());
                }
            } else {
                alertMess.setText("Old password is incorrect");
                return;
            }
        }
        //Turn off the stage
        Stage stage = (Stage) applyBtn.getScene().getWindow();
        stage.close();
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
