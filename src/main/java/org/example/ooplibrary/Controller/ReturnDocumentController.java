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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.ooplibrary.Object.BookLoan;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReturnDocumentController extends AbstractMenuController implements Initializable {
    @FXML
    private TableColumn<BookLoan, String> IDCol;

    @FXML
    private TableColumn<BookLoan, String> bookNameCol;

    @FXML
    private TableColumn<BookLoan, String> borrowerNameCol;

    @FXML
    private TableColumn<BookLoan, String> noteCol;


    @FXML
    private TableColumn<BookLoan, String> returnDateCol;

    @FXML
    private TableView<BookLoan> tableView;

    @FXML
    private TextField searchKeyword;

    @FXML
    private Label addReturnText;


    @FXML
    private Label booksListBtn;

    @FXML
    private Label borrowBtn;

    @FXML
    private Label dashboardBtn;


    @FXML
    private Label languageText;

    @FXML
    private Label logOutBtn;

    @FXML
    private Label returnBtn;


    @FXML
    private Text returnTitle;


    @FXML
    private Label userListBtn;


    private ObservableList<BookLoan> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.getColumns().clear();

        IDCol.setCellValueFactory(new PropertyValueFactory<>("bookLoanID"));
        bookNameCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        borrowerNameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
        returnDateCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        tableView.getColumns().addAll(IDCol, bookNameCol, borrowerNameCol, noteCol, returnDateCol);

        data = FXCollections.observableArrayList(

        );

        ArrayList<BookLoan> temp = SQLController.getBookLoansDataWithNoNullReturnDate();

        if (temp != null)
            for (BookLoan bookLoan : temp) {
                if (bookLoan.getReturnDate() != null) data.add(bookLoan);
            }


        tableView.setItems(data);

    }

    @FXML
    void openAddReturnRequestView(MouseEvent event) {
        try {
            // Tạo FXMLLoader và nạp file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/AddReturnRequest_View.fxml"));
            Parent secondRoot = loader.load();

            // Lấy controller từ loader và thiết lập documentArchiveController
            AddReturnRequestController addReturnRequestController = loader.getController();
            addReturnRequestController.setReturnDocumentController(this);

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

    public void addReturnRequest(BookLoan bookLoan) {
        data.add(bookLoan);
        tableView.setItems(data);
        tableView.refresh();
    }

    private void viewBookLoanDetails(BookLoan bookLoan) {

        System.out.println("Xem thông tin của phiếu mượn: " + bookLoan.getBookLoanID());
        // Thực hiện logic xem thông tin phiếu mượn ở đây
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
        returnTitle.setText("Return history");
        addReturnText.setText("Add return request");
        IDCol.setText("ID");
        bookNameCol.setText("Book Name");
        borrowerNameCol.setText("Borrower Name");
        returnDateCol.setText("Return Date");
        noteCol.setText("Note");
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
        returnTitle.setText("Lich sử trả sách");
        addReturnText.setText("Thêm YC trả sách");
        IDCol.setText("ID");
        bookNameCol.setText("Tên sách");
        borrowerNameCol.setText("Tên người mượn");
        returnDateCol.setText("Ngày trả");
        noteCol.setText("Ghi chú");
        tableView.setPlaceholder(new Label("Không có dữ liệu trong bảng"));
    }


}
