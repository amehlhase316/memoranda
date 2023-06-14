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
    public static String ADDRESS = "localhost";

    /**
     * Launches the server in a new thread
     */
    public static void main(String[] args){
        LaunchServer launch = new LaunchServer();
        launch.runTests();
    }
    public LaunchServer() {
        AuthenticationServer server = new AuthenticationServer();
        new Thread(() -> server.launch(SERVER_PORT)).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void runTests() {
        System.out.println(ClientLogin.login(true, "lioninn", "innlion"));
        System.out.println(ClientLogin.login(false, "lioninn", "innlion"));
        System.out.println(ClientLogin.login(true, "lioninn", "innlion"));
        System.out.println(ClientLogin.login(false, "lioninn", "ifefefnlion"));
        System.out.println(ClientLogin.login(false, "HeMan!", "innlion"));
    }
}
