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

	public JButton homeB = new JButton(); //Used to be Agenda
	public JButton signUpB = new JButton(); //Used to be Tasks
	public JButton trainerB = new JButton(); //Used to be Events
	public JButton adminB = new JButton(); //Used to be files

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

		homeB.setBackground(Color.white);
		homeB.setMaximumSize(new Dimension(60, 80));
		homeB.setMinimumSize(new Dimension(30, 30));

		homeB.setFont(new java.awt.Font("Dialog", 1, 10));
		homeB.setPreferredSize(new Dimension(50, 50));
		homeB.setBorderPainted(false);
		homeB.setContentAreaFilled(false);
		homeB.setFocusPainted(false);
		homeB.setHorizontalTextPosition(SwingConstants.CENTER);
		homeB.setText(Local.getString("Home"));
		homeB.setVerticalAlignment(SwingConstants.TOP);
		homeB.setVerticalTextPosition(SwingConstants.BOTTOM);
		homeB.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				homeB_actionPerformed(e);
			}
		});
		homeB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/rhrHomeIcon.png")));
		homeB.setOpaque(false);
		homeB.setMargin(new Insets(0, 0, 0, 0));
		homeB.setSelected(true);

		trainerB.setBackground(Color.white);
		trainerB.setMaximumSize(new Dimension(60, 80));
		trainerB.setMinimumSize(new Dimension(30, 30));

		trainerB.setFont(new java.awt.Font("Trainer", 1, 10));
		trainerB.setPreferredSize(new Dimension(50, 50));
		trainerB.setBorderPainted(false);
		trainerB.setContentAreaFilled(false);
		trainerB.setFocusPainted(false);
		trainerB.setHorizontalTextPosition(SwingConstants.CENTER);
		trainerB.setText(Local.getString("Trainer"));
		trainerB.setVerticalAlignment(SwingConstants.TOP);
		trainerB.setVerticalTextPosition(SwingConstants.BOTTOM);
		trainerB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trainerB_actionPerformed(e);
			}
		});
		trainerB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/rhrTrainerIcon.png")));
		trainerB.setOpaque(false);
		trainerB.setMargin(new Insets(0, 0, 0, 0));
		//trainerB.setSelected(true);

		signUpB.setSelected(true);
		signUpB.setFont(new java.awt.Font("Dialog", 1, 10));
		signUpB.setMargin(new Insets(0, 0, 0, 0));
		signUpB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/rhrSignupIcon.png")));
		signUpB.setVerticalTextPosition(SwingConstants.BOTTOM);
		signUpB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signUpB_actionPerformed(e);
			}
		});
		signUpB.setVerticalAlignment(SwingConstants.TOP);
		signUpB.setText(Local.getString("Sign Up"));
		signUpB.setHorizontalTextPosition(SwingConstants.CENTER);
		signUpB.setFocusPainted(false);
		signUpB.setBorderPainted(false);
		signUpB.setContentAreaFilled(false);
		signUpB.setPreferredSize(new Dimension(50, 50));
		signUpB.setMinimumSize(new Dimension(30, 30));
		signUpB.setOpaque(false);
		signUpB.setMaximumSize(new Dimension(60, 80));
		signUpB.setBackground(Color.white);

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
					"/ui/icons/rhrNotesIcon.png")));
		notesB.setMargin(new Insets(0, 0, 0, 0));
		notesB.setSelected(true);
		this.setPreferredSize(new Dimension(1073, 300));

		adminB.setSelected(true);
		adminB.setMargin(new Insets(0, 0, 0, 0));
		adminB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(

					"/ui/icons/rhrAdminIcon.png")));

		adminB.setVerticalTextPosition(SwingConstants.BOTTOM);
		adminB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminB_actionPerformed(e);
			}
		});
		adminB.setFont(new java.awt.Font("Dialog", 1, 10));
		adminB.setVerticalAlignment(SwingConstants.TOP);

		adminB.setText(Local.getString("Admin"));

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
		toolBar.add(homeB, null); 
		toolBar.add(signUpB, null); 
		toolBar.add(notesB, null);
	    toolBar.add(trainerB, null); 
		toolBar.add(adminB, null);
		currentB = homeB;

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
				signUpB_actionPerformed(null);
			else if (pan.equals("EVENTS"))
				trainerB_actionPerformed(null);
			else if (pan.equals("FILES"))
				adminB_actionPerformed(null);
		}
	}

	public void homeB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("AGENDA");
		setCurrentButton(homeB);
		Context.put("CURRENT_PANEL", "AGENDA");
	}

	public void notesB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("NOTES");
		setCurrentButton(notesB);
		Context.put("CURRENT_PANEL", "NOTES");
	}

	public void signUpB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("TASKS");
		setCurrentButton(signUpB);
		Context.put("CURRENT_PANEL", "TASKS");
	}

	public void trainerB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("EVENTS");
		setCurrentButton(trainerB);
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