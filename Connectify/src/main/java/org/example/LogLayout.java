package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.connection.Connect;

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
    private TextField login;
    @FXML
    private PasswordField password;

    @FXML
    private Label helperInfo;
    @FXML
    private TextField emailHelper;
    @FXML
    private Button btnHelp;

    @FXML
    private Label helperInfoText;
    @FXML
    private void loginUser() throws IOException {
        if (isCorrectPassword()){
            loginInfo.setText("Zalogowano!");
            switchToChat();
        } else {
            loginInfo.setText("Niepoprawny login lub hasło!");
        }
    }

    private Boolean isCorrectPassword(){
        try{
            String result = null;
            String query = connect.checkLoginPassword(login.getText());
            statement = sql.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                result = rs.getString("password");
                System.out.println(result);
            }
            if (result != null && result ==password.getText() )
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    private boolean checkCorrectEmail(){
        try {
            String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            Boolean result = false;
            String query = connect.checkUniqueEmail(emailHelper.getText());
            statement = sql.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                result = rs.getBoolean("exists");
                System.out.println(result);
            }
            if (!result && Pattern.matches(regex, emailHelper.getText()))
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @FXML
    private void switchToChat() throws IOException {
        App.setRoot("chat");
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
            helperInfo.setText("Hasło zostało wysłane na twoją pocztę email.");
            // TODO Sending email
        }else {
            helperInfo.setText("Nieprawidłowy email lub brak konta o takim emailu.");
        }
    }
}
