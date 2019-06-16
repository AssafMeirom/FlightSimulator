package BfsAlgo;
import java.awt.Point;
import java.io.StreamTokenizer;
import java.util.ArrayList;

public class MatGame implements Searchable<State> {
 
   	State[][] matrix;
   	State initialState;
   	State goalState;
	 
	 public MatGame(State[][] matrix, Point initialPoint, Point goalPoint) {
		this.matrix = matrix;
		this.initialState =new State(initialPoint);
		
		double costI=  matrix[initialPoint.x][initialPoint.y].getCost();
		this.initialState.setCost(costI);
		
		this.goalState = new State(goalPoint);
		
		double costG= matrix[goalPoint.x][goalPoint.y].getCost();
		this.goalState.setCost(costG);
	}

	@Override
	public State getInitialState() 
	{
		
		return this.initialState;
	}

	@Override
	public State getGoalState() {
		// TODO Auto-generated method stub
		return this.goalState;
	}

	@Override
	public ArrayList<State> getAllPossibleStates(State s) {
		ArrayList<State> myArray=new ArrayList<>();
		int x=s.getState().x;
		int y=s.getState().y;
		
		int rows=matrix.length;
		int cols=matrix[0].length;
		
		if (x-1>0) //can move up
		{
			Point pointUp= new Point(x-1,y);
			State stateUp= new State(pointUp);
			stateUp.setCost(matrix[x-1][y].getCost()+s.getCost());
			myArray.add(stateUp);
		}
		
		if (y-1>0 ) //can move left 
		{
			Point pointLeft= new Point(x,y-1);
			State stateLeft= new State(pointLeft);
			stateLeft.setCost(matrix[x][y-1].getCost()+s.getCost());
			myArray.add(stateLeft);
		}
		
		if(x+1<=rows-1) { 
			Point pointDown= new Point(x+1,y);
			State stateDown= new State(pointDown);
			stateDown.setCost(matrix[x+1][y].getCost()+s.getCost());
			myArray.add(stateDown);		
		}
		
		if(y+1<= cols-1) {
			Point pointRight= new Point(x,y+1);
		    State stateRight= new State(pointRight);
		    stateRight.setCost(matrix[x][y+1].getCost()+s.getCost());
		    myArray.add(stateRight);
		}
		
		return myArray;
	}
	
	
}
