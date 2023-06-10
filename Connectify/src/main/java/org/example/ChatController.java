package org.example;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import org.example.connection.Connect;


public class ChatController implements Initializable {

    public List<String> logins;
    private List<String> persons = new ArrayList<>(Arrays.asList("John", "Alice", "Steve", "Paul", "Dupa_rozpruwacz_69420"));

    String currentPerson;

    @FXML
    private ListView<String> myListView;

    @FXML
    private Label myLabel;

    @FXML
    private Label account;

    @FXML
    private Label searchError;

    @FXML
    private Circle status;

    @FXML
    private TextField search;


    //status.setFill(Color.web("#1e2124"));
    //status.setFill(Color.GREEN);


    private void showAllUsers() throws IOException {
        String zapytanie = "SELECT login FROM public.connectify";
        Connect connect = new Connect();
        Connection conn = connect.getConnection();
        ArrayList<String> logins = new ArrayList<>();
        this.logins = logins;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(zapytanie)) {

            while (rs.next()) {
                String login = rs.getString("login");
                logins.add(login);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(logins);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            showAllUsers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        myListView.getItems().addAll(persons);

        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                currentPerson = myListView.getSelectionModel().getSelectedItem();
                myLabel.setText(currentPerson);
            }
        });

    }



    @FXML
    public void searchUser() {
        searchError.setText("");
        String searchString = search.getText();
        if (searchString.isEmpty()) {
            System.out.println("Nothing given");
            return;
        }

        boolean found = false;
        for (String login : logins) {
            if (login.equals(searchString)) {
                System.out.println("User found: " + login);
                found = true;

                // Sprawdzenie, czy użytkownik już istnieje w liście persons
                if (!persons.contains(login)) {
                    persons.add(login);
                    myListView.getItems().setAll(persons);

                } else {
                    System.out.println("User already exists in the list");
                    searchError.setText("Użytkownik jest już dodany!");
                }
                break;
            }
        }

        if (!found) {
            System.out.println("User not found");
            searchError.setText("Nie odnaleziono użytkownika.");
        }
    }


    public Label getAccountLabel() {
        return account;
    }
}