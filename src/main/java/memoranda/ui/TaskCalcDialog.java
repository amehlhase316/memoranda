package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.java.memoranda.util.Local;

/*$Id: TaskCalcDialog.java,v 1.3 2005/06/10 18:36:24 velhonoja Exp $*/
public class TaskCalcDialog extends JDialog {
	JPanel topPanel = new JPanel(new BorderLayout());
	JPanel generalPanel = new JPanel(new GridBagLayout());
	GridBagConstraints gbc;
    public boolean CANCELLED = true;

	ButtonGroup closeGroup = new ButtonGroup();
	JCheckBox compactDatesChB = new JCheckBox();
	JCheckBox calcEffortChB = new JCheckBox();
	JCheckBox calcCompletionChB = new JCheckBox();
	JButton okB = new JButton();
	JButton cancelB = new JButton();
	JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
	JLabel label1 = new JLabel();
	JLabel label2 = new JLabel();

	public TaskCalcDialog(Frame frame) {
		super(frame, Local.getString("Preferences"), true);
		try {
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}

	public TaskCalcDialog() {
		this(null);
	}
	void jbInit() throws Exception {
	    this.setResizable(false);
		label1.setHorizontalAlignment(SwingConstants.RIGHT);
		label1.setText(Local.getString("Calculate and update data for this task using data from sub tasks."));
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(2, 10, 10, 15);
		gbc.anchor = GridBagConstraints.WEST;
		generalPanel.add(label1, gbc);

		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setText(Local.getString("Please select data fields to update") + ":");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(2, 10, 10, 15);
		gbc.anchor = GridBagConstraints.WEST;
		generalPanel.add(label2, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 1; gbc.gridy = 3;
		gbc.insets = new Insets(2, 0, 0, 10);
		gbc.anchor = GridBagConstraints.WEST;
		compactDatesChB.setText(Local.getString("Compact task dates based on sub task dates"));
//		compactDatesChB.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				enSystrayChB_actionPerformed(e);
//			}
//		});
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 10;
		gbc.insets = new Insets(2, 0, 0, 10);
		gbc.anchor = GridBagConstraints.WEST;
		generalPanel.add(compactDatesChB, gbc);
		calcEffortChB.setText(Local.getString("Calculate task effort based on sub task efforts"));
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 11;
		gbc.insets = new Insets(2, 0, 0, 10);
		gbc.anchor = GridBagConstraints.WEST;
		generalPanel.add(calcEffortChB, gbc);
		calcCompletionChB.setText(Local.getString("Calculate task completion based on sub task completion"));
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 12;
		gbc.insets = new Insets(2, 0, 0, 10);
		gbc.anchor = GridBagConstraints.WEST;
		generalPanel.add(calcCompletionChB, gbc);
//		calcCompletionChB.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				enSplashChB_actionPerformed(e);
//			}
//		});
		// Build TopPanel
		topPanel.add(generalPanel, BorderLayout.CENTER);

		// Build BottomPanel
		okB.setMaximumSize(new Dimension(100, 25));
		okB.setPreferredSize(new Dimension(100, 25));
		okB.setText(Local.getString("Ok"));
		okB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okB_actionPerformed(e);
			}
		});
		this.getRootPane().setDefaultButton(okB);
		bottomPanel.add(okB);
		cancelB.setMaximumSize(new Dimension(100, 25));
		cancelB.setPreferredSize(new Dimension(100, 25));
		cancelB.setText(Local.getString("Cancel"));
		cancelB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelB_actionPerformed(e);
			}
		});
		bottomPanel.add(cancelB);

		// Build Preferences-Dialog
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		// set all config-values
		setValues();

	}

	void setValues() {
		calcCompletionChB.setSelected(true);
		compactDatesChB.setSelected(true);
		calcEffortChB.setSelected(true);
	}

	void okB_actionPerformed(ActionEvent e) {
		CANCELLED = false;
		this.dispose();
	}

	void cancelB_actionPerformed(ActionEvent e) {
		this.dispose();
	}
}