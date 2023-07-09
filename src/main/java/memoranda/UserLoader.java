package main.java.memoranda;

import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.util.Util;

import java.io.*;
import java.nio.Buffer;

public class UserLoader {

    public static void init() {
        try {
            File f = new File(Util.getEnvDir() + "users.txt");
            if (!f.isFile()) {
                f.createNewFile();
            }
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
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Util.getEnvDir() + "users.txt", false));
            UserList userList = UserList.getInstance();
            for (var temp:
                    UserList.getUserList().entrySet()) {
                bufferedWriter.append(temp.getValue().getFirstName()).append("\n");
                bufferedWriter.append(temp.getValue().getLastName()).append("\n");
                bufferedWriter.append(temp.getValue().getUsername()).append("\n");
                bufferedWriter.append(String.valueOf(temp.getValue().getPermissions())).append("\n");
                bufferedWriter.append(String.valueOf(temp.getValue().getRank())).append("\n");
                bufferedWriter.append(temp.getValue().getPassword()).append("\n");
                bufferedWriter.append(String.valueOf(temp.getValue().getJoinDate().getDay())).append("\n");
                bufferedWriter.append(String.valueOf(temp.getValue().getJoinDate().getMonth())).append("\n");
                bufferedWriter.append(String.valueOf(temp.getValue().getJoinDate().getYear())).append("\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save user data. If new users were added, they may have been lost");
            throw new RuntimeException(e);
        }
    }

    private static void loadUsersFromFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(Util.getEnvDir() + "users.txt"));
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
