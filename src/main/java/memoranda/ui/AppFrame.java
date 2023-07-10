package main.java.memoranda.ui;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.text.html.HTMLDocument;

import main.java.memoranda.*;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.ui.htmleditor.HTMLEditor;
import main.java.memoranda.util.Configuration;
import main.java.memoranda.util.Context;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.ProjectExporter;
import main.java.memoranda.util.ProjectPackager;
import main.java.memoranda.util.Util;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;


/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: AppFrame.java,v 1.33 2005/07/05 08:17:24 alexeya Exp $*/

public class AppFrame extends JFrame {

    JPanel contentPane;
    JMenuBar menuBar = new JMenuBar();
    JMenu jMenuFile = new JMenu();
    JMenuItem jMenuFileExit = new JMenuItem();

    JToolBar toolBar = new JToolBar();
    JButton jButton3 = new JButton();
    JLabel statusBar = new JLabel();
    BorderLayout borderLayout1 = new BorderLayout();
    JSplitPane splitPane = new JSplitPane();
    ProjectsPanel projectsPanel = new ProjectsPanel();
    boolean prPanelExpanded = false;


    public WorkPanel workPanel = new WorkPanel();
    HTMLEditor editor = workPanel.dailyItemsPanel.editorPanel.editor;

    static Vector exitListeners = new Vector();



    public Action minimizeAction = new AbstractAction("Close the window") {
        public void actionPerformed(ActionEvent e) {
            doMinimize();
        }
    };

    public Action preferencesAction = new AbstractAction("Preferences") {
        public void actionPerformed(ActionEvent e) {
            showPreferences();
        }
    };

    
    JMenuItem jMenuFileNewPrj = new JMenuItem();
    JMenuItem jMenuFileNewNote = new JMenuItem(workPanel.dailyItemsPanel.editorPanel.newAction);
    JMenuItem jMenuFileExportNote = new JMenuItem(
            workPanel.dailyItemsPanel.editorPanel.exportAction);
    JMenuItem jMenuFileMin = new JMenuItem(minimizeAction);

    JMenuItem jMenuEditUndo = new JMenuItem(editor.undoAction);
    JMenuItem jMenuEditRedo = new JMenuItem(editor.redoAction);
    JMenuItem jMenuEditCut = new JMenuItem(editor.cutAction);
    JMenuItem jMenuEditCopy = new JMenuItem(editor.copyAction);
    JMenuItem jMenuEditPaste = new JMenuItem(editor.pasteAction);
    JMenuItem jMenuEditPasteSpec = new JMenuItem(editor.stylePasteAction);
    JMenuItem jMenuEditSelectAll = new JMenuItem(editor.selectAllAction);
    JMenuItem jMenuEditFind = new JMenuItem(editor.findAction);

    JMenuItem jMenuInsertImage = new JMenuItem(editor.imageAction);
    JMenuItem jMenuInsertTable = new JMenuItem(editor.tableAction);
    JMenuItem jMenuInsertLink = new JMenuItem(editor.linkAction);
    JMenu jMenuInsertList = new JMenu();
    JMenuItem jMenuInsertListUL = new JMenuItem(editor.ulAction);
    JMenuItem jMenuInsertListOL = new JMenuItem(editor.olAction);
    JMenuItem jMenuInsertBR = new JMenuItem(editor.breakAction);
    JMenuItem jMenuInsertHR = new JMenuItem(editor.insertHRAction);
    JMenuItem jMenuInsertChar = new JMenuItem(editor.insCharAction);
    JMenuItem jMenuInsertDate = new JMenuItem(
            workPanel.dailyItemsPanel.editorPanel.insertDateAction);
    JMenuItem jMenuInsertTime = new JMenuItem(
    		workPanel.dailyItemsPanel.editorPanel.insertTimeAction);
    JMenuItem jMenuInsertFile = new JMenuItem(
            workPanel.dailyItemsPanel.editorPanel.importAction);

