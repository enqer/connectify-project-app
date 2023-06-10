package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import org.example.connection.Connect;
import org.example.socket.WebSocketChatServer;


import javax.websocket.*;


public class ChatController implements Initializable {

    private Session session;
    public List<String> logins;


    @FXML
    public TextArea inputMessage;
    @FXML
    public ScrollPane showMessages;


    private List<String> persons = new ArrayList<>(Arrays.asList("John", "Alice", "Steve", "Paul", "Dupa_rozpruwacz_69420"));

    String currentPerson;

    @FXML
    private ListView<String> myListView;

    @FXML
    private Label myLabel;

    @FXML
    private Label account;

    @FXML
    private Circle status;

    @FXML
    private TextField search;

    private void showAllUsers() throws IOException {
        String zapytanie = "SELECT login FROM public.connectify";
        Connect connect = new Connect();
        Connection conn = connect.getConnection();
        ArrayList<String> logins = new ArrayList<>();
        this.logins = logins;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(zapytanie)) {

            while (rs.next()) {
                String login = rs.getString("login");
                logins.add(login);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(logins);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            showAllUsers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        myListView.getItems().addAll(persons);

        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                currentPerson = myListView.getSelectionModel().getSelectedItem();
                myLabel.setText(currentPerson);

                connectToServer("1");
            }
        });

        //status.setFill(Color.web("#1e2124"));
        //status.setFill(Color.GREEN);

    }



    @FXML
    public void searchUser() {
        String searchString = search.getText();
        if (searchString.isEmpty()) {
            System.out.println("Nothing given");
            return;
        }

        boolean found = false;
        for (String login : logins) {
            if (login.equals(searchString)) {
                System.out.println("User found: " + login);
                found = true;

                // Sprawdzenie, czy użytkownik już istnieje w liście persons
                if (!persons.contains(login)) {
                    persons.add(login);
                    myListView.getItems().setAll(persons);

                } else {
                    System.out.println("User already exists in the list");
                }
                break;
            }
        }

        if (!found) {
            System.out.println("User not found");
        }
    }


    public Label getAccountLabel() {
        return account;
    }








    @FXML
    public void sendMessageToServer() {
        if (inputMessage.getText() != null) {
            sendMessage(inputMessage.getText());
        }
    }

    private void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectToServer(String room) {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = "ws://localhost:8080/chat/" + room;
        try {
            session = container.connectToServer(new Endpoint() {
                @Override
                public void onOpen(Session session, EndpointConfig config) {
                    // Wywoływane, gdy połączenie z serwerem WebSocket zostanie otwarte
                    System.out.println("WebSocket connection opened");
                }

                @Override
                public void onClose(Session session, CloseReason closeReason) {
                    // Wywoływane, gdy połączenie z serwerem WebSocket zostanie zamknięte
                    System.out.println("WebSocket connection closed");
                }

                @Override
                public void onError(Session session, Throwable throwable) {
                    // Wywoływane, gdy wystąpi błąd w połączeniu z serwerem WebSocket
                    throwable.printStackTrace();
                }


                public void onMessage(Session session, String message) {
                    // Wywoływane, gdy otrzymano wiadomość od serwera WebSocket
                    Platform.runLater(() -> {
                        // Wyświetl otrzymaną wiadomość w interfejsie użytkownika
                        // np. w polu TextArea
//                        chatArea.appendText(message + "\n");
                        System.out.println(message);
                    });
                }
            }, new URI(uri));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}