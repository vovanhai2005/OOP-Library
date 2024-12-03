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

public class UpdateDocumentController implements AbstractLanguageConfig {

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

    @FXML
    private Button applyBtn;

    @FXML
    private Text authorText;

    @FXML
    private Text descriptionText;


    @FXML
    private Text genreText;

    @FXML
    private Text nameText;

    @FXML
    private Text updateBookTitle;


    @FXML
    private Text yearOfPublicationText;

    private ArrayList<String> genreLists = new ArrayList<String>();

    private String language;

    @FXML
    void handleUpdateBook(MouseEvent event) {
        //Check if any of the fields are empty
        if (ISBN.getText().isEmpty()) {
            if (language.equals("en")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty ISBN");
                alert.setContentText("Please enter the ISBN of the book you want to update.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText("ISBN trống");
                alert.setContentText("Vui lòng nhập ISBN của cuốn sách bạn muốn cập nhật.");
                alert.showAndWait();
            }
            return;
        }
        if (bookName.getText().isEmpty()) {
            if (language.equals("en")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty Name");
                alert.setContentText("Please enter the name of the book you want to update.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Tên trống");
                alert.setContentText("Vui lòng nhập tên của cuốn sách bạn muốn cập nhật.");
                alert.showAndWait();
            }
            return;
        }
        if (author.getText().isEmpty()) {
            if (language.equals("en")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty Author");
                alert.setContentText("Please enter the author of the book you want to update.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Tác giả trống");
                alert.setContentText("Vui lòng nhập tác giả của cuốn sách bạn muốn cập nhật.");
                alert.showAndWait();
            }
            return;
        }
        if (yearOfPublication.getText().isEmpty()) {
            if (language.equals("en")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty Year of Publication");
                alert.setContentText("Please enter the year of publication of the book you want to update.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Năm xuất bản trống");
                alert.setContentText("Vui lòng nhập năm xuất bản của cuốn sách bạn muốn cập nhật.");
                alert.showAndWait();
            }
            return;
        }
        if (description.getText().isEmpty()) {
            if (language.equals("en")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty Description");
                alert.setContentText("Please enter the description of the book you want to update.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Mô tả trống");
                alert.setContentText("Vui lòng nhập mô tả của cuốn sách bạn muốn cập nhật.");
                alert.showAndWait();
            }
            return;
        }
        if (flowPane.getChildren().isEmpty()) {
            if (language.equals("en")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty Genre");
                alert.setContentText("Please enter the genre of the book you want to update.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Thể loại trống");
                alert.setContentText("Vui lòng nhập thể loại của cuốn sách bạn muốn cập nhật.");
                alert.showAndWait();
            }
            return;
        }

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
        if (genreField.getText().isEmpty()) {
            if (language.equals("en")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty Genre");
                alert.setContentText("Please enter the genre you want to add.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Thể loại trống");
                alert.setContentText("Vui lòng nhập thể loại bạn muốn thêm.");
                alert.showAndWait();
            }
            return;
        }

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

    public void setLanguageToEn() {
        language = "en";
        updateBookTitle.setText("Update Book");
        nameText.setText("Name:");
        authorText.setText("Author:");
        yearOfPublicationText.setText("Year of Publication:");
        genreText.setText("Genre:");
        descriptionText.setText("Description:");
        applyBtn.setText("Apply");


    }

    public void setLanguageToVi() {
        language = "vi";
        updateBookTitle.setText("Cập nhật sách");
        nameText.setText("Tên sách:");
        authorText.setText("Tác giả:");
        yearOfPublicationText.setText("Năm xuất bản:");
        genreText.setText("TLoại:");
        descriptionText.setText("Mô tả:");
        applyBtn.setText("Áp dụng");

    }
}
