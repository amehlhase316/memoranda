package main.java.memoranda.ui;

import main.java.memoranda.*;
import main.java.memoranda.util.Local;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Color.*;

public class BusAndDriverPanel extends JPanel {
    private JButton createDriverButton;
    private JButton createBusButton;

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel listPane = new JPanel();
    JScrollPane driverScrollPane;
    JPanel driverPane;
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
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.X_AXIS));

        driverPane = new JPanel();
        driverPane.setLayout(new GridLayout(50, 1));
        updateList();
        driverScrollPane  = new JScrollPane(driverPane);
        driverScrollPane.setPreferredSize(new Dimension(getWidth(),getHeight()));


        busScrollPane.getViewport().setBackground(blue);

        //Edit the scroll bars for each scrollpane
        JScrollBar driverVertScrollBar = driverScrollPane.getVerticalScrollBar();
        driverVertScrollBar.setUnitIncrement(25);
        driverVertScrollBar.setBlockIncrement(50);
        JScrollBar busVertScrollBar = busScrollPane.getVerticalScrollBar();
        busVertScrollBar.setUnitIncrement(25);
        busVertScrollBar.setBlockIncrement(50);

        //Add lists to the listPanel
        listPane.add(driverScrollPane);
        listPane.add(busScrollPane);

        //Add listPanel to the main window
        this.add(listPane);

        BusAndDriverPanel.PopupListener ppListener = new BusAndDriverPanel.PopupListener();
        //driverScrollPane.addMouseListener(ppListener);

        //Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(getWidth(), 75));


        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        createDriverButton = new JButton();
        createDriverButton.setIcon(
                new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/Add_Driver.png")));
        createDriverButton.setMaximumSize(new Dimension(40, 50));
        createDriverButton.setToolTipText(Local.getString("Create a New Driver"));
        createDriverButton.setPreferredSize(new Dimension(40, 50));
        createDriverButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createDriverButton_ActionPerformed(e);
            }
        });

        buttonPanel.add(createDriverButton);

        topPanel.add(buttonPanel, BorderLayout.WEST);
        this.add(topPanel, BorderLayout.NORTH);
    }

    /*private void deleteDriverButton_ActionPerformed(ActionEvent e, int id) {
        driverList.removeDriver(id);
        updateList();
    }*/

    private void createDriverButton_ActionPerformed(ActionEvent e) {
        DriverDialog dialogBox = new DriverDialog(App.getFrame(), Local.getString("New Driver"));
        //Driver object has (int) ID, (String) Name, (String) Phone Number

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dialogBox.setLocation((frmSize.width - dialogBox.getSize().width) / 2 + loc.x, (frmSize.height - dialogBox.getSize().height) / 2 + loc.y);
        dialogBox.setVisible(true);
        if (dialogBox.CANCELLED)
            return;
        if(driverList.hasDriver(dialogBox.tempDriver.getID()))
            return; //temp solution so no duplicate IDs are made
        driverList.addDriver(dialogBox.tempDriver);

        updateList();
    }

    public class DeleteButton extends JButton {
        private final Driver driver;

        public DeleteButton(Driver driver) {
            this.setBackground(Color.red);
            this.setPreferredSize(new Dimension(75, 25));
            this.setText("Delete");
            this.driver = driver;
            this.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    deleteDriverButton_ActionPerformed(e);
                }
            });
        }
        private void deleteDriverButton_ActionPerformed(ActionEvent e) {
            driverList.removeDriver(this.driver);
            updateList();
        }
    }

    private void updateList() {
        driverPane.removeAll();
        for(Driver driver : driverList) {
            //Create a temporary panel to hold all the driver information
            JPanel tempPane = new JPanel();
            tempPane.setLayout(new GridBagLayout());

            //Create the individual sections for the driver information and the delete button
            JLabel driverID = new JLabel(String.valueOf(driver.getID()));
            JLabel driverName = new JLabel(driver.getName());
            JLabel driverPhone = new JLabel(driver.getPhone());
            DeleteButton deleteButton = new DeleteButton(driver);

            //Update the properties of each item
            driverID.setPreferredSize(new Dimension(100, 25));
            driverName.setPreferredSize(new Dimension(200,25));
            driverPhone.setPreferredSize(new Dimension(100, 25));

            //Add the items to the temporary pane
            tempPane.add(driverID, createConstraints(0,0));
            tempPane.add(driverName, createConstraints(1,0));
            tempPane.add(driverPhone, createConstraints(2,0));
            tempPane.add(deleteButton, createConstraints(3,0));

            //Add the tempPane to the driverPane
            driverPane.add(tempPane);
            driverPane.setAlignmentX(tempPane.LEFT_ALIGNMENT);
            driverPane.revalidate();
            driverPane.repaint();
        }
    }

    private GridBagConstraints createConstraints(int x, int y) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        int gap = 3;
        constraints.insets = new Insets(gap, gap + 2 * gap * x, gap, gap);
        return constraints;
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
