package main.java.memoranda.ui;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.*;

import main.java.memoranda.*;
import main.java.memoranda.date.*;
import main.java.memoranda.util.*;

import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class TaskTableSorter extends TaskTableModel{
	
	// -1 == no sorting
	int sorting_column = -1;
	
	// sort opposite direction
	boolean opposite = false;
	
	Comparator comparator = new Comparator(){
		public int compare(Object o1, Object o2){
			if(sorting_column == -1) return 0;
			if( (o1 instanceof Task) == false) return 0;
			if( (o2 instanceof Task) == false ) return 0;
			
			
			Task task1 = (Task) o1;
			Task task2 = (Task) o2;
			
			// based on TaskTableModel.columnNames
			switch(sorting_column){
				case 1: return task1.getText().compareTo(task2.getText());
				case 2: return task1.getStartDate().getDate().compareTo(task2.getStartDate().getDate());
				case 3: return task1.getEndDate().getDate().compareTo(task2.getEndDate().getDate());
				case 0: // task priority, same as 4
				case 4: return task1.getPriority() - task2.getPriority();
				case 5: return task1.getStatus( CurrentDate.get() ) - task2.getStatus( CurrentDate.get() );
				case 6: return task1.getProgress() - task2.getProgress();
			}
			
			return 0;
		}
	};
	
	public TaskTableSorter( TaskTable table ){
		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.addMouseListener( new MouseHandler() );
		tableHeader.setDefaultRenderer( new SortableHeaderRenderer());
	}
	
	public Object getChild(Object parent, int index) {
		Collection c = null;
		
		if (parent instanceof Project){
			if( activeOnly() ) c = CurrentProject.getTaskList().getActiveSubTasks(null, CurrentDate.get());
			else c = CurrentProject.getTaskList().getTopLevelTasks();
		}
		else{
			Task t = (Task) parent;
			if(activeOnly()) c = CurrentProject.getTaskList().getActiveSubTasks(t.getID(), CurrentDate.get());
			else c = t.getSubTasks();
		}
		
		Object array[] = c.toArray();
		Arrays.sort(array, comparator);
		if(opposite){
			return array[ array.length - index - 1];
		}
		return array[index];
	}

	
    
    private class MouseHandler extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            JTableHeader h = (JTableHeader) e.getSource();
            TableColumnModel columnModel = h.getColumnModel();
            int viewColumn = columnModel.getColumnIndexAtX(e.getX());
            int column = columnModel.getColumn(viewColumn).getModelIndex();
            if (column != -1) {
		sorting_column = column;
		
		// 0 == priority icon column
		// 4 == priority text column
		if(column == 0) sorting_column = 4;
		
		if(e.isControlDown()) sorting_column = -1;
		else opposite = !opposite;
		
		TaskTable treetable = ( (TaskTable) h.getTable());
		
		//java.util.Collection expanded = treetable.getExpandedTreeNodes();
		
		treetable.tableChanged();
		//treetable.setExpandedTreeNodes(expanded);
		//h.updateUI();
		h.resizeAndRepaint();
            }
        }
    }
    
	/**
	* Render sorting header differently
	*/
	private class SortableHeaderRenderer implements TableCellRenderer {
	    
	    
	    
		public Component getTableCellRendererComponent(JTable table, 
							       Object value,
							       boolean isSelected, 
							       boolean hasFocus,
							       int row, 
							       int column) {
			JComponent c = new JLabel(value.toString());
			if(column == sorting_column){
				c.setFont(c.getFont().deriveFont(Font.BOLD));
				//c.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
			}
			else c.setFont(c.getFont().deriveFont(Font.PLAIN));
			return c;
		}
	}
	
}
