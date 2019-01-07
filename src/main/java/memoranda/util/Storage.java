/**
 * Storage.java
 * Created on 12.02.2003, 0:58:42 Alex
 * Package: net.sf.memoranda.util
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda.util;

import main.java.memoranda.Note;
import main.java.memoranda.NoteList;
import main.java.memoranda.Project;
import main.java.memoranda.ResourcesList;
import main.java.memoranda.TaskList;
/**
 * 
 */
/*$Id: Storage.java,v 1.4 2004/01/30 12:17:42 alexeya Exp $*/
public interface Storage {
            
    TaskList openTaskList(Project prj);    
    void storeTaskList(TaskList tl, Project prj);
    
    NoteList openNoteList(Project prj);
    void storeNoteList(NoteList nl, Project prj);
    
    void storeNote(Note note, javax.swing.text.Document doc);    
    javax.swing.text.Document openNote(Note note);
    void removeNote(Note note);
    
    String getNoteURL(Note note);
    
    void openProjectManager();    
    void storeProjectManager();
    
    void openEventsManager();
    void storeEventsManager();
    
    void openMimeTypesList();
    void storeMimeTypesList();
    
    void createProjectStorage(Project prj);
    void removeProjectStorage(Project prj);
   
    ResourcesList openResourcesList(Project prj);
    void storeResourcesList(ResourcesList rl, Project prj);
    
    void restoreContext();
    void storeContext(); 
       
}
