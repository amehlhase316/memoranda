package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;

import main.java.memoranda.util.Local;

/*$Id: AddResourceDialog.java,v 1.12 2007/03/20 06:21:46 alexeya Exp $*/
public class AddResourceDialog extends JDialog {
    JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel header = new JLabel();
    ButtonGroup buttonGroup1 = new ButtonGroup();
    JPanel areaPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc;
    public JRadioButton localFileRB = new JRadioButton();
    public JCheckBox projectFileCB = new JCheckBox("Copy file to memoranda", false);
    JLabel jLabel1 = new JLabel();
    public JTextField pathField = new JTextField();
    JButton browseB = new JButton();
    JLabel jLabel2 = new JLabel();
    public JRadioButton inetShortcutRB = new JRadioButton();
    public JTextField urlField = new JTextField();
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
    JButton okB = new JButton();
    JButton cancelB = new JButton();
    public boolean CANCELLED = true;

    public AddResourceDialog(Frame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();
            pack();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
            ex.printStackTrace();
        }
    }

	/**
	 * setup user interface and init dialog
	 */
	 
    void jbInit() throws Exception {
		this.setResizable(false);
        dialogTitlePanel.setBackground(Color.WHITE);
        dialogTitlePanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("New resource"));
        header.setIcon(new ImageIcon(main.java.memoranda.ui.AddResourceDialog.class.getResource(
            "/ui/icons/resource48.png")));
        dialogTitlePanel.add(header);
        this.getContentPane().add(dialogTitlePanel, BorderLayout.NORTH);
        
        buttonGroup1.add(localFileRB);
        buttonGroup1.add(inetShortcutRB);
        localFileRB.setSelected(true);
        localFileRB.setText(Local.getString("Local file"));
        localFileRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                localFileRB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(10, 15, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(localFileRB, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.gridx = 2; gbc.gridy = 0;
        gbc.insets = new Insets(10, 15, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(projectFileCB, gbc);
        
        jLabel1.setText(Local.getString("Path")+": ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(5, 20, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(jLabel1, gbc);
        pathField.setMinimumSize(new Dimension(4, 24));
        pathField.setPreferredSize(new Dimension(250, 24));
        pathField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(CaretEvent e) {
                pathField_caretUpdate(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(pathField, gbc);
        browseB.setText(Local.getString("Browse"));
        browseB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browseB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 2; gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(browseB, gbc);
        inetShortcutRB.setText(Local.getString("Internet shortcut"));
        inetShortcutRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inetShortcutRB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(inetShortcutRB, gbc);
        jLabel2.setText(Local.getString("URL")+":  ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.insets = new Insets(5, 20, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel2, gbc);
        urlField.setMinimumSize(new Dimension(4, 24));
        urlField.setPreferredSize(new Dimension(335, 24));
        urlField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(CaretEvent e) {
                urlField_caretUpdate(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 0, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(urlField, gbc);
        this.getContentPane().add(areaPanel, BorderLayout.CENTER);
        
        okB.setEnabled(false);
        okB.setMaximumSize(new Dimension(100, 26));
        okB.setMinimumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText(Local.getString("Ok"));
        okB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okB_actionPerformed(e);
            }
        });
        this.getRootPane().setDefaultButton(okB);
        cancelB.setMaximumSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setText(Local.getString("Cancel"));
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
        buttonsPanel.add(okB);
        buttonsPanel.add(cancelB);
		enableFields();
        this.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
    }

	/**
	 * set CANCELLED variable to false so we can know the user 
	 * pressed the ok buton and close this dialog.
	 */
	 
    void okB_actionPerformed(ActionEvent e) {
        CANCELLED = false;
		this.dispose();
    }

	/**
	 * close the dialog window
	 */
	 
    void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }

	/**
	 * enable localRB fields. Request focus for the text field 
	 * so the user can start typing and set the pathField text selected
	 */
	 
    void localFileRB_actionPerformed(ActionEvent e) {
		enableFields();
        checkOkEnabled();
		urlField.select(0,0);
		pathField.select(0,pathField.getText().length());
		pathField.requestFocus();
	}

	/**
	 * enable inetShorcutRB fields. Request focus for the text field 
	 * so the user can start typing and set the urlField text selected
	 */
	
    void inetShortcutRB_actionPerformed(ActionEvent e) {
		enableFields();
        checkOkEnabled();
		pathField.select(0,0);
		urlField.select(0,urlField.getText().length());
		urlField.requestFocus();
	}

	/**
	 * setup the JFileChooser so the user can select the resource file
	 */
	 
    void browseB_actionPerformed(ActionEvent e) {
        // Fix until Sun's JVM supports more locales...
        UIManager.put("FileChooser.lookInLabelText", 
            Local.getString("Look in:"));
        UIManager.put("FileChooser.upFolderToolTipText", 
            Local.getString("Up One Level"));
        UIManager.put("FileChooser.newFolderToolTipText", 
            Local.getString("Create New Folder"));
        UIManager.put("FileChooser.listViewButtonToolTipText", 
            Local.getString("List"));
        UIManager.put("FileChooser.detailsViewButtonToolTipText", 
            Local.getString("Details"));
        UIManager.put("FileChooser.fileNameLabelText", 
            Local.getString("File Name:"));
        UIManager.put("FileChooser.filesOfTypeLabelText", 
            Local.getString("Files of Type:"));
        UIManager.put("FileChooser.openButtonText", 
            Local.getString("Open"));
        UIManager.put("FileChooser.openButtonToolTipText", 
            Local.getString("Open selected file"));
        UIManager.put("FileChooser.cancelButtonText", 
            Local.getString("Cancel"));
        UIManager.put("FileChooser.cancelButtonToolTipText", 
            Local.getString("Cancel"));
        
        JFileChooser chooser = new JFileChooser();
        chooser.setFileHidingEnabled(false);
        chooser.setDialogTitle(Local.getString("Add resource"));
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);        
        chooser.setPreferredSize(new Dimension(550, 375));
        /*java.io.File lastSel = (java.io.File) Context.get("LAST_SELECTED_RESOURCE_FILE");
        if (lastSel != null)
            chooser.setCurrentDirectory(lastSel);*/
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        /*try {
            Context.put("LAST_SELECTED_RESOURCE_FILE", chooser.getSelectedFile());
        }
        catch (Exception ex) {}*/
        pathField.setText(chooser.getSelectedFile().getPath());
        checkOkEnabled();
    }

	/**
	 * disable the ok button if pathField is empty
	 */
	 
    void pathField_caretUpdate(CaretEvent e) {
        checkOkEnabled();
    }

	/**
	 * disable the ok button if urlField is empty
	 */
	
    void urlField_caretUpdate(CaretEvent e) {        
        checkOkEnabled();
    }
    
	/**
	 * do not enable the ok button until the text field is not empty.
	 */
	 
    void checkOkEnabled() {        
         okB.setEnabled(
            (localFileRB.isSelected() && pathField.getText().length() > 0) ||
            (inetShortcutRB.isSelected() && urlField.getText().length() > 0)
         );
    }

	/** 
	 * enable and disable fields when user selects the radio buttons options
	 */
	 
	void enableFields() {
		 pathField.setEnabled(localFileRB.isSelected());
		 jLabel1.setEnabled(localFileRB.isSelected());
		 browseB.setEnabled(localFileRB.isSelected());
		 projectFileCB.setEnabled(localFileRB.isSelected());
		 
		 urlField.setEnabled(inetShortcutRB.isSelected());
		 jLabel2.setEnabled(inetShortcutRB.isSelected());
	}
}