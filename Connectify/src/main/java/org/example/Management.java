package org.example;

import Admin.AdminUsers;
import Admin.AdminUsersListController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
    ObservableList<AdminUsers> usersList = FXCollections.observableArrayList();
    @FXML
    ImageView logourlSelectedUser;
    @FXML
    Label nameSelectedUser;
    @FXML
    Label emailSelectedUser;
    @FXML
    TextField searchUsersTextField;
    @FXML
    Button blockButton;
    private boolean isBlocked = false;

    AdminUsers a1 = new AdminUsers("Jan", "Kowalski","Jan.kowalski@op.pl","https://static.vecteezy.com/system/resources/previews/005/005/788/original/user-icon-in-trendy-flat-style-isolated-on-grey-background-user-symbol-for-your-web-site-design-logo-app-ui-illustration-eps10-free-vector.jpg");
    AdminUsers a2 = new AdminUsers("Hugo","Bonzo","hugobonzo3@gmail.com","https://t3.ftcdn.net/jpg/05/17/79/88/360_F_517798849_WuXhHTpg2djTbfNf0FQAjzFEoluHpnct.jpg");
    AdminUsers a3 = new AdminUsers("Tomek","Noga","tomek.noga@ou.pl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png");
    AdminUsers a4 = new AdminUsers("Piotr","Reka","piotrekreka@gl.fl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png");
    AdminUsers a5 =  new AdminUsers("Ola","Cztery","ola4@gmail.com","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png");
    AdminUsers a6 =  new AdminUsers("Hugo","V2","hugov2@op.pl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png");

    private static ObservableList<AdminUsers> users = FXCollections.observableArrayList(
            new AdminUsers("Jan", "Kowalski","Jan.kowalski@op.pl","https://static.vecteezy.com/system/resources/previews/005/005/788/original/user-icon-in-trendy-flat-style-isolated-on-grey-background-user-symbol-for-your-web-site-design-logo-app-ui-illustration-eps10-free-vector.jpg"),
            new AdminUsers("Hugo","Bonzo","hugobonzo3@gmail.com","https://t3.ftcdn.net/jpg/05/17/79/88/360_F_517798849_WuXhHTpg2djTbfNf0FQAjzFEoluHpnct.jpg"),
            new AdminUsers("Tomek","Noga","tomek.noga@ou.pl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png"),
            new AdminUsers("Piotr","Reka","piotrekreka@gl.fl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png"),
            new AdminUsers("Ola","Cztery","ola4@gmail.com","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png"),
            new AdminUsers("Hugo","V2","hugov2@op.pl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png")

    );
    private static ObservableList<AdminUsers> users1 = FXCollections.observableArrayList(
            new AdminUsers("Jan", "Kowalski","Jan.kowalski@op.pl","https://static.vecteezy.com/system/resources/previews/005/005/788/original/user-icon-in-trendy-flat-style-isolated-on-grey-background-user-symbol-for-your-web-site-design-logo-app-ui-illustration-eps10-free-vector.jpg"),
            new AdminUsers("Hugo","Bonzo","hugobonzo3@gmail.com","https://t3.ftcdn.net/jpg/05/17/79/88/360_F_517798849_WuXhHTpg2djTbfNf0FQAjzFEoluHpnct.jpg"),
            new AdminUsers("Tomek","Noga","tomek.noga@ou.pl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png"),
            new AdminUsers("Piotr","Reka","piotrekreka@gl.fl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png"),
            new AdminUsers("Ola","Cztery","ola4@gmail.com","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png"),
            new AdminUsers("Hugo","V2","hugov2@op.pl","https://toppng.com/uploads/preview/icons-logos-emojis-user-icon-png-transparent-11563566676e32kbvynug.png")

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
            Parent selectedUserFXML = listView.getSelectionModel().getSelectedItem();
            if (selectedUserFXML != null) {
                AdminUsers selectedUser = users.get(listView.getSelectionModel().getSelectedIndex());
                System.out.println("Wybrany użytkownik: " + selectedUser.getFirstName() + " " + selectedUser.getLastName());
                nameSelectedUser.setText(selectedUser.getFirstName()+" "+selectedUser.getLastName());
                emailSelectedUser.setText(selectedUser.getEmail());
                logourlSelectedUser.setImage(new Image(selectedUser.getAvatar()));
           }
        users.clear();
        listView.getItems().clear();
        users.addAll(a1,a2,a3,a4,a5,a6);
        for (AdminUsers user : users) {
            addElement(user);
        }
    }

    public void hideText(){
        searchUsersTextField.clear();
    }
    public void pressButton(){
            isBlocked = !isBlocked;
            updateTextButton();
    }
    public void updateTextButton(){
        if (isBlocked) {
            blockButton.setText("Odblokuj użytkownika");
        } else {
            blockButton.setText("Zablokuj użytkownika");
        }
    }
    public void searchUsers() {
            filterUsers(searchUsersTextField.getText());
    }

    private void filterUsers(String keyword) {
        System.out.println(keyword);
        if (!keyword.isEmpty() && !keyword.equals("Search user")) {
            System.out.println("working");
            ObservableList<AdminUsers> filteredList = FXCollections.observableArrayList();
            for (AdminUsers user : users) {
                if (user.getFirstName().toLowerCase().contains(keyword.toLowerCase())
                        || user.getLastName().toLowerCase().contains(keyword.toLowerCase())
                        || user.getEmail().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredList.add(user);
                }
            }
            listView.getItems().clear();
            users.clear();
            users.addAll(filteredList);
            for (AdminUsers user : users) {
                addElement(user);
            }
        }
        System.out.println("not working");
    }

    public void initialize(URL location, ResourceBundle resources) {
        searchUsersTextField.setText("Search user");
        usersList.addAll(users);
        for (AdminUsers user : usersList) {
            addElement(user);
        }
        // add first user to start menu
        AdminUsers selectedUser = users.get(0);
        nameSelectedUser.setText(selectedUser.getFirstName()+" "+selectedUser.getLastName());
        emailSelectedUser.setText(selectedUser.getEmail());
        logourlSelectedUser.setImage(new Image(selectedUser.getAvatar()));




    }

}