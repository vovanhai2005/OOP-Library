package org.example.ooplibrary.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.ooplibrary.Object.Book;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DocumentArchiveController extends AbstractMenuController implements Initializable, AbstractLanguageConfig {

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

    @FXML
    private TextField searchKeyword;

    @FXML
    private Label languageText;

    @FXML
    private Label addBookText;



    @FXML
    private Text bookListTitle;

    @FXML
    private Label booksListBtn;

    @FXML
    private Label borrowBtn;

    @FXML
    private Label dashboardBtn;



    @FXML
    private Label logoutBtn;



    @FXML
    private Label returnBtn;



    @FXML
    private Label userListBtn;




    private ObservableList<Book> data;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.getColumns().clear();

        ISBNCol.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        yearOfPublicationCol.setCellValueFactory(new PropertyValueFactory<>("yearOfPublication"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genresString"));

        // Cấu hình cột featureCol với các nút tuỳ chỉnh
        addFeatureButtonsToTable("en");
        tableView.getColumns().addAll(ISBNCol, nameCol, yearOfPublicationCol, authorCol, genreCol, featureCol);

        new Thread(() -> {
            try {
                ArrayList<Book> temp = SQLController.getBookInfoData();
                Platform.runLater(() -> {
                    data = FXCollections.observableArrayList(temp);
                    tableView.setItems(data);
                });
            } catch (Exception e) {
                System.err.println("Error loading book info: " + e.getMessage());
            }
        }).start();
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void performSearch1(MouseEvent event) {
        String keyword = searchKeyword.getText();

        new Thread(() -> {
            try {
                ArrayList<Book> temp = SQLController.getBookInfoDataWithKeyword(keyword);
                Platform.runLater(() -> {
                    if (temp != null) {
                        data.clear();
                        data.addAll(temp);
                        tableView.setItems(data);
                    }
                });
            } catch (Exception e) {
                System.err.println("Error performing search: " + e.getMessage());
            }
        }).start();
    }


    @FXML
    void performSearch2(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            String keyword = searchKeyword.getText();

            try {
                // Gọi phương thức tìm kiếm đồng bộ
                ArrayList<Book> temp = SQLController.getBookInfoDataWithKeyword(keyword);

                if (temp != null) {
                    data.clear();
                    data.addAll(temp);
                    tableView.setItems(data);
                }
            } catch (Exception e) {
                // Xử lý lỗi nếu xảy ra trong quá trình tìm kiếm
                System.err.println("Error performing search: " + e.getMessage());
            }
        }
    }



    public void addBook(Book book) {
        data.add(book);
    }


    private void addFeatureButtonsToTable(String language) {
        final String viewText = (language.equals("en")) ? "View" : "Xem";
        final String editText = (language.equals("en")) ? "Edit" : "Chỉnh sửa";
        final String deleteText = (language.equals("en")) ? "Delete" : "Xóa";
        featureCol.setCellFactory(param -> new TableCell<Book, Void>() {
            private final Button viewButton = new Button(viewText);
            private final Button editButton = new Button(editText);
            private final Button deleteButton = new Button(deleteText);

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
        try {
            // Tải FXML của giao diện hiển thị tài liệu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/DisplayDocument_View.fxml"));
            Parent root = loader.load();

            // Lấy controller của cửa sổ hiển thị tài liệu
            DisplayDocumentController displayDocumentController = loader.getController();

            // Gán dữ liệu tài liệu vào controller
            displayDocumentController.setDocumentDetails(book);
            if (language.equals("en")) {
                displayDocumentController.setLanguageToEn();
            } else {
                displayDocumentController.setLanguageToVi();
            }
            System.out.println(book.getISBN());
            displayDocumentController.setUpTableWithISBN(book.getISBN());

            // Tạo cửa sổ mới để hiển thị thông tin chi tiết
            Stage stage = new Stage();
            if (language.equals("en")) {
                stage.setTitle("Document details");
            } else {
                stage.setTitle("Thông tin chi tiết tài liệu");
            }

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editBookInfo(Book book) {
        try {
            // Tải FXML của giao diện hiển thị tài liệu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/UpdateDocument_View.fxml"));
            Parent root = loader.load();

            // Lấy controller của cửa sổ hiển thị tài liệu
            UpdateDocumentController updateDocumentController = loader.getController();
            updateDocumentController.setDocumentArchiveController(this);

            // Tạo cửa sổ mới để hiển thị thông tin chi tiết
            Stage stage = new Stage();
            if (language.equals("en")) {
                stage.setTitle("Update document");
                updateDocumentController.setLanguageToEn();
            } else {
                stage.setTitle("Cập nhật tài liệu");
                updateDocumentController.setLanguageToVi();
            }
            // Gán dữ liệu tài liệu vào controller
            updateDocumentController.setDocumentDetails(book.getISBN());

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refresh() {
        // Lấy dữ liệu sách một cách đồng bộ
        ArrayList<Book> temp = SQLController.getBookInfoData(); // Giả sử bạn đã tạo phương thức này để lấy dữ liệu đồng bộ

        if (temp != null) {
            data.clear();
            data.addAll(temp);
            tableView.setItems(data);
        }
    }


    private void deleteBook(Book book) {
        // Tạo Alert kiểu xác nhận
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (language.equals("vi")) {
            alert.setTitle("Xác nhận xóa");
            alert.setHeaderText("Bạn có chắc chắn muốn xóa tài liệu này?");
            alert.setContentText("Tên tài liệu: " + book.getName() + "\n"
                    + "Tác giả: " + book.getAuthor() + "\n\n"
                    + "Lưu ý: Tài liệu sẽ bị xóa khỏi cơ sở dữ liệu và không thể khôi phục!");
        }
        else {
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure you want to delete this document?");
            alert.setContentText("Document name: " + book.getName() + "\n"
                    + "Author: " + book.getAuthor() + "\n\n"
                    + "Note: The document will be deleted from the database and cannot be recovered!");
        }

        // Hiển thị cửa sổ Alert và chờ phản hồi từ người dùng
        Optional<ButtonType> result = alert.showAndWait();

        // Kiểm tra phản hồi
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Người dùng chọn OK
            if (SQLController.deleteBook(book.getISBN())) {
                // Xóa sách khỏi danh sách dữ liệu
                data.remove(book);

                // Làm mới bảng
                tableView.refresh();

                // Hiển thị thông báo thành công
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                if (language.equals("vi")) {
                    successAlert.setTitle("Xóa thành công");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Tài liệu \"" + book.getName() + "\" đã được xóa thành công!");
                }
                else {
                    successAlert.setTitle("Delete successful");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Document \"" + book.getName() + "\" has been deleted successfully!");
                }

                successAlert.show();
            } else {
                // Hiển thị thông báo lỗi nếu không xóa được trong cơ sở dữ liệu
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                if (language.equals("vi")) {
                    errorAlert.setTitle("Lỗi xóa");
                    errorAlert.setHeaderText("Không thể xóa tài liệu");
                    errorAlert.setContentText("Đã xảy ra lỗi trong quá trình xóa tài liệu. Vui lòng thử lại sau.");
                }
                else {
                    errorAlert.setTitle("Delete error");
                    errorAlert.setHeaderText("Cannot delete document");
                    errorAlert.setContentText("An error occurred while deleting the document. Please try again later.");
                }
                errorAlert.show();
            }
        } else {
            // Người dùng chọn Cancel hoặc đóng cửa sổ Alert
            System.out.println("Hủy xóa tài liệu: " + book.getName());
        }
    }

    @FXML
    public void setLanguageToEn() {
        addFeatureButtonsToTable("en");
        language = "en";
        languageText.setText("Language:");
        dashboardBtn.setText("Dashboard");
        booksListBtn.setText("Books List");
        userListBtn.setText("User List");
        borrowBtn.setText("Borrow");
        returnBtn.setText("Return");
        logoutBtn.setText("Log out");
        bookListTitle.setText("Books List");
        addBookText.setText("Add new book");
        ISBNCol.setText("ISBN");
        nameCol.setText("Name");
        yearOfPublicationCol.setText("Year of Publication");
        authorCol.setText("Author");
        genreCol.setText("Genre");
        featureCol.setText("Feature");
        tableView.setPlaceholder(new Label("No content in table"));


    }

    @FXML
    public void setLanguageToVi() {
        addFeatureButtonsToTable("vi");
        language = "vi";
        languageText.setText("Ngôn ngữ:");
        dashboardBtn.setText("Bảng thông tin");
        booksListBtn.setText("DS sách");
        userListBtn.setText("DS người dùng");
        borrowBtn.setText("Mượn sách");
        returnBtn.setText("Trả sách");
        logoutBtn.setText("Đăng xuất");
        bookListTitle.setText("Danh sách sách");
        addBookText.setText("Thêm sách mới");
        ISBNCol.setText("Mã ISBN");
        nameCol.setText("Tên");
        yearOfPublicationCol.setText("Năm xuất bản");
        authorCol.setText("Tác giả");
        genreCol.setText("Thể loại");
        featureCol.setText("Chức năng");
        tableView.setPlaceholder(new Label("Không có dữ liệu trong bảng"));
    }


}