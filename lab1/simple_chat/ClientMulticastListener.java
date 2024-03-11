import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class ClientMulticastListener extends Thread{
    public MulticastSocket socketUDP = null;
    int portNumber;
    String multicastAddress;
    public ClientMulticastListener(int portNumber, String multicastAddress){
        this.portNumber = portNumber;
        this.multicastAddress = multicastAddress;

    }

    @Override
    public void run() {
        while (true){

            byte[] receiveBuffer = new byte[1024];

            try {
                socketUDP = new MulticastSocket(portNumber);
                InetAddress group = InetAddress.getByName(multicastAddress);
                socketUDP.joinGroup(group);
                while (true) {

                    // receive message from server
                    Arrays.fill(receiveBuffer, (byte) 0);
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    socketUDP.receive(receivePacket);

                    // print message from server
                    String msg = new String(receivePacket.getData());
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
