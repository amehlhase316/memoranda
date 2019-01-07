/**
 * NoteListImpl.java
 * Created on 21.02.2003, 15:43:26 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;
import java.util.Collection;
import java.util.Vector;

import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
/**
 * 
 */
/*$Id: NoteListImpl.java,v 1.14 2004/10/28 11:30:15 alexeya Exp $*/
public class NoteListImpl implements NoteList {

    private Project _project = null;
    private Document _doc = null;
    private Element _root = null;

//    public static final String NS_JNNL = "http://www.openmechanics.org/2003/jnotes-noteslist";

    /**
     * Constructor for NoteListImpl.
     */
    public NoteListImpl(Document doc, Project prj) {
        _doc = doc;
        _root = _doc.getRootElement();
        _project = prj;
    }

    public NoteListImpl(Project prj) {
    	
        //_root = new Element("noteslist", NS_JNNL);
        _root = new Element("noteslist");
        _doc = new Document(_root);
        _project = prj;    
    }

    public Collection getAllNotes() {
        Vector v = new Vector();
        Elements yrs = _root.getChildElements("year");
        for (int yi = 0; yi < yrs.size(); yi++) {
            Year y = new Year(yrs.get(yi));
            Vector ms = y.getMonths();
            for (int mi = 0; mi < ms.size(); mi++) {
                Month m = (Month) ms.get(mi);
                Vector ds = m.getDays();
                for (int di = 0; di < ds.size(); di++) {
                    Day d = (Day) ds.get(di);
					Vector ns = d.getNotes();
					for(int ni = 0; ni < ns.size(); ni++) {
						NoteElement n = (NoteElement) ns.get(ni);
						v.add(new NoteImpl(n.getElement(), _project));
					}
                }
            }
        }
        return v;
    }
    
    /**
     * @see main.java.memoranda.NoteList#getMarkedNotes()
     */
    public Collection getMarkedNotes() {
        Vector v = new Vector();
        Elements yrs = _root.getChildElements("year");
        for (int yi = 0; yi < yrs.size(); yi++) {
            Year y = new Year(yrs.get(yi));
            Vector ms = y.getMonths();
            for (int mi = 0; mi < ms.size(); mi++) {
                Month m = (Month) ms.get(mi);
                Vector ds = m.getDays();
                for (int di = 0; di < ds.size(); di++) {
                    Day d = (Day) ds.get(di);
					Vector ns = d.getNotes();
					for(int ni = 0; ni < ns.size(); ni++) {
						NoteElement ne = (NoteElement) ns.get(ni);
						Note n = new NoteImpl(ne.getElement(), _project);
						if (n.isMarked()) v.add(n);
                }
            }
        }
    }
	        return v;
	}

    public Collection getNotesForPeriod(CalendarDate startDate, CalendarDate endDate) {
        Vector v = new Vector();
        Elements yrs = _root.getChildElements("year");
        for (int yi = 0; yi < yrs.size(); yi++) {
            Year y = new Year(yrs.get(yi));
            if ((y.getValue() >= startDate.getYear()) && (y.getValue() <= endDate.getYear())) {
                Vector months = y.getMonths();
                for (int mi = 0; mi < months.size(); mi++) {
                    Month m = (Month) months.get(mi);
                    if (!((y.getValue() == startDate.getYear()) && (m.getValue() < startDate.getMonth()))
                        || !((y.getValue() == endDate.getYear()) && (m.getValue() > endDate.getMonth()))) {
                        Vector days = m.getDays();
                        for (int di = 0; di < days.size(); di++) {
                            Day d = (Day) days.get(di);
                            if (!((m.getValue() == startDate.getMonth()) && (d.getValue() < startDate.getDay()))
							|| !((m.getValue() == endDate.getMonth()) && (d.getValue() > endDate.getDay()))) {
								Vector ns = d.getNotes();
								for(int ni = 0; ni < ns.size(); ni++) {
									NoteElement n = (NoteElement) ns.get(ni);
									v.add(new NoteImpl(n.getElement(), _project));
								}
							}
                        }
                    }
                }
            }
        }
        return v;
    }

