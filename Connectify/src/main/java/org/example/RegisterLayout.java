package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.connection.Connect;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class RegisterLayout {

    Connect connect = new Connect();
    Connection sql = connect.getConnection();
    Statement statement;

    @FXML
    private TextField name, surname, login, email;

    @FXML
    private PasswordField password;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label registerInfo;
    @FXML
    private void registerUser(){
        if (checkDataValidity()){
            try {
                String query = connect.registerUser(name.getText(),surname.getText(),login.getText(),email.getText(),password.getText(),datePicker.getValue().toString());
                statement = sql.createStatement();
                int rs = statement.executeUpdate(query);
                System.out.println(rs);
                registerInfo.setText("Konto zostało utworzone!");
                switchToChat();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Boolean checkDataValidity(){
        if (!isFieldsFilled())
        {
            registerInfo.setText("Wypełnij wszystkie pola!");
            return false;
        }
        if (!isUniqueLogin()){
            registerInfo.setText("Podany login jest zajęty!");
            return false;
        }
        if (!isUniqueMail()){
            registerInfo.setText("Podany email jest zajęty albo niepoprawny!");
            return false;
        }
        if (!isEnoughAge()){
            registerInfo.setText("Musisz mieć co najmniej 13 lat\n aby założyć konto!");
            return false;
        }
        if (!isStrongPassword()){
            registerInfo.setText("Ustaw silniejsze hasło!\n Silne hasło to takie które zawiera:\n dużą i małą literę, cyfrę, znak specjalny\n oraz ma co najmniej 8 znaków. ");
            return false;
        }
        return true;
    }

    // checking if the login is not in used
    private Boolean isUniqueLogin(){
        try {
            Boolean result = false;
            String query = connect.checkUniqueLogin(login.getText());
            statement = sql.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                result = rs.getBoolean("exists");
                System.out.println(result);
            }
            if (!result)
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    // checking if the mail is not in used
    private Boolean isUniqueMail(){
        try {
            String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            Boolean result = false;
            String query = connect.checkUniqueEmail(email.getText());
            statement = sql.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                result = rs.getBoolean("exists");
                System.out.println(result);
            }
            if (!result && Pattern.matches(regex, email.getText()))
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    //checking if password is strong enough
    private Boolean isStrongPassword(){
        String pass = password.getText();

        // Sprawdzenie długości hasła
        if (pass.length() < 8) {
            return false;
        }

        // Sprawdzenie, czy hasło zawiera co najmniej jedną dużą literę
        if (!Pattern.compile("[A-Z]").matcher(pass).find()) {
            return false;
        }

        // Sprawdzenie, czy hasło zawiera co najmniej jedną małą literę
        if (!Pattern.compile("[a-z]").matcher(pass).find()) {
            return false;
        }

        // Sprawdzenie, czy hasło zawiera co najmniej jedną cyfrę
        if (!Pattern.compile("\\d").matcher(pass).find()) {
            return false;
        }

        // Sprawdzenie, czy hasło zawiera co najmniej jeden znak specjalny
        if (!Pattern.compile("[^a-zA-Z0-9]").matcher(pass).find()) {
            return false;
        }

        // Hasło spełnia wszystkie wymagania
        return true;
    }

    // checking if the age of user is enough
    private Boolean isEnoughAge(){
        LocalDate currentDate = LocalDate.now();
        LocalDate minimumDate = currentDate.minusYears(13);
        return datePicker.getValue().isBefore(minimumDate);
    }

    // check if every field have been filled
    private Boolean isFieldsFilled(){
        if (name.getText().equals("") || surname.getText().equals("") || login.getText().equals("") || email.getText().equals("") || password.getText().equals("") || datePicker.getValue() == null)
            return false;
        return true;
    }
    @FXML
    private void switchToChat() throws IOException {
        App.setRoot("chat");
    }
}
