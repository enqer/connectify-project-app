package org.example;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.example.connection.Connect;


public class ChatController implements Initializable {
    private String username;
    public List<String> logins;
    public List<String> friends;
    private List<String> persons = new ArrayList<>(Arrays.asList("John", "Alice", "Steve", "Paul", "Dupa_rozpruwacz_69420"));
    String currentPerson;
    Connect connect = new Connect();
    Connection conn = connect.getConnection();

    @FXML
    private AnchorPane chatScene;

    @FXML
    private ListView<String> myListView;

    @FXML
    private Label myLabel;

    @FXML
    private Label account;

    @FXML
    private Label searchError;

    @FXML
    private Label statusLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Circle status;

    @FXML
    private TextField search;

    @FXML
    private Button addUsername;

    @FXML
    private Button rejectUsername;

    @FXML
    private Button deleteUsername;


    //status.setFill(Color.web("#1e2124"));
    //status.setFill(Color.GREEN);

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Platform.runLater(() -> {
            Stage stage = (Stage) chatScene.getScene().getWindow();
            centerWindowOnScreen(stage);

            addUsername.setVisible(false);
            rejectUsername.setVisible(false);
            deleteUsername.setVisible(false);
            status.setVisible(false);
            statusLabel.setVisible(false);

            myListView.getItems().addAll(persons);

            myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                    currentPerson = myListView.getSelectionModel().getSelectedItem();
                    usernameLabel.setText(currentPerson);
                    myLabel.setText("");
                    searchError.setText("");
                    addUsername.setVisible(false);
                    rejectUsername.setVisible(false);
                    deleteUsername.setVisible(true);
                    status.setVisible(true);
                    statusLabel.setVisible(true);

                }
            });
        });

        try {
            showAllUsers();
            showFriends();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void displayName(String username) {
        account.setText(username);
    }

    public void setUsername(String username) {
        this.username = username;
    }


    private void centerWindowOnScreen(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = screenBounds.getMinX() + (screenBounds.getWidth() / 2);
        double centerY = screenBounds.getMinY() + (screenBounds.getHeight() / 2);

        stage.setX(centerX - (stage.getWidth() / 2));
        stage.setY(centerY - (stage.getHeight() / 2));
    }

    private void showAllUsers() throws IOException {
        String query = connect.showUsers();
        ArrayList<String> logins = new ArrayList<>();
        this.logins = logins;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String login = rs.getString("login");
                logins.add(login);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(logins);
    }

    private void showFriends() {
        String query = connect.showFriends(username);
        ArrayList<String> friends = new ArrayList<>();
        this.friends = friends;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String friend = rs.getString("contact");
                friends.add(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(friends);

    }

    private void addFriend() {
        String friend = myLabel.getText();
        String query = connect.addFriend(username, friend);

        System.out.println(username);
        System.out.println(friend);

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, friend);
            stmt.executeUpdate();
            System.out.println("Znajomy dodany pomyślnie.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Wystąpił błąd podczas dodawania znajomego.");
        }
    }


    @FXML
    public void searchUser() {
        usernameLabel.setText("");
        searchError.setText("");
        String searchString = search.getText();
        if (searchString.isEmpty()) {
            System.out.println("Nothing given");
            return;
        }

        boolean foundUser = false; // Flaga informująca, czy znaleziono użytkownika innego niż zalogowany
        boolean foundSelf = false; // Flaga informująca, czy znaleziono samego siebie

        for (String login : logins) {
            if (login.equals(searchString)) {
                if (login.equals(username)) {
                    System.out.println("You searched yourself");
                    foundSelf = true;
                } else {
                    System.out.println("User found: " + login);
                    foundUser = true;

                    if (!persons.contains(login)) {
                        myLabel.setText(login);

                        addUsername.setVisible(true);
                        rejectUsername.setVisible(true);
                        deleteUsername.setVisible(false);
                        status.setVisible(false);
                        statusLabel.setVisible(false);

                    } else {
                        System.out.println("User already exists in the list");
                        searchError.setText("Użytkownik jest już dodany!");
                    }
                }
                break;
            }
        }

        if (foundSelf && !foundUser) {
            searchError.setText("Tak, to Ty.");
        } else if (!foundUser) {
            System.out.println("User not found");
            searchError.setText("Nie odnaleziono użytkownika.");
        }
    }

    private void visibleElements() {
        addUsername.setVisible(false);
        rejectUsername.setVisible(false);
        deleteUsername.setVisible(false);
        status.setVisible(false);
        statusLabel.setVisible(false);
    }

    @FXML
    public void addUser() {
        searchError.setText("");
        String add = myLabel.getText();

        if (!persons.contains(add)) {
            myLabel.setText("");

            addFriend();
            persons.add(add);
            myListView.getItems().setAll(persons);

            visibleElements();

        } else {
            System.out.println("User already exists in the list");
            searchError.setText("Użytkownik jest już dodany!");
        }
    }

    @FXML
    public void rejectUser() {
        searchError.setText("");
        String remove = myLabel.getText();

        if (!persons.contains(remove)) {
            myLabel.setText("");

            visibleElements();

        } else {
            System.out.println("User already exists in the list");
            searchError.setText("Użytkownik jest już dodany!");
        }
    }

    @FXML
    public void deleteUser() {
        String delete = usernameLabel.getText();

        if (persons.contains(delete)) {
            persons.remove(delete);
            myListView.getItems().setAll(persons);

            visibleElements();

        }
    }

}