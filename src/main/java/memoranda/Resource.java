/**
 * Resource.java
 * Created on 04.04.2003, 20:59:24 Alex
 * Package: net.sf.memoranda
 *  
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

/**
 * 
 */
/*$Id: Resource.java,v 1.4 2007/03/20 06:21:46 alexeya Exp $*/
public class Resource {
    
    private String _path = null;	// the path to the file
    private boolean _isInetShortcut = false; // true if Internet shortcut
    private boolean _isProjectFile = false;	// true if file is in project directory 
    /**
     * Constructor for Resource.
     * @param path, the path to the file.
     * @param isInetShortcut, if the resource is a internet shortcut.
     * @param isProjectFile, if file is copied to project directory.
     */
    public Resource(String path, boolean isInetShortcut, boolean isProjectFile) {
        _path = path;
        _isInetShortcut = isInetShortcut;
        _isProjectFile = isProjectFile;
    }
    
    public Resource(String path) {
        _path = path;         
    }
    
    public String getPath() {
        return _path;
    }
    
    public boolean isInetShortcut() {
        return _isInetShortcut;
    }
    
    public boolean isProjectFile() {
    	return _isProjectFile;
    }

}
