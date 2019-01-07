/**
 * Start.java
 * Created on 19.08.2003, 20:40:08 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import java.net.ServerSocket;
import java.net.Socket;

import main.java.memoranda.ui.*;
import main.java.memoranda.util.Configuration;

/**
 *
 */
/*$Id: Start.java,v 1.7 2004/11/22 10:02:37 alexeya Exp $*/
public class Start {
    
    static App app = null;
    
    static int DEFAULT_PORT = 19432;
    static boolean checkIfAlreadyStartet = true;
    
    static {
        String port = Configuration.get("PORT_NUMBER").toString().trim();
        if (port.length() >0) {
            // The Portnumber must be between 1024 (in *nix all Port's < 1024
            // are privileged) and 65535 (the highest Portnumber everywhere)
            int i = Integer.parseInt(port);
            if ((i >= 1024) && (i <= 65535)) {
                DEFAULT_PORT = i;
            }
            /*DEBUG*/ //System.out.println("Port " + DEFAULT_PORT + " used.");
        }
        
        String check = Configuration.get("CHECK_IF_ALREADY_STARTED").toString().trim();
        if (check.length() > 0 && check.equalsIgnoreCase("no")) {
            checkIfAlreadyStartet = false;
        }
    }
    
    public static void main(String[] args) {
        if (checkIfAlreadyStartet) {
            try {
                // Try to open a socket. If socket opened successfully (app is already started), take no action and exit.
                Socket socket = new Socket("127.0.0.1", DEFAULT_PORT);
                socket.close();
                System.exit(0);
                
            } catch (Exception e) {
                // If socket is not opened (app is not started), continue
                // e.printStackTrace();
            }
            new SLThread().start();
        }
        
        //System.out.println(EventsScheduler.isEventScheduled());
        if ((args.length == 0) || (!args[0].equals("-m"))) {
            app = new App(true);
        }
        else
            app = new App(false);
    }
}

class SLThread extends Thread {
    
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Start.DEFAULT_PORT);
            serverSocket.accept();
            Start.app.show();
            serverSocket.close();
            new SLThread().start();
            
        } catch (Exception e) {
            System.err.println("Port:"+Start.DEFAULT_PORT);
            e.printStackTrace();
            new ExceptionDialog(e, "Cannot create a socket connection on localhost:"+Start.DEFAULT_PORT,
            "Make sure that other software does not use the port "+Start.DEFAULT_PORT+" and examine your security settings.");
        }
    }
}
