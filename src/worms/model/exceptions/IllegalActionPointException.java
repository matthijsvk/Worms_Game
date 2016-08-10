package worms.model.exceptions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class for signaling illegal action points
 * 
 * @author Matthijs Van keirsbilck and Bob Vanhoof
 *
 */

public class IllegalActionPointException extends Exception {
		
	/**
	 * Java's complaining...
	 */
	private static final long serialVersionUID = 1L;

	/**
	  * Initialise this Illegal Action Points Exception with the given action points
	  * @param 	radius	
	  * 		The radius for the new illegal radius exception
	  * @post	The value of the new illegal radius exception is set to the given radius
	  * 		| new.getRadius() == radius;
	  */
	public IllegalActionPointException(int remainingActionPoints){
		this.remainingActionPoints = remainingActionPoints;
	}
	
	/**
	 * Variable remainingActionPoints declared
	 */
	private final double remainingActionPoints;
	
	/**
	 * Getter for remainingActionPoints
	 */
	@Basic @Immutable
	public double getRemainingActionPoints(){
		return  this.remainingActionPoints;
	}
}
