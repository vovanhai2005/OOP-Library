package org.example.ooplibrary.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.example.ooplibrary.Object.Book;
import org.example.ooplibrary.Object.BookLoan;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;

public class DisplayDocumentController implements AbstractLanguageConfig, Initializable {

    @FXML
    private Text ISBN;

    @FXML
    private Text author;

    @FXML
    private Text authorText;

    @FXML
    private ImageView bookImage;

    @FXML
    private Text bookName;

    @FXML
    private TableColumn<BookLoan, String> bookNameCol;

    @FXML
    private Text datePublishedText;

    @FXML
    private Text description;

    @FXML
    private TableColumn<BookLoan, String> dueDateCol;

    @FXML
    private Text genre;

    @FXML
    private Text genreText;

    @FXML
    private TableColumn<BookLoan, String> noteCol;

    @FXML
    private TableColumn<BookLoan, String> returnDateCol;

    @FXML
    private TableView<BookLoan> tableView;

    @FXML
    private TableColumn<BookLoan, String> fullNameCol;

    @FXML
    private Text yearOfPublication;

    @FXML
    private Text borrowHistoryTitle;


    private String language;

    @Override
    public void initialize(URL url, java.util.ResourceBundle resourceBundle) {
        tableView.getColumns().clear();

        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        bookNameCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        returnDateCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));

        tableView.getColumns().addAll(fullNameCol, bookNameCol, dueDateCol, returnDateCol, noteCol);


    }

    public void setDocumentDetails(Book book) {
        ISBN.setText(book.getISBN());
        author.setText(book.getAuthor());
        bookName.setText(book.getName());
        description.setText(book.getDescription());
        genre.setText(book.getGenresString());
        yearOfPublication.setText(book.getYearOfPublication());

        // Nếu có hình ảnh, hiển thị trong bookImage
        if (book.getImage() != null) {
            Image image = new Image(new ByteArrayInputStream(book.getImage()));
            bookImage.setImage(image);
        } else {
            System.out.println("No image found");
        }


    }

    @FXML
    public void setLanguageToEn() {
        language = "en";
        borrowHistoryTitle.setText("Borrow History");
        fullNameCol.setText("Full Name");
        bookNameCol.setText("Book Name");
        dueDateCol.setText("Due Date");
        returnDateCol.setText("Return Date");
        noteCol.setText("Note");
        tableView.setPlaceholder(new Text("No content in table"));
        datePublishedText.setText("Date Published:");
        genreText.setText("Genre:");
        authorText.setText("Author:");
    }

    @FXML
    public void setLanguageToVi() {
        language = "vi";
        borrowHistoryTitle.setText("Lịch sử mượn sách");
        fullNameCol.setText("Họ và tên");
        bookNameCol.setText("Tên sách");
        dueDateCol.setText("Ngày hết hạn");
        returnDateCol.setText("Ngày trả");
        noteCol.setText("Ghi chú");
        tableView.setPlaceholder(new Text("Không có dữ liệu"));
        datePublishedText.setText("Ngày xuất bản:");
        genreText.setText("Thể loại:");
        authorText.setText("Tác giả:");

    }

    public void setUpTableWithISBN(String ISBN) {
        ArrayList<BookLoan> bookLoans = SQLController.getBookLoansDataWithISBN(ISBN);
        ObservableList<BookLoan> data = FXCollections.observableArrayList(bookLoans);
        tableView.setItems(data);
    }

}
