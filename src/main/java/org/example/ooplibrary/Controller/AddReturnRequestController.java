package org.example.ooplibrary.Controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.ooplibrary.Object.Book;
import org.example.ooplibrary.Object.BookLoan;
import org.example.ooplibrary.Object.User;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class AddReturnRequestController implements AbstractLanguageConfig {

    @FXML
    private TextField userName;

    @FXML
    private ScrollPane bookLoanPane;

    @FXML
    private FlowPane flowPane;

    private ReturnDocumentController returnDocumentController;

    @FXML
    private Text bookTitle;

    @FXML
    private TextField ISBN;

    @FXML
    private TextField bookName;

    @FXML
    private ImageView bookImage;

    @FXML
    private DatePicker returnDate;

    private String language;

    @FXML
    private TextField note;

    public void setReturnDocumentController(ReturnDocumentController returnDocumentController) {
        this.returnDocumentController = returnDocumentController;
    }

    @FXML
    public void autofill(MouseEvent mouseEvent) {
        flowPane.getChildren().clear();
        ArrayList<BookLoan> bookLoanArrayList = SQLController.getBookLoansDataWithUser(userName.getText());

        for (BookLoan bookLoan : bookLoanArrayList) {
            if (bookLoan.getReturnDate() == null) {
                HBox bookLoanBox = createBookLoanBox(bookLoan.getBookName());
                bookLoanBox.setSpacing(10);
                bookLoanBox.setPrefWidth(flowPane.getWidth());
                flowPane.getChildren().add(bookLoanBox);

                bookLoanBox.setOnMouseClicked(event -> {
                    bookTitle.setText(bookLoan.getBookName());
                    ISBN.setText(SQLController.getISBNWithBookLoanID(bookLoan.getBookLoanID()));
                    bookName.setText(bookLoan.getBookName());
                    Task<Book> bookTask = SQLController.getBookInfoDataWithISBN(String.format(ISBN.getText()));

                    bookTask.setOnSucceeded(taskEvent -> {
                        Book book = bookTask.getValue();
                        if (book != null) {
                            ByteArrayInputStream inputStream = new ByteArrayInputStream(book.getImage());
                            Image image = new Image(inputStream);
                            bookImage.setImage(image);
                        } else {
                            System.out.println("Book information is null.");
                        }
                    });
                    note.setText("Return request");
                    new Thread(bookTask).start(); // Bắt đầu thực thi Task
                });
            }
        }
    }

    private HBox createBookLoanBox(String bookName){
        HBox bookBox = new HBox();
        bookBox.setSpacing(10); // Khoảng cách giữa các phần tử
        bookBox.setStyle("-fx-padding: 5; -fx-background-color: #f4f4f4; -fx-border-color: #d3d3d3; -fx-border-radius: 5;");

        Text bookNamed = new Text(bookName);
        bookNamed.setStyle("-fx-font-size: 14;");

        bookBox.getChildren().add(bookNamed);

        return bookBox;
    }

    UserDisplayDocumentController userDisplayDocumentController;
    public void setUserDisplayDocumentController(UserDisplayDocumentController userDisplayDocumentController) {
        this.userDisplayDocumentController = userDisplayDocumentController;
    }

    @FXML
    public void handleReturnRequest(MouseEvent mouseEvent) {
        if (userName.getText().isEmpty() || returnDate.getValue() == null) {
            System.out.println("Please fill in all the fields");
            return;
        }
        ArrayList<BookLoan> bookLoanArrayList = SQLController.getBookLoansDataWithUser(userName.getText());

        for (BookLoan bookLoan : bookLoanArrayList) {
            if (bookLoan.getBookName().equals(bookName.getText())) {
                bookLoan.setUsername(userName.getText());
                bookLoan.setReturnDate(returnDate.getValue().toString());
                SQLController.updateBookLoan(bookLoan.getBookLoanID() , returnDate);
                if (returnDocumentController!=null) {
                    returnDocumentController.addReturnRequest(bookLoan);
                }
                if (userDisplayDocumentController!=null) {
                    userDisplayDocumentController.setBorrowed(false);
                }
                break;
            }
        }
        System.out.println("add complete");

        ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).close();
    }

    public void setUsername(String username) {
        this.userName.setText(username);
    }

    @Override
    public void setLanguageToEn() {
        language = "en";
    }

    @Override
    public void setLanguageToVi() {
        language = "vi";
    }
}
