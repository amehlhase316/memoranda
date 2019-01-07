/*
 * TaskProgressLabel.java         
 * -----------------------------------------------------------------------------
 * Project           memoranda
 * Package           net.sf.memoranda.ui
 * Created           Apr 5, 2007 12:51:26 PM by alex
 * Latest revision   $Revision: 1.1 $
 *                   $Date: 2007/04/05 08:28:14 $
 *                   $Author: alexeya $
 * Tag               $Name:  $
 *
 * @VERSION@ 
 *
 * @COPYRIGHT@
 * 
 * @LICENSE@ 
 *
 * Revisions:
 * $Log: TaskProgressLabel.java,v $
 * Revision 1.1  2007/04/05 08:28:14  alexeya
 * Fixed: Dates in TaskTable were not localized
 *
 * -----------------------------------------------------------------------------
 */


package main.java.memoranda.ui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;

import main.java.memoranda.Task;

/**
 * <h1>TaskProgressLabel</h1>
 * 
 * Component showing task progress as colorful bar>
 * 
 * @version $Name:  $ $Revision: 1.1 $
 * @author Alex Alishevskikh, alexeya(at)gmail.com
 * 
 */

class TaskProgressLabel extends JLabel{
    TaskTable table;
    int column;
    Task task;
    public TaskProgressLabel( TaskTable table ){
        this.table = table;
        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    }
    public void setTask(Task t){ task = t;}
    public void setColumn(int col){ column = col;}
    
    public void paintComponent(Graphics g) {
        int val = task.getProgress();
        int width = table.getColumnModel().getColumn(column).getWidth();
        int height = table.getRowHeight();
        int p = width * val / 100;
        
        g.setColor(Color.WHITE);
        g.fillRect(0,0,width, height);

        g.setColor( TaskTreeTableCellRenderer.getColorForTaskStatus(task, true) );
        g.fillRect(1, 1, p, height - 2);
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(1, 1, width, height - 2);
        
        setText(val + "%");
        setBounds(0, 0, width, height);
        
        super.paintComponent(g);
    }
}
