package sr.ice.server;

import com.zeroc.Ice.Current;

public class SecuritySystem implements HouseAdultsOnly.SecuritySystem {

    String[] listOfDangers = {"Alert! Mozliwosc niezdania rozprochow!"};

    @Override
    public String[] getStatus(Current current) {
        System.out.println("User checked security status (it's bad (very bad))");
        return listOfDangers;
    }
}
