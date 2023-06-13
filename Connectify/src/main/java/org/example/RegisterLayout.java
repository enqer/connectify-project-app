package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.example.connection.Connect;
import org.example.mail.MailSender;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class RegisterLayout {
    private static final int MAX_LENGTH = 50;


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
    private ImageView img1,img2,img3,img4,img5,img6;
    @FXML
    private StackPane imgSPane1,imgSPane2,imgSPane3,imgSPane4,imgSPane5,imgSPane6;
    private String avatar;

    public void initialize() throws IOException {
        maxLettersTextField(name);
        maxLettersTextField(surname);
        maxLettersTextField(login);
        maxLettersTextField(email);
        maxLettersTextField(password);
    }

    @FXML
    private void registerUser(){
        if (checkDataValidity()){
            try {
                String query = connect.registerUser(name.getText(),surname.getText(),login.getText(),email.getText(),password.getText(),datePicker.getValue().toString(),"batman");
                statement = sql.createStatement();
                int rs = statement.executeUpdate(query);
                System.out.println(rs);
                registerInfo.setText("Konto zostało utworzone!");
                sendWelcomeMail();
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
        if (!isEnoughLetters())
        {
            registerInfo.setText("Login powinien mieć od 5 do "+MAX_LENGTH+" znaków");
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
            return !result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // checking if the mail is not in used
    private Boolean isUniqueMail(){
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (!(Pattern.matches(regexPattern, email.getText())))
            return false;
        Boolean result = true;
        try {
            String query = connect.checkUniqueEmail(email.getText());
            statement = sql.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                result = rs.getBoolean("exists");
                System.out.println(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return !result;
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
        if (avatar == null || name.getText().equals("") || surname.getText().equals("") || login.getText().equals("") || email.getText().equals("") || password.getText().equals("") || datePicker.getValue() == null)
            return false;
        return true;
    }
    private Boolean isEnoughLetters(){
        return login.getText().length() >= 5 && login.getText().length() <= 30;
    }
    @FXML
    private void switchToChat() throws IOException {
        App.setRoot("chat");
    }

    private void sendWelcomeMail(){
        MailSender mailSender = new MailSender();
        mailSender.setSender(System.getenv("EMAIL"));
        mailSender.setRecipient(email.getText());
        mailSender.setSubject("Connectify - witamy na naszym czacie!");
        mailSender.setContent("Dzięki że dołączyłeś do naszej społeczności! :)");
        mailSender.send();
    }


    private void maxLettersTextField(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > MAX_LENGTH) {
                textField.setText(newValue.substring(0, MAX_LENGTH));
            }
        });
    }


    @FXML
    public void selectImg1(){
        avatar = "batman";
        imgSPane1.setStyle("-fx-background-color: #7289da");
        imgSPane2.setStyle("-fx-background-color: transparent");
        imgSPane3.setStyle("-fx-background-color: transparent");
        imgSPane4.setStyle("-fx-background-color: transparent");
        imgSPane5.setStyle("-fx-background-color: transparent");
        imgSPane6.setStyle("-fx-background-color: transparent");
    }

    @FXML
    public void selectImg2(){
        avatar = "breaking-bad";
        imgSPane1.setStyle("-fx-background-color: transparent");
        imgSPane2.setStyle("-fx-background-color: #7289da");
        imgSPane3.setStyle("-fx-background-color: transparent");
        imgSPane4.setStyle("-fx-background-color: transparent");
        imgSPane5.setStyle("-fx-background-color: transparent");
        imgSPane6.setStyle("-fx-background-color: transparent");

    }
    @FXML
    public void selectImg3(){
        avatar = "grandma";
        imgSPane1.setStyle("-fx-background-color: transparent");
        imgSPane2.setStyle("-fx-background-color: transparent");
        imgSPane3.setStyle("-fx-background-color: #7289da");
        imgSPane4.setStyle("-fx-background-color: transparent");
        imgSPane5.setStyle("-fx-background-color: transparent");
        imgSPane6.setStyle("-fx-background-color: transparent");
    }
    @FXML
    public void selectImg4(){
        avatar = "monster";
        imgSPane1.setStyle("-fx-background-color: transparent");
        imgSPane2.setStyle("-fx-background-color: transparent");
        imgSPane3.setStyle("-fx-background-color: transparent");
        imgSPane4.setStyle("-fx-background-color: #7289da");
        imgSPane5.setStyle("-fx-background-color: transparent");
        imgSPane6.setStyle("-fx-background-color: transparent");
    }
    @FXML
    public void selectImg5(){
        avatar = "muslim";
        imgSPane1.setStyle("-fx-background-color: transparent");
        imgSPane2.setStyle("-fx-background-color: transparent");
        imgSPane3.setStyle("-fx-background-color: transparent");
        imgSPane4.setStyle("-fx-background-color: transparent");
        imgSPane5.setStyle("-fx-background-color: #7289da");
        imgSPane6.setStyle("-fx-background-color: transparent");
    }@FXML
    public void selectImg6(){
        avatar = "man";
        imgSPane1.setStyle("-fx-background-color: transparent");
        imgSPane2.setStyle("-fx-background-color: transparent");
        imgSPane3.setStyle("-fx-background-color: transparent");
        imgSPane4.setStyle("-fx-background-color: transparent");
        imgSPane5.setStyle("-fx-background-color: transparent");
        imgSPane6.setStyle("-fx-background-color: #7289da");
    }

}
