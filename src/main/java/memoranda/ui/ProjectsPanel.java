package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.memoranda.CurrentProject;
import main.java.memoranda.NoteList;
import main.java.memoranda.Project;
import main.java.memoranda.ProjectListener;
import main.java.memoranda.ProjectManager;
import main.java.memoranda.ResourcesList;
import main.java.memoranda.TaskList;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.util.*;

/*$Id: ProjectsPanel.java,v 1.14 2005/01/04 09:59:22 pbielen Exp $*/
public class ProjectsPanel extends JPanel implements ExpandablePanel {
	BorderLayout borderLayout1 = new BorderLayout();
	JToolBar topBar = new JToolBar();
	JPanel toolbarPanel = new JPanel();
	BorderLayout borderLayout2 = new BorderLayout();
	JPanel buttonsPanel = new JPanel();
	JButton toggleButton = new JButton();
	FlowLayout flowLayout1 = new FlowLayout();
	Vector expListeners = new Vector();
	boolean expanded = false;
	ImageIcon expIcon =
		new ImageIcon(
			main.java.memoranda.ui.AppFrame.class.getResource(
				"/ui/icons/exp_panel.png"));
	ImageIcon collIcon =
		new ImageIcon(
			main.java.memoranda.ui.AppFrame.class.getResource(
				"/ui/icons/coll_panel.png"));
	JLabel curProjectTitle = new JLabel();
	Component component1;
	JPopupMenu projectsPPMenu = new JPopupMenu();
	JMenuItem ppNewProject = new JMenuItem();
	JMenuItem ppProperties = new JMenuItem();
	JMenuItem ppDeleteProject = new JMenuItem();
	JMenuItem ppOpenProject = new JMenuItem();	
	JCheckBoxMenuItem ppShowActiveOnlyChB = new JCheckBoxMenuItem();
	JButton ppOpenB = new JButton();
	ProjectsTablePanel prjTablePanel = new ProjectsTablePanel();

