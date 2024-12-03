package org.example.ooplibrary.Controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.ooplibrary.Object.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserSettingController implements Initializable {

    @FXML
    private Text alertMess;

    @FXML
    private Button applyBtn;


    @FXML
    private PasswordField newPassword1;

    @FXML
    private PasswordField newPassword2;

    @FXML
    private PasswordField oldPassword;

    @FXML
    private ComboBox<?> uiMode;

    protected String language;

    protected String password;
    protected String username;

    protected Stage stage;
    protected Scene scene;
    protected Parent root;


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

    @FXML
    public void deleteUser(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText("Bạn có chắc chắn muốn xóa tài khoản này ?");
        alert.setContentText("Tài khoản của bạn sẽ bị xóa và không thể khôi phục được. Bạn có chắc không ?");

        // Hiển thị cửa sổ Alert và chờ phản hồi từ người dùng
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() &&result.get() == ButtonType.OK) {


            SQLController.deleteUser(username);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/LogIn_View.fxml"));
                root = loader.load();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                LogInController LogInController = loader.getController();
                parentStage.close();
                if (this.language.equals("en")) {
                    LogInController.setLanguageToEn();
                } else {
                    LogInController.setLanguageToVi();
                }
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLanguageToEn() {
        language = "en";
    }

    public void setLanguageToVi() {
        language = "vi";
    }

    public Stage parentStage;

    public void setParentStage(Stage stage) {
        parentStage = stage;
    }
}
