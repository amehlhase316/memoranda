package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import main.java.memoranda.util.Configuration;
import main.java.memoranda.util.Local;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URL;


/*$Id: EventNotificationDialog.java,v 1.8 2004/10/18 19:08:56 ivanrise Exp $*/
public class EventNotificationDialog extends JFrame {
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JButton jButton1 = new JButton();
  Border border1;
  Border border2;
  Border border3;
  JPanel jPanel1 = new JPanel();
  JLabel textLabel = new JLabel();
  JLabel timeLabel = new JLabel();
  Border border4;

  public EventNotificationDialog(String title, String time, String text) {
    super();
    this.setTitle(title);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      new ExceptionDialog(ex);
    }
    timeLabel.setText(time);
    timeLabel.setIcon(new ImageIcon(main.java.memoranda.ui.TaskDialog.class.getResource(
            "/ui/icons/event48.png")));
    textLabel.setText(text);
    this.setSize(300,200);
    this.setLocationRelativeTo(null);
    this.setVisible(true);    
    this.toFront();
    this.requestFocus();
    //jButton1.requestFocus();
  }

  public EventNotificationDialog() {
    this("", "", "");
  }
  void jbInit() throws Exception {
    this.setResizable(false);
    this.setIconImage(new ImageIcon(EventNotificationDialog.class.getResource("/ui/icons/jnotes16.png")).getImage());
    this.getContentPane().setBackground(new Color(251, 197, 63));
    border2 = BorderFactory.createEmptyBorder(0,30,0,30);
    border3 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),BorderFactory.createEmptyBorder(0,30,0,30));
    border4 = BorderFactory.createEmptyBorder(10,10,0,10);
    panel1.setLayout(borderLayout1);
    panel1.setBackground(new Color(251, 197, 63));
    
    jButton1.setText(Local.getString("Ok"));
    jButton1.setBounds(150, 415, 95, 30);
    jButton1.setPreferredSize(new Dimension(95, 30));
    jButton1.setBackground(new Color(69, 125, 186));
    jButton1.setForeground(Color.white);
    jButton1.setDefaultCapable(true);
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    panel1.setBorder(border4);
    panel1.setMinimumSize(new Dimension(300, 200));
    panel1.setPreferredSize(new Dimension(300, 200));
    timeLabel.setFont(new java.awt.Font("Dialog", 0, 20));
    timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
    textLabel.setHorizontalAlignment(SwingConstants.CENTER);
    getContentPane().add(panel1);
    panel1.add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(jButton1, null);
    jPanel1.setBackground(new Color(251, 197, 63));
    panel1.add(textLabel, BorderLayout.CENTER);
    panel1.add(timeLabel, BorderLayout.NORTH);
    playSoundNotification();
  }

  void jButton1_actionPerformed(ActionEvent e) {
       this.dispose();
  }
  
  private void playSoundNotification() {
		if (Configuration.get("NOTIFY_SOUND").equals("DISABLED"))
			return;
		if (Configuration.get("NOTIFY_SOUND").equals("BEEP")) {
			java.awt.Toolkit.getDefaultToolkit().beep();
			return;
		}
		if (Configuration.get("NOTIFY_SOUND").equals("")) {
			Configuration.put("NOTIFY_SOUND", "DEFAULT");
			Configuration.saveConfig();
		}
		URL url;
		if (Configuration.get("NOTIFY_SOUND").equals("DEFAULT"))
			url =
				EventNotificationDialog.class.getResource(
					"/ui/beep.wav");
		else
			try {
				url =
					new File(Configuration.get("NOTIFY_SOUND").toString())
						.toURL();
			} catch (Exception ex) {
				url =
					EventNotificationDialog.class.getResource(
						"/ui/beep.wav");
			}
		try {
			AudioClip clip = Applet.newAudioClip(url);
			clip.play();
		} catch (Exception ex) {
			new ExceptionDialog(ex, "Error loading audioclip from "+url, "Check the location and type of audioclip file.");
		}
	}
}