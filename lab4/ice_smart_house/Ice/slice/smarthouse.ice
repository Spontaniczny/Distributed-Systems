#ifndef CALC_ICE
#define CALC_ICE

module HouseDevices
{
  sequence<string> Devices;

  class TimeOfDay
  {
      short hour;         // 0 - 23
      short minute;       // 0 - 59
      short second;       // 0 - 59
  }

  dictionary<string, int> ListOfFoods;

  interface Fridge
  {
    idempotent bool isOn();
    idempotent void turnOn();
    idempotent void turnOff();
    void putFood(string foodName, int quantity);
    ListOfFoods getListOfFoods();
    void eatFood(string foodName, int quantity);
  };

  exception WrongHourGiven {
          string reason = "Hour must be between 0 and 23";
  };

  exception WrongMinuteGiven {
          string reason = "Minute must be between 0 and 59";
  };
  exception WrongSecondGiven {
          string reason = "Second must be between 0 and 59";
  };


  interface Clock
  {
    idempotent TimeOfDay getTime();
    idempotent void setTime(TimeOfDay time)
        throws WrongHourGiven, WrongMinuteGiven, WrongSecondGiven;
  }

};

module HouseAdultsOnly
{
    exception TooHot {
        string reason = "Temperature can not be set over 50";
    };

    exception TooCold {
        string reason = "Temperature can not be set below 5";
    };

    exception ThermostatIsOff{
        string reason = "Thermostat must be on to perform this action";
    }

    interface Thermostat
    {
    // int temp = 20;
    // bool isOn;
    idempotent bool isOn();
    idempotent void turnOn();
    idempotent void turnOff();
    idempotent int getTemp()
        throws ThermostatIsOff;
    idempotent void setTemp(int temp)
        throws TooHot, TooCold, ThermostatIsOff;
    }

    sequence<string> ListOfDangers;

    interface SecuritySystem
    {
    idempotent ListOfDangers getStatus();
    }
}

#endif
