package main.java.memoranda.ui;

import main.java.memoranda.Driver;
import main.java.memoranda.util.Local;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class DriverDialog extends JDialog {
    JPanel mPanel = new JPanel(new BorderLayout());
    JPanel areaPanel = new JPanel(new BorderLayout());
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton cancelButton = new JButton();
    JButton okayButton = new JButton();
    Border border1;
    Border border2;
    JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel header = new JLabel();
    public boolean CANCELLED = true;
    JPanel jPanel8 = new JPanel(new GridBagLayout());
    Border border3;
    Border border4;
    JPanel gridPanel = new JPanel(new GridLayout(2, 2));
    JTextField nameField = new JTextField();
    JTextField idField = new JTextField();
    JTextField phoneField = new JTextField();
    Border border8;
    JPanel idFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel idLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel idLabel = new JLabel();
    JPanel phoneLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel phoneNumberLabel = new JLabel();
    JPanel phoneFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    Driver tempDriver;


    public DriverDialog(Frame frame, String title) {
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
	this.setSize(new Dimension(430,300));
        border1 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        border2 = BorderFactory.createEtchedBorder(Color.white, 
            new Color(142, 142, 142));
        border3 = new TitledBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0), 
        Local.getString("Driver Name"), TitledBorder.LEFT, TitledBorder.BELOW_TOP);
        border4 = BorderFactory.createEmptyBorder(0, 5, 0, 5);
        border8 = BorderFactory.createEtchedBorder(Color.white, 
            new Color(178, 178, 178));
        cancelButton.setMaximumSize(new Dimension(100, 26));
        cancelButton.setMinimumSize(new Dimension(100, 26));
        cancelButton.setPreferredSize(new Dimension(100, 26));
        cancelButton.setText(Local.getString("Cancel"));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });

        okayButton.setMaximumSize(new Dimension(100, 26));
        okayButton.setMinimumSize(new Dimension(100, 26));
        okayButton.setPreferredSize(new Dimension(100, 26));
        okayButton.setText(Local.getString("Ok"));
        okayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okayButton_ActionPerformed(e);
            }
        });
        
        this.getRootPane().setDefaultButton(okayButton);
        mPanel.setBorder(border1);
        areaPanel.setBorder(border2);
        dialogTitlePanel.setBackground(Color.WHITE);
        dialogTitlePanel.setBorder(border4);
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Driver"));
        header.setIcon(new ImageIcon(DriverDialog.class.getResource( "/ui/icons/task48.png")));
        
        GridBagLayout gbLayout = (GridBagLayout) jPanel8.getLayout();
        jPanel8.setBorder(border3);
				
        nameField.setBorder(border8);
        nameField.setPreferredSize(new Dimension(375, 24));
        GridBagConstraints nameFieldConstraints = new GridBagConstraints();
        nameFieldConstraints.gridwidth = GridBagConstraints.REMAINDER;
        nameFieldConstraints.weighty = 1;
        gbLayout.setConstraints(nameField,nameFieldConstraints);

        idField.setBorder(border8);
        idField.setPreferredSize(new Dimension(55, 24));
        GridBagConstraints idFieldConstraints = new GridBagConstraints();
        idFieldConstraints.gridwidth = GridBagConstraints.REMAINDER;
        idFieldConstraints.weighty = 1;
        gbLayout.setConstraints(idField,idFieldConstraints);

        phoneField.setBorder(border8);
        phoneField.setPreferredSize(new Dimension(155, 24));
        GridBagConstraints phoneFieldConstraints = new GridBagConstraints();
        phoneFieldConstraints.gridwidth = GridBagConstraints.REMAINDER;
        phoneFieldConstraints.weighty = 1;
        gbLayout.setConstraints(phoneField,phoneFieldConstraints);

        idLabel.setText(Local.getString("ID Number"));
        idLabel.setMinimumSize(new Dimension(60, 16));
        idLabel.setMaximumSize(new Dimension(100, 16));

        phoneNumberLabel.setMaximumSize(new Dimension(270, 16));
        phoneNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        phoneNumberLabel.setText(Local.getString("Phone Number"));

        getContentPane().add(mPanel);
        mPanel.add(areaPanel, BorderLayout.CENTER);
        mPanel.add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.add(okayButton, null);
        buttonsPanel.add(cancelButton, null);
        this.getContentPane().add(dialogTitlePanel, BorderLayout.NORTH);
        dialogTitlePanel.add(header, null);
        areaPanel.add(jPanel8, BorderLayout.NORTH);
        jPanel8.add(nameField, null);
        areaPanel.add(gridPanel, BorderLayout.CENTER);

        //Add the items to the gridPanel
        gridPanel.add(idLabelPanel, null);
        idLabelPanel.add(idLabel, null);
        gridPanel.add(phoneLabelPanel, null);
        phoneLabelPanel.add(phoneNumberLabel, null);
        gridPanel.add(idFieldPanel, null);
        idFieldPanel.add(idField, null);
        gridPanel.add(phoneField, phoneFieldConstraints);
        gridPanel.add(phoneFieldPanel, null);
        phoneFieldPanel.add(phoneField,null);

        //Listener to ensure only integers with length no greater than 6 characters are typed
        idField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(idField.getText().length() >= 6)
                    e.consume();
            }

            @Override
            public void keyPressed(KeyEvent e) {            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(!idField.getText().matches("\\d"))
                    idField.setText(idField.getText().replaceAll("\\D", ""));
            }
        });

        nameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(nameField.getText().length() >= 35)
                    e.consume();
            }

            @Override
            public void keyPressed(KeyEvent e) {       }

            @Override
            public void keyReleased(KeyEvent e) {      }
        });

        phoneField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(phoneField.getText().length() >= 14)
                    e.consume();
                if(phoneField.getText().length() == 0)
                    phoneField.setText("(");
                if(phoneField.getText().length() == 4)
                    phoneField.setText(phoneField.getText() + ") ");
                if(phoneField.getText().length() == 9)
                    phoneField.setText(phoneField.getText() + "-");
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(phoneField.getText().length() >= 14)
                    e.consume();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(phoneField.getText().length() >= 14)
                    e.consume();
            }
        });
    }

    void okayButton_ActionPerformed(ActionEvent e) {
	    CANCELLED = false;
        tempDriver = new Driver(idField.getText(), nameField.getText(), phoneField.getText());
        this.dispose();
    }

    void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }
}