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
    public static ArrayList<Socket> ConnectionArray = new ArrayList<>();
    public static ArrayList<String> CurrentUsers = new ArrayList<>();
    
            
    public static void main(String[] args) throws IOException
    {
        try  {
            final int PORT = 242;
            ServerSocket SERVER = new ServerSocket(PORT);
            System.out.println("waiting for clients");
            
            while (true) {
                Socket SOCKET = SERVER.accept();
                ConnectionArray.add(SOCKET);
                
                System.out.println("Client connected from: " + SOCKET.getLocalAddress().getHostName());
                
                       
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public static void AddUserName(Socket X) throws IOException
    {
        Scanner INPUT = new Scanner(X.getInputStream());
        String UserName = INPUT.nextLine();
        CurrentUsers.add(UserName);
        
        for(int i = 1; i <= ChatServer.ConnectionArray.size(); ++i) {
            
            Socket tempSocket = ChatServer.ConnectionArray.get(i-1);
            PrintWriter out = new PrintWriter(tempSocket.getOutputStream());
            out.println("#?!" + CurrentUsers);
            out.flush();
        }
    }
}
