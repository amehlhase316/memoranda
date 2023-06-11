/**
 * TaskList.java
 * Created on 21.02.2003, 12:25:16 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;
import java.util.Collection;

import main.java.memoranda.date.CalendarDate;
/**
 * 
 */
/*$Id: TaskList.java,v 1.8 2005/12/01 08:12:26 alexeya Exp $*/
public interface TaskList {

	Project getProject();
    Task getTask(String id);

    Task createTask(CalendarDate startDate, CalendarDate endDate, String text, int priority, long effort, String description, String parentTaskId);

    void removeTask(Task task);

    boolean hasSubTasks(String id);
    
	boolean hasParentTask(String id);

	Collection getTopLevelTasks();
	
    Collection getAllSubTasks(String taskId);
    Collection getActiveSubTasks(String taskId,CalendarDate date);
    
//    public void adjustParentTasks(Task t);
    
    long calculateTotalEffortFromSubTasks(Task t);
    CalendarDate getLatestEndDateFromSubTasks(Task t);
    CalendarDate getEarliestStartDateFromSubTasks(Task t);
    long[] calculateCompletionFromSubTasks(Task t);

    nu.xom.Document getXMLContent();

}
