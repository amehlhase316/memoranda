package memoranda;

import nu.xom.Attribute;
import nu.xom.Element;
import java.util.HashMap;

public class LessonList {
    public static HashMap<String, Element> lessons = new HashMap<>();

    public static void addLesson(Element el){
        lessons.put(el.getAttributeValue("id"), el);
    }

    public static Element getLesson(String id){
        return lessons.get(id);
    }
}
