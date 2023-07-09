package main.java.memoranda;

import java.io.BufferedOutputStream;
import java.util.HashMap;

/**
 Class: UserList
 Description: Class construct the Hash containing all users. Future version will read in users
 from a file.
 */
public class UserList {
    public static HashMap<String, User> users = new HashMap<>();
    private static UserList INSTANCE;

    public static UserList getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserList();
        }
        return INSTANCE;
    }

    public UserList() {
    }

    /**
     Method: addUser
     Inputs: User
     Returns: void
     Description: Will add user object to hashmap.
     */
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public static HashMap<String, User> getUserList() {
        return users;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (var temp:
             users.entrySet()) {
            sb.append(temp.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
