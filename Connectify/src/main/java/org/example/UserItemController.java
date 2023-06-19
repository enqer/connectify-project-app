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

    /**
     * Sets the name for the user.
     * @param name The name to be set.
     */
    public void setName(String name) {
        nameLabel.setText(name);
    }

    /**
     * Sets the email for the user.
     * @param email The email to be set.
     */
    public void setEmail(String email) {
        emailLabel.setText(email);
    }

    /**
     * Sets the logo image for the user item.
     * @param imageUrl The URL of the image to be set as the logo.
     */
    public void setLogo(String imageUrl) {
        // Ustawienie obrazu dla ImageView
        logoImageView.setImage(new Image(imageUrl));
    }

    /**
     * Initializes the user item.
     * Adds a listener to the 'isSelected' property of the user item.
     * When the value of 'isSelected' changes, it updates the style class of the user item accordingly.
     */
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
