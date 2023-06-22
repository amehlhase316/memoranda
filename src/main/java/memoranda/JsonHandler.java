package main.java.memoranda;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileWriter;
import java.io.IOException;


public class JsonHandler {
	
	public List<Node> nodes;
    public List<String> nodesString;
	
	public JsonHandler() {
		nodes = new ArrayList<Node>();
        nodesString = new ArrayList<String>();
	}
    public void addNodeString(String option) {
        nodesString.add(option);
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
                JSONObject routeObject = new JSONObject();
                JSONArray nodesArray = new JSONArray();

            for (Object obj : route.getNodes()) {
                JSONObject o = new JSONObject();
                String id = (String) o.get("id");
                Node nodeX = nodes.stream().filter(node -> node.getId().equals(id)).findFirst().orElse(null);
                if (nodeX != null) {
                    JSONObject node1Object = createJsonObject(nodeX);
                    nodesArray.add(node1Object);
                }
                routeObject.put("Route", nodesArray);
            }

                // Write the JSON object to the file
                FileWriter fileWriter = new FileWriter("nodes1.json");
                fileWriter.write(routeObject.toJSONString());
                fileWriter.flush();
                fileWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private JSONObject createJsonObject(Node node) {
            JSONObject object = new JSONObject();
            object.put("id", node.getId());
            object.put("lat", node.getLatitude());
            object.put("lon", node.getLongitude());
            return object;
        }



    public List<Node> getNodes() {
        return nodes;
    }

    public List<String> getNodesString() {
        return nodesString;
    }
}
