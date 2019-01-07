/**
 * Event.java
 * Created on 08.03.2003, 12:21:40 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 *-----------------------------------------------------
 */
package main.java.memoranda;
import java.util.Date;

import main.java.memoranda.date.CalendarDate;

/**
 * 
 */
/*$Id: Event.java,v 1.4 2004/07/21 17:51:25 ivanrise Exp $*/
public interface Event {
    
    String getId();
    
    //CalendarDate getDate();
    
    int getHour();
    
    int getMinute();
    
    //Date getTime();
    
    String getText();
    
    nu.xom.Element getContent();
    
    int getRepeat();
    
    CalendarDate getStartDate();
    CalendarDate getEndDate();
    int getPeriod();
    boolean isRepeatable();
    
    Date getTime();
    String getTimeString();
    
	boolean getWorkingDays();
    
}
