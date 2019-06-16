package BfsAlgo;

public interface CacheManager<Problem, Solution> { //

	//
  public boolean isSave(Problem p); //
  public Solution getSolution(Problem p); 
  public void saveSolution(Problem p, Solution s); 
  
}

