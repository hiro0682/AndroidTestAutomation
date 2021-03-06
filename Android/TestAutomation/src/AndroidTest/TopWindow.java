package AndroidTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

public class TopWindow extends Application implements Initializable{
	
	//These item are for Execution Window
	@FXML private TableView<Rowitem> caseList;
	@FXML private TableColumn<Rowitem, String> actList;
	@FXML private TableColumn<Rowitem, String> detList;
	@FXML private TableColumn<Rowitem, String> imgList;
	@FXML private ListView<String> cmdList;
	@FXML private ImageView imageView;
	
	public ObservableList<Rowitem> list = FXCollections.observableArrayList();
	public ObservableList<String> list2 = FXCollections.observableArrayList(
			new String[] {"tap","long_tap","swipe","sleep","verify_image","tap_image","capture_image"});
	public static ActionList actionList;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Execution_window.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		actList.setCellValueFactory(new PropertyValueFactory<Rowitem,String>("action"));
		detList.setCellValueFactory(new PropertyValueFactory<Rowitem,String>("detail"));
		imgList.setCellValueFactory(new PropertyValueFactory<Rowitem,String>("image"));
		caseList.setItems(list);
		cmdList.setItems(list2);
		actionList = new ActionList();
	}
	
	@FXML
	private void onClickExecute() {
		executeTest();
	}
	
	@FXML
	private void onClickRemove() {
		int currentItem = -1;
		currentItem = caseList.getSelectionModel().getSelectedIndex();
		if(currentItem >= 0) {
			caseList.getItems().remove(currentItem);
			actionList.removeAction(currentItem);
		}
	}
	
	@FXML
	private void onClickAdd() {
		String action = cmdList.getSelectionModel().getSelectedItem();
		Map<String,String> commandList = new HashMap<String,String>();
		init_commandList(commandList);
		switch(action) {
		case "tap":
		    caseList.getItems().add(new Rowitem("tap", "xaxis : 100, yaxis : 200", ""));
		    commandList.put("action", "tap");
			commandList.put("x_axis", String.valueOf(100));
			commandList.put("y_axis", String.valueOf(200));
			actionList.addAction(commandList);
			break;
		case "long_tap":
		    caseList.getItems().add(new Rowitem("ling_tap", "xaxis : 100, yaxis : 200, time : 2 sec", ""));
		    commandList.put("action", "long_tap");
			commandList.put("x_axis",String.valueOf(100));
			commandList.put("y_axis", String.valueOf(200));
			commandList.put("time", String.valueOf(2));
			actionList.addAction(commandList);
			break;
		case "swipe":
		    caseList.getItems().add(new Rowitem("swipe", "xaxis : 100, yaxis : 200, xd : 100, yd : 200, time : 1 sec", ""));
		    commandList.put("action", "swipe");
			commandList.put("x_axis", String.valueOf(100));
			commandList.put("y_axis", String.valueOf(200));
			commandList.put("dx_axis", String.valueOf(100));
			commandList.put("dy_axis", String.valueOf(200));
			commandList.put("time", String.valueOf(1));
			actionList.addAction(commandList);
			break;
		case "sleep":
		    caseList.getItems().add(new Rowitem("sleep", "time : 1 sec", ""));
		    commandList.put("action", "sleep");
			commandList.put("time", String.valueOf(1));
			actionList.addAction(commandList);
			break;
		case "verify_image":
		    caseList.getItems().add(new Rowitem("varify_image", "", "no image"));
		    commandList.put("action", "verify_image");
			commandList.put("data1", "res/image/correct/");
			commandList.put("data2", "album.png");
			actionList.addAction(commandList);
			break;
		case "tap_image":
		    caseList.getItems().add(new Rowitem("tap_image", "", "no_image"));
		    commandList.put("action", "tap_image");
			commandList.put("data1", "res/image/correct/");
			commandList.put("data2", "album.png");
			actionList.addAction(commandList);
			break;
		case "capture_image":
		    caseList.getItems().add(new Rowitem("capture_image", "", ""));
		    commandList.put("action", "capture_image");
		    actionList.addAction(commandList);
			break;
		default :
			break; 
		}
	}
	
	private static void init_commandList(Map<String,String> actionData) {
		actionData.put("action", null);
		actionData.put("x_axis", null);
		actionData.put("y_axis", null);
		actionData.put("dx_axis", null);
		actionData.put("dy_axis", null);
		actionData.put("time", null);
	}
	
	@FXML
	private void handleDragOver(DragEvent event) {
		if(event.getDragboard().hasFiles()) {
			event.acceptTransferModes(TransferMode.ANY);
		}
	}
	
	@FXML
	private void handleDrop(DragEvent event) throws FileNotFoundException {
		List<File> files = event.getDragboard().getFiles();
		Image img = new Image(new FileInputStream(files.get(0)));
		imageView.setImage(img);
	}
	
	public class Rowitem{
		private final SimpleStringProperty action;
		private final SimpleStringProperty detail;
		private final SimpleStringProperty image;
		
		public Rowitem(String action, String detail, String image) {
			super();
			this.action = new SimpleStringProperty(action);
			this.detail = new SimpleStringProperty(detail);
			this.image = new SimpleStringProperty(image);
		}

		public String getAction() {
			return action.get();
		}

		public String getDetail() {
			return detail.get();
		}

		public String getImage() {
			return image.get();
		}		
	}
	private static void executeTest() {
		ActionExecuter actionExecuter = new ActionExecuter();
		if(actionList != null) {
			actionExecuter.executeList(actionList.getActionList());
		}
		System.out.println("Succeed in executing test case");
	}
}
