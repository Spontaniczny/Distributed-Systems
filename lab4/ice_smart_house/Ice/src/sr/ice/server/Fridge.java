package sr.ice.server;

import com.zeroc.Ice.Current;

import java.util.HashMap;
import java.util.Map;

public class Fridge implements HouseDevices.Fridge {

    boolean isOn = true;
    Map<String, Integer> listOfFoods = new HashMap<String, Integer>();
    @Override
    public boolean isOn(Current current) {
        System.out.println("User is checking status of fridge");
        return this.isOn;
    }

    @Override
    public void turnOn(Current current) {
        System.out.println("User turned On fridge");
        this.isOn = true;
    }

    @Override
    public void turnOff(Current current) {
        System.out.println("User turned Off fridge");
        this.isOn = false;
    }

    @Override
    public void putFood(String foodName, int quantity, Current current) {
        System.out.println("User put food to fridge");
        if(!this.listOfFoods.containsKey(foodName)){
            this.listOfFoods.put(foodName, quantity);
            return;
        }
        this.listOfFoods.put(foodName, this.listOfFoods.get(foodName) + quantity);
    }

    @Override
    public Map<String, Integer> getListOfFoods(Current current) {
        System.out.println("User is checking what is in the fridge");
        return this.listOfFoods;
    }


    @Override
    public void eatFood(String foodName, int quantity, Current current) {
        if(!this.listOfFoods.containsKey(foodName)){
            System.out.println("There is no food that you are looking for");
            return;
        }
        int currentQuantity = this.listOfFoods.get(foodName);
        currentQuantity -= quantity;
        if(currentQuantity <= 0){
            System.out.println("User wanted to eat too much! He ate all of " + foodName + " :///");
            this.listOfFoods.remove(foodName);
        } else {
            System.out.println("User ate some food");
            this.listOfFoods.put(foodName, currentQuantity);
        }
    }
}
