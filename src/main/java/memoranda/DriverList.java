/**
 * TaskListImpl.java
 * Created on 21.02.2003, 12:29:54 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import java.util.ArrayList;
import java.util.List;



public class DriverList {
    private List<Driver> drivers;
    private int numberOfDrivers;

    public DriverList() {
        this.drivers = new ArrayList<Driver>();
        numberOfDrivers = 0;
    }

    public DriverList(List<Driver> drivers) {
        this.drivers = drivers;
        numberOfDrivers = drivers.size();
    }

    public Driver getDriver(int id) { return (Driver) drivers.get(id); }

    public void addDriver(Driver driver) {
        drivers.add(driver);
        numberOfDrivers++;
    }

    public void removeDriver(int id) {
        drivers.remove(id);
        numberOfDrivers--;
    }

    public int getNumberOfDrivers() { return numberOfDrivers; }
}
