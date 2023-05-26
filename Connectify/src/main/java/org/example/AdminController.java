package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private ListView<Parent> listView;

    private ArrayList<AdminUsers> userList;

    private static ObservableList<AdminUsers> users = FXCollections.observableArrayList(
            new AdminUsers("Jan", "Kowalski","Jan.kowalski@op.pl","https://static.vecteezy.com/system/resources/previews/005/005/788/original/user-icon-in-trendy-flat-style-isolated-on-grey-background-user-symbol-for-your-web-site-design-logo-app-ui-illustration-eps10-free-vector.jpg"),
            new AdminUsers("Hugo","Bonzo","hugobonzo3@gmail.com","https://t3.ftcdn.net/jpg/05/17/79/88/360_F_517798849_WuXhHTpg2djTbfNf0FQAjzFEoluHpnct.jpg"),
            new AdminUsers("Nie","wiem","nieWiem@Kto.gl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png")
    );

    private void addElement(AdminUsers user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("user_item.fxml"));
            Parent root = loader.load();
            AdminUsersListController controller = loader.getController();
            controller.setData(user);
            listView.getItems().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        listView.setItems(users);
//        listView.setCellFactory(param -> new UserListCell());
        for (AdminUsers user : users) {
            addElement(user);
        }
    }


}

