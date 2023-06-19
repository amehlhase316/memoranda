package main.java.memoranda.ui;

import main.java.memoranda.Bus;
import main.java.memoranda.Driver;
import main.java.memoranda.util.Local;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BusDialog extends JDialog {
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
    JTextField seatField = new JTextField();
    JTextField idField = new JTextField();
    JTextField phoneField = new JTextField();
    Border border8;

    JPanel idFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel idLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel idLabel = new JLabel();
    JPanel seatsLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel seatsLabel = new JLabel();
    JPanel seatsFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    Bus tempBus;

    public BusDialog(Frame frame, String title){
        super(frame, title, true);
        try{
            jbInit();
            pack();
        }
        catch(Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    void jbInit() throws Exception {
        this.setResizable(false);
        this.setSize(new Dimension(430, 300));
        border1 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        border2 = BorderFactory.createEtchedBorder(Color.white,
                new Color(142, 142, 142));
        border3 = new TitledBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0),
                Local.getString("Bus ID"), TitledBorder.LEFT, TitledBorder.BELOW_TOP);
        border4 = BorderFactory.createEmptyBorder(0, 5, 0, 5);
        border8 = BorderFactory.createEtchedBorder(Color.white,
                new Color(178, 178, 178));

        cancelButton.setMaximumSize(new Dimension(100, 26));
        cancelButton.setMinimumSize(new Dimension(100, 26));
        cancelButton.setPreferredSize(new Dimension(100, 26));
        cancelButton.setText(Local.getString("Cancel"));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelBtn_actionPerformed(e);
            }
        });

        okayButton.setMaximumSize(new Dimension(100, 26));
        okayButton.setMinimumSize(new Dimension(100, 26));
        okayButton.setPreferredSize(new Dimension(100, 26));
        okayButton.setText(Local.getString("Ok"));
        okayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okayBtn_ActionPerformed(e);
            }
        });

        this.getRootPane().setDefaultButton(okayButton);
        mPanel.setBorder(border1);
        areaPanel.setBorder(border2);
        dialogTitlePanel.setBackground(Color.WHITE);
        dialogTitlePanel.setBorder(border4);
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Bus"));
        header.setIcon(new ImageIcon(DriverDialog.class.getResource( "/ui/icons/task48.png")));

        GridBagLayout gbLayout = (GridBagLayout) jPanel8.getLayout();
        jPanel8.setBorder(border3);

        seatField.setBorder(border8);
        seatField.setPreferredSize(new Dimension(375, 24));
        GridBagConstraints seatFieldConstraints = new GridBagConstraints();
        seatFieldConstraints.gridwidth = GridBagConstraints.REMAINDER;
        seatFieldConstraints.weighty = 1;
        gbLayout.setConstraints(seatField,seatFieldConstraints);

        idLabel.setText(Local.getString("ID Number"));
        idLabel.setMinimumSize(new Dimension(60, 16));
        idLabel.setMaximumSize(new Dimension(100, 16));

        seatsLabel.setText(Local.getString("Number of Seats"));
        seatsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        seatsLabel.setMaximumSize(new Dimension(270, 16));

        getContentPane().add(mPanel);
        mPanel.add(areaPanel, BorderLayout.CENTER);
        mPanel.add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.add(okayButton, null);
        buttonsPanel.add(cancelButton, null);
        this.getContentPane().add(dialogTitlePanel, BorderLayout.NORTH);
        dialogTitlePanel.add(header, null);
        areaPanel.add(jPanel8, BorderLayout.NORTH);


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


    }

    void okayBtn_ActionPerformed(ActionEvent e) {
        CANCELLED = false;
        int id = Integer.valueOf(idField.getText());
        int seats = Integer.valueOf(seatField.getText());
        tempBus = new Bus(id, seats);
        this.dispose();
    }

    void cancelBtn_actionPerformed(ActionEvent e) {
        this.dispose();
    }
}



