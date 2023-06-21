package main.java.memoranda;


import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonHandler {

	public List<Node> nodes;
	public ArrayList<Driver> driverList;
    public ArrayList<Bus> busList;
	
	public JsonHandler() {
		nodes = new ArrayList<Node>();
        driverList = new ArrayList<Driver>();
        busList = new ArrayList<Bus>();
	}
	
	public void addNode(String id, double latitude, double longitude) {
        nodes.add(new Node(id, latitude, longitude));
    }
	
	public void addDriver(String id, String name, String phoneNumber) {
		driverList.add(new Driver(id, name, phoneNumber));
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

                addNode(id, latitude, longitude);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                int id = Integer.valueOf((String) nodeObj.get("id"));
                int seats = Integer.valueOf((String) nodeObj.get("seats"));

                busList.add(new Bus(id, seats));
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
}
