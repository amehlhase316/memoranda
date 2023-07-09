/*
 File: User.java
 Author: Jonathan Blicharz
 Date: 6/23/23
 Description: File contains User class which is used to create new User objects.
 */
package main.java.memoranda;

import main.java.memoranda.date.CalendarDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    private LessonPlanner lessons;
    private boolean login;
    private String password;
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
        this.lessons = null;
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
    public void setLessons(LessonPlanner lessons) {
        this.lessons = lessons;
    }

    @Override
    public LessonPlanner getLessons() {
        return this.lessons;
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

    @Override
    public void setNotes(String txt) {
        this.notes.add(txt);
    }

    @Override
    public List<String> getNotes() {
        return this.notes;
    }


}
