package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import main.java.memoranda.util.Context;
import main.java.memoranda.util.Local;

/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: WorkPanel.java,v 1.9 2004/04/05 10:05:44 alexeya Exp $*/
public class WorkPanel extends JPanel {
	BorderLayout borderLayout1 = new BorderLayout();
	JToolBar toolBar = new JToolBar();
	JPanel panel = new JPanel();
	CardLayout cardLayout1 = new CardLayout();

	public DailyItemsPanel dailyItemsPanel = new DailyItemsPanel(this);
//	public ResourcesPanel filesPanel = new ResourcesPanel();
	public MapPanel mapPanel = new MapPanel();

	public RoutePanel routePanel = new RoutePanel();
	public BusAndDriverPanel busAndDriverPanel = new BusAndDriverPanel();
	public JButton agendaB = new JButton();
	public JButton tasksB = new JButton();
	public JButton busAndDriverButton = new JButton();
//
	public JButton routeB = new JButton();
	
	//removing "Notes" button
//	public JButton notesB = new JButton();
	// Removing "Resources" button
//	public JButton filesB = new JButton();
	
	// NEW BUTTON FOR MAP PANEL
	public JButton mapB = new JButton();

	public JButton testB = new JButton();

	JButton currentB = null;
	Border border1;

	public WorkPanel() {
		try {
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}

	void jbInit() throws Exception {
		border1 =
			BorderFactory.createCompoundBorder(
				BorderFactory.createBevelBorder(
					BevelBorder.LOWERED,
					Color.white,
					Color.white,
					new Color(124, 124, 124),
					new Color(178, 178, 178)),
				BorderFactory.createEmptyBorder(0, 2, 0, 0));

		this.setLayout(borderLayout1);
		toolBar.setOrientation(JToolBar.VERTICAL);
		toolBar.setBackground(Color.white);

		toolBar.setBorderPainted(false);
		toolBar.setFloatable(false);
		panel.setLayout(cardLayout1);

		agendaB.setBackground(Color.white);
		agendaB.setMaximumSize(new Dimension(60, 80));
		agendaB.setMinimumSize(new Dimension(30, 30));

		agendaB.setFont(new java.awt.Font("Dialog", 1, 10));
		agendaB.setPreferredSize(new Dimension(50, 50));
		agendaB.setBorderPainted(false);
		agendaB.setContentAreaFilled(false);
		agendaB.setFocusPainted(false);
		agendaB.setHorizontalTextPosition(SwingConstants.CENTER);
		agendaB.setText(Local.getString("Agenda"));
		agendaB.setVerticalAlignment(SwingConstants.TOP);
		agendaB.setVerticalTextPosition(SwingConstants.BOTTOM);
		agendaB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agendaB_actionPerformed(e);
			}
		});
		agendaB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/agenda.png")));
		agendaB.setOpaque(false);
		agendaB.setMargin(new Insets(0, 0, 0, 0));
		agendaB.setSelected(true);

