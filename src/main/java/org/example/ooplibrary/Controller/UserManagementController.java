package org.example.ooplibrary.Controller;

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
import javafx.stage.Stage;
import javafx.scene.Parent;
import org.example.ooplibrary.Object.Book;
import org.example.ooplibrary.Object.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserManagementController extends AbstractMenuController implements Initializable {

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

    private ObservableList<User> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.getColumns().clear();

        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fulLName"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dob"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));


        // Cấu hình cột featureCol với các nút tuỳ chỉnh
        addFeatureButtonsToTable();


        tableView.getColumns().addAll(usernameCol, nameCol, genderCol, dobCol, emailCol, featureCol);

        data = FXCollections.observableArrayList(

        );

        ArrayList<User> temp = SQLController.getUserInfoData();

        if (temp != null) {
            for (User user : temp) {
                data.add(user);
            }
        }

        tableView.setItems(data);
    }

    @FXML
    void openAddUserWindow(MouseEvent event) {

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

    private void addFeatureButtonsToTable() {
        featureCol.setCellFactory(param -> new TableCell<User, Void>() {
            private final Button viewButton = new Button("Xem");
            private final Button editButton = new Button("Chỉnh sửa");
            private final Button deleteButton = new Button("Xóa");

            {
                // Xử lý sự kiện khi nhấn vào nút "Xem"
                viewButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    viewUserDetails(user);
                });

                // Xử lý sự kiện khi nhấn vào nút "Chỉnh sửa"
                editButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    editUserInfo(user);
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
                    HBox buttonsBox = new HBox(5, viewButton, editButton, deleteButton);
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
            displayUserController.setFullName(user.getFulLName());
            displayUserController.setDateofBirth(user.getDob());
            displayUserController.setGender(user.getGender());
            displayUserController.setPhoneNumber(user.getPhoneNumber());
            displayUserController.setUsername(user.getUsername());
            displayUserController.setImage(user.getImage());
            displayUserController.setTableView();


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
        System.out.println("Xóa người dùng ");
    }


}
