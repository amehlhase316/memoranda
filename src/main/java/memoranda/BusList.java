package main.java.memoranda;

import main.java.memoranda.Bus;

import java.util.ArrayList;
import java.util.List;

public class BusList {
    private List<Bus> buses;

    public BusList(){
        buses = new ArrayList<>();
    }
    public void addBus(Bus bus) {
        buses.add(bus);
    }

    public void removeBus(int id) {
        buses.remove(id);
    }

    public int getNumberOfBuses() {
        return buses.size();
    }

    public boolean hasBus(int id) {
        if(getBus(id) != null)
            return true;
        return false;
    }

    public Bus getBus(int id){
        for(int i = 0; i < buses.size(); ++i){
            if(buses.get(i).getID() == id) {
                return buses.get(i);
            }
        }
        return null;
    }
}
