/*
 * HomePanel.java
 * @auther Jonathan Blicharz
 * @date 6/24/23
 */
package main.java.memoranda.ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import main.java.memoranda.CurrentProject;
import main.java.memoranda.EventNotificationListener;
import main.java.memoranda.EventsManager;
import main.java.memoranda.EventsScheduler;
import main.java.memoranda.History;
import main.java.memoranda.NoteList;
import main.java.memoranda.Project;
import main.java.memoranda.ProjectListener;
import main.java.memoranda.ProjectManager;
import main.java.memoranda.ResourcesList;
import main.java.memoranda.TaskList;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.util.HomeGenerator;
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
	JButton export = new JButton();
	JEditorPane viewer = new JEditorPane("text/html", "");
	JEditorPane viewer1 = new JEditorPane("text/html", "");
	JEditorPane viewer2 = new JEditorPane("text/html", "");
	String[] priorities = {"Very High","High","Medium","Low","Very Low"};
	JScrollPane scrollPane = new JScrollPane();
	JPanel panel = new JPanel();
	JPanel addNotesPanel = new JPanel();

	JScrollPane notePanel = new JScrollPane();
	JScrollPane upcomingClassPanel = new JScrollPane();

	DailyItemsPanel parentPanel = null;

	//	JPopupMenu agendaPPMenu = new JPopupMenu();
	//	JCheckBoxMenuItem ppShowActiveOnlyChB = new JCheckBoxMenuItem();

	Collection expandedTasks;
	String gotoTask = null;

	boolean isActive = true;

	public HomePanel(DailyItemsPanel _parentPanel) {
		try {
			parentPanel = _parentPanel;
			jbInit();
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
                        stc.setSize(new Dimension(300,180));
                        Point loc = App.getFrame().getLocation();
                        stc.setLocation(
                                (frmSize.width - stc.getSize().width) / 2 + loc.x,
                                (frmSize.height - stc.getSize().height) / 2
                                       + loc.y);
                        stc.setVisible(true);
                        if (!stc.CANCELLED) {
                        EventsManager.removeSticker(id);
                        CurrentStorage.get().storeEventsManager();}
                        refresh(CurrentDate.get());
					} else if (d.startsWith("memoranda:addsticker")) {
						StickerDialog dlg = new StickerDialog(App.getFrame());
						Dimension frmSize = App.getFrame().getSize();
						dlg.setSize(new Dimension(300,380));
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
                            txt = "<div style=\"background-color:"+dlg.getStickerColor()+";font-size:"+dlg.getStickerTextSize()+";color:"+dlg.getStickerTextColor()+"; \">"+txt+"</div>";
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
						Element pre_sticker=(Element) EventsManager.getStickers().get(id);
						String sticker = pre_sticker.getValue();
						int first=sticker.indexOf(">");
						int last=sticker.lastIndexOf("<");
						int backcolor=sticker.indexOf("#");
						int fontcolor=sticker.indexOf("#", backcolor+1);
						int sP=Integer.parseInt(pre_sticker.getAttributeValue("priority"));
						String backGroundColor=sticker.substring(backcolor, sticker.indexOf(';',backcolor));
						String foreGroundColor=sticker.substring(fontcolor, sticker.indexOf(';',fontcolor));
						sticker="<html>"+sticker.substring(first+1, last)+"</html>";
						StickerExpand dlg = new StickerExpand(App.getFrame(),sticker,backGroundColor,foreGroundColor,Local.getString("priority")+": "+Local.getString(priorities[sP]));
						Dimension frmSize = App.getFrame().getSize();
						dlg.setSize(new Dimension(300,200));
						Point loc = App.getFrame().getLocation();
						dlg.setLocation(
								(frmSize.width - dlg.getSize().width) / 2 + loc.x,
								(frmSize.height - dlg.getSize().height) / 2
								+ loc.y);
						dlg.stickerText.setText(sticker);
						dlg.setVisible(true);
					}else if (d.startsWith("memoranda:editsticker")) {
						String id = d.split("#")[1];
						Element pre_sticker=(Element) EventsManager.getStickers().get(id);
						String sticker = pre_sticker.getValue();
						sticker=sticker.replaceAll("<br>","\n");
						int first=sticker.indexOf(">");
						int last=sticker.lastIndexOf("<");
						int backcolor=sticker.indexOf("#");
						int fontcolor=sticker.indexOf("#", backcolor+1);
						int sizeposition=sticker.indexOf("font-size")+10;
						int size=Integer.parseInt(sticker.substring(sizeposition,sizeposition+2));
						System.out.println(size+" "+sizeposition);
						int sP=Integer.parseInt(pre_sticker.getAttributeValue("priority"));
						String backGroundColor=sticker.substring(backcolor, sticker.indexOf(';',backcolor));
						String foreGroundColor=sticker.substring(fontcolor, sticker.indexOf(';',fontcolor));
						StickerDialog dlg = new StickerDialog(App.getFrame(), sticker.substring(first+1, last), backGroundColor, foreGroundColor, sP, size);
						Dimension frmSize = App.getFrame().getSize();
						dlg.setSize(new Dimension(300,380));
						Point loc = App.getFrame().getLocation();
						dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
							 		(frmSize.height - dlg.getSize().height) / 2 + loc.y);
						dlg.setVisible(true);
						if (!dlg.CANCELLED) {
							String txt = dlg.getStickerText();
							sP = dlg.getPriority();
							txt = txt.replaceAll("\\n", "<br>");
							txt = "<div style=\"background-color:"+dlg.getStickerColor()+";font-size:"+dlg.getStickerTextSize()+";color:"+dlg.getStickerTextColor()+";\">"+txt+"</div>";
							EventsManager.removeSticker(id);
							EventsManager.createSticker(txt, sP);
							CurrentStorage.get().storeEventsManager();
						 }
						 refresh(CurrentDate.get());
					}else if (d.startsWith("memoranda:exportstickerst")) {
						 /* Missing to add the export sticker in the meantime..*/
						 final JFrame parent = new JFrame();
						 String name = JOptionPane.showInputDialog(parent,Local.getString("Enter file name to export"),null);
						 new ExportSticker(name).export("txt");
						 //JOptionPane.showMessageDialog(null,name);
					}else if (d.startsWith("memoranda:exportstickersh")) {
						 /* Missing to add the export sticker in the meantime..*/
						 final JFrame parent = new JFrame();
						 String name = JOptionPane.showInputDialog(parent,Local.getString("Enter file name to export"),null);
						 new ExportSticker(name).export("html");
						 //JOptionPane.showMessageDialog(null,name);
					}else if (d.startsWith("memoranda:importstickers")) {
						final JFrame parent = new JFrame();
						String name = JOptionPane.showInputDialog(parent,Local.getString("Enter file name to import"),null);
						new ImportSticker(name).import_file();
					}
				}
			}
		});

		//Create login button
		//loginB.setAction(History.historyBackAction);
		loginB.setFocusable(false);
		loginB.setBorderPainted(false);
		loginB.setToolTipText(Local.getString("Login User"));
		loginB.setRequestFocusEnabled(false);
		loginB.setPreferredSize(new Dimension(72, 24));
		loginB.setMinimumSize(new Dimension(72, 24));
		loginB.setMaximumSize(new Dimension(72, 24));
		loginB.setText("Login");

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

		

		this.setLayout(borderLayout1);
		scrollPane.getViewport().setBackground(Color.red);
		panel.setBackground(Color.darkGray);
		panel.setSize(getWidth(),getHeight());
		GridBagLayout layout = new GridBagLayout();

		Border bevelBorder = BorderFactory.createBevelBorder(1, Color.black, Color.white);

		panel.setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();


		//Create user info panels
		//Create first name panel
		JLabel userFirstNameLabel = new JLabel("First Name", 0);
		JPanel userFirstName = new JPanel();
		userFirstName.add(userFirstNameLabel);
		Border userFirstNameTitleBorder = BorderFactory.createTitledBorder(bevelBorder,"First Name", TitledBorder.CENTER, TitledBorder.TOP);
		userFirstName.setBorder(userFirstNameTitleBorder);

		//Create last name panel
		JLabel userLastNameLabel = new JLabel("Last Name", 0);
		JPanel userLastName = new JPanel();
		userLastName.add(userLastNameLabel);
		Border userLastNameTitleBorder = BorderFactory.createTitledBorder(bevelBorder,"Last Name", TitledBorder.CENTER, TitledBorder.TOP);
		userLastName.setBorder(userLastNameTitleBorder);

		//Create user id panel
		JLabel userIDLabel = new JLabel("ID", 0);
		JPanel userID = new JPanel();
		userID.add(userIDLabel);
		Border userIDTitleBorder = BorderFactory.createTitledBorder(bevelBorder,"ID", TitledBorder.CENTER, TitledBorder.TOP);
		userID.setBorder(userIDTitleBorder);

		//Create user rank panel
		JLabel userRankLabel = new JLabel("Belt Rank", 0);
		JPanel userRank = new JPanel();
		userRank.add(userRankLabel);
		Border userRankTitleBorder = BorderFactory.createTitledBorder(bevelBorder,"Belt Rank", TitledBorder.CENTER, TitledBorder.TOP);
		userRank.setBorder(userRankTitleBorder);

		//Create join date panel
		JLabel userJoinDateLabel = new JLabel("Date Joined", 0);
		JPanel userJoinDate = new JPanel();
		userJoinDate.add(userJoinDateLabel);
		Border userJoinDateTitleBorder = BorderFactory.createTitledBorder(bevelBorder,"Date Joined", TitledBorder.CENTER, TitledBorder.TOP);
		userJoinDate.setBorder(userJoinDateTitleBorder);

		//Add user info panel to main panel
		Border userTitleBorder = BorderFactory.createTitledBorder(bevelBorder,"User Info", TitledBorder.LEFT, TitledBorder.TOP);
		JPanel userPanel = new JPanel();
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
		gbc.weightx= .67;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(userPanel, gbc);

		//Create panel to show enrolled classes
		Border classTitleBorder = BorderFactory.createTitledBorder(bevelBorder,"Enrolled Classes", TitledBorder.LEFT, TitledBorder.TOP);
		upcomingClassPanel.setBackground(Color.lightGray);
		upcomingClassPanel.setBorder(classTitleBorder);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.67;
		gbc.weighty = .5;
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(upcomingClassPanel, gbc);

		addNotesPanel.setLayout(new GridBagLayout());

		//Create buttons for notes
		//Create and add import button to note panel
		JButton importNote = new JButton();
		importNote.setText("Import Note");
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = .0625;
		gbc.gridx = 0;
		gbc.gridy = 0;
		addNotesPanel.add(importNote, gbc);

		//Create and add export button txt to note panel
		JButton exportNoteTxt = new JButton();
		exportNoteTxt.setText("Export Notes as .txt");
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = .0625;
		gbc.gridx = 1;
		gbc.gridy = 0;
		addNotesPanel.add(exportNoteTxt, gbc);

		//Create and add export button html to note panel
		JButton exportNoteHtml = new JButton();
		exportNoteHtml.setText("Export Notes as .html");
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = .0625;
		gbc.gridx = 2;
		gbc.gridy = 0;
		addNotesPanel.add(exportNoteHtml, gbc);

		//Create and add the add note button to note panel
		JButton addNote = new JButton();
		addNote.setText("Add Note");
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = .0625;
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 1;
		addNotesPanel.add(addNote, gbc);

		//Create and add edit note button to note panel
		JButton editNote = new JButton();
		editNote.setText("Edit");
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = .0625;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		addNotesPanel.add(editNote, gbc);

		//Create and add open note button to note panel
		JButton openNote = new JButton();
		openNote.setText("Open In a New Window");
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = .0625;
		gbc.gridx = 1;
		gbc.gridy = 2;
		addNotesPanel.add(openNote, gbc);

		//Create and add remove note button to note panel
		JButton removeNote = new JButton();
		removeNote.setText("Remove Note");
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = .0625;
		gbc.gridwidth = 3;
		gbc.gridx = 2;
		gbc.gridy = 2;
		addNotesPanel.add(removeNote, gbc);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = .8125;
		gbc.gridx = 0;
		gbc.gridy = 3;
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
		this.add(panel);

		scrollPane.getViewport().add(viewer, null);
		notePanel.getViewport().add(viewer1, null);
		upcomingClassPanel.getViewport().add(viewer2, null);
		//this.add(userPanel, BorderLayout.CENTER);
		//this.add(upcomingClassPanel, BorderLayout.EAST);
		//this.add(addNotesPanel, BorderLayout.SOUTH);
		toolBar.add(loginB, null);
		toolBar.add(logoutB, null);
		toolBar.addSeparator(new Dimension(8, 24));

		this.add(toolBar, BorderLayout.NORTH);

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
		viewer.setText(HomeGenerator.getUserInfo(date,expandedTasks));
		viewer1.setText(HomeGenerator.getUserNotes(date));
		viewer2.setText((HomeGenerator.getUserClasses(date)));
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if(gotoTask != null) {
					viewer.scrollToReference(gotoTask);
					scrollPane.setViewportView(viewer);
					//Show notes in note panel
					//Uses html, possibly change in future
					notePanel.setViewportView(viewer1);
					//Show events in enrolled class panel
					//Uses html, possibly change in future
					upcomingClassPanel.setViewportView(viewer2);
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
}
