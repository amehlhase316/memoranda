package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.memoranda.CurrentProject;
import main.java.memoranda.NoteList;
import main.java.memoranda.Project;
import main.java.memoranda.ProjectListener;
import main.java.memoranda.ResourcesList;
import main.java.memoranda.TaskList;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.util.Local;

/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: JNCalendarPanel.java,v 1.9 2004/04/05 10:05:44 alexeya Exp $*/
public class JNCalendarPanel extends JPanel {

  CalendarDate _date = CurrentDate.get();
  JToolBar navigationBar = new JToolBar();
  JPanel mntyPanel = new JPanel(new BorderLayout());
  JPanel navbPanel = new JPanel(new BorderLayout());
  JButton dayForwardB = new JButton();
  JPanel dayForwardBPanel = new JPanel();
  JButton todayB = new JButton();
  JPanel todayBPanel = new JPanel();
  JPanel dayBackBPanel = new JPanel();
  JButton dayBackB = new JButton();
  JComboBox monthsCB = new JComboBox(Local.getMonthNames());
  BorderLayout borderLayout4 = new BorderLayout();
  JNCalendar jnCalendar = new JNCalendar(CurrentDate.get());
  JPanel jnCalendarPanel = new JPanel();
  BorderLayout borderLayout5 = new BorderLayout();
  JSpinner yearSpin = new JSpinner(new SpinnerNumberModel(jnCalendar.get().getYear(), 1980, 2999, 1));
  JSpinner.NumberEditor yearSpinner = new JSpinner.NumberEditor(yearSpin, "####");

  boolean ignoreChange = false;

  private Vector selectionListeners = new Vector();

  Border border1;
  Border border2;

