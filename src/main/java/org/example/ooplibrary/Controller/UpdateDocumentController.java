package org.example.ooplibrary.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.ooplibrary.Object.Book;
import org.example.ooplibrary.Utils.GoogleBookAPIUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;

public class UpdateDocumentController {

    @FXML
    private TextField ISBN;

    @FXML
    private TextField author;

    @FXML
    private TextField bookName;

    @FXML
    private TextField description;

    @FXML
    private FlowPane flowPane;

    @FXML
    private TextField yearOfPublication;

    @FXML
    private ImageView bookImage;

    private DocumentArchiveController documentArchiveController;

    @FXML
    private ImageView addImageButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView autofillBtn;

    @FXML
    private TextField genreField;

    private ArrayList<String> genreLists = new ArrayList<String>();

    private String language;

    @FXML
    void handleUpdateBook(MouseEvent event) {
        byte[] bookImage = SQLController.convertImageViewToBlob(this.bookImage);
        String bookTitle = bookName.getText().replace("\\s" , "");
        Book book = new Book(ISBN.getText(),bookName.getText(),yearOfPublication.getText(),author.getText(),genreLists,description.getText(),bookImage);
        SQLController.updateBookInfo(book);

        documentArchiveController.refresh();
        System.out.println("DIT CON ME");
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    public void setDocumentArchiveController(DocumentArchiveController documentArchiveController) {
        this.documentArchiveController = documentArchiveController;
    }

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

    @FXML
    public void autofill(MouseEvent event) {
        // Gọi phương thức đồng bộ để lấy thông tin sách theo ISBN
        Book book = SQLController.getBookInfoDataWithISBN(ISBN.getText());

        if (book == null) {
            // Thông báo cho người dùng rằng sách không được tìm thấy
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Book not found!");
            alert.setHeaderText("Book not found");
            alert.setContentText("The book with the ISBN " + ISBN.getText() + " was not found. Please enter the details manually or check the ISBN again.");
            alert.showAndWait();
            return;
        }

        // Điền thông tin sách vào các trường tương ứng
        bookName.setText(book.getName());
        author.setText(book.getAuthor());
        yearOfPublication.setText(book.getYearOfPublication());
        description.setText(book.getDescription());

        if (book.getImage() != null) {
            Image image = new Image(new ByteArrayInputStream(book.getImage()));
            bookImage.setImage(image);
        }

        // Làm sạch và điền thông tin thể loại
        flowPane.getChildren().clear();
        for (String genre1 : book.getGenres()) {
            HBox genreBox = createGenreBox(genre1);
            genreBox.setSpacing(10);
            genreBox.setPrefWidth(flowPane.getWidth());
            flowPane.getChildren().add(genreBox);
            genreLists.add(genre1);
        }
    }



    private ObservableList<String> normalizeToList(String genres) {
        String[] genresArray = genres.split(",");
        //remove extra spaces
        for (int i = 0; i < genresArray.length; i++) {
            genresArray[i] = genresArray[i].trim();
        }
        return FXCollections.observableArrayList(genresArray);
    }

    private HBox createGenreBox(String genreName) {
        // Tạo HBox
        HBox genreBox = new HBox();
        genreBox.setSpacing(10); // Khoảng cách giữa các phần tử
        genreBox.setStyle("-fx-padding: 5; -fx-background-color: #f4f4f4; -fx-border-color: #d3d3d3; -fx-border-radius: 5;");

        // Tạo Text để hiển thị tên thể loại
        Text genreNamed = new Text(genreName);
        genreNamed.setStyle("-fx-font-size: 14;");

        // Tạo Region để đẩy deleteButton sang phải
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); // Đặt thuộc tính để Region giãn nở

        // Tạo Button để xóa HBox
        Button deleteButton = new Button("X");
        deleteButton.setStyle("-fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-padding: 5; -fx-border-radius: 5;");
        deleteButton.setOnAction(event -> {
            if (genreBox.getParent() instanceof FlowPane) {
                FlowPane parent = (FlowPane) genreBox.getParent();
                genreLists.remove(genreName);
                parent.getChildren().remove(genreBox); // Xóa HBox khỏi FlowPane
            }
        });

        // Thêm Text, Region và Button vào HBox
        genreBox.getChildren().addAll(genreNamed, spacer, deleteButton);

        return genreBox;
    }

    @FXML
    void addGenre(MouseEvent event) {
        if (genreField.getText().isEmpty()) return;

        HBox genreBox = createGenreBox(genreField.getText());
        genreBox.setSpacing(10);
        genreBox.setPrefWidth(flowPane.getWidth());
        flowPane.getChildren().add(genreBox);
        genreLists.add(genreField.getText());
    }

    private String createGenresAsString() {
        StringBuilder genresString = new StringBuilder();
        for (int i =0; i< genreLists.size(); i++) {
            genresString.append(genreLists.get(i));
            if (i != genreLists.size() - 1) {
                genresString.append(", ");
            }
        }
        return genresString.toString();
    }

    public void setDocumentDetails(String isbn) {
        ISBN.setText(isbn);
        autofill(null);
    }
}
