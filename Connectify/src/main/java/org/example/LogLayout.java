package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.App;
import org.example.ChatController;
import org.example.connection.Connect;
import org.example.mail.MailSender;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jasypt.util.text.AES256TextEncryptor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

public class LogLayout {

    Connect connect = new Connect();
    Connection sql = connect.getConnection();
    Statement statement;

    @FXML
    private Label loginInfo;
    @FXML
    private TextField loginLog;
    @FXML
    private PasswordField passwordLog;

    @FXML
    private Label helperInfo;
    @FXML
    private TextField emailHelper;
    @FXML
    private Button btnHelp;

    @FXML
    private Label helperInfoText, loginTitle;

    private void initialize(){
//        loginTitle.se
    }
    @FXML
    private void loginUser() throws IOException {
        if (adminLogin()){
            switchToPanel();
        }
        if (isCorrectPassword()){
            loginInfo.setText("Zalogowano!");
            switchToChat(loginLog.getText().toLowerCase());
        } else {
            loginInfo.setText("Niepoprawny login lub hasło!");
        }
    }

    private void switchToPanel() throws IOException {
        Stage stage = (Stage) loginInfo.getScene().getWindow();
        stage.setHeight(720);
        stage.setWidth(900);
        App.setRoot("admin");
    }


    private Boolean isCorrectPassword(){
        try{
            String result = null;
            String query = connect.checkLoginPassword(loginLog.getText().toLowerCase());
            statement = sql.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                result = rs.getString("password");
                System.out.println(result);
            }
            if (result != null && passwordLog.getText().equals(checkPassword(result)))
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    private boolean checkCorrectEmail(){
        Boolean result = true;
        try {
            String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            if (!(Pattern.matches(regex, emailHelper.getText())))
                return false;
            String query = connect.checkUniqueEmail(emailHelper.getText());
            statement = sql.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                result = rs.getBoolean("exists");
                System.out.println(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @FXML
    private void switchToChat(String username) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
        Parent root = loader.load();
        ChatController chatController = loader.getController();
        chatController.displayName(username);
        chatController.setUsername(username);


        Stage stage = new Stage();
        Image img = new Image(String.valueOf(this.getClass().getResource("img/logo.png")));
        stage.getIcons().add(img);
        stage.setTitle("Connectify - Chat Window");
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setScene(new Scene(root));
        stage.show();

        // Zamknięcie bieżącego okna logowania
        Stage currentStage = (Stage) loginInfo.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void showHelp(){
        helperInfo.setVisible(true);
        emailHelper.setVisible(true);
        btnHelp.setVisible(true);
    }

    @FXML
    private void sendHelp(){
        if (checkCorrectEmail()){
            String pass = passwordHelper();
            if (pass != null){
                MailSender mailSender = new MailSender();
                mailSender.setSender(System.getenv("EMAIL"));
                mailSender.setRecipient(emailHelper.getText());
                mailSender.setSubject("Connectify - przypomnienie hasła!");
                mailSender.setContent("Twoje hasło to: "+ checkPassword(pass));
                mailSender.send();
                helperInfo.setText("Hasło zostało wysłane na twoją pocztę email.");
            }else {
                helperInfo.setText("Ups, coś poszło nie tak, spróbuj ponownie później.");
            }
        }else {
            helperInfo.setText("Nieprawidłowy email lub brak konta o takim emailu.");
        }
    }

    protected String passwordHelper(){
        String result = null;
        System.out.println();
        try{
            String query = connect.passwordHelper(emailHelper.getText());
            statement = sql.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                result = rs.getString("password");
                System.out.println(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    protected Boolean adminLogin(){
        return loginLog.getText().equals(System.getenv("ALOGIN")) && passwordLog.getText().equals(System.getenv("APASSWORD"));
    }
    public static String checkPassword(String encryptedStoredPassword) {
//        StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
//        return encryptor.checkPassword(inputPassword, encryptedStoredPassword);
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(System.getenv("PASS"));
//        String myEncryptedText = textEncryptor.encrypt(inputPassword);
        return textEncryptor.decrypt(encryptedStoredPassword);
    }
}