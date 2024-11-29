package org.example.ooplibrary.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
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
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import org.example.ooplibrary.Object.Book;
import org.example.ooplibrary.Object.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserMainMenuController extends AbstractMenuController implements Initializable {
    @FXML
    private Label bookListBtn;

    @FXML
    private Label borrowBtn;

    @FXML
    private Label dashboardBtn;

    @FXML
    private Label logOutBtn;

    @FXML
    private TextField searchKeyword;

    @FXML
    private Label userListBtn;

    @FXML
    private FlowPane flowPane;

    private String username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        flowPane.setVgap(10);  // Khoảng cách dọc giữa các hàng

        // 2. Phần đầu tiên: Hiển thị số liệu
        HBox dataBox = new HBox();
        dataBox.setSpacing(20);  // Khoảng cách giữa các ô
        dataBox.setPrefWidth(1074);
        dataBox.setStyle("-fx-border-width: 0; -fx-border-color: transparent;"); // Loại bỏ viền

        // Tạo các ô hiển thị số sách, số người dùng và số lượt mượn trả sách
        HBox booksCountBox = new HBox();
        booksCountBox.setPrefWidth(358);  // Chiều ngang bằng nhau
        booksCountBox.setPrefHeight(150); // Chiều dọc cố định
        booksCountBox.setAlignment(Pos.CENTER); // Căn lề giữa
        Text booksCountText = new Text("Books: " + getBooksCount());
        booksCountText.setStyle("-fx-font-size: 30px;");  // Phóng to chữ lên 30px
        booksCountText.setTextAlignment(TextAlignment.CENTER);
        booksCountBox.getChildren().add(booksCountText);

        HBox usersCountBox = new HBox();
        usersCountBox.setPrefWidth(358);  // Chiều ngang bằng nhau
        usersCountBox.setPrefHeight(150); // Chiều dọc cố định
        usersCountBox.setAlignment(Pos.CENTER); // Căn lề giữa
        Text usersCountText = new Text("Users: " + getUsersCount());
        usersCountText.setStyle("-fx-font-size: 30px;");  // Phóng to chữ lên 30px
        usersCountText.setTextAlignment(TextAlignment.CENTER);
        usersCountBox.getChildren().add(usersCountText);

        HBox borrowCountBox = new HBox();
        borrowCountBox.setPrefWidth(358);  // Chiều ngang bằng nhau
        borrowCountBox.setPrefHeight(150); // Chiều dọc cố định
        borrowCountBox.setAlignment(Pos.CENTER); // Căn lề giữa
        Text borrowCountText = new Text("Borrow/Return: " + getBorrowReturnCount());
        borrowCountText.setStyle("-fx-font-size: 30px;");  // Phóng to chữ lên 30px
        borrowCountText.setTextAlignment(TextAlignment.CENTER);
        borrowCountBox.getChildren().add(borrowCountText);

        // Thêm các ô vào dataBox
        dataBox.getChildren().addAll(booksCountBox, usersCountBox, borrowCountBox);

        // 3. Phần thứ hai: Suggested for you
        VBox suggestedForYouBox = new VBox();
        Text suggestedText = new Text("Suggested for you");
        suggestedText.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");

        FlowPane subFlowPaneSuggested = new FlowPane(Orientation.VERTICAL);
        subFlowPaneSuggested.setHgap(10);  // Khoảng cách ngang giữa các vBox
        subFlowPaneSuggested.setVgap(10);
        subFlowPaneSuggested.setPrefWidth(1074);
        subFlowPaneSuggested.setPrefWrapLength(290);
        subFlowPaneSuggested.setStyle("-fx-border-width: 0; -fx-border-color: transparent;");

        // Trích xuất sách từ SQLController.getBookInfoData() và tạo VBox cho mỗi sách
        ArrayList<Book> suggestedBooks = SQLController.getBookInfoData();
        for (Book book : suggestedBooks) {
            VBox bookBox = createBookBox2(book);
            subFlowPaneSuggested.getChildren().add(bookBox);
        }

        // Đặt subFlowPaneSuggested vào một ScrollPane để cho phép cuộn ngang
        ScrollPane subScrollPaneSuggested = new ScrollPane(subFlowPaneSuggested);
        subScrollPaneSuggested.setFitToHeight(true);
        subScrollPaneSuggested.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);  // Hiển thị thanh cuộn ngang
        subScrollPaneSuggested.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // Ẩn thanh cuộn dọc
        subScrollPaneSuggested.setPrefHeight(290);  // Chiều cao cố định bằng chiều cao của VBox
        subScrollPaneSuggested.setStyle("-fx-border-width: 0; -fx-border-color: transparent;");

        suggestedForYouBox.getChildren().addAll(suggestedText, subScrollPaneSuggested);

        // 4. Phần thứ ba: Latest released books
        VBox latestBooksBox = new VBox();
        Text latestBooksText = new Text("Latest released books");
        latestBooksText.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");

        FlowPane subFlowPaneLatest = new FlowPane(Orientation.VERTICAL);
        subFlowPaneLatest.setHgap(10);  // Khoảng cách ngang giữa các vBox
        subFlowPaneLatest.setVgap(10);
        subFlowPaneLatest.setPrefWidth(1074);
        subFlowPaneLatest.setPrefWrapLength(290);
        subFlowPaneLatest.setStyle("-fx-border-width: 0; -fx-border-color: transparent;");  // Ngăn chặn việc xuống dòng

        // Trích xuất sách từ SQLController.getBookInfoData() và tạo VBox cho mỗi sách
        ArrayList<Book> latestBooks = SQLController.getBookInfoData(); // Lấy sách mới nhất
        for (Book book : latestBooks) {
            VBox bookBox = createBookBox2(book);
            subFlowPaneLatest.getChildren().add(bookBox);
        }

        // Đặt subFlowPaneLatest vào một ScrollPane để cho phép cuộn ngang
        ScrollPane subScrollPaneLatest = new ScrollPane(subFlowPaneLatest);
        subScrollPaneLatest.setFitToHeight(true);
        subScrollPaneLatest.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);  // Hiển thị thanh cuộn ngang
        subScrollPaneLatest.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // Ẩn thanh cuộn dọc
        subScrollPaneLatest.setPrefHeight(290);  // Chiều cao cố định bằng chiều cao của VBox
        subScrollPaneLatest.setStyle("-fx-border-width: 0; -fx-border-color: transparent;");

        latestBooksBox.getChildren().addAll(latestBooksText, subScrollPaneLatest);

        // 5. Thêm tất cả các phần vào flowPane
        flowPane.getChildren().addAll(dataBox, suggestedForYouBox, latestBooksBox);
    }





    // Các phương thức giả định để lấy thông tin
    private int getBooksCount() {
        return 1000;  // Ví dụ, bạn có thể thay thế bằng dữ liệu thực từ cơ sở dữ liệu
    }

    private int getUsersCount() {
        return 250;  // Ví dụ, bạn có thể thay thế bằng dữ liệu thực từ cơ sở dữ liệu
    }

    private int getBorrowReturnCount() {
        return 1500;  // Ví dụ, bạn có thể thay thế bằng dữ liệu thực từ cơ sở dữ liệu
    }


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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/UserDocumentArchive_View.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            UserDocumentArchiveController userDocumentArchiveController = loader.getController();
            userDocumentArchiveController.setUsername(username);
            userDocumentArchiveController.setSearchKeyword(searchKeyword.getText());
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void performSearch2(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/UserDocumentArchive_View.fxml"));
                root = loader.load();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                UserDocumentArchiveController userDocumentArchiveController = loader.getController();
                userDocumentArchiveController.setUsername(username);
                userDocumentArchiveController.setSearchKeyword(searchKeyword.getText());
                scene = new Scene(root);
                userDocumentArchiveController.performSearch2(event);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
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


    private VBox createBookBox2(Book book) {
        // Chiều rộng và chiều cao của VBox
        final int boxWidth = 120;


        // Tạo ImageView từ byte[]
        ImageView bookImageView = new ImageView();
        if (book.getImage() != null) {
            Image bookImage = new Image(new ByteArrayInputStream(book.getImage()));
            bookImageView.setImage(bookImage);
        }
        bookImageView.setFitWidth(boxWidth);
        bookImageView.setFitHeight(boxWidth * 4 / 3);  // Tỉ lệ 3:4
        bookImageView.setPreserveRatio(false);

        // Tạo Text cho tên sách, cho phép xuống dòng
        String displayedName = book.getName();
        if (displayedName.length() > 50) {
            displayedName = displayedName.substring(0, 50) + "...";
        }
        Text bookName = new Text(displayedName);
        bookName.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
        bookName.setWrappingWidth(boxWidth);
        bookName.setTextAlignment(TextAlignment.CENTER);

        // Tạo HBox cho tên sách và căn lề trái
        HBox bookNameBox = new HBox(bookName);
        bookNameBox.setAlignment(Pos.TOP_CENTER);  // Căn lề trái cho HBox tên sách
        bookNameBox.setPrefHeight(40);  // Chiều cao cố định cho HBox tên sách



        // Tạo Text cho tên tác giả, cho phép xuống dòng
        String displayedAuthor = book.getAuthor();
        if (displayedAuthor.length() > 25) {
            displayedAuthor = displayedAuthor.substring(0, 25) + "...";
        }
        Text authorName = new Text(displayedAuthor);
        authorName.setStyle("-fx-font-size: 8px;");
        authorName.setWrappingWidth(boxWidth);
        authorName.setTextAlignment(TextAlignment.CENTER);

        // Tạo HBox cho tên tác giả và căn lề trái
        HBox authorNameBox = new HBox(authorName);
        authorNameBox.setAlignment(Pos.TOP_CENTER);  // Căn lề trái cho HBox tên tác giả
        authorNameBox.setPrefHeight(10);  // Chiều cao cố định cho HBox tên tác giả



        // Tạo HBox chứa thành phần rating
        Rating rating = new Rating();
        rating.setMax(5);
        rating.setPartialRating(true);
        rating.setRating(4.0);
        rating.setScaleX(0.5);
        rating.setScaleY(0.5);
        rating.setDisable(true);  // Chặn người dùng thay đổi rating

        HBox ratingBox = new HBox(rating);
        ratingBox.setAlignment(Pos.CENTER_LEFT);
        ratingBox.setPrefWidth(boxWidth);  // Chiều rộng của HBox bằng với chiều rộng của VBox


        // Thêm khoảng trống linh hoạt để đồng bộ khoảng cách giữa các thành phần
        Region spacerTop = new Region();
        spacerTop.setMinHeight(5);  // Tùy chỉnh khoảng trống phía trên


        // Tạo VBox chứa tất cả thành phần và thêm viền
        VBox bookBox = new VBox(spacerTop, bookImageView, bookNameBox, authorNameBox, ratingBox);
        bookBox.setPrefWidth(boxWidth);
        bookBox.setAlignment(Pos.CENTER);
        bookBox.setSpacing(5);
        bookBox.setStyle("-fx-background-color: transparent;"); // Thêm viền cho bookBox


        return bookBox;
    }








    public void setUsername(String text) {
        this.username = text;
    }
}
