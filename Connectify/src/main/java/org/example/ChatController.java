package org.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ChatController implements Initializable {

    int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
    Stage stage;
    Scene scene;

    int initialX;
    int initialY;

    @FXML
    private ListView<String> myListView;

    @FXML
    private Label myLabel;

    @FXML
    private Circle status;

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

        //status.setFill(Color.web("#1e2124"));
        //status.setFill(Color.GREEN);

    }
}