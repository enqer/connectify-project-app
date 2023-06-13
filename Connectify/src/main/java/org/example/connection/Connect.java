package org.example.connection;
import java.sql.*;
public class Connect {
    private String driver = "org.postgresql.Driver";
    private String host = System.getenv("HOST");
    private String port = System.getenv("PORT");
    private String dbname = System.getenv("DBNAME");
    private String user = System.getenv("DBNAME");;
    private String url = "jdbc:postgresql://" + host+":"+port + "/" + dbname;
    private String pass = System.getenv("PASS");;
    private Connection connection;

    public Connect () {
        connection = makeConnection(); }

    public Connection getConnection(){
        return(connection);
    }
    public void close() {
        try {

            connection.close(); }

        catch (SQLException sqle){
            System.err.println("Blad przy zamykaniu polaczenia: " + sqle);

        } }

    public Connection makeConnection(){
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, pass); return(connection);

        }
        catch(ClassNotFoundException cnfe) {
            System.err.println("Blad ladowania sterownika: " + cnfe);

            return(null);
        }
        catch(SQLException sqle) {
            System.err.println("Blad przy nawiązywaniu polaczenia: " + sqle);

            return(null);
        }
    }

    public String checkUniqueLogin(String login){
        String sql = "SELECT EXISTS ( select * from public.connectify where login='"+login+"' );";
        return sql;
    }
    public String checkUniqueEmail(String email){
        String sql = "SELECT EXISTS ( select * from public.connectify where email='"+email+"' );";
        return sql;
    }
    public String registerUser(String name, String surname, String login, String email, String password, String date, String avatar ){
        String sql =    "INSERT INTO public.connectify (name, surname, login, email, password, date_of_birth, blocked, online,avatar)\n" +
                        "VALUES ('"+name+"', '"+surname+"', '"+login+"', '"+email+"', '"+password+"', '"+date+"', false, true,'"+avatar+"');";
        return sql;
    }

    public String checkLoginPassword(String login){
        String sql ="Select password from public.connectify where login='"+login+"'";
        return sql;
    }
    public String passwordHelper(String email){
        String sql ="Select password from public.connectify where email='"+email+"'";
        return sql;
    }
    public String showUsers() {
        String sql = "SELECT login FROM public.connectify";
        return sql;
    }
    public String showFriends(String user) {
        String sql = "SELECT contact FROM public.connectify_contacts WHERE user='"+user+"'";
        return sql;
    }
    public String addFriend(String user, String contact) {
        String sql = "INSERT INTO public.connectify_contacts(user, contact) VALUES('"+user+"','"+contact+"')";
        return sql;
    }

}

