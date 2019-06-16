package BfsAlgo;

import java.util.HashMap;

public class FileCacheManager<Problem, Solution> implements CacheManager<Problem, Solution> {
    
   HashMap<Problem, Solution> map;
   
   public FileCacheManager() {
	map=new HashMap<>();
}
   
	@Override
	public boolean isSave(Problem p) {	
		 return map.containsValue(map.get(p));
	}

	@Override
	public Solution getSolution(Problem p) {
		return map.get(p);
	}

	@Override
	public void saveSolution(Problem p, Solution s) {
		map.put(p, s);
	} //
}
