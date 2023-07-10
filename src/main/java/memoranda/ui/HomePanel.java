/*
 * HomePanel.java
 * @auther Jonathan Blicharz
 * @date 6/24/23
 */
package main.java.memoranda.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import main.java.memoranda.*;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.Util;

import nu.xom.Element;

/**
 Class: HomePanel
 Description: Builds home panel GUI using Swing.
 */

public class HomePanel extends JPanel {
	BorderLayout borderLayout1 = new BorderLayout();
	JButton loginB = new JButton();
	JToolBar toolBar = new JToolBar();
	JButton logoutB = new JButton();
	JEditorPane viewer = new JEditorPane("text/html", "");
	String[] priorities = {"Very High","High","Medium","Low","Very Low"};
	JScrollPane scrollPane = new JScrollPane();
	JPanel panel = new JPanel();
	JPanel addNotesPanel = new JPanel();

	JPanel userFirstName = new JPanel();

	UserList userList = new UserList();
	JScrollPane notePanel = new JScrollPane();
	JScrollPane upcomingClassPanel = new JScrollPane();

	DailyItemsPanel parentPanel = null;
	JFrame frameLogin;
	JFrame frameLogout;
	User user;
	boolean loggedIn = false;

	JLabel userFirstNameLabel = new JLabel("", SwingConstants.CENTER);

	JLabel userLastNameLabel = new JLabel("", SwingConstants.CENTER);

	JLabel userIDLabel = new JLabel("", SwingConstants.CENTER);
	JPanel userID = new JPanel();
	JLabel userRankLabel = new JLabel("", SwingConstants.CENTER);
	JPanel userRank = new JPanel();
	JPanel userLastName = new JPanel();
	JLabel userJoinDateLabel = new JLabel("Date Joined", SwingConstants.CENTER);
	JPanel userJoinDate = new JPanel();

	HashMap<String, User> users = UserList.users;

	JPanel userPanel = new JPanel();

	GridBagLayout layout = new GridBagLayout();

	String[][] data = {};
	String[] column ={"Class ID","Class Time","Class Date"};
	JTable jt = new JTable(data,column);

	JLabel lessons = new JLabel("", SwingConstants.CENTER);

	JLabel notesLabel = new JLabel("", SwingConstants.CENTER);

	JLabel loggedOut = new JLabel("<html>Please login to view<br/>Username: login<br/>Password: password</html>", SwingConstants.CENTER);

	JButton addNote = new JButton("Add Note");

	JFrame frameLoginPopUp = new JFrame();

	JFrame frameLogoutPopUp = new JFrame();
	JFrame frameNewNote = new JFrame();

	JFrame frameAddNote = new JFrame();

	JPanel innerNotes = new JPanel();

	JButton noteButton = new JButton();

	JFrame frameNote = new JFrame();
	JFrame frameEditNote = new JFrame();






	Collection expandedTasks;
	String gotoTask = null;

	boolean isActive = true;

