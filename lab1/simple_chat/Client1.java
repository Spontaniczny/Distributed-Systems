import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) throws IOException {
        Client client = new Client(9008, 9009, "localhost", "230.0.0.0");
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
