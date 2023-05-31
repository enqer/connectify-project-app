package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    private Stage primaryStage; // Deklaracja zmiennej primaryStage

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        //setLoginScene(primaryStage);
        setChatScene(primaryStage);
        primaryStage.show();
    }

    public void setLoginScene(Stage stage) throws IOException {
        Parent loginRoot = loadFXML("login");
        Scene loginScene = new Scene(loginRoot, 400, 550);
        String css = this.getClass().getResource("styles/app.css").toExternalForm();
        loginScene.getStylesheets().add(css);
        stage.setScene(loginScene);
    }

    public void setChatScene(Stage stage) throws IOException {
        Parent chatRoot = loadFXML("chat");
        Scene chatScene = new Scene(chatRoot, 1280, 720);
        String css = this.getClass().getResource("styles/chat.css").toExternalForm();
        chatScene.getStylesheets().add(css);
        stage.setScene(chatScene);
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
