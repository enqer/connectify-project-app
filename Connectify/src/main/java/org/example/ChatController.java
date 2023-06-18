package org.example;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Message.UserMessage;
import Message.UserMessageController;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.example.client.Client;
import org.example.connection.Connect;


public class ChatController implements Initializable {

    Client client;
    private String username;
    public List<String> logins;
    private List<String> persons = new ArrayList<>();
    String currentPerson;
    Connect connect = new Connect();
    Connection conn = connect.getConnection();
    private static ObservableList<UserMessage> messages = FXCollections.observableArrayList();

    @FXML
    private AnchorPane chatScene;

    @FXML
    private ListView<String> myListView;

    @FXML
    private ListView listViewMessage;

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
    private Label nameOfUser;

    @FXML
    private Label surnameOfUser;

    @FXML
    private Label emailOfUser;

    @FXML
    private Label dateOfUser;

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

    @FXML
    private TextField sendTextField;

    @FXML
    private ImageView userPicture;

    @FXML
    private ImageView accountPicture;

    /**
     * Initializes the controller class.
     * @param arg0 The URL location used to resolve relative paths for the root object, or null if the location is not known.
     * @param arg1 The resources used to localize the root object, or null if the root object was not localized.
     */
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

            userPicture.setVisible(false);
            nameOfUser.setVisible(false);
            surnameOfUser.setVisible(false);
            emailOfUser.setVisible(false);
            dateOfUser.setVisible(false);

            myListView.getItems().addAll(persons);

