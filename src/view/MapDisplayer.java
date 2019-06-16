package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.beans.property.IntegerProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MapDisplayer extends Canvas {
	public boolean markMap; // boolean for checking if there is a mark on the map
	public boolean path; //boolean if there is a path exists
	public boolean mapOn;
	
	Integer[][] theMatrix;
	Image planePos, goalImg;
	int maxHeight;
	int startPlaneX;
	int startPlaneY;
	double width, height;
	private Double w, h, cellW, cellH;
	double simulatorX = -5508747.0;
	double simulatorY = -2232854.0;
	double dist;
	int tempMapX, tempMapY;
	int destX, destY;
	
	//cellW= w/theMatrix[0].length; // each cell width
	//cellH= h/theMatrix.length; // each cell height
	
	//w -> canvas width, h->canvas height, cellH -> block height, cellW -> clock width
	public MapDisplayer() {
			markMap = false;
			path = false;
			mapOn = false;
	}
	//Initialize the map details
	public void setMap(Integer[][] matrix,int maxHeight, int startX, int startY, double d) {
		this.planePos = null;
		theMatrix = matrix;
		this.maxHeight = maxHeight;
		this.startPlaneX = startX;
		this.startPlaneY = startY;
		this.dist = d;
		this.tempMapX = startX;
		this.tempMapY = startY;
		
	}
	
	public void drawTheMap() { 
		w=getWidth(); 
		h=getHeight();
		double redBlock = 0 , greenBlock = 0 ;
		cellW= w/theMatrix[0].length; // each cell width
		cellH= h/theMatrix.length; // each cell height
	
		System.out.println("cell w ="+cellW +" cell h"+ cellH);
		GraphicsContext gc= getGraphicsContext2D();
		
		gc.clearRect(0, 0, w, h);
			
		for(int i=0; i<theMatrix.length; i++) {    
			for(int j=0; j<theMatrix[i].length; j++) {
								
				if(theMatrix[i][j] <= maxHeight * 0.5)
				{
					redBlock = 255;
					greenBlock = theMatrix[i][j] * (255 / maxHeight) * 2;
				}
				else
				{
					redBlock = Math.abs(255 - ((theMatrix[i][j] - (maxHeight/2)) * (255 / maxHeight) * 2));
					greenBlock = 255;
				}
				gc.setFill(new Color(redBlock/255, greenBlock/255, 0.286, 1));
				gc.fillRect(j*cellW, i*cellH, cellW, cellH);					
				gc.setFill(Color.BLACK);
				gc.fillText((int)theMatrix[i][j] + "", j*cellW + 4, i*cellH + cellH - 4);
				this.mapOn = true;
			}
		}	
	}	
	


	public void putMarkOnMap(double mouseX, double mouseY) {
		this.markMap = true;
		int tempX = (int)(mouseX / this.cellW);
		int tempY = (int)(mouseY / this.cellH);
		
		destX = tempX;
		destY = tempY;
		
		
		try {
			goalImg= new Image(new FileInputStream("./resources/goal.png"));
			GraphicsContext gc = getGraphicsContext2D();
			gc.drawImage(goalImg, tempX*this.cellW, tempY*this.cellH);
		
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	//	drawTheMap();
	//	setPlaneStart(this.startPlaneX, this.startPlaneY);
	}
	
	public void mapBfsPath() {
		
		
		
	}
	
	public Integer[][] getMapData() {
		return theMatrix;
	}
	
	public void drawThePath(String path) {
		String[] mapPath = path.split(",");
		GraphicsContext gc = getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		
		int X = startPlaneX, Y = startPlaneY;
		System.out.println("posx + posy:");
		System.out.println(X + "," + Y);
		
		int len = mapPath.length - 1;
		for(int i = 0; i < len; i++) {
			if(mapPath[i].equals("Right")) {
				++X;
				gc.fillRect(X * cellW, Y * cellH + cellH/3, cellW/2, cellH/6);
			}
			else if(mapPath[i].equals("Down")) {
				++Y;
				gc.fillRect(X * cellW + cellW/3, Y * cellH, cellW/6, cellH/2);
			}
			else if(mapPath[i].equals("Left")) {
				--X;
				gc.fillRect(X * cellW, Y * cellH + cellH/3, cellW/2, cellH/6);
			}
			else if(mapPath[i].equals("Up")) {
				--Y;
				gc.fillRect(X * cellW + cellW/3, Y * cellH, cellW/6, cellH/2);

			}
			
		}
	}
	
	//method to put the plane on the map (by simulator)
	public void setSTM(double stmX, double stmY) {
		int simToMapX = ((int)((stmX - simulatorX) / dist)) + this.tempMapX;
		int simToMapY = -1 * ((int)((stmX - simulatorY) / dist)) + this.tempMapY;
		drawTheMap();
		movePlaneByPosition(simToMapX, simToMapY);
		
		if(markMap == true)
			markDestByPosition(destX, destY);
		
	}

	public void movePlaneByPosition(int simToMapX, int simToMapY) {
		try {
			Image img = new Image(new FileInputStream("./resources/plane.png"));
			GraphicsContext gc = getGraphicsContext2D();
			gc.drawImage(img, simToMapX*cellW, simToMapY*cellH); // draw plane
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	public void markDestByPosition(int destX2, int destY2) {
		markMap = true;
		int tempX = destX2;
		int tempY = destY2;
		
		this.destX = tempX;
		this.destY = tempY;
		
		try {
			Image img = new Image(new FileInputStream("./resources/close.png"));
			GraphicsContext gc = getGraphicsContext2D();
			
			gc.drawImage(img, tempX*this.cellW, tempY*this.cellH); // draw the dest
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void movePlane(double posX, double posY) {
		int corX = (int)(posX / cellW);
		int corY = (int)(posY / cellH);
		startPlaneX = corX;
		startPlaneY = corY;
		
		try {
			Image img = new Image(new FileInputStream("./resources/plane.png"));
			GraphicsContext gc = getGraphicsContext2D();
			gc.drawImage(img, corX*cellW, corY*cellH); // draw plane
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
			

}	
	
