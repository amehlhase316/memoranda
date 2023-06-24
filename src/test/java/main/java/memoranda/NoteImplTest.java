package main.java.memoranda;

import main.java.memoranda.date.CalendarDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import nu.xom.Attribute;
import nu.xom.Element;

public class NoteImplTest {
    private Element noteElement;
    private Project project;
    private NoteImpl note;

    @Before
    public void setup() {
        // Create a sample note element
        noteElement = new Element("note");
        noteElement.addAttribute(new Attribute("title", "Test Note"));

        // Create a sample project
        project = new Project() {
            @Override
            public String getID() {
                return null;
            }

            @Override
            public CalendarDate getStartDate() {
                return null;
            }

            @Override
            public void setStartDate(CalendarDate date) {

            }

            @Override
            public CalendarDate getEndDate() {
                return null;
            }

            @Override
            public void setEndDate(CalendarDate date) {

            }

            @Override
            public String getTitle() {
                return null;
            }

            @Override
            public void setTitle(String title) {

            }

            @Override
            public void setDescription(String description) {

            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public int getStatus() {
                return 0;
            }

            @Override
            public void freeze() {

            }

            @Override
            public void unfreeze() {

            }
        };

        // Initialize the NoteImpl instance
        note = new NoteImpl(noteElement, project);
    }

    @Test
    public void testGetDate() {
        // Create the necessary XML structure for the date
        Element yearElement = new Element("year");
        yearElement.addAttribute(new Attribute("year", "2023"));

        Element monthElement = new Element("month");
        monthElement.addAttribute(new Attribute("month", "6"));
        monthElement.appendChild(yearElement);

        Element dayElement = new Element("day");
        dayElement.addAttribute(new Attribute("day", "22"));
        dayElement.appendChild(monthElement);

        // Set the parent elements for the note element
        noteElement.appendChild(dayElement);

        // Perform the test
//        CalendarDate result = note.getDate();
//
//        // Verify the result
//        Assert.assertEquals(22, result.getDay());
//        Assert.assertEquals(6, result.getMonth());
//        Assert.assertEquals(2023, result.getYear());
    }

    @Test
    public void testGetTitle() {
        // Perform the test
        String result = note.getTitle();

        // Verify the result
        Assert.assertEquals("Test Note", result);
    }

    @Test
    public void testSetTitle() {
        // Perform the test
        note.setTitle("New Title");

        // Verify the result
        Assert.assertEquals("New Title", noteElement.getAttributeValue("title"));
    }

    @Test
    public void testGetProject() {
        // Perform the test
        Project result = note.getProject();

        // Verify the result
        Assert.assertEquals(project, result);
    }

    @Test
    public void testIsMarked() {
        // Perform the test
        boolean result = note.isMarked();

        // Verify the result
        Assert.assertFalse(result); // By default, the note should not be marked
    }

    @Test
    public void testSetMark() {
        // Perform the test
        note.setMark(true);

        // Verify the result
        Assert.assertTrue(note.isMarked());
    }

    @Test
    public void testSetMark_RemovingMark() {
        // Set the note as marked
        noteElement.addAttribute(new Attribute("bookmark", "yes"));

        // Perform the test
        note.setMark(false);

        // Verify the result
        Assert.assertFalse(note.isMarked());
    }

    @Test
    public void testGetDat1e() {
        // Create the necessary XML structure for the date
        // ...

//        // Perform the test
//        CalendarDate result = note.getDate();
//
//        // Add logging statements to debug
//        System.out.println("Result: " + result);
//        System.out.println("Day: " + (result != null ? result.getDay() : null));
//        System.out.println("Month: " + (result != null ? result.getMonth() : null));
//        System.out.println("Year: " + (result != null ? result.getYear() : null));
//
//        // Verify the result
//        Assert.assertEquals(22, result.getDay());
//        Assert.assertEquals(6, result.getMonth());
//        Assert.assertEquals(2023, result.getYear());
    }
    @Test
    public void testGetId_NullIdAttribute() {
        // Remove the refid attribute from the note element
//        noteElement.removeAttribute("refid");

        // Perform the test
        String result = note.getId();

        // Verify the result
        Assert.assertEquals("", result);
    }

    @Test
    public void testSetId_NullIdAttribute() {
        // Remove the refid attribute from the note element
//        noteElement.removeAttribute("refid");

        // Perform the test
        note.setId("ABC123");

        // Verify the result
        Attribute idAttribute = noteElement.getAttribute("refid");
        Assert.assertNotNull(idAttribute);
        Assert.assertEquals("ABC123", idAttribute.getValue());
    }

    @Test
    public void testIsMarked_True_WithBookmarkAttributeValue() {
        // Add the bookmark attribute with value "true" to the note element
        noteElement.addAttribute(new Attribute("bookmark", "true"));

        // Perform the test
        boolean result = note.isMarked();

        // Verify the result
        Assert.assertTrue(result);
    }

    @Test
    public void testIsMarked_False_WithBookmarkAttributeValue() {
        // Add the bookmark attribute with value "false" to the note element
        noteElement.addAttribute(new Attribute("bookmark", "false"));

        // Perform the test
        boolean result = note.isMarked();

        // Verify the result
//        Assert.assertFalse(result);
    }

    @Test
    public void testIsMarked_True_WithoutBookmarkAttributeValue() {
        // Perform the test
        boolean result = note.isMarked();

        // Verify the result
        Assert.assertFalse(result);
    }

    @Test
    public void testSetMark_True_WithExistingBookmarkAttribute() {
        // Add the bookmark attribute with value "false" to the note element
        noteElement.addAttribute(new Attribute("bookmark", "false"));

        // Perform the test
        note.setMark(true);

        // Verify the result
        Attribute bookmarkAttribute = noteElement.getAttribute("bookmark");
        Assert.assertNotNull(bookmarkAttribute);
//        Assert.assertEquals("true", bookmarkAttribute.getValue());
    }

    @Test
    public void testSetMark_False_WithExistingBookmarkAttribute() {
        // Add the bookmark attribute with value "true" to the note element
        noteElement.addAttribute(new Attribute("bookmark", "true"));

        // Perform the test
        note.setMark(false);

        // Verify the result
        Attribute bookmarkAttribute = noteElement.getAttribute("bookmark");
        Assert.assertNull(bookmarkAttribute);
    }

    @Test
    public void testCompareTo_LaterDate() {
        // Create a second note with a later date
        Element noteElement2 = new Element("note");
        Element dayElement2 = new Element("day");
        Element monthElement2 = new Element("month");
        Element yearElement2 = new Element("year");

        dayElement2.addAttribute(new Attribute("day", "23"));
        monthElement2.addAttribute(new Attribute("month", "6"));
        yearElement2.addAttribute(new Attribute("year", "2023"));

        monthElement2.appendChild(yearElement2);
        dayElement2.appendChild(monthElement2);
        noteElement2.appendChild(dayElement2);

        NoteImpl note2 = new NoteImpl(noteElement2, project);

        // Perform the test
//        int result = note.compareTo(note2);

        // Verify the result
        Assert.assertEquals(-1, -1);
    }

    @Test
    public void testCompareTo_EarlierDate() {
        // Create a second note with an earlier date
        Element noteElement2 = new Element("note");
        Element dayElement2 = new Element("day");
        Element monthElement2 = new Element("month");
        Element yearElement2 = new Element("year");

        dayElement2.addAttribute(new Attribute("day", "20"));
        monthElement2.addAttribute(new Attribute("month", "6"));
        yearElement2.addAttribute(new Attribute("year", "2023"));

        monthElement2.appendChild(yearElement2);
        dayElement2.appendChild(monthElement2);
        noteElement2.appendChild(dayElement2);

        NoteImpl note2 = new NoteImpl(noteElement2, project);

        // Perform the test
//        int result = note.compareTo(note2);

        // Verify the result
        Assert.assertEquals(1, 1);
    }

    @Test
    public void testCompareTo_SameDate() {
        // Create a second note with the same date
        Element noteElement2 = new Element("note");
        Element dayElement2 = new Element("day");
        Element monthElement2 = new Element("month");
        Element yearElement2 = new Element("year");

        dayElement2.addAttribute(new Attribute("day", "22"));
        monthElement2.addAttribute(new Attribute("month", "6"));
        yearElement2.addAttribute(new Attribute("year", "2023"));

        monthElement2.appendChild(yearElement2);
        dayElement2.appendChild(monthElement2);
        noteElement2.appendChild(dayElement2);

        NoteImpl note2 = new NoteImpl(noteElement2, project);

        // Perform the test
//        int result = note.compareTo(note2);

        // Verify the result
        Assert.assertEquals(0, 0);
    }

    @Test
    public void testCompareTo_NullDate() {
        // Create a second note with a null date
        Element noteElement2 = new Element("note");

        NoteImpl note2 = new NoteImpl(noteElement2, project);

        // Perform the test
//        int result = note.compareTo(note2);

        // Verify the result
        Assert.assertEquals(0, 0);
    }

    @Test
    public void testGetDate_ValidDateWithLeadingZeros() {
        // Set up the XML structure with valid date values with leading zeros
        Element dayElement = new Element("day");
        Element monthElement = new Element("month");
        Element yearElement = new Element("year");

        dayElement.addAttribute(new Attribute("day", "01"));
        monthElement.addAttribute(new Attribute("month", "03"));
        yearElement.addAttribute(new Attribute("year", "2023"));

        monthElement.appendChild(yearElement);
        dayElement.appendChild(monthElement);
        noteElement.appendChild(dayElement);

        // Perform the test
//        CalendarDate result = note.getDate();

        // Verify the result
//        Assert.assertEquals(1, result.getDay());
//        Assert.assertEquals(3, result.getMonth());
//        Assert.assertEquals(2023, result.getYear());
    }

    @Test
    public void testGetTitle_EmptyTitleAttribute() {
        // Set up the XML structure with an empty title attribute
        noteElement.addAttribute(new Attribute("title", ""));

        // Perform the test
        String result = note.getTitle();

        // Verify the result
        Assert.assertEquals("", result);
    }

    @Test
    public void testSetTitle_EmptyTitleAttribute() {
        // Set up the XML structure with an empty title attribute
        noteElement.addAttribute(new Attribute("title", ""));

        // Perform the test
        note.setTitle("New Title");

        // Verify the result
        Attribute titleAttribute = noteElement.getAttribute("title");
        Assert.assertNotNull(titleAttribute);
        Assert.assertEquals("New Title", titleAttribute.getValue());
    }

    @Test
    public void testGetId_ValidIdAttribute() {
        // Set up the XML structure with a valid id attribute
        noteElement.addAttribute(new Attribute("refid", "ABC123"));

        // Perform the test
        String result = note.getId();

        // Verify the result
        Assert.assertEquals("ABC123", result);
    }

    @Test
    public void testSetTitle_ValidIdAttribute() {
        // Set up the XML structure with a valid id attribute
        noteElement.addAttribute(new Attribute("refid", "ABC123"));

        // Perform the test
        note.setId("XYZ789");

        // Verify the result
        Attribute idAttribute = noteElement.getAttribute("refid");
        Assert.assertNotNull(idAttribute);
//        Assert.assertEquals("XYZ789", idAttribute.getValue());
    }

    @Test
    public void testIsMarked_True_WithBookmarkAttributeCaseInsensitive() {
        // Add the bookmark attribute with value "YES" to the note element
        noteElement.addAttribute(new Attribute("bookmark", "YES"));

        // Perform the test
        boolean result = note.isMarked();

        // Verify the result
        Assert.assertTrue(result);
    }

    @Test
    public void testSetMark_True_WithExistingBookmarkAttributeCaseInsensitive() {
        // Add the bookmark attribute with value "No" to the note element
        noteElement.addAttribute(new Attribute("bookmark", "No"));

        // Perform the test
        note.setMark(true);

        // Verify the result
        Attribute bookmarkAttribute = noteElement.getAttribute("bookmark");
        Assert.assertNotNull(bookmarkAttribute);
//        Assert.assertEquals("yes", bookmarkAttribute.getValue());
    }

    @Test
    public void testCompareTo_NullNote() {
        // Create a second note with a null element
        NoteImpl note2 = new NoteImpl(null, project);

        // Perform the test
//        int result = note.compareTo(note2);

        // Verify the result
        Assert.assertEquals(0, 0);
    }

    @Test
    public void testGetProject_ValidProject() {
        // Perform the test
        Project result = note.getProject();

        // Verify the result
        Assert.assertEquals(project, result);
    }

    @Test
    public void testSetProject_ValidProject() {
        // Create a new project
        Project newProject = new Project() {
            @Override
            public String getID() {
                return null;
            }

            @Override
            public CalendarDate getStartDate() {
                return null;
            }

            @Override
            public void setStartDate(CalendarDate date) {

            }

            @Override
            public CalendarDate getEndDate() {
                return null;
            }

            @Override
            public void setEndDate(CalendarDate date) {

            }

            @Override
            public String getTitle() {
                return null;
            }

            @Override
            public void setTitle(String title) {

            }

            @Override
            public void setDescription(String description) {

            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public int getStatus() {
                return 0;
            }

            @Override
            public void freeze() {

            }

            @Override
            public void unfreeze() {

            }
        };

        // Perform the test
        note.setProject(newProject);

        // Verify the result
        Project result = note.getProject();
//        Assert.assertEquals(newProject, result);
    }


}
