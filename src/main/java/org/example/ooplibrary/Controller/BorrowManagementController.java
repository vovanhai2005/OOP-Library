package org.example.ooplibrary.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.ooplibrary.Object.Book;
import org.example.ooplibrary.Object.BookLoan;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BorrowManagementController extends AbstractMenuController implements Initializable,AbstractLanguageConfig {

    @FXML
    private TableColumn<BookLoan, String> IDCol;

    @FXML
    private TableColumn<BookLoan, String> bookNameCol;

    @FXML
    private TableColumn<BookLoan, String> borrowerNameCol;

    @FXML
    private TableColumn<BookLoan, String> dueDateCol;

    @FXML
    private TableColumn<BookLoan, String> informationCol;

    @FXML
    private TableView<BookLoan> tableView;

    @FXML
    private TextField searchKeyword;

    @FXML
    private Label addBorrowText;

    @FXML
    private Label booksListBtn;

    @FXML
    private Label borrowBtn;

    @FXML
    private Text borrowTitle;

    @FXML
    private Label dashboardBtn;

    @FXML
    private Label languageText;

    @FXML
    private Label logOutBtn;

    @FXML
    private Label returnBtn;

    @FXML
    private Label userListBtn;


    private ObservableList<BookLoan> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.getColumns().clear();

        IDCol.setCellValueFactory(new PropertyValueFactory<>("bookLoanID"));
        bookNameCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        borrowerNameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        informationCol.setCellValueFactory(new PropertyValueFactory<>("note"));

        tableView.getColumns().addAll(IDCol, bookNameCol, borrowerNameCol, dueDateCol, informationCol);

        data = FXCollections.observableArrayList(

        );

        ArrayList<BookLoan> temp = SQLController.getBookLoansData();

        if (temp != null)
            for (BookLoan bookLoan : temp) {
                data.add(bookLoan);
            }


        tableView.setItems(data);

    }

    @FXML
    void openRequestBorrowWindow(MouseEvent event) {
        try {
            // Tạo FXMLLoader và nạp file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/AddBorrowBook_View.fxml"));
            Parent secondRoot = loader.load();

            // Lấy controller từ loader và thiết lập documentArchiveController
            AddBorrowBookController addBorrowBookController = loader.getController();
            addBorrowBookController.setBorrowManagementController(this);

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
        data.clear();
        ArrayList<BookLoan> temp = SQLController.getBookLoansDataWithKeyword(searchKeyword.getText());

        if (temp != null)
            for (BookLoan bookLoan : temp) {
                data.add(bookLoan);
            }

        tableView.setItems(data);
    }

    @FXML
    void performSearch2(KeyEvent event) {
        //Check if KeyEvent is Enter
        if (event.getCode().toString().equals("ENTER")) {
            data.clear();
            ArrayList<BookLoan> temp = SQLController.getBookLoansDataWithKeyword(searchKeyword.getText());

            if (temp != null)
                for (BookLoan bookLoan : temp) {
                    data.add(bookLoan);
                }

            tableView.setItems(data);
        }
    }


    private void viewBookLoanDetails(BookLoan bookLoan) {

        System.out.println("Xem thông tin của phiếu mượn: " + bookLoan.getBookLoanID());
        // Thực hiện logic xem thông tin phiếu mượn ở đây
    }


    public void addBookLoan(BookLoan bookLoan) {
        data.add(bookLoan);
        tableView.setItems(data);
        tableView.refresh();
    }

    @FXML
    public void setLanguageToEn() {

        language = "en";
        languageText.setText("Language:");
        dashboardBtn.setText("Dashboard");
        booksListBtn.setText("Books List");
        userListBtn.setText("User List");
        borrowBtn.setText("Borrow");
        returnBtn.setText("Return");
        logOutBtn.setText("Log out");
        borrowTitle.setText("Borrow History");
        addBorrowText.setText("Add borrow request");
        IDCol.setText("ID");
        bookNameCol.setText("Book Name");
        borrowerNameCol.setText("Borrower Username");
        dueDateCol.setText("Due Date");
        informationCol.setText("Note");
        tableView.setPlaceholder(new Label("No content in table"));
    }

    @FXML
    public void setLanguageToVi() {
        language = "vi";
        languageText.setText("Ngôn ngữ:");
        dashboardBtn.setText("Bảng thông tin");
        booksListBtn.setText("DS sách");
        userListBtn.setText("DS người dùng");
        borrowBtn.setText("Mượn sách");
        returnBtn.setText("Trả sách");
        logOutBtn.setText("Đăng xuất");
        borrowTitle.setText("Lịch sử mượn sách");
        addBorrowText.setText("Thêm YC mượn sách");
        IDCol.setText("ID");
        bookNameCol.setText("Tên sách");
        borrowerNameCol.setText("Tên người mượn");
        dueDateCol.setText("Ngày hết hạn");
        informationCol.setText("Ghi chú");
        tableView.setPlaceholder(new Label("Không có dữ liệu trong bảng"));
    }
}