package main.java.memoranda;

import main.java.memoranda.Bus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BusList implements Iterable<Bus> {
    private List<Bus> buses;

    public BusList(){
        buses = new ArrayList<>();
    }

    public BusList(ArrayList<Bus> busList){
        buses = busList;
    }

    public void addBus(Bus bus) {
        buses.add(bus);
    }

    public void removeBus(Bus bus) {
        buses.remove(bus);
    }

    public int getNumberOfBuses() {
        return buses.size();
    }

    public boolean hasBus(String id) {
        if(getBus(id) != null)
            return true;
        return false;
    }

    public Bus getBus(String id){
        for(int i = 0; i < buses.size(); ++i){
            if(buses.get(i).getId().equals(id)) {
                return buses.get(i);
            }
        }
        return null;
    }

    @Override
    public Iterator<Bus> iterator() {
        return buses.iterator();
    }
}
