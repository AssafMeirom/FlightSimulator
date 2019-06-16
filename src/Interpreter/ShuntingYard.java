package Interpreter;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import Commands.CommandFactory;


public class ShuntingYard {
	
	
	public  Expression calc(String exp, CommandFactory cf){

				ArrayDeque<String> queue = new ArrayDeque<>();
				Stack<String> stack = new Stack<>();
				Stack<Expression> stackExp = new Stack<>();
				exp = exp.trim();
				if(exp.startsWith("-"))
					exp = "0" + exp;
				
				String[] tokens = exp.split("(?<=[-+*/()])|(?=[-+*/()])");
				String lastToken = "";
				
				for(String token : tokens)
				{
					token = token.trim();
					if(token.equals("") || token == null)
						continue;
					
					if(isDouble(token))
					{
						queue.add(token);
						lastToken = token;
						continue;
					}
					
					if(token.matches("^[a-zA-Z0-9_]+$"))
					{
						if(cf.StringSymbolTable.containsKey(token))
							queue.add(String.valueOf(cf.PathTable.get(cf.StringSymbolTable.get(token))));
						else if (cf.DoubleSymbolTable.containsKey(token)) {
							queue.add(String.valueOf(cf.DoubleSymbolTable.get(token)));
						}
						else
							try {
								throw new Exception("Cannot find the variable: " + token);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}		
					}
					
					switch(token)
					{
						case "/":
						case "*":
						case "(":
							stack.push(token);
							break;
						
						case "+":
						case "-":
							// Checking if the "-" is a negative sign of a number,
							// or whether it's a Minus Binary Operator.
							if(token.equals("-") && lastToken.matches("^[[\\/\\*\\+\\-\\(]]$"))
							{
								//System.out.println("--->" + lastToken);
								stack.push("~");
								break;
							}
							
							while (!stack.empty() && (!stack.peek().equals("("))){
								queue.add(stack.pop());
							}
							
							stack.push(token);
							break;
						
						case ")":
							while (!stack.peek().equals("(")){
								queue.add(stack.pop());
							}
							stack.pop();
							break;
					}
					
					lastToken = token;
				}
				
				while(!stack.isEmpty()) {
					queue.add(stack.pop());
				}
								
				for(String str : queue)
				{
					if(isDouble(str))
						stackExp.push(new Number(Double.parseDouble(str)));
					else if(str.matches("^[a-zA-Z0-9_]+$"))
						stackExp.push(new VarNumber(str, cf));
					else
					{
						if(stackExp.isEmpty() == true)
							try {
								throw new Exception("Invalid Expression: " + exp);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						Expression right = stackExp.pop();
						Expression left = null;
						
						// Since ~ ( Negative Sign ) is unary expression, no need to open two Expression, but only one.
						if(str.charAt(0) != '~')
						{
							if(stackExp.isEmpty() == true)
								try {
									throw new Exception("Invalid Expression: " + exp);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							
							left = stackExp.pop();
						}
						
						switch(str)
						{
						    case "/":
						    	stackExp.push(new Divide(left, right));
						    	break;
						    case "*":
						    	stackExp.push(new Multiply(left, right));
						    	break;
						    case "+":
						    	stackExp.push(new Plus(left, right));
						    	break;
						    case "-":
						    	stackExp.push(new Minus(left, right));
						    	break;
						    case "~":
						    	stackExp.push(new Multiply(new Number(-1.0), right));
						    	break;
						}
					}
				}

				return stackExp.pop();
			}
			
			private boolean isDouble(String val) {
				try {
					Double.parseDouble(val);
					return true;
				} catch (NumberFormatException e) {
					return false;
				}
			}
	
	
		
		
	}

