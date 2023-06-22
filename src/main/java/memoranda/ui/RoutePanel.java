package main.java.memoranda.ui;

import main.java.memoranda.*;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoutePanel extends JPanel implements ActionListener {

    private Route route;
    private JButton createButton;
    private JLabel startLabel;
    private JLabel finishLabel;
    private JTextField startField;
    private JTextField finishField;

    private JLabel driverLabel;
    private JLabel busLabel;
    private JTextField driverField;
    private JTextField busField;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JScrollPane scrollPane = new JScrollPane();
    private JsonHandler jsonHandler = new JsonHandler();
    private MapGenerator mapGen;

    private JList<String> startPointList;
    private JList<String> endPointList;
    private JComboBox<String> driverBox;
    private JComboBox<String> busBox;


    public RoutePanel() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    void jbInit() throws Exception {

    	jsonHandler = new JsonHandler();
        jsonHandler.readNodesFromJSON("nodes1.json");
        mapGen = new MapGenerator(jsonHandler.nodes);
        route = new Route(5.0);

        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);
        scrollPane.getViewport().add(mapGen);
        mapGen.repaint();


        JScrollBar vertScrollBar = scrollPane.getVerticalScrollBar();
        vertScrollBar.setUnitIncrement(25);
        vertScrollBar.setBlockIncrement(50);
        this.add(scrollPane, BorderLayout.CENTER);

        RoutePanel.PopupListener ppListener = new RoutePanel.PopupListener();
        scrollPane.addMouseListener(ppListener);

        // Side Panel
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(200, getHeight()));
        sidePanel.setBackground(Color.white);


        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        createButton = new JButton("Create");
        createButton.addActionListener(this);
        buttonPanel.add(createButton);

//        JButton modifyButton = new JButton("Modify");
//        modifyButton.addActionListener(this);
//        buttonPanel.add(modifyButton);

        sidePanel.add(buttonPanel, BorderLayout.CENTER);

        buildSidePanel();


    }


    private void buildSidePanel() {
        // Side Panel
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(200, getHeight()));
        sidePanel.setBackground(Color.white);


        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        startLabel = new JLabel("Start of Route: ");
        finishLabel = new JLabel("End of Route: ");
        startField =  new JTextField(15);
        finishField = new JTextField(15);
        driverLabel = new JLabel("Driver: ");
        busLabel = new JLabel("Bus: ");
        driverField =  new JTextField(15);
        busField = new JTextField(15);

        startLabel.setBounds(10,20,100,40);

        finishLabel.setBounds(10,70,100,40);

        driverLabel.setBounds(10,120,100,40);


        busLabel.setBounds(10,170,100,40);


        createButton = new JButton("Create");
        createButton.addActionListener(this);


        initializeDropdown();
        buttonPanel.add(createButton);

        //        JButton modifyButton = new JButton("Modify");
        //        modifyButton.addActionListener(this);
        //        buttonPanel.add(modifyButton);

        sidePanel.add(startPointList);
        sidePanel.add(endPointList);
        sidePanel.add(startLabel);
        sidePanel.add(startField);
        sidePanel.add(finishLabel);
        sidePanel.add(finishField);
        sidePanel.add(driverLabel);
        sidePanel.add(driverField);
        sidePanel.add(busLabel);
        sidePanel.add(busField);

        sidePanel.add(buttonPanel, BorderLayout.CENTER);
        this.add(sidePanel, BorderLayout.EAST);
    }

    private void initializeDropdown() {
        startPointList = new JList<>();
        endPointList = new JList<>();
        driverBox = new JComboBox<>();
        busBox = new JComboBox<>();

        startPointList.setBounds(10,50,80,25);
        endPointList.setBounds(10,100,50,25);
        driverBox.setBounds(10,150,50,25);
        busBox.setBounds(10,200,50,25);

        DefaultListModel listModel = new DefaultListModel();
        jsonHandler.getNodesString().forEach(listModel::addElement);
        startPointList.setModel(listModel);
        endPointList.setModel(listModel);

        //Set up nce Driver and bus json objects exists, and JsonHandler manipulates the same
//        DefaultComboBoxModel busModel = new DefaultComboBoxModel();
//        DefaultComboBoxModel driverModel = new DefaultComboBoxModel();
//
//        driverBox.setModel(model);
//        busBox.setModel(model);
    }

//    private void createRoute() {
//        String selectedChoice = (String) choicesComboBox.getSelectedItem();
//        JSONObject routeObject = new JSONObject();
//        routeObject.put( , selectedChoice);
//
//        // Save the routeObject to a JSON file or perform further processing
//    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == createButton) {
//            createRoute();
        }

    }

    class PopupListener extends MouseAdapter {

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
