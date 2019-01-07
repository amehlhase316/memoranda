
package main.java.memoranda.ui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import main.java.memoranda.util.Local;

/**
 *
 * @author  Alex
 */
public class FileExportDialog extends javax.swing.JDialog {
    
    public boolean CANCELLED = true;
    
    
    /** Creates new form ExportDialog */
    public FileExportDialog(java.awt.Frame parent, String title, JFileChooser chooser) {
        super(parent, title, true);
        fileChooser = chooser;
        initComponents();
    }
    
   
    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel2 = new javax.swing.JPanel();
        okB = new javax.swing.JButton();
        cancelB = new javax.swing.JButton();
        filePanel = new javax.swing.JPanel();
        //fileChooser = new javax.swing.JFileChooser();
        optionsPanel = new javax.swing.JPanel();
        encPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        encCB = new JComboBox(new String[]{Local.getString("System default"), "UTF-8", "ANSI"});
        usetemplChB = new javax.swing.JCheckBox();
        xhtmlChB = new javax.swing.JCheckBox();
        templPanel = new javax.swing.JPanel();
        templF = new javax.swing.JTextField();
        templF.setEditable(false);
        templBrowseB = new javax.swing.JButton();
        numentChB = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        okB.setText(Local.getString("Save"));
        okB.setPreferredSize(new java.awt.Dimension(90, 25));
        okB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CANCELLED = false;
                dispose();
            }
        });
        okB.setEnabled(false);
        jPanel2.add(okB);

        cancelB.setText(Local.getString("Cancel"));
        cancelB.setPreferredSize(new java.awt.Dimension(90, 25));
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {                
                dispose();
            }
        });
        jPanel2.add(cancelB);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        filePanel.setLayout(new java.awt.BorderLayout());

        filePanel.setBorder(new javax.swing.border.EtchedBorder());
        fileChooser.setControlButtonsAreShown(false);
        fileChooser.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                chooserActionPerformed();
                
            }
        
        });
        /*fileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooserActionPerformed();
            }
        });*/
        
        

        filePanel.add(fileChooser, java.awt.BorderLayout.CENTER);

        optionsPanel.setLayout(new java.awt.GridLayout(3, 2, 5, 0));

        optionsPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5)));
        encPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel2.setText(Local.getString("Encoding")+":");
        encPanel.add(jLabel2);

        encCB.setMaximumSize(new java.awt.Dimension(32767, 19));
        encPanel.add(encCB);

        optionsPanel.add(encPanel);

        usetemplChB.setText(Local.getString("Use template")+":");
        usetemplChB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        usetemplChB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {                
                if (usetemplChB.isSelected()) {
                    templF.setEnabled(true);
                    templBrowseB.setEnabled(true);
                }
                else {
                    templF.setEnabled(false);
                    templBrowseB.setEnabled(false);                    
                }
            }
        });
        optionsPanel.add(usetemplChB);

        xhtmlChB.setText(Local.getString("Save as XHTML"));
        xhtmlChB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xhtmlChBActionPerformed(evt);
            }
        });

        optionsPanel.add(xhtmlChB);

        templPanel.setLayout(new java.awt.BorderLayout());
        templF.setEnabled(false);
        templPanel.add(templF, java.awt.BorderLayout.CENTER);

        templBrowseB.setText("Browse");
        templBrowseB.setEnabled(false);
        templBrowseB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
               browseTemplate();
            }
        });
        templPanel.add(templBrowseB, java.awt.BorderLayout.EAST);

        optionsPanel.add(templPanel);

        numentChB.setText("Use numeric entities");
        optionsPanel.add(numentChB);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        optionsPanel.add(jPanel6);

        filePanel.add(optionsPanel, java.awt.BorderLayout.SOUTH);

        getContentPane().add(filePanel, java.awt.BorderLayout.CENTER);
        getRootPane().setDefaultButton(okB);
        pack();
    }//GEN-END:initComponents

    private void xhtmlChBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xhtmlChBActionPerformed
        // TODO add your handling code here:
    }

    private void chooserActionPerformed() {//GEN-FIRST:event_chooserActionPerformed
        okB.setEnabled(fileChooser.getSelectedFile() != null);            
    }
    
    private void browseTemplate() {
    	JFileChooser chooser = new JFileChooser();
        chooser.setFileHidingEnabled(false);
        chooser.setDialogTitle(Local.getString("Select file"));
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (templF.getText().length() >0)
        	chooser.setCurrentDirectory(new java.io.File(templF.getText()));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        	templF.setText(chooser.getSelectedFile().getPath());
    }
    
    private javax.swing.JButton cancelB;
    private javax.swing.JFileChooser fileChooser;
    
    private javax.swing.JPanel encPanel;
    private javax.swing.JPanel filePanel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    public javax.swing.JCheckBox numentChB;
    private javax.swing.JButton okB;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JButton templBrowseB;
    public javax.swing.JTextField templF;
    private javax.swing.JPanel templPanel;
    public javax.swing.JCheckBox usetemplChB;
    public javax.swing.JCheckBox xhtmlChB;
    public JComboBox encCB;
    // End of variables declaration//GEN-END:variables
    
}
