package memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.DateFormat;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.text.html.HTMLDocument;

import memoranda.CurrentNote;
import memoranda.History;
import memoranda.Note;
import memoranda.date.CurrentDate;
import memoranda.ui.htmleditor.HTMLEditor;
import memoranda.util.Configuration;
import memoranda.util.Context;
import memoranda.util.CurrentStorage;
import memoranda.util.HTMLFileExport;
import memoranda.util.HTMLFileImport;
import memoranda.util.Local;
import memoranda.util.Util;

/*$Id: EditorPanel.java,v 1.21 2006/06/28 22:58:31 alexeya Exp $*/
public class EditorPanel extends JPanel {
	BorderLayout borderLayout1 = new BorderLayout();

	JPanel jPanel1 = new JPanel();

	public HTMLEditor editor = null;

	JButton importB = new JButton();

	JButton exportB = new JButton();

	JButton redoB = new JButton();

	JButton copyB = new JButton();

	JButton historyBackB = new JButton();

	JToolBar editorToolBar = new JToolBar();

	JButton pasteB = new JButton();

	JButton historyForwardB = new JButton();

	JButton insDateB = new JButton();

	JButton insTimeB = new JButton();

	// JButton printB = new JButton();
	JButton undoB = new JButton();

	JButton cutB = new JButton();

	BorderLayout borderLayout2 = new BorderLayout();

	JToolBar titleBar = new JToolBar();

	JLabel titleLabel = new JLabel();

	public JTextField titleField = new JTextField();

	JButton newB = new JButton();

	JButton previewB = new JButton();

	DailyItemsPanel parentPanel = null;

