package Admin;

/**
 * This class represents single user in admin panel.
 * This class encapsulates the properties of an admin user, including their first name, last name, email, avatar, login, status, and blocked status.
 */

public class AdminUsers {
    private String firstName;
    private String lastName;
    private String email;
    private String avatar;
    private String login;
    private Boolean status;
    private Boolean blocked;

    /**
     * Constructs a new AdminUsers object with the specified properties.
     *
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param email the email of the user
     * @param avatar the avatar of the user
     * @param login the login of the user
     * @param status the online status of the user
     * @param blocked the blocked status of the user
     */
    public AdminUsers(String firstName, String lastName, String email,String avatar,String login,Boolean status,Boolean blocked) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatar = avatar;
        this.login=login;
        this.status=status;
        this.blocked=blocked;
    }

    /**
     * Returns the first name of the user.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the email of the user.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the user's name of the avatar.
     *
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Returns the login of the user.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Returns the blocked status of the user.
     *
     * @return true if the user is blocked, false otherwise
     */
    public Boolean getBlocked() {
        return blocked;
    }

    /**
     * Returns the online status of the user.
     *
     * @return true if the user is online, false otherwise
     */
    public Boolean getStatus() {
        return status;
    }
}
