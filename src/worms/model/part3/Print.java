package worms.model.part3;

import worms.gui.game.IActionHandler;
import worms.model.Worm;

/*
 * this class prints the result of the statements it contains
 */
public class Print extends Statement {
	
	private Expression<Type> s;
	private Worm worm;
	public Worm getWorm() {
		return this.worm;
	}
	
	public Print(Expression<Type> statement, int line){
		this.s = statement;
		this.line = line;
	}
	
	private int line;
	@Override
	public int getLine(){
		return this.line;
	}
	
	@Override
	public void function(int line){
		if ( line == this.line){
			System.out.println(s.toString());
			this.getWorm().getProgram().programCounterIncrement(); 
		}
		this.getWorm().getProgram().programLineIncrement(); 
		
	}

	@Override
	public void setAppropriateValues(Worm worm, IActionHandler handler) {
		s.setAppropriateValues(worm, handler);
		this.worm=worm;
	}
	
	@Override
	public String toString(){
		return "print( "+s.toString()+" )";
	}	
}
