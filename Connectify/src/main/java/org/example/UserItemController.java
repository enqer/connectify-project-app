package org.example;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class UserItemController {
    private BooleanProperty isSelected = new SimpleBooleanProperty(false);

    @FXML
    private AnchorPane userItem;

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
    @FXML
    public void initialize() {
        isSelected.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                userItem.getStyleClass().add("selected");
            } else {
                userItem.getStyleClass().remove("selected");
            }
        });
    }
}
