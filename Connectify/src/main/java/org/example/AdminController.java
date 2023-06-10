package org.example;

import Admin.AdminUsers;
import Admin.AdminUsersListController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    Pane adminPane;
    @FXML
    AnchorPane myAnchorPane;


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showManagement();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

