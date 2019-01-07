/**
 * CurrentDate.java
 * Created on 13.02.2003, 2:11:03 Alex
 * Package: net.sf.memoranda.date
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda.date;
import java.util.Collection;
import java.util.Vector;

/**
 *
 */
/*$Id: CurrentDate.java,v 1.4 2004/10/06 16:00:12 ivanrise Exp $*/
public class CurrentDate {

    private static CalendarDate _date = new CalendarDate();
    private static Vector dateListeners = new Vector();

    public static CalendarDate get() {
        return _date;
    }

    public static void set(CalendarDate date) {
        if (date.equals(_date)) return;
        _date = date;
        dateChanged(date);
    }

    public static void reset() {
        set(new CalendarDate());
    }

    public static void addDateListener(DateListener dl) {
        dateListeners.add(dl);
    }

    public static Collection getChangeListeners() {
        return dateListeners;
    }

    private static void dateChanged(CalendarDate date) {
        for (int i = 0; i < dateListeners.size(); i++)
            ((DateListener)dateListeners.get(i)).dateChange(date);
    }
}
