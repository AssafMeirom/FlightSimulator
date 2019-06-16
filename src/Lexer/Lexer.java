package Lexer;


public class Lexer {

    private boolean inTheMiddleOfAWhileLoop;
    private StringBuilder whileBlock;

    public Lexer() {
        this.whileBlock = new StringBuilder();
    }

    public String[] processLine(String line) {
       
    	String replacline = line.trim().replaceAll(" +", " ");
    	if (inTheMiddleOfAWhileLoop) {
            // we are in a while loop block
            whileBlock.append(replacline).append(" \n ");

            if (replacline.equals("}")){
                // end of a while loop
                inTheMiddleOfAWhileLoop = false;
                String[] whileBlockAsStringArray = whileBlockAsStringArray();

                // allocate a new
                whileBlock = new StringBuilder();
                return whileBlockAsStringArray;
            }
        } else if (replacline.startsWith("while")) {
            // start of a new while block
            inTheMiddleOfAWhileLoop = true;
            whileBlock.append(replacline).append(" \n ");
        }

        // regular line
    	return replacline.split("((?<=[a-zA-Z0-9={}\"])\\s+(?=[a-zA-Z0-9={}\"(])|[\\n\\r]+|(?<=[{}])|(?=[{}])|(?<=[=])|(?=[=]))(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
    }
//        return replacline.split(" ");

    private String[] whileBlockAsStringArray() {
        return whileBlock.toString().split("\n");
    }

}
