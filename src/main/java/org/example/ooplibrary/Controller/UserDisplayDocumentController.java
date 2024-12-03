package org.example.ooplibrary.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
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
import java.util.ArrayList;
import java.util.concurrent.Flow;

public class UserDisplayDocumentController implements Initializable {

    @FXML
    private Text ISBN;

    @FXML
    private Text author;

    @FXML
    private ImageView bookImage;

    @FXML
    private Text bookName;

    @FXML
    private Text description;

    @FXML
    private Text genre;

    @FXML
    private Text yearOfPublication;

    @FXML
    private Rating star_ranking;

    @FXML
    private ScrollPane reviewsScrollPane;

    @FXML
    private FlowPane reviewsFlowPane;

    @FXML
    private TextField commentBox;

    @FXML
    private ImageView sendBtn;

    @FXML
    private Text authorText;


    @FXML
    private Button borrowBtn;


    @FXML
    private Text datePublishedText;


    @FXML
    private Text genreText;

    @FXML
    private Text rateThisBookText;

    @FXML
    private Text reviewTitle;

    private boolean isBorrowed = false;


    private String username = "bo";

    private String language;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {


    }

    public void disableReview() {
        commentBox.setDisable(true);
        star_ranking.setDisable(true);
        sendBtn.setOnMouseClicked(null);
    }

    public void setReviewsPane() {

        // Xóa toàn bộ nội dung cũ trong reviewsFlowPane (nếu có)
        if (reviewsFlowPane.getChildren() == null ) {
            System.out.println("No reviews found");
            return;
        }

        reviewsFlowPane.getChildren().clear();

        if (reviewsFlowPane.getChildren() == null ) {
            System.out.println("No reviews found");
            return;
        }

        // Thiết lập hướng dọc cho FlowPane
        reviewsFlowPane.setOrientation(Orientation.HORIZONTAL);
        reviewsFlowPane.setVgap(10); // Khoảng cách dọc giữa các phần tử
        reviewsFlowPane.setPrefWrapLength(718); // Chiều rộng cố định

        // Lấy danh sách tên người dùng dựa trên ISBN
        ArrayList<String> usernames = SQLController.getRecentlyUsernameFromUserRatingsByISBN(ISBN.getText());

        // Thêm từng HBox vào FlowPane
        for (String username : usernames) {
            HBox reviewPane = createReviewPane(ISBN.getText(), username);
            if (reviewPane !=null) {
                reviewsFlowPane.getChildren().add(reviewPane);
            }
        }


    }

    public void setDetails(Book book,String username) {
        ISBN.setText(book.getISBN());
        author.setText(book.getAuthor());
        bookName.setText(book.getName());
        description.setText(book.getDescription());
        genre.setText(book.getGenresString());
        yearOfPublication.setText(book.getYearOfPublication());
        this.username = username;

        // Nếu có hình ảnh, hiển thị trong bookImage
        if (book.getImage() != null) {
            Image image = new Image(new ByteArrayInputStream(book.getImage()));
            bookImage.setImage(image);
        } else {
            System.out.println("No image found");
        }
        if (SQLController.userReviewed(ISBN.getText(), username)) {
            disableReview();
        }
        isBorrowed = SQLController.isUserBorrowedBook(username, ISBN.getText());

        setReviewsPane();
    }

    @FXML
    void handleBorrowReturn(MouseEvent event) {
        if (!isBorrowed) {

            handleAddBook(event);
        } else {
            handleReturnBook(event);
        }
    }