	public EditorPanel(DailyItemsPanel parent) {
		try {
			parentPanel = parent;
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}

	public Action insertTimeAction = new AbstractAction(Local
			.getString("Insert current time"), new ImageIcon(
			memoranda.ui.AppFrame.class
					.getResource("/ui/icons/time.png"))) {
		public void actionPerformed(ActionEvent e) {
			insTimeB_actionPerformed(e);
		}
	};

	public Action insertDateAction = new AbstractAction(Local
			.getString("Insert current date"), new ImageIcon(
			memoranda.ui.AppFrame.class
					.getResource("/ui/icons/date.png"))) {
		public void actionPerformed(ActionEvent e) {
			insDateB_actionPerformed(e);
		}
	};

	/*
	 * public Action printAction = new AbstractAction( "Print", new
	 * ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("/ui/icons/print.png"))) {
	 * public void actionPerformed(ActionEvent e) { doPrint(); } };
	 */

	public Action newAction = new AbstractAction(Local.getString("New note"),
			new ImageIcon(memoranda.ui.AppFrame.class
					.getResource("/ui/icons/filenew.png"))) {
		public void actionPerformed(ActionEvent e) {
			newB_actionPerformed(e);
		}
	};

	public Action exportAction = new AbstractAction(Local
			.getString("Export note to file"), new ImageIcon(
			memoranda.ui.AppFrame.class
					.getResource("/ui/icons/export.png"))) {
		public void actionPerformed(ActionEvent e) {
			exportB_actionPerformed(e);
		}
	};

	public Action importAction = new AbstractAction(Local
			.getString("Insert file"), new ImageIcon(
			memoranda.ui.AppFrame.class
					.getResource("/ui/icons/import.png"))) {
		public void actionPerformed(ActionEvent e) {
			importB_actionPerformed(e);
		}
	};

	public Action previewAction = new AbstractAction(Local
			.getString("Preview note in browser"), new ImageIcon(
			memoranda.ui.AppFrame.class
					.getResource("/ui/icons/preview.png"))) {
		public void actionPerformed(ActionEvent e) {
			previewB_actionPerformed(e);
		}
	};

	void jbInit() throws Exception {

		if (!Configuration.get("DISABLE_L10N").equals("yes"))
			memoranda.ui.htmleditor.util.Local.setMessages(Local
					.getMessages());

		editor = new HTMLEditor();

		this.setLayout(borderLayout1);

		newB.setAction(newAction);
		newB.setMaximumSize(new Dimension(24, 24));
		newB.setMinimumSize(new Dimension(24, 24));
		newB.setPreferredSize(new Dimension(24, 24));
		newB.setRequestFocusEnabled(false);
		newB.setToolTipText(Local.getString("New note"));
		newB.setBorderPainted(false);
		newB.setFocusable(false);
		newB.setText("");

		importB.setAction(importAction);
		importB.setBorderPainted(false);
		importB.setFocusable(false);
		importB.setPreferredSize(new Dimension(24, 24));
		importB.setRequestFocusEnabled(false);
		importB.setToolTipText(Local.getString("Insert file"));
		importB.setMinimumSize(new Dimension(24, 24));
		importB.setMaximumSize(new Dimension(24, 24));
		importB.setText("");

		exportB.setAction(exportAction);
		exportB.setMaximumSize(new Dimension(24, 24));
		exportB.setMinimumSize(new Dimension(24, 24));
		exportB.setPreferredSize(new Dimension(24, 24));
		exportB.setRequestFocusEnabled(false);
		exportB.setToolTipText(Local.getString("Export note to file"));
		exportB.setBorderPainted(false);
		exportB.setFocusable(false);
		exportB.setText("");

		redoB.setAction(editor.redoAction);
		redoB.setMaximumSize(new Dimension(24, 24));
		redoB.setMinimumSize(new Dimension(24, 24));
		redoB.setPreferredSize(new Dimension(24, 24));
		redoB.setRequestFocusEnabled(false);
		redoB.setToolTipText(Local.getString("Redo"));
		redoB.setBorderPainted(false);
		redoB.setFocusable(false);
		redoB.setText("");

		copyB.setAction(editor.copyAction);
		copyB.setMaximumSize(new Dimension(24, 24));
		copyB.setMinimumSize(new Dimension(24, 24));
		copyB.setPreferredSize(new Dimension(24, 24));
		copyB.setRequestFocusEnabled(false);
		copyB.setToolTipText(Local.getString("Copy"));
		copyB.setBorderPainted(false);
		copyB.setFocusable(false);
		copyB.setText("");

		historyBackB.setAction(History.historyBackAction);
		historyBackB.setMaximumSize(new Dimension(24, 24));
		historyBackB.setMinimumSize(new Dimension(24, 24));
		historyBackB.setPreferredSize(new Dimension(24, 24));
		historyBackB.setRequestFocusEnabled(false);
		historyBackB.setToolTipText(Local.getString("History back"));
		historyBackB.setBorderPainted(false);
		historyBackB.setFocusable(false);
		historyBackB.setText("");

		historyForwardB.setAction(History.historyForwardAction);
		historyForwardB.setBorderPainted(false);
		historyForwardB.setFocusable(false);
		historyForwardB.setPreferredSize(new Dimension(24, 24));
		historyForwardB.setRequestFocusEnabled(false);
		historyForwardB.setToolTipText(Local.getString("History forward"));
		historyForwardB.setMinimumSize(new Dimension(24, 24));
		historyForwardB.setMaximumSize(new Dimension(24, 24));
		historyForwardB.setText("");

		pasteB.setAction(editor.pasteAction);
		pasteB.setMaximumSize(new Dimension(24, 24));
		pasteB.setMinimumSize(new Dimension(24, 24));
		pasteB.setPreferredSize(new Dimension(24, 24));
		pasteB.setRequestFocusEnabled(false);
		pasteB.setToolTipText(Local.getString("paste"));
		pasteB.setBorderPainted(false);
		pasteB.setFocusable(false);
		pasteB.setText("");

		insDateB.setAction(insertDateAction);
		insDateB.setBorderPainted(false);
		insDateB.setFocusable(false);
		insDateB.setPreferredSize(new Dimension(24, 24));
		insDateB.setRequestFocusEnabled(false);
		insDateB.setToolTipText(Local.getString("Insert current date"));
		insDateB.setMinimumSize(new Dimension(24, 24));
		insDateB.setMaximumSize(new Dimension(24, 24));
		insDateB.setText("");

		insTimeB.setAction(insertTimeAction);
		insTimeB.setMaximumSize(new Dimension(24, 24));
		insTimeB.setMinimumSize(new Dimension(24, 24));
		insTimeB.setPreferredSize(new Dimension(24, 24));
		insTimeB.setRequestFocusEnabled(false);
		insTimeB.setToolTipText(Local.getString("Insert current time"));
		insTimeB.setBorderPainted(false);
		insTimeB.setFocusable(false);
		insTimeB.setText("");

		undoB.setAction(editor.undoAction);
		undoB.setBorderPainted(false);
		undoB.setFocusable(false);
		undoB.setPreferredSize(new Dimension(24, 24));
		undoB.setRequestFocusEnabled(false);
		undoB.setToolTipText(Local.getString("Undo"));
		undoB.setMinimumSize(new Dimension(24, 24));
		undoB.setMaximumSize(new Dimension(24, 24));
		undoB.setText("");

		cutB.setAction(editor.cutAction);
		cutB.setBorderPainted(false);
		cutB.setFocusable(false);
		cutB.setPreferredSize(new Dimension(24, 24));
		cutB.setRequestFocusEnabled(false);
		cutB.setToolTipText(Local.getString("Cut"));
		cutB.setMinimumSize(new Dimension(24, 24));
		cutB.setMaximumSize(new Dimension(24, 24));
		cutB.setText("");

		previewB.setAction(previewAction);
		previewB.setBorderPainted(false);
		previewB.setFocusable(false);
		previewB.setPreferredSize(new Dimension(24, 24));
		previewB.setRequestFocusEnabled(false);
		previewB.setToolTipText(previewAction.getValue(Action.NAME).toString());
		previewB.setMinimumSize(new Dimension(24, 24));
		previewB.setMaximumSize(new Dimension(24, 24));
		previewB.setText("");

		/*
		 * printB.setAction(printAction); printB.setMaximumSize(new
		 * Dimension(24, 24)); printB.setMinimumSize(new Dimension(24, 24));
		 * printB.setPreferredSize(new Dimension(24, 24));
		 * printB.setRequestFocusEnabled(false);
		 * printB.setToolTipText(Local.getString("Print"));
		 * printB.setBorderPainted(false); printB.setFocusable(false);
		 * printB.setText("");
		 */

		jPanel1.setLayout(borderLayout2);
		titleLabel.setFont(new java.awt.Font("Dialog", 1, 10));
		titleLabel.setText(Local.getString("Title") + "  ");
		titleField.setText("");
		editorToolBar.setFloatable(false);
		editor.editToolbar.setFloatable(false);
		titleBar.setFloatable(false);
		this.add(jPanel1, BorderLayout.CENTER);
		editorToolBar.add(newB, null);
		editorToolBar.addSeparator(new Dimension(8, 24));
		editorToolBar.add(historyBackB, null);
		editorToolBar.add(historyForwardB, null);
		editorToolBar.addSeparator(new Dimension(8, 24));
		editorToolBar.add(undoB, null);
		editorToolBar.add(redoB, null);
		editorToolBar.addSeparator(new Dimension(8, 24));
		editorToolBar.add(cutB, null);
		editorToolBar.add(copyB, null);
		editorToolBar.add(pasteB, null);
		editorToolBar.addSeparator(new Dimension(8, 24));
		editorToolBar.add(insDateB, null);
		editorToolBar.add(insTimeB, null);
		editorToolBar.addSeparator(new Dimension(8, 24));
		editorToolBar.add(importB, null);
		editorToolBar.add(exportB, null);
		editorToolBar.addSeparator(new Dimension(8, 24));
		editorToolBar.add(previewB, null);
		// editorToolBar.add(printB, null);
		jPanel1.add(editorToolBar, BorderLayout.NORTH);
		jPanel1.add(editor, BorderLayout.CENTER);
		this.add(titleBar, BorderLayout.NORTH);
		titleBar.add(titleLabel, null);
		titleBar.add(titleField, null);
		initCSS();
		editor.editor.setAntiAlias(Configuration.get("ANTIALIAS_TEXT").toString().equalsIgnoreCase("yes"));
		// editor.editor.enableInputMethods(false);
		// editor.editor.getInputContext().selectInputMethod(Locale.getDefault());
		titleField.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ENTER)
					editor.editor.requestFocus();
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyTyped(KeyEvent arg0) {
			}
		});
	}

	public void initCSS() {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				memoranda.ui.EditorPanel.class
						.getResourceAsStream("/ui/css/default.css")));
		String css = "";
		try {
			String s = br.readLine();
			while (s != null) {
				css = css + s + "\n";
				s = br.readLine();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		String NORMAL_FONT = Configuration.get("NORMAL_FONT").toString();
		String HEADER_FONT = Configuration.get("HEADER_FONT").toString();
		String MONO_FONT = Configuration.get("MONO_FONT").toString();
		String BASE_FONT_SIZE = Configuration.get("BASE_FONT_SIZE").toString();
		css = css.replaceAll("%NORMAL_FONT%", NORMAL_FONT.length() > 0 ? "\""+NORMAL_FONT+"\""
				: "serif");
		css = css.replaceAll("%HEADER_FONT%", HEADER_FONT.length() > 0 ? "\""+HEADER_FONT+"\""
				: "sans-serif");
		css = css.replaceAll("%MONO_FONT%", MONO_FONT.length() > 0 ? "\""+MONO_FONT+"\""
				: "monospaced");
		css = css.replaceAll("%BASE_FONT_SIZE%",
				BASE_FONT_SIZE.length() > 0 ? BASE_FONT_SIZE : "16");		
		editor.setStyleSheet(new StringReader(css));
		String usercss = (String) Configuration.get("USER_CSS");
		if (usercss.length() > 0)
			try {
				// DEBUG
				System.out.println("***[DEBUG] User css used: " + usercss);
				editor.setStyleSheet(new InputStreamReader(
						new java.io.FileInputStream(usercss)));
			} catch (Exception ex) {
				System.out.println("***[DEBUG] Failed to open: " + usercss);
				ex.printStackTrace();
			}

	}

	void insDateB_actionPerformed(ActionEvent e) {
		editor.editor.replaceSelection(CurrentDate.get().getFullDateString());
	}

	void insTimeB_actionPerformed(ActionEvent e) {
		java.util.Date d = new java.util.Date();
		editor.editor.replaceSelection(DateFormat.getTimeInstance(
				DateFormat.SHORT, Local.getCurrentLocale()).format(d));
	}

	void exportB_actionPerformed(ActionEvent e) {
		// Fix until Sun's JVM supports more locales...
		UIManager.put("FileChooser.lookInLabelText", Local
				.getString("Save in:"));
		UIManager.put("FileChooser.upFolderToolTipText", Local
				.getString("Up One Level"));
		UIManager.put("FileChooser.newFolderToolTipText", Local
				.getString("Create New Folder"));
		UIManager.put("FileChooser.listViewButtonToolTipText", Local
				.getString("List"));
		UIManager.put("FileChooser.detailsViewButtonToolTipText", Local
				.getString("Details"));
		UIManager.put("FileChooser.fileNameLabelText", Local
				.getString("File Name:"));
		UIManager.put("FileChooser.filesOfTypeLabelText", Local
				.getString("Files of Type:"));
		UIManager.put("FileChooser.saveButtonText", Local.getString("Save"));
		UIManager.put("FileChooser.saveButtonToolTipText", Local
				.getString("Save selected file"));
		UIManager
				.put("FileChooser.cancelButtonText", Local.getString("Cancel"));
		UIManager.put("FileChooser.cancelButtonToolTipText", Local
				.getString("Cancel"));

		JFileChooser chooser = new JFileChooser();
		chooser.setFileHidingEnabled(false);
		chooser.setDialogTitle(Local.getString("Export note"));
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser
				.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.XHTML));
		chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.HTML));
		// chooser.addChoosableFileFilter(new
		// AllFilesFilter(AllFilesFilter.RTF));
		String lastSel = (String) Context.get("LAST_SELECTED_EXPORT_FILE");
		if (lastSel != null)
			chooser.setCurrentDirectory(new File(lastSel));

		FileExportDialog dlg = new FileExportDialog(App.getFrame(), Local
				.getString("Export note"), chooser);
		String enc = (String) Context.get("EXPORT_FILE_ENCODING");
		if (enc != null)
			dlg.encCB.setSelectedItem(enc);
		String templ = (String) Context.get("EXPORT_TEMPLATE");
		if (templ != null)
			dlg.templF.setText(templ);
		String xhtml = (String) Context.get("EXPORT_XHTML");
		if ((xhtml != null) && (xhtml.equalsIgnoreCase("YES")))
			dlg.xhtmlChB.setSelected(true);
		String num = (String) Context.get("EXPORT_NUMENT");
		if ((num != null) && (num.equalsIgnoreCase("YES")))
			dlg.numentChB.setSelected(true);
		Dimension dlgSize = new Dimension(550, 475);
		dlg.setSize(dlgSize);
		Dimension frmSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();
		dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
				(frmSize.height - dlgSize.height) / 2 + loc.y);
		dlg.setVisible(true);
		if (dlg.CANCELLED)
			return;

		Context.put("LAST_SELECTED_EXPORT_FILE", chooser.getSelectedFile()
				.getPath());
		Context.put("EXPORT_FILE_ENCODING", dlg.encCB.getSelectedItem());
		Context.put("EXPORT_NUMENT", dlg.numentChB.isSelected() ? "YES" : "NO");
		Context.put("EXPORT_XHTML", dlg.xhtmlChB.isSelected() ? "YES" : "NO");
		String template = null;
		if (dlg.usetemplChB.isSelected() && dlg.templF.getText().length() > 0) {
			template = dlg.templF.getText();
			Context.put("EXPORT_TEMPLATE", template);
		}
		/*
		 * if (chooser.getFileFilter().getDescription().equals("Rich Text
		 * Format")) new RTFFileExport(chooser.getSelectedFile(),
		 * editor.document); else
		 */
		int ei = dlg.encCB.getSelectedIndex();
		enc = null;
		if (ei == 1)
			enc = "UTF-8";
		File f = chooser.getSelectedFile();
		new HTMLFileExport(f, editor.document, CurrentNote.get(), enc,
				dlg.numentChB.isSelected(), template, dlg.xhtmlChB.isSelected());
	}

	String initialTitle = "";

	public void setDocument(Note note) {
		// Note note = CurrentProject.getNoteList().getActiveNote();
		// try {
		// this.editor.editor.setPage(CurrentStorage.get().getNoteURL(note));
		editor.document = (HTMLDocument) CurrentStorage.get().openNote(note);
		editor.initEditor();
		if (note != null)
			titleField.setText(note.getTitle());
		else
			titleField.setText("");
		initialTitle = titleField.getText();
		/*
		 * } catch (Exception ex) { new ExceptionDialog(ex); }
		 */
		/*
		 * Document doc = CurrentStorage.get().openNote(note); try {
		 * this.editor.editor.setText(doc.getText(0, doc.getLength())); } catch
		 * (Exception ex){ ex.printStackTrace(); }
		 */
		// .setDocument(CurrentStorage.get().openNote(note));
	}

	public javax.swing.text.Document getDocument() {
		return this.editor.document;
	}

	public boolean isDocumentChanged() {
		return editor.isDocumentChanged()
				|| !titleField.getText().equals(initialTitle);
	}

	void importB_actionPerformed(ActionEvent e) {
		// Fix until Sun's JVM supports more locales...
		UIManager.put("FileChooser.lookInLabelText", Local
				.getString("Look in:"));
		UIManager.put("FileChooser.upFolderToolTipText", Local
				.getString("Up One Level"));
		UIManager.put("FileChooser.newFolderToolTipText", Local
				.getString("Create New Folder"));
		UIManager.put("FileChooser.listViewButtonToolTipText", Local
				.getString("List"));
		UIManager.put("FileChooser.detailsViewButtonToolTipText", Local
				.getString("Details"));
		UIManager.put("FileChooser.fileNameLabelText", Local
				.getString("File Name:"));
		UIManager.put("FileChooser.filesOfTypeLabelText", Local
				.getString("Files of Type:"));
		UIManager.put("FileChooser.openButtonText", Local.getString("Open"));
		UIManager.put("FileChooser.openButtonToolTipText", Local
				.getString("Open selected file"));
		UIManager
				.put("FileChooser.cancelButtonText", Local.getString("Cancel"));
		UIManager.put("FileChooser.cancelButtonToolTipText", Local
				.getString("Cancel"));

		JFileChooser chooser = new JFileChooser();
		chooser.setFileHidingEnabled(false);
		chooser.setDialogTitle(Local.getString("Insert file"));
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.HTML));
		chooser.setPreferredSize(new Dimension(550, 375));
		String lastSel = (String) Context.get("LAST_SELECTED_IMPORT_FILE");
		if (lastSel != null)
			chooser.setCurrentDirectory(new java.io.File(lastSel));
		if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
			return;

		Context.put("LAST_SELECTED_IMPORT_FILE", chooser.getSelectedFile()
				.getPath());

		File f = chooser.getSelectedFile();
		new HTMLFileImport(f, editor);
	}

	void newB_actionPerformed(ActionEvent e) {
		CurrentNote.set(null, true);
		setDocument(null);
		this.titleField.requestFocus();
	}

	void previewB_actionPerformed(ActionEvent e) {
		File f;
		try {
			f = Util.getTempFile();
			new HTMLFileExport(f, editor.document, CurrentNote.get(), "UTF-8",
					false, null, false);
			Util.runBrowser("file:" + f.getAbsolutePath());
		} catch (IOException ioe) {
			new ExceptionDialog(ioe, "Cannot create temporary file", null);
		}
	}
}