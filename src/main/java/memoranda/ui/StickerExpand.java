package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.text.DateFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.ui.StickerDialog.ComboBoxRenderer;
import main.java.memoranda.util.Context;
import main.java.memoranda.util.Local;

public class StickerExpand extends JDialog{
	String txt;
	Color backGroundColor, foreGroundColor;
	public boolean CANCELLED = true;
	JPanel panel1 = new JPanel();
	BorderLayout borderLayout1 = new BorderLayout();
	BorderLayout borderLayout2 = new BorderLayout();
	JPanel bottomPanel = new JPanel();
	JPanel topPanel = new JPanel();
	JLabel header = new JLabel();
	JScrollPane jScrollPane1 = new JScrollPane();
	JPanel jPanel1 = new JPanel();
	JLabel stickerText = new JLabel();
	JLabel jLabel1 = new JLabel();
	BorderLayout borderLayout3 = new BorderLayout();

	Border border1;
	Border border2;
	public StickerExpand(Frame frame,String txt, String backcolor, String fontcolor, String priority) {
		super(frame, Local.getString("Sticker")+" ["+priority+"]" , true);
		this.txt=txt;
		this.backGroundColor=Color.decode(backcolor);
		this.foreGroundColor=Color.decode(fontcolor);
		try {
			jbInit();
			pack();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
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

		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.setBorder(new EmptyBorder(new Insets(0, 5, 0, 5)));
		topPanel.setBackground(Color.WHITE);

		jPanel1.setLayout(borderLayout3);
		panel1.setBorder(border1);
		jPanel1.setBorder(border2);

		getContentPane().add(panel1, BorderLayout.CENTER);
		panel1.add(jScrollPane1, BorderLayout.CENTER);
		jScrollPane1.getViewport().add(stickerText, null);
		panel1.add(jPanel1, BorderLayout.SOUTH);
		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		this.getContentPane().add(topPanel, BorderLayout.NORTH);

		stickerText.setText(txt);
		stickerText.setOpaque(true);
		stickerText.setBackground(backGroundColor);
		stickerText.setForeground(foreGroundColor);
	}
}
