package org.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ChatController implements Initializable {
    @FXML
    private ListView<String> myListView;

    @FXML
    private Label myLabel;

    String[] persons = {"John", "Alice", "Steve", "Paul", "Dupa_rozpruwacz_69420"};

    String currentPerson;
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

    }
}