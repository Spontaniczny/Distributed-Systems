package sr.ice.client;

import HouseAdultsOnly.SecuritySystemPrx;
import HouseAdultsOnly.ThermostatPrx;
import HouseDevices.ClockPrx;
import HouseDevices.FridgePrx;
import HouseDevices.TimeOfDay;
import com.zeroc.Ice.*;

import java.lang.Exception;
import java.util.Map;
import java.util.Objects;

public class HouseClient {
    public static void main(String[] args) {
        int status = 0;
        Communicator communicator = null;

        try {
            communicator = Util.initialize(args);


            ObjectPrx clockBase = communicator.stringToProxy("clock/clock1:tcp -h 127.0.0.2 -p 10000 -z : udp -h 127.0.0.2 -p 10000 -z");
            ObjectPrx fridgeBase = communicator.stringToProxy("fridge/fridge1:tcp -h 127.0.0.2 -p 10000 -z : udp -h 127.0.0.2 -p 10000 -z");

            ObjectPrx SecurityBase = communicator.stringToProxy("securitySystem/securitySystem1:tcp -h 127.0.0.2 -p 10001 -z : udp -h 127.0.0.2 -p 10001 -z");
            ObjectPrx ThermostatBase = communicator.stringToProxy("thermostat/thermostat1:tcp -h 127.0.0.2 -p 10001 -z : udp -h 127.0.0.2 -p 10001 -z");

            ClockPrx clock = ClockPrx.checkedCast(clockBase);
            FridgePrx fridge = FridgePrx.checkedCast(fridgeBase);

            SecuritySystemPrx securitySystem = SecuritySystemPrx.checkedCast(SecurityBase);
            ThermostatPrx thermostat = ThermostatPrx.checkedCast(ThermostatBase);

            if (clock == null) throw new Error("Invalid proxy");
            if (fridge == null) throw new Error("Invalid proxy");
            if (securitySystem == null) throw new Error("Invalid proxy");
            if (thermostat == null) throw new Error("Invalid proxy");

            String line = null;
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

            do {
                try {
                    boolean wasLogedIn = false;
                    System.out.println("Select mode");
                    System.out.println("1) HouseDevices");
                    System.out.println("2) HouseAdultsOnly");
                    System.out.println("type 'exit!' to exit");
                    System.out.print("==> ");
                    line = in.readLine();
                    switch (line) {
                        case "1":
                            System.out.println("You entered HouseDevices");
                            do {
                                try {
                                    System.out.println("Choose device:");
                                    System.out.println("1) Clock");
                                    System.out.println("2) Fridge");
                                    System.out.println("3) exit");
                                    System.out.print("==> ");
                                    line = in.readLine();
                                    switch (line) {
                                        case "1":
                                            System.out.println("You have chosen clock");
                                            do {
                                                try {
                                                    System.out.println("Possible actions for clock");
                                                    System.out.println("1) getTime");
                                                    System.out.println("2) setTime");
                                                    System.out.println("3) exit");
                                                    System.out.print("==> ");
                                                    line = in.readLine();
                                                    switch (line) {
                                                        case "1":
                                                            TimeOfDay clockTime;
                                                            clockTime = clock.getTime();
                                                            System.out.println("Time on clock: " + clockTime.hour + ":" + clockTime.minute + ":" + clockTime.second);
                                                            break;
                                                        case "2":
                                                            short h, m, s;
                                                            System.out.print("Enter hour:");
                                                            h = Short.parseShort(in.readLine());
                                                            System.out.print("Enter minutes:");
                                                            m = Short.parseShort(in.readLine());
                                                            System.out.print("Enter seconds:");
                                                            s = Short.parseShort(in.readLine());
                                                            clock.setTime(new TimeOfDay(h, m, s));
                                                            System.out.println("Time was set to: " + h + ":" + m + ":" + s);
                                                            break;
                                                        case "3":
                                                            break;
                                                        default:
                                                            System.out.println("???");
                                                    }
                                                } catch (Exception ex) {
                                                    ex.printStackTrace(System.err);
                                                }
                                            }while(!Objects.equals(line, "3"));
                                            break;
                                        case "2":
                                            System.out.println("You have chosen fridge");
                                            do {
                                                try {
                                                    System.out.println("Possible actions for fridge");
                                                    System.out.println("1) isOn");
                                                    System.out.println("2) TurnOn");
                                                    System.out.println("3) TurnOff");
                                                    System.out.println("4) putFood");
                                                    System.out.println("5) getListOfFoods");
                                                    System.out.println("6) eatFood");
                                                    System.out.println("7) exit");
                                                    System.out.print("==> ");
                                                    line = in.readLine();
                                                    switch (line) {
                                                        case "1":
                                                            boolean fridgeStatus = fridge.isOn();
                                                            if(fridgeStatus){
                                                                System.out.println("Fridge is working");
                                                            } else {
                                                                System.out.println("Fridge is not working");
                                                            }
                                                            break;
                                                        case "2":
                                                            fridge.turnOn();
                                                            break;
                                                        case "3":
                                                            fridge.turnOff();
                                                            break;
                                                        case "4":
                                                            System.out.println("Enter food name");
                                                            String foodName = in.readLine();
                                                            System.out.println("Enter food quantity");
                                                            int quantity = Integer.parseInt(in.readLine());
                                                            fridge.putFood(foodName, quantity);
                                                            break;
                                                        case "5":
                                                            Map<String, Integer> listOfFood =  fridge.getListOfFoods();
                                                            System.out.println("Food list:");
                                                            for(String key: listOfFood.keySet()){
                                                                System.out.println(key + ":" + listOfFood.get(key));
                                                            }
                                                            break;
                                                        case "6":
                                                            System.out.println("Enter food name");
                                                            String foodName1 = in.readLine();
                                                            System.out.println("Enter food quantity");
                                                            int quantity1 = Integer.parseInt(in.readLine());
                                                            fridge.eatFood(foodName1, quantity1);
                                                            break;
                                                        case "7":
                                                            break;
                                                        default:
                                                            System.out.println("???");
                                                    }
                                                } catch (Exception ex) {
                                                    ex.printStackTrace(System.err);
                                                }
                                            }while(!Objects.equals(line, "7"));
                                            break;
                                        case "3":
                                            break;
                                        default:
                                            System.out.println("???");
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace(System.err);
                                }
                            }while(!Objects.equals(line, "3"));
                            break;
                        case "2":
                            do {
                                System.out.println("You are trying to enter HouseAdultsOnly");

                                try {
                                    System.out.println("Enter password or type exit to leave");
                                    System.out.print("==> ");
                                    line = in.readLine();
                                    if (line.equals("123")) {
                                        System.out.print("Correct password!");
                                        System.out.println("You entered HouseAdultsOnly");
                                        wasLogedIn = true;
                                        do {
                                            try {
                                                System.out.println("Choose device:");
                                                System.out.println("1) SecuritySystem");
                                                System.out.println("2) Thermostat");
                                                System.out.println("3) exit");
                                                System.out.print("==> ");
                                                line = in.readLine();
                                                switch (line) {
                                                    case "1":
                                                        System.out.println("You have chosen SecuritySystem");
                                                        do {
                                                            try {
                                                                System.out.println("Possible actions for SecuritySystem");
                                                                System.out.println("1) ListOfDangers");
                                                                System.out.println("2) exit");
                                                                System.out.print("==> ");
                                                                line = in.readLine();
                                                                if (line.equals("1")) {
                                                                    String[] securityStatus = securitySystem.getStatus();
                                                                    System.out.println(securityStatus[0]);
                                                                } else if(!line.equals("2")){
                                                                    System.out.println("???");
                                                                }
                                                            } catch (Exception ex) {
                                                                ex.printStackTrace(System.err);
                                                            }
                                                        }while(!Objects.equals(line, "2"));
                                                        break;
                                                    case "2":
                                                        System.out.println("You have chosen thermostat");
                                                        do {
                                                            try {
                                                                System.out.println("Possible actions for thermostat");
                                                                System.out.println("1) isOn");
                                                                System.out.println("2) TurnOn");
                                                                System.out.println("3) TurnOff");
                                                                System.out.println("4) getTemp");
                                                                System.out.println("5) setTemp");
                                                                System.out.println("6) exit");
                                                                System.out.print("==> ");
                                                                line = in.readLine();
                                                                switch (line) {
                                                                    case "1":
                                                                        boolean thermostatStatus = thermostat.isOn();
                                                                        if(thermostatStatus){
                                                                            System.out.println("thermostat is working");
                                                                        } else {
                                                                            System.out.println("thermostat is not working");
                                                                        }
                                                                        break;
                                                                    case "2":
                                                                        thermostat.turnOn();
                                                                        break;
                                                                    case "3":
                                                                        thermostat.turnOff();
                                                                        break;
                                                                    case "4":
                                                                        System.out.println("Current temperature: " + thermostat.getTemp());
                                                                        break;
                                                                    case "5":
                                                                        System.out.println("Enter new temperature: ");
                                                                        int newTemp = Integer.parseInt(in.readLine());
                                                                        thermostat.setTemp(newTemp);
                                                                        break;
                                                                    case "6":
                                                                        break;
                                                                    default:
                                                                        System.out.println("???");
                                                                }
                                                            } catch (Exception ex) {
                                                                ex.printStackTrace(System.err);
                                                            }
                                                        }while(!Objects.equals(line, "6"));
                                                        break;
                                                    case "3":
                                                        break;
                                                    default:
                                                        System.out.println("???");
                                                }
                                            } catch (Exception ex) {
                                                ex.printStackTrace(System.err);
                                            }
                                        } while(!Objects.equals(line, "3"));
                                    } else if (line.equals("exit")) {
                                        break;
                                    }
                                    else {
                                        System.out.println("Wrong password >:C");
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace(System.err);
                                }
                            } while(!Objects.equals(line, "exit") && !wasLogedIn);
                            break;
                        case "exit!":
                            break;
                        default:
                            System.out.println("???");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace(System.err);
                }
            }while (!Objects.equals(line, "exit!"));
        } catch (LocalException e) {
            e.printStackTrace();
            status = 1;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            status = 1;
        }
        if (communicator != null) { //clean
            try {
                communicator.destroy();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                status = 1;
            }
        }
        System.exit(status);
    }
}
