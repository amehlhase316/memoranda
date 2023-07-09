package main.java.memoranda;

import main.java.memoranda.date.CalendarDate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class UserLoader {

    static {

    }
    public static void init() {
        try {
            loadUsersFromFile();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public static void save() {

    }

    private static void loadUsersFromFile() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("/src/main/resources/users.txt"));
        UserList userList = UserList.getInstance();
        String line = "";
        Boolean isEndOfFile = false;
        while ((line = br.readLine()) != null) {
            User user = new User();
            user.setFirstName(line);

            line = br.readLine();
            user.setLastName(line);

            line = br.readLine();
            user.setUsername(line);

            line = br.readLine();
            user.setPermissions(Integer.parseInt(line));

            line = br.readLine();
            user.setRank(Integer.parseInt(line));

            line = br.readLine();
            user.setPassword(line);

            line = br.readLine();
            int day = Integer.parseInt(line);

            line = br.readLine();
            int month = Integer.parseInt(line);

            line = br.readLine();
            int year = Integer.parseInt(line);

            user.setJoinDate(new CalendarDate(day, month, year));

            userList.addUser(user);
        }
        br.close();

        // TODO: testing, remove later
        System.out.println(userList.toString());
    }
}
