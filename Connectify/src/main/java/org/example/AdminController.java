package org.example;

import Admin.AdminUsers;
import Admin.AdminUsersListController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    Pane adminPane;
    @FXML
    AnchorPane myAnchorPane;
    @FXML
    ImageView adminAppLogo;


    public void showManagement() throws IOException {
        AnchorPane newLoadedPane = FXMLLoader.load(getClass().getResource("managementLayout.fxml"));
        adminPane.getChildren().clear();
        adminPane.getChildren().add(newLoadedPane);
    }
    public void showStats() throws IOException {
        AnchorPane newLoadedPane = FXMLLoader.load(getClass().getResource("statsLayout.fxml"));
        adminPane.getChildren().clear();
        adminPane.getChildren().add(newLoadedPane);
    }
    public void signOut() throws Exception {
        Stage stage = (Stage) adminAppLogo.getScene().getWindow();
        stage.setHeight(750);
        stage.setWidth(400);
        App.setRoot("login");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showManagement();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        adminAppLogo.setOnMouseEntered(event -> {
            adminAppLogo.setCursor(Cursor.HAND);
        });

        // Przywrócenie domyślnego kursora myszki
        adminAppLogo.setOnMouseExited(event -> {
            adminAppLogo.setCursor(Cursor.DEFAULT);
        });
    }
}

