package org.example.ooplibrary.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.ooplibrary.Object.Book;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Stage secondStage;
    private Scene secondScene;
    private Parent secondRoot;


    private ObservableList<Book> data;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.getColumns().clear();

        //ISBNCol.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        yearOfPublicationCol.setCellValueFactory(new PropertyValueFactory<>("yearOfPublication"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        featureCol.setCellValueFactory(new PropertyValueFactory<>("features")); // Update to features

        tableView.getColumns().addAll(ISBNCol, nameCol, yearOfPublicationCol, authorCol, genreCol, featureCol);

        data = FXCollections.observableArrayList(

        );

        ArrayList<Book> temp = SQLController.getBookInfoData();

        if (temp != null)
        for (Book book : temp) {
            data.add(book);
        }


        tableView.setItems(data);
    }

    @FXML
    void switchToBorrowDocumentView(MouseEvent event) {

    }

    @FXML
    void switchToDocumentArchiveView(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/org/example/ooplibrary/View/DocumentArchive_View.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToLoginView(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/org/example/ooplibrary/View/LogIn_View.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToReturnDocumentView(MouseEvent event) {

    }

    @FXML
    void switchToUserManagementView(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/org/example/ooplibrary/View/UserManagement_View.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToMainMenuView(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/org/example/ooplibrary/View/MainMenu_View.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openAddDocumentWindow(MouseEvent event) {
        try {
            // Tạo FXMLLoader và nạp file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/AddDocument_View.fxml"));
            Parent secondRoot = loader.load();

            // Lấy controller từ loader và thiết lập documentArchiveController
            AddDocumentController addDocumentController = loader.getController();
            addDocumentController.setDocumentArchiveController(this);

            // Thiết lập cửa sổ và hiển thị
            Stage secondStage = new Stage();
            Scene secondScene = new Scene(secondRoot);
            secondStage.setScene(secondScene);
            secondStage.setTitle("Thêm tài liệu");
            secondStage.show();

            // Lưu lại tham chiếu đến secondStage nếu cần thiết
            this.secondStage = secondStage;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addBook(Book book) {
        data.add(book);
    }

    public Stage getSecondStage() {
        return secondStage;
    }
}