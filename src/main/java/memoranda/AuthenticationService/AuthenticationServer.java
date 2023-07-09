package memoranda.AuthenticationService;


import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * {@code @Author} Ryan Dinaro
 * This class acts as the Authentication Server for the Gym Master Program.
 * It stores a HashMap of usernames and hashed passwords in local storage.
 * When a user sends a username and a password this server hashes the password and compares the hashed password
 * to the stored hashed password. If it is a match it returns true.
 * Designed to be run as a dedicated server if wanted
 */
public class AuthenticationServer {
    private ServerSocket server;
    private BlockingQueue<Socket> clientSocketQueue;
    private HashMap<String, String> credentialMap;
    private PasswordHasher hasher;
    private static int SERVER_PORT = 1000;
    private static String ADDRESS = "localhost";
    private static final int LOAD_TIME = 100;

    /**
     * This constructor launches the server in a new thread, then waits for the server to load
     */
    public AuthenticationServer() {
        new Thread(() -> launch(SERVER_PORT)).start();
        try {
            Thread.sleep(LOAD_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Closes the server socket on call
     */
    public void closeServer() {
        try {
            server.close();
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
    }
    /**
     * This method initiates and loads the credentials of users and launches the server Socket
     * It also launches the queue for users waiting to be authenticated and launches the thread for
     * processing login requests
     * @param port - the port the server runs on
     */
    private void launch(int port) {
        credentialMap = new HashMap<String, String>();
        hasher = new PasswordHasher();
        try {
            Scanner userPopulator = new Scanner(new File("logs/loginInfoDatabase"));
            if(userPopulator.hasNextLine()) {
                while (userPopulator.hasNextLine()) {
                    String userName = userPopulator.nextLine();
                    if(userName!=null&&userPopulator.hasNextLine()) {
                        credentialMap.put(userName, userPopulator.nextLine());
                    }
                }
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
                        if(credentialMap.containsKey(userName)) {
                            out.writeObject(LoginReturns.USERNAME_TAKEN);
                        }
                        else {
                            credentialMap.put(userName, hasher.hashPassword(password));
                            File file = new File("logs/loginInfoDatabase");
                            FileWriter loginInfoWriter = new FileWriter(file, true);
                            BufferedWriter writer = new BufferedWriter(loginInfoWriter);
                            writer.write(userName + "\n");
                            writer.write(new PasswordHasher().hashPassword(password) + "\n");
                            writer.close();
                            out.writeObject(LoginReturns.CREATED_ACCOUNT_INFO);
                        }
                        out.flush();
                        //remember to save to file in v2
                    } else {
                        if(credentialMap.containsKey(userName)) {
                            String storedPassword = credentialMap.get(userName);
                            if (hasher.verifyPassword(password, storedPassword)) {
                                out.writeObject(LoginReturns.LOGIN_SUCCESSFUL);
                            } else {
                                out.writeObject(LoginReturns.INCORRECT_PASSWORD);
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
     * Changes the user password if credentials are correct
     * @param userName - user inputted username
     * @param oldPassword - user inputted old password
     * @param newPassword - user inputted desired password
     * @return returns a LoginReturn type that represents the state of the result
     */
    public LoginReturns changePassword(String userName, String oldPassword, String newPassword){
        String storedPassword = credentialMap.get(userName);
        if(storedPassword==null)
            return LoginReturns.USER_NOT_FOUND;
        boolean verifyCredentials = hasher.verifyPassword(oldPassword, storedPassword);
        if(!verifyCredentials) {
            return LoginReturns.INCORRECT_PASSWORD;
        }
        else {
            credentialMap.put(userName, hasher.hashPassword(newPassword));
            return LoginReturns.PASSWORD_CHANGED;
        }
    }

    /**
     * This method processes client login requests and returns the result of the authentication
     * @param newAccount - Creating a new account
     * @param username - Input Username
     * @param password - Input Password
     * @return - The LoginReturns that represents the state of the login attempt
     */
    public LoginReturns login(boolean newAccount, String username, String password) {
        try {
            Socket clientTest = new Socket(ADDRESS, SERVER_PORT);
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(clientTest.getOutputStream()));
            out.writeBoolean(newAccount);
            out.writeObject(username);
            out.writeObject(password);
            out.flush();
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(clientTest.getInputStream()));
            LoginReturns result = (LoginReturns) (in.readObject());
            if(result==LoginReturns.INCORRECT_PASSWORD) {
                logAttempt(username, clientTest);
            }
            in.close();
            out.close();
            return result;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method logs failed login attempts.
     * Multiple failures in a short period could indicate a security breach attempt.
     * @param username - The username failed to log in
     */
    private void logAttempt(String username, Socket clientSocket) {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy-HH:mm:ss");
        String timestamp = date.format(formatter);
        InetSocketAddress sockaddr = (InetSocketAddress)clientSocket.getRemoteSocketAddress();
        InetAddress inaddr = sockaddr.getAddress();
        Inet4Address in4addr = (Inet4Address)inaddr;
        String ip4string = in4addr.toString();
        try {
            FileWriter logWriter = new FileWriter("logs/loginAttemptLog.txt", true);
            logWriter.write(username + ": " + timestamp + " " + ip4string +"\n");
            logWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Author Ryan Dinaro
     * This private class creates a hashed and salted password from the Clients password
     * for storage and user authentication.
     */
    private static class PasswordHasher {
        private static final int SALT_SIZE = 16; // 16 bytes
        private static final int ITERATIONS = 10000;
        private static final int KEY_LENGTH = 128; // in bits

        /**
         * This method generates a hashed and salted password during user creation.
         * @param password - The users password
         * @return a String representation of the encrypted key
         */
        public String hashPassword(String password) {
            byte[] salt = new byte[SALT_SIZE];
            new SecureRandom().nextBytes(salt);
            //Create the Key with the salting (randomly regenerating the password)
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf;
            byte[] hash;
            try {
                skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                //generates the hashed key
                hash = skf.generateSecret(spec).getEncoded();
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
            //Adds the salt used to the end of the key
            byte[] saltAndHash = new byte[SALT_SIZE + hash.length];
            System.arraycopy(salt, 0, saltAndHash, 0, SALT_SIZE);
            System.arraycopy(hash, 0, saltAndHash, SALT_SIZE, hash.length);
            //Returns the key as a string for storage
            return Base64.getEncoder().encodeToString(saltAndHash);
        }

        /**
         * This method verifies the inputted password by retrieving the salt from the storedHash
         * Then generating a hash password from the user input using the same salt.
         * @param password - user inputted password
         * @param storedHash - password on file
         * @return - true if the stored password and user inputted password are identical
         */
        public boolean verifyPassword(String password, String storedHash) {
            byte[] saltAndHash = Base64.getDecoder().decode(storedHash);
            //separate the stored password into the salt and hash
            byte[] salt = Arrays.copyOfRange(saltAndHash, 0, SALT_SIZE);
            byte[] originalHash = Arrays.copyOfRange(saltAndHash, SALT_SIZE, saltAndHash.length);

            // Hash the password being verified
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf;
            byte[] hash;
            try {
                skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                hash = skf.generateSecret(spec).getEncoded();
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }

            // Compare the hashes
            return compareHashes(hash, originalHash);
        }

        private boolean compareHashes(byte[] a, byte[] b) {
            if (a.length != b.length)  {
                // Hashes are different length therefore it cannot be the correct password
                return false;
            }

            int result = 0;
            for (int i = 0; i < a.length; i++) {
                // Compare the bits with an ^ XOR bitwise comparison.
                // If the two bits are different it will result in the bit being 1
                // it will then compare result 0 with the | OR operation.
                // If it finds a single bit that is not identical, Result will be 1.
                // Returns the boolean compared to 0, the identical result
                result |= a[i] ^ b[i];
            }
            return result == 0;
        }

    }
}
