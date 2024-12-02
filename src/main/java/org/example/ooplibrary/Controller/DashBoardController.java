package org.example.ooplibrary.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.ResourceBundle;

public class DashBoardController extends AbstractMenuController implements Initializable, AbstractLanguageConfig {

    @FXML
    private PieChart pieChart;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private NumberAxis amountText;

    @FXML
    private Label bookListBtn;

    @FXML
    private Label borrowBtn;

    @FXML
    private NumberAxis countText;

    @FXML
    private Label dashboardBtn;

    @FXML
    private Text dashboardTitle;

    @FXML
    private CategoryAxis genreText;

    @FXML
    private Label languageText;

    @FXML
    private Label logOutBtn;

    @FXML
    private CategoryAxis periodText;


    @FXML
    private AnchorPane pieChart_pane;

    @FXML
    private Label returnBtn;

    @FXML
    private Label userListBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Using Pie Chart for percents of user's ages among under 18, between 18 and 25 and over 25
        int[] ages = SQLController.estimateUserAge();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Under 18", ages[0]),
                new PieChart.Data("Between 18 and 25", ages[1]),
                new PieChart.Data("Over 25", ages[2])
        );

        pieChart.setData(pieChartData);
        pieChart.setTitle("Percent of users' age:");
        loadLineChart();
        loadBarChart();
    }

    private void loadLineChart() {
        // Tạo một Task để lấy dữ liệu giao dịch từ SQLController
        Task<int[][]> transactionsTask = SQLController.estimateTransactions();

        transactionsTask.setOnSucceeded(event -> {
            int[][] transactions = transactionsTask.getValue(); // Lấy dữ liệu

            lineChart.setTitle("Borrow and Return Transactions in Last Month");

            // Tạo các series cho dữ liệu
            XYChart.Series<String, Number> borrowSeries = new XYChart.Series<>();
            borrowSeries.setName("Borrow Transactions");

            XYChart.Series<String, Number> returnSeries = new XYChart.Series<>();
            returnSeries.setName("Return Transactions");

            // Thêm dữ liệu vào series
            for (int i = 0; i < transactions.length; i++) {
                borrowSeries.getData().add(new XYChart.Data<>("Period " + (i + 1), transactions[i][0]));  // Số lượng sách mượn
                returnSeries.getData().add(new XYChart.Data<>("Period " + (i + 1), transactions[i][1]));  // Số lượng sách trả
            }

            // Xóa dữ liệu cũ và thêm dữ liệu mới
            lineChart.getData().clear(); // Xóa dữ liệu cũ
            lineChart.getData().addAll(borrowSeries, returnSeries); // Thêm dữ liệu mới
        });

        // Chạy Task trên một luồng nền
        new Thread(transactionsTask).start();
    }


    private void loadBarChart() {
        Map<String, int[]> genreTransactions = SQLController.getTransactionsByGenres();

        barChart.setTitle("Transactions by Genre");

        XYChart.Series<String, Number> borrowSeries = new XYChart.Series<>();
        borrowSeries.setName("Borrow");

        XYChart.Series<String, Number> returnSeries = new XYChart.Series<>();
        returnSeries.setName("Return");

        for (Map.Entry<String, int[]> entry : genreTransactions.entrySet()) {
            String genre = entry.getKey();
            int[] counts = entry.getValue();

            borrowSeries.getData().add(new XYChart.Data<>(genre, counts[0]));
            returnSeries.getData().add(new XYChart.Data<>(genre, counts[1]));
        }

        barChart.getData().addAll(borrowSeries, returnSeries);
    }

    @FXML
    public void setLanguageToEn() {
        language = "en";
        languageText.setText("Language:");
        dashboardTitle.setText("Dashboard");
        dashboardBtn.setText("Dashboard");
        bookListBtn.setText("Books List");
        userListBtn.setText("User List");
        borrowBtn.setText("Borrow");
        returnBtn.setText("Return");
        logOutBtn.setText("Log Out");
        amountText.setLabel("Amount");
        periodText.setLabel("Periods");
        genreText.setLabel("Genres");
        countText.setLabel("Count");

    }

    @FXML
    public void setLanguageToVi() {
        language = "vi";
        languageText.setText("Ngôn ngữ:");
        dashboardTitle.setText("Bảng thông tin");
        dashboardBtn.setText("Bảng thông tin");
        bookListBtn.setText("DS sách");
        userListBtn.setText("DS người dùng");
        borrowBtn.setText("Mượn sách");
        returnBtn.setText("Trả sách");
        logOutBtn.setText("Đăng xuất");
        amountText.setLabel("Số lượng");
        periodText.setLabel("Giai đoạn");
        genreText.setLabel("Thể loại");
        countText.setLabel("Số lượng");


    }
}
