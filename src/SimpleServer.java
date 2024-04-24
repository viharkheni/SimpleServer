import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    private ServerSocket serverSocket;
    private int portNumber = 5445;

    public static void main(String[] args) {
        System.out.println("Simple Server Demo started....");
        new SimpleServer().startServer();
        System.out.println("Simple Server Demo finished!");
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Server waiting for clients on port " + portNumber + "...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);


                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        } catch (IOException e) {
            System.err.println("IOException during server operation: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                while (true) {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new PrintWriter(clientSocket.getOutputStream(), true);

                    String request;
                    StringBuilder fullRequest = new StringBuilder();
                    while ((request = in.readLine()) != null && !request.isEmpty()) {
                        fullRequest.append(request).append("\n");
                    }


                    String response = processRequest(fullRequest.toString());

                    out.println(response);
                    //System.out.println("Server response: " + response);
                    if (response.equals("# #")) {
                        System.out.println("Simple Server Demo finished!");
                        break;
                    }

                }
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("IOException during client handling: " + e.getMessage());
            }
        }

        private String processRequest(String request) {
            int numLines = request.split("\n").length;
            int numChars = request.length()-numLines;
            if (numLines == 1 && numChars == -1) {
                numLines =0; numChars =0;
            }
            return numLines + " " + numChars;
        }
    }
}
