package main.java.memoranda;

import main.java.memoranda.date.CalendarDate;

import java.util.Calendar;

public interface UserInterface {
    public void setFirstName(String firstName);
    public String getFirstName();
    public void setLastName(String lastName);
    public String getLastName();
    public void setUsername(String username);
    public String getUsername();
    public CalendarDate getJoinDate();
    public void setPermissions(int permissions);
    public int getPermissions();
    public void setRank(int rank);
    public int getRank();
    public void setLessons(LessonPlanner lessons);
    public LessonPlanner getLessons();
    public void login();
    public boolean loginStatus();
    public void setPassword(String password);
    public String getPassword();
}