//		eventsB.setBackground(Color.white);
//		eventsB.setMaximumSize(new Dimension(60, 80));
//		eventsB.setMinimumSize(new Dimension(30, 30));
//
//		eventsB.setFont(new java.awt.Font("Dialog", 1, 10));
//		eventsB.setPreferredSize(new Dimension(50, 50));
//		eventsB.setBorderPainted(false);
//		eventsB.setContentAreaFilled(false);
//		eventsB.setFocusPainted(false);
//		eventsB.setHorizontalTextPosition(SwingConstants.CENTER);
//		eventsB.setText(Local.getString("Events"));
//		eventsB.setVerticalAlignment(SwingConstants.TOP);
//		eventsB.setVerticalTextPosition(SwingConstants.BOTTOM);
//		eventsB.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				eventsB_actionPerformed(e);
//			}
//		});
//		eventsB.setIcon(
//			new ImageIcon(
//				main.java.memoranda.ui.AppFrame.class.getResource(
//					"/ui/icons/events.png")));
//		eventsB.setOpaque(false);
//		eventsB.setMargin(new Insets(0, 0, 0, 0));
		routeB.setBackground(Color.white);
		routeB.setMaximumSize(new Dimension(60, 80));
		routeB.setMinimumSize(new Dimension(30, 30));

		routeB.setFont(new java.awt.Font("Dialog", 1, 10));
		routeB.setPreferredSize(new Dimension(50, 50));
		routeB.setBorderPainted(false);
		routeB.setContentAreaFilled(false);
		routeB.setFocusPainted(false);
		routeB.setHorizontalTextPosition(SwingConstants.CENTER);
		routeB.setText(Local.getString("Routes"));
		routeB.setVerticalAlignment(SwingConstants.TOP);
		routeB.setVerticalTextPosition(SwingConstants.BOTTOM);
		routeB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				routeB_actionPerformed(e);
			}
		});
		routeB.setIcon(
				new ImageIcon(
						main.java.memoranda.ui.AppFrame.class.getResource(
								"/ui/icons/routes.png")));
		routeB.setOpaque(false);
		routeB.setMargin(new Insets(0, 0, 0, 0));
		//eventsB.setSelected(true);

		
		busAndDriverButton.setSelected(true);
		busAndDriverButton.setFont(new java.awt.Font("Dialog", 1, 9));
		busAndDriverButton.setMargin(new Insets(0, 0, 0, 0));
		busAndDriverButton.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/bus-driver.png")));
		busAndDriverButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		busAndDriverButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				busDriverButton_actionPerformed(e);
			}
		});
		busAndDriverButton.setVerticalAlignment(SwingConstants.TOP);
		busAndDriverButton.setText(Local.getString("<html><center>Buses &amp;<br>Drivers</center></html>"));
		busAndDriverButton.setHorizontalTextPosition(SwingConstants.CENTER);
		busAndDriverButton.setFocusPainted(false);
		busAndDriverButton.setBorderPainted(false);
		busAndDriverButton.setContentAreaFilled(false);
		busAndDriverButton.setPreferredSize(new Dimension(50, 50));
		busAndDriverButton.setMinimumSize(new Dimension(30, 30));
		busAndDriverButton.setOpaque(false);
		busAndDriverButton.setMaximumSize(new Dimension(60, 80));
		busAndDriverButton.setBackground(Color.white);
		
		
		tasksB.setSelected(true);
		tasksB.setFont(new java.awt.Font("Dialog", 1, 10));
		tasksB.setMargin(new Insets(0, 0, 0, 0));
		tasksB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/tasks.png")));
		tasksB.setVerticalTextPosition(SwingConstants.BOTTOM);
		tasksB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tasksB_actionPerformed(e);
			}
		});
		tasksB.setVerticalAlignment(SwingConstants.TOP);
		tasksB.setText(Local.getString("Tasks"));
		tasksB.setHorizontalTextPosition(SwingConstants.CENTER);
		tasksB.setFocusPainted(false);
		tasksB.setBorderPainted(false);
		tasksB.setContentAreaFilled(false);
		tasksB.setPreferredSize(new Dimension(50, 50));
		tasksB.setMinimumSize(new Dimension(30, 30));
		tasksB.setOpaque(false);
		tasksB.setMaximumSize(new Dimension(60, 80));
		tasksB.setBackground(Color.white);

//		notesB.setFont(new java.awt.Font("Dialog", 1, 10));
//		notesB.setBackground(Color.white);
//		notesB.setBorder(null);
//		notesB.setMaximumSize(new Dimension(60, 80));
//		notesB.setMinimumSize(new Dimension(30, 30));
//		notesB.setOpaque(false);
//		notesB.setPreferredSize(new Dimension(60, 50));
//		notesB.setBorderPainted(false);
//		notesB.setContentAreaFilled(false);
//		notesB.setFocusPainted(false);
//		notesB.setHorizontalTextPosition(SwingConstants.CENTER);
//		notesB.setText(Local.getString("Notes"));
//		notesB.setVerticalAlignment(SwingConstants.TOP);
//		notesB.setVerticalTextPosition(SwingConstants.BOTTOM);
//		notesB.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				notesB_actionPerformed(e);
//			}
//		});
//		notesB.setIcon(
//			new ImageIcon(
//				main.java.memoranda.ui.AppFrame.class.getResource(
//					"/ui/icons/notes.png")));
//		notesB.setMargin(new Insets(0, 0, 0, 0));
//		notesB.setSelected(true);
		this.setPreferredSize(new Dimension(1073, 300));

