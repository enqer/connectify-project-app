package ServerClient;
import java.io.*;
import java.net.Socket;

public class Client {
    private String serverAddress;
    private int serverPort;
    private String name;

    public Client(String serverAddress, int serverPort, String name) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.name = name;
    }

    public void start() {
        try {
            Socket socket = new Socket(serverAddress, serverPort);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            String serverMessage = reader.readLine();
            System.out.println(serverMessage);

            writer.println(name+"."+"grandma");

            new Thread(new MessageReader(reader)).start();

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while ((message = consoleReader.readLine()) != null) {
                writer.println(message);
                if (message.equals("/quit")) {
                    break;
                }
            }

            socket.close();
            System.out.println("Zakończono połączenie z serwerem.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class MessageReader implements Runnable {
        private BufferedReader reader;

        public MessageReader(BufferedReader reader) {
            this.reader = reader;
        }

        public void run() {
            try {
                String serverMessage;
                while ((serverMessage = reader.readLine()) != null) {
                    System.out.println(serverMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String serverAddress = "localhost"; // Adres serwera
        int serverPort = 12345; // Numer portu serwera
        String name = "Lewy"; // Nazwa klienta

        Client client = new Client(serverAddress, serverPort, name);
        client.start();
    }
}
