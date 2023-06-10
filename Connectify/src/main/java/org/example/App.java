package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    private static double initialSceneWidth;
    private static double initialSceneHeight;

//    public static void setInitialSceneSize(double width, double height) {
//        initialSceneWidth = width;
//        initialSceneHeight = height;
//    }

    @Override
    public void start(Stage stage) throws IOException {
        try{
            scene = new Scene(loadFXML("login"), 400, 550);
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