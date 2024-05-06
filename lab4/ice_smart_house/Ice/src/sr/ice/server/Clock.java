package sr.ice.server;

import HouseAdultsOnly.TooCold;
import HouseDevices.TimeOfDay;
import HouseDevices.WrongHourGiven;
import HouseDevices.WrongMinuteGiven;
import HouseDevices.WrongSecondGiven;
import com.zeroc.Ice.Current;

public class Clock implements HouseDevices.Clock {

    TimeOfDay currentTime = new TimeOfDay();
    @Override
    public TimeOfDay getTime(Current current) {
        System.out.println("User is checking time");
        return this.currentTime;
    }

    @Override
    public void setTime(TimeOfDay time, Current current) throws WrongHourGiven, WrongMinuteGiven, WrongSecondGiven {
        System.out.println("User is setting time");
        if(time.hour < 0 || time.hour > 23){
            throw new WrongHourGiven();
        } else if (time.minute < 0 || time.minute > 59) {
            throw new WrongMinuteGiven();
        } else if (time.second < 0 || time.second > 59) {
            throw new WrongSecondGiven();
        }
        this.currentTime = time;
    }
}
