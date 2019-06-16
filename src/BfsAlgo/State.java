package BfsAlgo;

import java.awt.Point;
import java.io.PrintWriter;

public class State implements Comparable<State> {
 private  Point state;
 private double cost;
 private double totalCost;
 private State cameFrom;
 
 public State(Point state) {
	this.state=state;
}
 
// @Override
// public boolean equals(Object o) {
//	State s=(State)o;
//	return state.equals(s.state);
// }
// 

public boolean equals(State s) {
	if((state.getX()==s.getState().getX())&&(state.getY()==s.getState().getY()))
	{
			return true;
	}
	else return false;
	//return state.equals(s.getState()); 	
}

public Point getState() {
	return state;
}

public void setState(Point state) {
	this.state = state;
}

public double getCost() {
	return cost;
}

public double gettotalCost() {
	return cost;
}

public void setCost(double cost) {
	this.cost = cost;
}

public void setTotalCost(double cost) {
	this.totalCost = cost;
}

public State getCameFrom() {
	return this.cameFrom;
}

public void setCameFrom(State cameFrom) {
	this.cameFrom = cameFrom;
}

@Override
public int compareTo(State s) {
	return (int) (this.cost-s.getCost()); //positive-im bigger
} 
 

 
}
