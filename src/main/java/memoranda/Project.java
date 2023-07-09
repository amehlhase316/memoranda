/**
 * Project.java
 * Created on 11.02.2003, 16:11:47 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import main.java.memoranda.date.CalendarDate;

/**
 *
 */

/*$Id: Project.java,v 1.5 2004/11/22 10:02:37 alexeya Exp $*/
public interface Project {
    /**
     * Flag for SCHEDULED.
     */
    int SCHEDULED = 0;
    /**
     * Flag for ACTIVE.
     */
    int ACTIVE = 1;
    /**
     * Flag for COMPLETED.
     */
    int COMPLETED = 2;
    /**
     * Flag for FROZEN.
     */
    int FROZEN = 4;
    /**
     * Flag for FAILED.
     */
    int FAILED = 5;

    /**
     * Getter for ID.
     * @return ID
     */
    String getID();

    /**
     * Getter for StartDate.
     * @return StartDate
     */
    CalendarDate getStartDate();

    /**
     * Setter for StartDate.
     * @param date
     */
    void setStartDate(CalendarDate date);

    /**
     * Getter for EndDate.
     * @return EndDate
     */
    CalendarDate getEndDate();

    /**
     * Setter for EndDate.
     * @param date
     */
    void setEndDate(CalendarDate date);

    /**
     * Getter for Title.
     * @return Title
     */
    String getTitle();

    /**
     * Setter for Title.
     * @param title
     */
    void setTitle(String title);

    /**
     * Setter for Description.
     * @param description
     */
    void setDescription(String description);

    /**
     * Getter for Description.
     * @return Description
     */
    String getDescription();

    /**
     * Getter for Status.
     * @return Status
     */
    int getStatus();
    //int getProgress();
    //TaskList getTaskList();
    //NoteList getNoteList();
    //ResourcesList getResourcesList();

    /**
     * Adds attribute frozen.
     */
    void freeze();

    /**
     * Removes attribute frozen.
     */
    void unfreeze();
}
