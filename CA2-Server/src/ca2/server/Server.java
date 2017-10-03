package ca2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;

    public void startServer() {
        try {
            serverSocket = new ServerSocket(8081);

            System.out.println("server is running");
            while (true) {
                
                
                clientSocket = serverSocket.accept();
                System.out.println("Client recieved");
                
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
