import java.io.BufferedReader;
import java.io.IOException;

public class ClientTCPListener extends Thread{

    BufferedReader in;

    public ClientTCPListener(BufferedReader in){
        this.in = in;
    }

    @Override
    public void run() {
        while (true){
            String response = null;
            try {
                response = in.readLine();
                System.out.println(response);
            } catch (IOException e) {
                System.out.println("Connection lost...");
                break;
            }
        }
    }
}
