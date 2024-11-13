package org.example.ooplibrary.Controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.ooplibrary.Object.Book;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

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
    private AnchorPane anchorPane;


    @FXML
    void handleAddBook(MouseEvent event) {
        byte[] bookImage = convertImageViewToBlob(this.bookImage);
        if (!SQLController.addBook(ISBN.getText(), bookName.getText(), yearOfPublication.getText(), author.getText(), genre.getText(), description.getText(), bookImage))
            return;
        documentArchiveController.addBook(new Book(ISBN.getText(), bookName.getText(), yearOfPublication.getText(), author.getText(), genre.getText(), description.getText(), bookImage));
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

    public static byte[] convertImageViewToBlob(ImageView imageView) {
        Image image = imageView.getImage();  // Lấy Image từ ImageView

        if (image == null) {
            return null; // Nếu không có hình ảnh thì trả về null
        }

        // Chuyển Image thành BufferedImage
        BufferedImage bufferedImage = javafx.embed.swing.SwingFXUtils.fromFXImage(image, null);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            // Ghi ảnh dưới dạng JPEG vào ByteArrayOutputStream
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);

            // Trả về mảng byte của ảnh (BLOB)
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
