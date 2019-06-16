package viewmodel;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import model.Model;


public class ViewModel extends Observable implements Observer, Initializable{
		public StringProperty textBox;
		public BooleanProperty connectStatus;
		public Model model;
		
		//Joystic Variables
		public DoubleProperty aileron, elevator;
		public DoubleProperty thorttle, rudder;
		
		//Connect Popup Window Variables
		public StringProperty textIp, textPort;
		
		//Map Variables
		public StringProperty stringMapFile; //binding the map data 
		public IntegerProperty startPlaneX;
		public IntegerProperty startPlaneY;
		public DoubleProperty destX;
		public DoubleProperty destY;
		public StringProperty mapSolution;
		public DoubleProperty simulatorPlaneX;
		public DoubleProperty simulatorPlaneY;
		
		
		//Popup Map Connect
		public StringProperty textMapIP, textMapPort;
		public BooleanProperty connectMapStatus;
		
		
		public ViewModel(Model model) {
			this.model = model;
			startPlaneX = new SimpleIntegerProperty();
			startPlaneY = new SimpleIntegerProperty();
			textIp = new SimpleStringProperty();
			textPort= new SimpleStringProperty();
			thorttle = new SimpleDoubleProperty();
			stringMapFile = new SimpleStringProperty();
			aileron = new SimpleDoubleProperty();
			elevator = new SimpleDoubleProperty();
			rudder = new SimpleDoubleProperty();
			textBox = new SimpleStringProperty();
			connectStatus = new SimpleBooleanProperty();
			textMapIP = new SimpleStringProperty();
			textMapPort = new SimpleStringProperty();
			connectMapStatus = new SimpleBooleanProperty();
			destX = new SimpleDoubleProperty();
			destY = new SimpleDoubleProperty();
			mapSolution = new SimpleStringProperty();
			simulatorPlaneX = new SimpleDoubleProperty();
			simulatorPlaneY= new SimpleDoubleProperty();
		}
		
		
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			
			
		}
				
		//sending to the Model the value of the aileron(from joystick)
		public void setAileron() {
			model.setAileron(aileron.get());
			}
		//sending to the Model the value of the elevator(from joystick)	
		public void setElevator() {
			model.setElevator(elevator.get());
			}
		
		//after the method called in MainController, send it to the model
		public void updateThrottle() { 
			model.setThrottle(thorttle.get());
			
		}
		//after the method called in MainController, send it to the model
		public void updateRudder() { 
			model.setRudder(rudder.get());	
		}
	
		//send the auto pilot script to the model
		public void loadfromFile() {
			model.runScript(textBox.get());
		}

		//recieving messages from model (connect, connectsuccess, disconnect)
		//and notify popupcontroller to close 
		@Override
		public void update(Observable o, Object arg) { 
			if (o == model) {
				if(arg.equals("Success Connect To Server")) {
					this.simulatorPlaneX.set(model.sPlaneX);
					this.simulatorPlaneY.set(model.sPlaneY);
					connectStatus.set(false);
					setChanged();
					notifyObservers("Close Popup");
				}
				
				else if(arg.equals("Failed Connect To Server")) {
					connectStatus.set(true);	
					
				}
				else if (arg.equals("Calculation Done")) {
					mapSolution.set(model.getSolution());
					setChanged();
					notifyObservers("Calculation_Done");
				}
				
			}
			
		}

		
		
		//Send to model the ip and port
		public void connectToSimulator() {
			model.ConnectToServer(textIp.get(), Integer.parseInt(textPort.get()));
		}
		
		//send ip and port to milestone 3
		public void connectToMile3(Integer[][] theMat) {
			model.connectToMap(this.textMapIP.get(), Integer.parseInt(this.textMapPort.get()), theMat, this.startPlaneX.get(), 
					this.startPlaneY.get(), this.destX.get(), this.destY.get());

			
		}

	
		
		
}



