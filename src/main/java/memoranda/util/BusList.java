package main.java.memoranda.util;

import main.java.memoranda.Bus;

import java.util.ArrayList;
import java.util.List;

public class BusList {
    private List<Bus> buses;

    public BusList(){
        buses = new ArrayList<>();
    }
    public Bus getBus(int id){
        return (Bus) buses.get(id);
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
}
