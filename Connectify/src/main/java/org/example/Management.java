package org.example;

import Admin.AdminUsers;
import Admin.AdminUsersListController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Management implements Initializable {
    @FXML
    private ListView<Parent> listView;
    @FXML
    ImageView logourlSelectedUser;
    @FXML
    Label nameSelectedUser;
    @FXML
    Label emailSelectedUser;
    @FXML
    TextField searchUsersTextField;
    private ArrayList<AdminUsers> userList;

    private static ObservableList<AdminUsers> users = FXCollections.observableArrayList(
            new AdminUsers("Jan", "Kowalski","Jan.kowalski@op.pl","https://static.vecteezy.com/system/resources/previews/005/005/788/original/user-icon-in-trendy-flat-style-isolated-on-grey-background-user-symbol-for-your-web-site-design-logo-app-ui-illustration-eps10-free-vector.jpg"),
            new AdminUsers("Hugo","Bonzo","hugobonzo3@gmail.com","https://t3.ftcdn.net/jpg/05/17/79/88/360_F_517798849_WuXhHTpg2djTbfNf0FQAjzFEoluHpnct.jpg"),
            new AdminUsers("Nie","wiem","nieWiem@Kto.gl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png"),
            new AdminUsers("Nie","wiem","nieWiem@Kto.gl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png"),
            new AdminUsers("Nie","wiem","nieWiem@Kto.gl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png"),
            new AdminUsers("Nie","wiem","nieWiem@Kto.gl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png"),
            new AdminUsers("Nie","wiem","nieWiem@Kto.gl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png"),
            new AdminUsers("Nie","wiem","nieWiem@Kto.gl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png"),
            new AdminUsers("Nie","wiem","nieWiem@Kto.gl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png"),
            new AdminUsers("Nie","wiem","nieWiem@Kto.gl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png"),
            new AdminUsers("Nie","wiem","nieWiem@Kto.gl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png"),
            new AdminUsers("Nie","wiem","nieWiem@Kto.gl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png"),
            new AdminUsers("Nie","wiem","nieWiem@Kto.gl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png"),
            new AdminUsers("Ja","wiem","nieWiem@Kto.gl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png")
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

    //get user data from ListView
    public void getUserData() {
        listView.setOnMouseClicked(event -> {
            Parent selectedUserFXML = listView.getSelectionModel().getSelectedItem();
            if (selectedUserFXML != null) {
                AdminUsers selectedUser = users.get(listView.getSelectionModel().getSelectedIndex());
                System.out.println("Wybrany użytkownik: " + selectedUser.getFirstName() + " " + selectedUser.getLastName() + selectedUser.getAvatar());
                nameSelectedUser.setText(selectedUser.getFirstName()+" "+selectedUser.getLastName());
                emailSelectedUser.setText(selectedUser.getEmail());
                logourlSelectedUser.setImage(new Image(selectedUser.getAvatar()));
           }
        });
    }

    public void hideText(){
        searchUsersTextField.clear();
    }

    public void initialize(URL location, ResourceBundle resources) {

        searchUsersTextField.setText("Search user");
        for (AdminUsers user : users) {
            addElement(user);
        }

    }

}