  public JNCalendarPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      new ExceptionDialog(ex);
    }
  }

  public Action dayBackAction =
        new AbstractAction(
            "Go one day back",
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/back16.png"))) {
        public void actionPerformed(ActionEvent e) {
            dayBackB_actionPerformed(e);
        }
  };
  
  public Action dayForwardAction =
        new AbstractAction(
            "Go one day forward",
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/forward16.png"))) {
        public void actionPerformed(ActionEvent e) {
            dayForwardB_actionPerformed(e);
        }
  };
  
  public Action todayAction =
        new AbstractAction(
            "Go to today",
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/today16.png"))) {
        public void actionPerformed(ActionEvent e) {
            todayB_actionPerformed(e);
        }
  };
      
  void jbInit() throws Exception {
    //dayBackAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, KeyEvent.ALT_MASK));
    //dayForwardAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, KeyEvent.ALT_MASK));
    todayAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_HOME, KeyEvent.ALT_MASK));
    
    monthsCB.setRequestFocusEnabled(false);
    monthsCB.setMaximumRowCount(12);
    monthsCB.setPreferredSize(new Dimension(50 , 20));
    border1 = BorderFactory.createEmptyBorder(0,0,5,0);
    border2 = BorderFactory.createEmptyBorder();
    this.setLayout(new BorderLayout());
    navigationBar.setFloatable(false);
    dayForwardB.setAction(dayForwardAction);
    dayForwardB.setMinimumSize(new Dimension(24, 24));
    dayForwardB.setOpaque(false);
    dayForwardB.setPreferredSize(new Dimension(24, 24));
    dayForwardB.setRequestFocusEnabled(false);
    dayForwardB.setBorderPainted(false);
    dayForwardB.setFocusPainted(false);
    dayForwardB.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/forward.png")));
    dayForwardB.setText("");
    dayForwardB.setToolTipText(Local.getString("One day forward"));
    
    dayForwardBPanel.setAlignmentX((float) 0.0);
    dayForwardBPanel.setMinimumSize(new Dimension(40, 24));
    dayForwardBPanel.setOpaque(false);
    dayForwardBPanel.setPreferredSize(new Dimension(40, 24));
    
    todayB.setAction(todayAction);
    todayB.setMinimumSize(new Dimension(24, 24));
    todayB.setOpaque(false);
    todayB.setPreferredSize(new Dimension(24, 24));
    todayB.setRequestFocusEnabled(false);
    todayB.setBorderPainted(false);
    todayB.setFocusPainted(false);
    todayB.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/today.png")));
    todayB.setText("");
    todayB.setToolTipText(Local.getString("To today"));
    
    dayBackBPanel.setAlignmentX((float) 1.5);
    dayBackBPanel.setMinimumSize(new Dimension(40, 24));
    dayBackBPanel.setOpaque(false);
    dayBackBPanel.setPreferredSize(new Dimension(40, 24));
    
    dayBackB.setAction(dayBackAction);
    dayBackB.setMinimumSize(new Dimension(24, 24));
    dayBackB.setOpaque(false);
    dayBackB.setPreferredSize(new Dimension(24, 24));
    dayBackB.setRequestFocusEnabled(false);
    dayBackB.setToolTipText("");
    dayBackB.setBorderPainted(false);
    dayBackB.setFocusPainted(false);
    dayBackB.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/back.png")));
    dayBackB.setText("");
    dayBackB.setToolTipText(Local.getString("One day back"));
    
    yearSpin.setPreferredSize(new Dimension(70, 20));
    yearSpin.setRequestFocusEnabled(false);
        yearSpin.setEditor(yearSpinner);
    navbPanel.setMinimumSize(new Dimension(202, 30));
    navbPanel.setOpaque(false);
    navbPanel.setPreferredSize(new Dimension(155, 30));
    jnCalendar.getTableHeader().setFont(new java.awt.Font("Dialog", 1, 10));
    jnCalendar.setFont(new java.awt.Font("Dialog", 0, 10));
    jnCalendar.setGridColor(Color.lightGray);
    jnCalendarPanel.setLayout(borderLayout5);
    todayBPanel.setMinimumSize(new Dimension(68, 24));
    todayBPanel.setOpaque(false);
    todayBPanel.setPreferredSize(new Dimension(51, 24));
    this.add(navigationBar, BorderLayout.NORTH);
    navigationBar.add(navbPanel, null);
    navbPanel.add(dayBackBPanel, BorderLayout.WEST);
    dayBackBPanel.add(dayBackB, null);
    navbPanel.add(todayBPanel, BorderLayout.CENTER);
    todayBPanel.add(todayB, null);
    navbPanel.add(dayForwardBPanel, BorderLayout.EAST);
    dayForwardBPanel.add(dayForwardB, null);
    this.add(mntyPanel,  BorderLayout.SOUTH);
    mntyPanel.add(monthsCB, BorderLayout.CENTER);
    mntyPanel.add(yearSpin,  BorderLayout.EAST);
    this.add(jnCalendarPanel,  BorderLayout.CENTER);
    jnCalendar.getTableHeader().setPreferredSize(new Dimension(200, 15));
    jnCalendarPanel.add(jnCalendar.getTableHeader(), BorderLayout.NORTH);
    jnCalendarPanel.add(jnCalendar, BorderLayout.CENTER);
    jnCalendar.addSelectionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        setCurrentDateDay(jnCalendar.get(), jnCalendar.get().getDay());
      }
    });
    /*CurrentDate.addChangeListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        _date = CurrentDate.get();
        refreshView();
      }
    });*/
    monthsCB.setFont(new java.awt.Font("Dialog", 0, 11));

    monthsCB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        monthsCB_actionPerformed(e);
      }
    });

    yearSpin.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        yearSpin_actionPerformed();
      }
    });
    CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange(Project p, NoteList nl, TaskList tl, ResourcesList rl) {}
            public void projectWasChanged() {
                jnCalendar.updateUI();
            }
        });


    refreshView();
    yearSpin.setBorder(border2);
    
  }

  public void set(CalendarDate date) {
    _date = date;
    refreshView();
  }

  public CalendarDate get() {
    return _date;
  }

  public void addSelectionListener(ActionListener al) {
        selectionListeners.add(al);
    }

  private void notifyListeners() {
        for (Enumeration en = selectionListeners.elements(); en.hasMoreElements();)
             ((ActionListener) en.nextElement()).actionPerformed(new ActionEvent(this, 0, "Calendar event"));
  }

  private void setCurrentDateDay(CalendarDate dt, int d) {
    if (ignoreChange) return;
    if (_date.equals(dt)) return;
    _date = new CalendarDate(d, _date.getMonth(), _date.getYear());
    notifyListeners();
  }

  private void refreshView() {
    ignoreChange = true;
    jnCalendar.set(_date);
    monthsCB.setSelectedIndex(new Integer(_date.getMonth()));
    yearSpin.setValue(new Integer(_date.getYear()));
    ignoreChange = false;
  }

  void monthsCB_actionPerformed(ActionEvent e) {
    if (ignoreChange) return;
    _date = new CalendarDate(_date.getDay(), monthsCB.getSelectedIndex(), _date.getYear());
    jnCalendar.set(_date);
    notifyListeners();
  }

  void yearSpin_actionPerformed() {
    if (ignoreChange) return;
    _date = new CalendarDate(_date.getDay(), _date.getMonth(), ((Integer)yearSpin.getValue()).intValue());
    jnCalendar.set(_date);
    notifyListeners();
  }

  void dayBackB_actionPerformed(ActionEvent e) {
    Calendar cal = _date.getCalendar();
    cal.add(Calendar.DATE, -1); cal.getTime();
    _date = new CalendarDate(cal);
    refreshView();
    notifyListeners();
  }

  void todayB_actionPerformed(ActionEvent e) {
    _date = CalendarDate.today();
    refreshView();
    notifyListeners();
  }

  void dayForwardB_actionPerformed(ActionEvent e) {
    Calendar cal = _date.getCalendar();
    cal.add(Calendar.DATE, 1); cal.getTime();
    _date = new CalendarDate(cal);
    refreshView();
    notifyListeners();
  }



}