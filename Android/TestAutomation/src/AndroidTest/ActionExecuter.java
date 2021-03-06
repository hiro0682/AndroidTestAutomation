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

public class ActionExecuter {
	static Runtime runtime = Runtime.getRuntime();
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	static Process process;
	LocalDateTime time = null;
	LogManager Log = new LogManager();
	
	public ActionExecuter() {
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
    	Log.init_logList();
    	if(arrayList == null) {
    		System.out.println("There is no data in ActionList");
    		return;
    	}
    	for(int i = 0 ; i < arrayList.size() ; i++) {
    		execCommand(arrayList.get(i));
    	}
    	for(int j = 0 ; j < Log.logList.size() ; j++) {
    		System.out.println(Log.logList.get(j).get("message"));
    	}
    	process.destroy();
    }
    
    private void execCommand(Map<String, String> actionItem) {
    	int x,y,dx,dy,time;
    	String data1,data2;
    	System.out.println("Execute"+actionItem);
    	switch(actionItem.get("action")) {
    	case "tap":
    		x = Integer.parseInt(actionItem.get("x_axis"));
    		y = Integer.parseInt(actionItem.get("y_axis"));
    		tap(x,y);
    		Log.addLog("tap x:" + x + " y:" + y, null);
    		break;
    	case "long_tap":
    		x = Integer.parseInt(actionItem.get("x_axis"));
    		y = Integer.parseInt(actionItem.get("y_axis"));
    		time = Integer.parseInt(actionItem.get("time"));
    		longTap(x, y, time);
    		Log.addLog("long tap x:" + x + " y:" + y +" for "+ time + "seconds", null);
    		break;
    	case "swipe":
    		x = Integer.parseInt(actionItem.get("x_axis"));
    		y = Integer.parseInt(actionItem.get("y_axis"));
    		dx = Integer.parseInt(actionItem.get("dx_axis"));
    		dy = Integer.parseInt(actionItem.get("dy_axis"));
    		time = Integer.parseInt(actionItem.get("time"));
    		swipe(x, y, dx, dy, time);
    		Log.addLog("swipe from x:" + x + " y:" + y + " to x:"+ (x+dx) + " y:" + (y+dy) + " in "+ time/1000 + "seconds", null);
    		break;
    	case "sleep":
    		time = Integer.parseInt(actionItem.get("time"));
    		sleep(time);
    		Log.addLog("sleep for " + time/1000 + " seconds", null);
    		break;
    	case "verify_image":
    		data1 = actionItem.get("data1");
    		data2 = actionItem.get("data2");
    		if (verifyImage(data1, data2)) {
    			Log.addLog("image " + data2 + "found.", null); 
    		}
    		else Log.addLog("image " + data2 + " was not found.", null); 		
    		break;
    	case "tap_image":
    		data1 = actionItem.get("data1");
    		data2 = actionItem.get("data2");
    		if(tapImage(data1, data2)) {
    			Log.addLog("tap image " + data2, null); 	
    		}
    		else Log.addLog("can't find image" + data2, null); 	
    		break;
    	case "capture_image":
    		data1 = capture();
    		Log.addLog("screen saved as" + data1, null);
    		break;
    	default:
    		break;
    	}
    }

    private void tap(int x_axis, int y_axis) {
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
    
    private void longTap(int x_axis, int y_axis, int time) {
    	try {
			process = runtime.exec("adb shell input swipe " + x_axis + " " + y_axis + " "  + x_axis + " " + y_axis + " " + time*1000);
			System.out.println("adb shell input swipe " + x_axis + " " + y_axis + " "  + x_axis + " " + y_axis + " " + time*1000);
			process.waitFor();
		} catch (IOException e) {
			System.out.println("Error! IOException has occured! ");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
    }
    
    private void swipe(int x_axis, int y_axis, int dx_axis, int dy_axis, int time) {
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
    
    private void sleep(int time) {
    	try {
    	 Thread.sleep(time * 1000);
    	 System.out.println("Sleep " + time +" sec");
    	} catch (InterruptedException e) {
    	}
    }
    
    private boolean verifyImage(String uri_correct, String correct_name) {
    	Mat im = null;
    	Mat tmp = null;
    	Mat result = new Mat(new Size(1, 1), CvType.CV_32F);
  
    	String uri = capture();
    	
    	im = Imgcodecs.imread(uri);
    	tmp = Imgcodecs.imread(uri_correct+correct_name);
    	
    	Imgproc.matchTemplate(im, tmp, result, Imgproc.TM_CCOEFF_NORMED);   	
    	Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
    	
    	System.out.println(mmr.maxVal);
        
    	if (mmr.maxVal > 0.9) return true;
    	return false;	
    }
    
    private int[] findImage(String uri_correct, String correct_name) {
    	System.out.println("findImage#in");
    	Mat im = null;
    	Mat tmp = null;
    	Mat result = new Mat(new Size(1, 1), CvType.CV_32F);
  
    	String uri = capture();
    	
    	im = Imgcodecs.imread(uri);
    	tmp = Imgcodecs.imread(uri_correct+correct_name);
    	
    	Imgproc.matchTemplate(im, tmp, result, Imgproc.TM_CCOEFF_NORMED);   	
    	Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
    	
    	System.out.println(mmr.maxVal);
        
    	if (mmr.maxVal > 0.4) 
    		{
    		 int[] point = { (int)(mmr.maxLoc.x + tmp.cols()/2), (int)(mmr.maxLoc.y + tmp.rows()/2)};
    		    System.out.println("findImage#out point:"+point);
    			return point;
    		}
    	else {
    		System.out.println("findImage#out can't find image");
    		return null;	
    	}
    }
    
    private boolean tapImage(String uri_image, String image_name) {
    	System.out.println("tapImage#in");
    	int[] point = findImage(uri_image, image_name);
    	if(point == null) {
    		return false;
    	}
    	System.out.println("tap x:" + point[0] + " y:" + point[1]);
		tap(point[0], point[1]);
		System.out.println("tapImage#out");
		return true;
    }
    
    private String capture() {
    	System.out.println("capture#in");
    	time = LocalDateTime.now();
    	String timestamp =  "" + time.getYear() + time.getMonthValue() + time.getDayOfMonth() + time.getHour() + time.getSecond();
    	try {
    		System.out.println("adb shell screencap -p /sdcard/" + timestamp + ".png");
    		process = runtime.exec("adb shell screencap -p /sdcard/" + timestamp + ".png");
    		process.waitFor();
    		System.out.println("adb pull /sdcard/" + timestamp +".png " + "res/image/get/" + timestamp + ".png");
    		process = runtime.exec("adb pull /sdcard/" + timestamp +".png " + "res/image/get/" + timestamp + ".png" );
    		process.waitFor();
    		System.out.println("adb shell rm /sdcard/" + timestamp + ".png");
    		process = runtime.exec("adb shell rm /sdcard/" + timestamp + ".png");
    		process.waitFor();    		
    	}catch(IOException e) {
    		System.out.println("Error! IOException has occured!");
    		e.printStackTrace();
    	}catch(InterruptedException e) {
    		System.out.println("Error! InterruptedException has occured!");
    		e.printStackTrace();
    	}
    	System.out.println("capture#out");
    	return "res/image/get/" + timestamp + ".png";
    }
}
