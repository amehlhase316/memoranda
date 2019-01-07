/**
 * HTMLFileImport.java
 * Created on 16.03.2003, 14:34:48 Alex
 * Package: net.sf.memoranda.util
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import main.java.memoranda.ui.ExceptionDialog;
import main.java.memoranda.ui.htmleditor.HTMLEditor;

/**
 * 
 */
/*$Id: HTMLFileImport.java,v 1.5 2005/07/05 08:17:28 alexeya Exp $*/
public class HTMLFileImport {

    /**
     * Constructor for HTMLFileImport.
     */
    public HTMLFileImport(File f, HTMLEditor editor) {
        String text = "";
        BufferedReader in;
        try {
            //in = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            in = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String line = in.readLine();
            while (line != null) {
                text = text + line + "\n";
                line = in.readLine();
            }
            in.close();            
        }
        catch (Exception e) {
            new ExceptionDialog(e, "Failed to import "+f.getPath(), "");
            return;
        }
        text = Pattern.compile("<body(.*?)>", java.util.regex.Pattern.DOTALL + java.util.regex.Pattern.CASE_INSENSITIVE)
           .split(text)[1];
        text = Pattern.compile("</body>", java.util.regex.Pattern.DOTALL + java.util.regex.Pattern.CASE_INSENSITIVE)
            .split(text)[0];
        //text = text.substring(p1, p2);
        
        editor.insertHTML(text, editor.editor.getCaretPosition());        

    }

}
