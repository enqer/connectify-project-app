package Admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller class for the users list item.
 * This class manages the data and behavior of the users list item.
 */
public class AdminUsersListController {
    @FXML
    private Label name;
    @FXML
    private Label email;
    @FXML
    private ImageView logo;

    /**
     * Sets the data of the user for the list item.
     *
     * @param user the admin user object
     */
    public void setData(AdminUsers user){
        name.setText(user.getFirstName()+" "+ user.getLastName());
        email.setText(user.getEmail());
        Image img = new Image(getClass().getResource("/org/example/img/"+user.getAvatar()+".png").toExternalForm());
        logo.setImage(img);
    }
}
