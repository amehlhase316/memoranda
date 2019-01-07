/**
 * TaskTable.java         
 * -----------------------------------------------------------------------------
 * Project           Memoranda
 * Package           net.sf.memoranda.ui
 * Original author   Alex V. Alishevskikh
 *                   [alexeya@gmail.com]
 * Created           18.05.2005 15:12:19
 * Revision info     $RCSfile: TaskTable.java,v $ $Revision: 1.26 $ $State: Exp $  
 *
 * Last modified on  $Date: 2007/01/05 10:33:26 $
 *               by  $Author: alexeya $
 * 
 * @VERSION@ 
 *
 * @COPYRIGHT@
 * 
 * @LICENSE@ 
 */

package main.java.memoranda.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import java.util.EventObject;
import java.util.Collection;
import java.util.Vector;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.LookAndFeel;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;

import main.java.memoranda.*;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.ui.treetable.*;
import main.java.memoranda.util.*;

/**
 * JAVADOC:
 * <h1>TaskTable</h1>
 * <p>
 * JTable whick uses JTree as a CellRenderer to show
 * Tasks and subtasks logically.</p>
 *
 * <p>
 * Datamodel is TaskTableModel whick is not used directly but
 * via TaskTableSorter whick extends TaskTableModel and
 * adds sorting capability.
 * </p>
 *
 * <p>
 * To make this class simpler, most cellrendering code
 * has been moved to TaskTreeTableCellRenderer.
 * </p>
 *
 * <p>Article about <a href="http://java.sun.com/products/jfc/tsc/articles/treetable1/">treetables</a>.</p>
 * 
 * @see	main.java.memoranda.ui.TaskTreeTableCellRenderer
 * @version $Id: TaskTable.java,v 1.26 2007/01/05 10:33:26 alexeya Exp $
 * @author $Author: alexeya $
 */
public class TaskTable extends JTable {

    public static final int TASK_ID = 100;

    public static final int TASK = 101;

    protected TreeTableCellRenderer tree;

    protected TaskTableModel model;
    
    protected TreeTableModelAdapter modelAdapter;
    
    protected TaskTreeTableCellRenderer renderer;
	
	protected ExpansionHandler expansion; 
    
