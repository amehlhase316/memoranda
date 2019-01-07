/**
 * JNCalendar.java Created on 13.02.2003, 21:26:38 Alex Package:
 * net.sf.memoranda.ui
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net Copyright (c) 2003
 *         Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.util.Configuration;
import main.java.memoranda.util.Local;

/**
 *  
 */
/*$Id: JNCalendar.java,v 1.8 2004/11/05 07:38:10 pbielen Exp $*/
public class JNCalendar extends JTable {

	private CalendarDate _date = null;
	private boolean ignoreChange = false;
	private Vector selectionListeners = new Vector();
	CalendarDate startPeriod = null;
	CalendarDate endPeriod = null;
	public JNCalendarCellRenderer renderer = new JNCalendarCellRenderer();

	public JNCalendar() {
		this(CurrentDate.get());
	}
	/**
	 * Constructor for JNCalendar.
	 */
	public JNCalendar(CalendarDate date) {
		super();
		/* table properties */
		setCellSelectionEnabled(true);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(false);
		set(date);

		/* selection listeners */
		final ListSelectionModel rowSM = getSelectionModel();
		final ListSelectionModel colSM = getColumnModel().getSelectionModel();
		ListSelectionListener lsl = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
					//Ignore extra messages.
	if (e.getValueIsAdjusting())
					return;
				if (ignoreChange)
					return;
				int row = getSelRow();
				int col = getSelCol();
				Object val = getModel().getValueAt(row, col);
				if (val != null) {
					if (val
						.toString()
						.equals(new Integer(_date.getDay()).toString()))
						return;
					_date =
						new CalendarDate(
							new Integer(val.toString()).intValue(),
							_date.getMonth(),
							_date.getYear());
					notifyListeners();
				} else {
					//getSelectionModel().clearSelection();
					doSelection();
				}
			}

		};
		rowSM.addListSelectionListener(lsl);
		colSM.addListSelectionListener(lsl);
	}

	int getSelRow() {
		return this.getSelectedRow();
	}

	int getSelCol() {
		return this.getSelectedColumn();
	}

	public JNCalendar(CalendarDate date, CalendarDate sd, CalendarDate ed) {
		this(date);
		setSelectablePeriod(sd, ed);
	}

	public void set(CalendarDate date) {
		_date = date;
		setCalendarParameters();
		ignoreChange = true;
		this.setModel(new JNCalendarModel());
		ignoreChange = false;
		doSelection();
	}

	public CalendarDate get() {
		return _date;
	}

	public void addSelectionListener(ActionListener al) {
		selectionListeners.add(al);
	}

	public void setSelectablePeriod(CalendarDate sd, CalendarDate ed) {
		startPeriod = sd;
		endPeriod = ed;
	}

	private void notifyListeners() {
		for(int i=0;i<selectionListeners.size();i++) {
			((ActionListener) selectionListeners.get(i)).actionPerformed(
				new ActionEvent(this, 0, "Calendar event"));
		}
	}

	public TableCellRenderer getCellRenderer(int row, int column) {
		Object d = this.getModel().getValueAt(row, column);
		/*
		 * if (d != null) return new JNCalendarCellRenderer( new
		 * CalendarDate(new Integer(d.toString()).intValue(), _date.getMonth(),
		 * _date.getYear()));
		 */
		if (d != null)
			renderer.setDate(
				new CalendarDate(
					new Integer(d.toString()).intValue(),
					_date.getMonth(),
					_date.getYear()));
		else
			renderer.setDate(null);
		return renderer;
	}

	void doSelection() {
		ignoreChange = true;
		int selRow = getRow(_date.getDay());
		int selCol = getCol(_date.getDay());
		this.setRowSelectionInterval(selRow, selRow);
		this.setColumnSelectionInterval(selCol, selCol);
		ignoreChange = false;
	}

	int getRow(int day) {
		return ((day - 1) + firstDay) / 7;
	}

	int getCol(int day) {
		return ((day - 1) + firstDay) % 7;
	}

	int firstDay;
	int daysInMonth;

	void setCalendarParameters() {
		int d = 1;

		Calendar cal = _date.getCalendar();

		if (Configuration.get("FIRST_DAY_OF_WEEK").equals("mon")) {
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			d = 2;
		} else
			cal.setFirstDayOfWeek(Calendar.SUNDAY);

		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.getTime();
		firstDay = cal.get(Calendar.DAY_OF_WEEK) - d;
		if (firstDay == -1)
			firstDay = 6;
		daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/*$Id: JNCalendar.java,v 1.8 2004/11/05 07:38:10 pbielen Exp $*/
public class JNCalendarModel extends AbstractTableModel {

		private String[] dayNames = Local.getWeekdayNames();

		public JNCalendarModel() {
			super();
		}

		public int getColumnCount() {
			return 7;
		}

		public Object getValueAt(int row, int col) {
			//int pos = (row * 7 + col) - firstDay + 1;
			int pos = (row * 7 + (col + 1)) - firstDay;
			if ((pos > 0) && (pos <= daysInMonth))
				return new Integer(pos);
			else
				return null;

		}

		public int getRowCount() {
			return 6;
		}

		public String getColumnName(int col) {
			return dayNames[col];
		}

	}

}
