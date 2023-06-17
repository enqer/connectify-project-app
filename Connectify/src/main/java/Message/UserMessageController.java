package Message;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserMessageController {
    @FXML
    private ImageView messageAvatar;
    @FXML
    private Label messageLogin;
    @FXML
    private Label messageTime;
    @FXML
    private Label messageM;

    public void setData(UserMessage userMessage){
        messageLogin.setText(userMessage.getLogin());
        messageTime.setText(userMessage.getTime());
        messageM.setText(userMessage.getMessage());
        Image img = new Image(getClass().getResource("/org/example/img/"+userMessage.getAvatar()+".png").toExternalForm());
        messageAvatar.setImage(img);
    }
}
