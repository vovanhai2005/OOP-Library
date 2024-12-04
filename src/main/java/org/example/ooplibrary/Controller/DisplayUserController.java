package org.example.ooplibrary.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.ooplibrary.Object.Book;
import org.example.ooplibrary.Object.BookLoan;
import org.example.ooplibrary.Object.User;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DisplayUserController implements Initializable {
    @FXML
    private TableColumn<BookLoan, String> IDCol;

    @FXML
    private TableColumn<BookLoan, String> bookNameCol;

    @FXML
    private Label dateofBirth;

    @FXML
    private Label email;

    @FXML
    private TableColumn<BookLoan, Void> dueDateCol;

    @FXML
    private Label fullName;

    @FXML
    private Label gender;

    @FXML
    private TableColumn<BookLoan, String> informationCol;

    @FXML
    private TableColumn<BookLoan, String> returnDateCol;

    @FXML
    private Label phoneNumber;

    @FXML
    private TableView<BookLoan> tableView;

    @FXML
    private Label username;

    @FXML
    private ImageView imageView;

    @FXML
    private Text borrowHistoryTitle;

    @FXML
    private Label dobText;

    @FXML
    private Label emailText;

    @FXML
    private Label fullNameTxt;

    @FXML
    private Label genderText;

    @FXML
    private Label phoneText;

    @FXML
    private Label updateInfoText;


    @FXML
    private Label usernameText;

    private ObservableList<BookLoan> data;

    private UserManagementController userManagementController;

    private String language;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.getColumns().clear();

        IDCol.setCellValueFactory(new PropertyValueFactory<>("bookLoanID"));
        bookNameCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        returnDateCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        informationCol.setCellValueFactory(new PropertyValueFactory<>("note"));


        tableView.getColumns().addAll(IDCol, bookNameCol, dueDateCol, returnDateCol, informationCol);

        data = FXCollections.observableArrayList(

        );

        tableView.setItems(data);
    }

    @FXML
    public void closeDisplayUserView(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void switchToUpdateInfo(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/UserUpdateInfo_View.fxml"));
            Parent root = loader.load();

            UserUpdateInfoController controller = loader.getController();
            controller.setUsername(username.getText()); // Truyền username vào UserUpdateInfoController
            controller.setDisplayUserController(this);
            if (this.language.equals("en")) {
                controller.setLanguageToEn();
            } else {
                controller.setLanguageToVi();
            }
            // Lấy Stage hiện tại
            Stage stage = new Stage();

            // Set scene mới
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTableView() {
        new Thread(() -> {
            ArrayList<BookLoan> temp = SQLController.getBookLoansDataWithUser(username.getText());

            Platform.runLater(() -> {
                data.clear();
                if (temp != null) {
                    data.addAll(temp);
                }
                tableView.setItems(data);
            });
        }).start();

    }

    public void setUserManagementController(UserManagementController userManagementController) {
        this.userManagementController = userManagementController;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth.setText(dateofBirth);
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }

    public void setFullName(String fullName) {
        this.fullName.setText(fullName);
    }

    public void setGender(String gender) {
        this.gender.setText(gender);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.setText(phoneNumber);
    }

    public void setUsername(String username) {
        this.username.setText(username);
    }

    public void setImage(byte[] image) {
        if (image != null) {
            imageView.setImage(new Image(new ByteArrayInputStream(image)));
        } else {
            System.out.println("No image found");
        }
    }

    public void setLanguageToEn() {
        language = "en";
        fullNameTxt.setText("Full Name:");
        usernameText.setText("Username:");
        phoneText.setText("Phone Number:");
        dobText.setText("DOB:");
        genderText.setText("Gender:");
        emailText.setText("Email:");
        updateInfoText.setText("Update Info");
        borrowHistoryTitle.setText("Borrow History");
        IDCol.setText("ID");
        bookNameCol.setText("Book Name");
        dueDateCol.setText("Due Date");
        returnDateCol.setText("Return Date");
        informationCol.setText("Note");
        tableView.setPlaceholder(new Label("No content in table"));

    }

    public void setLanguageToVi() {
        language = "vi";
        fullNameTxt.setText("Họ và tên:");
        usernameText.setText("Tên đăng nhập:");
        phoneText.setText("SDT:");
        dobText.setText("Ngày sinh:");
        genderText.setText("Giới tính:");
        emailText.setText("Email:");
        updateInfoText.setText("Cập nhật thông tin");
        borrowHistoryTitle.setText("Lịch sử mượn sách");
        IDCol.setText("ID");
        bookNameCol.setText("Tên sách");
        dueDateCol.setText("Ngày hết hạn");
        returnDateCol.setText("Ngày trả");
        informationCol.setText("Ghi chú");
        tableView.setPlaceholder(new Label("Không có dữ liệu trong bảng"));
    }
}