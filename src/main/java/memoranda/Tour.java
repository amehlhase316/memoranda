package main.java.memoranda;

import java.util.UUID;
import java.util.ArrayList;
// tour(ID, route)
public class Tour {
    private UUID id;
    private ArrayList<Route> routes;
    private double duration;

    private double length;

    /**
     * Constructor for Tour with a pre-initialized Route
     * @param route the existing Route
     */
    Tour(Route route){
        id = UUID.randomUUID();
        routes.add(route);
        length = route.calculateLength();
        duration = route.getDuration();
    }

    /**
     * The default constructor for Tour.
     */
    Tour(){
        id = UUID.randomUUID();
        routes = new ArrayList<Route>();
        length = 0;
        duration = 0;
    }

    /**
     * Adds a Route to the ArrayList
     * @param route the Route to be added.
     */
    public void addRoute(Route route){
        routes.add(route);
        length += route.getLength();
        duration += route.getDuration();
    }

    /**
     * Removes the specified Route
     * @param route the Route to be removed
     * @return true if the Route is removed else false
     */
    public boolean removeRoute(Route route){
        for(int i = 0; i < routes.size(); ++i){
            if(route.equals(routes.get(i))){
                routes.remove(i);
                length = calculateLength();
                duration = calculateDuration();
                return true;
            }
        }
        return false;
    }

    /**
     * toString implementation
     * @return the String
     */
    public String toString(){
        String out = "";
        for(int i = 0; i < routes.size(); ++i){
            out += routes.get(i).toString() + "\n";
        }
        return out;
    }

    /**
     * Calculates the total length of the Route in km
     * @return the length of the route in km
     */
    public double calculateLength(){
        double out = 0;
        for(int i = 0; i < routes.size(); ++i){
            out += routes.get(i).calculateLength();
        }
        return out;
    }

    /**
     * Calculates the total Duration of the Route in minutes
     * @return the duration of the Route in minutes
     */
    public double calculateDuration(){
        double out = 0;
        for(int i = 0; i < routes.size(); ++i){
            out += routes.get(i).calculateDuration();
        }
        return out;
    }
}
