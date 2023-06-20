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
            JSONArray driversArray = new JSONArray();

            if (!driverList.isEmpty() && driverList != null) {
            	for (Driver driver : driverList) {
                    JSONObject driverObj = new JSONObject();
                    driverObj.put("id", driver.getID());
                    driverObj.put("name", driver.getName());
                    driverObj.put("phoneNumber", driver.getPhone());

                    driversArray.add(driverObj);
                }
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("drivers", driversArray);

            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
