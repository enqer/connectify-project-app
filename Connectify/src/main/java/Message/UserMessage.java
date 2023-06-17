package Message;

public class UserMessage {
    private String login;
    private String time;
    private String avatar;
    private String message;

    public UserMessage(String login, String time, String avatar, String message) {
        this.login = login;
        this.time = time;
        this.avatar = avatar;
        this.message = message;
    }

    public String getLogin() {
        return login;
    }

    public String getTime() {
        return time;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getMessage() {
        return message;
    }
}
