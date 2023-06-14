package memoranda.AuthenticationService;

public class LaunchServer {
    public static void main(String[] args){
        AuthenticationServer server = new AuthenticationServer();
        new Thread(() -> server.launch(1000)).start();
        //Gives Server Time to Start
        try {
            Thread.sleep (100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ClientLogin.login(false, "lioninn", "farts");
    }

}
