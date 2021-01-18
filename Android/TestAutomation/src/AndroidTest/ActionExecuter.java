package AndroidTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core.MinMaxLocResult;

public class ActionExecuter {
	static Runtime runtime = Runtime.getRuntime();
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	static Process process;
	LocalDateTime time = null;
	
	
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
	
    public void executeList(ArrayList<Map<String,String>> arrayList) {
    	System.out.println("test");
    	if(arrayList == null) {
    		System.out.println("There is no data in ActionList");
    		return;
    	}
    	for(int i = 0 ; i < arrayList.size() ; i++) {
    		execCommand(arrayList.get(i));
    	}
    	process.destroy();
    }
    
    private void execCommand(Map<String, String> actionItem) {
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
    	case "verify_image":
    		execVerify(actionItem.get("x_axis"), actionItem.get("y_axis"));
    		break;
    	case "capture_image":
    		execCap();
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
    
    public  boolean execVerify(String uri_correct, String correct_name) {
    	Mat im = null;
    	Mat tmp = null;
    	Mat result = new Mat(new Size(1, 1), CvType.CV_32F);
    	String uri = execCap();
    	System.out.println(uri);
    	im = Imgcodecs.imread(uri);
    	tmp = Imgcodecs.imread(uri_correct+correct_name);
    	Imgproc.matchTemplate(im, tmp, result, Imgproc.TM_CCOEFF);
    	MinMaxLocResult mmr = Core.minMaxLoc(result);
    	if (mmr.maxVal > 0.8) return true;
    	else return false;	
    }
    
    public String execCap() {
    	time = LocalDateTime.now();
    	String timestamp =  "" + time.getYear() + time.getMonthValue() + time.getDayOfMonth() + time.getHour() + time.getSecond();
    	try {
    		process = runtime.exec("adb shell screencap -p /sdcard/" + timestamp + ".png");
    		process.waitFor();
    		process = runtime.exec("adb pull /sdcard/" + timestamp +".png " + "res/image/get/" + timestamp + ".png" );
    		process.waitFor();
    		process = runtime.exec("adb shell rm /sdcard/ " + timestamp + ".png");
    		process.waitFor();    		
    	}catch(IOException e) {
    		System.out.println("Error! IOException has occured!");
    		e.printStackTrace();
    	}catch(InterruptedException e) {
    		System.out.println("Error! InterruptedException has occured!");
    		e.printStackTrace();
    	}
    	return "res/image/get/" + timestamp + ".png";
    }
}
