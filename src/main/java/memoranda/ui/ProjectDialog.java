package main.java.memoranda.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.memoranda.Project;
import main.java.memoranda.ProjectManager;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;

/*$Id: ProjectDialog.java,v 1.26 2004/10/18 19:09:10 ivanrise Exp $*/
public class ProjectDialog extends JDialog {
    public boolean CANCELLED = true;
    boolean ignoreStartChanged = false;
    boolean ignoreEndChanged = false;
    CalendarFrame endCalFrame = new CalendarFrame();
    CalendarFrame startCalFrame = new CalendarFrame();
    GridBagConstraints gbc;
    JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel header = new JLabel();
    JPanel centerPanel = new JPanel(new GridBagLayout());
    JLabel titleLabel = new JLabel();
    public JTextField prTitleField = new JTextField();
    JLabel sdLabel = new JLabel();
    public JSpinner startDate = new JSpinner(new SpinnerDateModel());
    JButton sdButton = new JButton();
    public JCheckBox endDateChB = new JCheckBox();
    public JSpinner endDate = new JSpinner(new SpinnerDateModel());
    JButton edButton = new JButton();
    //public JCheckBox freezeChB = new JCheckBox();
    JPanel bottomPanel = new JPanel();
    JButton okButton = new JButton();
    JButton cancelButton = new JButton();
    
