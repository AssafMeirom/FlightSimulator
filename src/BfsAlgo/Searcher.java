package BfsAlgo;

import java.util.List;

public interface Searcher<T> {
 public List<T> search(Searchable<T> s);
 public int getNumberOfNodesEvaluated();
}
