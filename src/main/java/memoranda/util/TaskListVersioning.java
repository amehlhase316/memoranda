/*
 * Created on Feb 12, 2005
 *
 */
package main.java.memoranda.util;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import main.java.memoranda.Project;
import main.java.memoranda.ProjectManager;
import nu.xom.Attribute;
import nu.xom.DocType;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

/**
 * @author ryanho
 *
 * Upgrades data files from older versions to new versions
 */
public class TaskListVersioning {
    
    public static final String[] VERSIONS = new String[]{
            "-//Memoranda//DTD Tasklist 1.0//EN",
            "-//Memoranda//DTD Tasklist 1.1d1//EN"
    };

    public static DocType getCurrentDocType() {
        return new DocType("tasklist",TaskListVersioning.getCurrentVersionPublicId(),"tasklist.dtd");
    }
    
    public static String getCurrentVersionPublicId() {
        return VERSIONS[VERSIONS.length - 1];
    }
        
    public static int getIndexOfVersion(String publicId) {
        if (publicId == null) {
            // earlier versions do not have public ID, it is version 1.0 which is the first entry
            return 0;
        }
        for (int i = 0; i < VERSIONS.length; i++) {
            if (publicId.equals(VERSIONS[i])) {
                return i;
            }
        }
        Util.debug("Version " + publicId + " not found");
        return -1;
    }
    
    public static boolean upgradeTaskList(String publicId) {
        int vid = getIndexOfVersion(publicId);
        
        if (vid == (VERSIONS.length - 1)) {
            Util.debug("Version " + publicId + " is the latest version, skipping upgrade");
            return false;
        }
        else {
            // get all projects
            Vector projects = ProjectManager.getAllProjects();
            String[] projectIds = new String[projects.size()];
            int c = 0;
            for (Iterator iter = projects.iterator(); iter.hasNext();) {
                Project prj = (Project) iter.next();
                projectIds[c++] = prj.getID();
            }
            
            // keep upgrading until it's the current version
            while (vid < (VERSIONS.length - 1)) {
                if(vid == 0) {
                    upgrade1_1d1(projectIds);
                }            
                vid++;
            }
            return true;
        }
    }
    
    private static void upgrade1_1d1(String[] projectIds) {
        for (int i = 0; i < projectIds.length; i++) {
            Util.debug("Upgrading project " + projectIds[i] + " from version 1.0 to version 1.1d1");
        
            String filePath = FileStorage.JN_DOCPATH + projectIds[i] + File.separator + ".tasklist";
            Document doc = FileStorage.openDocument(filePath);
                        
            Element root = doc.getRootElement();
            Elements tasks = root.getChildElements("task");
                        
            for (int j = 0; j < tasks.size(); j++) {                                
                Element task = tasks.get(j );

//	Decided not to change the date format after all but I'm leaving this code here 
//	in case we need it later. Ryan
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//                Attribute startDateAttr = task.getAttribute("startDate");
//                Date startDate = (new CalendarDate(startDateAttr.getValue(),"/")).getDate();
//                startDateAttr.setValue(sdf.format(startDate));
//
//                Attribute endDateAttr = task.getAttribute("endDate");
//                if (endDateAttr != null) {
//                    Date endDate = (new CalendarDate(endDateAttr.getValue(),"/")).getDate();
//                    endDateAttr.setValue(sdf.format(endDate));                    
//                }
                
                Attribute parentAttr = task.getAttribute("parent");
            	if ((parentAttr == null) || (parentAttr.getValue() == "")) {
            		// no parent, do nothing here
            	}
            	else {
                	// put the task under the parent task
                    String parentId = parentAttr.getValue();
                    for (int k = 0; k < tasks.size(); k++) {
                        Element potentialParent = tasks.get(k);
                        if(parentId.equals(potentialParent.getAttribute("id").getValue())) {
                            // found parent, put self under it
                            task.removeAttribute(parentAttr);
                            task.detach();
                            potentialParent.appendChild(task);                            
                        }
                    }
            	}            	
            }
            doc.setDocType(getCurrentDocType());
            FileStorage.saveDocument(doc,filePath);
        }        
    }
}
