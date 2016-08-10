package worms.model;

import worms.model.exceptions.IllegalPositionException;
import worms.model.exceptions.IllegalRadiusException;
import be.kuleuven.cs.som.annotate.*;
import worms.model.part3.Entity;
/**
 * A class that implements Game objects form the game 'Worms'
 * 
 * @date 21April2014
 * @author  Matthijs Van keirsbilck, Bob Vanhoof
 * @version 1.3
 * 
 * @invar x position is valid
 * 			| isValidXPosition(this.getXPosition())
 * @invar y position is valid
 * 			| isValidYPosition(this.getYPosition())
 * @invar radius is larger than 0
 * 			| isValidRadius(this.getRadius())
 * @invar the linked world is valid
 * 			| isValidWorld(this.getWorld())
 */
public class GameObject extends Entity{
	/** Create a Game Object that has a x and y position, a radius and is located in a World
	 * 
	 * @param 	world
	 * 			the world of this Game Object to be initialized (World)
	 * @param 	xPosition
	 * 			the x position of this Game Object to be initialized (double)
	 * @param 	yPosition
	 * 			the y position of this Game Object to be initialized (double)
	 * @param	radius
	 * 			the radius of this Game Object to be initialized (double)
	 * 
	 * @effect	the world bounding is set to the given world
	 * 			| new.getWorld() = world;
	 * @effect 	The x- position of this new Game Object is equal to the given x- position
         * 			| new.getXPosition() = xPosition;
	 * effect	The y- position of this new Game Object is equal to the given y- position
	 * 			| new.getYPosition() = yPosition;
	 * @effect	The radius of this new Game Object is equal to the given radius
	 * 			| new.getRadius() == radius;
	 * @effect	The maximum radius of this new Game Object is equal to a value proportional with the height of the world of this GameObject
	 * 			| new.getRadius() == radius;
	 * 
	 * @throws IllegalPositionException
	 * 			if the x or y position is not valid
	 * 			| if (! isValidXPosition(xPosition) || !isValidYPosition(yPosition))
	 * @throws IllegalRadiusException
	 * 			if the radius is not valid
	 * 			| if (! isValidRadius(radius))
	 * @throws	IllegalArgumentException
	 * 			if the given world is not valid
	 * 			| if (!isValidWorld(world)
	 */
	public GameObject(World world, double xPosition, double yPosition, double radius) 
			throws IllegalPositionException, IllegalRadiusException 
		{
		this.setWorld(world);
		this.setXPosition(xPosition);
		this.setYPosition(yPosition);
		this.setRadius(radius);
		
	}
	// --------------------- x position -------------------------
	/**
	 * This variables stores the current x position of this Game Object
	 */
	private double xPosition;
	
	/**
	 * Return the x position of this Game Object
	 */
	@Basic
	public double getXPosition(){
		return this.xPosition;
	}
	
	/**
	 * This sets the new x-position of this Game Object
	 * @param newXPosition
	 * 			the new x position to be set to this Game Object (double)
	 * @post 	the x- position of this Game Object is set to the given x- position
	 * 			| new.getXPosition()() == newXPosition;
	 * @throws IllegalPositionException
	 * 			if the new x position is not valid
	 * 			| ! isValidXPosition(newXPosition)
	 */
	public void setXPosition(double newXPosition) throws IllegalPositionException{
		if (! isValidXPosition(newXPosition))
			throw new IllegalPositionException(newXPosition,'x');
		this.xPosition = newXPosition;
	}
	
	/**
	 * Checker for x position
	 * @param xPosition
	 * 			the x position to be checked for this Game Object (double)
	 * @return false if xPosition is not a number
	 * 			| if (Double.isNan(xPosition)
	 * 			| 	return false
	 * @return true else
	 * 			| else
	 * 			| 	return true
	 */
	public boolean isValidXPosition(double xPosition){
		if (Double.isNaN(xPosition))				
			return false;
		double highbound = this.getWorld().getWidth();
		if (xPosition > highbound ) 
			return false;
		if (xPosition < 0)
			return false;
		return true;
	}
	
	
	// --------------------- y position -------------------------
	/**
	 * This variables stores the current y position of this Game Object
	 */
	private double yPosition;
	
