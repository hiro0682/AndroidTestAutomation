package AndroidTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogManager {
	private Map<String, Object> logData = new HashMap<>(); 
    public ArrayList<Map<String,Object>> logList = new ArrayList<Map<String,Object>>();
    private static int id_cnt;

	public LogManager() {
		init_logdata(logData);
		init_logList();
	}
	
	private void init_logdata(Map<String,Object> logData) {
		logData.put("id", id_cnt);
		logData.put("time", null);
		logData.put("message", null);
		logData.put("data", null);
		System.out.println(id_cnt);
	}
	
	public void init_logList() {
		logList.clear();
		id_cnt = 1;
	}
	
	private void addComponent(Map<String,Object> logData) {
		Map<String,Object> buf = new HashMap<String,Object>(logData);
		logList.add(buf);
		init_logdata(logData);
	}
	
	public void addLog(String message, String data) {
		logData.put("id", id_cnt);
		logData.put("time", String.valueOf(LocalDateTime.now()));
		logData.put("message", message);
		logData.put("data", data);
		addComponent(logData);
		id_cnt ++;
	}
	
}
