package worms.model.part3;

import worms.gui.game.IActionHandler;
import worms.model.Worm;

public class Assignment extends Statement {
	
	private String argumentLeft;
	private Expression<Type> argumentRight;
	private Worm worm;
	public Worm getWorm(){
		return this.worm;
	}
	
	public Assignment(String argumentLeft, Expression<Type> argumentRight, int line, Type t){
		System.out.print("-------------- AS CONSTRUCT -----------------------");
//		System.out.println(line);
		System.out.print(t.getClass()); System.out.print(" ");
		System.out.println(argumentRight.getReturnType());
		if(t.getClass() != null && argumentRight.getReturnType() != null && t.getClass() != argumentRight.getReturnType()){
			throw new IllegalArgumentException();
		}
		this.argumentLeft = argumentLeft;
		this.argumentRight = argumentRight;
		this.line = line;
	}
	
	private int line;
	
	public int getLine(){
		return this.line;
	}

	public Expression<Type> getAssignmentPartner(){
		return argumentRight;
	}
	
	public void setAssignmentPartner(Expression<Type> a){
		this.argumentRight = a;
	}
	
	@Override
	public void function(int line) {
		if ( line == this.getLine()){
//			System.out.print("before assignment: ");System.out.print(argumentLeft);System.out.print(", "); System.out.println(argumentRight);
//			System.out.print(argumentRight); System.out.println(argumentRight.function(line));
			
			Class<?> arguRClass= argumentRight.getReturnType();
			Type arguL = this.getWorm().getProgram().getGlobals().get(argumentLeft);
			if (arguL == null)
				this.getWorm().getProgram().getGlobals().put(argumentLeft, argumentRight.function(line));
			else {
				if (arguRClass == null || okToAssign(arguRClass, arguL))
					this.getWorm().getProgram().getGlobals().put(argumentLeft, argumentRight.function(line));
				else{
					throw new IllegalArgumentException();
				}	
			}
			System.out.print("after assignment done: ");System.out.print(argumentLeft);System.out.print(", "); System.out.println(argumentRight);
			this.getWorm().getProgram().programCounterIncrement(); 
		}
		this.getWorm().getProgram().programLineIncrement(); 
	}
	
	private boolean okToAssign(Class<?> arguRClass, Type arguL){
		boolean result = false;
		if (arguRClass == Entity.class){
			result = arguL instanceof Entity;
		}
		else if (arguRClass == Double.class)
			result = arguL instanceof Double;
		else if (arguRClass == Boolean.class)
			result = arguL instanceof Boolean;
		return result;
	}

	@Override
	public void setAppropriateValues(Worm worm, IActionHandler handler) {
		this.worm=worm;
		System.out.println("SAP assignment");
		argumentRight.setAppropriateValues(worm, handler);
	}
	
	@Override
	public String toString(){
		return argumentLeft+" := "+argumentRight.toString();
	}

}
