package AndroidTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class ActionExecuter {
	static Runtime runtime = Runtime.getRuntime();
	static Process process;
	
	public ActionExecuter() {
		init();
	}
	
	private static void init() {
		try {
			process = runtime.exec("adb start server");
			process.waitFor();
		} catch (IOException e) {
			System.out.println("Error! IOException has occured! ");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
	}
	
    public void execute(ArrayList<Map<String,String>> arrayList) {
    	System.out.println("test");
    	if(arrayList == null) {
    		System.out.println("There is no data in ActionList");
    		return;
    	}
    	for(int i = 0 ; i < arrayList.size() ; i++) {
    		executeItem(arrayList.get(i));
    	}
    	process.destroy();
    }
    
    private void executeItem(Map<String, String> actionItem) {
    	switch(actionItem.get("action")) {
    	case "tap":
    		execTap(Integer.parseInt(actionItem.get("x_axis")), Integer.parseInt(actionItem.get("y_axis")));
    		break;
    	case "long_tap":
    		execLongTap(Integer.parseInt(actionItem.get("x_axis")), Integer.parseInt(actionItem.get("y_axis")), Integer.parseInt(actionItem.get("time")));
    		break;
    	case "swipe":
    		execSwipe(Integer.parseInt(actionItem.get("x_axis")), Integer.parseInt(actionItem.get("y_axis")), 
    				Integer.parseInt(actionItem.get("dx_axis")), Integer.parseInt(actionItem.get("dy_axis")), Integer.parseInt(actionItem.get("time")));
    		break;
    	case "sleep":
    		execSleep(Integer.parseInt(actionItem.get("time")));
    		break;
    	default:
    		break;
    	}
    }
    
    private void execTap(int x_axis, int y_axis) {
    	try {
			process = runtime.exec("adb shell input touchscreen tap " + x_axis + " " +y_axis);
			System.out.println("adb shell input touchscreen tap " + x_axis + " " +y_axis);
			process.waitFor();
		} catch (IOException e) {
			System.out.println("Error! IOException has occured! ");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
    }
    
    private void execLongTap(int x_axis, int y_axis, int time) {
    	try {
			process = runtime.exec("adb shell input swipe " + x_axis + " " + y_axis + " "  + x_axis + " " + y_axis + " " + time);
			System.out.println("adb shell input swipe " + x_axis + " " + y_axis + " "  + x_axis + " " + y_axis + " " + time);
			process.waitFor();
		} catch (IOException e) {
			System.out.println("Error! IOException has occured! ");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
    }
    
    private void execSwipe(int x_axis, int y_axis, int dx_axis, int dy_axis, int time) {
    	try {
			process = runtime.exec("adb shell input swipe " + x_axis + " " + y_axis + " "  + (x_axis+dx_axis) + " " + (y_axis+dy_axis) + " " + time*100);
			System.out.println("adb shell input swipe " + x_axis + " " + y_axis + " "  + (x_axis+dx_axis) + " " + (y_axis+dy_axis) + " " + time*100);
			process.waitFor();
    	} catch (IOException e) {
    		System.out.println("Error! IOException has occured! ");
    		e.printStackTrace();
    	} catch (InterruptedException e) {
    		System.out.println("Error!");
    		e.printStackTrace();
    	}
    }
    
    private void execSleep(int time) {
    	try {
    	 Thread.sleep(time * 1000);
    	 System.out.println("Sleep " + time +" sec");
    	} catch (InterruptedException e) {
    	}
    }
}
