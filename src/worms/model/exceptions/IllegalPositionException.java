package worms.model.exceptions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class for signaling illegal positions
 * 
 * @author Matthijs Van keirsbilck and Bob Vanhoof
 *
 */
public class IllegalPositionException extends RuntimeException{
	/**
	 * Initialise this Illegal Position Exception with the given value and type
	 * @param 	position	
	 * 			The position for the new illegal position exception
	 * @param 	type	
	 * 			The type of the new illegal position exception
	 * @post	The value of the new illegal position exception is set to the given position
	 * 			| new.getPosition() == position;
	 * @post	The type of the  new illegal position exception is set to the given type
	 * 			| new.getType() == type;
	 */
	public IllegalPositionException(double position,char type){
		this.position = position;
		this.type = type;
	}
	
	/**
	 * Getter for position
	 */
	@Basic @Immutable
	public double getPosition(){
		return position;
	}
	
	/**
	 * Getter for the type
	 */
	@Basic @Immutable
	public char getType(){
		return this.type;
	}
	
	/**
	 * Variables xPos and yPos declared
	 */
	private final double position;
	private final char type;
	
	private static final long serialVersionUID = 1L;
	
}
