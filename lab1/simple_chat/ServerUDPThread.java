import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;

public class ServerUDPThread extends Thread{
    Server server;
    HashMap<Integer, Socket> clients = new HashMap<Integer, Socket>();

    public ServerUDPThread(Server server) {
        this.server = server;
        this.clients = server.clients;



    }

    @Override
    public void run() {
        DatagramSocket socket = null;
        int portNumber = server.portNumber;

        try{
            socket = new DatagramSocket(portNumber);
            byte[] receiveBuffer = new byte[1024];

            while(true) {
                Arrays.fill(receiveBuffer, (byte)0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String message = new String(receivePacket.getData()).trim();
//                System.out.println(message.replaceFirst("\\s++$", "")); .trim()



//                server.sendMessageFromUserViaUDP(receivePacket.getPort(), message);

                int senderId = -1;
                for (int i : clients.keySet()) {
                    if(clients.get(i).getPort() == receivePacket.getPort()){  // !!! locatport ?
                        senderId = i;
                        break;
                    }
                }
                System.out.println("Received message from: " + senderId);
                byte[] sendBuffer = ("(" + senderId + ") " + message).getBytes();
                for (int i : clients.keySet()) {
                    if(i != senderId){
                        Socket client = clients.get(i);
                        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, client.getLocalAddress(), client.getPort());
                        socket.send(sendPacket);
                    }
                }

                // print received message
//                System.out.println("received msg: " + msg);

                // send message to client

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}

