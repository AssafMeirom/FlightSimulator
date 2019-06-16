package BfsAlgo;

import java.util.List;

public class SearcherAdapter implements Solver<Searchable<State>,List<State>> {
 
	//Searcher searcher;
	Searcher<State> searcher;
	
	public SearcherAdapter(Searcher<State> searcher) {
		this.searcher = searcher;
	}

	@Override
	public List<State> solve(Searchable<State> s) {
		return  searcher.search(s);
	}
}

