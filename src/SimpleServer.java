import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/* simple iterative server */
/* single request scenario */

public class SimpleServer {
    ServerSocket serv = null;
    Socket s = null;
    private BufferedReader inStreamReader = null;
    private PrintWriter outStreamWriter = null;
    private int portNumber = 5445;

    public static void main(String[] args) {
        System.out.println("Simple Server Demo started....");
        new SimpleServer().startServer();
        System.out.println("Simple Server Demo finished!");
    }

    private void startServer() {
        try {
            serv = new ServerSocket(portNumber);
            while(true) {
                System.out.println("Waiting for some client to connect with this server....");
                // accept incoming connection request from a client
                s = serv.accept();  // blocking call
                System.out.println("New client has been connected!");
                // get the input (byte) stream from the data socket object and transform the byte stream into a
                // more comfortable BufferedReader character stream
                inStreamReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                // receive request from client
                String request = inStreamReader.readLine();  // blocking call
                System.out.println("Server received a new request message from a client: ("+ request + ")");
                // get the output (byte) stream from the data socket object and transform the byte stream into a
                // more comfortable  PrintWriter character stream
                outStreamWriter = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
                // send response
                outStreamWriter.println("Server Response: OK");
                // close data socket to client
                s.close();
                System.out.println("Connection to client has been closed!\n");
            }
        } catch (IOException e) {
            System.err.println("IOException during some socket operation ....: " + e.getLocalizedMessage());
        }
    }
}