package worms.model.exceptions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class for signaling illegal Names
 * 
 * @author Matthijs Van keirsbilck and Bob Vanhoof
 *
 */
public class IllegalNameException extends RuntimeException{
	
	/**
	 * Java's complaining...
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	  * Initialise this Illegal name Exception with the given name
	  * @param 	name	
	  * 		The name for the new illegal name exception
	  * @post	The value of the new illegal name exception is set to the given name
	  * 		| new.getName() == name;
	  */
	public IllegalNameException(String name){
//		System.arraycopy(naam,0,this.name,0,naam.length);
		this.name = name;
	}
	
	/**
	 * Variable name declared
	 */
	private final String name ;
	
	/**
	 * Getter for name
	 */
	@Basic @Immutable
	public String getName(){
		return this.name;
	}
}
