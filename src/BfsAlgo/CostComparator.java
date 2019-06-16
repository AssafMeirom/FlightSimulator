package BfsAlgo;

import java.util.Comparator;

public class CostComparator implements Comparator<State>{

	@Override
	public int compare(State a, State b) {
		return a.compareTo(b);
	}
	
	

}
