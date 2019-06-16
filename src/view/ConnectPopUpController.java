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

public class ConnectPopUpController implements Observer {
	ViewModel viewModel;
	@FXML private TextField textIp, textPort;
	@FXML private Label warningLabel;
	
	
	public void SetViewModel (ViewModel viewModel) {
		this.viewModel = viewModel;
		viewModel.textIp.bind(this.textIp.textProperty());
		viewModel.textPort.bind(this.textPort.textProperty());
		
		warningLabel.visibleProperty().bind(viewModel.connectStatus);
	}
	
	//when clicking on sumbit i will send to viewmodel to connect
	public void connectToSimulator() {
		viewModel.connectToSimulator();	
	}


	//update when to close the popup
	@Override
	public void update(Observable o, Object arg) {
		if (o == viewModel) {
			if(arg.equals("Close Popup")) {
				Stage stage = (Stage) textIp.getScene().getWindow();
				stage.close();
			}
		}
	}

		
}