//		filesB.setSelected(true);
//		filesB.setMargin(new Insets(0, 0, 0, 0));
//		filesB.setIcon(
//			new ImageIcon(
//				main.java.memoranda.ui.AppFrame.class.getResource(
//					"/ui/icons/files.png")));
//		filesB.setVerticalTextPosition(SwingConstants.BOTTOM);
//		filesB.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				filesB_actionPerformed(e);
//			}
//		});
//		filesB.setFont(new java.awt.Font("Dialog", 1, 10));
//		filesB.setVerticalAlignment(SwingConstants.TOP);
//		filesB.setText(Local.getString("Resources"));
//		filesB.setHorizontalTextPosition(SwingConstants.CENTER);
//		filesB.setFocusPainted(false);
//		filesB.setBorderPainted(false);
//		filesB.setContentAreaFilled(false);
//		filesB.setPreferredSize(new Dimension(50, 50));
//		filesB.setMinimumSize(new Dimension(30, 30));
//		filesB.setOpaque(false);
//		filesB.setMaximumSize(new Dimension(60, 80));
//		filesB.setBackground(Color.white);
		
		mapB.setSelected(true);
		mapB.setMargin(new Insets(0, 0, 0, 0));
		mapB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/map.png")));
		mapB.setVerticalTextPosition(SwingConstants.BOTTOM);
		mapB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapB_actionPerformed(e);
			}
		});
		mapB.setFont(new java.awt.Font("Dialog", 1, 10));
		mapB.setVerticalAlignment(SwingConstants.TOP);
		mapB.setText(Local.getString("Map"));
		mapB.setHorizontalTextPosition(SwingConstants.CENTER);
		mapB.setFocusPainted(false);
		mapB.setBorderPainted(false);
		mapB.setContentAreaFilled(false);
		mapB.setPreferredSize(new Dimension(50, 50));
		mapB.setMinimumSize(new Dimension(30, 30));
		mapB.setOpaque(false);
		mapB.setMaximumSize(new Dimension(60, 80));
		mapB.setBackground(Color.white);

		this.add(toolBar, BorderLayout.WEST);
		this.add(panel, BorderLayout.CENTER);
		panel.add(dailyItemsPanel, "DAILYITEMS");
		panel.add(mapPanel, "MAP");
		panel.add(routePanel,"Routes");
		panel.add(busAndDriverPanel, "Buses/Drivers");
//		panel.add(filesPanel, "FILES");
		toolBar.add(agendaB, null);
		toolBar.add(routeB, null);
		toolBar.add(busAndDriverButton, null);
		toolBar.add(tasksB, null);
		toolBar.add(mapB, null);
//		toolBar.add(notesB, null);
//		toolBar.add(filesB, null);
		currentB = agendaB;
		// Default blue color
		currentB.setBackground(new Color(215, 225, 250));
		currentB.setOpaque(true);

		toolBar.setBorder(null);
		panel.setBorder(null);
		dailyItemsPanel.setBorder(null);
//		filesPanel.setBorder(null);
		mapPanel.setBorder(null);

	}

	public void selectPanel(String pan) {
		if (pan != null) {
//			if (pan.equals("NOTES"))
//				notesB_actionPerformed(null);
			if (pan.equals("TASKS"))
				tasksB_actionPerformed(null);
			else if (pan.equals("EVENTS"))
				routeB_actionPerformed(null);
//			else if (pan.equals("FILES"))
//				filesB_actionPerformed(null);
			else if (pan.equals("MAP"))
				mapB_actionPerformed(null);
			else if (pan.equals("BUSDRIVERS"))
				busDriverButton_actionPerformed(null);
		}
	}

	public void agendaB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("AGENDA");
		setCurrentButton(agendaB);
		Context.put("CURRENT_PANEL", "AGENDA");
	}

//	public void notesB_actionPerformed(ActionEvent e) {
//		cardLayout1.show(panel, "DAILYITEMS");
//		dailyItemsPanel.selectPanel("NOTES");
//		setCurrentButton(notesB);
//		Context.put("CURRENT_PANEL", "NOTES");
//	}

	public void busDriverButton_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "Buses/Drivers"); //switches to the correct window
		setCurrentButton(busAndDriverButton);
		Context.put("CURRENT_PANEL", "Buses/Drivers");
	}
	
	public void tasksB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("TASKS");
		setCurrentButton(tasksB);
		Context.put("CURRENT_PANEL", "TASKS");
	}

//	public void eventsB_actionPerformed(ActionEvent e) {
//		cardLayout1.show(panel, "Routes");
//     	dailyItemsPanel.selectPanel("EVENTS");
//		setCurrentButton(eventsB);
//		Context.put("CURRENT_PANEL", "Routes");
//	}

	public void routeB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "Routes");
		setCurrentButton(routeB);
		Context.put("CURRENT_PANEL", "Routes");
	}

//	public void filesB_actionPerformed(ActionEvent e) {
//		cardLayout1.show(panel, "FILES");
//		setCurrentButton(filesB);
//		Context.put("CURRENT_PANEL", "FILES");
//	}
	
	public void mapB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "MAP");
		setCurrentButton(mapB);
		Context.put("CURRENT_PANEL", "MAP");
	}

	void setCurrentButton(JButton cb) {
		currentB.setBackground(Color.white);
		currentB.setOpaque(false);
		currentB = cb;
		// Default color blue
		currentB.setBackground(new Color(215, 225, 250));
		currentB.setOpaque(true);
	}
}