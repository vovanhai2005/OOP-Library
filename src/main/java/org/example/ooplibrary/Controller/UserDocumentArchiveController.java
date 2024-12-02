package org.example.ooplibrary.Controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import org.example.ooplibrary.Object.Book;
import org.example.ooplibrary.Object.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserDocumentArchiveController extends AbstractMenuController implements Initializable, AbstractLanguageConfig {

    @FXML
    private Label bookListBtn;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label logOutBtn;

    @FXML
    private FlowPane flowPane;

    @FXML
    private TextField searchKeyword;


    private String username;

    @FXML
    private ComboBox<String> categoriesBox;

    @FXML
    private ComboBox<String> orderBox;



    @FXML
    private Text bookListTitle;

    @FXML
    private Label languageText;

    @FXML
    private Label mainMenuBtn;

    @FXML
    private ScrollPane scrollPane;


    @FXML
    private Label settingsBtn;

    @FXML
    private Label userInfoBtn;

    @FXML
    private Label sortByText;

    @FXML
    private Label orderText;

    @FXML
    void openDisplayUserWindow(MouseEvent event) {
        try {
            // Tạo FXMLLoader và nạp file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/DisplayUser_View.fxml"));
            Parent root = loader.load();

            User user = SQLController.getUserInfoDataByUsername(username);
            DisplayUserController displayUserController = loader.getController();
            displayUserController.setEmail(user.getEmail());
            displayUserController.setFullName(user.getFullName());
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
        String keyword = searchKeyword.getText();
        Task<ArrayList<Book>> searchTask;

        // Tạo Task để tìm kiếm sách dựa vào từ khóa
        if (keyword.isEmpty()) {
            searchTask = SQLController.getBookInfoData(); // Lấy tất cả sách
        } else {
            searchTask = SQLController.getBookInfoDataWithKeyword(keyword); // Tìm kiếm theo từ khóa
        }

        searchTask.setOnSucceeded(e -> {
            List<Book> bookList = searchTask.getValue(); // Lấy danh sách sách tìm thấy
            flowPane.getChildren().clear();

            // Tính toán kích thước cho từng bookBox
            for (int i = 0; i < bookList.size(); i++) {
                int flowPaneWidth = 1072; // Width of the FlowPane
                int spacing = 10;        // Spacing between elements in the FlowPane
                int boxWidth = (flowPaneWidth - (5 - 1) * spacing) / 5; // Tính kích thước cho mỗi bookBox
                VBox bookBox = createBookBox(bookList.get(i), boxWidth);
                flowPane.getChildren().add(bookBox); // Thêm bookBox vào FlowPane
            }
        });
        new Thread(searchTask).start();
    }
    @FXML
    void performSearch2(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            String keyword = searchKeyword.getText();
            Task<ArrayList<Book>> searchTask;

            // Tạo Task để tìm kiếm sách dựa vào từ khóa
            if (keyword.isEmpty()) {
                searchTask = SQLController.getBookInfoData(); // Lấy tất cả sách
            } else {
                searchTask = SQLController.getBookInfoDataWithKeyword(keyword); // Tìm kiếm theo từ khóa
            }

            searchTask.setOnSucceeded(e -> {
                List<Book> bookList = searchTask.getValue(); // Lấy danh sách sách tìm thấy
                flowPane.getChildren().clear(); // Xóa các phần tử cũ trong FlowPane

                // Tính toán kích thước cho từng bookBox
                for (int i = 0; i < bookList.size(); i++) {
                    int flowPaneWidth = 1072; // Width of the FlowPane
                    int spacing = 10;        // Spacing between elements in the FlowPane
                    int boxWidth = (flowPaneWidth - (5 - 1) * spacing) / 5; // Tính kích thước cho mỗi bookBox
                    VBox bookBox = createBookBox(bookList.get(i), boxWidth);
                    flowPane.getChildren().add(bookBox); // Thêm bookBox vào FlowPane
                }
            });

            new Thread(searchTask).start();
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
            if (this.language.equals("en")) {
                userDocumentArchiveController.setLanguageToEn();
            } else {
                userDocumentArchiveController.setLanguageToVi();
            }
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
            if (this.language.equals("en")) {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String keyword = searchKeyword.getText(); // Lấy từ khóa tìm kiếm
        Task<ArrayList<Book>> loadBooksTask;

        // Tạo Task để lấy sách dựa vào từ khóa
        if (keyword.isEmpty()) {
            loadBooksTask = SQLController.getBookInfoData(); // Lấy tất cả sách
        } else {
            loadBooksTask = SQLController.getBookInfoDataWithKeyword(keyword); // Tìm kiếm theo từ khóa
        }

        loadBooksTask.setOnSucceeded(event -> {
            List<Book> bookList = loadBooksTask.getValue(); // Lấy danh sách sách tìm thấy

            // Calculate layout properties
            int flowPaneWidth = 1072; // Width of the FlowPane
            int spacing = 10;        // Spacing between elements in the FlowPane
            int boxWidth = (flowPaneWidth - (5 - 1) * spacing) / 5; // Number of boxes per row is 6
            int numBoxesPerRow = (flowPaneWidth + spacing) / (boxWidth + spacing);

            // Set horizontal and vertical gaps
            flowPane.setHgap(0); // Disable FlowPane's horizontal gap
            flowPane.setVgap(10); // Keep vertical gap

            flowPane.getChildren().clear(); // Clear previous items in the FlowPane

            for (int i = 0; i < bookList.size(); i++) {
                VBox bookBox = createBookBox(bookList.get(i), boxWidth);

                // Add spacing only for non-last items in a row
                if ((i + 1) % numBoxesPerRow != 0) {
                    FlowPane.setMargin(bookBox, new Insets(0, spacing, 10, 0)); // Add right margin
                } else {
                    FlowPane.setMargin(bookBox, new Insets(0, 0, 10, 0)); // No right margin
                }

                flowPane.getChildren().add(bookBox);
            }
        });

        loadBooksTask.setOnFailed(event -> {
            System.err.println("Error loading books: " + loadBooksTask.getException().getMessage());
        });

        // Chạy Task trên một luồng nền
        new Thread(loadBooksTask).start();
    }

    private VBox createBookBox(Book book, int boxWidth) {
        // Tạo ImageView từ byte[]
        ImageView bookImageView = new ImageView();
        if (book.getImage() != null) {
            Image bookImage = new Image(new ByteArrayInputStream(book.getImage()));
            bookImageView.setImage(bookImage);
        }
        bookImageView.setFitWidth(150); // Đặt chiều rộng ảnh
        bookImageView.setFitHeight(200); // Đặt chiều cao ảnh
        bookImageView.setPreserveRatio(false);

        // Xử lý tên sách nếu vượt quá 15 kí tự
        String displayedName = book.getName();
        if (displayedName.length() > 15) {
            displayedName = displayedName.substring(0, 15) + "...";
        }

        // Tạo Text cho tên sách
        Text bookName = new Text(displayedName);
        bookName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-alignment: center;");

        // Tạo Text cho tên tác giả
        String author = book.getAuthor();
        if (author.length() > 15) {
            author = author.substring(0, 15) + "...";
        }
        Text authorName = new Text("By: " + author);
        authorName.setStyle("-fx-font-size: 14px; -fx-text-alignment: center;");

        // Thêm Rating
        Rating rating = new Rating();
        rating.setMax(5); // Maximum 5 stars
        rating.setPartialRating(true); // Allow partial stars (optional)
        rating.setRating(SQLController.getUserRatings(book.getISBN())); // Set the actual rating
        rating.setStyle("-fx-scale-x: 0.6; -fx-scale-y: 0.6;"); // Optional: Adjust size
        rating.setDisable(true); // User can only see actual ratings

        // Add a listener if you want to handle rating changes (optional)
        rating.ratingProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("New Rating for " + book.getName() + ": " + newValue);
        });

        // Tạo HBox bao bọc Rating và căn chỉnh vào giữa
        HBox ratingContainer = new HBox(rating);
        ratingContainer.setStyle("-fx-alignment: center;");
        ratingContainer.setPrefWidth(rating.prefWidth(-1)); // Đặt kích thước bằng Rating
        ratingContainer.setPrefHeight(rating.prefHeight(-1));

        // Tạo VBox để chứa ảnh, thông tin và Rating
        VBox bookBox = new VBox(bookImageView, bookName, authorName, ratingContainer);
        bookBox.setSpacing(10);
        bookBox.setStyle("-fx-alignment: center; -fx-padding: 10px; -fx-background-color: #F0E9DD;");
        bookBox.setPrefWidth(boxWidth); // Fixed width for each bookBox

        // Thêm sự kiện click để hiển thị thông tin chi tiết
        bookBox.setOnMouseClicked(event -> openBookDetailView(book));

        return bookBox;
    }

    @FXML
    private void openBookDetailView(Book book) {
        System.out.println("Opening book detail view: " + book.getName() + "(in progress)");
        try {
            // Tải FXML của giao diện hiển thị tài liệu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/UserDisplayDocument_View.fxml"));
            Parent root = loader.load();

            // Lấy controller của cửa sổ hiển thị tài liệu
            UserDisplayDocumentController userDisplayDocumentController = loader.getController();

            // Gán dữ liệu tài liệu vào controller
            userDisplayDocumentController.setDetails(book,username);
            userDisplayDocumentController.setReviewsPane();
            if (this.language.equals("en")) {
                userDisplayDocumentController.setLanguageToEn();
            } else {
                userDisplayDocumentController.setLanguageToVi();
            }

            // Tạo cửa sổ mới để hiển thị thông tin chi tiết
            Stage stage = new Stage();
            if (this.language.equals("en")) {
                stage.setTitle("Document details");
            } else {
                stage.setTitle("Thông tin chi tiết tài liệu");
            }
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSearchKeyword(String text) {
        searchKeyword.setText(text);
    }

    @FXML
    public void setLanguageToEn() {
        language = "en";
        languageText.setText("Language:");
        mainMenuBtn.setText("Main Menu");
        bookListBtn.setText("Books List");
        userInfoBtn.setText("User Info");
        settingsBtn.setText("Settings");
        logOutBtn.setText("Log Out");
        bookListTitle.setText("Book List");
        sortByText.setText("Sort by:");
        orderText.setText("Order:");

    }

    @FXML
    public void setLanguageToVi() {
        language = "vi";
        languageText.setText("Ngôn ngữ:");
        mainMenuBtn.setText("Trang chủ");
        bookListBtn.setText("DS sách");
        userInfoBtn.setText("TT người dùng");
        settingsBtn.setText("Cài đặt");
        logOutBtn.setText("Đăng xuất");
        bookListTitle.setText("Danh sách tài liệu");
        sortByText.setText("SXếp theo:");
        orderText.setText("Thứ tự:");
    }
}
