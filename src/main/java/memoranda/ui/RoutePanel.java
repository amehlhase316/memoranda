package main.java.memoranda.ui;

import main.java.memoranda.*;


import javax.swing.*;

import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class RoutePanel extends JPanel {

    private JScrollPane scrollPane;
    private JsonHandler jsonHandler;
    private MapGenerator mapGen;


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
        String fileName = "nodes1.json";
        jsonHandler.readNodesFromJSON(fileName);
        mapGen = new MapGenerator(jsonHandler.getNodes());
        scrollPane = new JScrollPane();

        this.setLayout(new GridLayout(0,2));
        scrollPane.getViewport().setBackground(Color.DARK_GRAY);
        scrollPane.getViewport().add(mapGen);
        scrollPane.setPreferredSize(new Dimension(900, 800));
        mapGen.repaint();


        JScrollBar vertScrollBar = scrollPane.getVerticalScrollBar();
        vertScrollBar.setUnitIncrement(25);
        vertScrollBar.setBlockIncrement(50);
        this.add(scrollPane, BorderLayout.WEST);

        RoutePanel.PopupListener ppListener = new RoutePanel.PopupListener();
        scrollPane.addMouseListener(ppListener);

        buildSidePanel();
    }


    private void buildSidePanel() {
        // Side Panel
        CreateRoutePanel routePanel = new CreateRoutePanel();
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(2,1));
        sidePanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
        sidePanel.setBackground(Color.BLACK);

        sidePanel.add(routePanel);

        this.add(sidePanel, BorderLayout.EAST);
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
