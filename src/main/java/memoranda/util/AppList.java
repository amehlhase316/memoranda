/**
 * AppList.java
 * Created on 24.03.2003, 14:21:50 Alex
 * Package: net.sf.memoranda.util
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda.util;
import java.util.StringTokenizer;
import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
/**
 *
 */
/*$Id: AppList.java,v 1.5 2007/03/20 08:22:41 alexeya Exp $*/
public class AppList {

    public Element _root = null;

    public static String OS_WINDOWS = "windows";
    public static String OS_LINUX = "linux";
    public static String OS_SOLARIS = "solaris";

    public static String getPlafCode(String osName) {
        osName = osName.toLowerCase();
        if (osName.indexOf(OS_WINDOWS) > -1)
            return OS_WINDOWS;
        else if (osName.indexOf(OS_LINUX) > -1)
            return OS_LINUX;
        else if (osName.indexOf(OS_SOLARIS) > -1)
            return OS_SOLARIS;
        return "unknown";
    }
    /**
     * Constructor for AppList.
     */
    public AppList(Element root) {
        _root = root;
    }

    public String getCommandLine(String appId, String fPath) {
        Elements apps = _root.getChildElements("app");
        fPath = fPath.replaceAll("\\\\", "\\\\\\\\");        
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("id").getValue().equals(appId)) {
                Element app = apps.get(i);
                String pt = app.getAttribute("command").getValue();
                if (fPath.indexOf(' ') >= 0)
                   fPath = '"' + fPath + '"'; 
                pt = pt.replaceAll("\\$1", fPath);
                return app.getAttribute("findPath").getValue() + "/" + app.getAttribute("exec").getValue()+" "+pt;
            }
        return null;
    }
    
    public String[] getCommand(String appId, String fPath) {
        Elements apps = _root.getChildElements("app");
        //fPath = fPath.replaceAll("\\\\", "\\\\\\\\");        
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("id").getValue().equals(appId)) {
                Element app = apps.get(i);
                String command = app.getAttribute("command").getValue();
                String exec = app.getAttribute("findPath").getValue() + "/" + app.getAttribute("exec").getValue();
                StringTokenizer st = new StringTokenizer(command);
                String[] cmdarray = new String[st.countTokens()+1];
                cmdarray[0] = exec;
                for (int j = 1; st.hasMoreTokens(); j++) {
                    String tk = st.nextToken();
                    if (tk.equals("$1"))
                        cmdarray[j] = fPath;
                    else
                        cmdarray[j] = tk;
                }
                return cmdarray;
            }
        return null;
    }

    public String getFindPath(String appId) {
        Elements apps = _root.getChildElements("app");
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("id").getValue().equals(appId))
                return apps.get(i).getAttribute("findPath").getValue();
        return null;
    }

    public void setFindPath(String appId, String p) {
        Elements apps = _root.getChildElements("app");
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("id").getValue().equals(appId)) {
              if (apps.get(i).getAttribute("findPath") == null)
                apps.get(i).addAttribute(new Attribute("findPath", p));
              else
                apps.get(i).getAttribute("findPath").setValue(p);
            }

    }

    public String getExec(String appId) {
        Elements apps = _root.getChildElements("app");
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("id").getValue().equals(appId))
                return apps.get(i).getAttribute("exec").getValue();
        return null;
    }

    public void setExec(String appId, String e) {
        Elements apps = _root.getChildElements("app");
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("id").getValue().equals(appId)){
              if (apps.get(i).getAttribute("exec") == null)
                apps.get(i).addAttribute(new Attribute("exec", e));
              else
                apps.get(i).getAttribute("exec").setValue(e);
            }
    }

    public String getCommandLinePattern(String appId) {
        Elements apps = _root.getChildElements("app");
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("id").getValue().equals(appId))
                return apps.get(i).getAttribute("command").getValue();
        return null;
    }

    public void setCommandLinePattern(String appId, String clp) {
        Elements apps = _root.getChildElements("app");
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("id").getValue().equals(appId))
                apps.get(i).addAttribute(new Attribute("command", clp));
    }

    public void addApp(String appId, String fp, String exec, String clp) {
        Element el = new Element("app");
        el.addAttribute(new Attribute("id", appId));
        el.addAttribute(new Attribute("command", clp));
        el.addAttribute(new Attribute("exec", exec));
        el.addAttribute(new Attribute("findPath", fp));
        _root.appendChild(el);
    }

    public void addOrReplaceApp(String appId, String fp, String exec, String clp) {
        Elements apps = _root.getChildElements("app");
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("id").getValue().equals(appId)) {
                 if (apps.get(i).getAttribute("findPath") == null)
                  apps.get(i).addAttribute(new Attribute("findPath", fp));
                 else
                  apps.get(i).getAttribute("findPath").setValue(fp);
                 if (apps.get(i).getAttribute("exec") == null)
                  apps.get(i).addAttribute(new Attribute("exec", exec));
                 else
                  apps.get(i).getAttribute("exec").setValue(exec);
                if (apps.get(i).getAttribute("command") == null)
                  apps.get(i).addAttribute(new Attribute("command", clp));
                else
                  apps.get(i).getAttribute("command").setValue(clp);
                return;
            }
        addApp(appId, fp, exec, clp);
    }
    
    public String getBrowserExec() {
        Elements els = _root.getChildElements("browser");        
        if (els.size() < 1) return null;
        Element el = els.get(0);
        return (el.getAttribute("path").getValue());
    }
    
    public void setBrowserExec(String path) {
        Element el = null;
        Elements els = _root.getChildElements("browser");    
        if (els.size() < 1) {
            el = new Element("browser");
            _root.appendChild(el);
        }
        else
            el = els.get(0);
        if (el.getAttribute("path") != null)
            el.getAttribute("path").setValue(path);
        else 
            el.addAttribute(new Attribute("path", path));
    }
}
