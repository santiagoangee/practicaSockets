package co.edu.eafit.angeeRestrepo.practicaSockets;

/**
 *
 * @author Santiago Angee Agudelo
 */
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.net.ServerSocket;
import java.io.PrintWriter;

public class ChatServer {

    /**
     * @param args the command line arguments
     */
    public static ArrayList<Socket> connectionArray = new ArrayList<>();
    public static ArrayList<String> currentUsers = new ArrayList<>();
    
            
    public static void main(String[] args) throws IOException
    {
        try  {
            final int PORT = 242;
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("waiting for clients");
            
            while (true) {
                Socket socket = server.accept();
                connectionArray.add(socket);
                
                System.out.println("Client connected from: " + socket.getLocalAddress().getHostName());
                
                AddUserName(socket);
                
                ChatServerReturn chat = new ChatServerReturn(socket);
                Thread t = new Thread(chat);
                t.start();
                       
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public static void AddUserName(Socket x) throws IOException
    {
        Scanner INPUT = new Scanner(x.getInputStream());
        String UserName = INPUT.nextLine();
        currentUsers.add(UserName);
        
        for(int i = 0; i < ChatServer.connectionArray.size(); ++i) {
            
            Socket tempSocket = ChatServer.connectionArray.get(i);
            PrintWriter out = new PrintWriter(tempSocket.getOutputStream());
            out.println("#?!" + currentUsers);
            out.flush();
        }
    }
}
