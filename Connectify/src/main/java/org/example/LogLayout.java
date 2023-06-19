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



    /**
     * Method is based on log in to chat.
     * Checking how is going to log in and after changing to new window.
     * @throws IOException if layout is not gonna works throws an exception.
     */
    @FXML
    private void loginUser() throws IOException {
        if (adminLogin()){
            switchToPanel();
        } else {
            if (isBlocked(loginLog.getText().toLowerCase())){
                loginInfo.setText("Zostałeś zablokowany!");
            }else {
                if (isCorrectPassword()){
                    loginInfo.setText("Zalogowano!");
                    setOnline(loginLog.getText().toLowerCase());
                    switchToChat(loginLog.getText().toLowerCase());
                } else {
                    loginInfo.setText("Niepoprawny login lub hasło!");
                }
            }
        }

    }

    /**
     * Switching to panel is about switching window from log in to admin's panel.
     * @throws IOException if layout is not gonna works throws an exception.
     */
    private void switchToPanel() throws IOException {
        Stage stage = (Stage) loginInfo.getScene().getWindow();
        stage.setHeight(720);
        stage.setWidth(900);
        App.setRoot("admin");
    }

    /**
     * Method is chceking the password and when it is correct then returns true value.
     * Password has to be min 8 signs, digit, big and small letter with special sign.
     * @return boolean: true or false
     */
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

    /**
     * Method is chceking the email and when it is correct then returns true value.
     * Email can be with max 255 letters, it has to match to regex to be true.
     * Also email is checking in the database to not creating account with the same email.
     * @return boolean: true or false
     */
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

    /**
     * Method is switching window to chat if the login credentials was corrected.
     * @param username - name of the current user who wants to log in
     * @throws IOException
     */
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

    /**
     * Showing help with log in, when user forgot his password.
     */
    @FXML
    private void showHelp(){
        helperInfo.setVisible(true);
        emailHelper.setVisible(true);
        btnHelp.setVisible(true);
    }

    /**
     * Method sending help with password of user in mail.
     * Then user can log in again to application.
     */
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

    /**
     * Method checking password of current user to send help in mail.
     * @return password that user forgot.
     */
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

    /**
     * Admin can log in to application but when he is log in then he is switching to admin's panel.
     * @return true - if admin's credentials are good or false instead.
     */
    protected Boolean adminLogin(){
        return loginLog.getText().equals(System.getenv("ALOGIN")) && passwordLog.getText().equals(System.getenv("APASSWORD"));
    }

    /**
     * Method checking user's password in log in.
     * @param encryptedStoredPassword password from database
     * @return decrypted password
     */
    public static String checkPassword(String encryptedStoredPassword) {
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(System.getenv("PASS"));
        return textEncryptor.decrypt(encryptedStoredPassword);
    }

    /**
     * Method setting status of current logged user to online.
     * @param login - user's login
     */
    private void setOnline(String login){
        try{
            String query = connect.setOnline(login);
            statement = sql.createStatement();
            int rs = statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checking if user who wants to log in is blocked.
     * @param login login of user
     * @return true when user is blocked and false when user is not blocked
     */
    private Boolean isBlocked(String login){
        Boolean result = null;
        try{

            String query = connect.isBlocked(login);
            statement = sql.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                result = rs.getBoolean("blocked");
                System.out.println(result);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}