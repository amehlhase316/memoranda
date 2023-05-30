package main.java.memoranda.util;

// Route(nodes, length, duration)

import java.util.ArrayList;


public class Route {
    private ArrayList<Node> nodes;
    private double length;
    private double duration;

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
        
    }
    private double calculateLength()
    {




        return 0;
    }

    private double calculateDuration()
    {

        return 0;
    }
}
