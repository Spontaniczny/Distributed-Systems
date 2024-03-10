import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class JavaUdpServer4 {

    public static void main(String args[])
    {
        System.out.println("JAVA UDP SERVER");
        DatagramSocket socket = null;
        int portNumber = 9008;

        try{
            socket = new DatagramSocket(portNumber);
            byte[] receiveBuffer = new byte[1024];

            while(true) {
                Arrays.fill(receiveBuffer, (byte)0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                // print received message
                String msg = new String(receivePacket.getData());
                System.out.println("received msg: " + msg + msg.length());

                byte[] sendBuffer;
                if (msg.trim().equals("PYTHON")){
                    sendBuffer = "YOU ARE PYTHON".getBytes();
                }
                else if (msg.trim().equals("JAVA")){
                    sendBuffer = "YOU ARE JAVA".getBytes();
                }
                else{
                    sendBuffer = "WHO ARE YOU".getBytes();
                }

                // send message to client
                

                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, receivePacket.getAddress(), receivePacket.getPort());
                socket.send(sendPacket);
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
