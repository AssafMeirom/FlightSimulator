package BfsAlgo;

public class StringReverser implements Solver<String, String> {
    //Now, the types parameters have been given a type, a more specific class, is no longer parametric
	
	public StringReverser() {}

	@Override
	public String solve(String p) {
		return new StringBuilder(p).reverse().toString();
  }
}

