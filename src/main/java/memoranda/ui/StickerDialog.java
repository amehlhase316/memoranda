package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.util.Context;
import main.java.memoranda.util.Local;

/*$Id: StickerDialog.java,v 1.5 2004/10/07 21:31:33 ivanrise Exp $*/
public class StickerDialog extends JDialog {
	public boolean CANCELLED = true;
	JPanel panel1 = new JPanel();
	BorderLayout borderLayout1 = new BorderLayout();
	BorderLayout borderLayout2 = new BorderLayout();
	
	JButton cancelButton = new JButton();
	JButton okButton = new JButton();
	JButton boldButton= new JButton();
	JButton italicButton= new JButton();
	JButton underlineButton= new JButton();
	JButton unorderedListButton= new JButton();
	
	JPanel bottomPanel = new JPanel();
	JPanel topPanel = new JPanel();
	JLabel header = new JLabel();
	JScrollPane jScrollPane1 = new JScrollPane();
	JPanel jPanel1 = new JPanel();
	JTextArea stickerText = new JTextArea();
	JLabel jLabel1 = new JLabel();
	JLabel jLabel2 = new JLabel();
	JLabel jLabel3 = new JLabel();
	JLabel jLabel4 = new JLabel();
	GridLayout gridLayout1 = new GridLayout(6,2);

	Border border1;
	Border border2;
	Color[] colors =
		{
			Color.YELLOW,
			Color.ORANGE,
			Color.RED,
			Color.BLUE,
			Color.GREEN,
			Color.CYAN,
			Color.MAGENTA,
			Color.BLACK,
			Color.WHITE,
			Color.PINK };
	String[] colorLabels =
		{
			Local.getString("Yellow"),
			Local.getString("Orange"),
			Local.getString("Red"),
			Local.getString("Blue"),
			Local.getString("Green"),
			Local.getString("Cyan"),
			Local.getString("Magenta"),
			Local.getString("Black"),
			Local.getString("White"),
			Local.getString("Pink"),
			Local.getString("Custom")+"..."};
	String[] priorities = {
			Local.getString("HIGHEST"),
			Local.getString("HIGH"),
			Local.getString("NORMAL"),
			Local.getString("LOW"),
			Local.getString("LOWEST")};
	int[] font={10,15,20};
	String[] fontLabels= {"10px","15px","20px"};
	JComboBox stickerColor = new JComboBox(colorLabels);
	JComboBox textColor = new JComboBox(colorLabels);
	JComboBox fontSize = new JComboBox(fontLabels);
	JComboBox priorityList = new JComboBox(priorities);
	

