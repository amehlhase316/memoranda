package main.java.memoranda;

import java.util.HashMap;

/**
 Class: UserList
 Description: Class construct the Hash containing all users. Future version will read in users
 from a file.
 */
public class UserList {
    public static HashMap<String, User> users = new HashMap<>();
    User user;
    User user1;
    User user2;

    public UserList() {
        user = new User();
        user.setLastName("Smith");
        user.setFirstName("John");
        user.setUsername("login");
        user.setPassword("password");

        user1 = new User();
        user1.setLastName("Jones");
        user1.setFirstName("Arnold");
        user1.setUsername("1234");
        user1.setPassword("password");
        
        user2 = new User();
        user2.setLastName("Admin");
        user2.setFirstName("Default");
        user2.setUsername("Username");
        user2.setPassword("Password");
        user2.setPermissions(2);

        users.put(user.getUsername(), user);
        users.put(user1.getUsername(), user1);
        users.put(user2.getUsername(), user2);
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

}
