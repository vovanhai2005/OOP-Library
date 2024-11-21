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
import javafx.stage.Stage;
import org.example.ooplibrary.Object.Book;
import org.example.ooplibrary.Object.BookLoan;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BorrowManagementController extends AbstractMenuController implements Initializable {

    @FXML
    private TableColumn<BookLoan, String> IDCol;

    @FXML
    private TableColumn<BookLoan, String> bookNameCol;

    @FXML
    private TableColumn<BookLoan, String> borrowerNameCol;

    @FXML
    private TableColumn<BookLoan, String> dueDateCol;

    @FXML
    private TableColumn<BookLoan, Void> featureCol;

    @FXML
    private TableColumn<BookLoan, String> informationCol;

    @FXML
    private TableView<BookLoan> tableView;

    @FXML
    private TextField searchKeyword;

    private ObservableList<BookLoan> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.getColumns().clear();

        IDCol.setCellValueFactory(new PropertyValueFactory<>("bookLoanID"));
        bookNameCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        borrowerNameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        informationCol.setCellValueFactory(new PropertyValueFactory<>("note"));



        // Cấu hình cột featureCol với các nút tuỳ chỉnh
        addFeatureButtonsToTable();


        tableView.getColumns().addAll(IDCol, bookNameCol, borrowerNameCol, dueDateCol, informationCol, featureCol);

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
        System.out.println("Request Borrow Window Opened");
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

    private void addFeatureButtonsToTable() {
        featureCol.setCellFactory(param -> new TableCell<BookLoan, Void>() {
            private final Button viewButton = new Button("Xem");

            {
                // Xử lý sự kiện khi nhấn vào nút "Xem"
                viewButton.setOnAction(event -> {
                    BookLoan bookLoan = getTableView().getItems().get(getIndex());
                    viewBookLoanDetails(bookLoan);
                });


            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonsBox = new HBox(5, viewButton);
                    setGraphic(buttonsBox);
                }
            }
        });
    }

    private void viewBookLoanDetails(BookLoan bookLoan) {

        System.out.println("Xem thông tin của phiếu mượn: " + bookLoan.getBookLoanID());
        // Thực hiện logic xem thông tin phiếu mượn ở đây
    }


}
