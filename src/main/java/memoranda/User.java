package main.java.memoranda;
/**
File: User.java
Author: Jonathan Blicharz
Date: 6/23/23
Description: File contains User class which is used to create new User objects.
*/


import main.java.memoranda.date.CalendarDate;
/**
 Class: User
 Description: Creates User object for users of registry. Allows set/get of object attributes.
 Object contains firstname, lastname, userID, permissions, rank and joinDate.
 */
public class User {
    private String firstName = "";
    private String lastName = "";
    private int userID;
    private int permissions;
    private int rank;
    private CalendarDate joinDate;
    //private Lesson lessons; Implement after Lesson class is created


    /**
     Method: User
     Inputs:
     Returns:
     Description: Default constructor for new user object.
     */
    public User(){
        this.firstName = null;
        this.lastName = null;
        this.userID = 0;
        this.permissions = 0;
        this.rank = 0;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setUserID() {
        this.userID = this.lastName.hashCode() + this.firstName.hashCode();
    }

    public int getUserID() {
        return this.userID;
    }

    public void setJoinDate(CalendarDate date) {
        this.joinDate = date;
    }

    public CalendarDate getJoinDate() {
        return this.joinDate;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public int getPermissions() {
        return this.permissions;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }

    //public Lesson getLessons() Implement after Lesson class is created


}
