package Admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AdminUsersListController {

    @FXML
    private Label name;
    @FXML
    private Label email;
    @FXML
    private ImageView logo;

    public void setData(AdminUsers users){
        name.setText(users.getFirstName()+" "+ users.getLastName());
        //surname.setText(users.getLastName());
        email.setText(users.getEmail());
        Image image = new Image(users.getAvatar());
        logo.setImage(image);
    }

}
