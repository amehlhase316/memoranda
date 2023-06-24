package main.java.memoranda.ui;

import java.awt.*;
import javax.swing.*;

import main.java.memoranda.util.*;

import java.awt.event.*;
import java.io.*;

/*$Id: ExceptionDialog.java,v 1.2 2004/10/18 19:09:10 ivanrise Exp $*/
public class ExceptionDialog extends JDialog {
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  private final JPanel jPanel1 = new JPanel();
  private final JLabel jLabel1 = new JLabel();
  private final JPanel jPanel2 = new JPanel();
  private final JLabel jLabel2 = new JLabel();
  private final BorderLayout borderLayout2 = new BorderLayout();
  private final BorderLayout borderLayout3 = new BorderLayout();
  private final JLabel descLabel = new JLabel();

  private final String description;
  private final String tip;
  private final String trace;
  private final JPanel jPanel3 = new JPanel();
  private final JScrollPane jScrollPane1 = new JScrollPane();
  private final JTextArea traceTextArea = new JTextArea();
  private final JButton reportB = new JButton();
  private final JButton closeB = new JButton();
  private final FlowLayout flowLayout1 = new FlowLayout();
  private final JPanel jPanel4 = new JPanel();
  private final JButton copyB = new JButton();
  private final BorderLayout borderLayout4 = new BorderLayout();
  
  private final Frame owner;
    
  public ExceptionDialog(Exception exc, String description, String tip) {
    super(App.getFrame(), "Problem", true);
    exc.printStackTrace();
    owner = App.getFrame();
    if ((description != null) && (description.length() > 0))        
        this.description = description;
    else if (exc.getMessage() != null)
        this.description = exc.getMessage();
    else
        this.description = "Unknown error";
    this.tip = tip;
    StringWriter sw = new StringWriter();
    exc.printStackTrace(new PrintWriter(sw));
    this.trace = sw.toString();
    try {
      jbInit();      
      setVisible(true);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public ExceptionDialog(Exception exc) {
      this(exc, "", "");
  }

  public ExceptionDialog() {
    this(null, "", "");
  }

  private void jbInit() throws Exception {    
    panel1.setLayout(borderLayout1);    
    jPanel1.setBackground(Color.white);
    jPanel1.setLayout(borderLayout3);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 16));
    jLabel1.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel1.setHorizontalTextPosition(SwingConstants.RIGHT);
    jLabel1.setText("Problem occured");
    jLabel1.setIcon(new ImageIcon(main.java.memoranda.ui.ExceptionDialog.class.getResource(
            "/ui/icons/error.png")));
        
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 11));
    jLabel2.setText("<html>An internal exception occured. It is may be a result of bug in the " +
    "program, corrupted data, incorrect configuration or hardware failure.<br><br>" +
    "Click on <b>Report bug..</b> button to submit a bug to the Memoranda bugs tracker on SourceForge.net </html>");
    jPanel2.setLayout(borderLayout2);
    jPanel2.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    borderLayout3.setVgap(5);
    String labelText = "<html><b>Description:</b><br>"+description;
    if ((tip != null) && (tip.length() > 0))
      labelText = labelText + "<br><br><b>Tip:</b><br>"+tip;
    labelText = labelText + "<br><br><b>Stack trace:</b></html>";
    descLabel.setText(labelText);
    descLabel.setFont(new java.awt.Font("Dialog", 0, 12));
    jScrollPane1.setEnabled(false);
    reportB.setMaximumSize(new Dimension(120, 25));
    reportB.setMinimumSize(new Dimension(120, 25));
    reportB.setPreferredSize(new Dimension(120, 25));
    reportB.setText("Report bug...");
    reportB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        reportB_actionPerformed(e);
      }
    });
    closeB.setMaximumSize(new Dimension(120, 25));
    closeB.setMinimumSize(new Dimension(120, 25));
    closeB.setPreferredSize(new Dimension(120, 25));
    closeB.setText("Close");
    closeB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        closeB_actionPerformed(e);
      }
    });
    this.getRootPane().setDefaultButton(closeB);
    jPanel3.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    copyB.setText("Copy to clipboard");
    copyB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        copyB_actionPerformed(e);
      }
    });
    copyB.setHorizontalAlignment(SwingConstants.RIGHT);
    jPanel4.setLayout(borderLayout4);
    traceTextArea.setText(trace);
    traceTextArea.setEditable(false);
    borderLayout1.setVgap(5);   
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.NORTH);
    jPanel1.add(jLabel1,  BorderLayout.NORTH);
    jPanel1.add(jLabel2, BorderLayout.CENTER);
    panel1.add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(descLabel, BorderLayout.NORTH);
    jPanel2.add(jScrollPane1, BorderLayout.CENTER);
    jPanel2.add(jPanel4,  BorderLayout.SOUTH);
    jPanel4.add(copyB,  BorderLayout.WEST);
    jScrollPane1.getViewport().add(traceTextArea, null);
    panel1.add(jPanel3,  BorderLayout.SOUTH);
    jPanel3.add(closeB, null);
    jPanel3.add(reportB, null);
    Dimension dlgSize = new Dimension(400, 500);
    this.setSize(dlgSize); 
    if (owner != null) {
        Dimension frmSize = owner.getSize();
        Point loc = owner.getLocation();
        this.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    }
  }

  void copyB_actionPerformed(ActionEvent e) {
    traceTextArea.selectAll();
    traceTextArea.copy();
    traceTextArea.setSelectionEnd(0);
  }

  void closeB_actionPerformed(ActionEvent e) {
    this.dispose();
  }

  void reportB_actionPerformed(ActionEvent e) {
      Util.runBrowser(App.BUGS_TRACKER_URL);
  }
}