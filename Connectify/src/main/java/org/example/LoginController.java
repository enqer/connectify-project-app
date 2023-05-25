package org.example;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class LoginController {

    @FXML
    private Pane borderPane;
    public void showLogin (ActionEvent event) throws IOException {
        Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("logLayout.fxml"));
        borderPane.getChildren().add(newLoadedPane);
    }
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("chat");
    }


}
