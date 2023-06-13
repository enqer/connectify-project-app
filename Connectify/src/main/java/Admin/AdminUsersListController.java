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
        Image img = new Image(getClass().getResource("/org/example/img/"+users.getAvatar()+".png").toExternalForm());
        logo.setImage(img);
    }

}
