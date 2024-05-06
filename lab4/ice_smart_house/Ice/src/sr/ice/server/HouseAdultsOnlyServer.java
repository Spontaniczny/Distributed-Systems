package sr.ice.server;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

public class HouseAdultsOnlyServer {
    public void t1(String[] args) {
        int status = 0;
        Communicator communicator = null;

        try {
            communicator = Util.initialize(args);

            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter2", "tcp -h 127.0.0.2 -p 10001 -z : udp -h 127.0.0.2 -p 10001 -z");

            Thermostat thermostatServant = new Thermostat();
            SecuritySystem securitySystemServant = new SecuritySystem();

            adapter.add(thermostatServant, new Identity("thermostat1", "thermostat"));
            adapter.add(securitySystemServant, new Identity("securitySystem1", "securitySystem"));

            adapter.activate();

            System.out.println("Entering event processing loop for HouseAdultsOnly...");

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
        HouseAdultsOnlyServer app = new HouseAdultsOnlyServer();
        app.t1(args);
    }
}
