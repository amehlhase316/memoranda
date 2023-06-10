package main.java.memoranda.ui;

import main.java.memoranda.*;


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

    BorderLayout borderLayout1 = new BorderLayout();
    JScrollPane scrollPane = new JScrollPane();
    MapGraph mapGraph;

    public RoutePanel() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    void jbInit() throws Exception {

        mapGraph = new MapGraph();
        mapGraph.readNodesFromJSON("nodes1.json");
        route = new Route(5.0);

        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);
        scrollPane.getViewport().add(mapGraph);
        mapGraph.repaint();


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
        startField.setBounds(10,50,50,25);

        finishLabel.setBounds(10,70,100,40);
        finishField.setBounds(10,100,50,25);

        driverLabel.setBounds(10,120,100,40);
        driverField.setBounds(10,150,50,25);

        busLabel.setBounds(10,170,100,40);
        busField.setBounds(10,200,50,25);

        createButton = new JButton("Create");
        createButton.addActionListener(this);

        buttonPanel.add(createButton);

        //        JButton modifyButton = new JButton("Modify");
        //        modifyButton.addActionListener(this);
        //        buttonPanel.add(modifyButton);
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

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == createButton) {
            createRoute();
        }

    }

    private void createRoute(){

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
