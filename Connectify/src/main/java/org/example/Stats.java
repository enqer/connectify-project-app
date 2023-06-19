package org.example;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import org.example.connection.Connect;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * The Stats class handles the display statistics functionality of the application.
 */
public class Stats implements Initializable {

    Connect connect = new Connect();
    Connection sql = connect.getConnection();
    Statement statement;
    @FXML
    Label numberUsers;
    @FXML
    Label numberOnline;
    @FXML
    Label numberBlocked;
    @FXML
    CategoryAxis categoryAxis = new CategoryAxis();
    @FXML
    NumberAxis numberAxis = new NumberAxis();
    @FXML
    private LineChart<String,Number> lineChart=new LineChart<String,Number>(categoryAxis,numberAxis);

    /**
     * Retrieves the number of users from the database and displays it in the user count label.
     * This method queries the database to get the count of users and updates the UI accordingly.
     *
     * @throws SQLException if an SQL exception occurs while executing the database query.
     */
    private void getNumberOfUsers() throws SQLException {
        String query = connect.countUser();
        statement = sql.createStatement();
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            int numberOfUsers = rs.getInt(1);
            numberUsers.setText(String.valueOf(numberOfUsers));
        }
    }

    /**
     * Retrieves the number of online users from the database and displays it in the online user count label.
     * This method queries the database to get the count of online users and updates the UI accordingly.
     *
     * @throws SQLException if an SQL exception occurs while executing the database query.
     */
    private void getNumberofOnlineUsers() throws SQLException {
        String query = connect.countOnlineUser();
        statement = sql.createStatement();
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            int numberOfUsers = rs.getInt(1);
            numberOnline.setText(String.valueOf(numberOfUsers));
        }
    }

    /**
     * Retrieves the number of blocked users from the database and displays it in the online user count label.
     * This method queries the database to get the count of blocked users and updates the UI accordingly.
     *
     * @throws SQLException if an SQL exception occurs while executing the database query.
     */
    private void getNumberofBlockedUsers() throws SQLException {
        String query = connect.countBlockedUser();
        statement = sql.createStatement();
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            int numberOfUsers = rs.getInt(1);
            numberBlocked.setText(String.valueOf(numberOfUsers));
        }
    }

    /**
     * Adds data to the line chart.
     * This method sets up the categories on the X-axis and adds a series with data points to the line chart.
     * The data points represent the number of messages for each day of the week.
     */
    private void addData(){
        categoryAxis.setCategories(FXCollections.observableArrayList(
                "poniedziałek", "wtorek", "środa", "czwartek", "piątek"
        ));
        XYChart.Series<String,Number> series = new XYChart.Series<>();
        series.setName("Liczba wiadomości");

        series.getData().add(new XYChart.Data<>("poniedziałek", 45));
        series.getData().add(new XYChart.Data<>("wtorek", 34));
        series.getData().add(new XYChart.Data<>("środa", 98));
        series.getData().add(new XYChart.Data<>("czwartek", 29));
        series.getData().add(new XYChart.Data<>("piątek", 90));

        lineChart.getData().add(series);

    }

    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * @param url            the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getNumberOfUsers();
            getNumberofOnlineUsers();
            getNumberofBlockedUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        addData();


    }
}
