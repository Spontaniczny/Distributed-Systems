import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerClientHandler extends Thread{
    Server server;
    int clientId;
    Socket clientSocket;

    public ServerClientHandler(Server server, int clientId, Socket clientSocket) {
        this.server = server;
        this.clientId = clientId;
        this.clientSocket = clientSocket;
//        System.out.println(clientSocket.getPort() + "   " + clientSocket.getLocalPort());
    }

    public void run() {
        try {
            while(true) {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String message = in.readLine();
//                if (message != null) {
                    server.sendMessageFromUserViaTCP(clientId, message);
//                }
            }
        } catch (IOException e) {
            System.out.println(clientId + " disconnected...");
            try {
                server.disconnectUser(clientId);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
