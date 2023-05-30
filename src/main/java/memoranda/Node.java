package main.java.memoranda;

import java.util.UUID;

public class Node {
    UUID nodeID;
    double latitude;
    double longitude;

    Node(double lat, double lon){
        nodeID = UUID.randomUUID();
        latitude = lat;
        longitude = lon;
    }

    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }

    public static double distanceOfNodes(Node n, Node nn){
        double R = 6371; // radius of the earth in km;
        double dLat = Math.toRadians(n.getLatitude() - nn.getLatitude());
        double dLon = Math.toRadians(n.getLongitude() - nn.getLongitude());

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(nn.getLatitude())) * Math.cos(Math.toRadians(n.getLatitude())) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c; //distance in kilometers
        return d;
    }

    public String toString(){
        return "Node: " + nodeID.toString() + "\nlatitude: " + longitude + "\nlongitude: " + longitude;
    }

    public boolean equals(Node n){
        if(latitude == n.getLatitude() && longitude == n.getLongitude())
            return true;
        return false;
    }
}
