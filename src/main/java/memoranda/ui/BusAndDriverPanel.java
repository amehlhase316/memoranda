package main.java.memoranda.ui;

import main.java.memoranda.*;
import main.java.memoranda.BusList;
import main.java.memoranda.util.Local;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BusAndDriverPanel extends JPanel {
    JButton createDriverButton, createBusButton;
    JScrollPane driverScrollPane, busScrollPane;
    JPanel columnPane, listPane, driverPane, busPane, driverColumns, busColumns;
    JsonHandler jsonHandler = new JsonHandler();
    DriverList driverList;
    BusList busList;


    /**
     * Default Constructor.
     */
    public BusAndDriverPanel() {
        try {
            panelInitialization();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    /**
     * Initializes the panel and its respective lists.
     */
    private void panelInitialization() {
        //Import the drivers from the json file and populate driverList
        String fileName = "memoranda/nodes1.json";
    	jsonHandler.readDriversFromJSON(fileName);
        driverList = new DriverList(jsonHandler.driverList);
        jsonHandler.readBusesFromJSON(fileName);
        busList = new BusList(jsonHandler.busList);

        //set the main panel layout to be a borderlayout
        this.setLayout(new BorderLayout());

        //########################################## Top Panel ##########################################
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(getWidth(), 75));

        //Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        //Create Driver Button
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

        //Create Bus Button
        createBusButton = new JButton();

        // rescale image
        ImageIcon imageIcon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/Add_Bus.png"));
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(40, 50, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);

        createBusButton.setIcon(imageIcon);
        createBusButton.setMaximumSize(new Dimension(40, 50));
        createBusButton.setToolTipText(Local.getString("Create a New Bus"));
        createBusButton.setPreferredSize(new Dimension(40, 50));
        createBusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createBusButton_ActionPerformed(e);
            }
        });

        //Add buttons to buttonPanel
        buttonPanel.add(createDriverButton);
        buttonPanel.add(createBusButton);

        //Add button panel to the top panel
        topPanel.add(buttonPanel, BorderLayout.WEST);


        //########################################## Columns ##########################################
        //displayPane the columnPanes **NONE OF THIS COLUMN STUFF WORKS YET**
        columnPane = new JPanel();
        columnPane.setLayout(new BoxLayout(columnPane, BoxLayout.X_AXIS));
        columnPane.setPreferredSize(new Dimension(getWidth(), 50));

        //Set up the columnPanes
        driverColumns = new JPanel();
        driverColumns.setLayout(new GridLayout());
        driverColumns.setPreferredSize(new Dimension(getWidth(), 50));
        busColumns = new JPanel();
        busColumns.setLayout(new GridLayout());

        //set up the columns
        JPanel driverID = new JPanel();
        driverID.add(new JLabel("Driver ID"));
        JPanel driverName = new JPanel();
        driverName.add(new JLabel("Driver Name"));
        JPanel driverPhone = new JPanel();
        driverPhone.add(new JLabel("Driver Phone"));

        //Add columns to driverColumns pane
        driverColumns.add(driverID);
        driverColumns.add(driverName);
        driverColumns.add(driverPhone);

        //Add columnPanes to displayPane
        columnPane.add(driverColumns);
        columnPane.add(busColumns);


        //########################################## Lists ##########################################
        //listPane is set as a horizontal boxlayout
        listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.X_AXIS));

        //Driver List
        driverPane = new JPanel();
        driverPane.setLayout(new GridLayout(50, 1));
        //Bus List
        busPane = new JPanel();
        busPane.setLayout(new GridLayout(50, 1));

        //Update list and add both bus and driver panes to their respective lists
        updateList();
        driverScrollPane  = new JScrollPane(driverPane);
        driverScrollPane.setPreferredSize(new Dimension(getWidth(),getHeight()));
        busScrollPane = new JScrollPane(busPane);
        busScrollPane.setPreferredSize((new Dimension(getWidth(), getHeight())));

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



        //add top panel and lists to main panel
        this.add(topPanel, BorderLayout.NORTH);
        this.add(columnPane);
        this.add(listPane);
    }

    private void createBusButton_ActionPerformed(ActionEvent e) {
        //TODO: Insert call to BusDialog here
        BusDialog dialogBox = new BusDialog(App.getFrame(), Local.getString("New Bus"));
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dialogBox.setLocation((frmSize.width - dialogBox.getSize().width) / 2 + loc.x, (frmSize.height - dialogBox.getSize().height) / 2 + loc.y);
        dialogBox.setVisible(true);
        if (dialogBox.CANCELLED)
            return;
        if(busList.hasBus(dialogBox.tempBus.getID()))
            return; //temp solution so no duplicate IDs are made
        busList.addBus(dialogBox.tempBus);

        updateList();
    }

    /**
     * Opens a pop-up window to create a driver.
     * @param e ActionEvent
     */
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

    /**
     * Internal class to associate a delete button with a driver.
     */
    public class DeleteButton extends JButton {
        private final Driver driver;
        private final Bus bus;

        public DeleteButton(Driver driver) {
            this.setBackground(Color.red);
            this.setPreferredSize(new Dimension(75, 25));
            this.setText("Delete");
            this.driver = driver;
            this.bus = null;
            this.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    deleteButton_ActionPerformed(e);
                }
            });
        }

        public DeleteButton(Bus bus) {
            this.setBackground(Color.red);
            this.setPreferredSize(new Dimension(75, 25));
            this.setText("Delete");
            this.bus = bus;
            this.driver = null;
            this.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    deleteButton_ActionPerformed(e);
                }
            });
        }

        private void deleteButton_ActionPerformed(ActionEvent e) {
            if(this.bus == null)
                driverList.removeDriver(this.driver);
            else
                busList.removeBus(this.bus);
            updateList();
        }
    }

    /**
     * Updates the displayed list(s)
     */
    private void updateList() {
        //Remove all items from both lists
        driverPane.removeAll();
        busPane.removeAll();

        //Update the driverPane
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
        }
        driverPane.revalidate();
        driverPane.repaint();

        //Update the busPane
        for(Bus bus : busList) {
            //Create a temporary panel to hold all the driver information
            JPanel tempPane = new JPanel();
            tempPane.setLayout(new GridBagLayout());

            //Create the individual sections for the driver information and the delete button
            JLabel busID = new JLabel(String.valueOf(bus.getID()));
            JLabel busSeats = new JLabel(String.valueOf(bus.getSeats()));
            DeleteButton deleteButton = new DeleteButton(bus);

            //Update the properties of each item
            busID.setPreferredSize(new Dimension(100, 25));
            busSeats.setPreferredSize(new Dimension(200,25));

            //Add the items to the temporary pane
            tempPane.add(busID, createConstraints(0,0));
            tempPane.add(busSeats, createConstraints(1,0));
            tempPane.add(deleteButton, createConstraints(2,0));

            //Add the tempPane to the driverPane
            busPane.add(tempPane);
            busPane.setAlignmentX(tempPane.LEFT_ALIGNMENT);
        }
        busPane.revalidate();
        busPane.repaint();
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
}
