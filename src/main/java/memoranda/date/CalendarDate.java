/**
 * CalendarDate.java
 * Created on 11.02.2003, 18:02:02 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda.date;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import main.java.memoranda.util.Local;
import main.java.memoranda.util.Util;

/**
 *
 */
/*$Id: CalendarDate.java,v 1.3 2004/01/30 12:17:41 alexeya Exp $*/
public class CalendarDate {

    private int _year;
    private int _month;
    private int _day;

    public static Calendar dateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public CalendarDate() {
        this(Calendar.getInstance());
    }

    public CalendarDate(int day, int month, int year) {
        _year = year;
        _month = month;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, _year);
        cal.set(Calendar.MONTH, _month);cal.getTime();
        int dmax = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (day <= dmax)
          _day = day;
        else
          _day = dmax;

    }

    public CalendarDate(Calendar cal) {
        _year = cal.get(Calendar.YEAR);
        _day = cal.get(Calendar.DAY_OF_MONTH);
        _month = cal.get(Calendar.MONTH);
    }

    public CalendarDate(Date date) {
        this(dateToCalendar(date));
    }

    public CalendarDate(String date) {
        int[] d = Util.parseDateStamp(date);
        _day = d[0];
        _month = d[1];
        _year = d[2];

    }

    public static CalendarDate today() {
        return new CalendarDate();
    }

    public static CalendarDate yesterday() {
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.DATE, false);
        return new CalendarDate(cal);
    }

    public static CalendarDate tomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.DATE, true);
        return new CalendarDate(cal);
    }

    public static Calendar toCalendar(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.getTime();
        return cal;
    }

    public static Date toDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    public Calendar getCalendar() {
        return toCalendar(_day, _month, _year);
    }

    public Date getDate() {
        return toDate(_day, _month, _year);
    }

    public int getDay() {
        return _day;
    }

    public int getMonth() {
        return _month;
    }

    public int getYear() {
        return _year;
    }

    public boolean equals(Object object) {
        if (object.getClass().isInstance(CalendarDate.class)) {
            CalendarDate d2 = (CalendarDate) object;
            return ((d2.getDay() == getDay()) && (d2.getMonth() == getMonth()) && (d2.getYear() == getYear()));
        }
        else if (object.getClass().isInstance(Calendar.class)) {
            Calendar cal = (Calendar) object;
            return this.equals(new CalendarDate(cal));
        }
        else if (object.getClass().isInstance(Date.class)) {
            Date d = (Date) object;
            return this.equals(new CalendarDate(d));
        }
        return super.equals(object);
    }

    public boolean equals(CalendarDate date) {
        if (date == null) return false;
        return ((date.getDay() == getDay()) && (date.getMonth() == getMonth()) && (date.getYear() == getYear()));
    }

    public boolean before(CalendarDate date) {
        if (date == null) return true;
        return this.getCalendar().before(date.getCalendar());
    }

    public boolean after(CalendarDate date) {
        if (date == null) return true;
        return this.getCalendar().after(date.getCalendar());
    }

    public boolean inPeriod(CalendarDate startDate, CalendarDate endDate) {
        return (after(startDate) && before(endDate)) || equals(startDate) || equals(endDate);
    }

    public String toString() {
        return Util.getDateStamp(this);
    }  
    
    public String getFullDateString() {
        return Local.getDateString(this, DateFormat.FULL);
    }
    
    public String getMediumDateString() {
        return Local.getDateString(this, DateFormat.MEDIUM);
    }
    
    public String getLongDateString() {
        return Local.getDateString(this, DateFormat.LONG);
    }
    
    public String getShortDateString() {
        return Local.getDateString(this, DateFormat.SHORT);
    }
    

}
