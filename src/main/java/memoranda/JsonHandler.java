package main.java.memoranda;


import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;


public class JsonHandler {

	public List<Node> nodes;
    public List<String> nodesString;

    public List<String> driversToString;
    public List<String> busesToString;
	public ArrayList<Driver> driverList;
    public ArrayList<Bus> busList;
    private ArrayList<Route> routeList;

    public JsonHandler() {
        nodes = new ArrayList<Node>();
        nodesString = new ArrayList<String>();
        driverList = new ArrayList<Driver>();
        driversToString = new ArrayList<String>();
        busList = new ArrayList<Bus>();
        busesToString = new ArrayList<String>();
        routeList = new ArrayList<Route>();

    }

    private JSONObject createJsonObject(Node node) {
        JSONObject object = new JSONObject();
        object.put("id", node.getId());
        object.put("lat", node.getLatitude());
        object.put("lon", node.getLongitude());
        return object;
    }
	public void readNodesFromJSON(String filename) {
        try {
        	JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filename));

            JSONArray nodesArray = (JSONArray) jsonObject.get("nodes");


            for (Object obj : nodesArray) {
                JSONObject nodeObj = (JSONObject) obj;
                String id = (String) nodeObj.get("id");
                double latitude = Double.parseDouble((String) nodeObj.get("lat"));
                double longitude = Double.parseDouble((String) nodeObj.get("lon"));
                addNodeString((String) nodeObj.get("id"));
                addNode(id, latitude, longitude);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private JSONObject createNodePoint(String id) {
        JSONObject object = new JSONObject();


        Node selectedNode = nodes.stream()
                .filter(node -> node.getId().equals(id))
                .findFirst()
                .orElse(null);


        if (selectedNode != null) {
            object.put("id", selectedNode.getId());
            object.put("lat", selectedNode.getLatitude());
            object.put("lon", selectedNode.getLongitude());
        }

        return object;
    }



    public void writeRouteToJson(Route route) {
        try {
            // Read the existing JSON file and parse it into a JSONObject
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("nodes1.json"));

            // Create a new JSONArray to store all routes (existing + new)
            JSONArray routesArray = new JSONArray();

            // Create a new JSONObject for the current route
            JSONObject currentRouteObj = new JSONObject();

            // Create a new JSONArray to store all nodes of the current route
            JSONArray currentRouteNodesArray = new JSONArray();
            JSONObject nodeObj = new JSONObject();
            nodeObj.put("Driver", route.getDriver().getName());
            nodeObj.put("Bus ID", route.getBus().getId());
            nodeObj.put("Stop Duration", route.getStopDuration());
            for (Node node : route.getNodes()) {

                nodeObj.put("Id", node.getId());
                nodeObj.put("Longitude", node.getLongitude());
                nodeObj.put("Latitude", node.getLatitude());

                currentRouteNodesArray.add(nodeObj);
            }

            // Add the nodes array to the current route object
            currentRouteObj.put("Route", currentRouteNodesArray);

            // Add the current route object to the routes array
            routesArray.add(currentRouteObj);

            // Get the existing routes from the JSON object (if any) and add them to routesArray
            JSONArray existingRoutesArray = (JSONArray) jsonObject.get("Routes");
            if (existingRoutesArray != null) {
                routesArray.addAll(existingRoutesArray);
            }

            // Update the routes array in the JSONObject
            jsonObject.put("Routes", routesArray);

            // Write the updated JSONObject to the file
            FileWriter fileWriter = new FileWriter("nodes1.json");
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.flush();
            fileWriter.close();
            System.out.println("Routes have been written to and saved in nodes1.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readRoutesFromJSON() {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("nodes1.json"));

            JSONArray routesArray = (JSONArray) jsonObject.get("Routes");
            List<Node> listOfNodes= new ArrayList<Node>();
            Double stopDuration = 0.0;
            for (Object obj : routesArray) {
                JSONObject routeObj = (JSONObject) obj;
                if(routeObj.get("Stop Duration").equals("Stop Duration")) {
                    stopDuration = (Double) routeObj.get("Stop Duration");
                }
                String id = (String) routeObj.get("Id");
                Double longitude = (Double) routeObj.get("Longitude");
                Double latitude = (Double) routeObj.get("Latitude");
                listOfNodes.add(new Node(id,latitude,longitude));
            }


            addRoute(listOfNodes, stopDuration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addRoute(List<Node> listOfNodes, Double sd) {
        this.routeList.add(new Route((ArrayList<Node>) listOfNodes, sd));
    }

    public List<String> getNodesString() {
        return nodesString;
    }
    public void readDriversFromJSON(String filename) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filename));

            JSONArray nodesArray = (JSONArray) jsonObject.get("drivers");

            for (Object obj : nodesArray) {
                JSONObject nodeObj = (JSONObject) obj;
                String id = (String) nodeObj.get("id");
                String name = (String) nodeObj.get("name");
                String phoneNumber = (String) nodeObj.get("phoneNumber");
                addDriverToString(name);
                addDriver(id, name, phoneNumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readBusesFromJSON(String filename) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filename));

            JSONArray nodesArray = (JSONArray) jsonObject.get("buses");

            for (Object obj : nodesArray) {
                JSONObject nodeObj = (JSONObject) obj;
                String id = (String) nodeObj.get("id");
                int seats = Integer.valueOf((String) nodeObj.get("seats"));
                Bus tempBus = new Bus(id, seats);
                tempBus.setAssignedDriver((String) nodeObj.get("assignedDriver"));
                addBusToString(id);
                busList.add(tempBus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeDriversToJSON(String filename) {
        try {
            // Read the existing JSON file and parse it into a JSONObject
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filename));

            // Create a new JSONArray to store all drivers (existing + new)
            JSONArray driversArray = new JSONArray();

            // Add the new drivers to the driversArray
            for (Driver driver : driverList) {
                JSONObject driverObj = new JSONObject();
                driverObj.put("id", driver.getId());
                driverObj.put("name", driver.getName());
                driverObj.put("phoneNumber", driver.getPhoneNumber());

                driversArray.add(driverObj);
            }

            // Update the drivers array in the JSONObject
            jsonObject.put("drivers", driversArray);

            // Write the updated JSONObject to the file
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.flush();
            fileWriter.close();
            System.out.println("Drivers have been written to and saved in nodes1.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeBusesToJSON(String filename) {
        try {
            // Read the existing JSON file and parse it into a JSONObject
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filename));

            // Create a new JSONArray to store all drivers (existing + new)
            JSONArray busesArray = new JSONArray();

            // Add the new drivers to the driversArray
            for (Bus bus : busList) {
                JSONObject busObj = new JSONObject();
                busObj.put("id", String.valueOf(bus.getId()));
                busObj.put("seats", String.valueOf(bus.getSeats()));
                busObj.put("assignedDriver", String.valueOf(bus.getAssignedDriverID()));

                busesArray.add(busObj);
            }

            // Update the drivers array in the JSONObject
            jsonObject.put("buses", busesArray);

            // Write the updated JSONObject to the file
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.flush();
            fileWriter.close();
            System.out.println("Buses have been written to and saved in nodes1.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getDriversToString() {
        return driversToString;
    }

    public List<String> getBusesToString() {
        return busesToString;
    }
    public List<Node> getNodes() {
        return nodes;
    }
    public ArrayList<Route> getRouteList() {
        return routeList;
    }
    public void addNodeString(String option) {
        nodesString.add(option);
    }

    private void addDriverToString(String name) {
        driversToString.add(name);
    }

    private void addBusToString(String id) {
        busesToString.add(id);
    }
    public void addNode(String id, double latitude, double longitude) {
        nodes.add(new Node(id, latitude, longitude));
    }

    public void addDriver(String id, String name, String phoneNumber) {
        driverList.add(new Driver(id, name, phoneNumber));
    }



    public ArrayList<Driver> getDriverList() {
        return driverList;
    }



    public ArrayList<Bus> getBusList() {
        return busList;
    }

}
