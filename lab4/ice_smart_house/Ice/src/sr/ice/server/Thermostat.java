package sr.ice.server;

import HouseAdultsOnly.ThermostatIsOff;
import HouseAdultsOnly.TooCold;
import HouseAdultsOnly.TooHot;
import com.zeroc.Ice.Current;

public class Thermostat implements HouseAdultsOnly.Thermostat {

    boolean isOn = true;
    int currentTemp = 20;
    @Override
    public boolean isOn(Current current) {
        System.out.println("User check thermostat status");
        return isOn;
    }

    @Override
    public void turnOn(Current current) {
        System.out.println("User turned on thermostat");
        isOn = true;
    }

    @Override
    public void turnOff(Current current) {
        System.out.println("User turned off thermostat");
        isOn = false;
    }

    @Override
    public int getTemp(Current current) throws ThermostatIsOff {
        System.out.println("User checked temperature");
        if(!isOn) {
            throw new ThermostatIsOff();
        }
        return currentTemp;
    }

    @Override
    public void setTemp(int temp, Current current) throws TooCold, TooHot, ThermostatIsOff {
        if(!isOn) {
            throw new ThermostatIsOff();
        } else if(temp < 5){
            throw new TooCold();
        } else if (temp >= 40) {
            throw new TooHot();
        }
        System.out.println("User set new temperature");
        this.currentTemp = temp;
    }
}
