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


public class DriverList implements Iterable<Driver> {
    private ArrayList<Driver> driverList;

    /**
     * Default Constructor.
     */
    public DriverList() {
        this.driverList = new ArrayList<Driver>();
    }

    /**
     * General Constructor.
     * @param driverList ArrayList of driver objects.
     */
    public DriverList(ArrayList<Driver> driverList) {
        this.driverList = driverList;
    }

    /**
     * Add a driver to the driverList.
     * @param driver driver object
     */
    public void addDriver(Driver driver) { driverList.add(driver); }

    /**
     * Removes a driver from the driverList.
     * @param driver driver object
     */
    public void removeDriver(Driver driver) { driverList.remove(driver); }

    /**
     * Returns a driver object.
     * @param id integer driver ID.
     * @return driver object.
     */
    public Driver getDriver(String id) {
        for(int i = 0; i < driverList.size(); i++)
            if(driverList.get(i).getId().equals(id))
                return driverList.get(i);
        return null;
    }

    public Boolean hasDriver(String id) { return getDriver(id) != null; }

    /**
     * Gets the number of drivers within the driverList.
     * @return int numberOfDrivers
     */
    public int getNumberOfDrivers() { return driverList.size(); }

    /**
     * Returns an iterable driverList.
     * @return Iterator
     */
    public Iterator<Driver> iterator() { return driverList.iterator(); }
}
