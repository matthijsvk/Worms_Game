package worms.model.part3;

import worms.model.Worm;
import worms.gui.game.IActionHandler;

public class Action extends Statement {
	
	//Mottige constructie
	public Action(){}
	
	public Action(int line){
		this.line = line;
	}
	
	@Override
	public void function(int line){}
	
	@Override
	public void setAppropriateValues(Worm worm, IActionHandler handler){
		this.worm = worm;
		this.handler = handler;
	}
	
	private Worm worm;
	public Worm getWorm(){
		return this.worm;
	}
	private IActionHandler handler;
	public IActionHandler getHandler(){
		return this.handler;
	}
	private int line;
	@Override
	public int getLine(){
		return this.line;
	}
	
	@Override
	public String toString(){
		return "Superclass Action!";
	}
	
	@Override
	public boolean checkForLoop(){
		System.out.println("checking Action...");
		return true; // forloop checks ok?
	}
	
	@Override
	public boolean hasAction(){
		System.out.println("this is an action!");
		return true;
	}

	// ------------------- nested classes ----------------------------
	
	//---------------  Turn ----------------------------
	public class Turn extends Action{
		
		public Turn(Expression<Double> argument, int line) {
			super(line);
			this.argument = argument;
		}
		
		private Expression<Double> argument;
		
		@Override
		public void function(int line){	
			if( line == this.getLine() ){
				System.out.println("The worm turned--------------------------------------");
				this.getHandler().turn(getWorm(), argument.function(line).getValue());
				this.getWorm().getProgram().programCounterIncrement(); 
				//System.out.println("New Direction: "+this.getWorm().getDirection());
			}
			this.getWorm().getProgram().programLineIncrement(); 
		}
		
		@Override
		public String toString(){
			return "turn( "+this.getWorm()+" "+argument.toString()+" )";
		}
		
		@Override
		public void setAppropriateValues(Worm worm, IActionHandler handler){	
			super.setAppropriateValues(worm, handler);
			argument.setAppropriateValues(worm, handler);
			System.out.println("Turn is set");
		}
	}
	
	//---------------  Move  ----------------------------
	public class Move extends Action{
		
		public Move(int line) {
			super(line);
		}
		
		@Override
		public void function(int line){
			if( line == this.getLine() ){
				this.getHandler().move(this.getWorm());
				this.getWorm().getProgram().programCounterIncrement(); 
			}
			this.getWorm().getProgram().programLineIncrement();
		}
		
		@Override
		public String toString(){
			return "move( "+this.getWorm()+" )";
		}
	}
	
	//---------------  Jump ----------------------------
	public class Jump extends Action{
		
		public Jump(int line) {
			super(line);
		}
		
		@Override
		public void function(int line){
			if( line == this.getLine() ){
				this.getHandler().jump(this.getWorm());
				this.getWorm().getProgram().programCounterIncrement(); 
			}
			this.getWorm().getProgram().programLineIncrement();
		}
		
		@Override
		public String toString(){
			return "jump( "+this.getWorm()+" )";
		}
	}
	
	//---------------  Fire ----------------------------
	public class Fire extends Action{
		
		public Fire(Expression<Double> argument, int line) {
			super(line);
			this.argument = argument;
		}
		
		public Expression<Double> argument;
		
		@Override
		public void function(int line){
			if( line == this.getLine() ){
				int i = argument.function(line).intValue();
				this.getHandler().fire(this.getWorm(), i);
				this.getWorm().getProgram().programCounterIncrement(); 
			}
			this.getWorm().getProgram().programLineIncrement();
		}
		
		@Override
		public String toString(){
			return "fire( "+this.getWorm()+" "+argument.toString()+" )";
		}
		
		@Override
		public void setAppropriateValues(Worm worm, IActionHandler handler){
			super.setAppropriateValues(worm, handler);
			argument.setAppropriateValues(worm, handler);
			System.out.println("Fire is set.");
		}
	}
	
	//---------------  ToggleWeapon ----------------------------
	public class ToggleWeapon extends Action{
		
		public ToggleWeapon(int line) {
			super(line);
		}
		
		@Override
		public void function(int line){
			if( line == this.getLine() ){
				this.getHandler().toggleWeapon(this.getWorm());
				this.getWorm().getProgram().programCounterIncrement(); 
			}
			this.getWorm().getProgram().programLineIncrement(); 
		}
		
		@Override
		public String toString(){
			return "toggleWeap( "+this.getWorm()+" )";
		}
	}
	
	//---------------  Skip ----------------------------
	public class Skip extends Action{
		
		public Skip(int line) {
			super(line);
		}
		
		@Override
		public void function(int line){
			if( line == this.getLine() ){
				this.getWorm().getProgram().programCounterIncrement(); 
			}
			this.getWorm().getProgram().programLineIncrement(); 
		}
		
		@Override
		public String toString(){
			return "skip()";
		}
	}
}
