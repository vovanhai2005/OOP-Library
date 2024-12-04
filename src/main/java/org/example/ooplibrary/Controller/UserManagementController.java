package org.example.ooplibrary.Controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Parent;
import org.example.ooplibrary.Object.Book;
import org.example.ooplibrary.Object.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserManagementController extends AbstractMenuController implements Initializable, AbstractLanguageConfig {

    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, String> usernameCol;

    @FXML
    private TableColumn<User, String> nameCol;

    @FXML
    private TableColumn<User, String> genderCol;

    @FXML
    private TableColumn<User, String> dobCol;

    @FXML
    private TableColumn<User, String> emailCol;

    @FXML
    private TableColumn<User, Void> featureCol;

    @FXML
    private TextField searchKeyword;

    @FXML
    private Label booksListBtn;

    @FXML
    private Label borrowBtn;

    @FXML
    private Label dashboardBtn;

    @FXML
    private Label languageText;

    @FXML
    private Label logOutBtn;

    @FXML
    private Label returnBtn;

    @FXML
    private Label userListBtn;

    @FXML
    private Text userListTitle;

    private ObservableList<User> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.getColumns().clear();

        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dob"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));


        // Cấu hình cột featureCol với các nút tuỳ chỉnh
        addFeatureButtonsToTable("en");


        tableView.getColumns().addAll(usernameCol, nameCol, genderCol, dobCol, emailCol, featureCol);

        data = FXCollections.observableArrayList(

        );

        new Thread(() -> {
            ArrayList<User> temp = SQLController.getUserInfoData();

            if (temp != null) {
                for (User user : temp) {
                    data.add(user);
                }
            }

            Platform.runLater(() -> tableView.setItems(data));
        }).start();
    }

    @FXML
    void performSearch1(MouseEvent event) {
        data.clear();
        ArrayList<User> temp = SQLController.getUserInfoDataWithKeyword(searchKeyword.getText());

        if (temp != null) {
            for (User user : temp) {
                data.add(user);
            }
        }

        tableView.setItems(data);
    }

    @FXML
    void performSearch2(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            data.clear();
            ArrayList<User> temp = SQLController.getUserInfoDataWithKeyword(searchKeyword.getText());

            if (temp != null) {
                for (User user : temp) {
                    data.add(user);
                }
            }

            tableView.setItems(data);
        }
    }

    private void addFeatureButtonsToTable(String language) {
        final String viewText = language.equals("en") ? "View" : "Xem";
        final String deleteText = language.equals("en") ? "Delete" : "Xóa";

        featureCol.setCellFactory(param -> new TableCell<User, Void>() {

            private final Button viewButton = new Button(viewText);
            private final Button deleteButton = new Button(deleteText);

            {
                // Xử lý sự kiện khi nhấn vào nút "Xem"
                viewButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    viewUserDetails(user);
                });

                // Xử lý sự kiện khi nhấn vào nút "Xóa"
                deleteButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    deleteUser(user);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonsBox = new HBox(5, viewButton, deleteButton);
                    setGraphic(buttonsBox);
                }
            }
        });
    }

    private void viewUserDetails(User user) {
        System.out.println("Xem thông tin của người dùng ");
        try {
            // Tạo FXMLLoader và nạp file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/DisplayUser_View.fxml"));
            Parent root = loader.load();


            DisplayUserController displayUserController = loader.getController();
            displayUserController.setUserManagementController(this);
            displayUserController.setEmail(user.getEmail());
            displayUserController.setFullName(user.getFullName());
            displayUserController.setDateofBirth(user.getDob());
            displayUserController.setGender(user.getGender());
            displayUserController.setPhoneNumber(user.getPhoneNumber());
            displayUserController.setUsername(user.getUsername());
            displayUserController.setImage(user.getImage());
            displayUserController.setTableView();
            if (this.language.equals("en")) {
                displayUserController.setLanguageToEn();
            } else {
                displayUserController.setLanguageToVi();
            }


            // Tạo cửa sổ mới để hiển thị thông tin chi tiết
            Stage stage = new Stage();
            stage.setTitle("Thông tin chi tiết người dùng");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editUserInfo(User user) {
        System.out.println("Chỉnh sửa thông tin của người dùng ");
    }

    private void deleteUser(User user) {
        // Tạo Alert kiểu xác nhận
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (language.equals("en")) {
            alert.setTitle("Confirm deletion");
            alert.setHeaderText("Are you sure you want to delete this user?");
            alert.setContentText("Username: " + user.getUsername() + "\n"
                    + "Full name: " + user.getFullName() + "\n\n"
                    + "Note: This user will be deleted from the database and cannot be recovered!");
        } else {
            alert.setTitle("Xác nhận xóa");
            alert.setHeaderText("Bạn có chắc chắn muốn xóa người dùng này?");
            alert.setContentText("Tên đăng nhập: " + user.getUsername() + "\n"
                    + "Họ và tên: " + user.getFullName() + "\n\n"
                    + "Lưu ý: Người dùng này sẽ bị xóa khỏi cơ sở dữ liệu và không thể khôi phục!");
        }

        // Hiển thị cửa sổ Alert và chờ phản hồi từ người dùng
        Optional<ButtonType> result = alert.showAndWait();

        // Kiểm tra phản hồi
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Người dùng chọn OK
            if (SQLController.getUserInfoDataByUsername(user.getUsername()) == null) {
                // Người dùng không tồn tại
                Alert notFoundAlert = new Alert(Alert.AlertType.WARNING);
                if (language.equals("en")) {
                    notFoundAlert.setTitle("User not found");
                    notFoundAlert.setHeaderText(null);
                    notFoundAlert.setContentText("User \"" + user.getUsername() + "\" does not exist in the database.");
                } else {
                    notFoundAlert.setTitle("Người dùng không tồn tại");
                    notFoundAlert.setHeaderText(null);
                    notFoundAlert.setContentText("Người dùng \"" + user.getUsername() + "\" không tồn tại trong cơ sở dữ liệu.");
                }
                return;
            }

            // Xóa người dùng khỏi cơ sở dữ liệu
            SQLController.deleteUser(user.getUsername());
            // Xóa thành công: Cập nhật danh sách
            data.remove(user);

            // Hiển thị thông báo thành công
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            if (language.equals("en")) {
                successAlert.setTitle("Deletion successful");
                successAlert.setHeaderText(null);
                successAlert.setContentText("User \"" + user.getUsername() + "\" has been deleted successfully!");
            } else {
                successAlert.setTitle("Xóa thành công");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Người dùng \"" + user.getUsername() + "\" đã được xóa thành công!");
            }

            successAlert.show();

        } else {
            // Người dùng chọn Cancel hoặc đóng cửa sổ Alert
            System.out.println("Hủy xóa người dùng: " + user.getUsername());
        }
    }

    @FXML
    public void setLanguageToEn() {
        addFeatureButtonsToTable("en");
        language = "en";
        languageText.setText("Language:");
        dashboardBtn.setText("Dashboard");
        booksListBtn.setText("Books List");
        userListBtn.setText("User List");
        borrowBtn.setText("Borrow");
        returnBtn.setText("Return");
        logOutBtn.setText("Log out");
        userListTitle.setText("User List");
        usernameCol.setText("Username");
        nameCol.setText("Name");
        genderCol.setText("Gender");
        dobCol.setText("Date of Birth");
        emailCol.setText("Email");
        featureCol.setText("Feature");
        tableView.setPlaceholder(new Label("No content in table"));
    }

    @FXML
    public void setLanguageToVi() {
        addFeatureButtonsToTable("vi");
        language = "vi";
        languageText.setText("Ngôn ngữ:");
        dashboardBtn.setText("Bảng thông tin");
        booksListBtn.setText("DS sách");
        userListBtn.setText("DS người dùng");
        borrowBtn.setText("Mượn sách");
        returnBtn.setText("Trả sách");
        logOutBtn.setText("Đăng xuất");
        userListTitle.setText("Danh sách người dùng");
        usernameCol.setText("Tên đăng nhập");
        nameCol.setText("Họ và tên");
        genderCol.setText("Giới tính");
        dobCol.setText("Ngày sinh");
        emailCol.setText("Email");
        featureCol.setText("Chức năng");
        tableView.setPlaceholder(new Label("Không có dữ liệu trong bảng"));

    }


}
