/**
 * HTMLFileExport.java
 * Created on 25.02.2003, 17:59:14 Alex
 * Package: net.sf.memoranda.util
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda.util;
import java.io.*;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;

import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.cyberneko.html.parsers.SAXParser;
import org.xml.sax.InputSource;

import main.java.memoranda.Note;
import main.java.memoranda.ui.ExceptionDialog;
import main.java.memoranda.ui.htmleditor.AltHTMLWriter;
/**
 * 
 */
/*$Id: HTMLFileExport.java,v 1.7 2005/07/05 08:17:28 alexeya Exp $*/
public class HTMLFileExport {
    
    String charset = "";
    File f = null;
    HTMLDocument doc;
    Note note = null;
    boolean xhtml = false;
    boolean num = false;
    String templFile = null;
    /**
     * Constructor for HTMLFileExport.
     */
    public HTMLFileExport(File f, Document doc, Note note, String charset, boolean num, String templFile, boolean xhtml) {
        this.f = f;
        this.doc = (HTMLDocument)doc;
        this.note = note;
        this.charset = charset;
        this.num = num;
        this.templFile = templFile;
        this.xhtml = xhtml;
        doExport();
    }
    
    private void doExport() {
        try {
                    //FileWriter fw = new FileWriter(f);
                    Writer fw;
                    
					//Added to fix the file if there was no extention given
					//jcscoobyrs 17-Nov-2003 at 09:08:55
					//------------------------------------------------------
					if(f.getName().indexOf(".htm") == -1)
					{
						String dir = f.getPath();
						String ext = ".html";
						//String ps = System.getProperty("file.separator");
						String nfile = dir + ext;
			
						f = new File(nfile);                    	
					}
					//------------------------------------------------------
					//End appendage
                    
                    if (charset != null)    
                        fw = new OutputStreamWriter(new FileOutputStream(f), charset);
                    else
                        fw = new FileWriter(f);
                    fw.write(applyTemplate());
                    fw.flush();
                    fw.close();                    
                }
                catch (Exception ex) {
                    new ExceptionDialog(ex, "Cannot export file "+f.getPath(), null);
                }
        }
	 
     
	 private String getTemplateString(String templF) {		
	    if ((templF != null) && (templF.length() >0)) {
    		File f = new File(templF);			
			String text = "";	
			try {
				BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream(f)));		
				String s = fr.readLine();
				while (s != null) {				
					text = text + s;
					s = fr.readLine();
				}
				fr.close();
			}
			catch (Exception ex) {
				new ExceptionDialog(ex, "Cannot read template file from "+templF, null);
			}	
			if (text.length() > 0)
				return text;
		}
	    String t = (String)Configuration.get("DEFAULT_EXPORT_TEMPLATE");
	    if ((t != null) && (t.length() > 0))
	        return t;
		return "<html>\n<head>\n@METACHARSET@\n<title>@TITLE@ - @PROJECT@</title>\n</head>\n<body>\n@CONTENT@\n</body>\n</html>";
	 }
     
	 private String applyTemplate() {
        String body = getNoteBody();        
		String title = note != null? note.getTitle() : "";
		String id = note != null? note.getId() : "";
		String project = note != null? note.getProject().getTitle() : "";
		String date = note != null? note.getDate().getFullDateString() : "";
		String now = new Date().toString();
		String templ = getTemplateString(templFile);
		templ = templ.replaceAll("@CONTENT@", body);
		templ = templ.replaceAll("@TITLE@", title);
		templ = templ.replaceAll("@ID@", id);
		templ = templ.replaceAll("@PROJECT@", project);
		templ = templ.replaceAll("@DATE@", date);
		templ = templ.replaceAll("@NOW@", now);
		if ((charset != null) && (charset.length() >0))
		    templ = templ.replaceAll("@METACHARSET@", "<meta http-equiv=\"Content-Type\" content=\"text/html; charset="
                        + charset + "\" >");
		if (xhtml)
		    templ = convertToXHTML(templ);
		return templ;
     }
     
     private String getNoteBody() {
        String text = "";
        StringWriter sw = new StringWriter();
        AltHTMLWriter writer = new AltHTMLWriter(sw, doc, charset, num);
        try {
            writer.write();
            sw.flush();
            sw.close();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
        text = sw.toString();
        text = Pattern
                .compile("<body(.*?)>", java.util.regex.Pattern.DOTALL
                        + java.util.regex.Pattern.CASE_INSENSITIVE).split(text)[1];
        text = Pattern
                .compile("</body>", java.util.regex.Pattern.DOTALL
                        + java.util.regex.Pattern.CASE_INSENSITIVE).split(text)[0];
        return text;
     }
     
     public static String convertToXHTML(String in) {       
        SAXParser parser = new SAXParser();
        InputSource source;
        OutputFormat outputFormat = new OutputFormat();
        try {
            //parser.setProperty("http://cyberneko.org/html/properties/default-encoding", charset);
            parser.setProperty("http://cyberneko.org/html/properties/names/elems", "lower");
            outputFormat.setOmitDocumentType(true);
            outputFormat.setOmitXMLDeclaration(true);
            outputFormat.setMethod(Method.XHTML);
            outputFormat.setIndenting(true);            
            StringReader sr = new StringReader(in);
            StringWriter sw = new StringWriter();
            source = new InputSource(sr);
            parser.setContentHandler(new XMLSerializer(sw, outputFormat));
            parser.parse(source);
            return sw.toString();
        }
        catch (Exception ex) {
           new ExceptionDialog(ex);
        }
        return null;
    }
}
