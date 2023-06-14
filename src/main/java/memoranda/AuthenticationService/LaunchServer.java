package memoranda.AuthenticationService;

import java.io.*;
import java.net.Socket;

public class LaunchServer {
    public static void main(String args[]){
        AuthenticationServer server = new AuthenticationServer();
        new Thread(() -> {server.launch(1000);}).start();
        try {
            Thread.sleep (5000);
            System.out.println("Initiating Test");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Setting up username");
        if(login(true, "lioninn", "innlion"))
            System.out.println("Pass");
        else
            System.out.println("Fail");

        System.out.println("testing if username is in");
        if(login(false, "lioninn", "innlion"))
            System.out.println("Pass");
        else
            System.out.println("Fail");

        System.out.println("testing wrong password");
        if(!login(true, "lioninn", "d"))
            System.out.println("Pass");
        else
            System.out.println("Fail");
    }
    public static boolean login(boolean newAccount, String username, String password) {
        try {
            Socket clientTest = new Socket("localhost", 1000);
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(clientTest.getOutputStream()));
            out.writeBoolean(newAccount);
            out.writeObject(username);
            out.writeObject(password);
            out.flush();
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(clientTest.getInputStream()));
            boolean result = (in.readBoolean());
            in.close();
            out.close();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
