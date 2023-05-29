package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        checkNullInput();
    }

    private void checkNullInput(){
        if (name.getText() != null && surname.getText() != null && login.getText() != null && email.getText() != null && password.getText() != null && datePicker.getValue() != null){
            if (isUniqueLogin()){
                if (isUniqueMail()){
                    if (isEnoughAge()){
                        if (isStrongerPassword()){

                        }else {
                            registerInfo.setText("Ustaw silniejsze hasło!");
                        }
                    }else {
                        registerInfo.setText("Musisz mieć co najmniej 13 lat aby założyć konto!");
                    }
                } else {
                    registerInfo.setText("Podany email jest zajęty!");
                }
            }else {
                registerInfo.setText("Podany login jest zajęty!");
            }

            registerInfo.setText(datePicker.getValue().toString());
        }else {
            registerInfo.setText("Wypełnij wszystkie pola!");
        }
    }
    private Boolean isUniqueLogin(){
        // checking
        return true;
    }
    private Boolean isUniqueMail(){
        // checking
        return true;
    }

    private Boolean isStrongerPassword(){

        return true;
    }

    private Boolean isEnoughAge(){

        return true;
    }
}
