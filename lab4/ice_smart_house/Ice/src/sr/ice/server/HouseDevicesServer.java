package sr.ice.server;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

public class HouseDevicesServer {
    public void t1(String[] args) {
        int status = 0;
        Communicator communicator = null;

        try {
            communicator = Util.initialize(args);

            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h 127.0.0.2 -p 10000 -z : udp -h 127.0.0.2 -p 10000 -z");

            Clock clockServant = new Clock();
            Fridge fridgeServant = new Fridge();

            adapter.add(clockServant, new Identity("clock1", "clock"));
            adapter.add(fridgeServant, new Identity("fridge1", "fridge"));

            adapter.activate();

            System.out.println("Entering event processing loop for HouseDevices...");

            communicator.waitForShutdown();

        } catch (Exception e) {
            e.printStackTrace(System.err);
            status = 1;
        }
        if (communicator != null) {
            try {
                communicator.destroy();
            } catch (Exception e) {
                e.printStackTrace(System.err);
                status = 1;
            }
        }
        System.exit(status);
    }


    public static void main(String[] args) {
        HouseDevicesServer app = new HouseDevicesServer();
        app.t1(args);
    }
}
