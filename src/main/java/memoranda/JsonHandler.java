package main.java.memoranda;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonHandler {
	
	public List<Node> nodes;
	
	public JsonHandler() {
		nodes = new ArrayList<Node>();
	}
	
	public void addNode(String id, double latitude, double longitude) {
        nodes.add(new Node(id, latitude, longitude));
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
}
