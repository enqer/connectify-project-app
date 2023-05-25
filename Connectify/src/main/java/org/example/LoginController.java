package org.example;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class LoginController {

    // Pane - container of login and register layout
    @FXML
    private Pane Pane;



    public void showLogin (ActionEvent event) throws IOException {
        Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("logLayout.fxml"));
        Pane.getChildren().clear();
        Pane.getChildren().add(newLoadedPane);
    }

    public void showRegister (ActionEvent event) throws IOException {
        Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("registerLayout.fxml"));
        Pane.getChildren().clear();
        Pane.getChildren().add(newLoadedPane);
    }
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("chat");
    }


}
