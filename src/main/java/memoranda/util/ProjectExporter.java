/*
 * ProjectExporter.java Package: net.sf.memoranda.util Created on 19.01.2004
 * 16:44:05 @author Alex
 */
package main.java.memoranda.util;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.Collections;

import javax.swing.text.html.HTMLDocument;

import main.java.memoranda.*;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.ui.*;
import main.java.memoranda.ui.htmleditor.AltHTMLWriter;

/**
 *  
 */
/* $Id: ProjectExporter.java,v 1.7 2005/07/05 08:17:28 alexeya Exp $ */
public class ProjectExporter {

    static boolean _chunked = false;
    static boolean _num = false;
    static boolean _xhtml = false;
    static boolean _copyImages = false;
    static File output = null;
    static String _charset = null;
    static boolean _titlesAsHeaders = false;
    static boolean _navigation = false;
    
    static String charsetString = "\n";

    public static void export(Project prj, File f, String charset,
            boolean xhtml, boolean chunked, boolean navigation, boolean num,
            boolean titlesAsHeaders, boolean copyImages) {

        _num = num;
        _chunked = chunked;
        _charset = charset;
        _xhtml = xhtml;
        _titlesAsHeaders = titlesAsHeaders;
        _copyImages = copyImages;
        _navigation = navigation;
        if (f.isDirectory())
            output = new File(f.getPath() + "/index.html");
        else
            output = f;
        NoteList nl = CurrentStorage.get().openNoteList(prj);
        Vector notes = (Vector) nl.getAllNotes();
        //NotesVectorSorter.sort(notes);
        Collections.sort(notes);

        Writer fw;

        if (output.getName().indexOf(".htm") == -1) {
            String dir = output.getPath();
            String ext = ".html";

            String nfile = dir + ext;

            output = new File(nfile);
        }        
        try {
            if (charset != null) {
                fw = new OutputStreamWriter(new FileOutputStream(output),
                        charset);
                charsetString = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset="
                        + charset + "\" />";
            }
            else
                fw = new FileWriter(output);
        }
        catch (Exception ex) {
            new ExceptionDialog(ex, "Failed to write to " + output, "");
            return;
        }
        write(fw, "<html>\n<head>\n" + charsetString + "<title>"
                + prj.getTitle()
                + "</title>\n</head>\n<body>\n<h1 class=\"projecttitle\">"
                + prj.getTitle() + "</h1><br></br>\n");
        generateToc(fw, notes);
        generateChunks(fw, notes);
        write(fw, "\n<hr></hr><a href=\"http://memoranda.sf.net\">Memoranda</a> "
                + App.VERSION_INFO + "\n<br></br>\n" + new Date().toString()
                + "\n</body>\n</html>");
        try {
            fw.flush();
            fw.close();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex, "Failed to write to " + output, "");
        }
    }

    private static void generateToc(Writer w, Vector notes) {
        write(w, "<div class=\"toc\"><ul>\n");
        for (Iterator i = notes.iterator(); i.hasNext(); ) {
            Note note = (Note) i.next();
            String link = "";
            CalendarDate d = note.getDate();
            String id = note.getId();
            if (!_chunked)
                link = "#" + id;
            else
                link = id + ".html";
            write(w, "<li><a href=\"" + link + "\">"
                    + note.getDate().getMediumDateString() + " "
                    + note.getTitle() + "</a></li>\n");
        }
        write(w, "</ul></div>\n");
    }

    private static String getNoteHTML(Note note) {
        String text = "";
        StringWriter sw = new StringWriter();
        AltHTMLWriter writer = new AltHTMLWriter(sw,
                (HTMLDocument) CurrentStorage.get().openNote(note), _charset,
                _num);
        try {
            writer.write();
            sw.flush();
            sw.close();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
        text = sw.toString();
        if (_xhtml)
            text = HTMLFileExport.convertToXHTML(text);
        text = Pattern
                .compile("<body(.*?)>", java.util.regex.Pattern.DOTALL
                        + java.util.regex.Pattern.CASE_INSENSITIVE).split(text)[1];
        text = Pattern
                .compile("</body>", java.util.regex.Pattern.DOTALL
                        + java.util.regex.Pattern.CASE_INSENSITIVE).split(text)[0];
        /*
                 * if (_copyImages) { ?)\"" + java.util.regex.Pattern.DOTALL +
                 * java.util.regex.Pattern.CASE_INSENSITIVE); Matcher m =
                 * p.matcher(text); for (int i = 1; i < m.groupCount(); i++) { String g =
                 * m.group(i); String url = g.split("\"")[1];
                 *  }
                 */
        text = "<div class=\"note\">" + text + "</div>";

        if (_titlesAsHeaders)
                        text = "\n\n<div class=\"date\">"
                    + note.getDate().getFullDateString()
                    + ":</div>\n<h1 class=\"title\">" + note.getTitle()
                    + "</h1>\n" + text;
        return text;
    }

    private static String generateNav(Note prev, Note next) {
        String s = "<hr></hr><div class=\"navigation\"><table border=\"0\" width=\"100%\" cellpadding=\"2\"><tr><td width=\"33%\">";
        if (prev != null)   
            s += "<div class=\"navitem\"><a href=\"" + prev.getId() + ".html\">"
                    + Local.getString("Previous") + "</a><br></br>"
                    + prev.getDate().getMediumDateString() + " "
                    + prev.getTitle() + "</div>";
        
        else
            s += "&nbsp;";
                s += "</td><td width=\"34%\" align=\"center\"><a href=\""
                + output.getName()
                + "\">Up</a></td><td width=\"33%\" align=\"right\">";
        if (next != null) 
            s += "<div class=\"navitem\"><a href=\"" + next.getId() + ".html\">"
                    + Local.getString("Next") + "</a><br></br>"
                    + next.getDate().getMediumDateString() + " "
                    + next.getTitle() + "</div>";
        
        else
            s += "&nbsp;";
        s += "</td></tr></table></div>\n";
        return s;
    }

    private static void generateChunks(Writer w, Vector notes) {
        Object[] n = notes.toArray();
        for (int i = 0; i < n.length; i++) {
            Note note = (Note) n[i];
            CalendarDate d = note.getDate();
            if (_chunked) {
                File f = new File(output.getParentFile().getPath() + "/"
                        + note.getId()
                        + ".html");
                Writer fw = null;
                try {
                    if (_charset != null)
                        fw = new OutputStreamWriter(new FileOutputStream(f),
                                _charset);
                    else
                        fw = new FileWriter(f);
                    String s = "<html>\n<head>\n"+charsetString+"<title>" + note.getTitle()
                            + "</title>\n</head>\n<body>\n" + getNoteHTML(note);
                    if (_navigation) {
                        Note nprev = null;
                        if (i > 0)
                            nprev = (Note) n[i - 1];
                        Note nnext = null;
                        if (i < n.length - 1)
                            nnext = (Note) n[i + 1];
                        s += generateNav(nprev, nnext);
                    }
                    s += "\n</body>\n</html>";
                    fw.write(s);
                    fw.flush();
                    fw.close();
                }
                catch (Exception ex) {
                    new ExceptionDialog(ex, "Failed to write to " + output, "");
                }
            }
            else
                                write(w, "<a name=\"" + note.getId() + "\">" + getNoteHTML(note) + "</a>\n");
        }
    }

    private static void write(Writer w, String s) {
        try {
            w.write(s);
        }
        catch (Exception ex) {
            new ExceptionDialog(ex, "Failed to write to " + output, "");
        }
    }
}