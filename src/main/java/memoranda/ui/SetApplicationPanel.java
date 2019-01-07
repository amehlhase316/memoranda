package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;

import main.java.memoranda.util.Local;

/*$Id: SetApplicationPanel.java,v 1.6 2004/04/05 10:05:44 alexeya Exp $*/
public class SetApplicationPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    public JTextField applicationField = new JTextField();
    BorderLayout borderLayout5 = new BorderLayout();
    JButton selectAppBrowseB = new JButton();
    JPanel jPanel3 = new JPanel();
    Border border1;
    TitledBorder titledBorder1;
    public JTextField argumentsField = new JTextField();
    BorderLayout borderLayout6 = new BorderLayout();
    JPanel jPanel4 = new JPanel();
    Border border2;
    TitledBorder titledBorder2;
    JLabel argHelpLabel = new JLabel();
    public File d = null;
    
    public SetApplicationPanel() {
        try {
            jbInit();
        }
        catch(Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    void jbInit() throws Exception {
        border1 = BorderFactory.createEmptyBorder();
        titledBorder1 = new TitledBorder(BorderFactory.createEmptyBorder(),Local.getString("Path to executable"));
        border2 = BorderFactory.createEmptyBorder();
        titledBorder2 = new TitledBorder(border2, Local.getString("Arguments"));
        jPanel3.setLayout(borderLayout5);
        selectAppBrowseB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectAppBrowseB_actionPerformed(e);
            }
        });
        selectAppBrowseB.setText(Local.getString("Browse"));
        applicationField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(CaretEvent e) {
                applicationField_caretUpdate(e);
            }
        });
        applicationField.setPreferredSize(new Dimension(300, 24));
        applicationField.setMinimumSize(new Dimension(4, 24));
        this.setLayout(borderLayout1);
        jPanel3.setBorder(titledBorder1);
        argumentsField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(CaretEvent e) {
                argumentsField_caretUpdate(e);
            }
        });
        argumentsField.setPreferredSize(new Dimension(300, 24));
        argumentsField.setText("$1");
        argumentsField.setMinimumSize(new Dimension(4, 24));
        jPanel4.setBorder(titledBorder2);
        jPanel4.setLayout(borderLayout6);
        argHelpLabel.setFont(new java.awt.Font("Dialog", 0, 11));
        argHelpLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        argHelpLabel.setText(Local.getString("Use $1 as an alias of the file to open"));
        jPanel3.add(applicationField, BorderLayout.CENTER);
        jPanel3.add(selectAppBrowseB, BorderLayout.EAST);
        this.add(jPanel4,  BorderLayout.SOUTH);
        jPanel4.add(argumentsField, BorderLayout.CENTER);
        jPanel4.add(argHelpLabel,  BorderLayout.SOUTH);
        this.add(jPanel3,  BorderLayout.NORTH);
    }
    
    void applicationField_caretUpdate(CaretEvent e) {
        
    }
    
    void selectAppBrowseB_actionPerformed(ActionEvent e) {
        // Fix until Sun's JVM supports more locales...
        UIManager.put("FileChooser.lookInLabelText", Local.getString("Look in:"));
        UIManager.put("FileChooser.upFolderToolTipText", Local.getString("Up One Level"));
        UIManager.put("FileChooser.newFolderToolTipText", Local.getString("Create New Folder"));
        UIManager.put("FileChooser.listViewButtonToolTipText", Local.getString("List"));
        UIManager.put("FileChooser.detailsViewButtonToolTipText", Local.getString("Details"));
        UIManager.put("FileChooser.fileNameLabelText", Local.getString("File Name:"));
        UIManager.put("FileChooser.filesOfTypeLabelText", Local.getString("Files of Type:"));
        UIManager.put("FileChooser.openButtonText", Local.getString("Open"));
        UIManager.put("FileChooser.openButtonToolTipText", Local.getString("Open selected file"));
        UIManager.put("FileChooser.cancelButtonText", Local.getString("Cancel"));
        UIManager.put("FileChooser.cancelButtonToolTipText", Local.getString("Cancel"));
        UIManager.put("FileChooser.acceptAllFileFilterText", Local.getString("All Files") + " (*.*)");
        
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(Local.getString("Path to executable"));
        chooser.setFileHidingEnabled(false);
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (System.getProperty("os.name").startsWith("Win")) {
            chooser.setFileFilter(new AllFilesFilter(AllFilesFilter.EXE));
            chooser.setCurrentDirectory(new File("C:\\Program Files"));
        }
        chooser.setPreferredSize(new Dimension(550, 375));
        /*
            java.io.File lastSel = (java.io.File) Context.get("LAST_SELECTED_IMPORT_FILE");
            if (lastSel != null)
                chooser.setCurrentDirectory(lastSel);
        */
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            applicationField.setText(chooser.getSelectedFile().getPath());
        }
        
        void argumentsField_caretUpdate(CaretEvent e) {
            
        }
}
