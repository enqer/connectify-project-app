package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserItemController {
    private String image;
    private String email;


    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private ImageView logoImageView;

    public void setName(String name) {
        nameLabel.setText(name);
    }

    public void setEmail(String email) {
        emailLabel.setText(email);
    }

    public void setLogo(String imageUrl) {
        // Ustawienie obrazu dla ImageView
        logoImageView.setImage(new Image(imageUrl));
    }

    public void addElement(String name, String email, String image) {
        this.nameLabel.setText(name);
        this.emailLabel.setText(email);
        // Ustaw obrazek w ImageView
        Image img = new Image(image);
        this.logoImageView.setImage(img);
    }

}
