package memoranda.AuthenticationService;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
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


    /**
     * This method initiates and loads the credentials of users and launches the server Socket
     * It also launches the queue for users waiting to be authenticated and launches the thread for
     * processing login requests
     * @param port - the port the server runs on
     */
    public void launch(int port) {
        credentialMap = new HashMap<Integer, Integer>();
        try {
            Scanner userPopulator = new Scanner(new File("logs/loginInfoDatabase"));
            while(userPopulator.hasNextInt()) {
                credentialMap.put(userPopulator.nextInt(),userPopulator.nextInt());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    /**
     * This thread authenticates a users credentials and returns to the clients
     * The result of the login attempt
     */
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
                            out.writeObject(LoginReturns.USERNAME_TAKEN);
                        }
                        else {
                            credentialMap.put(hashPassword(userName), hashPassword(password));
                            File file = new File("logs/loginInfoDatabase");
                            FileWriter loginInfoWriter = new FileWriter(file, true);
                            BufferedWriter writer = new BufferedWriter(loginInfoWriter);
                            writer.write(hashPassword(userName) + "\n");
                            writer.write(hashPassword(password) + "\n");
                            writer.close();
                            out.writeObject(LoginReturns.CREATED_ACCOUNT_INFO);
                        }
                        out.flush();
                        //remember to save to file in v2
                    } else {
                        if(credentialMap.containsKey(hashPassword(userName))) {
                            Integer storedPassword = credentialMap.get(hashPassword(userName));
                            if (storedPassword.equals(hashPassword(password))) {
                                out.writeObject(LoginReturns.LOGIN_SUCCESSFUL);
                            } else {
                                out.writeObject(LoginReturns.INCORRECT_PASSWORD);
                                logAttempt(userName, LocalDateTime.now());
                            }
                            out.flush();
                        }
                        else {
                            out.writeObject(LoginReturns.USER_NOT_FOUND);
                            out.flush();
                        }
                    }
                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                    e.printStackTrace();
            }
        }
    }

    /**
     * This method logs failed login attempts.
     * Multiple failures in a short period could indicate a security breach attempt.
     * @param username - The username failed to log in
     * @param date - The time stamp of the attempt
     */
    private void logAttempt(String username, LocalDateTime date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy-HH:mm:ss");
        String timestamp = date.format(formatter);
        try {
            FileWriter logWriter = new FileWriter("logs/loginAttemptLog.txt", true);
            logWriter.write(username + ": " + timestamp + "\n");
            logWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method hashes the passwords using a simple hashcode.
     * Able to be modified to use more complex hashing with fewer collisions should the need arise.
     * @param password - the users password
     * @return - the hashed password
     */
    private Integer hashPassword(String password){
        return password.hashCode();
    }
}
