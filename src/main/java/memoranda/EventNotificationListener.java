/**
 * EventNotificationListener.java
 * Created on 10.03.2003, 20:43:16 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

/**
 * 
 */
/*$Id: EventNotificationListener.java,v 1.2 2004/01/30 12:17:41 alexeya Exp $*/
public interface EventNotificationListener {

    void eventIsOccured(Event ev);
    
    void eventsChanged();

}
