package main.java.memoranda;

import nu.xom.Attribute;
import nu.xom.Element;

import java.util.ArrayList;
import java.util.HashMap;

public class LessonList {
    public static HashMap<String, Element> lessons = new HashMap<>();
    public static ArrayList<String> listedLessons = new ArrayList<>();
    public static ArrayList<Element> lessonsArray = new ArrayList<>();

    public static void addLesson(Element el){
        lessons.put(el.getAttributeValue("id"), el);
        lessonsArray.add(el);

        String lessonInfo = new String("ID: " + el.getAttributeValue("id") + " Name: " + el.getAttributeValue("name") + " Room: " + el.getAttributeValue("room")
                + " Date: " + el.getAttributeValue("month") + "-" + el.getAttributeValue("date") + "-" + el.getAttributeValue("year") + " Time: "
                + el.getAttributeValue("hour") + ":00" );
        listedLessons.add(lessonInfo);
    }

    public static Element getLesson(String id){
        return lessons.get(id);
    }

    public static Element getLesson(int index){
        Element el = lessons.get(lessonsArray.get(index).getAttributeValue("id"));
        return el;
    }

    public static void removeLesson(int index){
        lessons.remove(lessonsArray.get(index).getAttributeValue("id"));
        lessonsArray.remove(index);
        listedLessons.remove(index);
    }
}
