/**
 * Configuration.java
 * Created on 12.03.2003, 0:31:22 Alex
 * Package: net.sf.memoranda.util
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import main.java.memoranda.ui.ExceptionDialog;
/**
 *
 */
/*$Id: Configuration.java,v 1.5 2004/10/11 08:48:21 alexeya Exp $*/
public class Configuration {

    static LoadableProperties config  = new LoadableProperties();
    static String configPath = getConfigPath();

    static {
    try {
     config.load(new FileInputStream(configPath));
     System.out.println("Loaded from " + configPath);
    }
    catch (Exception e) {      
      File f = new File(configPath);
      new File(f.getParent()).mkdirs();      
      /*DEBUG*/System.out.println("New configuration created: "+configPath);
      try {
        config.load(Configuration.class.getResourceAsStream("/util/memoranda.default.properties"));
        saveConfig();      
      }
      catch (Exception e2) {
        new ExceptionDialog(e2, "Failed to load default configuration from resources.", "");
        config = null;
      }
    }
  }
  
  static String getConfigPath() {
    String p = Util.getEnvDir()+"memoranda.properties";
    if (new File(p).exists()) 
        return p;
    String p1 = Util.getEnvDir()+"jnotes2.properties";
    if (new File(p1).exists()) {
        /*DEBUG*/System.out.println(p + " not found.\n"+p1+" used instead.");
        return p1;
    }    
    return p;
  }

  public static void saveConfig() {
    try {
    config.save(new FileOutputStream(configPath));
    }
    catch (Exception e) {
     new ExceptionDialog(e, "Failed to save a configuration file:<br>"+configPath, "");
    }
  }

  public static Object get(String key) {
    if ((config.get(key)) == null) {
        /*DEBUG*///System.out.println("Configuration: Key '"+key+"' not found.");
        return "";
    }
    return config.get(key);
  }

  @SuppressWarnings("unchecked")
public static void put(String key, Object value) {
    config.put(key, value);
  }
}
