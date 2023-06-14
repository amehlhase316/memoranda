package memoranda.AuthenticationService;

import java.io.*;
import java.net.Socket;

public class ClientLogin {
    public static LoginReturns login(boolean newAccount, String username, String password) {
        try {
            Socket clientTest = new Socket("localhost", 1000);
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
