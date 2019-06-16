package view;
	
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import Commands.CommandFactory;
import ServerClient.MyClient;
import ServerClient.MyServer;
import ServerClient.Server;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import viewmodel.ViewModel;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxl = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			AnchorPane root = (AnchorPane)fxl.load();
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
						
			ConcurrentHashMap<String,Double> DoublesymbolTable = new ConcurrentHashMap<>(); // x = 5
			ConcurrentHashMap<String, String> SymbolTable = new ConcurrentHashMap<>(); // x -> path x
			ConcurrentHashMap<String, Double> PathTable = new ConcurrentHashMap<>(); //path simx -> value
			
			String[] DMnames= new String[] {
						"instrumentation/airspeed-indicator/indicated-speed-kt",
						"instrumentation/altimeter/indicated-altitude-ft",
						"instrumentation/altimeter/pressure-alt-ft",
						"instrumentation/attitude-indicator/indicated-pitch-deg",
						"instrumentation/attitude-indicator/indicated-roll-deg",
						"instrumentation/attitude-indicator/internal-pitch-deg",
						"instrumentation/attitude-indicator/internal-roll-deg",
						"instrumentation/encoder/indicated-altitude-ft",
						"instrumentation/encoder/pressure-alt-ft",
						"instrumentation/gps/indicated-altitude-ft",
						"instrumentation/gps/indicated-ground-speed-kt",
						"instrumentation/gps/indicated-vertical-speed",
						"instrumentation/heading-indicator/indicated-heading-deg",
						"instrumentation/magnetic-compass/indicated-heading-deg",
						"instrumentation/slip-skid-ball/indicated-slip-skid",
						"instrumentation/turn-indicator/indicated-turn-rate",
						"instrumentation/vertical-speed-indicator/indicated-speed-fpm",
						"controls/flight/aileron",
						"controls/flight/elevator",
						"controls/flight/rudder",
						"controls/flight/flaps",
						"controls/engines/current-engine/throttle",
						"engines/engine/rpm",
						"sim/current-view/viewer-x-m",
						"sim/current-view/viewer-y-m"
			 };
			
		
		
			MyServer server = new MyServer(DMnames, PathTable);
		
			
			Thread serverThread;

			serverThread = new Thread(() -> {
				server.OpenServer(5400, 10);
				});
				
			serverThread.start();
			//MVVM
			MyClient client = new MyClient(DMnames,PathTable);
			
			
			CommandFactory cf = new CommandFactory(server,client,SymbolTable,DoublesymbolTable,PathTable );
			Model model = new Model(client, cf);
			ViewModel viewModel = new ViewModel(model);
			model.addObserver(viewModel);
			
			MainWindowController mwc = fxl.getController();
			mwc.SetViewModel(viewModel);
			viewModel.addObserver(mwc);
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
