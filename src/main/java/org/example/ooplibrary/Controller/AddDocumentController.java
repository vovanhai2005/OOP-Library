package org.example.ooplibrary.Controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.ooplibrary.Object.Book;

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

    private DocumentArchiveController documentArchiveController;


    @FXML
    void handleAddBook(MouseEvent event) {
        if (!SQLController.addBook(ISBN.getText(), bookName.getText(), yearOfPublication.getText(), author.getText(), genre.getText(), description.getText()))
            return;
        documentArchiveController.addBook(new Book(ISBN.getText(), bookName.getText(), yearOfPublication.getText(), author.getText(), genre.getText(), description.getText()));
        documentArchiveController.getSecondStage().hide();
    }

    public void setDocumentArchiveController(DocumentArchiveController documentArchiveController) {
        this.documentArchiveController = documentArchiveController;
    }


}
