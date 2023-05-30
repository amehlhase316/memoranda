package main.java.memoranda;

import java.util.UUID;
import java.util.ArrayList;
// tour(ID, route)
public class Tour {
    private UUID id;
    private ArrayList<Route> routes;
    private double duration;

    private double length;

    Tour(Route route){
        id = UUID.randomUUID();
        routes.add(route);
        length = route.calculateLength();
        duration = route.getDuration();
    }

    public void addRoute(Route route){
        routes.add(route);
        length += route.getLength();
        duration += route.getDuration();
    }

    public boolean removeRoute(Route route){
        for(int i = 0; i < routes.size(); ++i){
            if(route.equals(routes.get(i))){
                routes.remove(i);
                return true;
            }
        }
        return false;
    }

    public String toString(){
        String out = "";
        for(int i = 0; i < routes.size(); ++i){
            out += routes.get(i).toString() + "\n";
        }
        return out;
    }

}
