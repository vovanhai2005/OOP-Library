package org.example.ooplibrary.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.example.ooplibrary.Object.Book;

import java.net.URL;
import java.util.ResourceBundle;

public class DocumentArchiveController extends MainMenuController implements Initializable {

    @FXML
    private TableView<Book> tableView;

    @FXML
    private TableColumn<Book, String> ISBNCol;

    @FXML
    private TableColumn<Book, String> nameCol;

    @FXML
    private TableColumn<Book, Integer> yearOfPublicationCol;

    @FXML
    private TableColumn<Book, String> authorCol;

    @FXML
    private TableColumn<Book, String> genreCol;

    // Updated TableColumn for features
    @FXML
    private TableColumn<Book, ImageView[]> featureCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.getColumns().clear();

        ISBNCol.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        yearOfPublicationCol.setCellValueFactory(new PropertyValueFactory<>("yearOfPublication"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        featureCol.setCellValueFactory(new PropertyValueFactory<>("features")); // Update to features

        tableView.getColumns().addAll(ISBNCol, nameCol, yearOfPublicationCol, authorCol, genreCol, featureCol);

        final ObservableList<Book> data = FXCollections.observableArrayList(
                new Book("HAHA", "VoHai", 2000, "DoYouLoveMe", "Supernatural and Horror")

        );

        tableView.setItems(data);
    }
}