package memoranda.AuthenticationService;

import java.io.*;
import java.net.Socket;

/**
 * @Author Ryan Dinaro
 */
public class ClientLogin {
    /**
     * This class processes client login requests and returns the result of the authentication
     * @param newAccount - Creating a new account
     * @param username - Input Username
     * @param password - Input Password
     * @return - The LoginReturns that represents the state of the login attempt
     */
    public static LoginReturns login(boolean newAccount, String username, String password) {
        try {
            Socket clientTest = new Socket(LaunchServer.ADDRESS, LaunchServer.SERVER_PORT);
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(clientTest.getOutputStream()));
            out.writeBoolean(newAccount);
            out.writeObject(username);
            out.writeObject(password);
            out.flush();
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(clientTest.getInputStream()));
            LoginReturns result = (LoginReturns) (in.readObject());
            in.close();
            out.close();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
