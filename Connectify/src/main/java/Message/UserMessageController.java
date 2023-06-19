package Message;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller class for the message list item.
 * This class manages the data and behavior of the message list item.
 */
public class UserMessageController {
    @FXML
    private ImageView messageAvatar;
    @FXML
    private Label messageLogin;
    @FXML
    private Label messageTime;
    @FXML
    private Label messageM;

    /**
     * Sets the data for the message.
     *
     * @param userMessage the UserMessage object containing the message data
     */
    public void setData(UserMessage userMessage){
        messageLogin.setText(userMessage.getLogin());
        messageTime.setText(userMessage.getTime());
        messageM.setText(userMessage.getMessage());
        Image img = new Image(getClass().getResource("/org/example/img/"+userMessage.getAvatar()+".png").toExternalForm());
        messageAvatar.setImage(img);
    }
}
