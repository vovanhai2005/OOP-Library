package org.example.ooplibrary.Controller;

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
import javafx.stage.Stage;
import org.example.ooplibrary.Object.Book;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DocumentArchiveController extends AbstractMenuController implements Initializable {

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
        addFeatureButtonsToTable();


        tableView.getColumns().addAll(ISBNCol, nameCol, yearOfPublicationCol, authorCol, genreCol, featureCol);

        data = FXCollections.observableArrayList(

        );

        ArrayList<Book> temp = SQLController.getBookInfoData();

        for (Book book : temp) {
            data.add(book);
        }


        tableView.setItems(data);
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
        ArrayList<Book> temp = SQLController.getBookInfoDataWithKeyword(searchKeyword.getText());

        if (temp != null) {
            data.clear();
            for (Book book : temp) {
                data.add(book);
            }
            tableView.setItems(data);
        }
    }

    @FXML
    void performSearch2(KeyEvent event) {
        //Check if KeyEvent is Enter
        if (event.getCode().toString().equals("ENTER")) {
            data.clear();
            ArrayList<Book> temp = SQLController.getBookInfoDataWithKeyword(searchKeyword.getText());

            if (temp != null)
                for (Book book : temp) {
                    data.add(book);
                }

            tableView.setItems(data);
        }
    }

    public void addBook(Book book) {
        data.add(book);
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
        try {
            // Tải FXML của giao diện hiển thị tài liệu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/DisplayDocument_View.fxml"));
            Parent root = loader.load();

            // Lấy controller của cửa sổ hiển thị tài liệu
            DisplayDocumentController displayDocumentController = loader.getController();

            // Gán dữ liệu tài liệu vào controller
            displayDocumentController.setDocumentDetails(book);

            // Tạo cửa sổ mới để hiển thị thông tin chi tiết
            Stage stage = new Stage();
            stage.setTitle("Thông tin chi tiết tài liệu");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editBookInfo(Book book) {
        // Mã để mở cửa sổ chỉnh sửa thông tin của tài liệu
        System.out.println("Chỉnh sửa thông tin của tài liệu: " + book.getName());
        // Thực hiện logic chỉnh sửa tài liệu ở đây
    }

    private void deleteBook(Book book) {
        // Tạo Alert kiểu xác nhận
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText("Bạn có chắc chắn muốn xóa tài liệu này?");
        alert.setContentText("Tên tài liệu: " + book.getName() + "\n"
                + "Tác giả: " + book.getAuthor() + "\n\n"
                + "Lưu ý: Tài liệu sẽ bị xóa khỏi cơ sở dữ liệu và không thể khôi phục!");

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
                successAlert.setTitle("Xóa thành công");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Tài liệu \"" + book.getName() + "\" đã được xóa thành công!");
                successAlert.show();
            } else {
                // Hiển thị thông báo lỗi nếu không xóa được trong cơ sở dữ liệu
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Lỗi xóa");
                errorAlert.setHeaderText("Không thể xóa tài liệu");
                errorAlert.setContentText("Đã xảy ra lỗi trong quá trình xóa tài liệu. Vui lòng thử lại sau.");
                errorAlert.show();
            }
        } else {
            // Người dùng chọn Cancel hoặc đóng cửa sổ Alert
            System.out.println("Hủy xóa tài liệu: " + book.getName());
        }
    }


}