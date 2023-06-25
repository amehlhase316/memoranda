package main.java.memoranda.ui;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.memoranda.CurrentProject;
import main.java.memoranda.Resource;
import main.java.memoranda.util.AppList;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.MimeType;
import main.java.memoranda.util.MimeTypesList;
import main.java.memoranda.util.Util;

import java.io.*;

/*$Id: ResourcesPanel.java,v 1.13 2007/03/20 08:22:41 alexeya Exp $*/
public class AdminPanel extends JPanel {
//    BorderLayout borderLayout1 = new BorderLayout();
//    JToolBar toolBar = new JToolBar();
//    JButton newResB = new JButton();
//    ResourcesTable resourcesTable = new ResourcesTable();
//    JButton removeResB = new JButton();
//    JScrollPane scrollPane = new JScrollPane();
//    JButton refreshB = new JButton();
//  JPopupMenu resPPMenu = new JPopupMenu();
//  JMenuItem ppRun = new JMenuItem();
//  JMenuItem ppRemoveRes = new JMenuItem();
//  JMenuItem ppNewRes = new JMenuItem();
//  JMenuItem ppRefresh = new JMenuItem();

    public AdminPanel() {
        try {
            jbInit();
        }
        catch (Exception ex) {
           new ExceptionDialog(ex);
        }
    }