    JMenu jMenuFormatPStyle = new JMenu();
    JMenuItem jMenuFormatP = new JMenuItem(editor.new BlockAction(editor.T_P,
            ""));
    JMenuItem jMenuFormatH1 = new JMenuItem(editor.new BlockAction(editor.T_H1,
            ""));
    JMenuItem jMenuFormatH2 = new JMenuItem(editor.new BlockAction(editor.T_H2,
            ""));
    JMenuItem jMenuFormatH3 = new JMenuItem(editor.new BlockAction(editor.T_H3,
            ""));
    JMenuItem jMenuFormatH4 = new JMenuItem(editor.new BlockAction(editor.T_H4,
            ""));
    JMenuItem jMenuFormatH5 = new JMenuItem(editor.new BlockAction(editor.T_H5,
            ""));
    JMenuItem jMenuFormatH6 = new JMenuItem(editor.new BlockAction(editor.T_H6,
            ""));
    JMenuItem jMenuFormatPRE = new JMenuItem(editor.new BlockAction(
            editor.T_PRE, ""));
    JMenuItem jMenuFormatBLCQ = new JMenuItem(editor.new BlockAction(
            editor.T_BLOCKQ, ""));
    JMenu jjMenuFormatChStyle = new JMenu();
    JMenuItem jMenuFormatChNorm = new JMenuItem(editor.new InlineAction(
            editor.I_NORMAL, ""));
    JMenuItem jMenuFormatChEM = new JMenuItem(editor.new InlineAction(
            editor.I_EM, ""));
    JMenuItem jMenuFormatChSTRONG = new JMenuItem(editor.new InlineAction(
            editor.I_STRONG, ""));
    JMenuItem jMenuFormatChCODE = new JMenuItem(editor.new InlineAction(
            editor.I_CODE, ""));
    JMenuItem jMenuFormatChCite = new JMenuItem(editor.new InlineAction(
            editor.I_CITE, ""));
    JMenuItem jMenuFormatChSUP = new JMenuItem(editor.new InlineAction(
            editor.I_SUPERSCRIPT, ""));
    JMenuItem jMenuFormatChSUB = new JMenuItem(editor.new InlineAction(
            editor.I_SUBSCRIPT, ""));
    JMenuItem jMenuFormatChCustom = new JMenuItem(editor.new InlineAction(
            editor.I_CUSTOM, ""));
    JMenuItem jMenuFormatChB = new JMenuItem(editor.boldAction);
    JMenuItem jMenuFormatChI = new JMenuItem(editor.italicAction);
    JMenuItem jMenuFormatChU = new JMenuItem(editor.underAction);
    JMenu jMenuFormatAlign = new JMenu();
    JMenuItem jMenuFormatAlignL = new JMenuItem(editor.lAlignAction);
    JMenuItem jMenuFormatAlignC = new JMenuItem(editor.cAlignAction);
    JMenuItem jMenuFormatAlignR = new JMenuItem(editor.rAlignAction);
    JMenu jMenuFormatTable = new JMenu();
    JMenuItem jMenuFormatTableInsR = new JMenuItem(editor.insertTableRowAction);
    JMenuItem jMenuFormatTableInsC = new JMenuItem(editor.insertTableCellAction);
    JMenuItem jMenuFormatProperties = new JMenuItem(editor.propsAction);

    JMenuItem jMenuEditPref = new JMenuItem(preferencesAction);

    JMenu jMenuInsertSpecial = new JMenu();
    
    JMenu jMenuHelp = new JMenu();
    
    JMenuItem jMenuHelpGuide = new JMenuItem();
    JMenuItem jMenuHelpWeb = new JMenuItem();
    JMenuItem jMenuHelpBug = new JMenuItem();
    JMenuItem jMenuHelpAbout = new JMenuItem();

