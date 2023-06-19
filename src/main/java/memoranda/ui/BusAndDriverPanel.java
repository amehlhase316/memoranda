package main.java.memoranda.ui;

import main.java.memoranda.*;
import main.java.memoranda.util.Local;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class BusAndDriverPanel extends JPanel {
    private JButton createDriverButton;
    private JButton createBusButton;

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel listPanel = new JPanel();
    JScrollPane driverScrollPane = new JScrollPane();
    JScrollPane busScrollPane = new JScrollPane();
    JPanel driverDetail;
    JsonHandler jsonHandler = new JsonHandler();
    DriverList driverList;
    //buslist here

    JList<Driver> driverJList = new JList<>();

    public BusAndDriverPanel() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    private void jbInit() throws Exception {
        //Import the drivers from the json file and populate driverList
    	jsonHandler.readDriversFromJSON("memoranda/nodes1.json");
        driverList = new DriverList(jsonHandler.drivers);

        //set the main panel layout to be a borderlayout
        this.setLayout(borderLayout1);
        //listPanel is set as a horizontal boxlayout
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.X_AXIS));
        driverScrollPane.getViewport().setBackground(Color.white);
        busScrollPane.getViewport().setBackground(Color.blue);

        //Edit the scroll bars for each scrollpane
        JScrollBar driverVertScrollBar = driverScrollPane.getVerticalScrollBar();
        driverVertScrollBar.setUnitIncrement(25);
        driverVertScrollBar.setBlockIncrement(50);
        JScrollBar busVertScrollBar = busScrollPane.getVerticalScrollBar();
        busVertScrollBar.setUnitIncrement(25);
        busVertScrollBar.setBlockIncrement(50);

        //Add lists to the listPanel
        listPanel.add(driverScrollPane);
        listPanel.add(busScrollPane);

        //Add listPanel to the main window
        this.add(listPanel);

        BusAndDriverPanel.PopupListener ppListener = new BusAndDriverPanel.PopupListener();
        driverScrollPane.addMouseListener(ppListener);

        //buildTopPanel();

        // Side Panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(getWidth(), 75));
        //topPanel.setBackground(Color.white);


        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        createDriverButton = new JButton("Create Driver");
        createDriverButton.setMaximumSize(new Dimension(50, 50));
        createDriverButton.setToolTipText(Local.getString("Create a New Driver"));
        createDriverButton.setPreferredSize(new Dimension(50, 50));
        createDriverButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createDriverButton_ActionPerformed(e);
            }
        });

        buttonPanel.add(createDriverButton);

        topPanel.add(buttonPanel, BorderLayout.WEST);
        this.add(topPanel, BorderLayout.NORTH);

    }


    private void createDriverButton_ActionPerformed(ActionEvent e) {
        DriverDialog dialogBox = new DriverDialog(App.getFrame(), Local.getString("New Driver"));
        //Driver object has (int) ID, (String) Name, (String) Phone Number

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dialogBox.setLocation((frmSize.width - dialogBox.getSize().width) / 2 + loc.x, (frmSize.height - dialogBox.getSize().height) / 2 + loc.y);
        dialogBox.setVisible(true);
        if (dialogBox.CANCELLED)
            return;

        driverList.addDriver(dialogBox.tempDriver);
        for(Driver driver : driverList)
            System.out.println(driver.getName());
        updateList();
    }

    private void updateList() {
        listPanel.removeAll();

        for(Driver driver : driverList) {
            JLabel tempLabel = new JLabel(driver.getName());
            driverScrollPane.add(tempLabel);
        }
        driverScrollPane.revalidate();
        driverScrollPane.repaint();
        listPanel.add(driverScrollPane);
        listPanel.add(busScrollPane);
    }


    private class PopupListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            //            if ((e.getClickCount() == 2) && (eventsTable.getSelectedRow() > -1))
            //                editEventB_actionPerformed(null);
        }

        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                //                eventPPMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }

    }

}
