package main.java.memoranda;

import java.util.*;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Graph {
    private Map<String, Node> nodes;
    private List<WeightedEdge> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new ArrayList<>();
    }

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
    }

    public void addEdge(WeightedEdge edge) {
        edges.add(edge);
    }
    
    public List<Node> getShortestRoute(String sourceId, String destinationId) {
        // Check if source and destination nodes exist in the graph
        if (!nodes.containsKey(sourceId) || !nodes.containsKey(destinationId)) {
            throw new IllegalArgumentException("Invalid source or destination node.");
        }

        // Create a map to store distances from the source node
        Map<Node, Double> distances = new HashMap<>();

        // Create a map to store the previous node in the shortest path
        Map<Node, Node> previous = new HashMap<>();

        // Initialize all distances to infinity except the source node (0 distance)
        for (Node node : nodes.values()) {
            distances.put(node, Double.POSITIVE_INFINITY);
        }
        distances.put(nodes.get(sourceId), 0.0);

        // Create a priority queue to track nodes with the minimum distance
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));

        // Add the source node to the queue
        queue.add(nodes.get(sourceId));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            // Stop if the destination node is reached
            if (current.getId().equals(destinationId)) {
                break;
            }

            // Explore the neighboring nodes
            for (WeightedEdge edge : edges) {
                if (edge.getSource().equals(current)) {
                    Node neighbor = edge.getDestination();
                    double distanceThroughCurrent = distances.get(current) + edge.getWeight();

                    // Update the distance and previous node if a shorter path is found
                    if (distanceThroughCurrent < distances.get(neighbor)) {
                        distances.put(neighbor, distanceThroughCurrent);
                        previous.put(neighbor, current);
                        queue.add(neighbor);
                    }
                }
            }
        }

        // Reconstruct the shortest path from source to destination
        List<Node> shortestPath = new ArrayList<>();
        Node currentNode = nodes.get(destinationId);
        while (currentNode != null) {
            shortestPath.add(0, currentNode);
            currentNode = previous.get(currentNode);
        }

        // Return the sequence of nodes representing the shortest path
        return shortestPath;
    }
    
    public void readNodesFromJSON(String filename) {
        try {
            JSONParser parser = new JSONParser();
            JSONArray nodesArray = (JSONArray) parser.parse(new FileReader(filename));

            for (Object obj : nodesArray) {
                JSONObject nodeObj = (JSONObject) obj;
                String id = (String) nodeObj.get("id");
                double latitude = (double) nodeObj.get("latitude");
                double longitude = (double) nodeObj.get("longitude");

                Node node = new Node(id, latitude, longitude);
                addNode(node);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
