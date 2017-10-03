package ca2.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    ExecutorService es = Executors.newFixedThreadPool(30);
    public static ClientPocket[] clients;

    public void startServer() {
        try {
            serverSocket = new ServerSocket(8081);
            

            System.out.println("server is running");
            while (true) {
                
                
                clientSocket = serverSocket.accept();
                es.execute(new ClientPocket(clientSocket));
                
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
