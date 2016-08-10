package worms.model.part3;

import worms.model.Worm;
import worms.gui.game.IActionHandler;


public abstract class Statement{
	
	public void function(int line){
	}
	
	public abstract void setAppropriateValues(Worm worm, IActionHandler handler);
	
	@Override
	public abstract String toString();
	
	public int getLength(){
		return 1;
	}
	
	public abstract int getLine();
	
	public boolean checkForLoop(){
		return true; 
	}
	
	public boolean hasAction(){
		System.out.println("no overridden found");
		return false;
	}	
	
	public Statement get(int index){
		return this;
	}
	
	public int size(){
		return 1;
	}
}