	/**
	 * Return the y position of this Game Object
	 */
	@Basic
	public double getYPosition(){
		return this.yPosition;
	}
	
	/**
	 * This sets the new y-position of this Game Object
	 * @param newYPosition
	 * 			the new y position to be set to this Game Object (double)
	 * @post 	the y- position of this Game Object is set to the given y- position
	 * 			| new.getYPosition()() == newYPosition;
	 * @throws IllegalPositionException
	 * 			if the new y position is not valid
	 * 			| ! isValidYPosition(newYPosition)
	 */
	public void setYPosition(double newYPosition){
		
		if (! isValidYPosition(newYPosition)){
			throw new IllegalPositionException(newYPosition,'y');
		}
		this.yPosition = newYPosition;
	}
	
	/**
	 * Checker for  y position
	 * @param yPosition
	 * 			the y position to be checked for this Game Object (double)
	 *  @return false if yPosition is not a number
	 * 			| if (Double.isNan(yPosition)
	 * 			| 	return false
	 * @return true else
	 * 			| else
	 * 			| 	return true
	 */
	public boolean isValidYPosition(double yPosition){
		if (Double.isNaN(yPosition))	{
			return false;
		}
		double highbound = this.getWorld().getHeight();
		if (yPosition > highbound ){ 
			return false;
		}
		if (yPosition < 0.0){
			return false;
		}
		return true;
	}
	

	// --------------------- radius ----------------------------------
	/**
	 * This variable stores the current radius of this Game Object
	 */
	private double radius;
	
	/**
	 * Return the radius of this Game Object
	 */
	@Basic
	public double getRadius(){
		return this.radius;
	}
	
	/**
	 * This sets the new radius of this Game Object
	 * @param radius
	 * 			the new radius to be set to this Game Object (double)
	 * @post 	the new radius of this Game Object is set to the given radius
	 * 			| new.getRadius() == radius;
	 * @throws IllegalRadiusException
	 * 			if the radius is not valid
	 * 			| ! isValidRadius(radius)
	 */
	public void setRadius(double radius) throws IllegalRadiusException{ 
		if (! isValidRadius(radius)) {
			throw new IllegalRadiusException(radius);
		}
		this.radius = radius;
	}
	
	/**
	 * Checker for the radius
	 * @param radius
	 * 			the radius to be checked (double)
	 * @return	true if and only if the given radius is larger than 0
	 * 			| radius > 0
	 */
	public boolean isValidRadius(double radius){
		return ((radius > 0) && (radius!= Double.NaN));
	}
	
	
	//----------------------- Terminate----------------------------
	/**
	 * The variable stores the boolean whether or not this Game Object is terminated
	 */
	private boolean terminated;
	
	/**
	 * Return whether or not this Game Object is terminated.
	 */
	@Basic
	public boolean isTerminated() {
		return this.terminated;
	}

	/**
	 * This terminates this movable Game Object.
	 * @post 	the terminated is set to true
	 * 			| new.isTerminated() == true;
	 */
	public void terminate() {
		this.terminated = true;
	}
	
		
	//########## ASSOCIATIONS WITH OTHER CLASSES ##############
	/**
	 * The variable stores the current world in which the Game Object is found
	 */
	private World world;
	
	/**
	 * Return the associated World of this Game Object
	 */
	@Basic
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * This sets the associated World of this Game Object
	 * @param 	world
	 * 			the new world to be set to this Game Object
	 * @post 	the new world is set to the given world
	 * 			| new.getWorld() == world;
	 * @throws 	IllegalArgumentException
	 * 			if the world is not valid
	 * 			| ! isValidWorld(world)
	 */
	@Raw
	public void setWorld(World world)  throws IllegalArgumentException {
		if(! this.isValidWorld(world))
			throw new IllegalArgumentException();
		this.world = world;
	}
	
	/**
	 * Checker for the world
	 * @param 	world
	 * 			the world to be checked (World)
	 * @return	true if and only if the world is not null
	 * 			| world != null
	 */
	@Raw
	public boolean isValidWorld(World world){
		if (this.isTerminated())
			return true; // you are allowed to set the world to null if the object is (being)terminated.
		return (world != null);
	}
}
