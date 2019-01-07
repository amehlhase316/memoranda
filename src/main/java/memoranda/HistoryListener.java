/**
 * HistoryListener.java
 * Created on 23.02.2003, 1:56:52 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

/**
 * 
 */
/*$Id: HistoryListener.java,v 1.2 2004/01/30 12:17:41 alexeya Exp $*/
public interface HistoryListener {

    /*void historyWasRolledBack();
    
    void historyWasRolledForward();*/
    
    void historyWasRolledTo(HistoryItem item);

}
