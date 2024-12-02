package org.example.ooplibrary.Controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.ooplibrary.Object.Book;
import org.example.ooplibrary.Object.BookLoan;
import org.example.ooplibrary.Object.User;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class AddBorrowBookController {
    @FXML
    private ImageView addImageButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView bookImage;

    @FXML
    private TextField ISBN;

    @FXML
    private TextField bookName;

    @FXML
    private TextField email;

    @FXML
    private TextField fullName;

    @FXML
    private TextField phoneNumber;

    @FXML
    private DatePicker dueDate;

    @FXML
    private TextField username;

    @FXML
    private TextField note;

    @FXML
    void autofillByISBN(MouseEvent event) {
        String isbn = ISBN.getText(); // Lấy mã ISBN từ TextField

        // Tạo Task để lấy thông tin sách theo ISBN
        Task<Book> bookTask = SQLController.getBookInfoDataWithISBN(isbn);

        bookTask.setOnSucceeded(e -> {
            Book book = bookTask.getValue(); // Lấy thông tin sách
            if (book != null) {
                bookName.setText(book.getName()); // Điền tên sách vào TextField
                Image image = new Image(new ByteArrayInputStream(book.getImage())); // Tạo hình ảnh từ byte array
                bookImage.setImage(image); // Cập nhật hình ảnh sách
            } else {
                System.out.println("No book found with the given ISBN.");
            }
        });

        // Chạy Task trên một luồng nền
        new Thread(bookTask).start();
    }


    @FXML
    void autofillByUsername(MouseEvent event) {
        if (SQLController.getUserInfoDataByUsername(username.getText()) != null) {
            User user = SQLController.getUserInfoDataByUsername(username.getText());
            fullName.setText(user.getFullName());
            email.setText(user.getEmail());
            phoneNumber.setText(user.getPhoneNumber());
        }
    }

    private BorrowManagementController borrowManagementController;

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
            bookImage.setImage(image);
        }
    }

    public void setBorrowManagementController(BorrowManagementController borrowManagementController) {
        this.borrowManagementController = borrowManagementController;
    }

    @FXML
    void handleAddBorrowRequest(MouseEvent event) {
        // check if any textfield is empty
        if (ISBN.getText().isEmpty() || note.getText().isEmpty() || bookName.getText().isEmpty() || email.getText().isEmpty() || fullName.getText().isEmpty() || phoneNumber.getText().isEmpty() || dueDate.getValue() == null || username.getText().isEmpty()) {
            System.out.println("Please fill in all the fields");
            return;
        }
        byte[] bookImage = SQLController.convertImageViewToBlob(this.bookImage);
        String bookLoanId = SQLController.addBookLoan(ISBN.getText(),username.getText(),note.getText(), SQLController.getDateOfBirthAsString(dueDate));
        //hide the window
        borrowManagementController.addBookLoan(SQLController.getBookLoansDataWithBookLoanID(bookLoanId));
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

}
