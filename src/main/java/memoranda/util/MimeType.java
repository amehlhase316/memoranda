/**
 * MimeType.java
 * Created on 24.03.2003, 13:55:46 Alex
 * Package: net.sf.memoranda.util
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda.util;
import java.util.Vector;

import javax.swing.ImageIcon;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
/**
 *
 */
/*$Id: MimeType.java,v 1.3 2004/01/30 12:17:42 alexeya Exp $*/
public class MimeType {

    public Element _root = null;

    public MimeType(Element root) {
        _root = root;
    }

    public MimeType() {
      _root = new Element("default-type");
      _root.addAttribute(new Attribute("id", "__UNKNOWN"));
      _root.addAttribute(new Attribute("label", "Unknown"));
    }

    public String getMimeTypeId() {
        return _root.getAttribute("id").getValue();
    }

    public String getExtension() {
        Elements exts = _root.getChildElements("ext");
        if (exts.size() > 0)
            return exts.get(0).getValue();
        return null;
    }

    public String[] getExtensions() {
        Vector v = new Vector();
        String[] ss = {};
        Elements exts = _root.getChildElements("ext");
        for (int i = 0; i < exts.size(); i++)
            v.add(exts.get(i).getValue());
        return (String[]) v.toArray(ss);
    }

    public void addExtension(String ext) {
        Element exe = new Element("ext");
        exe.appendChild(ext);
        _root.appendChild(exe);
    }

    public String getLabel() {
        if ((_root.getAttribute("label") != null) && (_root.getAttribute("label").getValue().length() >0))
          return _root.getAttribute("label").getValue();
        else
           return _root.getAttribute("id").getValue();
    }

    public void setLabel(String label) {
        if (_root.getAttribute("label") != null)
         _root.getAttribute("label").setValue(label);
        else
        _root.addAttribute(new Attribute("label", label));
    }

    public String getAppId(String plafCode) {
        Elements apps = _root.getChildElements("app");
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("platform").getValue().toLowerCase().equals(plafCode.toLowerCase()))
                return apps.get(i).getAttribute("appId").getValue();
        return null;
    }

    public void setApp(String plafCode, String appId) {
        if (getAppId(plafCode) != null) {
            Elements apps = _root.getChildElements("app");
            for (int i = 0; i < apps.size(); i++)
                if (apps.get(i).getAttribute("platform").getValue().toLowerCase().equals(plafCode.toLowerCase()))
                    apps.get(i).getAttribute("appId").setValue(appId);
        }
        else {
            Element app = new Element("app");
            app.addAttribute(new Attribute("appId", appId));
            app.addAttribute(new Attribute("platform", plafCode));
            _root.appendChild(app);
        }
    }

    public void setApp(String appId) {
        setApp(AppList.getPlafCode(System.getProperty("os.name")), appId);
    }

    public String getAppId() {
        String plaf = AppList.getPlafCode(System.getProperty("os.name"));
        return getAppId(plaf);
    }

    public String getIconPath() {
        if (_root.getAttribute("icon") != null)
          return _root.getAttribute("icon").getValue();
        else
          return "";
    }

    public void setIconPath(String path) {
         if (_root.getAttribute("icon") != null)
          _root.getAttribute("icon").setValue(path);
        else
        _root.addAttribute(new Attribute("icon", path));
    }

    public ImageIcon getIcon() {
       String ip = getIconPath();
       ImageIcon icon = null;
       if (ip.equals("")) {
        ip = "/util/icons/mimetypes/"+getMimeTypeId()+".png";
        try {
          icon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource(ip));
        }
        catch (Exception ex) {
          ip = "/util/icons/mimetypes/"+getMimeTypeId().split("/")[0]+"/default.png";
          try {
            icon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource(ip));
          }
          catch (Exception ex2) {
            icon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/util/icons/mimetypes/default.png"));
          }
        }
      }
      else
        try {
            icon = new ImageIcon(ip);
          }
          catch (Exception ex) {
          ip = "/util/icons/mimetypes/"+getMimeTypeId().split("/")[0]+"/default.png";
          try {
            icon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource(ip));
          }
          catch (Exception ex2) {
            icon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/util/icons/mimetypes/default.png"));
          }
        }
      return icon;
    }
}
