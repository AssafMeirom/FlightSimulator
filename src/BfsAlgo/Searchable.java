package BfsAlgo;

import java.util.ArrayList;

public interface Searchable<T> {
	T getInitialState();
	T getGoalState();
	ArrayList<T> getAllPossibleStates(T t); //list??
}
