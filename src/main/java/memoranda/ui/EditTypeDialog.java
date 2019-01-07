package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
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

/*$Id: EditTypeDialog.java,v 1.9 2005/07/05 08:17:24 alexeya Exp $*/
public class EditTypeDialog extends JDialog {
    JButton cancelB = new JButton();
    JPanel buttonsPanel = new JPanel();
    FlowLayout flowLayout7 = new FlowLayout();
    public JLabel header = new JLabel();
    JPanel jPanel1 = new JPanel();
    JPanel dialogTitlePanel = new JPanel();
    JButton okB = new JButton();
    JPanel areaPanel = new JPanel();
    JPanel mPanel = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    Border border1;
    TitledBorder titledBorder1;
    public JTextField extField = new JTextField();
    BorderLayout borderLayout4 = new BorderLayout();
    JLabel jLabel1 = new JLabel();
    Border border2;
    TitledBorder titledBorder2;
    public JTextField descField = new JTextField();
    JPanel jPanel4 = new JPanel();
    BorderLayout borderLayout6 = new BorderLayout();
    Border border3;
    TitledBorder titledBorder3;
    BorderLayout borderLayout1 = new BorderLayout();
    Border border4;
    TitledBorder titledBorder4;
    public SetApplicationPanel appPanel = new SetApplicationPanel();
    Border border5;
    TitledBorder titledBorder5;
    Border border6;
    TitledBorder titledBorder6;
    public boolean CANCELLED = true;
    JPanel jPanel5 = new JPanel();
    Border border7;
    TitledBorder titledBorder7;
    String[] mimes = { "application", "audio", "image", "text", "video" };
    public String iconPath = "";
    BorderLayout borderLayout7 = new BorderLayout();
    BorderLayout borderLayout5 = new BorderLayout();
    JLabel iconLabel = new JLabel();
    JButton setIconB = new JButton();
    JPanel jPanel3 = new JPanel();
    Border border8;
    Border border9;

