package main.java.memoranda;

public class WeightedEdge {
    private Node source;
    private Node destination;
    private double weight;

    public WeightedEdge(Node source, Node destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    // Getters
    public Node getSource() {
        return source;
    }

    public Node getDestination() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }
}
