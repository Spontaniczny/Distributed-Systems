import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class ClientUDPListener extends Thread{

    Client client;

    public DatagramSocket socketUDP = null;
    public ClientUDPListener(Client client){
        this.client = client;
    }

    @Override
    public void run() {
        while (true){

            byte[] receiveBuffer = new byte[1024];

            try {
                socketUDP = new DatagramSocket(client.socket.getLocalPort());
                client.socketUDP = socketUDP;
                while (true) {

                    // receive message from server
                    Arrays.fill(receiveBuffer, (byte) 0);
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    socketUDP.receive(receivePacket);

                    // print message from server
                    String msg = new String(receivePacket.getData()).trim();
                    System.out.println(msg);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally {
                if (socketUDP != null) {
                    socketUDP.close();
                }
            }
        }
    }
}
