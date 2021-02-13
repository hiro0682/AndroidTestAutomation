package AndroidTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainComponent {
	private final static int MAKE_TEST = 1;
	private final static int REMOVE_TEST = 2;
	private final static int EXECUTE_TEST = 3;
	private final static int SHOW_TESTCASE = 4;
	private final static int END_PROG = 5;
	
	private final static int ACTION_TAP = 1;
	private final static int ACTION_LONGTAP = 2;
	private final static int ACTION_SWIPE = 3;
	private final static int ACTION_SLEEP = 4;
	private final static int ACTION_VERIFY = 5;
	private final static int ACTION_IMAGETAP = 6;
	private final static int ACTION_CAPTURE = 7;
	private final static int END_EDITACTION = 8;
	public static ActionList actionList;
	
	
	/*
	public static void main(String[] args) throws InterruptedException {
		
		
		//ActionExecuter test = new ActionExecuter();
		//System.out.println(test.tapImage("res/image/get/", "2021122015.png"));
		
		actionList = new ActionList();
		while(executeAction(selectAction()));
	}
	*/
	
	private static void init_commandList(Map<String,String> actionData) {
		actionData.put("action", null);
		actionData.put("x_axis", null);
		actionData.put("y_axis", null);
		actionData.put("dx_axis", null);
		actionData.put("dy_axis", null);
		actionData.put("time", null);
	}
	
	private static int selectAction() {
		System.out.println("Please select Action."
				+ "\n1.Make and save test case."
				+ "\n2.Remove test case."
				+ "\n3.Execute test case."
				+ "\n4.Show test case."
				+ "\n5.End program.");
		return receiveInt(5);
	}
	
	private static boolean executeAction(int action) {
		switch(action) {
		case MAKE_TEST:
			System.out.println("Let's make test case!");
			makeTest();
			return true;
		case REMOVE_TEST:
			System.out.println("Remove test case!");
			removeTest();
			return true;
		case EXECUTE_TEST:
			System.out.println("Let's execute test case!");
			executeTest();
			return true;
		case SHOW_TESTCASE:
			System.out.println("Following is current test case.");
			showTestcase();
			return true;
		case END_PROG:
			System.out.println("End progaram.");
			return false;
		default:
			System.out.println("Error has occured! Please input crrect number");
			return true;
		}
	}
	
	private static void makeTest() {
		int action;
		int x_axis, y_axis, dx_axis, dy_axis;
		int time;
		String path,im_name;
		boolean flag = true;
		Map<String,String> commandList = new HashMap<String,String>();
		while(flag) {
			init_commandList(commandList);
			System.out.println("Select an action."
					+ "\n1.Tap"
					+ "\n2.Long tap"
					+ "\n3.Swipe"
					+ "\n4.Sleep"
					+ "\n5.Verify Image"
					+ "\n6.Tap image"
					+ "\n7.Capture image"
					+ "\n8.End adding action.");
			action = receiveInt(8);
			switch(action) {
			case ACTION_TAP:
				System.out.println("Input X axis.");
				x_axis = receiveInt(2000);
				System.out.println("Input Y axis.");
				y_axis = receiveInt(2000);
				//actionList.addTapAction(Integer.toString(x_axis), Integer.toString(y_axis));
				commandList.put("action", "tap");
				commandList.put("x_axis", String.valueOf(x_axis));
				commandList.put("y_axis", String.valueOf(y_axis));
				actionList.addAction(commandList);				
				break;
			case ACTION_LONGTAP:
				System.out.println("Input X axis.");
				x_axis = receiveInt(2000);
				System.out.println("Input Y axis.");
				y_axis = receiveInt(2000);
				System.out.println("Input long tap time.");
				time = receiveInt(10);
				//actionList.addLongTapAction(Integer.toString(x_axis), Integer.toString(y_axis), Integer.toString(time));
				commandList.put("action", "long_tap");
				commandList.put("x_axis",String.valueOf(x_axis));
				commandList.put("y_axis", String.valueOf(y_axis));
				commandList.put("time", String.valueOf(time));
				actionList.addAction(commandList);
				break;
			case ACTION_SWIPE:
				System.out.println("Input X axis.");
				x_axis = receiveInt(2000);
				System.out.println("Input Y axis.");
				y_axis = receiveInt(2000);
				System.out.println("Input dX axis.");
				dx_axis = receiveInt(2000);
				System.out.println("Input dY axis.");
				dy_axis = receiveInt(2000);
				System.out.println("Input swipe time.");
				time = receiveInt(10);
				//actionList.addSwipeAction(Integer.toString(x_axis), Integer.toString(y_axis), Integer.toString(dx_axis), Integer.toString(dy_axis), Integer.toString(time));
				commandList.put("action", "swipe");
				commandList.put("x_axis", String.valueOf(x_axis));
				commandList.put("y_axis", String.valueOf(y_axis));
				commandList.put("dx_axis", String.valueOf(dx_axis));
				commandList.put("dy_axis", String.valueOf(dy_axis));
				commandList.put("time", String.valueOf(time));
				actionList.addAction(commandList);
				break;
			case ACTION_SLEEP:
				System.out.println("Input sleep time.");
				time = receiveInt(10);
				//actionList.addSleepAction(Integer.toString(time));
				commandList.put("action", "sleep");
				commandList.put("time", String.valueOf(time));
				actionList.addAction(commandList);
				break;
			case ACTION_VERIFY:
				System.out.println("Input image path.");
				path = receiveStr();
				System.out.println("Input image name.");
				im_name = receiveStr();
				commandList.put("action", "verify_image");
				commandList.put("data1", path);
				commandList.put("data2", im_name);
				actionList.addAction(commandList);
				break;
			case ACTION_IMAGETAP:
				System.out.println("Input image path.");
				path = receiveStr();
				System.out.println("Input image name.");
				im_name = receiveStr();
				commandList.put("action", "tap_image");
				commandList.put("data1", path);
				commandList.put("data2", im_name);
				actionList.addAction(commandList);
				break;
			case ACTION_CAPTURE:
				commandList.put("action", "capture_image");
				actionList.addAction(commandList);
				break;
			case END_EDITACTION:
				System.out.println("End action edit...");
				flag = false;
				break;
			default:
				System.out.println("Please input correct value.");
			}	
		}
	}
	
	private static void removeTest() {
		actionList.clearAction();
		System.out.println("Succeed in clear test case.");
	}
	
	private static void executeTest() {
		ActionExecuter actionExecuter = new ActionExecuter();
		if(actionList != null) {
			actionExecuter.executeList(actionList.getActionList());
		}
		System.out.println("Succeed in executing test case");
	}
	
	private static void showTestcase() {
		for(int i = 0 ; i < actionList.getActionList().size() ; i++)
		System.out.println(actionList.getActionList().get(i));
	}
	
	private static int receiveInt(int max_val){
		int num = 0;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please input integer value");
		while(true) {			
			try {
				scanner.reset();
				num = scanner.nextInt();
			}catch(Exception e) {
				System.out.println("Please input int value");
			}
			if (num <= max_val) {
				return num;
			}
			System.out.println("Max value is"+ max_val);
		}	
	}
	
	private static String receiveStr() {
		String str = "";
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		try {
			scanner.reset();
			str = scanner.next();
		}catch(Exception e) {
			System.out.println("Please input int value");
		}
		return str;
	}

}