    public ProjectDialog(Frame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();
            pack();
        }
        catch(Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    void jbInit() throws Exception {
	this.setResizable(false);
        getContentPane().setLayout(new GridBagLayout());
        topPanel.setBorder(new EmptyBorder(new Insets(0, 5, 0, 5)));
        topPanel.setBackground(Color.WHITE);        
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Project"));
        //header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setIcon(new ImageIcon(main.java.memoranda.ui.ProjectDialog.class.getResource(
            "/ui/icons/project48.png")));
        topPanel.add(header);
        
        centerPanel.setBorder(new EtchedBorder());
        titleLabel.setText(Local.getString("Title"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.insets = new Insets(5, 10, 5, 10);
        //gbc.anchor = GridBagConstraints.WEST;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        centerPanel.add(titleLabel, gbc);
        
        //prTitleField.setPreferredSize(new Dimension(270, 20));
        gbc = new GridBagConstraints();
        gbc.gridwidth = 5;
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 5, 0);
        //gbc.anchor = GridBagConstraints.EAST;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(prTitleField, gbc);
        
        sdLabel.setText(Local.getString("Start date"));
        sdLabel.setPreferredSize(new Dimension(70, 20));
        sdLabel.setMinimumSize(new Dimension(70, 20));
        sdLabel.setMaximumSize(new Dimension(70, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 10, 10);
        centerPanel.add(sdLabel, gbc);

        startDate.setPreferredSize(new Dimension(80, 20));
        startDate.setLocale(Local.getCurrentLocale());
		//Added by (jcscoobyrs) on 17-Nov-2003 at 14:24:43 PM
		//---------------------------------------------------
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT);
		startDate.setEditor(new JSpinner.DateEditor(startDate, 
			sdf.toPattern()));
		//---------------------------------------------------
        startDate.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (ignoreStartChanged) return;
                ignoreStartChanged = true;
                Date sd = (Date) startDate.getModel().getValue();
                if (endDate.isEnabled()) {
                  Date ed = (Date) endDate.getModel().getValue();
                  if (sd.after(ed)) {
                    startDate.getModel().setValue(ed);
                    sd = ed;
                  }
                }
                startCalFrame.cal.set(new CalendarDate(sd));
                ignoreStartChanged = false;
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 10, 5);
        centerPanel.add(startDate, gbc);
        
        sdButton.setMinimumSize(new Dimension(20, 20));
        sdButton.setPreferredSize(new Dimension(20, 20));
        sdButton.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/calendar.png")));
        sdButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sdButton_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 2; gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 10, 25);
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(sdButton, gbc);
        
        endDateChB.setForeground(Color.gray);
        endDateChB.setText(Local.getString("End date"));
        endDateChB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                endDateChB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 3; gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(endDateChB, gbc);
        
        endDate.setEnabled(false);
        endDate.setPreferredSize(new Dimension(80, 20));
        endDate.setLocale(Local.getCurrentLocale());
		//Added by (jcscoobyrs) on 17-Nov-2003 at 14:24:43 PM
		//---------------------------------------------------
		endDate.setEditor(new JSpinner.DateEditor(endDate, 
			sdf.toPattern()));
		//---------------------------------------------------
        endDate.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (ignoreEndChanged) return;
                ignoreEndChanged = true;
                Date sd = (Date) startDate.getModel().getValue();
                Date ed = (Date) endDate.getModel().getValue();
                if (sd.after(ed)) {
                    endDate.getModel().setValue(sd);
                    ed = sd;
                }
                endCalFrame.cal.set(new CalendarDate(ed));
                ignoreEndChanged = false;
            }
        });
        //((JSpinner.DateEditor) endDate.getEditor()).setLocale(Local.getCurrentLocale());
        gbc = new GridBagConstraints();
        gbc.gridx = 4; gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(endDate, gbc);
        
        edButton.setEnabled(false);
        edButton.setMinimumSize(new Dimension(20, 20));
        edButton.setMaximumSize(new Dimension(20, 20));
        edButton.setPreferredSize(new Dimension(20, 20));
        edButton.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/calendar.png")));
        edButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                edButton_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 5; gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(edButton, gbc);
        
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        okButton.setMaximumSize(new Dimension(100, 25));
        okButton.setMinimumSize(new Dimension(100, 25));
        okButton.setPreferredSize(new Dimension(100, 25));
        okButton.setText(Local.getString("Ok"));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okButton_actionPerformed(e);
            }
        });
        this.getRootPane().setDefaultButton(okButton);
        cancelButton.setMaximumSize(new Dimension(100, 25));
        cancelButton.setMinimumSize(new Dimension(100, 25));
        cancelButton.setPreferredSize(new Dimension(100, 25));
        cancelButton.setText(Local.getString("Cancel"));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelButton_actionPerformed(e);
            }
        });
        bottomPanel.add(okButton);
        bottomPanel.add(cancelButton);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(topPanel, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        getContentPane().add(centerPanel, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(bottomPanel, gbc);
    
        startCalFrame.cal.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ignoreStartChanged)
                    return;
                startDate.getModel().setValue(startCalFrame.cal.get().getCalendar().getTime());
            }
        });
        endCalFrame.cal.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ignoreEndChanged)
                    return;
                endDate.getModel().setValue(endCalFrame.cal.get().getCalendar().getTime());
            }
        });
    }
    
    void okButton_actionPerformed(ActionEvent e) {
        CANCELLED = false;
        this.dispose();
    }
    
    void cancelButton_actionPerformed(ActionEvent e) {
        this.dispose();
    }
    
    void endDateChB_actionPerformed(ActionEvent e) {
        endDate.setEnabled(endDateChB.isSelected());
        edButton.setEnabled(endDateChB.isSelected());
        if (endDateChB.isSelected()) {
            endDateChB.setForeground(Color.BLACK);
            endDate.getModel().setValue(startDate.getModel().getValue());
        }
        else endDateChB.setForeground(Color.GRAY);
    }
    
    void sdButton_actionPerformed(ActionEvent e) {
        //startCalFrame.setLocation(sdButton.getLocation());
        startCalFrame.setLocation(0, 0);
        startCalFrame.setSize((this.getContentPane().getWidth() / 2), 
            this.getContentPane().getHeight());
        this.getLayeredPane().add(startCalFrame);
        startCalFrame.setTitle(Local.getString("Start date"));
        startCalFrame.show();
    }
    
    void edButton_actionPerformed(ActionEvent e) {
        endCalFrame.setLocation((this.getContentPane().getWidth() / 2),0);
        endCalFrame.setSize((this.getContentPane().getWidth() / 2), 
            this.getContentPane().getHeight());
        this.getLayeredPane().add(endCalFrame);
        endCalFrame.setTitle(Local.getString("End date"));
        endCalFrame.show();
    }
    
    public static void newProject() {
        ProjectDialog dlg = new ProjectDialog(null, Local.getString("New project"));
        
        Dimension dlgSize = dlg.getSize();
        //dlg.setSize(dlgSize);
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        String title = dlg.prTitleField.getText();
        CalendarDate startD = new CalendarDate((Date) dlg.startDate.getModel().getValue());
        CalendarDate endD = null;
        if (dlg.endDateChB.isSelected())
            endD = new CalendarDate((Date) dlg.endDate.getModel().getValue());
        Project prj = ProjectManager.createProject(title, startD, endD);
        /*if (dlg.freezeChB.isSelected())
            prj.freeze();*/
        CurrentStorage.get().storeProjectManager();
    }
}