    void jbInit() throws Exception{
                //Setting Layout Manager to BoxLayout. Contents will be put into different panels for
                //different sections of the UI using FlowLayout, which will organize the contents in a
                //horizontal fashion. These panels will then be stacked on top of one another using
                //BoxLayout.
                //setLayout(new GridLayout(5,1,0,0));
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                //Creating different sections of Admin Panel UI.

                //Creating first section of Admin Panel UI used to create a new user.

                //Instruction text informing user how to create new user
                JLabel createUserInstruction = new JLabel("To create a new user, enter in their username and password, select a user type and click the 'Create new user' button.");
                add(createUserInstruction);

                //Creating new panel for the rest of the create user UI. A FlowLayout is used, which will
                //organize the content of the panel in a horizontal fashion.
                //JPanel createUserPanel = new JPanel(new FlowLayout());
                JPanel createUserPanel = new JPanel();
                createUserPanel.setLayout(new BoxLayout(createUserPanel, BoxLayout.X_AXIS));
                createUserPanel.add(Box.createHorizontalStrut(400));
                //Label for username textbox
                JLabel userNameLabel = new JLabel("Username: ");
                createUserPanel.add(userNameLabel);
                //Textbox to enter username for new user with space for 15 characters
                JTextField userName = new JTextField(15);
                userName.setMaximumSize(userName.getPreferredSize());
                createUserPanel.add(userName);
                //Label for password textbox
                JLabel userPasswordLabel = new JLabel("Password: ");
                createUserPanel.add(userPasswordLabel);
                //Textbox to enter user password with space for 15 characters
                JTextField userPassword = new JTextField(15);
        userPassword.setMaximumSize(userPassword.getPreferredSize());

        createUserPanel.add(userPassword);
                //Creating a list of user types that can be selected from
            JLabel typeLabel = new JLabel("User type: ");
            createUserPanel.add(typeLabel);
                String userTypes[] = {"Student", "Trainer", "Admin"};
                JList userType = new JList(userTypes);
                createUserPanel.add(userType);
                //Button to create user with the inputted information
                //JPanel userButtonsPanel = new JPanel();
                //userButtonsPanel.setLayout(new BoxLayout(userButtonsPanel, BoxLayout.Y_AXIS));
                //userButtonsPanel.add(createUser);
                //createUserPanel.add(userButtonsPanel);
        JButton createUser = new JButton("Create new user");
createUserPanel.add(createUser);
        //The create user panel is added to the AdminPanel using BoxLayout
                //so that it will sit under the instructions
                createUserPanel.setMaximumSize(createUserPanel.getPreferredSize());
                add(createUserPanel);

        //Instruction text informing user how to edit a user's info
        JLabel editUserInstruction1 = new JLabel("To edit an existing user's info, enter their username and press the 'Find user' button.");
        add(editUserInstruction1);
        JLabel editUserInstruction2 = new JLabel("Their details will autopopulate in the textboxes. Edit any fields then press the 'Save user' button to save changes.");
        add(editUserInstruction2);

        //Creating new panel for the rest of the create user UI. A FlowLayout is used, which will
        //organize the content of the panel in a horizontal fashion.
        //JPanel createUserPanel = new JPanel(new FlowLayout());
        JPanel editUserPanel = new JPanel(new FlowLayout());
        //createUserPanel.setLayout(new BoxLayout(createUserPanel, BoxLayout.X_AXIS));
        //createUserPanel.add(Box.createHorizontalStrut(400));
        //Label for username textbox
        JLabel userNameLabel = new JLabel("Username: ");
        createUserPanel.add(userNameLabel);
        //Textbox to enter username for new user with space for 15 characters
        JTextField userName = new JTextField(15);
        userName.setMaximumSize(userName.getPreferredSize());
        createUserPanel.add(userName);
        //Label for password textbox
        JLabel userPasswordLabel = new JLabel("Password: ");
        createUserPanel.add(userPasswordLabel);
        //Textbox to enter user password with space for 15 characters
        JTextField userPassword = new JTextField(15);
        userPassword.setMaximumSize(userPassword.getPreferredSize());

        createUserPanel.add(userPassword);
        //Creating a list of user types that can be selected from
        JLabel typeLabel = new JLabel("User type: ");
        createUserPanel.add(typeLabel);
        String userTypes[] = {"Student", "Trainer", "Admin"};
        JList userType = new JList(userTypes);
        createUserPanel.add(userType);
        //Button to create user with the inputted information
        //JPanel userButtonsPanel = new JPanel();
        //userButtonsPanel.setLayout(new BoxLayout(userButtonsPanel, BoxLayout.Y_AXIS));
        //userButtonsPanel.add(createUser);
        //createUserPanel.add(userButtonsPanel);
        JButton createUser = new JButton("Create new user");
        createUserPanel.add(createUser);
        //The create user panel is added to the AdminPanel using BoxLayout
        //so that it will sit under the instructions
        createUserPanel.setMaximumSize(createUserPanel.getPreferredSize());
        add(createUserPanel);

        //Creating second section of Admin Panel UI that allows user to create a class

                //JLabel with instructions on how to create a class
                JLabel createClassInstructions = new JLabel("To create a new class, enter in the class type, room and date and select either private or public, then press the 'Create new class' button.");
                JLabel deleteClassInstructions = new JLabel("To delete a class, enter the class type and date, then press the 'Delete Class' button.");
                add(createClassInstructions);
                add(deleteClassInstructions);


        //Creating new panel for the rest of the create/delete class UI. A FlowLayout is used, which will
                //organize the content of the panel in a horizontal fashion.
                JPanel createClassPanel = new JPanel(new FlowLayout());

        //Label for class type
                JLabel classTypeLabel = new JLabel("Class type: ");
                createClassPanel.add(classTypeLabel);
                //Textbox to enter class type into
                JTextField classType = new JTextField(15);
                createClassPanel.add(classType);
                //Label for room number
                JLabel classRoomLabel = new JLabel("Room: ");
                createClassPanel.add(classRoomLabel);
                //List to choose room number from
                String roomNumber[] = {"1", "2", "3", "4"};
                JList roomNumberList = new JList(roomNumber);
                createClassPanel.add(roomNumberList);
                //Label for date
                JLabel classDateLabel = new JLabel("Date: ");
                createClassPanel.add(classDateLabel);
                //Textbox for date
                JTextField classDate = new JTextField(15);
                createClassPanel.add(classDate);
                //List of public or private class for user to select
                String classAvailabilities[] = {"Public", "Private"};
                JList classAvailability = new JList(classAvailabilities);
                createClassPanel.add(classAvailability);

                //Creating panel with create and delete class buttons laid on top of each other
                JPanel classButtonsPanel = new JPanel();
                classButtonsPanel.setLayout(new BoxLayout(classButtonsPanel, BoxLayout.Y_AXIS));
                //Button to create class
                JButton createClass = new JButton("Create new class");
                createClassPanel.add(createClass);
                //classButtonsPanel.add(createClass);

                //Button to delete class
                List classes = new List();
                JList classList = new JList();
                JButton deleteClass = new JButton("Delete class");
                //classButtonsPanel.add(deleteClass);

                //createClassPanel.add(classButtonsPanel);
                createClassPanel.add(deleteClass);
                add(createClassPanel);
            }
//    void jbInit() throws Exception {
//        toolBar.setFloatable(false);
//        this.setLayout(borderLayout1);
//        newResB.setIcon(
//            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/addresource.png")));
//        newResB.setEnabled(true);
//        newResB.setMaximumSize(new Dimension(24, 24));
//        newResB.setMinimumSize(new Dimension(24, 24));
//        newResB.setToolTipText(Local.getString("New resource"));
//        newResB.setRequestFocusEnabled(false);
//        newResB.setPreferredSize(new Dimension(24, 24));
//        newResB.setFocusable(false);
//        newResB.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                newResB_actionPerformed(e);
//            }
//        });
//        newResB.setBorderPainted(false);
//        resourcesTable.setMaximumSize(new Dimension(32767, 32767));
//        resourcesTable.setRowHeight(24);
//        removeResB.setBorderPainted(false);
//        removeResB.setFocusable(false);
//        removeResB.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                removeResB_actionPerformed(e);
//            }
//        });
//        removeResB.setPreferredSize(new Dimension(24, 24));
//        removeResB.setRequestFocusEnabled(false);
//        removeResB.setToolTipText(Local.getString("Remove resource"));
//        removeResB.setMinimumSize(new Dimension(24, 24));
//        removeResB.setMaximumSize(new Dimension(24, 24));
//        removeResB.setIcon(
//            new ImageIcon(
//                main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/removeresource.png")));
//        removeResB.setEnabled(false);
//        scrollPane.getViewport().setBackground(Color.white);
//        toolBar.addSeparator(new Dimension(8, 24));
//        toolBar.addSeparator(new Dimension(8, 24));
//
//
//        PopupListener ppListener = new PopupListener();
//        scrollPane.addMouseListener(ppListener);
//        resourcesTable.addMouseListener(ppListener);
//
//        resourcesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            public void valueChanged(ListSelectionEvent e) {
//                boolean enbl = (resourcesTable.getRowCount() > 0) && (resourcesTable.getSelectedRow() > -1);
//
//                removeResB.setEnabled(enbl); ppRemoveRes.setEnabled(enbl);
//                ppRun.setEnabled(enbl);
//            }
//        });
//        refreshB.setBorderPainted(false);
//        refreshB.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                refreshB_actionPerformed(e);
//            }
//        });
//        refreshB.setFocusable(false);
//        refreshB.setPreferredSize(new Dimension(24, 24));
//        refreshB.setRequestFocusEnabled(false);
//        refreshB.setToolTipText(Local.getString("Refresh"));
//        refreshB.setMinimumSize(new Dimension(24, 24));
//        refreshB.setMaximumSize(new Dimension(24, 24));
//        refreshB.setEnabled(true);
//        refreshB.setIcon(
//            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/refreshres.png")));
//        resPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
//    ppRun.setFont(new java.awt.Font("Dialog", 1, 11));
//    ppRun.setText(Local.getString("Open resource")+"...");
//    ppRun.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                ppRun_actionPerformed(e);
//            }
//        });
//    ppRun.setEnabled(false);
//
//    ppRemoveRes.setFont(new java.awt.Font("Dialog", 1, 11));
//    ppRemoveRes.setText(Local.getString("Remove resource"));
//    ppRemoveRes.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                ppRemoveRes_actionPerformed(e);
//            }
//        });
//    ppRemoveRes.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/removeresource.png")));
//    ppRemoveRes.setEnabled(false);
//    ppNewRes.setFont(new java.awt.Font("Dialog", 1, 11));
//    ppNewRes.setText(Local.getString("New resource")+"...");
//    ppNewRes.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                ppNewRes_actionPerformed(e);
//            }
//        });
//    ppNewRes.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/addresource.png")));
//
//    ppRefresh.setFont(new java.awt.Font("Dialog", 1, 11));
//    ppRefresh.setText(Local.getString("Refresh"));
//    ppRefresh.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        ppRefresh_actionPerformed(e);
//      }
//    });
//    ppRefresh.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/refreshres.png")));
//
//    toolBar.add(newResB, null);
//        toolBar.add(removeResB, null);
//        toolBar.addSeparator();
//        toolBar.add(refreshB, null);
//        this.add(scrollPane, BorderLayout.CENTER);
//        scrollPane.getViewport().add(resourcesTable, null);
//        this.add(toolBar, BorderLayout.NORTH);
//    resPPMenu.add(ppRun);
//    resPPMenu.addSeparator();
//    resPPMenu.add(ppNewRes);
//    resPPMenu.add(ppRemoveRes);
//    resPPMenu.addSeparator();
//    resPPMenu.add(ppRefresh);
//
//		// remove resources using the DEL key
//		resourcesTable.addKeyListener(new KeyListener() {
//			public void keyPressed(KeyEvent e){
//				if(resourcesTable.getSelectedRows().length>0
//					&& e.getKeyCode()==KeyEvent.VK_DELETE)
//					ppRemoveRes_actionPerformed(null);
//			}
//			public void	keyReleased(KeyEvent e){}
//			public void keyTyped(KeyEvent e){}
//		});
//    }

//    void newResB_actionPerformed(ActionEvent e) {
//        AddResourceDialog dlg = new AddResourceDialog(App.getFrame(), Local.getString("New resource"));
//        Dimension frmSize = App.getFrame().getSize();
//        Point loc = App.getFrame().getLocation();
//        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
//        dlg.setVisible(true);
//        if (dlg.CANCELLED)
//            return;
//        if (dlg.localFileRB.isSelected()) {
//            String fpath = dlg.pathField.getText();
//            MimeType mt = MimeTypesList.getMimeTypeForFile(fpath);
//            if (mt.getMimeTypeId().equals("__UNKNOWN")) {
//                mt = addResourceType(fpath);
//                if (mt == null)
//                    return;
//            }
//            if (!checkApp(mt))
//                return;
//            // if file if projectFile, than copy the file and change url.
//            if (dlg.projectFileCB.isSelected()) {
//            	fpath = copyFileToProjectDir(fpath);
//            	CurrentProject.getResourcesList().addResource(fpath, false, true);
//            }
//            else
//            	CurrentProject.getResourcesList().addResource(fpath);
//
//            resourcesTable.tableChanged();
//        }
//        else {
//            if (!Util.checkBrowser())
//                return;
//            CurrentProject.getResourcesList().addResource(dlg.urlField.getText(), true, false);
//            resourcesTable.tableChanged();
//        }
//    }

//    void removeResB_actionPerformed(ActionEvent e) {
//        int[] toRemove = resourcesTable.getSelectedRows();
//        String msg = "";
//        if (toRemove.length == 1)
//            msg =
//                Local.getString("Remove the shortcut to resource")
//                    + "\n'"
//                    + resourcesTable.getModel().getValueAt(toRemove[0], 0)
//                    + "'";
//
//        else
//            msg = Local.getString("Remove") + " " + toRemove.length + " " + Local.getString("shortcuts");
//        msg +=
//            "\n"
//            + Local.getString("Are you sure?");
//        int n =
//            JOptionPane.showConfirmDialog(
//                App.getFrame(),
//                msg,
//                Local.getString("Remove resource"),
//                JOptionPane.YES_NO_OPTION);
//        if (n != JOptionPane.YES_OPTION)
//            return;
//        for (int i = 0; i < toRemove.length; i++) {
//        		CurrentProject.getResourcesList().removeResource(
//                        ((Resource) resourcesTable.getModel().getValueAt(toRemove[i], ResourcesTable._RESOURCE)).getPath());
//        }
//        resourcesTable.tableChanged();
//    }

//    MimeType addResourceType(String fpath) {
//        ResourceTypeDialog dlg = new ResourceTypeDialog(App.getFrame(), Local.getString("Resource type"));
//        Dimension dlgSize = new Dimension(420, 300);
//        dlg.setSize(dlgSize);
//        Dimension frmSize = App.getFrame().getSize();
//        Point loc = App.getFrame().getLocation();
//        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
//        dlg.ext = MimeTypesList.getExtension(fpath);
//        dlg.setVisible(true);
//        if (dlg.CANCELLED)
//            return null;
//        int ix = dlg.getTypesList().getSelectedIndex();
//        MimeType mt = (MimeType) MimeTypesList.getAllMimeTypes().toArray()[ix];
//        mt.addExtension(MimeTypesList.getExtension(fpath));
//        CurrentStorage.get().storeMimeTypesList();
//        return mt;
//    }

//    boolean checkApp(MimeType mt) {
//        String appId = mt.getAppId();
//        AppList appList = MimeTypesList.getAppList();
//        File d;
//        if (appId == null) {
//            appId = Util.generateId();
//            d = new File("/");
//        }
//        else {
//            File exe = new File(appList.getFindPath(appId) + "/" + appList.getExec(appId));
//            if (exe.isFile())
//                return true;
//            d = new File(exe.getParent());
//            while (!d.exists())
//                d = new File(d.getParent());
//        }
//        SetAppDialog dlg =
//            new SetAppDialog(
//                App.getFrame(),
//                Local.getString(Local.getString("Select the application to open files of type")+" '" + mt.getLabel() + "'"));
//        Dimension dlgSize = new Dimension(420, 300);
//        dlg.setSize(dlgSize);
//        Dimension frmSize = App.getFrame().getSize();
//        Point loc = App.getFrame().getLocation();
//        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
//        dlg.setDirectory(d);
//        dlg.appPanel.argumentsField.setText("$1");
//        dlg.setVisible(true);
//        if (dlg.CANCELLED)
//            return false;
//        File f = new File(dlg.appPanel.applicationField.getText());
//
//        appList.addOrReplaceApp(
//            appId,
//            f.getParent().replace('\\', '/'),
//            f.getName().replace('\\', '/'),
//            dlg.appPanel.argumentsField.getText());
//        mt.setApp(appId);
//        /*appList.setFindPath(appId, chooser.getSelectedFile().getParent().replace('\\','/'));
//        appList.setExec(appId, chooser.getSelectedFile().getName().replace('\\','/'));*/
//        CurrentStorage.get().storeMimeTypesList();
//        return true;
//    }


//    void runApp(String fpath) {
//        MimeType mt = MimeTypesList.getMimeTypeForFile(fpath);
//        if (mt.getMimeTypeId().equals("__UNKNOWN")) {
//            mt = addResourceType(fpath);
//            if (mt == null)
//                return;
//        }
//        if (!checkApp(mt))
//            return;
//        String[] command = MimeTypesList.getAppList().getCommand(mt.getAppId(), fpath);
//        if (command == null)
//            return;
//        /*DEBUG*/
//        System.out.println("Run: " + command[0]);
//        try {
//            Runtime.getRuntime().exec(command);
//        }
//        catch (Exception ex) {
//            new ExceptionDialog(ex, "Failed to run an external application <br><code>"
//                    +command[0]+"</code>", "Check the application path and command line parameters for this resource type " +
//                    		"(File-&gt;Preferences-&gt;Resource types).");
//        }
//    }

//    void runBrowser(String url) {
//        Util.runBrowser(url);
//    }

//    class PopupListener extends MouseAdapter {
//
//        public void mouseClicked(MouseEvent e) {
//            if ((e.getClickCount() == 2) && (resourcesTable.getSelectedRow() > -1)) {
//                String path = (String) resourcesTable.getValueAt(resourcesTable.getSelectedRow(), 3);
//                if (path.length() >0)
//                    runApp(path);
//                else
//                    runBrowser((String) resourcesTable.getValueAt(resourcesTable.getSelectedRow(), 0));
//            }
//            //editTaskB_actionPerformed(null);
//        }
//
//                public void mousePressed(MouseEvent e) {
//                    maybeShowPopup(e);
//                }
//
//                public void mouseReleased(MouseEvent e) {
//                    maybeShowPopup(e);
//                }
//
//                private void maybeShowPopup(MouseEvent e) {
//                    if (e.isPopupTrigger()) {
//                        resPPMenu.show(e.getComponent(), e.getX(), e.getY());
//                    }
//                }
//
//    }
//    void refreshB_actionPerformed(ActionEvent e) {
//        resourcesTable.tableChanged();
//    }
//
//  void ppRun_actionPerformed(ActionEvent e) {
//    String path = (String) resourcesTable.getValueAt(resourcesTable.getSelectedRow(), 3);
//                if (path.length() >0)
//                    runApp(path);
//                else
//                    runBrowser((String) resourcesTable.getValueAt(resourcesTable.getSelectedRow(), 0));
//  }
//  void ppRemoveRes_actionPerformed(ActionEvent e) {
//    removeResB_actionPerformed(e);
//  }
//  void ppNewRes_actionPerformed(ActionEvent e) {
//    newResB_actionPerformed(e);
//  }
//
//  void ppRefresh_actionPerformed(ActionEvent e) {
//     resourcesTable.tableChanged();
//  }

//  /**
//   * Copy a file to the directory of the current project
//   * @param srcStr The path of the source file.
//   * @param destStr The destination path.
//   * @return The new path of the file.
//   */
//  String copyFileToProjectDir(String srcStr) {
//
//	  String JN_DOCPATH = Util.getEnvDir();
//
//	  String baseName;
//	  int i = srcStr.lastIndexOf( File.separator );
//		if ( i != -1 ) {
//			baseName = srcStr.substring(i+1);
//		} else
//			baseName = srcStr;
//
//	  String destStr = JN_DOCPATH + CurrentProject.get().getID()
//	  				   + File.separator + "_projectFiles" + File.separator + baseName;
//
//	  File f = new File(JN_DOCPATH + CurrentProject.get().getID() + File.separator + "_projectFiles");
//	  if (!f.exists()) {
//		  f.mkdirs();
//	  }
//	  System.out.println("[DEBUG] Copy file from: "+srcStr+" to: "+destStr);
//
//	  try {
//         FileInputStream in = new FileInputStream(srcStr);
//         FileOutputStream out = new FileOutputStream(destStr);
//         byte[] buf = new byte[4096];
//         int len;
//         while ((len = in.read(buf)) > 0) {
//           out.write(buf, 0, len);
//         }
//         out.close();
//         in.close();
//       }
//	   catch (IOException e) {
//         System.err.println(e);
//       }
//
//  return destStr;
//  }
}