    @FXML
    void handleAddBook(MouseEvent event) {
        System.out.println("Add book to cart (in progress)");
        try {
            // Tạo FXMLLoader và nạp file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/AddBorrowBook_View.fxml"));
            Parent secondRoot = loader.load();

            // Lấy controller từ loader và thiết lập documentArchiveController
            AddBorrowBookController addBorrowBookController = loader.getController();
            addBorrowBookController.setUsername(username);
            addBorrowBookController.autofillByUsername(null);
            addBorrowBookController.setISBN(ISBN.getText());
            addBorrowBookController.autofillByISBN(null);
            addBorrowBookController.setUserDisplayDocumentController(this);


            // Thiết lập cửa sổ và hiển thị
            Stage secondStage = new Stage();
            Scene secondScene = new Scene(secondRoot);
            secondStage.setScene(secondScene);
            secondStage.setTitle("Thêm tài liệu");
            secondStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleReturnBook(MouseEvent event) {
        try {
            // Tạo FXMLLoader và nạp file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/AddReturnRequest_View.fxml"));
            Parent secondRoot = loader.load();

            // Lấy controller từ loader và thiết lập documentArchiveController
            AddReturnRequestController addReturnRequestController = loader.getController();
            addReturnRequestController.setUsername(username);
            addReturnRequestController.autofill(null);
            addReturnRequestController.setUserDisplayDocumentController(this);
            if (language.equals("vi")) {
                addReturnRequestController.setLanguageToVi();
            } else {
                addReturnRequestController.setLanguageToEn();
            }


            // Thiết lập cửa sổ và hiển thị
            Stage secondStage = new Stage();
            Scene secondScene = new Scene(secondRoot);
            secondStage.setScene(secondScene);
            secondStage.setTitle("Thêm tài liệu");
            secondStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HBox createReviewPane(String ISBN, String username) {

        User user = SQLController.getUserInfoDataByUsername(username);
        if (user == null) {
            System.out.println("User " + username + " not found");
            return null;
        }
        System.out.println(user.getFullName());

        // Tạo HBox chính để chứa avatar và nội dung nhận xét
        HBox reviewBox = new HBox();
        reviewBox.setSpacing(10); // Khoảng cách giữa các phần
        reviewBox.setPrefWidth(718); // Chiều rộng cố định
        reviewBox.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: black; -fx-padding: 5;");


        // Phần thứ nhất: VBox chứa avatar
        VBox avatarBox = new VBox();
        avatarBox.setPrefWidth(40); // Chiều rộng cố định cho avatar
        avatarBox.setAlignment(Pos.TOP_CENTER); // Căn giữa avatar


        ImageView avatarImage = new ImageView();
        avatarImage.setFitWidth(35);
        avatarImage.setFitHeight(35);
        if (user.getImage() != null) {
            Image image = new Image(new ByteArrayInputStream(user.getImage()));
            avatarImage.setImage(image);
        }
        avatarBox.getChildren().add(avatarImage);

        // Phần thứ hai: VBox chứa nội dung nhận xét
        VBox contentBox = new VBox();
        contentBox.setSpacing(5); // Khoảng cách giữa các phần trong VBox
        contentBox.setPrefWidth(718 - 40); // Phần còn lại trừ avatar
        contentBox.setAlignment(Pos.TOP_LEFT); // Căn góc trái trên cùng


        // HBox trên cùng chứa tên người dùng và xếp hạng
        HBox headerBox = new HBox();
        headerBox.setSpacing(10);



        HBox usernameBox = new HBox();
        usernameBox.setAlignment(Pos.CENTER_LEFT);
        usernameBox.setPrefWidth(150); // Đặt chiều rộng cố định cho tên




        Text usernameText = new Text(user.getFullName());
        usernameText.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        usernameBox.getChildren().add(usernameText);

        HBox ratingBox = new HBox();
        ratingBox.setAlignment(Pos.CENTER_LEFT);



        Rating rating = new Rating();
        rating.setDisable(true);
        rating.setScaleX(1);
        rating.setScaleY(1);
        rating.setPartialRating(true);
        rating.setRating(SQLController.getRecentlyUserRating(ISBN, username));
        ratingBox.getChildren().add(rating);

        headerBox.getChildren().addAll(usernameBox, ratingBox);

        // HBox dưới cùng chứa comment
        HBox commentBox = new HBox();
        commentBox.setAlignment(Pos.TOP_LEFT);

        Label commentLabel = new Label(SQLController.getRecentlyUserComment(ISBN, username));
        commentLabel.setWrapText(true); // Tự động xuống dòng nếu quá dài
        commentLabel.setStyle("-fx-font-size: 14px;");
        commentBox.getChildren().add(commentLabel);

        // Thêm headerBox và commentBox vào contentBox
        contentBox.getChildren().addAll(headerBox, commentBox);

        // Thêm avatarBox và contentBox vào reviewBox
        reviewBox.getChildren().addAll(avatarBox, contentBox);

        // Trả về HBox chứa review
        return reviewBox;
    }

    @FXML
    void handleSendReview(MouseEvent event) {
        SQLController.addUserRatings(ISBN.getText(), username, star_ranking.getRating(), commentBox.getText());
        setReviewsPane();
    }

    public void setLanguageToEn() {
        language = "en";
        datePublishedText.setText("DATE PUBLISHED:");
        genreText.setText("GENRE:");
        authorText.setText("AUTHOR:");
        rateThisBookText.setText("Rate this book");
        reviewTitle.setText("Most recently reviews");
        if (isBorrowed) {
            borrowBtn.setText("Return book");
        } else {
            borrowBtn.setText("Borrow book");
        }

    }

    public void setLanguageToVi() {
        language = "vi";
        datePublishedText.setText("NGÀY XUẤT BẢN:");
        genreText.setText("THỂ LOẠI:");
        authorText.setText("TÁC GIẢ:");
        rateThisBookText.setText("Đánh giá sách");
        reviewTitle.setText("Nhận xét gần đây nhất");
        if (isBorrowed) {
            borrowBtn.setText("Trả sách");
        } else {
            borrowBtn.setText("Mượn sách");
        }
    }

    public void setBorrowed(boolean b) {
        isBorrowed = b;
        if (isBorrowed) {
            if (language.equals("vi")) {
                borrowBtn.setText("Trả sách");
            } else {
                borrowBtn.setText("Return book");
            }

        } else {
            if (language.equals("vi")) {
                borrowBtn.setText("Mượn sách");
            } else {
                borrowBtn.setText("Borrow book");
            }
        }
    }
}