            myListView.setCellFactory(param -> new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserItem.fxml"));
                            AnchorPane userItem = loader.load();
                            UserItemController controller = loader.getController();

                            String query = connect.userLook("login");
                            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                                stmt.setString(1, item);
                                ResultSet rs = stmt.executeQuery();
                                if (rs.next()) {
                                    String login = rs.getString("login");
                                    String avatar = rs.getString("avatar");

                                    // Setting up user data
                                    controller.setName(login);

                                    // Setting the image path
                                    String imagePath = getClass().getResource("/org/example/img/" + avatar + ".png").toExternalForm();
                                    controller.setLogo(imagePath);

                                    // Adding CSS style for the selected element
                                    userItem.styleProperty().bind(Bindings.when(selectedProperty())
                                            .then("-fx-background-color: #36393e")
                                            .otherwise("-fx-background-color: transparent;"));

                                    setGraphic(userItem);

                                }
                            }
                        } catch (IOException | SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                /**
                 * Called when the selection in the ListView changes.
                 * @param arg0 The observable value holding the new selected item.
                 * @param arg1 The previous selected item.
                 * @param arg2 The new selected item.
                 */
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

                    userPicture.setVisible(true);
                    nameOfUser.setVisible(true);
                    surnameOfUser.setVisible(true);
                    emailOfUser.setVisible(true);
                    dateOfUser.setVisible(true);

                    giveAllData(currentPerson);
                }
            });
        });

        try {
            showAllUsers();
            Runtime.getRuntime().addShutdownHook(new Thread(this::cleanup));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        Date currentDate = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//        String formattedTime = dateFormat.format(currentDate);
//        messages.add(new UserMessage("Serwer",formattedTime.toString(),"batman","Witaj! Możesz zacząć rozmowę."));
//        for (UserMessage userMessage: messages) {
//            addElement(userMessage);
//        }
        String serverAddress = "localhost"; // Adres serwera
        int serverPort = 12345; // Numer portu serwera
        String name = "marek"; // Nazwa klienta

        client = new Client(serverAddress, serverPort, "olekzmorek300", "breaking-bad", listViewMessage);
        client.start();
//        client.writer.println("olekzmorek300");
    }

    /**
     * Retrieves and displays all data associated with a specific person.
     * The data includes their name, surname, email, date of birth, avatar, and online status.
     * @param person The login of the person whose data is to be retrieved and displayed.
     */
    private void giveAllData(String person) {
        System.out.println("\ngiveAllData / Clicked person: " + person);

        String query = connect.giveAllData(person);
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, person);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                String dateOfBirth = rs.getString("date_of_birth");
                String avatar = rs.getString("avatar");
                boolean online = rs.getBoolean("online");

                nameOfUser.setText("Imię: " + name);
                surnameOfUser.setText("Nazwisko: " + surname);
                emailOfUser.setText("Email: " + email);
                dateOfUser.setText("Data ur.: " + dateOfBirth);

                String imagePath = getClass().getResource("/org/example/img/" + avatar + ".png").toString();
                Image image = new Image(imagePath);

                userPicture.setImage(image);

                if (online) {
                    status.setFill(Color.GREEN);
                } else {
                    status.setFill(Color.RED);
                }

            } else {
                System.out.println("giveAllData / No record found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void addElement(UserMessage userMessage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("singleMessage.fxml"));
            Parent root = loader.load();
            UserMessageController controller = loader.getController();
            controller.setData(userMessage);
            listViewMessage.getItems().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMessage() {
        String message = sendTextField.getText();
//        messages.add(0,new UserMessage("marek","22:00","batman",message));
//        UserMessage userMessage = messages.get(0);
//        addElement(userMessage);
        client.writer.println(message);
    }

    /**
     * Displays the logo associated with the user's account.
     * The logo is retrieved from the database based on the user's login.
     */
    private void showYourLogo() {
        String login = account.getText();
        String query = connect.userLook(login);

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String avatar = rs.getString("avatar");

                String imagePath = getClass().getResource("/org/example/img/" + avatar + ".png").toString();
                Image image = new Image(imagePath);
                accountPicture.setImage(image);

            } else {
                System.out.println("showYourLogo / No record found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the username and sets the online status to true.
     * @param username The username to be displayed.
     */
    public void displayName(String username) {
        account.setText(username);
        showYourLogo();
        onlineOffline(true);
    }

    /**
     * Sets the username for the current user.
     * @param username The username to be set.
     */
    public void setUsername(String username) {
        this.username = username;
        showFriends();
    }

    /**
     * Centers the window on the screen.
     * @param stage The Stage object representing the window.
     */
    private void centerWindowOnScreen(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = screenBounds.getMinX() + (screenBounds.getWidth() / 2);
        double centerY = screenBounds.getMinY() + (screenBounds.getHeight() / 2);

        stage.setX(centerX - (stage.getWidth() / 2));
        stage.setY(centerY - (stage.getHeight() / 2));
    }

    /**
     * Retrieves and displays all users from the database.
     * It populates the 'logins' list with the retrieved logins.
     * @throws IOException If an I/O error occurs.
     */
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
        System.out.println("showAllUsers / All logins in database: " + logins);
    }

    /**
     * Retrieves and displays the friends of the user.
     * It also updates the 'persons' list with the retrieved friends.
     */
    private void showFriends() {
        showAllFriends();
        String query = connect.showFriends(username);
        List<String> friends = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String friend = rs.getString("contact_login");
                friends.add(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        persons = friends;
        System.out.println("showFriends / All your friends: " + persons);
    }

    /**
     * Retrieves and displays all the friends of the user.
     */
    private void showAllFriends() {
        String query = connect.showAllFriends();
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("showAllFriends:");
            while (rs.next()) {
                String user = rs.getString("user_login");
                String contact = rs.getString("contact_login");

                System.out.println("User: " + user + ", Contact: " + contact);
            }
            System.out.println("-------------------");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("showAllFriends / An error occurred while displaying contacts");
        }
    }

    /**
     * Adds a friend to the user's friend list.
     * @param friend The login of the friend to be added.
     * @throws SQLException If an SQL exception occurs.
     */
    private void addFriend(String friend) throws SQLException {
        String query = connect.addFriend("user_login", "friend_login");

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, friend);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("addFriend / Friend added successfully");
            } else {
                System.out.println("addFriend / Failed to add a friend");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("addFriend / An error occurred while adding a friend");
        }
    }

    /**
     * Deletes a friend from the user's friend list.
     * @param friend The login of the friend to be deleted.
     */
    private void deleteFriend(String friend) {
        String query = connect.deleteFriend("user_login", "friend_login");

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, friend);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("deleteFriend / Friend removed successfully");
            } else {
                System.out.println("deleteFriend / Failed to remove a friend");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("deleteFriend / An error occurred while deleting a friend");
        }
    }

    /**
     * Searches for a user based on the entered search string.
     * If the search string is empty, it does nothing.
     * If the searched user is the logged-in user, it displays a message.
     * If the searched user is found and not already in the list, it allows adding the user to the list.
     * If the searched user is already in the list, it displays an error message.
     */
    @FXML
    public void searchUser() {
        usernameLabel.setText("");
        searchError.setText("");
        String searchString = search.getText();
        if (searchString.isEmpty()) {
            System.out.println("searchUser / Nothing given");
            return;
        }

        boolean foundUser = false; // Flaga informująca, czy znaleziono użytkownika innego niż zalogowany
        boolean foundSelf = false; // Flaga informująca, czy znaleziono samego siebie

        for (String login : logins) {
            if (login.equals(searchString)) {
                if (login.equals(username)) {
                    System.out.println("searchUser / You searched yourself");
                    foundSelf = true;
                } else {
                    System.out.println("searchUser / User found: " + login);
                    foundUser = true;

                    if (!persons.contains(login)) {
                        myLabel.setText(login);

                        addUsername.setVisible(true);
                        rejectUsername.setVisible(true);
                        deleteUsername.setVisible(false);
                        status.setVisible(false);
                        statusLabel.setVisible(false);

                        userPicture.setVisible(false);
                        nameOfUser.setVisible(false);
                        surnameOfUser.setVisible(false);
                        emailOfUser.setVisible(false);
                        dateOfUser.setVisible(false);

                    } else {
                        System.out.println("searchUser / User already exists in the list");
                        searchError.setText("Użytkownik jest już dodany!");
                    }
                }
                break;
            }
        }

        if (foundSelf && !foundUser) {
            searchError.setText("Tak, to Ty.");
        } else if (!foundUser) {
            System.out.println("searchUser / User not found");
            searchError.setText("Nie odnaleziono użytkownika.");
        }
    }

    /**
     * Hides the visibility of certain elements.
     */
    private void visibleElements() {
        addUsername.setVisible(false);
        rejectUsername.setVisible(false);
        deleteUsername.setVisible(false);
        status.setVisible(false);
        statusLabel.setVisible(false);

        userPicture.setVisible(false);
        nameOfUser.setVisible(false);
        surnameOfUser.setVisible(false);
        emailOfUser.setVisible(false);
        dateOfUser.setVisible(false);
    }

    /**
     * Adds the selected user to the list.
     * If the user already exists in the list, it displays an error message.
     * @throws SQLException if an SQL exception occurs during the database operation.
     */
    @FXML
    public void addUser() throws SQLException {
        searchError.setText("");
        String add = myLabel.getText();

        if (!persons.contains(add)) {
            myLabel.setText("");

            addFriend(add);
            persons.add(add);
            myListView.getItems().setAll(persons);

            visibleElements();

        } else {
            System.out.println("addUser / User already exists in the list");
            searchError.setText("Użytkownik jest już dodany!");
        }
    }

    /**
     * Rejects the selected user.
     * If the user does not exist in the list, it clears the label and hides certain elements.
     * If the user already exists in the list, it displays an error message.
     */
    @FXML
    public void rejectUser() {
        searchError.setText("");
        String remove = myLabel.getText();

        if (!persons.contains(remove)) {
            myLabel.setText("");

            visibleElements();

        } else {
            System.out.println("rejectUser / User already exists in the list");
            searchError.setText("Użytkownik jest już dodany!");
        }
    }

    /**
     * Deletes the selected user from the list.
     * If the user exists in the list, it removes the user, updates the list view,
     * deletes the friend relationship, and hides certain elements.
     * If the user is not found in the list, it displays an error message.
     */
    @FXML
    public void deleteUser() {
        String delete = usernameLabel.getText();

        if (persons.contains(delete)) {
            persons.remove(delete);
            myListView.getItems().setAll(persons);

            deleteFriend(delete);
            visibleElements();
        } else {
            System.out.println("deleteUser / User has already been removed from the list");
            searchError.setText("Użytkownik został już usunięty.");
        }
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        cleanup();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        Image img = new Image(String.valueOf(this.getClass().getResource("img/logo.png")));
        stage.getIcons().add(img);
        stage.setTitle("Connectify");
        stage.setWidth(400);
        stage.setHeight(750);
        stage.setScene(new Scene(root));
        stage.show();

        // Zamknięcie bieżącego okna logowania
        Stage currentStage = (Stage) myLabel.getScene().getWindow();
        currentStage.close();

    }

    /**
     * Sets the online status for the specified user.
     * @param status The online status to be set (true for online, false for offline).
     */
    private void onlineOffline(boolean status){
        String login = account.getText();
        String query = connect.onlineOffline(status, login);

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, status);
            stmt.setString(2, login);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("onlineOffline / Online status set to " + status + " for login: " + login);
            } else {
                System.out.println("onlineOffline / No rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the database connection and sets the online status to false for the current user.
     * It is recommended to call this method before closing the application.
     */
    private void cleanup() {
        onlineOffline(false);

        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}