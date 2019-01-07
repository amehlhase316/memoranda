/**
 * ProjectPackager.java
 * Created on 16.03.2003, 18:41:29 Alex
 * Package: net.sf.memoranda.util
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda.util;
import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;

import main.java.memoranda.Project;
import main.java.memoranda.ProjectManager;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.ui.App;
import main.java.memoranda.ui.ExceptionDialog;
/**
 * 
 */
/*$Id: ProjectPackager.java,v 1.10 2007/03/20 06:21:46 alexeya Exp $*/
public class ProjectPackager {

    /*private static String JN_DOCPATH = System.getProperty("user.home") 
        + File.separator + ".memoranda" + File.separator;  //Chaned .jnotes2 to be
        //.memoranda so the pack feature would work.  (jcscoobyrs) on 17-Nov-2003
        //at 14:57:06 PM
    Changed to: */
    private static String JN_DOCPATH = Util.getEnvDir(); 
    // for compatibility with previous installations (jnotes2) [alexeyA]

    public static void pack(Project prj, File zipfile) {
        ZipOutputStream zip = null;
        
        if(zipfile.getName().indexOf(".zip") == -1)
        {
            zipfile = new File(zipfile.getPath() + ".zip");
        }
        
        try {
            zip = new ZipOutputStream(new FileOutputStream(zipfile));
            File prDir = new File(JN_DOCPATH + prj.getID());
            
            PackDirectory(prDir.getPath(), prDir, zip);
            zip.putNextEntry(new ZipEntry("__PROJECT_INFO__"));
            String prInfo = prj.getID() + "\n" + prj.getTitle() + "\n" + prj.getStartDate().toString() + "\n";
            if (prj.getEndDate() != null)
                prInfo += prj.getEndDate().toString();
            zip.write(prInfo.getBytes("UTF-8"));
            zip.closeEntry();
            
            zip.close();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex, "Failed to write to "+zipfile, "");
        }
    }

    public static void unpack(File zipfile) {
        try {
            ZipFile zip = new ZipFile(zipfile);
            ZipEntry info = zip.getEntry("__PROJECT_INFO__");
            BufferedReader in = new BufferedReader(new InputStreamReader(zip.getInputStream(info), "UTF-8"));
            String pId = in.readLine();
            String pTitle = in.readLine();
            String pStartD = in.readLine();
            String pEndD = in.readLine();
            in.close();
            if (ProjectManager.getProject(pId) != null) {
                int n =
                    JOptionPane.showConfirmDialog(
                        App.getFrame(),
                        Local.getString("This project is already exists and will be replaced.\nContinue?"),
                        Local.getString("Project is already exists"),
                        JOptionPane.YES_NO_OPTION);
                if (n != JOptionPane.YES_OPTION) {
                    zip.close();
                    return;
                }	
                ProjectManager.removeProject(pId);
            }
            Project prj = ProjectManager.createProject(pId, pTitle, new CalendarDate(pStartD), null);
            if (pEndD != null)
                prj.setEndDate(new CalendarDate(pEndD));
            //File prDir = new File(JN_DOCPATH + prj.getID());
            Enumeration files;           
            for (files = zip.entries(); files.hasMoreElements();){
                ZipEntry ze = (ZipEntry)files.nextElement();
                if ( ze.isDirectory() )
                {
                   File theDirectory = new File (JN_DOCPATH + prj.getID()+ "/" + ze.getName() );
                   // create this directory (including any necessary parent directories)
                   theDirectory.mkdirs();
                   theDirectory = null;
                }
                if ((!ze.getName().equals("__PROJECT_INFO__")) && (!ze.isDirectory())) {
                    FileOutputStream out = new FileOutputStream(JN_DOCPATH + prj.getID() +"/"+ ze.getName());
                    InputStream inp = zip.getInputStream(ze);
                    
                    byte[] buffer = new byte[1024];
                    int len;

                    while((len = inp.read(buffer)) >= 0)
                      out.write(buffer, 0, len);

                    inp.close();
                    out.close();
                    
                }
            }
            zip.close();
            CurrentStorage.get().storeProjectManager();             
        }
        catch (Exception ex) {
            new ExceptionDialog(ex, "Failed to read from "+zipfile, "Make sure that this file is a Memoranda project archive.");
        }
    }
    
    
    /**
     * Packs all files in the given directory into the given ZIP stream.
     * Also recurses down into subdirectories.
     */
    public static void PackDirectory( String startingDirectory,
                                    File theDirectory,
                                    ZipOutputStream theZIPStream )
    throws java.io.IOException
    {
       File[] theFiles = theDirectory.listFiles();
       File stDirectory = new File(startingDirectory);
       System.out.println("Path="+stDirectory.getPath()+";length="+stDirectory.getPath().length() + "==>"+theFiles[0]);
       int j = stDirectory.getPath().length();
       for ( int i=0 ; i<theFiles.length ; i++ )
       {
          String sRelPath = theFiles[i].getPath().substring(j);
          if ( theFiles[i].isDirectory() )
          {
             // create a directory entry.
             // directory entries must be terminated by a slash!
             ZipEntry theEntry = new ZipEntry("."+sRelPath+"/" );
             theZIPStream.putNextEntry(theEntry);
             theZIPStream.closeEntry();

             // recurse down
             PackDirectory( startingDirectory, theFiles[i], theZIPStream );
          }
          else // regular file
          { 
            File f = theFiles[i];
            ZipEntry ze = new ZipEntry("."+sRelPath);
            FileInputStream in = new FileInputStream(f);
            byte[] data = new byte[(int) f.length()];
            in.read(data);
            in.close();
            theZIPStream.putNextEntry(ze);
            theZIPStream.write(data);
            theZIPStream.closeEntry();           
          }
       }
    }

}
