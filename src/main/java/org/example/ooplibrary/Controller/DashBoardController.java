package org.example.ooplibrary.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class DashBoardController extends AbstractMenuController implements Initializable {

    @FXML
    private PieChart pieChart;

    @FXML
    private LineChart lineChart;

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

        // Using 2-Line Chart for percents of borrow and return transactions in the past 12 months
        int[][] transactions = SQLController.estimateTransactions();

        lineChart.setTitle("Total transactions in last month");
        XYChart.Series<String, Number> borrowSeries = new XYChart.Series<>();
        borrowSeries.setName("Borrow Transactions");

        XYChart.Series<String, Number> returnSeries = new XYChart.Series<>();
        returnSeries.setName("Return Transactions");

        for (int i = 0; i < 6; i++) {
            borrowSeries.getData().add(new XYChart.Data<>("Period " + (i + 1), transactions[i][0]));
            returnSeries.getData().add(new XYChart.Data<>("Period " + (i + 1), transactions[i][1]));
        }

        lineChart.getData().addAll(borrowSeries , returnSeries);

        // Using Bar Chart for
    }

}