	/**
	 * returns the first note for a date.
	 * @param CalendarDate
	 * @return Note
	 */
	 
    public Note getNoteForDate(CalendarDate date) {
        Day d = getDay(date);
        if (d == null)
            return null;
		Vector ns = d.getNotes();
		if(ns.size()>0) {
			NoteElement n = (NoteElement) ns.get(0);
			Note currentNote = new NoteImpl(n.getElement(), _project);
			return currentNote; 
		}
		return null;
        //return new NoteImpl(d.getElement(), _project);
    }

    public Note createNoteForDate(CalendarDate date) {
        Year y = getYear(date.getYear());
        if (y == null)
            y = createYear(date.getYear());
        Month m = y.getMonth(date.getMonth());
        if (m == null)
            m = y.createMonth(date.getMonth());
        Day d = m.getDay(date.getDay());
        if (d == null) 
            d = m.createDay(date.getDay());
		NoteElement ne = d.createNote(Util.generateId());
        return new NoteImpl(ne.getElement(), _project);
    }
    
     /*
     * @see net.sf.memoranda.NoteList#removeNoteForDate(net.sf.memoranda.date.CalendarDate)
     */
/*    public void removeNoteForDate(CalendarDate date) {
        Day d = getDay(date);
        if (d == null) return;
        d.getElement().getParent().removeChild(d.getElement());             
    }
*/
	 public void removeNote(CalendarDate date, String id) {
        Day d = getDay(date);
        if (d == null) return;
		Vector ns = d.getNotes();
		for(int i=0;i<ns.size();i++) {
			NoteElement n = (NoteElement) ns.get(i);
			Element ne = n.getElement();
			if(ne.getAttribute("refid").getValue().equals(id)) d.getElement().removeChild(n.getElement());
		}
//		CurrentNote.set(null);
    }
	
    public Note getActiveNote() {
        //return CurrentNote.get(); 
    	return getNoteForDate(CurrentDate.get());
    	// FIXED: Must return the first note for today [alexeya]
    }

    private Year getYear(int y) {
        Elements yrs = _root.getChildElements("year");
        String yy = new Integer(y).toString();
        for (int i = 0; i < yrs.size(); i++)
            if (yrs.get(i).getAttribute("year").getValue().equals(yy))
                return new Year(yrs.get(i));
        //return createYear(y);
        return null;
    }

    private Year createYear(int y) {
        Element el = new Element("year");
        el.addAttribute(new Attribute("year", new Integer(y).toString()));
        _root.appendChild(el);
        return new Year(el);
    }
/*
    private Vector getYears() {
        Vector v = new Vector();
        Elements yrs = _root.getChildElements("year");
        for (int i = 0; i < yrs.size(); i++)
            v.add(new Year(yrs.get(i)));
        return v;
    }
*/
    private Day getDay(CalendarDate date) {
        Year y = getYear(date.getYear());
        if (y == null)
            return null;
        Month m = y.getMonth(date.getMonth());
        if (m == null)
            return null;
        return m.getDay(date.getDay());
    }

    private class Year {
        Element yearElement = null;

        public Year(Element el) {
            yearElement = el;
        }

        public int getValue() {
            return new Integer(yearElement.getAttribute("year").getValue()).intValue();
        }

        public Month getMonth(int m) {
            Elements ms = yearElement.getChildElements("month");
            String mm = new Integer(m).toString();
            for (int i = 0; i < ms.size(); i++)
                if (ms.get(i).getAttribute("month").getValue().equals(mm))
                    return new Month(ms.get(i));
            //return createMonth(m);
            return null;
        }

        private Month createMonth(int m) {
            Element el = new Element("month");
            el.addAttribute(new Attribute("month", new Integer(m).toString()));
            yearElement.appendChild(el);
            return new Month(el);
        }

        public Vector getMonths() {
            Vector v = new Vector();
            Elements ms = yearElement.getChildElements("month");
            for (int i = 0; i < ms.size(); i++)
                v.add(new Month(ms.get(i)));
            return v;
        }