	public StickerDialog(Frame frame) {
		super(frame, Local.getString("Sticker"), true);
		try {
			jbInit();
			pack();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}
	public StickerDialog(Frame frame, String text, String backcolor, String forecolor, int sP, int size){
		super(frame, Local.getString("Sticker"), true);
		try {
			jbInit();
			pack();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
		stickerText.setText(text);
		Color back = Color.decode(backcolor);
		Color front = Color.decode(forecolor);
		int i = findColorIndex(back);
		if (i > -1)
			stickerColor.setSelectedIndex(i);
		else
			stickerColor.setSelectedIndex(10);
		i = findColorIndex(front);
		if (i > -1)
			textColor.setSelectedIndex(i);
		else
			textColor.setSelectedIndex(stickerColor.getSelectedIndex()+1);
		if (sP > -1 && sP < 5)
			priorityList.setSelectedIndex(sP);
		else
			priorityList.setSelectedIndex(2);
		if(size==10)
			fontSize.setSelectedIndex(0);
		else if(size == 20)
			fontSize.setSelectedIndex(2);
		else fontSize.setSelectedIndex(1);
	}

	public StickerDialog() {
		this(null);
	}
	void jbInit() throws Exception {
		stickerColor.setRenderer(new ComboBoxRenderer());
		stickerColor.setMaximumRowCount(11);
		textColor.setRenderer(new ComboBoxRenderer2());
		textColor.setMaximumRowCount(11);
		priorityList.setSelectedIndex(2);
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
		
		boldButton.setText(Local.getString("Bold"));
		boldButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boldButton_actionPerformed(e);
			}
		});
		italicButton.setText(Local.getString("Italic"));
		italicButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				italicButton_actionPerformed(e);
			}
		});
		underlineButton.setText(Local.getString("Underline"));
		underlineButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underlineButton_actionPerformed(e);
			}
		});
		unorderedListButton.setText("* "+Local.getString("List"));
		unorderedListButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				unorderedListButton_actionPerformed(e);
			}
		});
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.setBorder(new EmptyBorder(new Insets(0, 5, 0, 5)));
		topPanel.setBackground(Color.WHITE);
		//topPanel.setBackground(new Color(215, 225, 250));
		header.setFont(new java.awt.Font("Dialog", 0, 20));
		header.setForeground(new Color(0, 0, 124));
		header.setText(Local.getString("Sticker"));
		header.setIcon(new ImageIcon(main.java.memoranda.ui.StickerDialog.class.getResource(
            "/ui/icons/sticker48.png")));
		//header.setHorizontalAlignment(SwingConstants.LEFT);

		jLabel1.setText(Local.getString("Sticker color")+": ");
		jLabel2.setText(Local.getString("Font color")+": ");
		jLabel3.setText(Local.getString("Font Size")+": ");
		jLabel4.setText(Local.getString("Priority")+": ");
		jPanel1.setLayout(gridLayout1);
		panel1.setBorder(border1);
		jPanel1.setBorder(border2);
		
		getContentPane().add(panel1, BorderLayout.CENTER);
		panel1.add(jScrollPane1, BorderLayout.CENTER);
		jScrollPane1.getViewport().add(stickerText, null);
		panel1.add(jPanel1, BorderLayout.SOUTH);
		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.add(okButton);
		bottomPanel.add(cancelButton);
		this.getContentPane().add(topPanel, BorderLayout.NORTH);
		topPanel.add(header);
		
		jPanel1.add(jLabel1);
		jPanel1.add(stickerColor);
		jPanel1.add(jLabel2);
		jPanel1.add(textColor);
		jPanel1.add(jLabel3);
		jPanel1.add(fontSize);
		
		jPanel1.add(boldButton);
		jPanel1.add(italicButton);
		jPanel1.add(underlineButton);
		jPanel1.add(unorderedListButton);
		jPanel1.add(jLabel4);
		jPanel1.add(priorityList);
		
		if (Context.get("STICKER_COLOR") != null) {
			Color c = new Color(new Integer(Context.get("STICKER_COLOR").toString()).intValue());
			stickerText.setBackground(c);
			int i = findColorIndex(c);
			if (i > -1)
				stickerColor.setSelectedIndex(i);
			else
				stickerColor.setSelectedIndex(10);
		}
		else{
			stickerText.setBackground(Color.YELLOW);
			stickerColor.setSelectedIndex(0);
		}
		stickerText.setWrapStyleWord(true);
		stickerText.setText(
			CalendarDate.today().getLongDateString()
				+ " "
				+ DateFormat.getTimeInstance(
					DateFormat.SHORT,
					Local.getCurrentLocale()).format(
					new java.util.Date())+"\n");
		stickerColor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stickerColor_actionPerformed(e);
			}
		});
		if (Context.get("TEXT_COLOR") != null) {
			Color d = new Color(new Integer(Context.get("TEXT_COLOR").toString()).intValue());
			stickerText.setForeground(d);
			int i = findColorIndex(d);
			if (i > -1){
				if(i!=stickerColor.getSelectedIndex()){
					textColor.setSelectedIndex(i);
				}
				else{
					textColor.setSelectedIndex(i+1);
					stickerText.setForeground(colors[i]);
				}
			}
			else
				textColor.setSelectedIndex(10);
		}
		else{
			stickerText.setForeground(Color.BLACK);
			textColor.setSelectedIndex(7);
		}
		textColor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textColor_actionPerformed(e);
			}
		});
		Font f= stickerText.getFont();
		if (Context.get("TEXT_SIZE") != null) {
			int h= (fontSize.getSelectedIndex()*5)+10;
			if (h!=10 && h!=15 && h!=20) h=15;
			stickerText.setFont(new Font(f.getFontName(), f.PLAIN, h));
		}
		else{
			stickerText.setFont(new Font(f.getFontName(), f.PLAIN, 15));
			fontSize.setSelectedIndex(1);
		}
		fontSize.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fontSize_actionPerformed(e);
			}
		});
	}

	int findColorIndex(Color c) {		
		for (int i = 0; i < colors.length; i++)
			if (c.equals(colors[i]))
				return i;
		return -1;
	}

	public String getStickerText() {
		return stickerText.getText();
	}
	public String getStickerTextSize() {
		return ""+stickerText.getFont().getSize();
	}
	public String getStickerTextColor(){
		return "#"
				+ Integer
					.toHexString(stickerText.getForeground().getRGB() - 0xFF000000)
					.toUpperCase();	
		}
	public String getStickerColor() {
		return "#"
			+ Integer
				.toHexString(stickerText.getBackground().getRGB() - 0xFF000000)
				.toUpperCase();
	}
	int getPriority(){
		return priorityList.getSelectedIndex();
	}

	void cancelButton_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	void okButton_actionPerformed(ActionEvent e) {
		CANCELLED = false;
		this.dispose();
	}
	void boldButton_actionPerformed(ActionEvent e) {
		int pos=stickerText.getCaretPosition();
		stickerText.insert("<b></b>", pos);
		stickerText.requestFocusInWindow();
		stickerText.setCaretPosition(pos+3);
	}
	void italicButton_actionPerformed(ActionEvent e) {
		int pos=stickerText.getCaretPosition();
		stickerText.insert("<i></i>", pos);
		stickerText.requestFocusInWindow();
		stickerText.setCaretPosition(pos+3);
	}
	protected void underlineButton_actionPerformed(ActionEvent e) {
		int pos=stickerText.getCaretPosition();
		stickerText.insert("<u></u>", pos);
		stickerText.requestFocusInWindow();
		stickerText.setCaretPosition(pos+3);
	}
	void unorderedListButton_actionPerformed(ActionEvent e) {
		int pos=stickerText.getCaretPosition();
		stickerText.insert("<li></li>", pos);
		stickerText.requestFocusInWindow();
		stickerText.setCaretPosition(pos+4);
	}
	void stickerColor_actionPerformed(ActionEvent e) {
		int i=stickerColor.getSelectedIndex();
		if (i< colors.length){
			if(i!=textColor.getSelectedIndex()){
				stickerText.setBackground(colors[i]);
				stickerColor.setSelectedIndex(i);
			}
			else{
				stickerColor.setSelectedIndex(i+1);
				stickerText.setBackground(colors[i+1]);
				JOptionPane.showMessageDialog(this, Local.getString("SAME BACKGROUND COLOR"), "Error", 0);
			}
			stickerText.setForeground(colors[textColor.getSelectedIndex()]);
		}
		else {
			Color c =
				JColorChooser.showDialog(
					this,
					Local.getString("Sticker color"),
					stickerText.getBackground());
			if (c != null)
				stickerText.setBackground(c);
		}
		Context.put("STICKER_COLOR", new Integer(stickerText.getBackground().getRGB()));
	}
	void textColor_actionPerformed(ActionEvent e) {
		int i=textColor.getSelectedIndex();
		if (i < colors.length){
			if(i!=stickerColor.getSelectedIndex()){
				stickerText.setForeground(colors[i]);
				textColor.setSelectedIndex(i);
			}
			else{
				textColor.setSelectedIndex(i+1);
				stickerText.setForeground(colors[i+1]);
				JOptionPane.showMessageDialog(this, Local.getString("SAME FOREGROUND COLOR"), "Error", 0);
			}
			stickerText.setForeground(colors[textColor.getSelectedIndex()]);
		}
		else {
			Color c =
				JColorChooser.showDialog(
					this,
					Local.getString("Text color"),
					stickerText.getForeground());
			if (c != null)
				stickerText.setForeground(c);
		}
		Context.put("TEXT_COLOR", new Integer(stickerText.getForeground().getRGB()));		
	}
	protected void fontSize_actionPerformed(ActionEvent e) {
		int i=fontSize.getSelectedIndex();
		if (i < fontLabels.length){
			Font f= stickerText.getFont();
			stickerText.setFont(new Font(f.getFontName(), f.PLAIN, (i*5)+10));
		}
		fontSize.setSelectedIndex(i);
		Context.put("TEXT_SIZE", new Integer(stickerText.getFont().getSize()));		
		}
	class ComboBoxRenderer extends JLabel implements ListCellRenderer {
		public ComboBoxRenderer() {
			setOpaque(true);

		}
		public Component getListCellRendererComponent(
			JList list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {
			/*
			 * if (isSelected) { setBackground(list.getSelectionBackground());
			 * setForeground(list.getSelectionForeground());
			 */
			if(index==7){
				this.setForeground(Color.WHITE);
			}
			else setForeground(list.getForeground());
			if ((index > -1) && (index < colors.length))
				setBackground(colors[index]);
			else
				setBackground(list.getBackground());
			//}
			setText(value.toString());
			return this;
		}
	}
	class ComboBoxRenderer2 extends JLabel implements ListCellRenderer {
		public ComboBoxRenderer2() {
			setOpaque(true);

		}
		public Component getListCellRendererComponent(
			JList list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {
			/*
			 * if (isSelected) { setBackground(list.getSelectionBackground());
			 * setForeground(list.getSelectionForeground());
			 */
			if ((index > -1) && (index < colors.length))
				setForeground(colors[index]);
			else
				setForeground(list.getForeground());
			setBackground(list.getBackground());
			//}
			setText(value.toString());
			return this;
		}
	}

}