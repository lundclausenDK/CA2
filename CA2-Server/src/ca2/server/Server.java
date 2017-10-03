package ca2.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    ExecutorService es = Executors.newFixedThreadPool(30);
    public static ArrayList<ClientPocket> clients = new ArrayList();

    public void startServer() {
        try {
            serverSocket = new ServerSocket(8081);
            
            es.execute(new Postman());
            System.out.println("server is running");
            while (true) {
                
                
                clientSocket = serverSocket.accept();
                ClientPocket cp = new ClientPocket(clientSocket);
                clients.add(cp);
                es.execute(cp);
                
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());;
        }
    }

}
