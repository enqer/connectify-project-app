package Message;

/**
 * Represents a single message on chat.
 * This class encapsulates the login, time, avatar, and message content of a user message.
 */
public class UserMessage {
    private String login;
    private String time;
    private String avatar;
    private String message;

    /**
     * Constructs a UserMessage object with the specified login, time, avatar, and message.
     *
     * @param login   the login of the user who sent the message
     * @param time    the time when the message was sent
     * @param avatar  the avatar of the user who sent the message
     * @param message the content of the message
     */
    public UserMessage(String login, String time, String avatar, String message) {
        this.login = login;
        this.time = time;
        this.avatar = avatar;
        this.message = message;
    }

    /**
     * Returns the login of the user who sent the message.
     *
     * @return the login of the user
     */
    public String getLogin() {
        return login;
    }

    /**
     * Returns the time when the message was sent.
     *
     * @return the message time
     */
    public String getTime() {
        return time;
    }

    /**
     * Returns the avatar of the user who sent the message.
     *
     * @return the user avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Returns the content of the message.
     *
     * @return the message content
     */
    public String getMessage() {
        return message;
    }
}
