package ca2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    ExecutorService es = Executors.newFixedThreadPool(30);

    public void startServer() {
        try {
            serverSocket = new ServerSocket(8081);

            System.out.println("server is running");
            while (true) {
                
                
                clientSocket = serverSocket.accept();
                es.execute(new ClientPocket(clientSocket));
                
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
