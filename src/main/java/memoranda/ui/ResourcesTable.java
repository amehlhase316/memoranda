package main.java.memoranda.ui;

import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import main.java.memoranda.CurrentProject;
import main.java.memoranda.NoteList;
import main.java.memoranda.Project;
import main.java.memoranda.ProjectListener;
import main.java.memoranda.Resource;
import main.java.memoranda.ResourcesList;
import main.java.memoranda.TaskList;
import main.java.memoranda.ui.table.TableSorter;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.MimeType;
import main.java.memoranda.util.MimeTypesList;

/*$Id: ResourcesTable.java,v 1.4 2004/04/05 10:05:44 alexeya Exp $*/
public class ResourcesTable extends JTable {

    Vector files = null;
    TableSorter sorter = null;
    
    ImageIcon inetIcon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/mimetypes/inetshortcut.png"));

    public ResourcesTable() {
        super();
        initTable();
        sorter = new TableSorter(new ResourcesTableModel());
        sorter.addMouseListenerToHeaderInTable(this);
        setModel(sorter);
        this.setShowGrid(false);
        this.setFont(new Font("Dialog",0,11));
        initColumsWidth();
        //this.setModel(new ResourcesTableModel());
        CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange(Project p, NoteList nl, TaskList tl, ResourcesList rl) {                
               
            }
            public void projectWasChanged() {
                 tableChanged();
            }
        });
    }

    void initColumsWidth() {
        for (int i = 0; i < 4; i++) {
            TableColumn column = getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(32767);
            }
            else {
                column.setMinWidth(100);
                column.setPreferredWidth(100);
            }
        }
    }

    public void tableChanged() {
        initTable();
        sorter.tableChanged(null);
        initColumsWidth();
        updateUI();
    }

    public void initTable() {
        Vector v = CurrentProject.getResourcesList().getAllResources();
        files = new Vector();
        for (int i = 0; i < v.size(); i++) {
            Resource r = (Resource)v.get(i);
            if (!r.isInetShortcut()) {
                File f = new File(r.getPath());
                if (f.isFile())
                    files.add(r);
            }
            else 
                files.add(r);
        }

    }
    
     public static final int _RESOURCE = 100;

    public TableCellRenderer getCellRenderer(int row, int column) {
        return new javax.swing.table.DefaultTableCellRenderer() {

            public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {
                JLabel comp;

                comp = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == 0) {
                  Resource r = (Resource)getModel().getValueAt(row, _RESOURCE);
                  if (!r.isInetShortcut())  
                    comp.setIcon(MimeTypesList.getMimeTypeForFile((String)value).getIcon());
                  else 
                    comp.setIcon(inetIcon);
                }
                return comp;
            }
        };

    }

    class ResourcesTableModel extends AbstractTableModel {

        String[] columnNames = {
                Local.getString("Name"),
                Local.getString("Type"),
                Local.getString("Date modified"),
                Local.getString("Path")};

        public String getColumnName(int i) {
            return columnNames[i];
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return files.size();
        }
        
       
        
        public Object getValueAt(int row, int col) {
            Resource r = (Resource)files.get(row);
            if (col == _RESOURCE)
                return r;
            if (!r.isInetShortcut())  {
                File f = new File(r.getPath());
                switch (col) {
                    case 0: return f.getName();
                    case 1: MimeType mt = MimeTypesList.getMimeTypeForFile(f.getName());
                            if (mt != null) return mt.getLabel();
                            else return "unknown";
                    case 2: Date d = new Date(f.lastModified());
                            return d;/*Local.getDateString(d, java.text.DateFormat.SHORT) +" "+
                                   Local.getTimeString(d);*/
                    case 3:return f.getPath();
                }
            }
            else {
                if (col == 0)
                    return r.getPath();
                else if (col == 1)
                    return Local.getString("Internet shortcut");
                else
                    return "";                
            }
            return null;
        }

        
public Class getColumnClass(int col) {
            try {
            switch (col) {
                case 0 :
                case 1 :
                case 3 :
                    return Class.forName("java.lang.String");
                case 2 :
                    return Class.forName("java.util.Date");
            }
            }
            catch (Exception ex) {new ExceptionDialog(ex);}
            return null;
        }
    }

}