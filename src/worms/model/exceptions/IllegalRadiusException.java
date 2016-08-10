package worms.model.exceptions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class for signaling illegal radiusses
 * 
 * @author Matthijs Van keirsbilck and Bob Vanhoof
 *
 */
public class IllegalRadiusException extends RuntimeException{
	
	/**
	 * Java's complaining...
	 */
	private static final long serialVersionUID = 1L;
	
	 /**
	  * Initialise this Illegal Radius Exception with the given radius
	  * @param 	radius	
	  * 		The radius for the new illegal radius exception
	  * @post	The value of the new illegal radius exception is set to the given radius
	  * 		| new.getRadius() == radius;
	  */
	public IllegalRadiusException(double radius){
		this.radius = radius;
	}
	
	/**
	 * Variable radius declared
	 */
	private final double radius;
	
	/**
	 * Getter for radius
	 */
	@Basic @Immutable
	public double getRadius(){
		return  this.radius;
	}
}
