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

import main.java.memoranda.CurrentProject;
import main.java.memoranda.History;
import main.java.memoranda.Note;
import main.java.memoranda.NoteList;
import main.java.memoranda.Project;
import main.java.memoranda.ProjectListener;
import main.java.memoranda.ResourcesList;
import main.java.memoranda.TaskList;
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
    ImageIcon image1;
    ImageIcon image2;
    ImageIcon image3;
    JLabel statusBar = new JLabel();
    BorderLayout borderLayout1 = new BorderLayout();
    JSplitPane splitPane = new JSplitPane();
    ProjectsPanel projectsPanel = new ProjectsPanel();
    boolean prPanelExpanded = false;

    JMenu jMenuEdit = new JMenu();
    JMenu jMenuFormat = new JMenu();
    JMenu jMenuInsert = new JMenu();

    public WorkPanel workPanel = new WorkPanel();
    HTMLEditor editor = workPanel.dailyItemsPanel.editorPanel.editor;

    static Vector exitListeners = new Vector();

    public Action prjPackAction = new AbstractAction("Pack current project") {
        public void actionPerformed(ActionEvent e) {
            doPrjPack();
        }
    };

    public Action prjUnpackAction = new AbstractAction("Unpack project") {
        public void actionPerformed(ActionEvent e) {
            doPrjUnPack();
        }
    };

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
    
    public Action exportNotesAction =
                new AbstractAction(Local.getString("Export notes") + "...") {

                public void actionPerformed(ActionEvent e) {
                        ppExport_actionPerformed(e);
                }
        };
        
        public Action importNotesAction =
                        new AbstractAction(Local.getString("Import multiple notes")) {

                        public void actionPerformed(ActionEvent e) {
                                ppImport_actionPerformed(e);
                        }
                };
        public Action importOneNoteAction =
                new AbstractAction(Local.getString("Import one note")) {

                public void actionPerformed(ActionEvent e) {
                        p1Import_actionPerformed(e);
                }
        };
    
    JMenuItem jMenuFileNewPrj = new JMenuItem();
        JMenuItem jMenuFileNewNote = new JMenuItem(workPanel.dailyItemsPanel.editorPanel.newAction);
    JMenuItem jMenuFilePackPrj = new JMenuItem(prjPackAction);
    JMenuItem jMenuFileUnpackPrj = new JMenuItem(prjUnpackAction);
    JMenuItem jMenuFileExportPrj = new JMenuItem(exportNotesAction);
    JMenuItem jMenuFileImportPrj = new JMenuItem(importNotesAction);
    JMenuItem jMenuFileImportNote = new JMenuItem(importOneNoteAction);
    JMenuItem jMenuFileExportNote = new JMenuItem(
            workPanel.dailyItemsPanel.editorPanel.exportAction);
    JMenuItem jMenuFileMin = new JMenuItem(minimizeAction);

    JMenuItem jMenuItem1 = new JMenuItem();
    JMenuItem jMenuEditUndo = new JMenuItem(editor.undoAction);
    JMenuItem jMenuEditRedo = new JMenuItem(editor.redoAction);
    JMenuItem jMenuEditCut = new JMenuItem(editor.cutAction);
    JMenuItem jMenuEditCopy = new JMenuItem(editor.copyAction);
    JMenuItem jMenuEditPaste = new JMenuItem(editor.pasteAction);
    JMenuItem jMenuEditPasteSpec = new JMenuItem(editor.stylePasteAction);
    JMenuItem jMenuEditSelectAll = new JMenuItem(editor.selectAllAction);
    JMenuItem jMenuEditFind = new JMenuItem(editor.findAction);

    JMenu jMenuGo = new JMenu();
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
    JMenuItem jMenuGoHBack = new JMenuItem(History.historyBackAction);
    JMenuItem jMenuGoFwd = new JMenuItem(History.historyForwardAction);

    JMenuItem jMenuGoDayBack = new JMenuItem(
            workPanel.dailyItemsPanel.calendar.dayBackAction);
    JMenuItem jMenuGoDayFwd = new JMenuItem(
            workPanel.dailyItemsPanel.calendar.dayForwardAction);
    JMenuItem jMenuGoToday = new JMenuItem(
            workPanel.dailyItemsPanel.calendar.todayAction);

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

        jMenuFileUnpackPrj.setText(Local.getString("Unpack project") + "...");
        jMenuFileExportNote.setText(Local.getString("Export current note")
                + "...");
        jMenuFileImportNote.setText(Local.getString("Import one note")
                + "...");
        jMenuFilePackPrj.setText(Local.getString("Pack project") + "...");
        jMenuFileMin.setText(Local.getString("Close the window"));
        jMenuFileMin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10,
                InputEvent.ALT_MASK));

        jMenuEdit.setText(Local.getString("Edit"));

        jMenuEditUndo.setText(Local.getString("Undo"));
        jMenuEditUndo.setToolTipText(Local.getString("Undo"));
        jMenuEditRedo.setText(Local.getString("Redo"));
        jMenuEditRedo.setToolTipText(Local.getString("Redo"));
        jMenuEditCut.setText(Local.getString("Cut"));
        jMenuEditCut.setToolTipText(Local.getString("Cut"));
        jMenuEditCopy.setText((String) Local.getString("Copy"));
        jMenuEditCopy.setToolTipText(Local.getString("Copy"));
        jMenuEditPaste.setText(Local.getString("Paste"));
        jMenuEditPaste.setToolTipText(Local.getString("Paste"));
        jMenuEditPasteSpec.setText(Local.getString("Paste special"));
        jMenuEditPasteSpec.setToolTipText(Local.getString("Paste special"));
        jMenuEditSelectAll.setText(Local.getString("Select all"));

        jMenuEditFind.setText(Local.getString("Find & replace") + "...");

        jMenuEditPref.setText(Local.getString("Preferences") + "...");

        jMenuInsert.setText(Local.getString("Insert"));

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

        jMenuFormat.setText(Local.getString("Format"));
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

        jMenuGo.setText(Local.getString("Go"));
        jMenuGoHBack.setText(Local.getString("History back"));
        jMenuGoHBack.setToolTipText(Local.getString("History back"));
        jMenuGoFwd.setText(Local.getString("History forward"));
        jMenuGoFwd.setToolTipText(Local.getString("History forward"));
        jMenuGoDayBack.setText(Local.getString("One day back"));
        jMenuGoDayFwd.setText(Local.getString("One day forward"));
        jMenuGoToday.setText(Local.getString("To today"));

        jMenuInsertSpecial.setText(Local.getString("Special"));
        jMenuInsertBR.setText(Local.getString("Line break"));
        jMenuInsertBR.setToolTipText(Local.getString("Insert break"));
        jMenuInsertHR.setText(Local.getString("Horizontal rule"));
        jMenuInsertHR.setToolTipText(Local.getString("Insert Horizontal rule"));

        toolBar.add(jButton3);
        jMenuFile.add(jMenuFileNewPrj);
                jMenuFile.add(jMenuFileNewNote);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuFilePackPrj);
        jMenuFile.add(jMenuFileUnpackPrj);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuFileExportPrj);
        jMenuFile.add(jMenuFileExportNote);
        jMenuFile.add(jMenuFileImportNote);
        jMenuFile.add(jMenuFileImportPrj);
        jMenuFile.addSeparator();
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
        menuBar.add(jMenuEdit);
        menuBar.add(jMenuInsert);
        menuBar.add(jMenuFormat);
        menuBar.add(jMenuGo);
        menuBar.add(jMenuHelp);
        this.setJMenuBar(menuBar);
        //contentPane.add(toolBar, BorderLayout.NORTH);
        contentPane.add(statusBar, BorderLayout.SOUTH);
        contentPane.add(splitPane, BorderLayout.CENTER);
        splitPane.add(projectsPanel, JSplitPane.TOP);
        splitPane.add(workPanel, JSplitPane.BOTTOM);
        jMenuEdit.add(jMenuEditUndo);
        jMenuEdit.add(jMenuEditRedo);
        jMenuEdit.addSeparator();
        jMenuEdit.add(jMenuEditCut);
        jMenuEdit.add(jMenuEditCopy);
        jMenuEdit.add(jMenuEditPaste);
        jMenuEdit.add(jMenuEditPasteSpec);
        jMenuEdit.addSeparator();
        jMenuEdit.add(jMenuEditSelectAll);
        jMenuEdit.addSeparator();
        jMenuEdit.add(jMenuEditFind);

        jMenuInsert.add(jMenuInsertImage);
        jMenuInsert.add(jMenuInsertTable);
        jMenuInsert.add(jMenuInsertLink);
        jMenuInsert.add(jMenuInsertList);
        //jMenuInsert.add(jMenuInsertSpecial);
        jMenuInsertList.add(jMenuInsertListUL);
        jMenuInsertList.add(jMenuInsertListOL);
        jMenuInsert.addSeparator();
        jMenuInsert.add(jMenuInsertBR);
        jMenuInsert.add(jMenuInsertHR);
        jMenuInsert.add(jMenuInsertChar);
        jMenuInsert.addSeparator();
        jMenuInsert.add(jMenuInsertDate);
        jMenuInsert.add(jMenuInsertTime);
        jMenuInsert.addSeparator();
        jMenuInsert.add(jMenuInsertFile);

        jMenuFormat.add(jMenuFormatPStyle);
        jMenuFormat.add(jjMenuFormatChStyle);
        jMenuFormat.add(jMenuFormatAlign);
        jMenuFormat.addSeparator();
        jMenuFormat.add(jMenuFormatTable);
        jMenuFormat.addSeparator();
        jMenuFormat.add(jMenuFormatProperties);
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
        jMenuGo.add(jMenuGoHBack);
        jMenuGo.add(jMenuGoFwd);
        jMenuGo.addSeparator();
        jMenuGo.add(jMenuGoDayBack);
        jMenuGo.add(jMenuGoDayFwd);
        jMenuGo.add(jMenuGoToday);

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

        this.workPanel.tasksB.addActionListener(setMenusDisabled);
        this.workPanel.eventsB.addActionListener(setMenusDisabled);
        this.workPanel.filesB.addActionListener(setMenusDisabled);
        this.workPanel.agendaB.addActionListener(setMenusDisabled);

        this.workPanel.notesB.addActionListener(
                new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setEnabledEditorMenus(true);
                    }
                });

        Object fwo = Context.get("FRAME_WIDTH");
        Object fho = Context.get("FRAME_HEIGHT");
        if ((fwo != null) && (fho != null)) {
            int w = new Integer((String) fwo).intValue();
            int h = new Integer((String) fho).intValue();
            this.setSize(w, h);
        }
        else
            this.setExtendedState(Frame.MAXIMIZED_BOTH);

        Object xo = Context.get("FRAME_XPOS");
        Object yo = Context.get("FRAME_YPOS");
        if ((xo != null) && (yo != null)) {
            int x = new Integer((String) xo).intValue();
            int y = new Integer((String) yo).intValue();
            this.setLocation(x, y);
        }

        String pan = (String) Context.get("CURRENT_PANEL");
        if (pan != null) {
            workPanel.selectPanel(pan);
            setEnabledEditorMenus(pan.equalsIgnoreCase("NOTES"));
        }

        CurrentProject.addProjectListener(new ProjectListener() {

            public void projectChange(Project prj, NoteList nl, TaskList tl,
                    ResourcesList rl) {
            }

            public void projectWasChanged() {
                setTitle("Memoranda - " + CurrentProject.get().getTitle());
            }
        });

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

        Context.put("FRAME_WIDTH", new Integer(this.getWidth()));
        Context.put("FRAME_HEIGHT", new Integer(this.getHeight()));
        Context.put("FRAME_XPOS", new Integer(this.getLocation().x));
        Context.put("FRAME_YPOS", new Integer(this.getLocation().y));
        exitNotify();
        System.exit(0);
    }

    public void doMinimize() {
        exitNotify();
        App.closeWindow();
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
                    WindowEvent.WINDOW_CLOSING));
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
        this.jMenuEdit.setEnabled(enabled);
        this.jMenuFormat.setEnabled(enabled);
        this.jMenuInsert.setEnabled(enabled);
        this.jMenuFileNewNote.setEnabled(enabled);
        this.jMenuFileExportNote.setEnabled(enabled);
    }

    public void doPrjPack() {
        // Fix until Sun's JVM supports more locales...
        UIManager.put("FileChooser.saveInLabelText", Local
                .getString("Save in:"));
        UIManager.put("FileChooser.upFolderToolTipText", Local.getString(
                "Up One Level"));
        UIManager.put("FileChooser.newFolderToolTipText", Local.getString(
                "Create New Folder"));
        UIManager.put("FileChooser.listViewButtonToolTipText", Local
                .getString("List"));
        UIManager.put("FileChooser.detailsViewButtonToolTipText", Local
                .getString("Details"));
        UIManager.put("FileChooser.fileNameLabelText", Local.getString(
                "File Name:"));
        UIManager.put("FileChooser.filesOfTypeLabelText", Local.getString(
                "Files of Type:"));
        UIManager.put("FileChooser.saveButtonText", Local.getString("Save"));
        UIManager.put("FileChooser.saveButtonToolTipText", Local.getString(
                "Save selected file"));
        UIManager
                .put("FileChooser.cancelButtonText", Local.getString("Cancel"));
        UIManager.put("FileChooser.cancelButtonToolTipText", Local.getString(
                "Cancel"));

        JFileChooser chooser = new JFileChooser();
        chooser.setFileHidingEnabled(false);
        chooser.setDialogTitle(Local.getString("Pack project"));
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.RTF));
        chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.ZIP));
        // fixes XP style look cosmetical problems JVM 1.4.2 and 1.4.2_01
        chooser.setPreferredSize(new Dimension(550, 375));

        //Added to fix the problem with packing a file then deleting that file.
        //(jcscoobyrs) 17-Nov-2003 at 14:57:06 PM
        //---------------------------------------------------------------------
        File lastSel = null;

        try {
            lastSel = (java.io.File) Context.get("LAST_SELECTED_PACK_FILE");
        }
        catch (ClassCastException cce) {
            lastSel = new File(System.getProperty("user.dir") + File.separator);
        }
        //---------------------------------------------------------------------

        if (lastSel != null)
            chooser.setCurrentDirectory(lastSel);
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        Context.put("LAST_SELECTED_PACK_FILE", chooser.getSelectedFile());        
        java.io.File f = chooser.getSelectedFile();
        ProjectPackager.pack(CurrentProject.get(), f);
    }

    public void doPrjUnPack() {
        // Fix until Sun's JVM supports more locales...
        UIManager.put("FileChooser.lookInLabelText", Local
                .getString("Look in:"));
        UIManager.put("FileChooser.upFolderToolTipText", Local.getString(
                "Up One Level"));
        UIManager.put("FileChooser.newFolderToolTipText", Local.getString(
                "Create New Folder"));
        UIManager.put("FileChooser.listViewButtonToolTipText", Local
                .getString("List"));
        UIManager.put("FileChooser.detailsViewButtonToolTipText", Local
                .getString("Details"));
        UIManager.put("FileChooser.fileNameLabelText", Local.getString(
                "File Name:"));
        UIManager.put("FileChooser.filesOfTypeLabelText", Local.getString(
                "Files of Type:"));
        UIManager.put("FileChooser.openButtonText", Local.getString("Open"));
        UIManager.put("FileChooser.openButtonToolTipText", Local.getString(
                "Open selected file"));
        UIManager
                .put("FileChooser.cancelButtonText", Local.getString("Cancel"));
        UIManager.put("FileChooser.cancelButtonToolTipText", Local.getString(
                "Cancel"));

        JFileChooser chooser = new JFileChooser();
        chooser.setFileHidingEnabled(false);
        chooser.setDialogTitle(Local.getString("Unpack project"));
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.ZIP));
        //chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.RTF));
        // fixes XP style look cosmetical problems JVM 1.4.2 and 1.4.2_01
        chooser.setPreferredSize(new Dimension(550, 375));

        //Added to fix the problem with packing a file then deleting that file.
        //(jcscoobyrs) 17-Nov-2003 at 14:57:06 PM
        //---------------------------------------------------------------------
        File lastSel = null;

        try {
            lastSel = (java.io.File) Context.get("LAST_SELECTED_PACK_FILE");
        }
        catch (ClassCastException cce) {
            lastSel = new File(System.getProperty("user.dir") + File.separator);
        }
        //---------------------------------------------------------------------

        if (lastSel != null)
            chooser.setCurrentDirectory(lastSel);
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        Context.put("LAST_SELECTED_PACK_FILE", chooser.getSelectedFile());        
        java.io.File f = chooser.getSelectedFile();
        ProjectPackager.unpack(f);
        projectsPanel.prjTablePanel.updateUI();
    }

    public void showPreferences() {
        PreferencesDialog dlg = new PreferencesDialog(this);
        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }
    
            protected void ppExport_actionPerformed(ActionEvent e) {
                // Fix until Sun's JVM supports more locales...
                UIManager.put(
                        "FileChooser.lookInLabelText",
                        Local.getString("Save in:"));
                UIManager.put(
                        "FileChooser.upFolderToolTipText",
                        Local.getString("Up One Level"));
                UIManager.put(
                        "FileChooser.newFolderToolTipText",
                        Local.getString("Create New Folder"));
                UIManager.put(
                        "FileChooser.listViewButtonToolTipText",
                        Local.getString("List"));
                UIManager.put(
                        "FileChooser.detailsViewButtonToolTipText",
                        Local.getString("Details"));
                UIManager.put(
                        "FileChooser.fileNameLabelText",
                        Local.getString("File Name:"));
                UIManager.put(
                        "FileChooser.filesOfTypeLabelText",
                        Local.getString("Files of Type:"));
                UIManager.put("FileChooser.saveButtonText", Local.getString("Save"));
                UIManager.put(
                        "FileChooser.saveButtonToolTipText",
                        Local.getString("Save selected file"));
                UIManager.put(
                        "FileChooser.cancelButtonText",
                        Local.getString("Cancel"));
                UIManager.put(
                        "FileChooser.cancelButtonToolTipText",
                        Local.getString("Cancel"));

                JFileChooser chooser = new JFileChooser();
                chooser.setFileHidingEnabled(false);
                chooser.setDialogTitle(Local.getString("Export notes"));
                chooser.setAcceptAllFileFilterUsed(false);
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                chooser.addChoosableFileFilter(
                        new AllFilesFilter(AllFilesFilter.XHTML));
                chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.HTML));

                String lastSel = (String) Context.get("LAST_SELECTED_EXPORT_FILE");
                if (lastSel != null)
                        chooser.setCurrentDirectory(new File(lastSel));

                ProjectExportDialog dlg =
                        new ProjectExportDialog(
                                App.getFrame(),
                                Local.getString("Export notes"),
                                chooser);
                String enc = (String) Context.get("EXPORT_FILE_ENCODING");
                if (enc != null)
                        dlg.encCB.setSelectedItem(enc);
                String spl = (String) Context.get("EXPORT_SPLIT_NOTES");
                if (spl != null)
                        dlg.splitChB.setSelected(spl.equalsIgnoreCase("true"));
                String ti = (String) Context.get("EXPORT_TITLES_AS_HEADERS");
                if (ti != null)
                        dlg.titlesAsHeadersChB.setSelected(ti.equalsIgnoreCase("true"));
                Dimension dlgSize = new Dimension(550, 500);
                dlg.setSize(dlgSize);
                Dimension frmSize = App.getFrame().getSize();
                Point loc = App.getFrame().getLocation();
                dlg.setLocation(
                        (frmSize.width - dlgSize.width) / 2 + loc.x,
                        (frmSize.height - dlgSize.height) / 2 + loc.y);
                dlg.setVisible(true);
                if (dlg.CANCELLED)
                        return;
                
                        Context.put(
                                "LAST_SELECTED_EXPORT_FILE",
                                chooser.getSelectedFile().getPath());
                        Context.put("EXPORT_SPLIT_NOTES", new Boolean(dlg.splitChB.isSelected()).toString());
                        Context.put("EXPORT_TITLES_AS_HEADERS", new Boolean(dlg.titlesAsHeadersChB.isSelected()).toString());
                
                int ei = dlg.encCB.getSelectedIndex();
                enc = null;
                if (ei == 1)
                        enc = "UTF-8";
                boolean nument = (ei == 2);
                File f = chooser.getSelectedFile();
                boolean xhtml =
                        chooser.getFileFilter().getDescription().indexOf("XHTML") > -1;
                 CurrentProject.save();
                 ProjectExporter.export(CurrentProject.get(), chooser.getSelectedFile(), enc, xhtml, 
                                 dlg.splitChB.isSelected(), true, nument, dlg.titlesAsHeadersChB.isSelected(), false); 
                }
            
            protected void ppImport_actionPerformed(ActionEvent e) {
            
            UIManager.put("FileChooser.lookInLabelText", Local
                    .getString("Look in:"));
            UIManager.put("FileChooser.upFolderToolTipText", Local.getString(
                    "Up One Level"));
            UIManager.put("FileChooser.newFolderToolTipText", Local.getString(
                    "Create New Folder"));
            UIManager.put("FileChooser.listViewButtonToolTipText", Local
                    .getString("List"));
            UIManager.put("FileChooser.detailsViewButtonToolTipText", Local
                    .getString("Details"));
            UIManager.put("FileChooser.fileNameLabelText", Local.getString(
                    "File Name:"));
            UIManager.put("FileChooser.filesOfTypeLabelText", Local.getString(
                    "Files of Type:"));
            UIManager.put("FileChooser.openButtonText", Local.getString("Open"));
            UIManager.put("FileChooser.openButtonToolTipText", Local.getString(
                    "Open selected file"));
            UIManager.put("FileChooser.cancelButtonText", Local.getString("Cancel"));
            UIManager.put("FileChooser.cancelButtonToolTipText", Local.getString(
                    "Cancel"));

            JFileChooser chooser = new JFileChooser();
            chooser.setFileHidingEnabled(false);
            chooser.setDialogTitle(Local.getString("Import notes"));
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.HTML));
            chooser.setPreferredSize(new Dimension(550, 375));

            File lastSel = null;

            try {
                lastSel = (java.io.File) Context.get("LAST_SELECTED_NOTE_FILE");
            }
            catch (ClassCastException cce) {
                lastSel = new File(System.getProperty("user.dir") + File.separator);
            }
            //---------------------------------------------------------------------

            if (lastSel != null)
                chooser.setCurrentDirectory(lastSel);
            if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
                return;
            Context.put("LAST_SELECTED_NOTE_FILE", chooser.getSelectedFile());        
            java.io.File f = chooser.getSelectedFile();
            HashMap<String,String> notesName = new HashMap<String,String>();
                HashMap<String,String> notesContent = new HashMap<String,String>();
            Builder parser = new Builder();
            String id="", name="", content = "";
            try{
                    Document document = parser.build(f);
                    Element body = document.getRootElement().getFirstChildElement("body");
                    Element names = body.getFirstChildElement("div").getFirstChildElement("ul");
                    Elements namelist = names.getChildElements("li");
                    Element item;
                    
                    for(int i = 0;i<namelist.size();i++){
                            item = namelist.get(i);
                            id = item.getFirstChildElement("a").getAttributeValue("href").replace("\"","").replace("#","");
                            name = item.getValue();
                            notesName.put(id,name);
                    }
                    System.out.println("id: "+id+" name: "+name);
                    
                    Elements contlist = body.getChildElements("a");
                    for(int i = 0;i<(contlist.size()-1);i++){
                            item = contlist.get(i);
                            id = item.getAttributeValue("name").replace("\"","");
                            content = item.getFirstChildElement("div").getValue();
                            notesContent.put(id,content);
                    }

                    JEditorPane p = new JEditorPane();
                    p.setContentType("text/html");
                    for (Map.Entry<String,String> entry : notesName.entrySet()){
                            id = entry.getKey();
                            name = entry.getValue().substring(11);
                            content = notesContent.get(id);
                            p.setText(content);
                            HTMLDocument doc = (HTMLDocument)p.getDocument();
                            Note note = CurrentProject.getNoteList().createNoteForDate(CurrentDate.get());
                    note.setTitle(name);
                            note.setId(Util.generateId());
                    CurrentStorage.get().storeNote(note, doc);
                    }
                    workPanel.dailyItemsPanel.notesControlPane.refresh();
                    
            }catch(Exception exc){
                    exc.printStackTrace();
            }
        }
            protected void p1Import_actionPerformed(ActionEvent e) {
                
            UIManager.put("FileChooser.lookInLabelText", Local
                    .getString("Look in:"));
            UIManager.put("FileChooser.upFolderToolTipText", Local.getString(
                    "Up One Level"));
            UIManager.put("FileChooser.newFolderToolTipText", Local.getString(
                    "Create New Folder"));
            UIManager.put("FileChooser.listViewButtonToolTipText", Local
                    .getString("List"));
            UIManager.put("FileChooser.detailsViewButtonToolTipText", Local
                    .getString("Details"));
            UIManager.put("FileChooser.fileNameLabelText", Local.getString(
                    "File Name:"));
            UIManager.put("FileChooser.filesOfTypeLabelText", Local.getString(
                    "Files of Type:"));
            UIManager.put("FileChooser.openButtonText", Local.getString("Open"));
            UIManager.put("FileChooser.openButtonToolTipText", Local.getString(
                    "Open selected file"));
            UIManager.put("FileChooser.cancelButtonText", Local.getString("Cancel"));
            UIManager.put("FileChooser.cancelButtonToolTipText", Local.getString(
                    "Cancel"));

            JFileChooser chooser = new JFileChooser();
            chooser.setFileHidingEnabled(false);

            chooser.setDialogTitle(Local.getString("Import notes"));
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.HTML));
            chooser.setPreferredSize(new Dimension(550, 375));

            File lastSel = null;

            try {
                lastSel = (java.io.File) Context.get("LAST_SELECTED_NOTE_FILE");
            }
            catch (ClassCastException cce) {
                lastSel = new File(System.getProperty("user.dir") + File.separator);
            }
            //---------------------------------------------------------------------

            if (lastSel != null)
                chooser.setCurrentDirectory(lastSel);
            if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
                return;
            Context.put("LAST_SELECTED_NOTE_FILE", chooser.getSelectedFile());        
            java.io.File f = chooser.getSelectedFile();
            HashMap<String,String> notesName = new HashMap<String,String>();
            HashMap<String,String> notesContent = new HashMap<String,String>();
            Builder parser = new Builder();
            String id="", name="", content = "";
            try{
                    Document document = parser.build(f);
                    content = document.getRootElement().getFirstChildElement("body").getValue();
                    content = content.substring(content.indexOf("\n", content.indexOf("-")));
                    content = content.replace("<p>","").replace("</p>","\n");
                    name = f.getName().substring(0,f.getName().lastIndexOf("."));	
                    Element item;
                    id=Util.generateId();
                    System.out.println(id+" "+name+" "+content);
                    notesName.put(id, name);
                    notesContent.put(id, content);
                    JEditorPane p = new JEditorPane();
                    p.setContentType("text/html");
                    
                    for (Map.Entry<String,String> entry : notesName.entrySet()){
                            id = entry.getKey();
                            System.out.println(id+" "+name+" "+content);
                            p.setText(content);
                            HTMLDocument doc = (HTMLDocument)p.getDocument();
                            Note note = CurrentProject.getNoteList().createNoteForDate(CurrentDate.get());
                    note.setTitle(name);
                            note.setId(Util.generateId());
                    CurrentStorage.get().storeNote(note, doc);
                    }
                    workPanel.dailyItemsPanel.notesControlPane.refresh();
                    
            }catch(Exception exc){
                    exc.printStackTrace();
            }
        }

}