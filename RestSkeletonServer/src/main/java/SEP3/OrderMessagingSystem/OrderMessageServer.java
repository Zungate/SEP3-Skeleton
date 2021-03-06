package SEP3.OrderMessagingSystem;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class OrderMessageServer extends Thread
{
    private ServerSocket serverSocket;


    public OrderMessageServer() throws IOException
    {
        serverSocket = new ServerSocket(9777);
    }

    public void run() {
        while(true) {
            try {
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();

                System.out.println("Just connected to " + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());
                System.out.println(in.readUTF());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
                        + "\nGoodbye!");
                server.close();

            }
            catch (SocketTimeoutException s) {
                System.out.println( "Server timed out");
                break;
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String [] args) {
        try {
            Thread t = new OrderMessageServer();
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
