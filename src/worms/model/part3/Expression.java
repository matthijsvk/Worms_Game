package worms.model.part3;

import worms.gui.game.IActionHandler;
import worms.model.Worm;

public abstract class Expression<T> {
	/**
	 * This method returns the result of the evaluation of this expression. The result is of type T.
	 */
	public T function(int line){
		System.out.println("This top-level function() should be overridden... ");
		return null;
	}
	
	public Class<?> getReturnType(){
		return null;
	}
	
	public void setAppropriateValues(Worm worm, IActionHandler handler){
		this.worm = worm;
		this.handler= handler;
	}
	protected Worm worm;
	protected IActionHandler handler;
	
	@Override
	public abstract String toString();
	
}
