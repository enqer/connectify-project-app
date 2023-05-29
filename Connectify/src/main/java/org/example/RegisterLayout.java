package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class RegisterLayout {

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
            registerInfo.setText("Konto zostało utworzone!");
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
            registerInfo.setText("Podany email jest zajęty!");
            return false;
        }
        if (!isEnoughAge()){
            registerInfo.setText("Musisz mieć co najmniej 13 lat aby założyć konto!");
            return false;
        }
        if (!isStrongPassword()){
            registerInfo.setText("Ustaw silniejsze hasło! Silne hasło to takie które zawiera: dużą i małą literę, cyfrę, znak specjalny oraz ma co najmniej 8 znaków ");
            return false;
        }

        return true;
    }
    private Boolean isUniqueLogin(){
        // checking
        return true;
    }
    private Boolean isUniqueMail(){
        // checking
        return true;
    }

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

    private Boolean isEnoughAge(){

        return true;
    }

    private Boolean isFieldsFilled(){
        return name.getText() != null && surname.getText() != null && login.getText() != null && email.getText() != null && password.getText() != null && datePicker.getValue() != null;
    }
}
