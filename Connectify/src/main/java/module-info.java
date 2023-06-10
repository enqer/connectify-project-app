module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;
    requires jakarta.websocket.api;
    requires tyrus.server;


    opens org.example to javafx.fxml;
    exports org.example;
    exports Admin;
    opens Admin to javafx.fxml;
}