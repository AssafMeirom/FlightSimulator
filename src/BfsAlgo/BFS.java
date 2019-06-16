package BfsAlgo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class BFS implements Searcher<State>{
	
	@Override
	public List<State> search(Searchable<State> s) {
		PriorityQueue<State> OpenList= new PriorityQueue<>(new CostComparator());
		OpenList.add(s.getInitialState());
		HashSet<State> closeSet= new HashSet<>();
		
		while(OpenList.size()>0)
		{
			
			State n = OpenList.remove();
			closeSet.add(n);
		
		 if(n.equals(s.getGoalState())) {
		
			 return backTrace(n, s.getInitialState());
		 }
		 ArrayList<State> successors=s.getAllPossibleStates(n);
		 for(State state: successors) {
			if(!closeSet.contains(state)&& !OpenList.contains(state)) {
				state.setCameFrom(n);
			//	state.setTotalCost(n.gettotalCost()+state.getCost());
				OpenList.add(state);
			
	          }
			else 
			{
				if(!OpenList.contains(state))
				{
	                OpenList.add(state);				

				}
			}
		 }
		}
				//close list??
//				System.out.println("eli7");
//				if (n.gettotalCost()+state.getCost()<state.gettotalCost())
//				{
//					System.out.println("eli8");
//					if(OpenList.contains(state))
//					{
//						  OpenList.remove(state);
//						  State succesor=new State(state.getState());
//			              succesor.setCameFrom(n);
//			              succesor.setCost(n.gettotalCost()+state.getCost());
//						  OpenList.add(succesor);}
//					System.out.println("eli9");
//				}
//         		}
//		    }	
		
		
		return null;
	}

	private LinkedList<State> backTrace(State goalState, State initialState) {
		//State temp= new State(goalState.getState());
		LinkedList<State> backTrace = new LinkedList<>();
		//backTrace.addFirst(goalState);
	
		
		while(!goalState.equals(initialState)) {
			backTrace.addFirst(goalState);
			goalState=goalState.getCameFrom();
		
		}
		
		backTrace.addFirst(initialState);
		return backTrace;
}

	@Override
	public int getNumberOfNodesEvaluated() {
		// TODO Auto-generated method stub
		return 0;
	}
}
