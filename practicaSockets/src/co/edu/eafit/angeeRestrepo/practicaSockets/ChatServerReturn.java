/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.eafit.angeeRestrepo.practicaSockets;

/**
 *
 * @author santiago
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatServerReturn implements Runnable{
    
    Socket socket;
    private Scanner input;
    private PrintWriter output;
    String message = "";
    
    public ChatServerReturn(Socket x)
    {
        this.socket = x;
    }
  
    public void checkConnection() throws IOException
    {
        if(!socket.isConnected()) {
            for(int i = 0; i < ChatServer.connectionArray.size(); ++i)
            {
                if(ChatServer.connectionArray.get(i) == socket) {
                    ChatServer.connectionArray.remove(i);
                }
            }
            
            for(int i = 0; i < ChatServer.connectionArray.size(); ++i) {
                Socket tempSocket = ChatServer.connectionArray.get(i);
                PrintWriter tempOutput = new PrintWriter(tempSocket.getOutputStream());
                tempOutput.println(tempSocket.getLocalAddress().getHostName() + " disconnected");
                tempOutput.flush();
                //Show disconnection at Server
                System.out.println(tempSocket.getLocalAddress().getHostAddress() + " disconnected");
            }
        }
    }
    
    public void run()
    {
        try {
            try {
                input = new Scanner(socket.getInputStream());
                output = new PrintWriter(socket.getOutputStream());
                
                while(true) {
                    checkConnection();
                    
                    if(input.hasNext()) {
                        return;
                    }
                    
                    message =input.nextLine();
                    
                    System.out.println("Client said: " + message);
                    
                    for(int i = 0; i < ChatServer.connectionArray.size(); ++i) {
                        Socket tempSocket = ChatServer.connectionArray.get(i);
                        PrintWriter tempOutput = new PrintWriter(tempSocket.getOutputStream());
                        tempOutput.println(message);
                        tempOutput.flush();
                        System.out.println("Sent to: " + tempSocket.getLocalAddress().getHostName());
                    }
                }
            } finally {
                socket.close();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
