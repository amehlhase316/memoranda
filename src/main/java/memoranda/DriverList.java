/**
 * TaskListImpl.java
 * Created on 21.02.2003, 12:29:54 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.util.Util;
import nu.xom.*;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;



public class DriverList {
    private Hashtable drivers;
    private int numberOfDrivers;

    public DriverList() {
        drivers = new Hashtable();
        numberOfDrivers = 0;

        //import the drivers from JSON here
    }

    public Driver getDriver(int id) { return (Driver) drivers.get(id); }

    public void addDriver(Driver driver) {
        drivers.put(driver.getID(), driver);
        numberOfDrivers++;
    }

    public void removeDriver(int id) {
        drivers.remove(id);
        numberOfDrivers--;
    }

    public int getNumberOfDrivers() { return numberOfDrivers; }
}
