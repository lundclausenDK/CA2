package ca2.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientPocket extends Thread{
    private String chatterName;
    private PrintWriter out;
    private BufferedReader in;
    private Socket clientSocket;
    
    public ClientPocket(Socket clientSocket){
        this.clientSocket = clientSocket;
        try {
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void run(){
        try {
            String login = in.readLine();
            String[] loginArray = login.split(":");
            if(loginArray[0].equals("LOGIN")){                
                chatterName = loginArray[1];
                Postman.messages.put("MSG:*:Welcome to " + chatterName+":server");
                Postman.messages.put("CLIENTLIST:");
                boolean running = true;

                while (running) {
                    String incomming = in.readLine();
                    String[] incommingArray = incomming.split(":", 2);
                    switch (incommingArray[0]) {
                        case "MSG": {
                            String recieversAndMessage = incommingArray[1];
                            Postman.messages.put("MSG:"+recieversAndMessage + ":" + chatterName);
                            break;
                        }
                        
                        case "LOGOUT":{
                            running = false;
                            break;
                        }
                    }
                }
            }
            out.println("Goodbye");
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ClientPocket.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            Server.clients.remove(this);
            try {
                Postman.messages.put("CLIENTLIST:");
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(ClientPocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void sendMessage(String message){
        out.println(message);
    }

    /**
     * @return the name
     */
    public String getChatterName() {
        return chatterName;
    }
}
