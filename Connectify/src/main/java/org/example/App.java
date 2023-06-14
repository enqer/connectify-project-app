package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    private static double initialSceneWidth;
    private static double initialSceneHeight;


    @Override
    public void start(Stage stage) {
        try{

            scene = new Scene(loadFXML("login"), 400,700);
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
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));

        if (initialSceneWidth > 0 && initialSceneHeight > 0) {
            Stage stage = (Stage) scene.getWindow();
            stage.setWidth(initialSceneWidth);
            stage.setHeight(initialSceneHeight);


        }

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}