package main.java.memoranda;

public class Node {
    String id;
    double latitude;
    double longitude;
    private int x;
    private int y;

    /**
     * The default constructor for Node
     * @param id the ID associated with the specific node
     * @param lat the latitude coordinates of the Node
     * @param lon the longitude coordinates of the Node
     */
    public Node(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        x = -1;
        y = -1;
    }

	/**
     * Getter function for the String ID
     * @return the ID associated with the node
     */
    public String getId() {
    	return id;
    }

    /**
     * Getter function for the latitude value
     * @return the latitude value of the Node
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Getter function for the longitude value
     * @return the longitude value of the Node
     */
    public double getLongitude() {
        return longitude;
    }
    
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

    /**
     * Calculates the distance between the Nodes in km
     * @param n the first Node
     * @param nn the second Node
     * @return the distance of the Nodes in km
     */
    public static double distanceOfNodes(Node n, Node nn) {
//        double R = 6371; // radius of the earth in km;
//        double dLat = Math.toRadians(n.getLatitude() - nn.getLatitude());
//        double dLon = Math.toRadians(n.getLongitude() - nn.getLongitude());
//
//        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
//                Math.cos(Math.toRadians(nn.getLatitude())) * Math.cos(Math.toRadians(n.getLatitude())) *
//                        Math.sin(dLon/2) * Math.sin(dLon/2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double d = R * c; //distance in kilometers
//        return d;
    	double xDiff = nn.getX() - n.getX();
        double yDiff = nn.getY() - n.getY();
        
        double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
        return distance;
    }

    /**
     * Calculates the distance between Nodes in miles
     * @param n first node
     * @param nn second node
     * @return The distance of nodes in miles
     */
    public static double distanceOfNodesMiles(Node n, Node nn) {
        return distanceOfNodes(n, nn) * 0.621371;
    }

    /**
     * toString implementation:
     * (unique ID number of Node)
     * latitude: (latitude of Node)
     * longitude: (longitude of Node)
     *
     * @return the string
     */
    public String toString() {
        return "Node: " + id.toString() + "\nlatitude: " + latitude + "\nlongitude: " + longitude;
    }

    /**
     * equals implementation
     * @param n the comparison Node
     * @return returns true if the latitude and longitude values are equal, else returns false
     */
    public boolean equals(Node n) {
        if(latitude == n.getLatitude() && longitude == n.getLongitude())
            return true;
        return false;
    }
}
