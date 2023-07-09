package main.java.memoranda;
/**
File: User.java
Author: Jonathan Blicharz
Date: 6/23/23
Description: File contains User class which is used to create new User objects.
*/


import main.java.memoranda.date.CalendarDate;

import java.io.BufferedOutputStream;
import java.util.Calendar;

/**
 Class: User
 Description: Creates User object for users of registry. Allows set/get of object attributes.
 Object contains firstname, lastname, userID, permissions, rank and joinDate.
 */
public class User implements UserInterface {
    private String firstName = "";
    private String lastName = "";
    private String username;
    private int permissions;
    private int rank;
    private CalendarDate joinDate;
    private boolean login;
    private String password;
    //private Lesson lessons; Implement after Lesson class is created


    /**
     Method: User
     Inputs:
     Returns:
     Description: Default constructor for new user object.
     */
    public User(){
        this.firstName = "null";
        this.lastName = "null";
        this.username = "null";
        this.permissions = 0;
        this.rank = 0;
        this.login = false;
        this.password = null;
        this.joinDate =  CalendarDate.today();
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public CalendarDate getJoinDate() {
        return this.joinDate;
    }

    @Override
    public void setJoinDate(CalendarDate joinDate) {
        this.joinDate = joinDate;
    }

    public String getJoinDateAsString() {
        return this.joinDate.getFullDateString();
    }

    @Override
    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    @Override
    public int getPermissions() {
        return this.permissions;
    }

    @Override
    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public int getRank() {
        return this.rank;
    }

    @Override
    public void login() {
        this.login = true;
    }

    @Override
    public boolean loginStatus() {
        return this.login;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String toString() {
        return "First Name: " + getFirstName() + "\n" +
                "Last Name: " + getLastName() + "\n" +
                "Username: " + getUsername() + "\n" +
                "Permissions: " + getPermissions() + "\n" +
                "Rank: " + getRank() + "\n" +
                "Password: " + getPassword() + "\n" +
                "Join Date: " + getJoinDateAsString() + "\n";
    }


}
