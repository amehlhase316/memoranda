/**
 * TaskListImpl.java
 * Created on 21.02.2003, 12:29:54 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;


public class DriverList extends JList<Driver> implements Iterable<Driver> {
    private ArrayList<Driver> drivers;


    public DriverList() {
        this.drivers = new ArrayList<Driver>();
    }

    public DriverList(ArrayList<Driver> drivers) {
        this.drivers = drivers;
    }

    public Driver getDriver(int id) { return (Driver) drivers.get(id); }

    public void addDriver(Driver driver) { drivers.add(driver); }

    public void removeDriver(int id) { drivers.remove(id); }

    public int getNumberOfDrivers() { return drivers.size(); }

    public Iterator<Driver> iterator() { return drivers.iterator(); }
}
