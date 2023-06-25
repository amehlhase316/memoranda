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

	public JButton notesB = new JButton();
	public DailyItemsPanel dailyItemsPanel = new DailyItemsPanel(this);
	public AdminPanel adminPanel = new AdminPanel();
	public JButton overviewB = new JButton(); //Used to be Agenda
	public JButton enrollB = new JButton(); //Used to be Tasks
	public JButton classesB = new JButton(); //Used to be Events
	public JButton adminB = new JButton();
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

		overviewB.setBackground(Color.white);
		overviewB.setMaximumSize(new Dimension(60, 80));
		overviewB.setMinimumSize(new Dimension(30, 30));

		overviewB.setFont(new java.awt.Font("Dialog", 1, 10));
		overviewB.setPreferredSize(new Dimension(50, 50));
		overviewB.setBorderPainted(false);
		overviewB.setContentAreaFilled(false);
		overviewB.setFocusPainted(false);
		overviewB.setHorizontalTextPosition(SwingConstants.CENTER);
		overviewB.setText(Local.getString("Overview"));
		overviewB.setVerticalAlignment(SwingConstants.TOP);
		overviewB.setVerticalTextPosition(SwingConstants.BOTTOM);
		overviewB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				overviewB_actionPerformed(e);
			}
		});
		overviewB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/agenda.png")));
		overviewB.setOpaque(false);
		overviewB.setMargin(new Insets(0, 0, 0, 0));
		overviewB.setSelected(true);

		classesB.setBackground(Color.white);
		classesB.setMaximumSize(new Dimension(60, 80));
		classesB.setMinimumSize(new Dimension(30, 30));

		classesB.setFont(new java.awt.Font("Dialog", 1, 10));
		classesB.setPreferredSize(new Dimension(50, 50));
		classesB.setBorderPainted(false);
		classesB.setContentAreaFilled(false);
		classesB.setFocusPainted(false);
		classesB.setHorizontalTextPosition(SwingConstants.CENTER);
		classesB.setText(Local.getString("Classes"));
		classesB.setVerticalAlignment(SwingConstants.TOP);
		classesB.setVerticalTextPosition(SwingConstants.BOTTOM);
		classesB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				classesB_actionPerformed(e);
			}
		});
		classesB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/events.png")));
		classesB.setOpaque(false);
		classesB.setMargin(new Insets(0, 0, 0, 0));
		//classesB.setSelected(true);

		enrollB.setSelected(true);
		enrollB.setFont(new java.awt.Font("Dialog", 1, 10));
		enrollB.setMargin(new Insets(0, 0, 0, 0));
		enrollB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/tasks.png")));
		enrollB.setVerticalTextPosition(SwingConstants.BOTTOM);
		enrollB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enrollB_actionPerformed(e);
			}
		});
		enrollB.setVerticalAlignment(SwingConstants.TOP);
		enrollB.setText(Local.getString("Enroll"));
		enrollB.setHorizontalTextPosition(SwingConstants.CENTER);
		enrollB.setFocusPainted(false);
		enrollB.setBorderPainted(false);
		enrollB.setContentAreaFilled(false);
		enrollB.setPreferredSize(new Dimension(50, 50));
		enrollB.setMinimumSize(new Dimension(30, 30));
		enrollB.setOpaque(false);
		enrollB.setMaximumSize(new Dimension(60, 80));
		enrollB.setBackground(Color.white);

		notesB.setFont(new java.awt.Font("Dialog", 1, 10));
		notesB.setBackground(Color.white);
		notesB.setBorder(null);
		notesB.setMaximumSize(new Dimension(60, 80));
		notesB.setMinimumSize(new Dimension(30, 30));
		notesB.setOpaque(false);
		notesB.setPreferredSize(new Dimension(60, 50));
		notesB.setBorderPainted(false);
		notesB.setContentAreaFilled(false);
		notesB.setFocusPainted(false);
		notesB.setHorizontalTextPosition(SwingConstants.CENTER);
		notesB.setText(Local.getString("Notes"));
		notesB.setVerticalAlignment(SwingConstants.TOP);
		notesB.setVerticalTextPosition(SwingConstants.BOTTOM);
		notesB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				notesB_actionPerformed(e);
			}
		});
		notesB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/notes.png")));
		notesB.setMargin(new Insets(0, 0, 0, 0));
		notesB.setSelected(true);
		this.setPreferredSize(new Dimension(1073, 300));

		adminB.setSelected(true);
		adminB.setMargin(new Insets(0, 0, 0, 0));
		adminB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/files.png")));
		adminB.setVerticalTextPosition(SwingConstants.BOTTOM);
		adminB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminB_actionPerformed(e);
			}
		});
		adminB.setFont(new java.awt.Font("Dialog", 1, 10));
		adminB.setVerticalAlignment(SwingConstants.TOP);
		adminB.setText(Local.getString("Resources"));
		adminB.setHorizontalTextPosition(SwingConstants.CENTER);
		adminB.setFocusPainted(false);
		adminB.setBorderPainted(false);
		adminB.setContentAreaFilled(false);
		adminB.setPreferredSize(new Dimension(50, 50));
		adminB.setMinimumSize(new Dimension(30, 30));
		adminB.setOpaque(false);
		adminB.setMaximumSize(new Dimension(60, 80));
		adminB.setBackground(Color.white);
		this.add(toolBar, BorderLayout.WEST);
		this.add(panel, BorderLayout.CENTER);
		panel.add(dailyItemsPanel, "DAILYITEMS");
		panel.add(adminPanel, "FILES");
		toolBar.add(overviewB, null); 
		toolBar.add(classesB, null); 
		toolBar.add(enrollB, null); 
		toolBar.add(notesB, null);
		toolBar.add(adminB, null);
		currentB = overviewB;
		// Default blue color
		currentB.setBackground(new Color(215, 225, 250));
		currentB.setOpaque(true);

		toolBar.setBorder(null);
		panel.setBorder(null);
		dailyItemsPanel.setBorder(null);
		adminPanel.setBorder(null);

	}

	public void selectPanel(String pan) {
		if (pan != null) {
			if (pan.equals("NOTES"))
				notesB_actionPerformed(null);
			else if (pan.equals("TASKS"))
				enrollB_actionPerformed(null);
			else if (pan.equals("EVENTS"))
				classesB_actionPerformed(null);
			else if (pan.equals("FILES"))
				adminB_actionPerformed(null);
		}
	}

	public void overviewB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("AGENDA");
		setCurrentButton(overviewB);
		Context.put("CURRENT_PANEL", "AGENDA");
	}

	public void notesB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("NOTES");
		setCurrentButton(notesB);
		Context.put("CURRENT_PANEL", "NOTES");
	}

	public void enrollB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("TASKS");
		setCurrentButton(enrollB);
		Context.put("CURRENT_PANEL", "TASKS");
	}

	public void classesB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("EVENTS");
		setCurrentButton(classesB);
		Context.put("CURRENT_PANEL", "EVENTS");
	}

	public void adminB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "FILES");
		setCurrentButton(adminB);
		Context.put("CURRENT_PANEL", "FILES");
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