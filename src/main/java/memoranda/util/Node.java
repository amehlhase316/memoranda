package main.java.memoranda.util;

public class Node {
    double latitude;
    double longitude;

    Node(double lat, double lon){
        latitude = lat;
        longitude = lon;
    }

    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }


}
