package memoranda.AuthenticationService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * {@code @Author} Ryan Dinaro
 * Executing this code will launch the dedicated authentication server for Gym Master login system
 */
public class LaunchServer {
    public static int SERVER_PORT = 1000;

    /**
     * Launches the server in a new thread
     */
    public static void main(String[] args){
        AuthenticationServer server = new AuthenticationServer();
        new Thread(() -> server.launch(SERVER_PORT)).start();
    }

}
