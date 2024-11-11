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
        if (!SQLController.addBook(ISBN.getText(), bookName.getText(), yearOfPublication.getText(), author.getText(), genre.getText(), description.getText(), convertFXImageToByteArray(bookImage.getImage())))
            return;
        documentArchiveController.addBook(new Book(ISBN.getText(), bookName.getText(), yearOfPublication.getText(), author.getText(), genre.getText(), description.getText(), convertFXImageToByteArray(bookImage.getImage())));
        ((Node) event.getSource()).getScene().getWindow().hide();
        documentArchiveController.getSecondStage().hide();
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

    private byte[] convertFXImageToByteArray(Image fxImage) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(fxImage, null);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
