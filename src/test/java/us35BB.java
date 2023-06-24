package test.java;
/**
 File: us35BB.java
 Author: Jonathan Blicharz
 Date: 6/23/23
 Description: File must test the User class.
 */
import main.java.memoranda.date.CalendarDate;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import main.java.memoranda.User;


/**
 Class: us35BB
 Description: Tests the functions of the User class
 */
public class us35BB {

    private final User user = new User();
    private CalendarDate date;

    @Before
    public void createUser() {
        user.setFirstName("Bob");
        user.setLastName("Smith");
        user.setUserID();
        user.setJoinDate(date);
        user.setPermissions(1);
        user.setRank(2);
    }

    @Test
    public void getUserFirstName() {
        assertEquals("Bob", user.getFirstName());
    }

    @Test
    public void getUserLastName() {
        assertEquals("Smith", user.getLastName());
    }

    @Test
    public void getUserID() {
        assertNotEquals(0, user.getUserID());
    }

    @Test
    public void getUserPermissions() {
        assertEquals(1, user.getPermissions());
    }

    @Test
    public void getUserRank() {
        assertEquals(2, user.getRank());
    }

}
