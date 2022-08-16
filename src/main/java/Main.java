import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) {
        int counter = 0;
        ServerSocket serverSocket = null;
        RequestHandler requestHandler = new RequestHandler(Database.getInstance());
        TextRequestHandler textRequestHandler = new TextRequestHandler(requestHandler);
        try {
            serverSocket = new ServerSocket(6379);
            serverSocket.setReuseAddress(true);
            System.out.println("Server started");

            while (true) {
                new ClientListener(serverSocket.accept(), textRequestHandler, ++counter)
                        .start();
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                    System.out.println("Server stopped");
                }
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }
    }
}
