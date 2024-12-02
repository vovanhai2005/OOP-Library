package org.example.ooplibrary.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.ooplibrary.Object.BookLoan;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainMenuController extends AbstractMenuController implements AbstractLanguageConfig, Initializable {

    @FXML
    private Label bookListBtn;

    @FXML
    private Label borrowBtn;

    @FXML
    private Label dashboardBtn;

    @FXML
    private Label logOutBtn;

    @FXML
    private Label returnBtn;

    @FXML
    private Label userListBtn;

    @FXML
    private ImageView logo;

    @FXML
    private Label languageBtn;

    @FXML
    private Text lms;

    @FXML
    private Text bookCount;

    @FXML
    private Text userCount;

    @FXML
    private Text transactionsCount;

    public void initialize(URL url , ResourceBundle rb) {
        bookCount.setText(String.valueOf(getBooksCount()));
        userCount.setText(String.valueOf(getUsersCount()));
        transactionsCount.setText(String.valueOf(getBorrowReturnCount()));

    }

    @FXML
    public void setLanguageToEn() {
        language = "en";
        bookListBtn.setText("Book List");
        borrowBtn.setText("Borrow");
        dashboardBtn.setText("Dashboard");
        logOutBtn.setText("Log Out");
        returnBtn.setText("Return");
        userListBtn.setText("User List");
        languageBtn.setText("Language:");
        lms.setText("LIBRARY MANAGEMENT SYSTEM");
    }

    @FXML
    public void setLanguageToVi() {
        language = "vi";
        bookListBtn.setText("DS Sách");
        borrowBtn.setText("Mượn sách");
        dashboardBtn.setText("Bảng thông tin");
        logOutBtn.setText("Đăng xuất");
        returnBtn.setText("Trả sách");
        userListBtn.setText("Dsach người dùng");
        languageBtn.setText("Ngôn ngữ:");
        lms.setText("HỆ THỐNG QUẢN LÝ THƯ VIỆN");
    }

    private int getBooksCount() {
        return SQLController.getBookCount();
    }

    private int getUsersCount() {
        return SQLController.getUserCount();
    }

    private int getBorrowReturnCount() {
        ArrayList<BookLoan> bookLoans =  SQLController.getBookLoansData();
        int count = 0;
        for (BookLoan bookLoan : bookLoans) {
            if (bookLoan.getReturnDate() != null) {
                count++;
            }
            ++count;
        }
        return count;
    }

}