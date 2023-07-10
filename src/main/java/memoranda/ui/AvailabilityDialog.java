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
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.memoranda.User;
import main.java.memoranda.UserList;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.util.Local;

/*$Id: AvailabilityDialog.java,v 1.28 2005/02/19 10:06:25 rawsushi Exp $*/
public class AvailabilityDialog extends JDialog implements WindowListener {	
    HashMap<String, User> users = UserList.users;
    User user;
    public boolean CANCELLED = false;
    JPanel topPanel = new JPanel(new BorderLayout());
    JPanel bottomPanel = new JPanel(new BorderLayout());
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    public JLabel header = new JLabel();
    JPanel eventPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc;
 
    
    JLabel lblSun = new JLabel();
    public JSpinner sunSpinStart;
    public JSpinner sunSpinEnd;
    JLabel lblMon = new JLabel();
    public JSpinner monSpinStart;
    public JSpinner monSpinEnd;
    JLabel lblTues = new JLabel();
    public JSpinner tuesSpinStart;
    public JSpinner tuesSpinEnd;
    JLabel lblWed = new JLabel();
    public JSpinner wedSpinStart;
    public JSpinner wedSpinEnd;
    JLabel lblThurs = new JLabel();
    public JSpinner thursSpinStart;
    public JSpinner thursSpinEnd;
    JLabel lblFri = new JLabel();
    public JSpinner friSpinStart;
    public JSpinner friSpinEnd;
    JLabel lblSat = new JLabel();
    public JSpinner satSpinStart;
    public JSpinner satSpinEnd;
    
    
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    JButton okB = new JButton();
    JButton cancelB = new JButton();
    
