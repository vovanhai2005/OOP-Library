package org.example.ooplibrary.Controller;

import javafx.application.Platform;
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

        new Thread(() -> {
            ArrayList<BookLoan> bookLoanArrayList = SQLController.getBookLoansDataWithUserAndNullReturnDate(userName.getText());

            Platform.runLater(() -> {
                for (BookLoan bookLoan : bookLoanArrayList) {
                    HBox bookLoanBox = createBookLoanBox(bookLoan.getBookName());
                    bookLoanBox.setSpacing(10);
                    bookLoanBox.setPrefWidth(flowPane.getWidth());
                    flowPane.getChildren().add(bookLoanBox);

                    bookLoanBox.setOnMouseClicked(event -> {
                        bookTitle.setText(bookLoan.getBookName());
                        ISBN.setText(SQLController.getISBNWithBookLoanID(bookLoan.getBookLoanID()));
                        bookName.setText(bookLoan.getBookName());

                        new Thread(() -> {
                            Book book = SQLController.getBookInfoDataWithISBN(ISBN.getText());

                            Platform.runLater(() -> {
                                if (book != null) {
                                    ByteArrayInputStream inputStream = new ByteArrayInputStream(book.getImage());
                                    Image image = new Image(inputStream);
                                    bookImage.setImage(image);
                                    note.setText("Return request");
                                } else {
                                    System.out.println("Book information is null.");
                                }
                            });
                        }).start();
                    });
                }
            });
        }).start();
    }

    @FXML
    public void autofillBook(BookLoan bookLoan) {
        ISBN.setText(SQLController.getISBNWithBookLoanID(bookLoan.getBookLoanID()));
        bookName.setText(bookLoan.getBookName());

        new Thread(() -> {
            Book book = SQLController.getBookInfoDataWithISBN(ISBN.getText());

            Platform.runLater(() -> {
                if (book != null) {
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(book.getImage());
                    Image image = new Image(inputStream);
                    bookImage.setImage(image);
                    note.setText("Return request");
                } else {
                    System.out.println("Book information is null.");
                }
            });
        }).start();
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

    public void setBookTitle(String text) {
        this.bookTitle.setText(text);
    }
}
