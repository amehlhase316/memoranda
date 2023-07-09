import memoranda.ProjectManager;
import memoranda.date.CalendarDate;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

import static memoranda.util.FileStorage.*;
import static org.junit.Assert.*;

public class StorageAndUtilUnitTest {
    /**
     * Test case to verify saving and opening a document.
     */
    @Test
    public void testSaveAndOpenDocument() throws Exception {
        File tempFile;
        tempFile = File.createTempFile("temp", null);
        Element root = new Element("root");
        Document doc = new Document(root);
        root.appendChild("child1");
        root.appendChild("child2");

        // Save the document to the temp file
        memoranda.util.FileStorage.saveDocument(doc, tempFile.getAbsolutePath());

        // Load the saved document and check that it's the same as the original
        Document loadedDoc = new Builder().build(new FileInputStream(tempFile));
        Serializer serializer = new Serializer(System.out);
        serializer.write(doc);
        assertEquals(doc.toXML(), loadedDoc.toXML());
    }

    /**
     * Test case to verify opening a saved document.
     */
    @Test
    public void testOpenDocument() throws Exception {
        File tempFile;
        tempFile = File.createTempFile("temp", null);
        Element root = new Element("root");
        Document doc = new Document(root);
        root.appendChild("child1");
        root.appendChild("child2");

        // Save the document to the temp file
        memoranda.util.FileStorage.saveDocument(doc, tempFile.getAbsolutePath());

        // Load the saved document using openDocument
        Document loadedDoc = memoranda.util.FileStorage.openDocument(new FileInputStream(tempFile));

        // Check that the loaded document is the same as the original
        Serializer serializer = new Serializer(System.out);
        serializer.write(doc);
        assertEquals(doc.toXML(), loadedDoc.toXML());
    }

    /**
     * Test case to verify if a document exists.
     */
    @Test
    public void testDocumentExists() throws Exception {
        File tempFile;
        tempFile = File.createTempFile("temp", null);

        Element root = new Element("root");
        Document doc = new Document(root);
        root.appendChild("child1");
        root.appendChild("child2");

        // Save the document to the temp file
        memoranda.util.FileStorage.saveDocument(doc, tempFile.getAbsolutePath());

        // Check if the file exists
        assertTrue(memoranda.util.FileStorage.documentExists(tempFile.getAbsolutePath()));
    }

    /**
     * Test case to verify conversion of milliseconds to hours.
     */
    @Test
    public void testGetHoursFromMillis() {
        long ms = 3600000L; // 1 hour in milliseconds
        String expected = "1.0";
        String result = memoranda.util.Util.getHoursFromMillis(ms);
        assertEquals(expected, result);
    }

    /**
     * Test case to verify conversion of hours to milliseconds.
     */
    @Test
    public void testGetMillisFromHours() {
        String hours = "2.5";
        int expected = 9000000; // 2.5 hours in milliseconds
        long result = (long) memoranda.util.Util.getMillisFromHours(hours);
        assertEquals(expected, result);
    }
}
