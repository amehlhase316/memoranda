package main.java.memoranda;

<<<<<<< HEAD
import main.java.memoranda.DriverList;
=======
>>>>>>> 1dd83ad09e07cf1fa6b101cbf09f88e48ebc136a
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonHandler {

	public List<Node> nodes;
	public List<Driver> drivers;
	
	public JsonHandler() {
		nodes = new ArrayList<Node>();
	}
	
	public void addNode(String id, double latitude, double longitude) {
        nodes.add(new Node(id, latitude, longitude));
    }
	
	public void addDriver(int id, String name, String phoneNumber) {
		drivers.add(new Driver(id, name, phoneNumber));
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
                int id = (int) nodeObj.get("ID");
                String name = (String) nodeObj.get("name");
                String phoneNumber = (String) nodeObj.get("phoneNumber");

                addDriver(id, name, phoneNumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
