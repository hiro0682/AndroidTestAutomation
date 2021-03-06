package AndroidTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class ActionList {
	private Map<String, String> actionData = new HashMap<>(); 
    private ArrayList<Map<String,String>> actionList = new ArrayList<Map<String,String>>();
    private Element command;
    private Document commandList;
    private String[] inputList;
    private int numInput;
    
     
	public ActionList() {
		init_actiondata(actionData);
		init_actionList(actionList);
	}
	
	private void init_actiondata(Map<String,String> actionData) {
		actionData.put("action", null);
		actionData.put("x_axis", null);
		actionData.put("y_axis", null);
		actionData.put("dx_axis", null);
		actionData.put("dy_axis", null);
		actionData.put("time", null);
		actionData.put("data1", null);
		actionData.put("data2", null);
	}
	
	private void init_actionList(ArrayList<Map<String,String>> actionList) {
		actionList.clear();
	}
	
	public void addAction(Map<String,String> inputAction) {
		commandList = getCommandList();
		command = commandList.getElementById(inputAction.get("action"));
		if(command == null) {
			System.out.println("Command not found. Please use command defined in Command List.");
			return;
		}
		actionData.put("action", inputAction.get("action"));
		inputList = command.getElementsByTagName("input").item(0).getTextContent().split(",");
		numInput = Integer.parseInt(command.getElementsByTagName("input_n").item(0).getTextContent());
		System.out.println(inputList[0]);
		for (int i = 0 ; i < numInput ; i++) {
			if (inputAction.get(inputList[i]) == null ) {
				System.out.println("Required input item is Null!");
				return;
			}
			actionData.put(inputList[i], inputAction.get(inputList[i]));
		}
		addComponent(actionData);    
	}
	
	private void addComponent(Map<String,String> actionData) {
		Map<String,String> buf = new HashMap<String,String>(actionData);
		actionList.add(buf);
		init_actiondata(actionData);
	}
	
	public void removeAction(int id) {
		if (actionList.isEmpty()) {
			System.out.println("Action List has no action.");
			return;
		}
		Map<String,String> removed = new HashMap<String,String>(actionList.get(0));
		actionList.remove(id);
		System.out.println("action" + removed + "has removed" );
	}
	
	public void clearAction() {
		init_actionList(actionList);
	}
 
	public ArrayList<Map<String,String>> getActionList(){
		return actionList;
	}
	
	public int size() {
		return actionList.size();
	}
	
    private Document getCommandList() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = builder.parse("res/CommandList/CommandList.xml");
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

}
