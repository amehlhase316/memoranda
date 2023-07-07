
import memoranda.util.Local;
import memoranda.util.Util;
import org.junit.*;

import java.util.Calendar;

import static org.junit.Assert.*;

public class utilandLocalUnitTest {
    @Test
    public void testGenerateId() {
        Util util = new Util();
        String id1 = util.generateId();
        String id2 = util.generateId();

        assertNotNull(id1);
        assertNotNull(id2);
        assertNotEquals(id1, id2);
    }

    @Test
    public void testGenerateIdNotNull() {
        Util util = new Util();
        String id = util.generateId();
        assertNotNull(id);
    }

    @Test
    public void testParseDateStamp() {
        String dateString = "4/16/2023";
        int[] expectedDate = new int[]{4, 16, 2023};

        int[] actualDate = Util.parseDateStamp(dateString);

        assertArrayEquals(expectedDate, actualDate);
    }

    @Test
    public void testParseInvalidDateStamp() {
        String dateString = "Invalid Date";
        int[] expectedDate = new int[]{0, 0, 0};

        int[] actualDate = Util.parseDateStamp(dateString);

        assertArrayEquals(expectedDate, actualDate);
    }

    @Test
    public void testGetDateStamp() {
        Util util = new Util();
        Calendar cal = Calendar.getInstance();
        cal.set(2023, Calendar.APRIL, 16);

        String expectedDateStamp = "16/3/2023";
        String actualDateStamp = util.getDateStamp(cal);

        assertEquals(expectedDateStamp, actualDateStamp);
    }

    @Test
    public void testGetDateStampWithDifferentDate() {
        Util util = new Util();
        Calendar cal = Calendar.getInstance();
        cal.set(2023, Calendar.JUNE, 22);

        String expectedDateStamp = "22/5/2023";
        String actualDateStamp = util.getDateStamp(cal);

        assertEquals(expectedDateStamp, actualDateStamp);
    }

    @Test
    public void testGetDateString() {
        Local loc = new Local();
        int month = Calendar.APRIL;
        int dayOfMonth = 16;
        int year = 2023;
        int format = 0;

        String expectedOutput = "Sunday, April 16, 2023";
        String actualOutput = loc.getDateString(month, dayOfMonth, year, format);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testGetDateStringWithDifferentFormat() {
        Local loc = new Local();
        int month = Calendar.MAY;
        int dayOfMonth = 22;
        int year = 2023;
        int format = 1;

        String expectedOutput = "May 22, 2023";
        String actualOutput = loc.getDateString(month, dayOfMonth, year, format);

        assertEquals(expectedOutput, actualOutput);
    }
}
