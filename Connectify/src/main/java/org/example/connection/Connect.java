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
        return "SELECT EXISTS ( select * from public.connectify where login='" + login + "' );";
    }

    public String checkUniqueEmail(String email) {
        return "SELECT EXISTS ( select * from public.connectify where email='" + email + "' );";
    }

    public String registerUser(String name, String surname, String login, String email, String password, String date, String avatar) {
        return "INSERT INTO public.connectify (name, surname, login, email, password, date_of_birth, blocked, online,avatar)\n" +
                "VALUES ('" + name + "', '" + surname + "', '" + login + "', '" + email + "', '" + password + "', '" + date + "', false, true,'" + avatar + "');";
    }

    public String checkLoginPassword(String login) {
        return "Select password from public.connectify where login='" + login + "'";
    }

    public String passwordHelper(String email) {
        return "Select password from public.connectify where email='" + email + "'";
    }

    public String showUsers() {
        return "SELECT login FROM public.connectify";
    }

    public String showAllFriends() {
        return "SELECT * FROM public.connectify_contacts";
    }

    public String showFriends(String userLogin) {
        return "SELECT contact_login FROM public.connectify_contacts WHERE user_login='" + userLogin + "'";
    }

    public String addFriend(String userLogin, String friendLogin) {
        return "INSERT INTO public.connectify_contacts (user_login, contact_login) VALUES (?, ?)";
    }

    public String deleteFriend(String userLogin, String friendLogin) {
        return "DELETE FROM public.connectify_contacts WHERE user_login = ? AND contact_login = ?";
    }

    public String getUsers() {
        return "SELECT * from public.connectify";
    }

    public String blockUser(String status, String login) {
        return "UPDATE public.connectify SET blocked=" + status + " WHERE login='" + login + "'";
    }

    public String countUser() {
        return "SELECT COUNT(*) FROM public.connectify";
    }

    public String countOnlineUser() {
        return "SELECT COUNT(*) FROM public.connectify WHERE online=true";
    }

    public String countBlockedUser() {
        return "SELECT COUNT(*) FROM public.connectify WHERE blocked=true";
    }

    public String userLook(String login) {
        return "SELECT login, avatar FROM public.connectify WHERE login = ?";
    }

    public String giveAllData(String user) {
        return "SELECT name, surname, email, date_of_birth, avatar, online FROM public.connectify WHERE login = ?";
    }
    public String setOnline(String user){
        return "UPDATE public.connectify SET online='true' WHERE login='"+user+"'";
    }
    public String isBlocked(String user){
        return "SELECT blocked FROM public.connectify WHERE login='"+user+"'";
    }


    public String onlineOffline(boolean online, String login) {
        String sql = "UPDATE public.connectify SET online = ? WHERE login = ?";
        return sql;
    }

    public String finalQuery(String user) {
        String sql = "SELECT online FROM public.connectify WHERE login = ?";
        return sql;
    }

}

