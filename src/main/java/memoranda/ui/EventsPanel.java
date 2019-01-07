package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.memoranda.EventsManager;
import main.java.memoranda.EventsScheduler;
import main.java.memoranda.History;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.util.Configuration;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.Util;

/*$Id: EventsPanel.java,v 1.25 2005/02/19 10:06:25 rawsushi Exp $*/
public class EventsPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JButton historyBackB = new JButton();
    JToolBar eventsToolBar = new JToolBar();
    JButton historyForwardB = new JButton();
    JButton newEventB = new JButton();
    JButton editEventB = new JButton();
    JButton removeEventB = new JButton();
    JScrollPane scrollPane = new JScrollPane();
    EventsTable eventsTable = new EventsTable();
    JPopupMenu eventPPMenu = new JPopupMenu();
    JMenuItem ppEditEvent = new JMenuItem();
    JMenuItem ppRemoveEvent = new JMenuItem();
    JMenuItem ppNewEvent = new JMenuItem();
    DailyItemsPanel parentPanel = null;

    public EventsPanel(DailyItemsPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    void jbInit() throws Exception {
        eventsToolBar.setFloatable(false);

        historyBackB.setAction(History.historyBackAction);
        historyBackB.setFocusable(false);
        historyBackB.setBorderPainted(false);
        historyBackB.setToolTipText(Local.getString("History back"));
        historyBackB.setRequestFocusEnabled(false);
        historyBackB.setPreferredSize(new Dimension(24, 24));
        historyBackB.setMinimumSize(new Dimension(24, 24));
        historyBackB.setMaximumSize(new Dimension(24, 24));
        historyBackB.setText("");

        historyForwardB.setAction(History.historyForwardAction);
        historyForwardB.setBorderPainted(false);
        historyForwardB.setFocusable(false);
        historyForwardB.setPreferredSize(new Dimension(24, 24));
        historyForwardB.setRequestFocusEnabled(false);
        historyForwardB.setToolTipText(Local.getString("History forward"));
        historyForwardB.setMinimumSize(new Dimension(24, 24));
        historyForwardB.setMaximumSize(new Dimension(24, 24));
        historyForwardB.setText("");

        newEventB.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_new.png")));
        newEventB.setEnabled(true);
        newEventB.setMaximumSize(new Dimension(24, 24));
        newEventB.setMinimumSize(new Dimension(24, 24));
        newEventB.setToolTipText(Local.getString("New event"));
        newEventB.setRequestFocusEnabled(false);
        newEventB.setPreferredSize(new Dimension(24, 24));
        newEventB.setFocusable(false);
        newEventB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newEventB_actionPerformed(e);
            }
        });
        newEventB.setBorderPainted(false);

        editEventB.setBorderPainted(false);
        editEventB.setFocusable(false);
        editEventB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editEventB_actionPerformed(e);
            }
        });
        editEventB.setPreferredSize(new Dimension(24, 24));
        editEventB.setRequestFocusEnabled(false);
        editEventB.setToolTipText(Local.getString("Edit event"));
        editEventB.setMinimumSize(new Dimension(24, 24));
        editEventB.setMaximumSize(new Dimension(24, 24));
        editEventB.setEnabled(true);
        editEventB.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_edit.png")));

        removeEventB.setBorderPainted(false);
        removeEventB.setFocusable(false);
        removeEventB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeEventB_actionPerformed(e);
            }
        });
        removeEventB.setPreferredSize(new Dimension(24, 24));
        removeEventB.setRequestFocusEnabled(false);
        removeEventB.setToolTipText(Local.getString("Remove event"));
        removeEventB.setMinimumSize(new Dimension(24, 24));
        removeEventB.setMaximumSize(new Dimension(24, 24));
        removeEventB.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_remove.png")));

        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);
        eventsTable.setMaximumSize(new Dimension(32767, 32767));
        eventsTable.setRowHeight(24);
        eventPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
        ppEditEvent.setFont(new java.awt.Font("Dialog", 1, 11));
        ppEditEvent.setText(Local.getString("Edit event") + "...");
        ppEditEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppEditEvent_actionPerformed(e);
            }
        });
        ppEditEvent.setEnabled(false);
        ppEditEvent.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_edit.png")));
        ppRemoveEvent.setFont(new java.awt.Font("Dialog", 1, 11));
        ppRemoveEvent.setText(Local.getString("Remove event"));
        ppRemoveEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppRemoveEvent_actionPerformed(e);
            }
        });
        ppRemoveEvent.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_remove.png")));
        ppRemoveEvent.setEnabled(false);
        ppNewEvent.setFont(new java.awt.Font("Dialog", 1, 11));
        ppNewEvent.setText(Local.getString("New event") + "...");
        ppNewEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppNewEvent_actionPerformed(e);
            }
        });
        ppNewEvent.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_new.png")));
        scrollPane.getViewport().add(eventsTable, null);
        this.add(scrollPane, BorderLayout.CENTER);
        eventsToolBar.add(historyBackB, null);
        eventsToolBar.add(historyForwardB, null);
        eventsToolBar.addSeparator(new Dimension(8, 24));

        eventsToolBar.add(newEventB, null);
        eventsToolBar.add(removeEventB, null);
        eventsToolBar.addSeparator(new Dimension(8, 24));
        eventsToolBar.add(editEventB, null);

        this.add(eventsToolBar, BorderLayout.NORTH);

        PopupListener ppListener = new PopupListener();
        scrollPane.addMouseListener(ppListener);
        eventsTable.addMouseListener(ppListener);

        CurrentDate.addDateListener(new DateListener() {
            public void dateChange(CalendarDate d) {
                eventsTable.initTable(d);     
                boolean enbl = d.after(CalendarDate.today()) || d.equals(CalendarDate.today());
                newEventB.setEnabled(enbl);           
                ppNewEvent.setEnabled(enbl);
                editEventB.setEnabled(false);
                ppEditEvent.setEnabled(false);
                removeEventB.setEnabled(false);
                ppRemoveEvent.setEnabled(false);
            }
        });

        eventsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                boolean enbl = eventsTable.getSelectedRow() > -1;
                editEventB.setEnabled(enbl);
                ppEditEvent.setEnabled(enbl);
                removeEventB.setEnabled(enbl);
                ppRemoveEvent.setEnabled(enbl);
            }
        });
        editEventB.setEnabled(false);
        removeEventB.setEnabled(false);
        eventPPMenu.add(ppEditEvent);
        eventPPMenu.addSeparator();
        eventPPMenu.add(ppNewEvent);
        eventPPMenu.add(ppRemoveEvent);
		
		// remove events using the DEL key
		eventsTable.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e){
				if(eventsTable.getSelectedRows().length>0 
					&& e.getKeyCode()==KeyEvent.VK_DELETE)
					ppRemoveEvent_actionPerformed(null);
			}
			public void	keyReleased(KeyEvent e){}
			public void keyTyped(KeyEvent e){} 
		});
    }

    void editEventB_actionPerformed(ActionEvent e) {
        EventDialog dlg = new EventDialog(App.getFrame(), Local.getString("Event"));
        main.java.memoranda.Event ev =
            (main.java.memoranda.Event) eventsTable.getModel().getValueAt(
                eventsTable.getSelectedRow(),
                EventsTable.EVENT);
        
        dlg.timeSpin.getModel().setValue(ev.getTime());
        /*if (new CalendarDate(ev.getTime()).equals(CalendarDate.today())) 
            ((SpinnerDateModel)dlg.timeSpin.getModel()).setStart(new Date());
        else
        ((SpinnerDateModel)dlg.timeSpin.getModel()).setStart(CalendarDate.today().getDate());
        ((SpinnerDateModel)dlg.timeSpin.getModel()).setEnd(CalendarDate.tomorrow().getDate());*/    
        dlg.textField.setText(ev.getText());
        int rep = ev.getRepeat();
        if (rep > 0) {
            dlg.startDate.getModel().setValue(ev.getStartDate().getDate());
            if (rep == EventsManager.REPEAT_DAILY) {
                dlg.dailyRepeatRB.setSelected(true);
                dlg.dailyRepeatRB_actionPerformed(null);
                dlg.daySpin.setValue(new Integer(ev.getPeriod()));
            }
            else if (rep == EventsManager.REPEAT_WEEKLY) {
                dlg.weeklyRepeatRB.setSelected(true);
                dlg.weeklyRepeatRB_actionPerformed(null);
		int d = ev.getPeriod() - 1;
		if(Configuration.get("FIRST_DAY_OF_WEEK").equals("mon")) {
		    d--;
		    if(d<0) d=6;
		}
                dlg.weekdaysCB.setSelectedIndex(d);
            }
            else if (rep == EventsManager.REPEAT_MONTHLY) {
                dlg.monthlyRepeatRB.setSelected(true);
                dlg.monthlyRepeatRB_actionPerformed(null);
                dlg.dayOfMonthSpin.setValue(new Integer(ev.getPeriod()));
            }
	    else if (rep == EventsManager.REPEAT_YEARLY) {
		dlg.yearlyRepeatRB.setSelected(true);
		dlg.yearlyRepeatRB_actionPerformed(null);
		dlg.dayOfMonthSpin.setValue(new Integer(ev.getPeriod()));
	    }
        if (ev.getEndDate() != null) {
           dlg.endDate.getModel().setValue(ev.getEndDate().getDate());
           dlg.enableEndDateCB.setSelected(true);
           dlg.enableEndDateCB_actionPerformed(null);
        }
		if(ev.getWorkingDays()) {
			dlg.workingDaysOnlyCB.setSelected(true);
		}
		
        }

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        EventsManager.removeEvent(ev);
        
		Calendar calendar = new GregorianCalendar(Local.getCurrentLocale()); //Fix deprecated methods to get hours
		//by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
		calendar.setTime(((Date)dlg.timeSpin.getModel().getValue()));//Fix deprecated methods to get hours
		//by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
		int hh = calendar.get(Calendar.HOUR_OF_DAY);//Fix deprecated methods to get hours
		//by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
		int mm = calendar.get(Calendar.MINUTE);//Fix deprecated methods to get hours
		//by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
        
        //int hh = ((Date) dlg.timeSpin.getModel().getValue()).getHours();
        //int mm = ((Date) dlg.timeSpin.getModel().getValue()).getMinutes();
        String text = dlg.textField.getText();
        if (dlg.noRepeatRB.isSelected())
   	    EventsManager.createEvent(CurrentDate.get(), hh, mm, text);
        else {
	    updateEvents(dlg,hh,mm,text);
	}    
	saveEvents();
    }

    void newEventB_actionPerformed(ActionEvent e) {
        Calendar cdate = CurrentDate.get().getCalendar();
        // round down to hour
        cdate.set(Calendar.MINUTE,0);  
        Util.debug("Default time is " + cdate);
        
    	newEventB_actionPerformed(e, null, cdate.getTime(), cdate.getTime());
    }
    
    void newEventB_actionPerformed(ActionEvent e, String tasktext, Date startDate, Date endDate) {
    	EventDialog dlg = new EventDialog(App.getFrame(), Local.getString("New event"));
    	Dimension frmSize = App.getFrame().getSize();
    	Point loc = App.getFrame().getLocation();
    	if (tasktext != null) {
    		dlg.textField.setText(tasktext);
    	}
		dlg.startDate.getModel().setValue(startDate);
		dlg.endDate.getModel().setValue(endDate);
		dlg.timeSpin.getModel().setValue(startDate);

    	dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
    	dlg.setEventDate(startDate);
		dlg.setVisible(true);
    	if (dlg.CANCELLED)
    		return;
    	Calendar calendar = new GregorianCalendar(Local.getCurrentLocale()); //Fix deprecated methods to get hours
    	//by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
    	calendar.setTime(((Date)dlg.timeSpin.getModel().getValue()));//Fix deprecated methods to get hours
    	//by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
    	int hh = calendar.get(Calendar.HOUR_OF_DAY);//Fix deprecated methods to get hours
    	//by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
    	int mm = calendar.get(Calendar.MINUTE);//Fix deprecated methods to get hours
    	//by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
    	
    	//int hh = ((Date) dlg.timeSpin.getModel().getValue()).getHours();
    	//int mm = ((Date) dlg.timeSpin.getModel().getValue()).getMinutes();
    	String text = dlg.textField.getText();
		
		CalendarDate eventCalendarDate = new CalendarDate(dlg.getEventDate());
		
    	if (dlg.noRepeatRB.isSelected())
    		EventsManager.createEvent(eventCalendarDate, hh, mm, text);
    	else {
    		updateEvents(dlg,hh,mm,text);
    	}
    	saveEvents();
    }

    private void saveEvents() {
	CurrentStorage.get().storeEventsManager();
        eventsTable.refresh();
        EventsScheduler.init();
        parentPanel.calendar.jnCalendar.updateUI();
        parentPanel.updateIndicators();
    }

    private void updateEvents(EventDialog dlg, int hh, int mm, String text) {
	int rtype;
        int period;
        CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
        CalendarDate ed = null;
        if (dlg.enableEndDateCB.isSelected())
            ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
        if (dlg.dailyRepeatRB.isSelected()) {
            rtype = EventsManager.REPEAT_DAILY;
            period = ((Integer) dlg.daySpin.getModel().getValue()).intValue();
        }
        else if (dlg.weeklyRepeatRB.isSelected()) {
            rtype = EventsManager.REPEAT_WEEKLY;
            period = dlg.weekdaysCB.getSelectedIndex() + 1;
	    if (Configuration.get("FIRST_DAY_OF_WEEK").equals("mon")) {
		if(period==7) period=1;
		else period++;
	    }
        }
	else if (dlg.yearlyRepeatRB.isSelected()) {
	    rtype = EventsManager.REPEAT_YEARLY;
	    period = sd.getCalendar().get(Calendar.DAY_OF_YEAR);
	    if((sd.getYear() % 4) == 0 && sd.getCalendar().get(Calendar.DAY_OF_YEAR) > 60) period--;
	}
        else {
            rtype = EventsManager.REPEAT_MONTHLY;
            period = ((Integer) dlg.dayOfMonthSpin.getModel().getValue()).intValue();
        }
        EventsManager.createRepeatableEvent(rtype, sd, ed, period, hh, mm, text, dlg.workingDaysOnlyCB.isSelected());
    }

    void removeEventB_actionPerformed(ActionEvent e) {
		String msg;
		main.java.memoranda.Event ev;

		if(eventsTable.getSelectedRows().length > 1) 
			msg = Local.getString("Remove") + " " + eventsTable.getSelectedRows().length 
				+ " " + Local.getString("events") + "\n" + Local.getString("Are you sure?");
		else {
			ev = (main.java.memoranda.Event) eventsTable.getModel().getValueAt(
                eventsTable.getSelectedRow(),
                EventsTable.EVENT);
			msg = Local.getString("Remove event") + "\n'" 
				+ ev.getText() + "'\n" + Local.getString("Are you sure?");
		}

        int n =
            JOptionPane.showConfirmDialog(
                App.getFrame(),
                msg,
                Local.getString("Remove event"),
                JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION) return;

        for(int i=0; i< eventsTable.getSelectedRows().length;i++) {
			ev = (main.java.memoranda.Event) eventsTable.getModel().getValueAt(
                  eventsTable.getSelectedRows()[i], EventsTable.EVENT);
        EventsManager.removeEvent(ev);
		}
        eventsTable.getSelectionModel().clearSelection();
/*        CurrentStorage.get().storeEventsManager();
        eventsTable.refresh();
        EventsScheduler.init();
        parentPanel.calendar.jnCalendar.updateUI();
        parentPanel.updateIndicators();
*/ saveEvents();  
  }

    class PopupListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            if ((e.getClickCount() == 2) && (eventsTable.getSelectedRow() > -1))
                editEventB_actionPerformed(null);
        }

        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                eventPPMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }

    }
    void ppEditEvent_actionPerformed(ActionEvent e) {
        editEventB_actionPerformed(e);
    }
    void ppRemoveEvent_actionPerformed(ActionEvent e) {
        removeEventB_actionPerformed(e);
    }
    void ppNewEvent_actionPerformed(ActionEvent e) {
        newEventB_actionPerformed(e);
    }
}