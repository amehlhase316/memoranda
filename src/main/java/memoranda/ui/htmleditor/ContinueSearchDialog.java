package main.java.memoranda.ui.htmleditor;

import main.java.memoranda.ui.htmleditor.util.Local;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ContinueSearchDialog extends JPanel {
    public volatile boolean isPaused = true;  // New variable to control pausing and resuming of thread
    public boolean cont = false;
    public boolean cancel = false;
    JPanel panel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    FlowLayout flowLayout1 = new FlowLayout();
    JButton cancelB = new JButton();
    JButton continueB = new JButton();
    JPanel buttonsPanel = new JPanel();
    JLabel jLabel1 = new JLabel();
    JTextField textF = new JTextField();
    String text;
    Thread thread;

    public ContinueSearchDialog (Thread t, String txt) {
        try {
            text = txt;
            thread = t;
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit () throws Exception {

        this.setLayout(borderLayout1);
        textF.setEditable(false);
        textF.setText(text);
        cancelB.setMaximumSize(new Dimension(120, 26));
        cancelB.setMinimumSize(new Dimension(80, 26));
        cancelB.setPreferredSize(new Dimension(120, 26));
        cancelB.setText(Local.getString("Cancel"));
        cancelB.setFocusable(false);
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed (ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
        continueB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed (ActionEvent e) {
                continueB_actionPerformed(e);
            }
        });
        continueB.setText(Local.getString("Find next"));
        continueB.setPreferredSize(new Dimension(120, 26));
        continueB.setMinimumSize(new Dimension(80, 26));
        continueB.setMaximumSize(new Dimension(120, 26));
        continueB.setFocusable(false);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        buttonsPanel.setLayout(flowLayout1);

        jLabel1.setText(" " + Local.getString("Search for") + ":  ");
        jLabel1.setIcon(new ImageIcon(main.java.memoranda.ui.htmleditor.HTMLEditor.class.getResource("/htmleditor/icons/findbig.png")));
        this.add(jLabel1, BorderLayout.WEST);
        this.add(textF, BorderLayout.CENTER);
        buttonsPanel.add(continueB, null);
        buttonsPanel.add(cancelB, null);
        this.add(buttonsPanel, BorderLayout.EAST);
    }

    void cancelB_actionPerformed (ActionEvent e) {
        cont = true;
        cancel = true;
        isPaused = false;  // Set the shared variable to false to 'resume' the thread
    }

    void continueB_actionPerformed (ActionEvent e) {
        cont = true;
        isPaused = false;  // Set the shared variable to false to 'resume' the thread
    }
}