    public EditTypeDialog(Frame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();
            pack();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    void jbInit() throws Exception {
	this.setResizable(false);
        border1 = BorderFactory.createLineBorder(Color.gray, 1);
        titledBorder1 = new TitledBorder(BorderFactory.createEmptyBorder(), Local.getString("File type extensions"));
        border2 = BorderFactory.createLineBorder(Color.gray, 1);
        titledBorder2 = new TitledBorder(border2, Local.getString("Description"));
        border3 = BorderFactory.createLineBorder(Color.gray, 1);
        titledBorder3 = new TitledBorder(BorderFactory.createEmptyBorder(), Local.getString("Command line"));
        border4 = BorderFactory.createEmptyBorder();
        titledBorder4 = new TitledBorder(BorderFactory.createEmptyBorder(), Local.getString("Description"));
        border5 = BorderFactory.createLineBorder(Color.gray, 1);
        titledBorder5 = new TitledBorder(border5, Local.getString("Application"));
        border6 = BorderFactory.createEmptyBorder();
        titledBorder6 = new TitledBorder(BorderFactory.createEmptyBorder(), Local.getString("Icon")+":");
        border7 = BorderFactory.createEmptyBorder();
        titledBorder7 = new TitledBorder(BorderFactory.createEmptyBorder(), Local.getString("MIME-type")+":");
        border8 = BorderFactory.createLineBorder(Color.gray, 1);
        border9 = BorderFactory.createEmptyBorder(0, 5, 0, 5);
        buttonsPanel.setLayout(flowLayout1);
        cancelB.setMaximumSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setText(Local.getString("Cancel"));
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
        flowLayout7.setAlignment(FlowLayout.LEFT);        
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Resource type"));
        header.setIcon(new ImageIcon(main.java.memoranda.ui.EditTypeDialog.class.getResource(
            "/ui/icons/resource48.png")));
        jPanel1.setLayout(borderLayout1);
        dialogTitlePanel.setBackground(Color.WHITE); 
        dialogTitlePanel.setLayout(flowLayout7);
        dialogTitlePanel.setBorder(border9);
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
        areaPanel.setLayout(borderLayout2);
        mPanel.setLayout(borderLayout3);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        borderLayout3.setHgap(5);
        jPanel2.setBorder(titledBorder1);
        jPanel2.setLayout(borderLayout4);
        extField.setMaximumSize(new Dimension(2147483647, 24));
        extField.setMinimumSize(new Dimension(4, 24));
        extField.setPreferredSize(new Dimension(300, 24));
        extField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(CaretEvent e) {
                extField_caretUpdate(e);
            }
        });
        jLabel1.setFont(new java.awt.Font("Dialog", 0, 11));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText(Local.getString("List of file extensions, divided by spaces"));
        descField.setPreferredSize(new Dimension(300, 24));
        descField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(CaretEvent e) {
                descField_caretUpdate(e);
            }
        });
        descField.setMaximumSize(new Dimension(2147483647, 24));
        descField.setMinimumSize(new Dimension(4, 24));
        jPanel4.setBorder(titledBorder4);
        jPanel4.setLayout(borderLayout6);
        jPanel4.setPreferredSize(new Dimension(300, 24));
        appPanel.setBorder(titledBorder5);
        jPanel5.setLayout(borderLayout7);
        iconLabel.setMaximumSize(new Dimension(24, 24));
        iconLabel.setMinimumSize(new Dimension(24, 24));
        iconLabel.setPreferredSize(new Dimension(24, 24));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setIconTextGap(0);
        setIconB.setMaximumSize(new Dimension(160, 24));
        setIconB.setMinimumSize(new Dimension(100, 24));
        setIconB.setText(Local.getString("Set icon"));
        setIconB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setIconB_actionPerformed(e);
            }
        });
        jPanel3.setLayout(borderLayout5);
        jPanel3.setBorder(titledBorder6);
        buttonsPanel.add(okB, null);
        buttonsPanel.add(cancelB, null);
        mPanel.add(areaPanel, BorderLayout.CENTER);
        areaPanel.add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.NORTH);
        jPanel2.add(extField, BorderLayout.CENTER);
        jPanel2.add(jLabel1, BorderLayout.SOUTH);
        mPanel.add(buttonsPanel, BorderLayout.SOUTH);
        this.getContentPane().add(dialogTitlePanel, BorderLayout.NORTH);
        dialogTitlePanel.add(header, null);
        this.getContentPane().add(mPanel, BorderLayout.CENTER);
        jPanel4.add(descField, BorderLayout.CENTER);
        jPanel1.add(jPanel3, BorderLayout.SOUTH);
        areaPanel.add(appPanel, BorderLayout.SOUTH);
        areaPanel.add(jPanel5, BorderLayout.NORTH);
        jPanel3.add(setIconB, BorderLayout.EAST);
        jPanel3.add(iconLabel, BorderLayout.CENTER);
        jPanel1.add(jPanel4, BorderLayout.CENTER);
    }

    void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    void okB_actionPerformed(ActionEvent e) {
        CANCELLED = false;
        this.dispose();
    }

    void extField_caretUpdate(CaretEvent e) {
        checkOkEnabled();
    }

    void descField_caretUpdate(CaretEvent e) {}

    void checkOkEnabled() {
        okB.setEnabled((extField.getText().length() > 0));
    }

    void setIconB_actionPerformed(ActionEvent e) {
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
        chooser.setPreferredSize(new Dimension(550, 375));
        chooser.setFileHidingEnabled(false);
        chooser.setDialogTitle(Local.getString("Choose icon file"));
        //chooser.setAcceptAllFileFilterUsed(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setCurrentDirectory(
            new File(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/mimetypes").getPath()));
        /*if (System.getProperty("os.name").startsWith("Win")) {
            chooser.setFileFilter(new AllFilesFilter(AllFilesFilter.ICO));
            chooser.setCurrentDirectory(new File("C:\\Program Files"));
        }
        else */
        chooser.addChoosableFileFilter(new main.java.memoranda.ui.htmleditor.filechooser.ImageFilter());
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                iconLabel.setIcon(new ImageIcon(chooser.getSelectedFile().getPath()));
           }
            catch (Exception ex) {
		    //ex.printStackTrace();
	    }
            finally {
                iconPath = chooser.getSelectedFile().getPath();
            }

        }
    }

}