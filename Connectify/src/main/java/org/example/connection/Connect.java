package org.example.connection;
import java.sql.*;
public class Connect {
    private String driver = "org.postgresql.Driver";
    private String host = System.getenv("HOST");
    private String port = System.getenv("PORT");
    private String dbname = System.getenv("DBNAME");
    private String user = System.getenv("DBNAME");
    ;
    private String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
    private String pass = System.getenv("PASS");
    ;
    private Connection connection;

    public Connect() {
        connection = makeConnection();
    }

    public Connection getConnection() {
        return (connection);
    }

    public void close() {
        try {

            connection.close();
        } catch (SQLException sqle) {
            System.err.println("Blad przy zamykaniu polaczenia: " + sqle);

        }
    }

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

    public String checkUniqueLogin(String login) {
        String sql = "SELECT EXISTS ( select * from public.connectify where login='" + login + "' );";
        return sql;
    }

    public String checkUniqueEmail(String email) {
        String sql = "SELECT EXISTS ( select * from public.connectify where email='" + email + "' );";
        return sql;
    }

    public String registerUser(String name, String surname, String login, String email, String password, String date, String avatar) {
        String sql = "INSERT INTO public.connectify (name, surname, login, email, password, date_of_birth, blocked, online,avatar)\n" +
                "VALUES ('" + name + "', '" + surname + "', '" + login + "', '" + email + "', '" + password + "', '" + date + "', false, true,'" + avatar + "');";
        return sql;
    }

    public String checkLoginPassword(String login) {
        String sql = "Select password from public.connectify where login='" + login + "'";
        return sql;
    }

    public String passwordHelper(String email) {
        String sql = "Select password from public.connectify where email='" + email + "'";
        return sql;
    }

    public String showUsers() {
        String sql = "SELECT login FROM public.connectify";
        return sql;
    }

    public String showAllFriends() {
        String sql = "SELECT * FROM public.connectify_contacts";
        return sql;
    }

    public String showFriends(String userLogin) {
        String sql = "SELECT contact_login FROM public.connectify_contacts WHERE user_login='" + userLogin + "'";
        return sql;
    }

    public String addFriend(String userLogin, String friendLogin) {
        String sql = "INSERT INTO public.connectify_contacts (user_login, contact_login) VALUES (?, ?)";
        return sql;
    }

    public String deleteFriend(String userLogin, String friendLogin) {
        String sql = "DELETE FROM public.connectify_contacts WHERE user_login = ? AND contact_login = ?";
        return sql;
    }

    public String getUsers() {
        String sql = "SELECT * from public.connectify";
        return sql;
    }

    public String blockUser(String status, String login) {
        String sql = "UPDATE public.connectify SET blocked=" + status + " WHERE login='" + login + "'";
        return sql;
    }

    public String countUser() {
        String sql = "SELECT COUNT(*) FROM public.connectify";
        return sql;
    }

    public String countOnlineUser() {
        String sql = "SELECT COUNT(*) FROM public.connectify WHERE online=true";
        return sql;
    }

    public String countBlockedUser() {
        String sql = "SELECT COUNT(*) FROM public.connectify WHERE blocked=true";
        return sql;
    }

    public String userLook(String login) {
        String sql = "SELECT login, avatar FROM public.connectify WHERE login = ?";
        return sql;
    }

    public String giveAllData(String user) {
        String sql = "SELECT name, surname, email, date_of_birth, avatar, online FROM public.connectify WHERE login = ?";
        return sql;
    }
    public String setOnline(String user){
        String sql = "UPDATE public.connectify SET online='true' WHERE login='"+user+"'";
        return sql;
    }

    public String onlineOffline(boolean online, String login) {
        String sql = "UPDATE public.connectify SET online = ? WHERE login = ?";
        return sql;
    }

}