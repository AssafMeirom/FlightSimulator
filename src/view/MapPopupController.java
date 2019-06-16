package view;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import viewmodel.ViewModel;

public class MapPopupController implements Observer {
	ViewModel viewModel;
	@FXML private TextField textMapIp, textMapPort;
	@FXML private Label warningMapLabel;
	MapDisplayer mapDisplayer;
	
	public void SetViewModel (ViewModel viewModel, MapDisplayer map) {
		this.viewModel = viewModel;
		viewModel.textMapIP.bind(this.textMapIp.textProperty());
		viewModel.textMapPort.bind(this.textMapPort.textProperty());
		this.mapDisplayer = map;
		warningMapLabel.visibleProperty().bind(viewModel.connectMapStatus);
	}
	
	//when clicking on sumbit i will send to viewmodel to connect
	public void connectToMileStone() {
		 viewModel.connectToMile3(mapDisplayer.getMapData());
	}


	//update when to close the popup
	@Override
	public void update(Observable o, Object arg) {
		if (o == viewModel) {
			if(arg.equals("Close Popup")) {
				Stage stage = (Stage) textMapIp.getScene().getWindow();
				stage.close();
			}
		}
	}

		
}

