package memoranda.AuthenticationService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * {@code @Author} Ryan Dinaro
 * This class will launch the dedicated authentication server for Gym Master login system
 */
public class LaunchServer {
    public static int SERVER_PORT = 1000;
    public static String ADDRESS = "localhost";
    private static final int LOAD_TIME = 100;
    private AuthenticationServer server;

    /**
     * Launches the server in a new thread to be deleted if Gym Master implements local Authentication Server
     */
    public static void main(String[] args){
        LaunchServer launch = new LaunchServer();
    }

    /**
     * Starts the authentication server in a separate thread pauses main thread to let the server load before
     * any requests are sent in
     */
    public LaunchServer() {
        server = new AuthenticationServer();
        new Thread(() -> server.launch(SERVER_PORT)).start();
        try {
            Thread.sleep(LOAD_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