    public AvailabilityDialog(Frame frame, String title) {
        super(frame, title, true);
        for (User u : users.values()) {
            if (u.loginStatus()) {
               user = u;
            }
         }
        try {
            jbInit();
            pack();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
        super.addWindowListener(this);
    }

    void jbInit() throws Exception {
    	this.setResizable(false);
    	//Set spinners
    	sunSpinStart = new JSpinner(new SpinnerNumberModel(user.getStart(0), 0, 23, 1));
        sunSpinEnd = new JSpinner(new SpinnerNumberModel(user.getEnd(0), 0, 23, 1));
        monSpinStart = new JSpinner(new SpinnerNumberModel(user.getStart(1), 0, 23, 1));
        monSpinEnd = new JSpinner(new SpinnerNumberModel(user.getEnd(1), 0, 23, 1));
        tuesSpinStart = new JSpinner(new SpinnerNumberModel(user.getStart(2), 0, 23, 1));
        tuesSpinEnd = new JSpinner(new SpinnerNumberModel(user.getEnd(2), 0, 23, 1));
        wedSpinStart = new JSpinner(new SpinnerNumberModel(user.getStart(3), 0, 23, 1));
        wedSpinEnd = new JSpinner(new SpinnerNumberModel(user.getEnd(3), 0, 23, 1));
        thursSpinStart = new JSpinner(new SpinnerNumberModel(user.getStart(4), 0, 23, 1));
        thursSpinEnd = new JSpinner(new SpinnerNumberModel(user.getEnd(4), 0, 23, 1));
        friSpinStart = new JSpinner(new SpinnerNumberModel(user.getStart(5), 0, 23, 1));
        friSpinEnd = new JSpinner(new SpinnerNumberModel(user.getEnd(5), 0, 23, 1));
        satSpinStart = new JSpinner(new SpinnerNumberModel(user.getStart(6), 0, 23, 1));
        satSpinEnd = new JSpinner(new SpinnerNumberModel(user.getEnd(6), 0, 23, 1));
    	
        // Build headerPanel
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Set Availability"));
        header.setIcon(new ImageIcon(main.java.memoranda.ui.AvailabilityDialog.class.getResource(
            "/ui/icons/rhrTrainerIcon.png")));
        headerPanel.add(header);
        
        // Build eventPanel
        lblSun.setText(Local.getString("Sunday"));
        lblSun.setMinimumSize(new Dimension(60, 24));
        lblMon.setText(Local.getString("Monday"));
        lblMon.setMinimumSize(new Dimension(60, 24));
        lblTues.setText(Local.getString("Tuesday"));
        lblTues.setMinimumSize(new Dimension(60, 24));
        lblWed.setText(Local.getString("Wednesday"));
        lblWed.setMinimumSize(new Dimension(60, 24));
        lblThurs.setText(Local.getString("Thursday"));
        lblThurs.setMinimumSize(new Dimension(60, 24));
        lblFri.setText(Local.getString("Friday"));
        lblFri.setMinimumSize(new Dimension(60, 24));
        lblSat.setText(Local.getString("Saturday"));
        lblSat.setMinimumSize(new Dimension(60, 24));
        
        gbc = new GridBagConstraints();        
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(lblSun, gbc);
        sunSpinStart.setPreferredSize(new Dimension(35, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(sunSpinStart, gbc);
        sunSpinEnd.setPreferredSize(new Dimension(35, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 2; gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(sunSpinEnd, gbc);
        
        gbc = new GridBagConstraints();        
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(lblMon, gbc);
        monSpinStart.setPreferredSize(new Dimension(35, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(monSpinStart, gbc);
        monSpinEnd.setPreferredSize(new Dimension(35, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 2; gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(monSpinEnd, gbc);
        
        gbc = new GridBagConstraints();        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(lblTues, gbc);
        tuesSpinStart.setPreferredSize(new Dimension(35, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(tuesSpinStart, gbc);
        tuesSpinEnd.setPreferredSize(new Dimension(35, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 2; gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(tuesSpinEnd, gbc);
        
        gbc = new GridBagConstraints();        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(lblWed, gbc);
        wedSpinStart.setPreferredSize(new Dimension(35, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(wedSpinStart, gbc);
        wedSpinEnd.setPreferredSize(new Dimension(35, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 2; gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(wedSpinEnd, gbc);
        
        gbc = new GridBagConstraints();        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(lblThurs, gbc);
        thursSpinStart.setPreferredSize(new Dimension(35, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 4;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(thursSpinStart, gbc);
        thursSpinEnd.setPreferredSize(new Dimension(35, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 2; gbc.gridy = 4;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(thursSpinEnd, gbc);
        
        gbc = new GridBagConstraints();        
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(lblFri, gbc);
        friSpinStart.setPreferredSize(new Dimension(35, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 5;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(friSpinStart, gbc);
        friSpinEnd.setPreferredSize(new Dimension(35, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 2; gbc.gridy = 5;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(friSpinEnd, gbc);
        
        gbc = new GridBagConstraints();        
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(lblSat, gbc);
        satSpinStart.setPreferredSize(new Dimension(35, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 6;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(satSpinStart, gbc);
        satSpinEnd.setPreferredSize(new Dimension(35, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 2; gbc.gridy = 6;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(satSpinEnd, gbc);

        
        // Build ButtonsPanel
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
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
        cancelB.setText(Local.getString("Cancel"));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setMaximumSize(new Dimension(100, 26));
        buttonsPanel.add(okB);
        buttonsPanel.add(cancelB);
        
        // Finally build the Dialog
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(eventPanel, BorderLayout.SOUTH);
        bottomPanel.add(buttonsPanel, BorderLayout.SOUTH);
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    void okB_actionPerformed(ActionEvent e) {
        
        int [][] availability = new int[7][2];
        availability[0][0] = (Integer) sunSpinStart.getValue();
        availability[0][1] = (Integer) sunSpinEnd.getValue();
        availability[1][0] = (Integer) monSpinStart.getValue();
        availability[1][1] = (Integer) monSpinEnd.getValue();
        availability[2][0] = (Integer) tuesSpinStart.getValue();
        availability[2][1] = (Integer) tuesSpinEnd.getValue();
        availability[3][0] = (Integer) wedSpinStart.getValue();
        availability[3][1] = (Integer) wedSpinEnd.getValue();
        availability[4][0] = (Integer) thursSpinStart.getValue();
        availability[4][1] = (Integer) thursSpinEnd.getValue();
        availability[5][0] = (Integer) friSpinStart.getValue();
        availability[5][1] = (Integer) friSpinEnd.getValue();
        availability[6][0] = (Integer) satSpinStart.getValue();
        availability[6][1] = (Integer) satSpinEnd.getValue();
 
        User user = null;
        
        for (User u : users.values()) {
            if (u.loginStatus()) {
               user = u;
            }
         }
        
        if(user != null) {
            user.setAvailability(availability);
        }
        this.dispose();
    }

    void cancelB_actionPerformed(ActionEvent e) {
        CANCELLED = true;
        this.dispose();
    }

    
    public void windowOpened( WindowEvent e ) {}

    public void windowClosing( WindowEvent e ) {
        CANCELLED = true;
        this.dispose();
    }
	
    public void windowClosed( WindowEvent e ) {}

	public void windowIconified( WindowEvent e ) {}

	public void windowDeiconified( WindowEvent e ) {}

	public void windowActivated( WindowEvent e ) {}

	public void windowDeactivated( WindowEvent e ) {}

}