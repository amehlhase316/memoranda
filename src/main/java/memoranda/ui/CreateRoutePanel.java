package main.java.memoranda.ui;

import main.java.memoranda.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;


public class CreateRoutePanel extends JPanel implements ActionListener {


    private JButton createButton;
    private JTextField stopDuration;
    private JsonHandler jsonHandler;
    private JList<String> routePointList;
    private JComboBox<Object> driverBox;
    private JComboBox<Object> busBox;
    private List<Node> listOfNodes;


    public CreateRoutePanel() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    void jbInit() throws Exception {

        jsonHandler = new JsonHandler();
        String fileName = "nodes1.json";
        jsonHandler.readDriversFromJSON(fileName);
        jsonHandler.readBusesFromJSON(fileName);
        jsonHandler.readNodesFromJSON(fileName);
        listOfNodes = new ArrayList<>();
        buildPanel();
    }

    private void buildPanel() {
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.GRAY);

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));

        // Route Selection
        JPanel routeSelectionPanel = new JPanel();
        routeSelectionPanel.setLayout(new BorderLayout());

        JLabel routeLabel = new JLabel("Select stops for route: ");
        JLabel instructionsLabel = instructionsLabelMaker();
        routeSelectionPanel.add(routeLabel, BorderLayout.NORTH);
        routeSelectionPanel.add(routeSelectionList(), BorderLayout.CENTER);
        routeSelectionPanel.add(instructionsLabel,BorderLayout.SOUTH);

        mainPanel.add(routeSelectionPanel, BorderLayout.NORTH);

        // Other Components
        JPanel otherComponentsPanel = new JPanel();
        otherComponentsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel stopDurationLabel = new JLabel("Stop Duration:");
        stopDuration = new JTextField(10);
        JLabel driverLabel = new JLabel("Driver:");

        JLabel busLabel = new JLabel("Bus:");

        otherComponentsPanel.add(stopDurationLabel, gbc);
        otherComponentsPanel.add(stopDuration, gbc);
        otherComponentsPanel.add(driverLabel, gbc);
        otherComponentsPanel.add(driverPanel(), gbc);
        otherComponentsPanel.add(busLabel, gbc);
        otherComponentsPanel.add(busPanel(), gbc);

        // Create Button
        JPanel buttonPanel = new JPanel();
        createButton = new JButton("Create Route");
        createButton.addActionListener(this);
        buttonPanel.add(createButton);

        mainPanel.add(otherComponentsPanel, BorderLayout.WEST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JLabel instructionsLabelMaker() {
        JLabel label = new JLabel("<html>To select multiple stops at once:<br>" +
                "SHIFT + mouse click or Control key + mouse click on desired stops<br>" +
                "then click on create route once driver, bus, and stop duration have been chosen</html>");
        return label;
    }


    private JScrollPane routeSelectionList() {
        routePointList = new JList(jsonHandler.getNodesString().toArray());
        routePointList.setVisibleRowCount(8); // Set the number of visible rows
        routePointList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        addRoutePointListListener();
        JScrollPane sp = new JScrollPane(routePointList);
        sp.setBounds(10, 70, 100, 100);
        return sp;
    }

    private void addRoutePointListListener() {
        routePointList.addListSelectionListener(e -> {
            List<String> nodes = routePointList.getSelectedValuesList();
            listOfNodes = new ArrayList<>();

            try {
                for (String selectedNode : nodes) {
                    for (Node node : jsonHandler.getNodes()) {
                        if (node.getId().equals(selectedNode)) {
                            listOfNodes.add(node);
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private JComboBox<Object> driverPanel() {
        driverBox = new JComboBox<>();
        DefaultComboBoxModel<Object> driverModel = new DefaultComboBoxModel<>();
        jsonHandler.getDriversToString().forEach(driverModel::addElement);
        driverBox.setModel(driverModel);
        driverBox.setVisible(true);
        driverBox.setBounds(10,310,100,25);
        return driverBox;
    }

    private JComboBox<Object> busPanel() {
        busBox = new JComboBox<>();
        DefaultComboBoxModel<Object> busModel = new DefaultComboBoxModel<>();
        jsonHandler.getBusesToString().forEach(busModel::addElement);
        busBox.setModel(busModel);
        busBox.setVisible(true);
        busBox.setBounds(10,360,100,25);
        return busBox;
    }

    private void createRoute() {
        String driverName = (String) driverBox.getSelectedItem();
        String busId = (String) busBox.getSelectedItem();
        double stopTime = Double.parseDouble(stopDuration.getText());
        Route route = new Route((ArrayList<Node>) listOfNodes, stopTime);

        for(Driver driver : jsonHandler.getDriverList()) {
            if(driver.getName().equals(driverName)) {
                route.setDriver(driver);
            }
        }

        for(Bus bus : jsonHandler.getBusList()) {
            if(bus.getId().equals(busId)) {
                route.setBus(bus);
            }
        }

        jsonHandler.writeRouteToJson(route);
        routePointList.clearSelection();
        this.repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == createButton) {
            createRoute();

        }

        message();
    }

    private void message() {

        JOptionPane.showConfirmDialog(this, "Route created successfully", "Message", JOptionPane.DEFAULT_OPTION);
    }


    public static void main(String[] args) {

                CreateRoutePanel createRoutePanel = new CreateRoutePanel();

                JFrame frame = new JFrame("Create Route");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(createRoutePanel);
                frame.pack();
                frame.setVisible(true);
    }

}
