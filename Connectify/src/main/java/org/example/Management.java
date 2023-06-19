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

/**
 * The Management class handles the management functionality of the application.
 */
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

    /**
     * Adds the AdminUsers object as a separate item to the list view.
     *
     * @param user The AdminUsers object to be added.
     */
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

    /**
     * Retrieves and displays user data based on the selected item in the list view.
     * This method updates the UI elements with the selected user's information.
     * It also updates the user list and refreshes the list view.
     *
     * @throws SQLException If there is an error retrieving user data from the database.
     */
    public void getUserData() throws SQLException {
            Parent selectedUserFXML = listView.getSelectionModel().getSelectedItem();
            if (selectedUserFXML != null) {
                selectedUser = users.get(listView.getSelectionModel().getSelectedIndex());
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
        users.clear();
        listView.getItems().clear();
        addUsers();
        for (AdminUsers user : users) {
            addElement(user);
        }
    }

    /**
     * This method clears the textview when the search is done
     */
    public void hideText(){
        searchUsersTextField.clear();
    }

    /**
     * Event handler for button press action.
     * This method updates the text of the button.
     *
     * @throws SQLException If there is an error updating the text.
     */
    public void pressButton() throws SQLException {
        updateTextButton();
    }

    /**
     * Updates the text of the button based on the current block status.
     * If the user is blocked, the button text is updated to unblock the user and perform related actions.
     * If the user is not blocked, the button text is updated to block the user and perform related actions.
     *
     * @throws SQLException If there is an error updating the button text or performing database operations.
     */
    public void updateTextButton() throws SQLException {
        if (isBlocked) {
            String query = connect.blockUser("false",selectedUserB);
            System.out.println(selectedUserB);
            statement = sql.createStatement();
            statement.executeUpdate(query);
            blockButton.setText("Zablokuj użytkownika");
            System.out.println("Użytkownik odblokowany");
            String query2 = connect.setOffline(selectedUserB);
            statement.executeUpdate(query2);
            isBlocked=false;
        } else {
            String query = connect.blockUser("true",selectedUserB);
            statement = sql.createStatement();
            statement.executeUpdate(query);
            blockButton.setText("Odblokuj użytkownika");
            System.out.println("Użytkownik zablokowany");
            isBlocked=true;
            sendMailToBlockedUser();
        }
    }

    /**
     * Searches for users based on the entered search text and filters the user list accordingly.
     * The search is performed using the text entered in the search bar.
     */
    public void searchUsers() {
            filterUsers(searchUsersTextField.getText());
    }

    /**
     * Search the user list based on the provided keyword.
     * Only users whose first name, last name, or email contains the keyword will be included in the filtered list.
     * The filtered list is then displayed in the listView.
     *
     * @param keyword The keyword to search for in user names and email addresses.
     */
    private void filterUsers(String keyword) {
        System.out.println(keyword);
        if (!keyword.isEmpty() && !keyword.equals("Search user")) {
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
    }

    /**
     * Retrieves user data from the database and populates the users list.
     * Each user is represented by an instance of AdminUsers and added to the users list.
     *
     * @throws SQLException If an SQL exception occurs while retrieving user data from the database.
     */
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

    /**
     * Sends an email to the selected user with the content provided in the text area.
     * The email is sent using the MailSender utility class, which handles the email sending process.
     * If the content is empty, an error message is displayed.
     */
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

    /**
     * Sends an email to the blocked user with a notification message.
     */
    private void sendMailToBlockedUser(){
        MailSender mailSender = new MailSender();
        mailSender.setSender(System.getenv("EMAIL"));
        mailSender.setRecipient(selectedUser.getEmail());
        mailSender.setSubject("Connectify - wiadomość od Administratora");
        mailSender.setContent("Zostałeś zablokowany!");
        mailSender.send();
    }

    /**
     * Clears the content status label, hiding any previous error or informational message.
     */
    public void hideContentStatus(){
        emptyContent.setText("");
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * @param location  the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources the resources used to localize the root object, or null if the root object was not localized.
     */
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