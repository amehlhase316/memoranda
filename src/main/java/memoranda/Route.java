package main.java.memoranda;

// Route(nodes, length, duration)

import java.util.ArrayList;

public class Route {
    private ArrayList<Node> nodes;
    private double length; // in km
    private double duration; // in minutes

    double stopDuration; // in minutes

    Route(ArrayList<Node> n, double sd)
    {
        nodes = n;
        length = calculateLength();
        duration = calculateDuration();
        stopDuration = sd;
    }

    Route(Node initialNode, double sd){
        nodes.add(initialNode);
        length = calculateLength();
        duration = calculateDuration();
        stopDuration = sd;

    }

    public double getLength(){
        return length;
    }

    public double getDuration(){
        return duration;
    }

    public void setStopDuration(double sd){
        stopDuration = sd;
    }

    public ArrayList<Node> getNodes(){
        return nodes;
    }

    public void addNode(Node n){
        nodes.add(n);
    }

    public boolean addNodesAtIndex(int i, Node n){
        if (i > 0 && i < nodes.size()) {
            nodes.add(i, n);
            return true;
        }
        else if(i == nodes.size()){
            nodes.add(n);
        }
        else{
            return false;
        }
        return false;
    }

    public boolean removeNode(Node n){
        for(int i = 0; i < nodes.size(); ++i){
            if(n.equals(nodes.get(i))){
                nodes.remove(n);
                return true;
            }
        }
        return false;
    }

    public boolean removeNodeAtIndex(int i){
        if(i > 0 && i < nodes.size()) {
            nodes.remove(i);
            return true;
        }
        else{
            return false;
        }
    }

    public double calculateLength()
    {
        for(int i = 0; i < nodes.size() - 1; ++i){
            length += Node.distanceOfNodes(nodes.get(i), nodes.get(i + 1));
        }

        return length;
    }

    /**
     * Based on cityobservatory.org, the average bus speed is 12.7 mph in cities (20.44 kph). This speed will be used to
     * calculate the duration of the route using the calculated length. t = d/s.
     *
     * Duration also includes the duration of each stop.
     *
     * @return double: the approximate duration of the Route in minutes.
     */
    public double calculateDuration()
    {
        double distance = length;
        double speed = 20.44;
        double minutes = (distance/speed) * 60; // convert to minutes
        minutes += (stopDuration * nodes.size());
        return minutes;
    }

    public String toString(){
        String out = "";
        for(int i = 0; i < nodes.size(); ++i){
            out += nodes.get(i).toString() + "\n";
        }
        return out;
    }
}