    //Construct the frame
    public AppFrame() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        }
        catch (Exception e) {
            new ExceptionDialog(e);
        }
    }
    //Component initialization
    private void jbInit() throws Exception {
        this.setIconImage(new ImageIcon(AppFrame.class.getResource(
                "/ui/icons/jnotes16.png"))
                .getImage());
        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(borderLayout1);
        //this.setSize(new Dimension(800, 500));
        this.setTitle("Memoranda - " + CurrentProject.get().getTitle());
        //Added a space to App.VERSION_INFO to make it look some nicer
        statusBar.setText(" Version:" + App.VERSION_INFO + " (Build "
                + App.BUILD_INFO + " )");

        jMenuFile.setText(Local.getString("File"));
        jMenuFileExit.setText(Local.getString("Exit"));
        jMenuFileExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doExit();
            }
        });
        jMenuHelp.setText(Local.getString("Help"));
        
        jMenuHelpGuide.setText(Local.getString("Online user's guide"));
        jMenuHelpGuide.setIcon(new ImageIcon(AppFrame.class.getResource(
                "/ui/icons/help.png")));
        jMenuHelpGuide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuHelpGuide_actionPerformed(e);
            }
        });
        
        jMenuHelpWeb.setText(Local.getString("Memoranda web site"));
        jMenuHelpWeb.setIcon(new ImageIcon(AppFrame.class.getResource(
                "/ui/icons/web.png")));
        jMenuHelpWeb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuHelpWeb_actionPerformed(e);
            }
        });
        
        jMenuHelpBug.setText(Local.getString("Report a bug"));
        jMenuHelpBug.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuHelpBug_actionPerformed(e);
            }
        });        
        
        jMenuHelpAbout.setText(Local.getString("About Memoranda"));
        jMenuHelpAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuHelpAbout_actionPerformed(e);
            }
        });
        //jButton3.setIcon(image3);
        jButton3.setToolTipText(Local.getString("Help"));
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

        splitPane.setContinuousLayout(true);
        splitPane.setDividerSize(3);
        //splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(28);
        //projectsPanel.setMaximumSize(new Dimension(2147483647, 200));
        projectsPanel.setMinimumSize(new Dimension(10, 28));
        projectsPanel.setPreferredSize(new Dimension(10, 28));
        /*workPanel.setMinimumSize(new Dimension(734, 300));
         workPanel.setPreferredSize(new Dimension(1073, 300));*/
        splitPane.setDividerLocation(28);

        /* jMenuFileNewPrj.setText(Local.getString("New project") + "...");
         jMenuFileNewPrj.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         ProjectDialog.newProject();
         }
         });
         */
        jMenuFileNewPrj.setAction(projectsPanel.newProjectAction);

        jMenuFileMin.setText(Local.getString("Close the window"));
        jMenuFileMin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10,
                InputEvent.ALT_MASK));


        jMenuEditUndo.setText(Local.getString("Undo"));
        jMenuEditUndo.setToolTipText(Local.getString("Undo"));
        jMenuEditRedo.setText(Local.getString("Redo"));
        jMenuEditRedo.setToolTipText(Local.getString("Redo"));
        jMenuEditCut.setText(Local.getString("Cut"));
        jMenuEditCut.setToolTipText(Local.getString("Cut"));
        jMenuEditCopy.setText(Local.getString("Copy"));
        jMenuEditCopy.setToolTipText(Local.getString("Copy"));
        jMenuEditPaste.setText(Local.getString("Paste"));
        jMenuEditPaste.setToolTipText(Local.getString("Paste"));
        jMenuEditPasteSpec.setText(Local.getString("Paste special"));
        jMenuEditPasteSpec.setToolTipText(Local.getString("Paste special"));
        jMenuEditSelectAll.setText(Local.getString("Select all"));

        jMenuEditFind.setText(Local.getString("Find & replace") + "...");

        jMenuEditPref.setText(Local.getString("Preferences") + "...");


        jMenuInsertImage.setText(Local.getString("Image") + "...");
        jMenuInsertImage.setToolTipText(Local.getString("Insert Image"));
        jMenuInsertTable.setText(Local.getString("Table") + "...");
        jMenuInsertTable.setToolTipText(Local.getString("Insert Table"));
        jMenuInsertLink.setText(Local.getString("Hyperlink") + "...");
        jMenuInsertLink.setToolTipText(Local.getString("Insert Hyperlink"));
        jMenuInsertList.setText(Local.getString("List"));

        jMenuInsertListUL.setText(Local.getString("Unordered"));
        jMenuInsertListUL.setToolTipText(Local.getString("Insert Unordered"));
        jMenuInsertListOL.setText(Local.getString("Ordered"));

        jMenuInsertSpecial.setText(Local.getString("Special"));
        jMenuInsertBR.setText(Local.getString("Line break"));
        jMenuInsertHR.setText(Local.getString("Horizontal rule"));

        jMenuInsertListOL.setToolTipText(Local.getString("Insert Ordered"));

        jMenuInsertChar.setText(Local.getString("Special character") + "...");
        jMenuInsertChar.setToolTipText(Local.getString(
                "Insert Special character"));
        jMenuInsertDate.setText(Local.getString("Current date"));
        jMenuInsertTime.setText(Local.getString("Current time"));
        jMenuInsertFile.setText(Local.getString("File") + "...");

        jMenuFormatPStyle.setText(Local.getString("Paragraph style"));
        jMenuFormatP.setText(Local.getString("Paragraph"));
        jMenuFormatH1.setText(Local.getString("Header") + " 1");
        jMenuFormatH2.setText(Local.getString("Header") + " 2");
        jMenuFormatH3.setText(Local.getString("Header") + " 3");
        jMenuFormatH4.setText(Local.getString("Header") + " 4");
        jMenuFormatH5.setText(Local.getString("Header") + " 5");
        jMenuFormatH6.setText(Local.getString("Header") + " 6");
        jMenuFormatPRE.setText(Local.getString("Preformatted text"));
        jMenuFormatBLCQ.setText(Local.getString("Blockquote"));
        jjMenuFormatChStyle.setText(Local.getString("Character style"));
        jMenuFormatChNorm.setText(Local.getString("Normal"));
        jMenuFormatChEM.setText(Local.getString("Emphasis"));
        jMenuFormatChSTRONG.setText(Local.getString("Strong"));
        jMenuFormatChCODE.setText(Local.getString("Code"));
        jMenuFormatChCite.setText(Local.getString("Cite"));
        jMenuFormatChSUP.setText(Local.getString("Superscript"));
        jMenuFormatChSUB.setText(Local.getString("Subscript"));
        jMenuFormatChCustom.setText(Local.getString("Custom style") + "...");
        jMenuFormatChB.setText(Local.getString("Bold"));
        jMenuFormatChB.setToolTipText(Local.getString("Bold"));
        jMenuFormatChI.setText(Local.getString("Italic"));
        jMenuFormatChI.setToolTipText(Local.getString("Italic"));
        jMenuFormatChU.setText(Local.getString("Underline"));
        jMenuFormatChU.setToolTipText(Local.getString("Underline"));
        jMenuFormatAlign.setText(Local.getString("Alignment"));
        jMenuFormatAlignL.setText(Local.getString("Left"));
        jMenuFormatAlignL.setToolTipText(Local.getString("Left"));
        jMenuFormatAlignC.setText(Local.getString("Center"));
        jMenuFormatAlignC.setToolTipText(Local.getString("Center"));
        jMenuFormatAlignR.setText(Local.getString("Right"));
        jMenuFormatAlignR.setToolTipText(Local.getString("Right"));
        jMenuFormatTable.setText(Local.getString("Table"));
        jMenuFormatTableInsR.setText(Local.getString("Insert row"));
        jMenuFormatTableInsC.setText(Local.getString("Insert cell"));
        jMenuFormatProperties.setText(Local.getString("Object properties")
                + "...");
        jMenuFormatProperties.setToolTipText(Local.getString(
                "Object properties"));


        jMenuInsertSpecial.setText(Local.getString("Special"));
        jMenuInsertBR.setText(Local.getString("Line break"));
        jMenuInsertBR.setToolTipText(Local.getString("Insert break"));
        jMenuInsertHR.setText(Local.getString("Horizontal rule"));
        jMenuInsertHR.setToolTipText(Local.getString("Insert Horizontal rule"));

        toolBar.add(jButton3);
        jMenuFile.add(jMenuEditPref);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuFileMin);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuFileExit);
        
        jMenuHelp.add(jMenuHelpGuide);
        jMenuHelp.add(jMenuHelpWeb);
        jMenuHelp.add(jMenuHelpBug);
        jMenuHelp.addSeparator();
        jMenuHelp.add(jMenuHelpAbout);
        
        menuBar.add(jMenuFile);
        menuBar.add(jMenuHelp);
        this.setJMenuBar(menuBar);
        contentPane.add(statusBar, BorderLayout.SOUTH);
        contentPane.add(splitPane, BorderLayout.CENTER);
        splitPane.add(projectsPanel, JSplitPane.TOP);
        splitPane.add(workPanel, JSplitPane.BOTTOM);

        jMenuInsertList.add(jMenuInsertListUL);
        jMenuInsertList.add(jMenuInsertListOL);

        jMenuFormatPStyle.add(jMenuFormatP);
        jMenuFormatPStyle.addSeparator();
        jMenuFormatPStyle.add(jMenuFormatH1);
        jMenuFormatPStyle.add(jMenuFormatH2);
        jMenuFormatPStyle.add(jMenuFormatH3);
        jMenuFormatPStyle.add(jMenuFormatH4);
        jMenuFormatPStyle.add(jMenuFormatH5);
        jMenuFormatPStyle.add(jMenuFormatH6);
        jMenuFormatPStyle.addSeparator();
        jMenuFormatPStyle.add(jMenuFormatPRE);
        jMenuFormatPStyle.add(jMenuFormatBLCQ);
        jjMenuFormatChStyle.add(jMenuFormatChNorm);
        jjMenuFormatChStyle.addSeparator();
        jjMenuFormatChStyle.add(jMenuFormatChB);
        jjMenuFormatChStyle.add(jMenuFormatChI);
        jjMenuFormatChStyle.add(jMenuFormatChU);
        jjMenuFormatChStyle.addSeparator();
        jjMenuFormatChStyle.add(jMenuFormatChEM);
        jjMenuFormatChStyle.add(jMenuFormatChSTRONG);
        jjMenuFormatChStyle.add(jMenuFormatChCODE);
        jjMenuFormatChStyle.add(jMenuFormatChCite);
        jjMenuFormatChStyle.addSeparator();
        jjMenuFormatChStyle.add(jMenuFormatChSUP);
        jjMenuFormatChStyle.add(jMenuFormatChSUB);
        jjMenuFormatChStyle.addSeparator();
        jjMenuFormatChStyle.add(jMenuFormatChCustom);
        jMenuFormatAlign.add(jMenuFormatAlignL);
        jMenuFormatAlign.add(jMenuFormatAlignC);
        jMenuFormatAlign.add(jMenuFormatAlignR);
        jMenuFormatTable.add(jMenuFormatTableInsR);
        jMenuFormatTable.add(jMenuFormatTableInsC);

        splitPane.setBorder(null);
        workPanel.setBorder(null);

        setEnabledEditorMenus(false);

        projectsPanel.AddExpandListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (prPanelExpanded) {
                    prPanelExpanded = false;
                    splitPane.setDividerLocation(28);
                }
                else {
                    prPanelExpanded = true;
                    splitPane.setDividerLocation(0.2);
                }
            }
        });

        java.awt.event.ActionListener setMenusDisabled = new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setEnabledEditorMenus(false);
            }
        };

        this.workPanel.dailyItemsPanel.taskB
                .addActionListener(setMenusDisabled);
        this.workPanel.dailyItemsPanel.alarmB.addActionListener(
                setMenusDisabled);

        this.workPanel.signUpB.addActionListener(setMenusDisabled);
        this.workPanel.trainerB.addActionListener(setMenusDisabled);
        this.workPanel.adminB.addActionListener(setMenusDisabled);
        this.workPanel.homeB.addActionListener(setMenusDisabled);


        Object fwo = Context.get("FRAME_WIDTH");
        Object fho = Context.get("FRAME_HEIGHT");
        if ((fwo != null) && (fho != null)) {
            int w = Integer.parseInt((String) fwo);
            int h = Integer.parseInt((String) fho);
            this.setSize(w, h);
        }
        else
            this.setExtendedState(Frame.MAXIMIZED_BOTH);

        Object xo = Context.get("FRAME_XPOS");
        Object yo = Context.get("FRAME_YPOS");
        if ((xo != null) && (yo != null)) {
            int x = Integer.parseInt((String) xo);
            int y = Integer.parseInt((String) yo);
            this.setLocation(x, y);
        }

    }
   
    protected void jMenuHelpBug_actionPerformed(ActionEvent e) {
        Util.runBrowser(App.BUGS_TRACKER_URL);
    }
   
    protected void jMenuHelpWeb_actionPerformed(ActionEvent e) {
        Util.runBrowser(App.WEBSITE_URL);
    }
   
    protected void jMenuHelpGuide_actionPerformed(ActionEvent e) {
        Util.runBrowser(App.GUIDE_URL);
    }
    
    //File | Exit action performed
    public void doExit() {
        if (Configuration.get("ASK_ON_EXIT").equals("yes")) {
                        Dimension frmSize = this.getSize();
                        Point loc = this.getLocation();
                        
                        ExitConfirmationDialog dlg = new ExitConfirmationDialog(this,Local.getString("Exit"));
                        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
                        dlg.setVisible(true);
                        if(dlg.CANCELLED) return;
        }

        Context.put("FRAME_WIDTH", this.getWidth());
        Context.put("FRAME_HEIGHT", this.getHeight());
        Context.put("FRAME_XPOS", this.getLocation().x);
        Context.put("FRAME_YPOS", this.getLocation().y);
        exitNotify();
        UserLoader.save();
        App.exitProgram();
    }

    //App.closeWindow() changed to App.minimizeWindow()
    public void doMinimize() {
        exitNotify();
        App.minimizeWindow();
    }

    //Help | About action performed
    public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
         AppFrame_AboutBox dlg = new AppFrame_AboutBox(this);        
         Dimension dlgSize = dlg.getSize();
         Dimension frmSize = getSize();
         Point loc = getLocation();
         dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
         dlg.setModal(true);
         dlg.setVisible(true);
    }

    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            if (Configuration.get("ON_CLOSE").equals("exit"))
                doExit();
            else
                doMinimize();
        }
        else if ((e.getID() == WindowEvent.WINDOW_ICONIFIED)) {
            super.processWindowEvent(new WindowEvent(this,
                    WindowEvent.WINDOW_ICONIFIED));
            doMinimize();
        }
        else
            super.processWindowEvent(e);
    }

    public static void addExitListener(ActionListener al) {
        exitListeners.add(al);
    }

    public static Collection getExitListeners() {
        return exitListeners;
    }

    private static void exitNotify() {
        for (int i = 0; i < exitListeners.size(); i++)
            ((ActionListener) exitListeners.get(i)).actionPerformed(null);
    }

    public void setEnabledEditorMenus(boolean enabled) {
        this.jMenuFileNewNote.setEnabled(enabled);
        this.jMenuFileExportNote.setEnabled(enabled);
    }
    public void showPreferences() {
        PreferencesDialog dlg = new PreferencesDialog(this);
        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }

}