        public Element getElement() {
            return yearElement;
        }

    }

    private class Month {
        Element mElement = null;

        public Month(Element el) {
            mElement = el;
        }

        public int getValue() {
            return new Integer(mElement.getAttribute("month").getValue()).intValue();
        }

        public Day getDay(int d) {
            if (mElement == null)
                return null;
            Elements ds = mElement.getChildElements("day");
            String dd = new Integer(d).toString();
            for (int i = 0; i < ds.size(); i++)
                if (ds.get(i).getAttribute("day").getValue().equals(dd))
                    return new Day(ds.get(i));
            //return createDay(d);
            return null;
        }

        private Day createDay(int d) {
            Element el = new Element("day");
            el.addAttribute(new Attribute("day", new Integer(d).toString()));
/*            el.addAttribute(
                new Attribute(
                    "date",
                    new CalendarDate(
                        d,
                        getValue(),
                        new Integer(((Element) mElement.getParent()).getAttribute("year").getValue()).intValue())
                        .toString()));
*/
            mElement.appendChild(el);
            return new Day(el);
        }

        public Vector getDays() {
            if (mElement == null)
                return null;
            Vector v = new Vector();
            Elements ds = mElement.getChildElements("day");
            for (int i = 0; i < ds.size(); i++)
                v.add(new Day(ds.get(i)));
            return v;
        }

        public Element getElement() {
            return mElement;
        }

    }

	
	/*
	 * private class Day
	 */
	 
    private class Day {
        Element dEl = null;

        public Day(Element el) {
            dEl = el;
            // Added to fix old '.notes' XML format 
            // Old-style XML is converted on the fly [alexeya]
            if (dEl.getAttribute("date") != null) {
            	Attribute dAttr = dEl.getAttribute("date");
            	Attribute tAttr = dEl.getAttribute("title");
				Element nEl = new Element("note");
				String date = dAttr.getValue().replace('/', '-');
				nEl.addAttribute(new Attribute("refid", date));
				nEl.addAttribute(new Attribute("title", tAttr.getValue()));
				dEl.appendChild(nEl);
            	dEl.removeAttribute(dAttr);            	
				dEl.removeAttribute(tAttr);
            }
        }

        public int getValue() {
            return new Integer(dEl.getAttribute("day").getValue()).intValue();
        }

        /*public Note getNote() {
            return new NoteImpl(dEl);
        }*/
		
		public NoteElement getNote(String d) {
            if (dEl == null) 
				return null;
            Elements ne = dEl.getChildElements("note");
            
            for (int i = 0; i < ne.size(); i++)
                if (ne.get(i).getAttribute("refid").getValue().equals(d))
                    return new NoteElement(ne.get(i));
            //return createDay(d);
            return null;
        }

        public NoteElement createNote(String d) {
            Element el = new Element("note");
//			el.addAttribute(new Attribute("refid", d));
/*            el.addAttribute(new Attribute("day", new Integer(d).toString()));
                        el.addAttribute(
                new Attribute(
                    "date",
                    new CalendarDate(
                        10,
                        10,
                        2004).toString()));
*/						
            dEl.appendChild(el);
            return new NoteElement(el);
        }

        public Vector getNotes() {
            if (dEl == null)
                return null;
            Vector v = new Vector();
            Elements ds = dEl.getChildElements("note");
            for (int i = 0; i < ds.size(); i++)
                v.add(new NoteElement(ds.get(i)));                                    
            return v;
        }

        public Element getElement() {
            return dEl;
        }
    }
	
	
	/*
	 * private class Day
	 */
	 
	private class NoteElement {
		Element nEl;
		
		public NoteElement(Element el) {
			nEl = el;
		}
		
		public Element getElement() {
			return nEl;
		}
	}
	
    /**
     * @see main.java.memoranda.NoteList#getXMLContent()
     */
    public Document getXMLContent() {
        return _doc;
    }
   
    

}
