package org.example;

import java.net.URL;
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
    private Circle status;

    @FXML
    private TextField search;

    public void showUsers(List<String> logins) {
        this.logins = logins;

        System.out.println(logins);

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        myListView.getItems().addAll(persons);

        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                currentPerson = myListView.getSelectionModel().getSelectedItem();
                myLabel.setText(currentPerson);
            }
        });

        //status.setFill(Color.web("#1e2124"));
        //status.setFill(Color.GREEN);

    }

    @FXML
    public void searchUser() {
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
                }
                break;
            }
        }

        if (!found) {
            System.out.println("User not found");
        }
    }


    public Label getAccountLabel() {
        return account;
    }
}