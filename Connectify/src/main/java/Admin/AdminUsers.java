package Admin;

public class AdminUsers {
    private String firstName;
    private String lastName;
    private String email;
    private String avatar;
    private String login;
    private Boolean status;

    public AdminUsers(String firstName, String lastName, String email,String avatar,String login,Boolean status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatar = "img/" + avatar + ".png";
        this.login=login;
        this.status=status;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getLogin() {
        return login;
    }

    public Boolean getStatus() {
        return status;
    }
}