	public HomePanel(DailyItemsPanel _parentPanel) {
		try {
			parentPanel = _parentPanel;
			jbInit();
			launchHomePanel();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
			ex.printStackTrace();
		}
	}
	void jbInit() throws Exception {
		expandedTasks = new ArrayList();

		toolBar.setFloatable(false);
		viewer.setEditable(false);
		viewer.setOpaque(false);
		viewer.addHyperlinkListener(new HyperlinkListener() {

			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					String d = e.getDescription();
					if (d.equalsIgnoreCase("memoranda:events"))
						parentPanel.alarmB_actionPerformed(null);
					else if (d.startsWith("memoranda:tasks")) {
						String id = d.split("#")[1];
						CurrentProject.set(ProjectManager.getProject(id));
						parentPanel.taskB_actionPerformed(null);
					} else if (d.startsWith("memoranda:project")) {
						String id = d.split("#")[1];
						CurrentProject.set(ProjectManager.getProject(id));
					} else if (d.startsWith("memoranda:removesticker")) {
						String id = d.split("#")[1];
						StickerConfirmation stc = new StickerConfirmation(App.getFrame());
						Dimension frmSize = App.getFrame().getSize();
						stc.setSize(new Dimension(300, 180));
						Point loc = App.getFrame().getLocation();
						stc.setLocation(
								(frmSize.width - stc.getSize().width) / 2 + loc.x,
								(frmSize.height - stc.getSize().height) / 2
										+ loc.y);
						stc.setVisible(true);
						if (!stc.CANCELLED) {
							EventsManager.removeSticker(id);
							CurrentStorage.get().storeEventsManager();
						}
						refresh(CurrentDate.get());
					} else if (d.startsWith("memoranda:addsticker")) {
						StickerDialog dlg = new StickerDialog(App.getFrame());
						Dimension frmSize = App.getFrame().getSize();
						dlg.setSize(new Dimension(300, 380));
						Point loc = App.getFrame().getLocation();
						dlg.setLocation(
								(frmSize.width - dlg.getSize().width) / 2 + loc.x,
								(frmSize.height - dlg.getSize().height) / 2
										+ loc.y);
						dlg.setVisible(true);
						if (!dlg.CANCELLED) {
							String txt = dlg.getStickerText();
							int sP = dlg.getPriority();
							txt = txt.replaceAll("\\n", "<br>");
							txt = "<div style=\"background-color:" + dlg.getStickerColor() + ";font-size:" + dlg.getStickerTextSize() + ";color:" + dlg.getStickerTextColor() + "; \">" + txt + "</div>";
							EventsManager.createSticker(txt, sP);
							CurrentStorage.get().storeEventsManager();
						}
						refresh(CurrentDate.get());
						System.out.println("agregué un sticker");
					} else if (d.startsWith("memoranda:expandsubtasks")) {
						String id = d.split("#")[1];
						gotoTask = id;
						expandedTasks.add(id);
						refresh(CurrentDate.get());
					} else if (d.startsWith("memoranda:closesubtasks")) {
						String id = d.split("#")[1];
						gotoTask = id;
						expandedTasks.remove(id);
						refresh(CurrentDate.get());
					} else if (d.startsWith("memoranda:expandsticker")) {
						String id = d.split("#")[1];
						Element pre_sticker = (Element) EventsManager.getStickers().get(id);
						String sticker = pre_sticker.getValue();
						int first = sticker.indexOf(">");
						int last = sticker.lastIndexOf("<");
						int backcolor = sticker.indexOf("#");
						int fontcolor = sticker.indexOf("#", backcolor + 1);
						int sP = Integer.parseInt(pre_sticker.getAttributeValue("priority"));
						String backGroundColor = sticker.substring(backcolor, sticker.indexOf(';', backcolor));
						String foreGroundColor = sticker.substring(fontcolor, sticker.indexOf(';', fontcolor));
						sticker = "<html>" + sticker.substring(first + 1, last) + "</html>";
						StickerExpand dlg = new StickerExpand(App.getFrame(), sticker, backGroundColor, foreGroundColor, Local.getString("priority") + ": " + Local.getString(priorities[sP]));
						Dimension frmSize = App.getFrame().getSize();
						dlg.setSize(new Dimension(300, 200));
						Point loc = App.getFrame().getLocation();
						dlg.setLocation(
								(frmSize.width - dlg.getSize().width) / 2 + loc.x,
								(frmSize.height - dlg.getSize().height) / 2
										+ loc.y);
						dlg.stickerText.setText(sticker);
						dlg.setVisible(true);
					} else if (d.startsWith("memoranda:editsticker")) {
						String id = d.split("#")[1];
						Element pre_sticker = (Element) EventsManager.getStickers().get(id);
						String sticker = pre_sticker.getValue();
						sticker = sticker.replaceAll("<br>", "\n");
						int first = sticker.indexOf(">");
						int last = sticker.lastIndexOf("<");
						int backcolor = sticker.indexOf("#");
						int fontcolor = sticker.indexOf("#", backcolor + 1);
						int sizeposition = sticker.indexOf("font-size") + 10;
						int size = Integer.parseInt(sticker.substring(sizeposition, sizeposition + 2));
						System.out.println(size + " " + sizeposition);
						int sP = Integer.parseInt(pre_sticker.getAttributeValue("priority"));
						String backGroundColor = sticker.substring(backcolor, sticker.indexOf(';', backcolor));
						String foreGroundColor = sticker.substring(fontcolor, sticker.indexOf(';', fontcolor));
						StickerDialog dlg = new StickerDialog(App.getFrame(), sticker.substring(first + 1, last), backGroundColor, foreGroundColor, sP, size);
						Dimension frmSize = App.getFrame().getSize();
						dlg.setSize(new Dimension(300, 380));
						Point loc = App.getFrame().getLocation();
						dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
								(frmSize.height - dlg.getSize().height) / 2 + loc.y);
						dlg.setVisible(true);
						if (!dlg.CANCELLED) {
							String txt = dlg.getStickerText();
							sP = dlg.getPriority();
							txt = txt.replaceAll("\\n", "<br>");
							txt = "<div style=\"background-color:" + dlg.getStickerColor() + ";font-size:" + dlg.getStickerTextSize() + ";color:" + dlg.getStickerTextColor() + ";\">" + txt + "</div>";
							EventsManager.removeSticker(id);
							EventsManager.createSticker(txt, sP);
							CurrentStorage.get().storeEventsManager();
						}
						refresh(CurrentDate.get());
					} else if (d.startsWith("memoranda:exportstickerst")) {
						/* Missing to add the export sticker in the meantime..*/
						final JFrame parent = new JFrame();
						String name = JOptionPane.showInputDialog(parent, Local.getString("Enter file name to export"), null);
						new ExportSticker(name).export("txt");
						//JOptionPane.showMessageDialog(null,name);
					} else if (d.startsWith("memoranda:exportstickersh")) {
						/* Missing to add the export sticker in the meantime..*/
						final JFrame parent = new JFrame();
						String name = JOptionPane.showInputDialog(parent, Local.getString("Enter file name to export"), null);
						new ExportSticker(name).export("html");
						//JOptionPane.showMessageDialog(null,name);
					} else if (d.startsWith("memoranda:importstickers")) {
						final JFrame parent = new JFrame();
						String name = JOptionPane.showInputDialog(parent, Local.getString("Enter file name to import"), null);
						new ImportSticker(name).import_file();
					}
				}
			}
		});
	}

	/**
	 Method: launchHomePanel
	 Inputs: N/A
	 Returns: N/A
	 Description: Builds GUI default objects for home panel.
	 */
	public void launchHomePanel() {
		//Create login button
		loginB.setFocusable(false);
		loginB.setBorderPainted(false);
		loginB.setToolTipText(Local.getString("Login User"));
		loginB.setRequestFocusEnabled(false);
		loginB.setPreferredSize(new Dimension(72, 24));
		loginB.setMinimumSize(new Dimension(72, 24));
		loginB.setMaximumSize(new Dimension(72, 24));
		loginB.setText("Login");
		loginB.setEnabled(true);
		//Add login button functionality
		loginB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frameLogin = loginFrame();
				frameLogin.setVisible(true);
				loginB.setEnabled(false);
				logoutB.setEnabled(true);
			}
		});

		//Create logout button
		//logoutB.setAction(History.historyForwardAction);
		logoutB.setBorderPainted(false);
		logoutB.setFocusable(false);
		logoutB.setPreferredSize(new Dimension(72, 24));
		logoutB.setRequestFocusEnabled(false);
		logoutB.setToolTipText(Local.getString("Logout User"));
		logoutB.setMinimumSize(new Dimension(72, 24));
		logoutB.setMaximumSize(new Dimension(72, 24));
		logoutB.setText("Logout");
		logoutB.setEnabled(false);
		//Add logout button functionality
		logoutB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frameLogout = logoutFrame();
				frameLogout.setVisible(true);
				loginB.setEnabled(true);
				logoutB.setEnabled(false);
			}
		});


		this.setLayout(borderLayout1);
		scrollPane.getViewport().setBackground(Color.red);
		panel.setBackground(Color.WHITE);
		panel.setSize(getWidth(), getHeight());

		panel.setLayout(layout);
		panel.add(loggedOut);
		this.add(panel);

		scrollPane.getViewport().add(viewer, null);
		toolBar.add(loginB, null);
		toolBar.add(logoutB, null);
		toolBar.addSeparator(new Dimension(8, 24));

		this.add(toolBar, BorderLayout.NORTH);
		panel.revalidate();


		CurrentDate.addDateListener(new DateListener() {
			public void dateChange(CalendarDate d) {
				if (isActive)
					refresh(d);
			}
		});
		CurrentProject.addProjectListener(new ProjectListener() {

			public void projectChange(
					Project prj,
					NoteList nl,
					TaskList tl,
					ResourcesList rl) {
			}

			public void projectWasChanged() {
				if (isActive)
					refresh(CurrentDate.get());
			}});
		EventsScheduler.addListener(new EventNotificationListener() {
			public void eventIsOccured(main.java.memoranda.Event ev) {
				if (isActive)
					refresh(CurrentDate.get());
			}

			public void eventsChanged() {
				if (isActive)
					refresh(CurrentDate.get());
			}
		});
		refresh(CurrentDate.get());

		//        agendaPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
		//        agendaPPMenu.add(ppShowActiveOnlyChB);
		//        PopupListener ppListener = new PopupListener();
		//        viewer.addMouseListener(ppListener);
		//		ppShowActiveOnlyChB.setFont(new java.awt.Font("Dialog", 1, 11));
		//		ppShowActiveOnlyChB.setText(
		//			Local.getString("Show Active only"));
		//		ppShowActiveOnlyChB.addActionListener(new java.awt.event.ActionListener() {
		//			public void actionPerformed(ActionEvent e) {
		//				toggleShowActiveOnly_actionPerformed(e);
		//			}
		//		});		
		//		boolean isShao =
		//			(Context.get("SHOW_ACTIVE_TASKS_ONLY") != null)
		//				&& (Context.get("SHOW_ACTIVE_TASKS_ONLY").equals("true"));
		//		ppShowActiveOnlyChB.setSelected(isShao);
		//		toggleShowActiveOnly_actionPerformed(null);		
	}

	public void refresh(CalendarDate date) {
		//viewer.setText(HomeGenerator.getUserInfo(date,expandedTasks));
		//viewer1.setText(HomeGenerator.getUserNotes(date));
		//viewer2.setText((HomeGenerator.getUserClasses(date)));
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if(gotoTask != null) {
					viewer.scrollToReference(gotoTask);
					scrollPane.setViewportView(viewer);
					//Show notes in note panel
					//Uses html, possibly change in future
					//notePanel.setViewportView(viewer1);
					//Show events in enrolled class panel
					//Uses html, possibly change in future
					//upcomingClassPanel.setViewportView(viewer2);
					Util.debug("Set view port to " + gotoTask);
				}
			}
		});

		Util.debug("Summary updated.");
	}

	public void setActive(boolean isa) {
		isActive = isa;
	}

	//	void toggleShowActiveOnly_actionPerformed(ActionEvent e) {
	//		Context.put(
	//			"SHOW_ACTIVE_TASKS_ONLY",
	//			new Boolean(ppShowActiveOnlyChB.isSelected()));
	//		/*if (taskTable.isShowActiveOnly()) {
	//			// is true, toggle to false
	//			taskTable.setShowActiveOnly(false);
	//			//showActiveOnly.setToolTipText(Local.getString("Show Active Only"));			
	//		}
	//		else {
	//			// is false, toggle to true
	//			taskTable.setShowActiveOnly(true);
	//			showActiveOnly.setToolTipText(Local.getString("Show All"));			
	//		}*/	    
	//		refresh(CurrentDate.get());
	////		parentPanel.updateIndicators();
	//		//taskTable.updateUI();
	//	}

	//    class PopupListener extends MouseAdapter {
	//
	//        public void mouseClicked(MouseEvent e) {
	//        	System.out.println("mouse clicked!");
	////			if ((e.getClickCount() == 2) && (taskTable.getSelectedRow() > -1))
	////				editTaskB_actionPerformed(null);
	//		}
	//
	//		public void mousePressed(MouseEvent e) {
	//        	System.out.println("mouse pressed!");
	//			maybeShowPopup(e);
	//		}
	//
	//		public void mouseReleased(MouseEvent e) {
	//        	System.out.println("mouse released!");
	//			maybeShowPopup(e);
	//		}
	//
	//		private void maybeShowPopup(MouseEvent e) {
	//			if (e.isPopupTrigger()) {
	//				agendaPPMenu.show(e.getComponent(), e.getX(), e.getY());
	//			}
	//		}
	//
	//    }

	/**
	 Method: loginFrame
	 Inputs:
	 Returns: JFrame
	 Description: Creates pop up when login button is pressed.
	 Confirms user login validity. If valid, displays user info.
	 */
	public  JFrame loginFrame() {
		JPanel panelLogin = new JPanel();
		panelLogin.setLayout(null);

		frameLoginPopUp.setTitle("Login Page");
		frameLoginPopUp.pack();
		frameLoginPopUp.setLocationRelativeTo(null);
		frameLoginPopUp.add(panelLogin);
		frameLoginPopUp.setSize(new Dimension(400, 200));

		JLabel label = new JLabel();
		label.setBounds(100, 8, 70, 20);
		panelLogin.add(label);

		JTextField username = new JTextField("Username");
		username.setBounds(100, 27, 200, 30);
		panelLogin.add(username);

		JTextField password = new JTextField("Password");
		password.setBounds(100, 55, 200, 30);
		panelLogin.add(password);

		JButton button = new JButton("Login");
		button.setBounds(100, 110, 200, 30);
		button.setForeground(Color.WHITE);
		button.setBackground(Color.BLACK);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String userName = username.getText();
				String userPassword = password.getText();

				if (users.containsKey(userName)) {
					user = users.get(userName);
					if (user.getPassword().equals(userPassword)) {
						user.login();
						loggedIn = true;

						panel.remove(loggedOut);

						loginHomePanel();

						JOptionPane.showMessageDialog(null, "Login Successful!");
						frameLogin.setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(null, "Password Incorrect");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Username does not exist in system");
				}

			}
		});
		panelLogin.add(button);

		return frameLoginPopUp;
	}

	/**
	 Method: logoutFrame
	 Inputs:
	 Returns: JFrame
	 Description: Creates pop up when logout button is pressed.
	 Confirms user logout. If confirmed, changes info displayed..
	 */
	public JFrame logoutFrame() {
		JPanel panelLogout = new JPanel();
		panelLogout.setLayout(null);

		frameLogoutPopUp.setTitle("Logout Page");
		frameLogoutPopUp.pack();
		frameLogoutPopUp.setLocationRelativeTo(null);
		frameLogoutPopUp.add(panelLogout);
		frameLogoutPopUp.setSize(new Dimension(400, 200));

		JLabel label = new JLabel("Are you sure you want to logout?");
		label.setBounds(100, 60, 200, 20);
		panelLogout.add(label);

		JButton button = new JButton("Confirm");
		button.setBounds(100, 110, 200, 30);
		button.setForeground(Color.WHITE);
		button.setBackground(Color.BLACK);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				user.logout();
				user = null;
				loggedIn = false;

				panel.removeAll();
				innerNotes.removeAll();

				launchHomePanel();

				JOptionPane.showMessageDialog(null, "Logout Successful!");
				frameLogoutPopUp.setVisible(false);
			}
		});
		panelLogout.add(button);

		return frameLogoutPopUp;
	}

	/**
	 Method: loginHomePanel
	 Inputs:
	 Returns:
	 Description: Recreates home panel after user login.
	 */
	public void loginHomePanel() {

		this.setLayout(borderLayout1);
		panel.setBackground(Color.darkGray);
		panel.setSize(getWidth(), getHeight());

		Border bevelBorder = BorderFactory.createBevelBorder(1, Color.black, Color.white);

		panel.setLayout(layout);

		//Create user info panels
		//Create first name panel
		userFirstNameLabel.setText(user.getFirstName());
		userFirstName.add(userFirstNameLabel);
		Border userFirstNameTitleBorder = BorderFactory.createTitledBorder(bevelBorder, "First Name", TitledBorder.CENTER, TitledBorder.TOP);
		userFirstName.setBorder(userFirstNameTitleBorder);

		//Create last name panel
		userLastNameLabel.setText(user.getLastName());
		userLastName.add(userLastNameLabel);
		Border userLastNameTitleBorder = BorderFactory.createTitledBorder(bevelBorder, "Last Name", TitledBorder.CENTER, TitledBorder.TOP);
		userLastName.setBorder(userLastNameTitleBorder);

		//Create user id panel
		userIDLabel.setText(user.getUsername());
		userID.add(userIDLabel);
		Border userIDTitleBorder = BorderFactory.createTitledBorder(bevelBorder, "Username", TitledBorder.CENTER, TitledBorder.TOP);
		userID.setBorder(userIDTitleBorder);

		//Create user rank panel
		userRankLabel.setText("" + user.getRank());
		userRank.add(userRankLabel);
		Border userRankTitleBorder = BorderFactory.createTitledBorder(bevelBorder, "Belt Rank", TitledBorder.CENTER, TitledBorder.TOP);
		userRank.setBorder(userRankTitleBorder);

		//Create join date panel
		userJoinDateLabel.setText("" + user.getJoinDate());
		userJoinDate.add(userJoinDateLabel);
		Border userJoinDateTitleBorder = BorderFactory.createTitledBorder(bevelBorder, "Date Joined", TitledBorder.CENTER, TitledBorder.TOP);
		userJoinDate.setBorder(userJoinDateTitleBorder);

		GridBagConstraints gbc = new GridBagConstraints();

		//Add user info panel to main panel
		Border userTitleBorder = BorderFactory.createTitledBorder(bevelBorder, "User Info", TitledBorder.LEFT, TitledBorder.TOP);
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
		userPanel.setBackground(Color.lightGray);
		userPanel.setBorder(userTitleBorder);
		userPanel.add(userFirstName);
		userPanel.add(userLastName);
		userPanel.add(userID);
		userPanel.add(userRank);
		userPanel.add(userJoinDate);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = .5;
		gbc.weightx = .67;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(userPanel, gbc);

        // TODO: replace this with code from branch working on lessons
        lessons.setText("TODO: replace lessonPlanner code with new lesson code");

//		if (user.getLessons() == null) {
//			lessons.setText("No upcoming classes");
//			upcomingClassPanel = new JScrollPane(lessons);
//		} else {
//			while (user.getLessons() != null) {
//				//lessonList = user.getLessons();
//			}
//			upcomingClassPanel = new JScrollPane(jt);
//		}


		//Create panel to show enrolled classes
		Border classTitleBorder = BorderFactory.createTitledBorder(bevelBorder, "Enrolled Classes", TitledBorder.LEFT, TitledBorder.TOP);
		upcomingClassPanel.setBackground(Color.lightGray);
		upcomingClassPanel.setBorder(classTitleBorder);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.67;
		gbc.weighty = .5;
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(upcomingClassPanel, gbc);

		addNotesPanel.setLayout(new GridBagLayout());

		//Create and add the add note button to note panel
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = .0625;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		addNotesPanel.add(addNote, gbc);

		//Add functionality to add note button
		addNote.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frameAddNote = addNewNote();
				frameAddNote.setVisible(true);
			}
		});

		//Create inner notes panel. Notes will be displayed here.
		innerNotes.setLayout(new GridLayout());
		innerNotes.setSize(getWidth(), getHeight());

		//If user has no notes, show label. If user has notes, display notes as buttons.
		if (user.getNotes().isEmpty()) {
			innerNotes.setLayout(new GridLayout());
			notesLabel = new JLabel("Notes will display here.", SwingConstants.CENTER);
			innerNotes.add(notesLabel);
		} else {
			innerNotes.setLayout(new GridLayout(0, 3));
			innerNotes.remove(notesLabel);
			for (String note : user.getNotes()) {
				System.out.println(note);
				addNoteButton(note);
			}
		}

		notePanel = new JScrollPane(innerNotes);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = .8125;
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 1;
		addNotesPanel.add(notePanel, gbc);

		//Add note panel to main panel
		Border notesTitleBorder = BorderFactory.createTitledBorder(bevelBorder,"Notes", TitledBorder.LEFT, TitledBorder.TOP);
		addNotesPanel.setBackground(Color.lightGray);
		addNotesPanel.setBorder(notesTitleBorder);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.33;
		gbc.gridheight = 2;
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(addNotesPanel, gbc);

		panel.revalidate();
	}

	/**
	 Method: addNewNote
	 Inputs:
	 Returns: JFrame
	 Description: Create pop up to allow user to input a new note. Note will be saved to user file.
	 */
	public JFrame addNewNote() {
		JPanel panelNote = new JPanel();
		panelNote.setLayout(null);

		frameNewNote.setTitle("Add Note");
		frameNewNote.pack();
		frameNewNote.setLocationRelativeTo(null);
		frameNewNote.add(panelNote);
		frameNewNote.setSize(new Dimension(400, 200));

		JTextField note = new JTextField("Type note here.");
		note.setBounds(100, 27, 200, 30);
		panelNote.add(note);

		JButton button = new JButton("Add");
		button.setBounds(100, 110, 200, 30);
		button.setForeground(Color.WHITE);
		button.setBackground(Color.BLACK);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String noteAdded = note.getText();

				innerNotes.removeAll();
				addNotesPanel.removeAll();
				panel.removeAll();

				user.setNotes(noteAdded);

				loginHomePanel();

				JOptionPane.showMessageDialog(null, "Note Added.");
				frameNewNote.setVisible(false);
			}
		});
		panelNote.add(button);

		return frameNewNote;
	}

	/**
	 Method: addNoteButton
	 Inputs: String
	 Returns:
	 Description: Create new note as a button.
	 */
	private void addNoteButton(String note) {
		noteButton = new JButton(note);
		noteButton.setForeground(Color.BLUE);
		noteButton.setBackground(Color.ORANGE);
		innerNotes.add(noteButton);
		noteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frameNote = editNote(note);
				frameNote.setVisible(true);
			}
		});
		innerNotes.revalidate();
	}

	/**
	 Method: editNote
	 Inputs: String
	 Returns: JFrame
	 Description: Create pop up of selected note. Allow user to edit or delete the active note.
	 Edit or delete will be saved to user.
	 */
	public JFrame editNote(String notes) {
		JPanel panelNote = new JPanel();
		panelNote.setLayout(null);

		frameEditNote.setTitle("Edit Note");
		frameEditNote.pack();
		frameEditNote.setLocationRelativeTo(null);
		frameEditNote.add(panelNote);
		frameEditNote.setSize(new Dimension(400, 200));

		JTextField note = new JTextField(notes);
		note.setBounds(40, 27, 300, 50);
		panelNote.add(note);

		JButton buttonSave = new JButton("Save");
		buttonSave.setBounds(100, 110, 100, 30);
		buttonSave.setForeground(Color.WHITE);
		buttonSave.setBackground(Color.BLACK);
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String noteEdited = note.getText();

				innerNotes.removeAll();
				addNotesPanel.removeAll();
				panel.removeAll();

				user.getNotes().remove(notes);
				user.setNotes(noteEdited);
				innerNotes.removeAll();

				loginHomePanel();

				JOptionPane.showMessageDialog(null, "Note Saved.");
				frameEditNote.setVisible(false);
			}
		});

		JButton buttonDelete = new JButton("Delete");
		buttonDelete.setBounds(200, 110, 100, 30);
		buttonDelete.setForeground(Color.WHITE);
		buttonDelete.setBackground(Color.BLACK);
		buttonDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				panel.removeAll();
				innerNotes.removeAll();

				user.getNotes().remove(notes);
				innerNotes.removeAll();
				addNotesPanel.removeAll();
				panel.removeAll();

				loginHomePanel();

				JOptionPane.showMessageDialog(null, "Note Removed.");
				frameEditNote.setVisible(false);
			}
		});

		panelNote.add(buttonSave);
		panelNote.add(buttonDelete);

		return frameEditNote;
	}
}
