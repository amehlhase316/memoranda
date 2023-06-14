package memoranda.AuthenticationService;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * {@code @Author} Ryan Dinaro
 * This class acts as the Authentication Server for the Gym Master Program.
 * It stores a HashMap of usernames and hashed passwords in local storage.
 * When a user sends a username and a password this server hashes the password and compares the hashed password
 * to the stored hashed password. If it is a match it returns true.
 */
public class AuthenticationServer {
    private ServerSocket server;
    private BlockingQueue<Socket> clientSocketQueue;
    private HashMap<Integer, Integer> credentialMap;


    public void launch(int port) {
        credentialMap = new HashMap<Integer, Integer>(); //remember to read from file in v2
        clientSocketQueue = new LinkedBlockingQueue<Socket>();
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("There was an issue creating a server socket");
            e.printStackTrace();
        }

        Thread passwordCheck = new Thread(this::runCheck);
        passwordCheck.start();
        new Thread(this::userSolicit).start();
    }

    /**
     * This method when called from a separate thread will wait for users to connect
     * and add them to a queue for authentication.
     */
    private void userSolicit(){
        while (true) {
            try {
                clientSocketQueue.put(server.accept());
            } catch (IOException | InterruptedException e) {
                System.out.println("Unexpected Client Error");
                e.printStackTrace();
            }
        }
    }


    private void runCheck() {
        while(true) {
                try {
                    Socket client = clientSocketQueue.take();
                    ObjectInputStream clientCredentialReader = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
                    boolean newUserCheck = clientCredentialReader.readBoolean();
                    String userName = (String) clientCredentialReader.readObject();
                    String password = (String) clientCredentialReader.readObject();
                    ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));
                    if (newUserCheck) {
                        if(credentialMap.containsKey(hashPassword(userName))) {
                            out.writeBoolean(false);
                        }
                        else {
                            credentialMap.put(hashPassword(userName), hashPassword(password));
                            File file = new File("logs/loginInfoDatabase");
                            FileWriter loginInfoWriter = new FileWriter(file, true);
                            BufferedWriter writer = new BufferedWriter(loginInfoWriter);
                            writer.write(hashPassword(userName) + "\n");
                            writer.write(hashPassword(password) + "\n");
                            writer.close();
                            out.writeBoolean(true);
                        }
                        out.flush();
                        //remember to save to file in v2
                    } else {
                        if(credentialMap.containsKey(hashPassword(userName))) {
                            Integer storedPassword = credentialMap.get(hashPassword(userName));
                            if (storedPassword.equals(hashPassword(password))) {
                                out.writeBoolean(true);
                            } else {
                                out.writeBoolean(false);
                                logAttempt(userName, Calendar.getInstance());
                            }
                            out.flush();
                        }
                        else {
                            out.writeBoolean(false);
                            out.flush();
                        }
                    }
                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                    e.printStackTrace();
            }
        }
    }

    private void logAttempt(String username, Calendar date) {
        String dateStamp = String.valueOf(date.getTime().getTime());
        try {
            FileWriter logWriter = new FileWriter("logs/loginAttemptLog.txt", true);
            logWriter.write(username + ": " + dateStamp);
            logWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer hashPassword(String password){
        return password.hashCode();
    }
}
