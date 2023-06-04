package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.connection.Connect;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        setLoginScene(stage);
        showAllUsers();
    }

    private void showAllUsers() throws IOException {
        String zapytanie = "SELECT login FROM public.connectify";
        Connect connect = new Connect();
        Connection conn = connect.getConnection();
        ArrayList<String> logins = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(zapytanie)) {

            while (rs.next()) {
                String login = rs.getString("login");
                logins.add(login);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ChatController chatController = (ChatController) scene.getUserData();
        chatController.showUsers(logins);

    }

    public void setLoginScene(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("chat.fxml"));
        Parent chatRoot = fxmlLoader.load();
        ChatController chatController = fxmlLoader.getController();
        scene = new Scene(chatRoot, 1280, 720);
        scene.setUserData(chatController);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }


}