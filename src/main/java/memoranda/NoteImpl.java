/**
 * NoteImpl.java
 * Created on 13.02.2003, 15:36:55 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import main.java.memoranda.date.CalendarDate;
import nu.xom.Attribute;
import nu.xom.Element;

/**
 * 
 */
/*$Id: NoteImpl.java,v 1.6 2004/10/06 19:15:44 ivanrise Exp $*/
public class NoteImpl implements Note, Comparable {
    
    private Element _el = null; 
    private Project _project;
    
    /**
     * Constructor for NoteImpl.
     */
    public NoteImpl(Element el, Project project) {
        _el = el;
        _project = project;
    }

    /**
     * @see main.java.memoranda.Note#getDate()
     */
    public CalendarDate getDate() {
		Element day = (Element)_el.getParent();
		Element month = (Element)day.getParent();
		Element year = (Element)month.getParent();

     //   return new CalendarDate(day.getAttribute("date").getValue());
		
		return new CalendarDate(new Integer(day.getAttribute("day").getValue()).intValue(), 
								new Integer(month.getAttribute("month").getValue()).intValue(),
								new Integer(year.getAttribute("year").getValue()).intValue());

    }
    
    public Project getProject() {
        return _project;
    }
    /**
     * @see main.java.memoranda.Note#getTitle()
     */
    public String getTitle() {
        Attribute ta = _el.getAttribute("title");
        if (ta == null) return "";
        return _el.getAttribute("title").getValue();
    }
    /**
     * @see main.java.memoranda.Note#setTitle(java.lang.String)
     */
    public void setTitle(String s) {
        Attribute ta = _el.getAttribute("title");
        if (ta == null) _el.addAttribute(new Attribute("title", s));
        else 
            ta.setValue(s);
    }
	
	/**
     * @see main.java.memoranda.Note#getId
     */
	
	public String getId() {
		Attribute id = _el.getAttribute("refid");
		if (id==null) return "";
		return _el.getAttribute("refid").getValue();
	}
	
	/**
     * @see main.java.memoranda.Note#setId(java.lang.String)
     */
	 
	public void setId(String s) {
		Attribute id = _el.getAttribute("refid");
		if(id==null) _el.addAttribute(new Attribute("refid", s));
	}
    /**
     * @see main.java.memoranda.Note#isMarked()
     */
    public boolean isMarked() {
        return _el.getAttribute("bookmark") != null;        
    }
    /**
     * @see main.java.memoranda.Note#setMark(boolean)
     */
    public void setMark(boolean mark) {
        Attribute ma = _el.getAttribute("bookmark");        
        if (ma == null) {
            if (mark)
                _el.addAttribute(new Attribute("bookmark", "yes"));
            return;
        }
        else if (!mark)
            _el.removeAttribute(ma);
    }
	
	/*
	 * Comparable interface
	 */
	public int compareTo(Object o) {
		Note note = (Note) o;
		if(getDate().getDate().getTime() > note.getDate().getDate().getTime())
			return 1;
		else if(getDate().getDate().getTime() < note.getDate().getDate().getTime())
			return -1;
		else 
			return 0;
	}
    
}
