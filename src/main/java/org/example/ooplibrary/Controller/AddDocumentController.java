package org.example.ooplibrary.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.ooplibrary.Object.Book;
import org.example.ooplibrary.Utils.GoogleBookAPIUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddDocumentController {
    @FXML
    private TextField ISBN;

    @FXML
    private TextField author;

    @FXML
    private TextField bookName;

    @FXML
    private TextField description;

    @FXML
    private TextField genre;

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
    void handleAddBook(MouseEvent event) {
        byte[] bookImage = SQLController.convertImageViewToBlob(this.bookImage);
        if (!SQLController.addBook(ISBN.getText(), bookName.getText(), yearOfPublication.getText(), author.getText(), genre.getText(), description.getText(), bookImage))
            return;
        Book addedBook = new Book(ISBN.getText(), bookName.getText(), yearOfPublication.getText(), author.getText(), normalizeToList(genre.getText()), description.getText(), bookImage);

        documentArchiveController.addBook(addedBook);

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
        Book book = GoogleBookAPIUtil.fetchBookDetailsByISBN(ISBN.getText());
        if (book == null) {
            return;
        }
        bookName.setText(book.getName());
        author.setText(book.getAuthor());
//        genre.setText(book.getGenresString());
        yearOfPublication.setText(book.getYearOfPublication());
        description.setText(book.getDescription());
        if (book.getImage() != null) {
            Image image = new Image(new ByteArrayInputStream(book.getImage()));
            bookImage.setImage(image);
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


}
