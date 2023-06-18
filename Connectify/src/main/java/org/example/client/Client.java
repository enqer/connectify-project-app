package org.example.client;

import Message.UserMessage;
import Message.UserMessageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import org.example.ChatController;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Client class that provided to connection to server and reads and writes messages.
 */
public class Client {
    private String serverAddress;
    private int serverPort;
    private String name;
    private String avatar;

    public PrintWriter writer;

    @FXML
    private static ListView listViewMessage;

    private static ObservableList<UserMessage> messages = FXCollections.observableArrayList();

    /**
     * Constructor of Client class that setter the credentials for server.
     * @param serverAddress address of server that we want to log in
     * @param serverPort port of server that we want to log in
     * @param name name of the client
     * @param avatar avatar of the client
     * @param listViewMessage listview of showing messages
     */
    public Client(String serverAddress, int serverPort, String name, String avatar, ListView listViewMessage) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.name = name;
        this.avatar = avatar;
        this.listViewMessage = listViewMessage;
    }

    /**
     * Method that start a connection to server and available to write and read messages.
     */
    public void start() {
        try {
            Socket socket = new Socket(serverAddress, serverPort);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            String serverMessage = reader.readLine();
            System.out.println(serverMessage);

            writer.println(name+"."+avatar);

            new Thread(new MessageReader(reader)).start();

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String message;
//            while ((message = consoleReader.readLine()) != null) {
//                writer.println(message);
//                if (message.equals("/quit")) {
//                    break;
//                }
//            }
//            if (message.equals("/quit")) {
//                    break;
//            }
//            socket.close();
//            System.out.println("Zakończono połączenie z serwerem.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * MessageReader class reads the message to send.
     */
    private static class MessageReader implements Runnable {
        private BufferedReader reader;

        /**
         * Constructor to set a reader.
         * @param reader - reading from server
         */
        public MessageReader(BufferedReader reader) {
            this.reader = reader;
        }

        /**
         * Method that run to adding messages.
         */
        public void run() {
            try {
                String serverMessage;
                while ((serverMessage = reader.readLine()) != null) {
                    System.out.println(serverMessage);
                    addMessage(serverMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Method that adding new element to listview with new fxml layout.
         * @param userMessage - message
         */
        private void addElement(UserMessage userMessage) {
            try {
                FXMLLoader loader = new FXMLLoader(ChatController.class.getResource("singleMessage.fxml"));
                Parent root = loader.load();
                UserMessageController controller = loader.getController();
                controller.setData(userMessage);
                listViewMessage.getItems().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Processing the message and setting the data.
         * @param message - text of message
         */
        public void addMessage(String message){
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String formattedTime = dateFormat.format(currentDate);
            System.out.println(message);
            int a = message.indexOf(':');
            if (message.substring(0,a).equals("Serwer")){
                messages.add(0,new UserMessage("Serwer",formattedTime,"man", message.substring(a+1)));
                UserMessage userMessage = messages.get(0);
                addElement(userMessage);
            }else {
                int b = message.indexOf(',');
                int c = message.indexOf('.');
                String mess = message.substring(a);
                String client = message.substring(1, c);
                String avatar = message.substring(c+1,b);
                String flag = message.substring(b+1,b+2);
                String to;
                System.out.println(mess);
                System.out.println(client);
                System.out.println(avatar);
                System.out.println(flag);

                if (flag.equals("a"))
                    to = "Do wszystkich";
                else
                    to = "Do ciebie";
                System.out.println(to);
                messages.add(0,new UserMessage(client,formattedTime,avatar, to + mess));
//            messages.add(0,new UserMessage("olekzmorek","22:00","batman", message));
                UserMessage userMessage = messages.get(0);
                addElement(userMessage);
            }

        }
    }

}
