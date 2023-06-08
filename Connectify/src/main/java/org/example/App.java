package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        try{
            scene = new Scene(loadFXML("login"), 400, 550);
            stage.setTitle("Connectify");
            Image img = new Image(String.valueOf(this.getClass().getResource("img/logo.png")));
            stage.getIcons().add(img);
            stage.setScene(scene);
            String css = this.getClass().getResource("styles/app.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }


//        scene = new Scene(loadFXML("login"), 1280, 720);
//        //String css = this.getClass().getResource("application.css").toExternalForm();
//        //scene.getStylesheets().add(css);
//        stage.setScene(scene);
//        stage.show();

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