    public TaskTable() {
        super();
        initTable();
        // Force the JTable and JTree to share their row selection models.
        ListToTreeSelectionModelWrapper selectionWrapper = new ListToTreeSelectionModelWrapper();
        tree.setSelectionModel(selectionWrapper);
        setSelectionModel(selectionWrapper.getListSelectionModel());

        CurrentDate.addDateListener(new DateListener() {
            public void dateChange(CalendarDate d) {
                //updateUI();
                tableChanged();
            }
        });
        CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange(Project p, NoteList nl, TaskList tl,
                    ResourcesList rl) {
            }

            public void projectWasChanged() {
                //initTable();
				tableChanged();
            }
        });
	
    }

    private void initTable() {
	
		//model = new TaskTableModel();
		model = new TaskTableSorter( this );
	
		// Create the tree. It will be used as a renderer and editor.
		tree = new TreeTableCellRenderer(model);
		
		// store tree expansion status and
		// restore after sorting/project change etc.
		expansion = new ExpansionHandler();
		tree.addTreeExpansionListener(expansion);
	
		// Install a tableModel representing the visible rows in the tree.
		modelAdapter = new TreeTableModelAdapter(model, tree);
		super.setModel(modelAdapter);
			
		// Install the tree editor renderer and editor.
		renderer = new TaskTreeTableCellRenderer(this);
		
		
		tree.setCellRenderer(renderer);
		setDefaultRenderer(TreeTableModel.class, tree);
		setDefaultRenderer(Integer.class, renderer);
		setDefaultRenderer(TaskTable.class, renderer);
		setDefaultRenderer(String.class, renderer);
		setDefaultRenderer(java.util.Date.class, renderer);

		setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor());
		
		// column name is repeated in 2 places, do something about it!
		getColumn( "% " + Local.getString("done") ).setCellEditor(new TaskProgressEditor());
		
		// TODO: editor for task progress
		
		
		//  grid.
		setShowGrid(false);

		// No intercell spacing
		setIntercellSpacing(new Dimension(0, 0));

		// And update the height of the trees row to match that of
		// the table.
		//if (tree.getRowHeight() < 1) {
			setRowHeight(18);
		//}
		initColumnWidths();
		
		// do not allow moving columns
		getTableHeader().setReorderingAllowed(false);
    }

    void initColumnWidths() {
        for (int i = 0; i < 7; i++) {
            TableColumn column = getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(8);
            } 
            else if (i == 1) {
                column.setPreferredWidth(32767);
            }
	    else if( i == 6 ){
		    column.setPreferredWidth(100);
		    column.setMinWidth(100);
	    }
            else {
                column.setMinWidth(67); // 65);
                column.setPreferredWidth(67); //65);
            }
        }
    }
    
    public void tableChanged() {
		model.fireUpdateCache();
		model.fireTreeStructureChanged();
		expansion.expand(tree);
		updateUI();
    }
    
    /**
     * Overridden to message super and forward the method to the tree. Since the
     * tree is not actually in the component hieachy it will never receive this
     * unless we forward it in this manner.
     */
    public void updateUI() {
		super.updateUI();
			if (tree != null) { 
			tree.updateUI();
			}


        // Use the tree's default foreground and background colors in the
        // table.
        LookAndFeel.installColorsAndFont(this, "Tree.background",
                "Tree.foreground", "Tree.font");
    }

    /*
     * Workaround for BasicTableUI anomaly. Make sure the UI never tries to
     * paint the editor. The UI currently uses different techniques to paint the
     * renderers and editors and overriding setBounds() below is not the right
     * thing to do for an editor. Returning -1 for the editing row in this case,
     * ensures the editor is never painted.
     */
    public int getEditingRow() {
        return (getColumnClass(editingColumn) == TreeTableModel.class) ? -1
                : editingRow;
    }

    /**
     * Overridden to pass the new rowHeight to the tree.
     */
    public void setRowHeight(int rowHeight) {
        super.setRowHeight(rowHeight);
        if (tree != null && tree.getRowHeight() != rowHeight) {
            tree.setRowHeight(getRowHeight());
        }
    }

    /**
     * Returns the tree that is being shared between the model.
     */
    public TreeTableCellRenderer getTree() {
        return tree;
    }

    /**
     * A TreeCellRenderer that displays a JTree.
     */
	 public class TreeTableCellRenderer extends JTree implements // {{{
            TableCellRenderer {
        /** Last table/tree row asked to renderer. */
        protected int visibleRow;

        public TreeTableCellRenderer(TreeModel model) {
            super(model);
            //ToolTipManager.sharedInstance().registerComponent(this);//XXX
            this.setRootVisible(false);
            this.setShowsRootHandles(true);
			this.setCellRenderer(renderer);                       
        }

        /**
         * updateUI is overridden to set the colors of the Tree's renderer to
         * match that of the table.
         */
        public void updateUI() {
            super.updateUI();
	    
            // Make the tree's cell renderer use the table's cell selection
            // colors.
            TreeCellRenderer tcr = getCellRenderer();
            if (tcr instanceof DefaultTreeCellRenderer) {
                DefaultTreeCellRenderer dtcr = ((DefaultTreeCellRenderer) tcr);
 
				dtcr.setBorderSelectionColor(null);
                dtcr.setTextSelectionColor(UIManager
                        .getColor("Table.selectionForeground"));
                dtcr.setBackgroundSelectionColor(UIManager
                        .getColor("Table.selectionBackground"));
            }
        }

        /**
         * Sets the row height of the tree, and forwards the row height to the
         * table.
         */
        public void setRowHeight(int rowHeight) {
            if (rowHeight > 0) {
                super.setRowHeight(rowHeight);
                if (TaskTable.this != null
                        && TaskTable.this.getRowHeight() != rowHeight) {
                    TaskTable.this.setRowHeight(getRowHeight());
                }
            }
        }

        /**
         * This is overridden to set the height to match that of the JTable.
         */
        public void setBounds(int x, int y, int w, int h) {
            super.setBounds(x, 0, w, TaskTable.this.getHeight());
        }

        /**
         * Subclassed to translate the graphics such that the last visible row
         * will be drawn at 0,0.
         */
        public void paint(Graphics g) {
            g.translate(0, -visibleRow * getRowHeight());
            super.paint(g);
        }

        /**
         * TreeCellRenderer method. Overridden to update the visible row.
         */
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {
            if (isSelected)
                setBackground(table.getSelectionBackground());
            else
                setBackground(table.getBackground());
            visibleRow = row;
            return this;
        }
	} // }}}

    /**
     * TreeTableCellEditor implementation. Component returned is the JTree.
     */
	 public class TreeTableCellEditor extends AbstractCellEditor implements //{{{
            TableCellEditor {
        public Component getTableCellEditorComponent(JTable table,
                Object value, boolean isSelected, int r, int c) {
            return tree;
        }

        /**
         * Overridden to return false, and if the event is a mouse event it is
         * forwarded to the tree.
         * <p>
         * The behavior for this is debatable, and should really be offered as a
         * property. By returning false, all keyboard actions are implemented in
         * terms of the table. By returning true, the tree would get a chance to
         * do something with the keyboard events. For the most part this is ok.
         * But for certain keys, such as left/right, the tree will
         * expand/collapse where as the table focus should really move to a
         * different column. Page up/down should also be implemented in terms of
         * the table. By returning false this also has the added benefit that
         * clicking outside of the bounds of the tree node, but still in the
         * tree column will select the row, whereas if this returned true that
         * wouldn't be the case.
         * <p>
         * By returning false we are also enforcing the policy that the tree
         * will never be editable (at least by a key sequence).
         */
        public boolean isCellEditable(EventObject e) {
            if (e instanceof MouseEvent) {
                for (int counter = getColumnCount() - 1; counter >= 0; counter--) {
                    if (getColumnClass(counter) == TreeTableModel.class) {
                        MouseEvent me = (MouseEvent) e;
                        MouseEvent newME = new MouseEvent(tree, me.getID(), me
                                .getWhen(), me.getModifiers(), me.getX()
                                - getCellRect(0, counter, true).x, me.getY(),
                                me.getClickCount(), me.isPopupTrigger());
                        tree.dispatchEvent(newME);
                        break;
                    }
                }
            }
            return false;
        }
	} // }}}

    /**
     * ListToTreeSelectionModelWrapper extends DefaultTreeSelectionModel to
     * listen for changes in the ListSelectionModel it maintains. Once a change
     * in the ListSelectionModel happens, the paths are updated in the
     * DefaultTreeSelectionModel.
     */
	 public class ListToTreeSelectionModelWrapper extends // {{{
            DefaultTreeSelectionModel {
        /** Set to true when we are updating the ListSelectionModel. */
        protected boolean updatingListSelectionModel;

        public ListToTreeSelectionModelWrapper() {
            super();
            getListSelectionModel().addListSelectionListener(
                    createListSelectionListener());
        }

        /**
         * Returns the list selection model. ListToTreeSelectionModelWrapper
         * listens for changes to this model and updates the selected paths
         * accordingly.
         */
        public ListSelectionModel getListSelectionModel() {
            return listSelectionModel;
        }

        /**
         * This is overridden to set <code>updatingListSelectionModel</code>
         * and message super. This is the only place DefaultTreeSelectionModel
         * alters the ListSelectionModel.
         */
        public void resetRowSelection() {
            if (!updatingListSelectionModel) {
                updatingListSelectionModel = true;
                try {
                    super.resetRowSelection();
                } finally {
                    updatingListSelectionModel = false;
                }
            }
            // Notice how we don't message super if
            // updatingListSelectionModel is true. If
            // updatingListSelectionModel is true, it implies the
            // ListSelectionModel has already been updated and the
            // paths are the only thing that needs to be updated.
        }

        /**
         * Creates and returns an instance of ListSelectionHandler.
         */
        protected ListSelectionListener createListSelectionListener() {
            return new ListSelectionHandler();
        }

        /**
         * If <code>updatingListSelectionModel</code> is false, this will
         * reset the selected paths from the selected rows in the list selection
         * model.
         */
        protected void updateSelectedPathsFromSelectedRows() {
            if (!updatingListSelectionModel) {
                updatingListSelectionModel = true;
                try {
                    // This is way expensive, ListSelectionModel needs an
                    // enumerator for iterating.
                    int min = listSelectionModel.getMinSelectionIndex();
                    int max = listSelectionModel.getMaxSelectionIndex();

                    clearSelection();
                    if (min != -1 && max != -1) {
                        for (int counter = min; counter <= max; counter++) {
                            if (listSelectionModel.isSelectedIndex(counter)) {
                                TreePath selPath = tree.getPathForRow(counter);

                                if (selPath != null) {
                                    addSelectionPath(selPath);
                                }
                            }
                        }
                    }
                } finally {
                    updatingListSelectionModel = false;
                }
            }
        }

	/**
	 * Class responsible for calling updateSelectedPathsFromSelectedRows
	 * when the selection of the list changse.
	 */
	class ListSelectionHandler implements ListSelectionListener {
            public void valueChanged(ListSelectionEvent e) {
                updateSelectedPathsFromSelectedRows();
            }
        }
	} // }}}
	
	
	/**
	 * Stores expanded treepaths so that they
	 * can be restored after treeStructureChanged-method call
	 * which collapses everything
	 */
	 class ExpansionHandler implements TreeExpansionListener { // {{{
	
		private java.util.Set expanded = new java.util.HashSet();
		
		public void treeExpanded(TreeExpansionEvent e) {
			expanded.add(e.getPath());
		}
		
		public void treeCollapsed(TreeExpansionEvent e) {
			TreePath p = e.getPath();
			int index = p.getPathCount() - 1;
			Object collapsed = p.getLastPathComponent();

			Object[] components = expanded.toArray();
			for(int i=0; i<components.length; i++){
				TreePath epath = (TreePath) components[i];
				if( (epath.getPathCount() > index) && (epath.getPathComponent(index).equals(collapsed))){
					expanded.remove(epath);
				}
			}
		}
		
		/**
		 * Expands stored treepaths in JTree
		 * <p>
		 * If model has been changed (eg. project change) we
		 * still try to expand paths whick do not exist.
		 * We just assume that this is not causing problems,
		 * and as a side effect it preserved tree expansion status
		 * even after project has been changed to some other project 
		 * and then back.
		 * </p>
		 * <p>
		 * It is possible that there will be memory leak
		 * if expanded paths have been removed from model, but
		 * effect of this is quite insignificant.
		 * </p>
		 */
		public void expand(JTree tree){
			Iterator iter = expanded.iterator();
			while(iter.hasNext()){
				tree.expandPath( (TreePath) iter.next() );
			}
			System.out.println(expanded.size());
		}
		
	} // }}}	
	
}