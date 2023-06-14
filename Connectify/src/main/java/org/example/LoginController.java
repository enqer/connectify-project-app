package org.example;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class LoginController {

    @FXML
    private Button loginBtn, registerBtn;

    // Pane - container of login and register layout
    @FXML
    private Pane Pane;

    // on start window
    public void initialize() throws IOException {
        showLogin();
    }



    public void showLogin () throws IOException {
        Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("logLayout.fxml"));
//        Pane snewLoadedPane =  FXMLLoader.load(getClass().getClassLoader().getResource("logLayout.fxml"));
        Pane.getChildren().clear();
        Pane.getChildren().add(newLoadedPane);
        loginBtn.setDisable(true);
        registerBtn.setDisable(false);
    }

    public void showRegister (ActionEvent event) throws IOException {
        Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("registerLayout.fxml"));
        Pane.getChildren().clear();
        Pane.getChildren().add(newLoadedPane);
        loginBtn.setDisable(false);
        registerBtn.setDisable(true);
    }
//    @FXML
//    private void switchToSecondary() throws IOException {
//        App.setRoot("chat");
//    }


}
