package main.java.memoranda.ui;

/**
 * AllFilesFilter.java
 * Created on 25.02.2003, 17:30:12 Alex
 * Package:
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
import java.io.File;

import javax.swing.filechooser.FileFilter;

import main.java.memoranda.util.Local;

/**
 *
 */
/*$Id: AllFilesFilter.java,v 1.5 2004/01/30 12:17:41 alexeya Exp $*/
public class AllFilesFilter extends FileFilter {

    public static final String RTF = "RTF";
    public static final String HTML = "HTML";
    public static final String HTM = "HTM";
    public static final String XHTML = "XHTML";
    public static final String XML = "XML";
    public static final String ZIP = "ZIP";
    public static final String EXE = "EXE";
    public static final String COM = "COM";
    public static final String BAT = "BAT";
    public static final String JAR = "JAR";
    public static final String ICO = "ICO";
    public static final String WAV = "WAV";

    String _type = "";
    /**
     * Constructor for AllFilesFilter.
     */
    public AllFilesFilter(String type) {
        super();
        _type = type;
    }

    /**
     * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
     */
    public boolean accept(File f) {
        if (f.isDirectory())
            return true;
        String ext = getExtension(f);
        if (_type.equals(RTF))
            return ext.equals("rtf");
        else if (_type.equals(ZIP))
            return ext.equals("zip");
        else if (_type.equals(EXE))
            return (ext.equals("exe") || ext.equals("com") || ext.equals("bat"));
        else if (_type.equals(JAR))
            return ext.equals("jar");
        else if (_type.equals(WAV))
            return (ext.equals("wav") || ext.equals("au"));
        else if (_type.equals(XHTML))
            return (ext.equals("xhtml") || ext.equals("xml"));
        else if (_type.equals(ICO))
            return (ext.equals("ico") || ext.equals("png"));
        return ext.startsWith("htm");
    }

    /**
     * @see javax.swing.filechooser.FileFilter#getDescription()
     */
    public String getDescription() {
        if (_type.equals(RTF))
            return "Rich Text Format (*.rtf)";
        else if (_type.equals(ZIP))
            return "ZIP archives (*.zip)";
        else if (_type.equals(EXE))
            return Local.getString("Executable Files") + " (*.exe, *.com, *.bat)";
        else if (_type.equals(JAR))
            return "JAR " + Local.getString("Files") + " (*.jar)";
        else if (_type.equals(WAV))
            return Local.getString("Sound files") + " (*.wav, *.au)";
        else if (_type.equals(XHTML))
            return "XHTML files (*.xhtml, *.xml)";
        else if (_type.equals(ICO))
            return Local.getString("Icon") + " " + Local.getString("Files") + " (*.ico, *.png)";
        return "HTML files (*.html, *.htm)";
    }

    private static String getExtension(File f) {
        String ext = "";
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

}
