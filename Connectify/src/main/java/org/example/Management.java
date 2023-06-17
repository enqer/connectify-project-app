package org.example;

import Admin.AdminUsers;
import Admin.AdminUsersListController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.example.connection.Connect;
import org.example.mail.MailSender;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Management implements Initializable {
    Connect connect = new Connect();
    Connection sql = connect.getConnection();
    Statement statement;
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
    Label loginSelectedUser;
    @FXML
    Label statusSelectedUser;
    @FXML
    Button blockButton;
    @FXML
    Circle circleStatus;
    @FXML
    TextArea contentSelectedUser;
    @FXML
    Label emptyContent;
    @FXML
    Label searchLabel;
    @FXML
    ImageView searchImage;
    AdminUsers selectedUser;
    private boolean isBlocked ;
    private String selectedUserB;

    private static ObservableList<AdminUsers> users = FXCollections.observableArrayList();

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
    public void getUserData() throws SQLException {
            Parent selectedUserFXML = listView.getSelectionModel().getSelectedItem();
            if (selectedUserFXML != null) {
                selectedUser = users.get(listView.getSelectionModel().getSelectedIndex());
                System.out.println("Wybrany użytkownik: " + selectedUser.getFirstName() + " " + selectedUser.getLastName());
                nameSelectedUser.setText(selectedUser.getFirstName()+" "+selectedUser.getLastName());
                emailSelectedUser.setText(selectedUser.getEmail());
                logourlSelectedUser.setImage(new Image(getClass().getResource("/org/example/img/"+selectedUser.getAvatar()+".png").toExternalForm()));
                loginSelectedUser.setText(selectedUser.getLogin());
                selectedUserB = selectedUser.getLogin();
                System.out.println(selectedUser.getBlocked());
                if(!selectedUser.getBlocked()) {
                    blockButton.setText("Zablokuj użytkownika");
                    isBlocked=false;
                }
                else {
                    blockButton.setText("Odblokuj użytkownika");
                    isBlocked=true;
                }
                if (selectedUser.getStatus()==true){
                    statusSelectedUser.setText("Online");
                    circleStatus.setFill(Color.GREEN);
                }
                else {
                    statusSelectedUser.setText("Offline");
                    circleStatus.setFill(Color.RED);
                }
           }
        users.clear();
        listView.getItems().clear();
        addUsers();
        for (AdminUsers user : users) {
            addElement(user);
        }
    }

    public void hideText(){
        searchUsersTextField.clear();
    }
    public void pressButton() throws SQLException {
        updateTextButton();

    }
    public void updateTextButton() throws SQLException {
        if (isBlocked) {
            String query = connect.blockUser("false",selectedUserB);
            System.out.println(selectedUserB);
            statement = sql.createStatement();
            statement.executeUpdate(query);
            blockButton.setText("Zablokuj użytkownika");
            System.out.println("Użytkownik odblokowany");
            isBlocked=false;
        } else {
            String query = connect.blockUser("true",selectedUserB);
            statement = sql.createStatement();
            statement.executeUpdate(query);
            blockButton.setText("Odblokuj użytkownika");
            System.out.println("Użytkownik zablokowany");
            isBlocked=true;
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

    public void addUsers() throws SQLException {
        try {
            String query = connect.getUsers();
            statement = sql.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String login = rs.getString("login");
                String email = rs.getString("email");
                String avatar = rs.getString("avatar");
                Boolean status = rs.getBoolean("online");
                Boolean blocked = rs.getBoolean("blocked");
                AdminUsers adminUsers = new AdminUsers(name,surname,email,avatar,login,status,blocked);
                users.add(adminUsers);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMail(){
        if(!contentSelectedUser.getText().isEmpty()) {
            MailSender mailSender = new MailSender();
            mailSender.setSender(System.getenv("EMAIL"));
            mailSender.setRecipient(selectedUser.getEmail());
            mailSender.setSubject("Connectify - wiadomość od Administratora");
            mailSender.setContent(contentSelectedUser.getText());
            mailSender.send();
        }
        else emptyContent.setText("Nie można wysłać wiadomości bez treści!");
    }
    public void hideContentStatus(){
        emptyContent.setText("");
    }

    public void initialize(URL location, ResourceBundle resources) {
        listView.getItems().clear();
        usersList.clear();
        users.clear();

        searchImage.setOnMouseEntered(event -> {
            searchLabel.setStyle("-fx-background-color: #7289da;");
        });
        searchImage.setOnMouseExited(event -> {
            searchLabel.setStyle("");
        });
        try {
            addUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        searchUsersTextField.setText("Search user");
        searchUsersTextField.setStyle("-fx-text-fill: white;");
        usersList.addAll(users);
        for (AdminUsers user : usersList) {
            addElement(user);
        }
        //add first user to start menu
        AdminUsers selectedUser = users.get(0);
        nameSelectedUser.setText(selectedUser.getFirstName()+" "+selectedUser.getLastName());
        emailSelectedUser.setText(selectedUser.getEmail());
        logourlSelectedUser.setImage(new Image(getClass().getResource("/org/example/img/"+selectedUser.getAvatar()+".png").toExternalForm()));
        loginSelectedUser.setText(selectedUser.getLogin());
        selectedUserB = selectedUser.getLogin();
        if(!selectedUser.getBlocked()) {
            blockButton.setText("Zablokuj użytkownika");
            isBlocked=false;
        }
        else {
            blockButton.setText("Odblokuj użytkownika");
            isBlocked=true;
        }
        if (selectedUser.getStatus()==true){
            statusSelectedUser.setText("Online");
            circleStatus.setFill(Color.GREEN);
        }
        else {
            statusSelectedUser.setText("Offline");
            circleStatus.setFill(Color.RED);
        }


    }

}