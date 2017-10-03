/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca2.server;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author craci
 */
public class Postman extends Thread {
    
    public static ArrayBlockingQueue<String> messages = new ArrayBlockingQueue(30);
    
    @Override
    public void run(){
        while(true){
            try {
                String toBeSend = messages.take();
                String[] toBeSendArray = toBeSend.split(":", 2);
                switch(toBeSendArray[0]){
                    case "MSG":{
                        String[] receiversAndMessages = toBeSendArray[1].split(":");
                        String[] recievers = receiversAndMessages[0].split(",");
                        for (ClientPocket client : Server.clients) {
                            for (String receiver : recievers) {
                                if (recievers[0].equals("*") || client.getChatterName().equals(receiver)) {
                                    client.sendMessage("MSGRES:" + receiversAndMessages[2] + ":" + receiversAndMessages[1]);
                                }
                            }
                        }
                        break;
                    }
                    
                    case "CLIENTLIST":{
                        String message = "CLIENTLIST:";
                        for (int i = 0; i < Server.clients.size(); i++) {
                            if(i != Server.clients.size()-1){
                                message += Server.clients.get(i).getChatterName()+",";
                            }else{
                                message += Server.clients.get(i).getChatterName();
                            }
                        }
                        
                        for (ClientPocket client : Server.clients) {
                            client.sendMessage(message);
                        }
                        
                        break;
                    }
                }
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
