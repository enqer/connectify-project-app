package org.example.connection;
import java.sql.*;

/**
 * The Connect class is responsible for managing the database connection and provides methods for database operations.
 */
public class Connect {
    private String driver = "org.postgresql.Driver";
    private String host = System.getenv("HOST");
    private String port = System.getenv("PORT");
    private String dbname = System.getenv("DBNAME");
    private String user = System.getenv("DBNAME");
    private String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
    private String pass = System.getenv("PASS");
    private Connection connection;

    /**
     * Constructor for the Connect class.
     * Initializes a Connect object and establishes a connection to the database.
     */
    public Connect() {
        connection = makeConnection();
    }

    /**
     * Returns the Connection object representing the database connection.
     *
     * @return the Connection object representing the database connection
     */
    public Connection getConnection() {
        return (connection);
    }

    /**
     * Closes the database connection.
     */
    public void close() {
        try {

            connection.close();
        } catch (SQLException sqle) {
            System.err.println("Blad przy zamykaniu polaczenia: " + sqle);

        }
    }

    /**
     * Establishes a connection to the database.
     *
     * @return the Connection object representing the database connection
     */
    public Connection makeConnection() {
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, pass);
            return (connection);

        } catch (ClassNotFoundException cnfe) {
            System.err.println("Blad ladowania sterownika: " + cnfe);

            return (null);
        } catch (SQLException sqle) {
            System.err.println("Blad przy nawiązywaniu polaczenia: " + sqle);

            return (null);
        }
    }

    /**
     * Checks if a login is unique in the database.
     *
     * @param login the login to check
     * @return the SQL query for checking the uniqueness of the login
     */
    public String checkUniqueLogin(String login) {
        return "SELECT EXISTS ( select * from public.connectify where login='" + login + "' );";
    }

    /**
     * Checks if an email is unique in the database.
     *
     * @param email the email to check
     * @return the SQL query for checking the uniqueness of the email
     */
    public String checkUniqueEmail(String email) {
        return "SELECT EXISTS ( select * from public.connectify where email='" + email + "' );";
    }

    /**
     * Generates the SQL query for registering a new user.
     *
     * @param name     the user's name
     * @param surname  the user's surname
     * @param login    the user's login
     * @param email    the user's email
     * @param password the user's password
     * @param date     the user's date of birth
     * @param avatar   the user's avatar
     * @return the SQL query for registering a new user
     */
    public String registerUser(String name, String surname, String login, String email, String password, String date, String avatar) {
        return "INSERT INTO public.connectify (name, surname, login, email, password, date_of_birth, blocked, online,avatar)\n" +
                "VALUES ('" + name + "', '" + surname + "', '" + login + "', '" + email + "', '" + password + "', '" + date + "', false, true,'" + avatar + "');";
    }

    /**
     * Retrieves the SQL query to check the password associated with a login.
     *
     * @param login the login to check
     * @return the SQL query to retrieve the password for the given login
     */
    public String checkLoginPassword(String login) {
        return "Select password from public.connectify where login='" + login + "'";
    }

    /**
     * Retrieves the SQL query to retrieve the password associated with an email.
     *
     * @param email the email to check
     * @return the SQL query to retrieve the password for the given email
     */
    public String passwordHelper(String email) {
        return "Select password from public.connectify where email='" + email + "'";
    }

    /**
     * Retrieves the SQL query to fetch all users' logins from the database.
     *
     * @return the SQL query to retrieve all users' logins
     */
    public String showUsers() {
        return "SELECT login FROM public.connectify";
    }

    /**
     * Retrieves the SQL query to fetch all friends from the database.
     *
     * @return the SQL query to retrieve all friends
     */
    public String showAllFriends() {
        return "SELECT * FROM public.connectify_contacts";
    }

    /**
     * Retrieves the SQL query to fetch the friends of a specific user.
     *
     * @param userLogin the login of the user
     * @return the SQL query to retrieve the friends of the user
     */
    public String showFriends(String userLogin) {
        return "SELECT contact_login FROM public.connectify_contacts WHERE user_login='" + userLogin + "'";
    }

    /**
     * Retrieves the SQL query to add a friend to the user's contact list.
     *
     * @param userLogin   the login of the user
     * @param friendLogin the login of the friend to add
     * @return the SQL query to add a friend to the contact list
     */
    public String addFriend(String userLogin, String friendLogin) {
        return "INSERT INTO public.connectify_contacts (user_login, contact_login) VALUES (?, ?)";
    }

    /**
     * Retrieves the SQL query to delete a friend from the user's contact list.
     *
     * @param userLogin   the login of the user
     * @param friendLogin the login of the friend to delete
     * @return the SQL query to delete a friend from the contact list
     */
    public String deleteFriend(String userLogin, String friendLogin) {
        return "DELETE FROM public.connectify_contacts WHERE user_login = ? AND contact_login = ?";
    }

    /**
     * Retrieves the SQL query to fetch all users from the database.
     *
     * @return the SQL query to retrieve all users
     */
    public String getUsers() {
        return "SELECT * from public.connectify";
    }

    /**
     * Retrieves the SQL query to block or unblock a user.
     *
     * @param status the status (true for blocked, false for unblocked)
     * @param login  the login of the user
     * @return the SQL query to block or unblock a user
     */
    public String blockUser(String status, String login) {
        return "UPDATE public.connectify SET blocked=" + status + " WHERE login='" + login + "'";
    }

    /**
     * Retrieves the SQL query to count the total number of users in the database.
     *
     * @return the SQL query to count the total number of users
     */
    public String countUser() {
        return "SELECT COUNT(*) FROM public.connectify";
    }

    /**
     * Retrieves the SQL query to count the number of online users in the database.
     *
     * @return the SQL query to count the number of online users
     */
    public String countOnlineUser() {
        return "SELECT COUNT(*) FROM public.connectify WHERE online=true";
    }

    /**
     * Retrieves the SQL query to count the number of blocked users in the database.
     *
     * @return the SQL query to count the number of blocked users
     */
    public String countBlockedUser() {
        return "SELECT COUNT(*) FROM public.connectify WHERE blocked=true";
    }

    /**
     * Retrieves the SQL query to fetch the login and avatar of a specific user.
     *
     * @param login the login of the user
     * @return the SQL query to retrieve the login and avatar of the user
     */
    public String userLook(String login) {
        return "SELECT login, avatar FROM public.connectify WHERE login = ?";
    }

    /**
     * Retrieves the SQL query to fetch all data of a specific user.
     *
     * @param user the login of the user
     * @return the SQL query to retrieve all data of the user
     */
    public String giveAllData(String user) {
        return "SELECT name, surname, email, date_of_birth, avatar, online FROM public.connectify WHERE login = ?";
    }

    /**
     * Retrieves the SQL query to set a user's online status to "online".
     *
     * @param user the login of the user
     * @return the SQL query to set the user's online status to "online"
     */
    public String setOnline(String user){
        return "UPDATE public.connectify SET online='true' WHERE login='"+user+"'";
    }

    /**
     * Retrieves the SQL query to check if a user is blocked.
     *
     * @param user the login of the user
     * @return the SQL query to check if the user is blocked
     */
    public String isBlocked(String user){
        return "SELECT blocked FROM public.connectify WHERE login='"+user+"'";
    }

    /**
     * Retrieves the SQL query to set a user's online status to "offline".
     *
     * @param login the login of the user
     * @return the SQL query to set the user's online status to "offline"
     */
    public String onlineOffline(boolean online, String login) {
        return "UPDATE public.connectify SET online = ? WHERE login = ?";
    }

    /**
     * Retrieves the SQL query to set a user's online status to "offline".
     *
     * @param login the login of the user
     * @return the SQL query to set the user's online status to "offline"
     */
    public String setOffline(String login){
        return "UPDATE public.connectify SET online='false' WHERE login ='"+login+"'";
    }

}