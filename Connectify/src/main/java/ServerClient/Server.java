package ServerClient;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private int port;
    private Map<String, PrintWriter> clientWriters;
    public Map<String, String> avatars = new HashMap<>();


    public Server(int port) {
        this.port = port;
        this.clientWriters = new HashMap<>();
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Serwer nasłuchuje na porcie " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nowe połączenie: " + clientSocket.getInetAddress().getHostAddress());

                // Tworzenie nowego wątku obsługującego klienta
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToClient(String recipient, String message) {
        PrintWriter writer = clientWriters.get(recipient);
        if (writer != null) {
            writer.println(message);
        }
    }

    private void broadcastMessage(String sender, String message) {
        for (PrintWriter writer : clientWriters.values()) {
            writer.println("[" + sender +",a]: " + message);
        }
    }

    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader reader;
        private PrintWriter writer;
        private String clientName;
        private String avatar;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                writer.println("Serwer: Podaj swoje imię:");
                clientName = reader.readLine();

                writer.println("Serwer: Witaj, " + clientName.substring(0,clientName.indexOf('.')) + "! Możesz zacząć rozmowę.");
                avatar = clientName.substring(clientName.indexOf('.')+1);
                clientName = clientName.substring(0,clientName.indexOf('.'));
                clientWriters.put(clientName, writer);
                avatars.put(clientName,avatar);


                String clientMessage;
                while ((clientMessage = reader.readLine()) != null) {
                    if (clientMessage.equals("/quit")) {
                        break;
                    } else if (clientMessage.startsWith("/msg")) {
                        String[] parts = clientMessage.split(" ", 3);
                        if (parts.length == 3) {
                            String recipient = parts[1];
                            String message = parts[2];
                            sendMessageToClient(recipient, "[" + clientName + "."+avatars.get(clientName)+",y]: " + message);
                        }
                    } else {
                        broadcastMessage(clientName+"."+avatars.get(clientName), clientMessage);
                    }
                }

                clientWriters.remove(clientName);
                clientSocket.close();
                System.out.println("Zakończono połączenie z klientem: " + clientName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int port = 12345; // Wybierz dowolny numer portu
        Server server = new Server(port);
        server.start();
    }
}