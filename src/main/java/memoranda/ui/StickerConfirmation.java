package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.text.DateFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.util.Context;
import main.java.memoranda.util.Local;


public class StickerConfirmation extends JDialog {
        public boolean CANCELLED = true;
        JPanel panel1 = new JPanel();
        BorderLayout borderLayout1 = new BorderLayout();
        BorderLayout borderLayout2 = new BorderLayout();
        JButton cancelButton = new JButton();
        JButton okButton = new JButton();
        JPanel bottomPanel = new JPanel();
        JPanel topPanel = new JPanel();
        JLabel header = new JLabel();
        JPanel jPanel1 = new JPanel();
        JLabel jLabel1 = new JLabel();
        
        Border border1;
        Border border2;
        
        public StickerConfirmation(Frame frame) {
                super(frame, Local.getString("Sticker"), true);
                try {
                        jbInit();
                        pack();
                } catch (Exception ex) {
                        new ExceptionDialog(ex);
                }
        }

        public StickerConfirmation() {
                this(null);
        }
        void jbInit() throws Exception {
                border1 =
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createEtchedBorder(
                                        Color.white,
                                        new Color(156, 156, 158)),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5));
                border2 = BorderFactory.createEmptyBorder(5, 0, 5, 0);
                panel1.setLayout(borderLayout1);
                this.getContentPane().setLayout(borderLayout2);
                cancelButton.setMaximumSize(new Dimension(100, 25));
                cancelButton.setMinimumSize(new Dimension(100, 25));
                cancelButton.setPreferredSize(new Dimension(100, 25));
                cancelButton.setText(Local.getString("Cancel"));
                cancelButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                cancelButton_actionPerformed(e);
                        }
                });
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
                
                bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                topPanel.setBorder(new EmptyBorder(new Insets(0, 5, 0, 5)));
                topPanel.setBackground(Color.WHITE);

                header.setFont(new java.awt.Font("Dialog", 0, 20));
                header.setForeground(new Color(0, 0, 124));
                header.setText(Local.getString("Sticker"));
                header.setIcon(new ImageIcon(main.java.memoranda.ui.StickerDialog.class.getResource(
            "/ui/icons/sticker48.png")));


                jLabel1.setText(Local.getString("DELETE THIS STICKER?"));
                panel1.setBorder(border1);
                jPanel1.setBorder(border2);
                getContentPane().add(panel1, BorderLayout.CENTER);
                panel1.add(jPanel1, BorderLayout.SOUTH);
                this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
                bottomPanel.add(okButton);
                bottomPanel.add(cancelButton);
                this.getContentPane().add(topPanel, BorderLayout.NORTH);
                topPanel.add(header);
                jPanel1.add(jLabel1, BorderLayout.WEST);
                
        }
        
        
        
        void cancelButton_actionPerformed(ActionEvent e) {
                this.dispose();
        }

        void okButton_actionPerformed(ActionEvent e) {
                CANCELLED = false;
                this.dispose();
        }

        
}