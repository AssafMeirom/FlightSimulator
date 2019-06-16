package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import viewmodel.ViewModel;

public class RouteController extends Canvas implements Observer {
	ViewModel viewModel;
	public StringProperty mapFile; //bind the matrix
	String[] theFile;
	int maxMapHeight; //Max height of the map (for coloring)
	private Integer[][] theMatrix; //matrix for milestone 3
	public IntegerProperty startPlaneX;
	public IntegerProperty startPlaneY;
	public DoubleProperty destX;
	public DoubleProperty destY;
	public StringProperty mapSol;
	public DoubleProperty simulatorPlaneX;
	public DoubleProperty simulatorPlaneY;
	
	@FXML MapDisplayer mapDisplayer; 
	Image goalImg;
	
	
	
	public RouteController() {
		this.mapDisplayer = new MapDisplayer();
		startPlaneX = new SimpleIntegerProperty();
		startPlaneY = new SimpleIntegerProperty();
		destX = new SimpleDoubleProperty();
		destY = new SimpleDoubleProperty();
		mapFile = new SimpleStringProperty();
		mapSol = new SimpleStringProperty();
		simulatorPlaneX = new SimpleDoubleProperty();
		simulatorPlaneY = new SimpleDoubleProperty();
	}
	
	public void SetViewModel (ViewModel vm) {
		this.viewModel = vm;
		vm.startPlaneX.bind(this.startPlaneX);
		vm.startPlaneY.bind(this.startPlaneY);
		this.simulatorPlaneX.bind(vm.simulatorPlaneX);
		this.simulatorPlaneY.bind(vm.simulatorPlaneY);
		
		this.mapSol.bind(vm.mapSolution);
		this.maxMapHeight =0;
		
		vm.destX.bind(this.destX);
		vm.destY.bind(this.destY);
	
	}
	
	//connect popup window (Map ip+port popup)
	public void HandleCalculatePopup()  {	
		try {
			FXMLLoader fxl = new FXMLLoader(getClass().getResource("MapPopupController.fxml"));
			AnchorPane root = (AnchorPane)fxl.load();
			Scene scene = new Scene(root,250,250);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
						
			MapPopupController mpc = fxl.getController();
			mpc.SetViewModel(viewModel, mapDisplayer);
			viewModel.addObserver(mpc);
			
			

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//Open file dialog (Load Map data Action Button)
	public void OpenFile() throws IOException { 
		FileChooser fc = new FileChooser();
		fc.setTitle("Open File");
		fc.setInitialDirectory(new File("./resources"));
		
		File selectFile = fc.showOpenDialog(null);
		if(selectFile != null) {
			Scanner myScanner = new Scanner(selectFile);
			mapFile.set(myScanner.useDelimiter("\\A").next().trim());			
			String[] rows = mapFile.get().split("\n");
			String[] start = rows[0].split(",");
			String[] takeDist = rows[1].split(",");
			int startingPointX = Integer.parseInt(start[0].trim());
			int startingPointY = Integer.parseInt(start[1].trim());
			int numRow = rows.length -2 ;
			int numCol = (((rows[1].split(",")).length)); //not including start 0,0 and second row
			this.theMatrix = new Integer[numRow][numCol]; 
			double dist = Double.parseDouble(takeDist[0].trim());
			for(int i = 2; i < numRow +2 ; i++) {
				String[] columns = rows[i].split(",");
				for(int j = 0; j < numCol; j++) {
					theMatrix[i-2][j] = Integer.parseInt(columns[j].trim());
					if (theMatrix[i-2][j] > maxMapHeight)
						maxMapHeight = theMatrix[i -2 ][j]; 
					//find the highest point to draw the colors
				}
			}
			
			
			this.startPlaneX.set(startingPointX); //setting the start point plane
			this.startPlaneY.set(startingPointY); //setting the start point plane
	
			//sending to create the map
			mapDisplayer.setMap(this.theMatrix, this.maxMapHeight, startingPointX, startingPointY, dist);
			mapDisplayer.drawTheMap();
			//mapDisplayer.setPlaneStart(startingPointX, startingPointY);
			mapDisplayer.movePlane(startingPointX, startingPointY);
		}
		else {
			System.out.println("Didn't Load File");
		}
	}

	
	
public void putMarkOnMap(MouseEvent event) {		
		double mouseX = event.getX();
		double mouseY = event.getY();
		
		mapDisplayer.drawTheMap();
		mapDisplayer.movePlaneByPosition(mapDisplayer.startPlaneX, mapDisplayer.startPlaneY);
		mapDisplayer.putMarkOnMap(mouseX,mouseY);
		
		this.destX.set(mapDisplayer.destX);
		this.destY.set(mapDisplayer.destY);

//		this.viewModel.connectToMile3(this.theMatrix);
		
	}

	

	//update when to close the popup
	@Override
	public void update(Observable o, Object arg) {
		if (o == viewModel) {
			if(arg.equals("Close Popup")) {
				
			}
			
			else if (arg.equals("Calculation_Done")) {
				String mapTemp = this.mapSol.get();
				
				if (mapDisplayer.mapOn == true) {
					mapDisplayer.setSTM(this.simulatorPlaneX.get(), this.simulatorPlaneY.get());
				}
				mapDisplayer.drawThePath(mapTemp);
				
			}
		}
	}

		
}

