package org.example.ooplibrary.Controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import org.controlsfx.control.Rating;
import org.example.ooplibrary.Object.Book;

import java.io.ByteArrayInputStream;

public class UserDisplayDocumentController {

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


    public void setDocumentDetails(Book book) {
        ISBN.setText(book.getISBN());
        author.setText(book.getAuthor());
        bookName.setText(book.getName());
        description.setText(book.getDescription());
        genre.setText(book.getGenresString());
        yearOfPublication.setText(book.getYearOfPublication());

        Scale scale = new Scale(0.7, 0.7);
        star_ranking.getTransforms().add(scale);

        // Nếu có hình ảnh, hiển thị trong bookImage
        if (book.getImage() != null) {
            Image image = new Image(new ByteArrayInputStream(book.getImage()));
            bookImage.setImage(image);
        } else {
            System.out.println("No image found");
        }
    }

    @FXML
    void handleAddBook(MouseEvent event) {
        System.out.println("Add book to cart (in progress)");
    }
}
