package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import ServerClient.MyClient;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Model;
import viewmodel.ViewModel;

public class MainWindowController implements Observer, Initializable {
	public ViewModel viewModel;		
	
	@FXML private Button HomeB,ManualB, ConnectB ;
	@FXML private AnchorPane HomeAnchor,  ManualAnchor, JoystickAnchor;
	 
	
	//AutoPilot Section
	@FXML private Button AutopilotB, AutopilotLoad;
	@FXML private TextArea AutopilotTextBox;
	@FXML private AnchorPane AutopilotAnchor;
	
	
	//Joystick Section
	@FXML private Circle JoystickB, Outerjoystick;
	@FXML private Slider throttleSlider, rudderSlider;
	private double centerX = 0; //mouse location(example 467)
	private double centerY = 0;//mouse location (example 467)
	private double JoyRadius =0 ;
	private double initializeCenterX =0; //real location
	private double initializeCenterY=0; //real location
	public DoubleProperty aileron, elevator;
	
	
	public MainWindowController() {
		aileron = new SimpleDoubleProperty();
		elevator = new SimpleDoubleProperty();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initializeCenterX = JoystickB.getLayoutX();
		initializeCenterY = JoystickB.getLayoutY();

		
	}
	
	//setting the viewmodel and databinding viewmodel to view (mainwindowcontroller)
	//binding - aileron, elevator, ruddersilder, throttleslider, autopilotbox 
	public void SetViewModel (ViewModel viewModel) {
		this.viewModel = viewModel;	
		  viewModel.aileron.bind(this.aileron); 
		  viewModel.elevator.bind(this.elevator);
		  viewModel.rudder.bind(this.rudderSlider.valueProperty());
		  viewModel.thorttle.bind(this.throttleSlider.valueProperty());
		  viewModel.textBox.bind(this.AutopilotTextBox.textProperty());
		 
	}

	
	//connect popup window (home connect)
	public void HandleConnectPopup()  {	
		try {
			FXMLLoader fxl = new FXMLLoader(getClass().getResource("ConnectPopUpController.fxml"));
			AnchorPane root = (AnchorPane)fxl.load();
			Scene scene = new Scene(root,250,250);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
						
			ConnectPopUpController cpc = fxl.getController();
			cpc.SetViewModel(viewModel);
			viewModel.addObserver(cpc);
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//connect popup window (home connect)
	public void HandleRouteCalculatePopup()  {	
		try {
			FXMLLoader fxl = new FXMLLoader(getClass().getResource("RouteController.fxml"));
			AnchorPane root = (AnchorPane)fxl.load();
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
						
			RouteController rc = fxl.getController();
			if (rc ==null)
			{
				System.out.println("rc null");
			}
			
			rc.SetViewModel(viewModel);
			viewModel.addObserver(rc);
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	

	
	public void loadFromFile() {
		viewModel.loadfromFile();
	}
	
	

	//left side anchor (main menu)
	public void handleButtonAction(ActionEvent event) { 
		if (event.getSource()== HomeB) {
			HomeAnchor.setVisible(true);
			AutopilotAnchor.setVisible(false); 
			ManualAnchor.setVisible(false);
		}
		if (event.getSource()== AutopilotB) {
			HomeAnchor.setVisible(false);
			AutopilotAnchor.setVisible(true); 
			ManualAnchor.setVisible(false);
		}		
		if (event.getSource()== ManualB) {
			HomeAnchor.setVisible(false);
			AutopilotAnchor.setVisible(false); 
			ManualAnchor.setVisible(true);
		}
	}
	


	//open file and write to textbox (autopilot load)
	public void WriteToText() throws IOException { 
		
		FileChooser fc = new FileChooser();
		fc.setTitle("Open File");
		fc.setInitialDirectory(new File("./resources"));
		File selectFile = fc.showOpenDialog(null);
		if(selectFile != null) {
			BufferedReader in = new BufferedReader(new FileReader(selectFile));
			String line = in.readLine();
			while(line != null){
				AutopilotTextBox.appendText(line + "\n");
				  line = in.readLine();
		}
		}
		
	}
	// Joystick Stuff
	//moving the joystick
	public void mousedrag(MouseEvent event) { 
		double x2, y2;
		if(JoyRadius == 0) {
			JoyRadius = Outerjoystick.getRadius();
			centerX = (JoystickB.localToScene(JoystickB.getBoundsInLocal()).getMinX() + JoystickB.localToScene(JoystickB.getBoundsInLocal()).getMaxX())/2;
			centerY = (JoystickB.localToScene(JoystickB.getBoundsInLocal()).getMinY() + JoystickB.localToScene(JoystickB.getBoundsInLocal()).getMaxY())/2;
		}
			double x1 = event.getSceneX();
			double y1 = event.getSceneY();
			x2 = x1;
			y2 = y1;
			double distance = dist(event.getSceneX(), event.getSceneY(), centerX, centerY);
			if(distance <= this.JoyRadius) {
				this.JoystickB.setLayoutX(initializeCenterX + x1 - centerX);
				this.JoystickB.setLayoutY(initializeCenterY + y1 - centerY);
			}
			else
			{	
				if(x1 > centerX) {
					double alfa = Math.atan((y1-centerY)/(x1-centerX));
					double w = JoyRadius * Math.cos(alfa);
					double z = JoyRadius * Math.sin(alfa);
					x2 = centerX + w;
					y2 = centerY + z;
					this.JoystickB.setLayoutX(initializeCenterX + x2 - centerX);
					this.JoystickB.setLayoutY(initializeCenterY + y2 - centerY);
				}
				else
				{
					double alfa = Math.atan((centerY - y1) / (centerX - x1));
					double w = this.JoyRadius * Math.cos(alfa);
					double z = JoyRadius * Math.sin(alfa);
					x2 = centerX - w;
					y2 = centerY - z;
				}
				this.JoystickB.setLayoutX(initializeCenterX + x2 - centerX);
				this.JoystickB.setLayoutY(initializeCenterY + y2 - centerY);
			}
			aileron.set((x2-centerX)/JoyRadius);
			elevator.set((centerY - y2) / JoyRadius);
			viewModel.setAileron();
			viewModel.setElevator();			
		}
	//make the joystick return to the center after realeasing the mouse	
	public void ExitJoystick () { 
		this.JoystickB.setLayoutX(this.initializeCenterX);
		this.JoystickB.setLayoutY(this.initializeCenterY);
		aileron.set(0);
		elevator.set(0);
	}
	//calculating distance
	private double dist(double x1, double y1, double x2, double y2) { 
			return Math.sqrt((x1-x2) * (x1-x2) + (y1-y2) * (y1-y2));

		}
	
	
	//Slider control
	public void throttleDrag() { //connecting the slider with throttle
		viewModel.updateThrottle();
	}
	public void rudderDrag() { //connecting the slider with rudder
		viewModel.updateRudder();
	}
		
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void stopAutoP() {
		
	}



}