package org.example.ooplibrary.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.example.ooplibrary.Object.Book;
import org.example.ooplibrary.Object.BookLoan;
import org.example.ooplibrary.Object.User;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class AddReturnRequestController {

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

    public void setReturnDocumentController(ReturnDocumentController returnDocumentController) {
        this.returnDocumentController = returnDocumentController;
    }

    @FXML
    public void autofill(MouseEvent mouseEvent) {
        flowPane.getChildren().clear();
        ArrayList<BookLoan> bookLoanArrayList = SQLController.getBookLoansDataWithUser(userName.getText());

        for (BookLoan bookLoan : bookLoanArrayList) {
            HBox bookLoanBox = createBookLoanBox(bookLoan.getBookName());
            bookLoanBox.setSpacing(10);
            bookLoanBox.setPrefWidth(flowPane.getWidth());
            flowPane.getChildren().add(bookLoanBox);

            bookLoanBox.setOnMouseClicked(event -> {
                bookTitle.setText(bookLoan.getBookName());
                ISBN.setText(SQLController.getISBNWithBookLoanID(bookLoan.getBookLoanID()));
                bookName.setText(bookLoan.getBookName());
            });
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
}
