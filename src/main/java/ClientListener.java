import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientListener extends Thread {

    private final Socket clientSocket;
    private final TextRequestHandler requestHandler;
    private final int id;

    public ClientListener(Socket clientSocket,
                          TextRequestHandler requestHandler,
                          int id) {
        this.clientSocket = clientSocket;
        this.requestHandler = requestHandler;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            OutputStream out = clientSocket.getOutputStream();
            InputStream in = clientSocket.getInputStream();
            System.out.println("Client " + id + " connected");

            int count;
            byte[] buffer = new byte[8192];
            while ((count = in.read(buffer)) > 0) {
                String request = new String(buffer, 0, count, StandardCharsets.UTF_8);
                System.out.println("Client " + id + " sent: " + request);

                String response = requestHandler.handle(request);
                out.write(response.getBytes(StandardCharsets.UTF_8));
                System.out.println("To client " + id + " sent: " + response);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                    System.out.println("Client " + id + " disconnected");
                }
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }
    }
}
