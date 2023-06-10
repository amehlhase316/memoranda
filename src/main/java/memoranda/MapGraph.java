package main.java.memoranda;

import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class MapGraph extends JPanel {
    private List<Node> nodes;
    private List<WeightedEdge> edges;
    private List<Node> route;
    
    private double longitudeMin;	// left
    private double longitudeMax;	// right
    private double latitudeMin;		// bottom
    private double latitudeMax;		// top
    
    private BufferedImage image;

    public MapGraph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();

    	getImage("/map1.png");
    	
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
//        this.longitudeMin = longitudeMin;
//        this.longitudeMax = longitudeMax;
//        this.latitudeMin = latitudeMin;
//        this.latitudeMax = latitudeMax;
    }
    
    public void getImage(String fileName) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter and Setters for List of nodes
     * @return nodes
     */
    public List<Node> getNodes() {
        return nodes;
    }


    public void addNode(String id, double latitude, double longitude) {
        nodes.add(new Node(id, latitude, longitude));
    }

    public void addEdge(WeightedEdge edge) {
        edges.add(edge);
    }
    
    public void addRoutePoint(Node n) {
        route.add(n);
    }
    
    public List<Node> getShortestRoute(String sourceId, String destinationId) {
        List<Node> shortestPath = new ArrayList<>();

        // Return the sequence of nodes representing the shortest path
        return shortestPath;
    }
    
    public void readNodesFromJSON(String filename) {
        try {
        	JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filename));

            JSONArray nodesArray = (JSONArray) jsonObject.get("nodes");

            for (Object obj : nodesArray) {
                JSONObject nodeObj = (JSONObject) obj;
                String id = (String) nodeObj.get("id");
                double latitude = Double.parseDouble((String) nodeObj.get("lat"));
                double longitude = Double.parseDouble((String) nodeObj.get("lon"));

                addNode(id, latitude, longitude);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, getWidth(), getHeight());
        Graphics2D g2d = (Graphics2D) g;

        double refLatitude = 0;
    	double refLongitude = 0;
    	for (Node node : nodes)
    		if (node.getId().equals("Reference")) {
        		refLatitude = node.getLatitude();
        		refLongitude = node.getLongitude();
        	}
        
        if (image != null) {
            g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), this);
        }

        Stroke str1 = new BasicStroke(2f);
        g2d.setColor(Color.GREEN);
        g2d.setStroke(str1);;

        g2d.setColor(Color.RED);
        // Iterate over the nodes and draw them on the panel
        for (Node node : nodes) {
        	if (node.getId().equals("Reference")) {
        		continue;
        	} 
            // Scale the longitude and latitude to fit within the panel dimensions
            int x = (int) ((((refLongitude - node.getLongitude()) * -1) / 0.0000206) + 222);
            int y = (int) (((refLatitude - node.getLatitude()) / 0.00001706) + 135);
            node.setX(x);
            node.setY(y);

            // Draw a dot for each node
            g.setColor(Color.DARK_GRAY);
            g.fillOval(x, y, 15, 15);
//            g.fillOval(222, 140, 10, 10); // reference point
//            g.fillOval(385, 250, 10, 10);
//            g.fillOval(1053, 300, 10, 10);
//            g.fillOval(1053, 700, 10, 10);
//            g.fillOval(385, 700, 10, 10);

            // Draw the node's ID
            g.setColor(Color.BLACK);
            g.drawString(node.getId(), x, y);
        }

        // Draw route
//        g2d.setColor(Color.RED);
//        if (route.size() > 1) {
//            Point2D.Double prevPoint = route.get(0);
//            for (int i = 1; i < route.size(); i++) {
//                Point2D.Double currPoint = route.get(i);
//                int x1 = (int) ((prevPoint.x - longitudeMin) * width / (longitudeMax - longitudeMin));
//                int y1 = (int) ((latitudeMax - prevPoint.y) * height / (latitudeMax - latitudeMin));
//                int x2 = (int) ((currPoint.x - longitudeMin) * width / (longitudeMax - longitudeMin));
//                int y2 = (int) ((latitudeMax - currPoint.y) * height / (latitudeMax - latitudeMin));
//                g2d.drawLine(x1, y1, x2, y2);
//                prevPoint = currPoint;
//            }
//        }
    }
}
