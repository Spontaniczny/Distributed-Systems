import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {

    int portNumber;
    ServerSocket serverSocket;
    HashMap<Integer, Socket> clients = new HashMap<Integer, Socket>();
    public Server(int portNumber) throws IOException {
        this.portNumber = portNumber;
        this.serverSocket = new ServerSocket(portNumber);
    }

    private void acceptNewClients() throws IOException {
        System.out.println("Server is running...");
        int lastClientId = 1;
        try {
            while (true) {
                // accept client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected. ID: " + lastClientId);
                sendGlobalMessage("Client " + lastClientId + " has joined the chat.");
                clients.put(lastClientId, clientSocket);

                // create client server connection thread
                Thread thread = new ServerClientHandler(this, lastClientId, clientSocket);
                thread.start();
                lastClientId += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if (serverSocket != null){
                serverSocket.close();
            }
        }
    }

    public void CreateUDPThread(){
        Thread udpThread = new ServerUDPThread(this);
        udpThread.start();
    }

    public void sendMessageFromUserViaTCP(int senderId, String msg) throws IOException {
        System.out.println("Received message from: " + senderId);
        for (int i : clients.keySet()) {
            if(i != senderId){
                PrintWriter out = new PrintWriter(clients.get(i).getOutputStream(), true);
                out.println("(" + senderId + ") " + msg);
            }
        }
    }

//    public void sendMessageFromUserViaUDP(int senderPort, String msg) throws IOException {
//        System.out.println("ASD");
////        It is in ServerUDPThread.java file
//    }

    public void sendGlobalMessage(String msg) throws IOException {
        System.out.println("Sending global message: " + msg);
        for (int i : clients.keySet()) {
            PrintWriter out = new PrintWriter(clients.get(i).getOutputStream(), true);
            out.println(msg);
        }
    }

    public void disconnectUser(int userId) throws IOException {
        clients.remove(userId);
        String msg = "User " + userId + " disconnects...";
        sendGlobalMessage(msg);
    }

    public static void main(String[] args) throws IOException {

        int portNumber = 9008;
        Server server = new Server(portNumber);
        server.CreateUDPThread();
        server.acceptNewClients();
    }
}
