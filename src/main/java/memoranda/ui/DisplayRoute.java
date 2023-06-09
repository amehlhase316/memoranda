package main.java.memoranda.ui;

import main.java.memoranda.Node;
import main.java.memoranda.Route;


import javax.swing.*;

public class DisplayRoute extends JDialog {


    private Route route;

    /**
     * Constructor
     * @param parent
     * @param route
     */
    public DisplayRoute(JFrame parent, Route route) {
        super(parent, "Route Details", true);
        this.route = route;

        //Dialog Components
        JTextArea textArea = new JTextArea(20, 50);
        textArea.setEditable(false);


        textArea.setText(route.toString());

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JButton closeButton = new JButton("Close");


        //Dialog Layout
        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
        contentPane.add(scrollPane);
        contentPane.add(Box.createVerticalStrut(10));
        contentPane.add(closeButton);

        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(parent);
        closeButton.addActionListener(e -> this.dispose());
    }

    public static void main(String[] args) {

        Node node = new Node("start", 10,20);
        Node node2 = new Node("finish", 60, 100);

        Route route = new Route(node, 10);
        route.addNode(node2);
        JFrame parent = new JFrame();
        parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parent.setLocationRelativeTo(null);

        DisplayRoute display = new DisplayRoute(parent, route);
        display.setVisible(true);
    }
}