	public Action newProjectAction =
		new AbstractAction(
			Local.getString("New project") + "...",
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/newproject.png"))) {

		public void actionPerformed(ActionEvent e) {
			ppNewProject_actionPerformed(e);
		}
	};

	
	public ProjectsPanel() {
		try {
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}

	void jbInit() throws Exception {
		component1 = Box.createHorizontalStrut(20);
		this.setLayout(borderLayout1);
		topBar.setBackground(new Color(215, 225, 250));
		topBar.setAlignmentX((float) 0.0);
		topBar.setFloatable(false);
		toolbarPanel.setLayout(borderLayout2);
		toggleButton.setMaximumSize(new Dimension(20, 20));
		toggleButton.setMinimumSize(new Dimension(20, 20));
		toggleButton.setOpaque(false);
		toggleButton.setPreferredSize(new Dimension(20, 20));
		toggleButton.setBorderPainted(false);
		toggleButton.setContentAreaFilled(false);
		toggleButton.setFocusPainted(false);
		toggleButton.setVerticalAlignment(SwingConstants.TOP);
		toggleButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		toggleButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleButton_actionPerformed(null);
			}
		});

		toggleButton.setIcon(expIcon);
		toggleButton.setMargin(new Insets(0, 0, 0, 0));
		buttonsPanel.setMinimumSize(new Dimension(70, 22));
		buttonsPanel.setOpaque(false);
		buttonsPanel.setPreferredSize(new Dimension(80, 22));
		buttonsPanel.setRequestFocusEnabled(false);
		buttonsPanel.setLayout(flowLayout1);
		toolbarPanel.setBackground(SystemColor.textHighlight);
		toolbarPanel.setMinimumSize(new Dimension(91, 22));
		toolbarPanel.setOpaque(false);
		toolbarPanel.setPreferredSize(new Dimension(91, 22));
		flowLayout1.setAlignment(FlowLayout.RIGHT);
		flowLayout1.setHgap(0);
		flowLayout1.setVgap(0);

		curProjectTitle.setFont(new java.awt.Font("Dialog", 1, 11));
		curProjectTitle.setForeground(new Color(64, 70, 128));
		curProjectTitle.setMaximumSize(new Dimension(32767, 22));
		curProjectTitle.setPreferredSize(new Dimension(32767, 22));
		curProjectTitle.setText(CurrentProject.get().getTitle());
		curProjectTitle.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				toggleButton_actionPerformed(null);
			}
		});

		/*
		 * buttonsPanel.add(newProjectB, null); buttonsPanel.add(editProjectB,
		 * null);
		 */
		ppNewProject.setFont(new java.awt.Font("Dialog", 1, 11));
		ppNewProject.setAction(newProjectAction);

		ppProperties.setFont(new java.awt.Font("Dialog", 1, 11));
		ppProperties.setText(Local.getString("Project properties"));
		ppProperties.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ppProperties_actionPerformed(e);
			}
		});
		ppProperties.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/editproject.png")));
		ppProperties.setEnabled(false);
		ppDeleteProject.setFont(new java.awt.Font("Dialog", 1, 11));
		ppDeleteProject.setText(Local.getString("Delete project"));
		ppDeleteProject.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ppDeleteProject_actionPerformed(e);
			}
		});
		ppDeleteProject.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/removeproject.png")));
		ppDeleteProject.setEnabled(false);

		ppOpenProject.setFont(new java.awt.Font("Dialog", 1, 11));

		ppOpenProject.setText(" " + Local.getString("Open project"));

		ppOpenProject.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ppOpenProject_actionPerformed(e);
			}
		});
		ppOpenProject.setEnabled(false);

		ppShowActiveOnlyChB.setFont(new java.awt.Font("Dialog", 1, 11));
		ppShowActiveOnlyChB.setText(
			Local.getString("Show active projects only"));
		ppShowActiveOnlyChB
			.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ppShowActiveOnlyChB_actionPerformed(e);
			}
		});
		boolean isShao =
			(Context.get("SHOW_ACTIVE_PROJECTS_ONLY") != null)
				&& (Context.get("SHOW_ACTIVE_PROJECTS_ONLY").equals("true"));
		ppShowActiveOnlyChB.setSelected(isShao);
		ppShowActiveOnlyChB_actionPerformed(null);

		projectsPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
		ppOpenB.setMaximumSize(new Dimension(34, 20));
		ppOpenB.setMinimumSize(new Dimension(24, 10));
		ppOpenB.setOpaque(false);
		ppOpenB.setPreferredSize(new Dimension(24, 20));
		ppOpenB.setBorderPainted(false);
		ppOpenB.setFocusPainted(false);
		ppOpenB.setMargin(new Insets(0, 0, 0, 0));
		ppOpenB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ppOpenB_actionPerformed(e);
			}
		});
		ppOpenB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/ppopen.png")));
		buttonsPanel.add(ppOpenB, null);
		buttonsPanel.add(component1, null);
		this.add(topBar, BorderLayout.NORTH);
		this.add(prjTablePanel, BorderLayout.CENTER);
		topBar.add(toolbarPanel, null);
		toolbarPanel.add(buttonsPanel, BorderLayout.EAST);
		buttonsPanel.add(toggleButton, null);
		toolbarPanel.add(curProjectTitle, BorderLayout.CENTER);
		projectsPPMenu.add(ppOpenProject);
		projectsPPMenu.addSeparator();
		projectsPPMenu.add(ppNewProject);
		projectsPPMenu.add(ppDeleteProject);
		projectsPPMenu.addSeparator();
		projectsPPMenu.add(ppProperties);
		projectsPPMenu.addSeparator();
		projectsPPMenu.add(ppShowActiveOnlyChB);
		CurrentProject.addProjectListener(new ProjectListener() {
			public void projectChange(
				Project p,
				NoteList nl,
				TaskList tl,
				ResourcesList rl) {
			}
			public void projectWasChanged() {
				curProjectTitle.setText(CurrentProject.get().getTitle());
				prjTablePanel.updateUI();
			}
		});
		CurrentDate.addDateListener(new DateListener() {
			public void dateChange(CalendarDate d) {
				prjTablePanel.updateUI();
			}
		});
		prjTablePanel.projectsTable.addMouseListener(new PopupListener());
		prjTablePanel
			.projectsTable
			.getSelectionModel()
			.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				boolean enabled =
					!prjTablePanel
						.projectsTable
						.getModel()
						.getValueAt(
							prjTablePanel.projectsTable.getSelectedRow(),
							ProjectsTablePanel.PROJECT_ID)
						.toString()
						.equals(CurrentProject.get().getID());
				ppDeleteProject.setEnabled(enabled);
				ppOpenProject.setEnabled(enabled);				
				ppProperties.setEnabled(true);
			}
		});
		prjTablePanel.projectsTable.setToolTipText(
			Local.getString("Double-click to set a current project"));

			// delete projects using the DEL kew
			prjTablePanel.projectsTable.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e){
					if(prjTablePanel.projectsTable.getSelectedRows().length>0 
						&& e.getKeyCode()==KeyEvent.VK_DELETE)
						ppDeleteProject_actionPerformed(null);
				}
				public void	keyReleased(KeyEvent e){}
				public void keyTyped(KeyEvent e){} 
			});
	}

	class PopupListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2)
				ppOpenProject_actionPerformed(null);
		}

		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				projectsPPMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	void toggleButton_actionPerformed(ActionEvent e) {
		for (int i = 0; i < expListeners.size(); i++)
			((ActionListener) expListeners.get(i)).actionPerformed(
				new ActionEvent(this, 0, "Panel expanded (collapsed)"));
		if (expanded) {
			expanded = false;
			toggleButton.setIcon(expIcon);
		} else {
			expanded = true;
			toggleButton.setIcon(collIcon);
		}
	}

	public void AddExpandListener(ActionListener al) {
		expListeners.add(al);
	}

	void ppOpenB_actionPerformed(ActionEvent e) {
		projectsPPMenu.show(
			buttonsPanel,
			(int) (ppOpenB.getLocation().getX() + 24)
				- projectsPPMenu.getWidth(),
			(int) ppOpenB.getLocation().getY() + 24);
	}

	void ppOpenProject_actionPerformed(ActionEvent e) {
		CurrentProject.set(prjTablePanel.getSelectedProject());
		prjTablePanel.updateUI();
		ppDeleteProject.setEnabled(false);
		ppOpenProject.setEnabled(false);
	}

	void ppNewProject_actionPerformed(ActionEvent e) {
		ProjectDialog.newProject();
		prjTablePanel.updateUI();
	}

	void ppDeleteProject_actionPerformed(ActionEvent e) {
		String msg;
		Project prj;
		Vector toremove = new Vector();
		if (prjTablePanel.projectsTable.getSelectedRows().length > 1)
			msg =
				Local.getString("Delete")
					+ " "
					+ prjTablePanel.projectsTable.getSelectedRows().length
					+ " "
					+ Local.getString("projects")
					+ "\n"
					+ Local.getString("Are you sure?");
		else {
			prj = prjTablePanel.getSelectedProject();
			msg =
				Local.getString("Delete project")
					+ " '"
					+ prj.getTitle()
					+ "'.\n"
					+ Local.getString("Are you sure?");
		}

		int n =
			JOptionPane.showConfirmDialog(
				App.getFrame(),
				msg,
				Local.getString("Delete project"),
				JOptionPane.YES_NO_OPTION);
		if (n != JOptionPane.YES_OPTION)
			return;

		for (int i = 0;
			i < prjTablePanel.projectsTable.getSelectedRows().length;
			i++) {
			prj =
				(main.java.memoranda.Project) prjTablePanel
					.projectsTable
					.getModel()
					.getValueAt(
					prjTablePanel.projectsTable.getSelectedRows()[i],
					ProjectsTablePanel.PROJECT);
			toremove.add(prj.getID());
		}
		for (int i = 0; i < toremove.size(); i++) {
			ProjectManager.removeProject((String) toremove.get(i));
		}
		CurrentStorage.get().storeProjectManager();
		prjTablePanel.projectsTable.clearSelection();
		prjTablePanel.updateUI();
		setMenuEnabled(false);
	}

	void ppProperties_actionPerformed(ActionEvent e) {
		Project prj = prjTablePanel.getSelectedProject();
		ProjectDialog dlg =
			new ProjectDialog(null, Local.getString("Project properties"));
		Dimension dlgSize = dlg.getSize();
		Dimension frmSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();
		dlg.setLocation(
			(frmSize.width - dlgSize.width) / 2 + loc.x,
			(frmSize.height - dlgSize.height) / 2 + loc.y);
		dlg.prTitleField.setText(prj.getTitle());
		dlg.startDate.getModel().setValue(
			prj.getStartDate().getCalendar().getTime());
		if (prj.getEndDate() != null) {
			dlg.edButton.setEnabled(true);
            dlg.endDateChB.setForeground(Color.BLACK);

			dlg.endDateChB.setSelected(true);
			dlg.endDate.setEnabled(true);
			dlg.endDate.getModel().setValue(
				prj.getEndDate().getCalendar().getTime());
		}
		/*if (prj.getStatus() == Project.FROZEN)
			dlg.freezeChB.setSelected(true);*/
		dlg.setVisible(true);
		if (dlg.CANCELLED)
			return;
		prj.setTitle(dlg.prTitleField.getText());
		prj.setStartDate(
			new CalendarDate((Date) dlg.startDate.getModel().getValue()));

		if (dlg.endDateChB.isSelected())
			prj.setEndDate(
				new CalendarDate((Date) dlg.endDate.getModel().getValue()));
		else
			prj.setEndDate(null);
		prjTablePanel.updateUI();
		/*
		 * if (dlg.freezeChB.isSelected()) prj.freeze(); else
		 */
	}

	void ppShowActiveOnlyChB_actionPerformed(ActionEvent e) {
		prjTablePanel.setShowActiveOnly(ppShowActiveOnlyChB.isSelected());
		Context.put(
			"SHOW_ACTIVE_PROJECTS_ONLY",
			new Boolean(ppShowActiveOnlyChB.isSelected()));
	}

	void setMenuEnabled(boolean enabled) {
		ppDeleteProject.setEnabled(enabled);
		ppOpenProject.setEnabled(enabled);
		ppProperties.setEnabled(enabled);		
	}

}