package org.example.ooplibrary.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.ooplibrary.Object.Book;
import org.example.ooplibrary.Object.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserDocumentArchiveController extends AbstractMenuController implements Initializable {

    @FXML
    private Label bookListBtn;

    @FXML
    private Label borrowBtn;

    @FXML
    private Label dashboardBtn;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label logOutBtn;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField searchKeyword;

    @FXML
    private Label userListBtn;

    private String username;

    @FXML
    void openDisplayUserWindow(MouseEvent event) {
        try {
            // Tạo FXMLLoader và nạp file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/DisplayUser_View.fxml"));
            Parent root = loader.load();

            User user = SQLController.getUserInfoDataByUsername(username);
            DisplayUserController displayUserController = loader.getController();
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

    @FXML
    void openSettingsWindow(MouseEvent event) {
        try {
            // Tạo FXMLLoader và nạp file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/UserSetting_View.fxml"));
            Parent root = loader.load();

            UserSettingController userSettingController = loader.getController();
            userSettingController.setPassword(SQLController.getUserPassword(username));
            userSettingController.setUsername(username);

            // Tạo cửa sổ mới để hiển thị thông tin chi tiết
            Stage stage = new Stage();
            stage.setTitle("Thông tin chi tiết người dùng");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void performSearch1(MouseEvent event) {
        List<Book> bookList;
        if (searchKeyword.getText().isEmpty()) {
            bookList = SQLController.getBookInfoData(); // Lấy danh sách sách (từ database hoặc bất kỳ nguồn nào)
        } else {
            bookList = SQLController.getBookInfoDataWithKeyword(searchKeyword.getText());
        }
        gridPane.getChildren().clear();
        for (int i = 0; i < bookList.size(); i++) {
            VBox bookBox = createBookBox(bookList.get(i));

            // Tính toán vị trí cột và hàng
            int column = i % 5; // 5 cột
            int row = i / 5;

            // Thêm vào GridPane
            gridPane.add(bookBox, column, row);
        }
    }

    @FXML
    void performSearch2(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            List<Book> bookList;
            if (searchKeyword.getText().isEmpty()) {

                bookList = SQLController.getBookInfoData(); // Lấy danh sách sách (từ database hoặc bất kỳ nguồn nào)
            } else {
                bookList = SQLController.getBookInfoDataWithKeyword(searchKeyword.getText());
            }
            gridPane.getChildren().clear();
            for (int i = 0; i < bookList.size(); i++) {
                VBox bookBox = createBookBox(bookList.get(i));

                // Tính toán vị trí cột và hàng
                int column = i % 6; // 6 cột
                int row = i / 6;

                // Thêm vào GridPane
                gridPane.add(bookBox, column, row);
            }
        }
    }

    @FXML
    void switchToUserDocumentArchiveView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/UserDocumentArchive_View.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            UserDocumentArchiveController userDocumentArchiveController = loader.getController();
            userDocumentArchiveController.setUsername(username);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToUserMainMenuView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/UserMainMenu_View.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            UserMainMenuController userMainMenuController = loader.getController();
            userMainMenuController.setUsername(username);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(searchKeyword.getText());
        List<Book> bookList;
        if (searchKeyword.getText().isEmpty()) {
            System.out.println(searchKeyword.getText() + "  !!!!!");
            bookList = SQLController.getBookInfoData(); // Lấy danh sách sách (từ database hoặc bất kỳ nguồn nào)
        } else {
            bookList = SQLController.getBookInfoDataWithKeyword(searchKeyword.getText());
        }
        for (int i = 0; i < bookList.size(); i++) {
            VBox bookBox = createBookBox(bookList.get(i));

            // Tính toán vị trí cột và hàng
            int column = i % 5; // 5 cột
            int row = i / 5;

            // Thêm vào GridPane
            gridPane.add(bookBox, column, row);
        }
    }

    private VBox createBookBox(Book book) {
        // Tạo ImageView từ byte[]
        ImageView bookImageView = new ImageView();
        if (book.getImage() != null) {
            Image bookImage = new Image(new ByteArrayInputStream(book.getImage()));
            bookImageView.setImage(bookImage);
        }
        bookImageView.setFitWidth(150); // Đặt chiều rộng ảnh
        bookImageView.setFitHeight(200); // Đặt chiều cao ảnh
        bookImageView.setPreserveRatio(true);

        // Tạo Text cho tên sách
        Text bookName = new Text(book.getName());
        bookName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-alignment: center;");

        // Tạo Text cho tên tác giả
        Text authorName = new Text("By: " + book.getAuthor());
        authorName.setStyle("-fx-font-size: 14px; -fx-text-alignment: center;");

        // Tạo VBox để chứa ảnh và thông tin
        VBox bookBox = new VBox(bookImageView, bookName, authorName);
        bookBox.setSpacing(10);
        bookBox.setStyle("-fx-alignment: center; -fx-padding: 10px; -fx-background-color: #f9f9f9;");

        // Thêm sự kiện click để hiển thị thông tin chi tiết
        bookBox.setOnMouseClicked(event -> openBookDetailView(book));

        return bookBox;
    }

    @FXML
    private void openBookDetailView(Book book) {
        System.out.println("Opening book detail view: " + book.getName() + "(in progress)");
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSearchKeyword(String text) {
        searchKeyword.setText(text);
    }
}
