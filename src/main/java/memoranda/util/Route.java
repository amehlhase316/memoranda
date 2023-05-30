package main.java.memoranda.util;

// Route(nodes, length, duration)

import java.util.ArrayList;


public class Route {
    private ArrayList<Node> nodes;
    private double length; // in km
    private double duration; // in minutes

    Route(ArrayList<Node> n)
    {
        nodes = n;
        length = calculateLength();
        duration = calculateDuration();
    }

    Route(Node initialNode){
        nodes.add(initialNode);
        length = calculateLength();
        duration = calculateDuration();
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

    double calculateLength()
    {
        for(int i = 0; i < nodes.size() - 1; ++i){
            length += Util.distanceOfNodes(nodes.get(i), nodes.get(i + 1));
        }
        
        return 0;
    }

    private double calculateDuration()
    {

        return 0;
    }
}
