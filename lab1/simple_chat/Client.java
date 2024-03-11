import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class Client {
    int portNumber;
    int portMulticastNumber;
    String hostName;
    String multicastAddress;
    Socket socket = null;
    PrintWriter out;
    Thread listener;
    Thread udpListener;
    Thread multicastListener;
    DatagramSocket socketUDP;

    public Client(int portNumber, int portMulticastNumber, String hostName, String multicastAddress){
        System.out.println("JAVA CLIENT");
        this.portNumber = portNumber;
        this.hostName = hostName;
        this.portMulticastNumber = portMulticastNumber;
        this.multicastAddress = multicastAddress;
    }

    public void connectToServer() throws IOException {
        socket = new Socket(hostName, portNumber);
    }

    public void startChatting() throws IOException {
        createMulticastListener();
        createUDPListener();
        createTCPListener();
    }

    public void createTCPListener() throws IOException {
        // in & out streams
        out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        listener = new ClientTCPListener(in);
        listener.start();
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        while(true) {
            String message = myObj.nextLine();  // Read user input
            messageAction(message, out);
        }
    }

    public void createUDPListener(){
        udpListener = new ClientUDPListener(this);
        udpListener.start();
    }

    public void createMulticastListener(){
        multicastListener = new ClientMulticastListener(portMulticastNumber, multicastAddress);
        multicastListener.start();
    }

    public void messageAction(String msg, PrintWriter out) throws UnknownHostException {
        if (msg.equals("U")){
            System.out.println("NEXT MESSAGE WILL BE SENT VIA UDP.");
            DatagramSocket socketUDP = null;

            try {
//                socketUDP = new DatagramSocket(socket.getLocalPort());
                socketUDP = this.socketUDP;
                InetAddress address = InetAddress.getByName(hostName);

                Scanner myObj = new Scanner(System.in);  // Create a Scanner object
                String message = myObj.nextLine();  // Read user input
                StringBuilder sb = new StringBuilder();
                while (!message.isEmpty()){
                    sb.append(message + "\n");
                    message = myObj.nextLine();
                }
                message = sb.toString();

                byte[] sendBuffer = message.getBytes();

                // send message to server
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                socketUDP.send(sendPacket);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        } else if (msg.equals("M")) {
            System.out.println("NEXT MESSAGE WILL BE SENT IN MULTICAST.");
            MulticastSocket socketMulticastUDP = null;

            try {
                socketMulticastUDP = new MulticastSocket();
                InetAddress address = InetAddress.getByName(multicastAddress);

                Scanner myObj = new Scanner(System.in);  // Create a Scanner object
                String message = myObj.nextLine();  // Read user input

                byte[] sendBuffer = message.getBytes();

                // send message to server
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portMulticastNumber);
                socketMulticastUDP.send(sendPacket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            out.println(msg);
        }
    }
    public static void main(String[] args) throws IOException {
        Client client = new Client(9008, 9009,"localhost", "230.0.0.0");
        try {
            client.connectToServer();
            client.startChatting();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client.socket != null){
                client.socket.close();
            }
        }
    }
}