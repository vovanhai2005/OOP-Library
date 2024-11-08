package org.example.ooplibrary.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
    private TableColumn<Book, Void> featureCol;

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

        // Cấu hình cột featureCol với các nút tuỳ chỉnh
        addFeatureButtonsToTable();


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

    private void addFeatureButtonsToTable() {
        featureCol.setCellFactory(param -> new TableCell<Book, Void>() {
            private final Button viewButton = new Button("Xem");
            private final Button editButton = new Button("Chỉnh sửa");
            private final Button deleteButton = new Button("Xóa");

            {
                // Xử lý sự kiện khi nhấn vào nút "Xem"
                viewButton.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    viewBookDetails(book);
                });

                // Xử lý sự kiện khi nhấn vào nút "Chỉnh sửa"
                editButton.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    editBookInfo(book);
                });

                // Xử lý sự kiện khi nhấn vào nút "Xóa"
                deleteButton.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    deleteBook(book);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonsBox = new HBox(5, viewButton, editButton, deleteButton);
                    setGraphic(buttonsBox);
                }
            }
        });
    }

    private void viewBookDetails(Book book) {
        // Mã để hiển thị chi tiết của tài liệu
        System.out.println("Xem chi tiết của tài liệu: " + book.getName());
        // Bạn có thể mở một cửa sổ mới để hiển thị thông tin chi tiết
    }

    private void editBookInfo(Book book) {
        // Mã để mở cửa sổ chỉnh sửa thông tin của tài liệu
        System.out.println("Chỉnh sửa thông tin của tài liệu: " + book.getName());
        // Thực hiện logic chỉnh sửa tài liệu ở đây
    }

    private void deleteBook(Book book) {
        // Mã để xóa tài liệu
        System.out.println("Xóa tài liệu: " + book.getName());
        // Xóa tài liệu khỏi cơ sở dữ liệu và cập nhật TableView
        if (SQLController.deleteBook(book.getISBN())) {
            data.remove(book); // Xóa sách khỏi danh sách
            tableView.refresh(); // Làm mới bảng
        }
    }


}