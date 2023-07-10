/*
 File: User.java
 Author: Jonathan Blicharz
 Date: 6/23/23
 Description: File contains User class which is used to create new User objects.
 */
package main.java.memoranda;

import main.java.memoranda.date.CalendarDate;

import java.util.ArrayList;
import java.util.List;

/**
 Class: User
 Description: Creates User object for users of registry. Allows set/get of object attributes.
 Object contains firstname, lastname, userID, permissions, rank and joinDate.
 */
public class User implements UserInterface {
    //Permission variables
    static final int USER = 0;
    static final int TRAINER = 1;
    static final int ADMIN = 2;
    
    private String firstName = "";
    private String lastName = "";
    private String username;
    private int permissions;
    private int rank;
    private CalendarDate joinDate;
    private boolean login;
    private String password;

    
    //Availability is saved as integers for each day of the week.
    // Stored in a 7x2 matrix so each day has a start and end time.
    private int[][] availability = new int [7][2]; 

    private List<String> notes;



    /**
     Method: User
     Inputs:
     Returns:
     Description: Default constructor for new user object.
     */
    public User(){
        notes = new ArrayList<>();
        this.firstName = "null";
        this.lastName = "null";
        this.username = "null";
        this.permissions = 0;
        this.rank = 0;
        this.login = false;
        this.password = null;
        this.joinDate =  CalendarDate.today();
        for(int i = 0; i < 7; i++) {
            availability[i][0] = 0;
            availability[i][1] = 0;
        }
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
    public void logout() {
        this.login = false;
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
    
    public void setAvailability(int[][] times) {
        for(int i = 0; i < 7; i++) {
            availability[i][0] = times[i][0];
            availability[i][1] = times[i][1];
        }
    }
    
    public int getStart(int day) {
        return availability[day][0];
    }
    
    public int getEnd(int day) {
        return availability[day][1];
    }

    @Override
    public void setNotes(String txt) {
        this.notes.add(txt);
    }

    @Override
    public List<String> getNotes() {
        return this.notes;
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
