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
/**
 * This class represents the controller for the admin panel.
 */
public class AdminController implements Initializable {

    @FXML
    Pane adminPane;
    @FXML
    AnchorPane myAnchorPane;
    @FXML
    ImageView adminAppLogo;

    /**
     * Displays the management view after clicking the button.
     *
     * @throws IOException if an error occurs while loading the view.
     */
    public void showManagement() throws IOException {
        AnchorPane newLoadedPane = FXMLLoader.load(getClass().getResource("managementLayout.fxml"));
        adminPane.getChildren().clear();
        adminPane.getChildren().add(newLoadedPane);
    }

    /**
     * Displays the statistics view after clicking the button.
     *
     * @throws IOException if an error occurs while loading the view.
     */
    public void showStats() throws IOException {
        AnchorPane newLoadedPane = FXMLLoader.load(getClass().getResource("statsLayout.fxml"));
        adminPane.getChildren().clear();
        adminPane.getChildren().add(newLoadedPane);
    }

    /**
     * Signs out the current user and switches to the login view.
     *
     * @throws Exception if an error occurs during the sign-out process.
     */
    public void signOut() throws Exception {
        Stage stage = (Stage) adminAppLogo.getScene().getWindow();
        stage.setHeight(750);
        stage.setWidth(400);
        App.setRoot("login");
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
            showManagement();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

