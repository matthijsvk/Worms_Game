package worms.model;

import java.util.LinkedList;

import worms.model.exceptions.IllegalPositionException;
import worms.model.exceptions.IllegalRadiusException;
import be.kuleuven.cs.som.annotate.Basic;

/**
  A class that implements the movable game objects form the game 'Worms'
 * 
 * @date 21April2014
 * @author  Matthijs Van keirsbilck, Bob Vanhoof
 * @version 1.3
 *
 * @invar direction is larger or equal to 0 and smaller than 2*Pi
 * 			| isValidDirection(this.getDirection())
 */
public abstract class Movable extends GameObject {
	
	double timeMultiplier = 1;  // 1 for test because otherwise jumpTime has to deal with too much iterations (set to 10 for use in GUI)
	
	/**Create a Movable Game Object that has an x and y position, a direction, a radius and is set in a World
	 * 
	 * @pre		the direction is valid
	 * 			| isValidDirection(direction)
	 * @param 	direction
	 * 			the direction of this Movable Game Object to be initialized
	 * @effect 	the direction is set to the given direction
	 * 			| new.getDirection() == direction;
	 */
	public Movable(World world, double xPosition, double yPosition, double direction, double radius) 
			throws IllegalPositionException,IllegalRadiusException {
		super(world, xPosition, yPosition, radius);
		this.setDirection(direction);
	}
	
	
	// -------------------------- Abstract Methods -------------------------
	/**
	 * This returns the mass of this movable game object.
	 * @return this.mass (for projectiles)
	 * @return the mass for spherical worm objects
	 */
	public abstract double getMass();
	
	/**
	 * This returns the initial force, given to this movable game object at the beginning of a jump
	 * @return force
	 */
	public abstract double getForce();

	
	// -------------------------- Direction --------------------------------
	/**
	 * This variable stores the current direction of this movable game object
	 */
	private double direction;
	
	/**
	 * Return the direction of this movable game object
	 * @return this.direction
	 */	
	@Basic
	public double getDirection(){
		return this.direction;
	}
	
	/**
	 * This sets the new direction of this movable game object
	 * @param 	newDirection
	 * 			the new direction to be set to this worm (double)
	 * @pre 	the given direction must be a valid direction
	 * 			| isValidDirection(newDirection) == true;
	 * @post 	the direction of this worm is set to the given direction
	 * 			| new.getDirection() == newDirection;
	 */
	public void setDirection(double newDirection){
		assert isValidDirection(newDirection);
		this.direction = newDirection;
	}
	
	/** A function that verifies whether a given direction is valid
	 * @param direction
	 * 			the direction to be checked (double)
	 * @return 0 <= direction <= 2*Math.PI
	 * 			| 0 <= direction && direction <= 2*Math.PI
	 */
	public static boolean isValidDirection(double direction){
		return ( (0 <= direction) && (direction <= 2*Math.PI));
	}

	
	// --------------------------- Jump ------------------------------------
	/**
	 * This variable describes the gravitational constant g
	 */
	public static final double g = 9.80665;
	
	/**
	 * This method makes this movable game object jump
	 * 
	 * @param   timeStep
	 * 			the time step in which this movable game object isn't going to fly trough impassable terrain
	 * @effect the new x position of this movable game object is set to the adjacent position after it has made a parabolic-shaped jump
	 * 			| new.getXposition() == this.getJumpStep(this.getJumpTime(timeStep))[0]
	 * 			| new.getWorld.isAdjacent(new.getXPosition(), new.getYPosition(), new.getRadius()) == true
	 * @effect the new y position of this movable game object is set to the adjacent position after it has made a parabolic-shaped jump
	 * 			| new.getXposition() == this.getJumpStep(this.getJumpTime(timeStep))[1]
	 * 			| new.getWorld.isAdjacent(new.getXPosition(), new.getYPosition(), new.getRadius()) == true
	 * @effect if the new position is not valid, this movable game object is terminated
	 * 			| if (!new.isValidPosition(new.getXPosition) || (!new.isValidPosition(new.getXPosition))
	 * 			|	new.isTerminated == true
	 */
	public void jump(double timeStep){
		double time = this.getJumpTime(timeStep);
		double[] newPosition = {this.getRadius(),this.getRadius()}; //arbitrary init.
		try{
			newPosition = getJumpStep(time);
			double[] oldPosition= {this.getXPosition(), this.getYPosition()};
			double sigma = 0.1;  // the minimum distance you can jump.
			// hitting impassable terrain
			if (World.pythagorianDistance(newPosition, oldPosition) >= sigma) {
				this.setXPosition(newPosition[0]);
				this.setYPosition(newPosition[1]);
			}
		}
		catch(IllegalPositionException e){
			this.terminate();
		}
		
	}
	
	/**
	 * Return the airtime for a jump from the current position, in the current direction, and without hitting impassable terrain
	 * 
	 * @return 	the airtime for a jump from the current position, in the current direction, and without hitting impassable terrain
	 * 			| double[] pos = this.getJumpStep(airtime)
	 * 			| this.getWorld().isAdjacent(pos[0],pos[1],this.getRadius()) == true && airtime != 0
	 */
	public double getJumpTime(double timeStep) {
		double timeMultiplier = this.timeMultiplier;  // because otherwise this function has to deal with too much iterations
		double[] current = {this.getXPosition(), this.getYPosition()};
		double previousTime = 0.0;
		double time = timeMultiplier*timeStep;
		double radius = this.getRadius();
		
		LinkedList<GameObject> allWormObjects = new LinkedList<>();  // cast all worms to GameObjects
		for (Worm worm: this.getWorld().getAllWormsProtected()) {
			allWormObjects.add((GameObject)worm);
		}
		while ( (this.isValidXPosition(current[0]) && this.isValidYPosition(current[1])) 
				&& !(this.getWorld().isImpassablePosition(current[0], current[1], radius))
				&& ( (this.getWorld().overlapsWith(current, radius, allWormObjects) == null )
					|| (this.getWorld().overlapsWith(current, radius, allWormObjects) == this)) ) { //while not off the map or hit impassable terrain... and you didn't hit  a worm
			previousTime = time;
			time += timeMultiplier*timeStep;
			try{
				current = getJumpStep(time);
			}
			catch (IllegalPositionException e){
				return time;
			}
		}
		return previousTime;
	}
	
	/**
	 * Return the x and y position on a given DeltaT after starting the jump.
	 * 
	 * @param 	time
	 * 			the time this movable game object has jumped (double)
	 * @return	the xPosition is increased/decreased with v0x * jump time
	 * 			|	return this.getXPosition() + v0x * time
	 * @return the yPosition is increased/decreased with v0y * time - 0.5 * g * time^2
	 * 			| return this.getYPosition() + v0y * time - 0.5* g* Math.pow(time, 2);
	 * @throws IllegalPositionException
	 * 			if the calculated x or y position are invalid
	 * 			| ! isValidXPostion(xDeltaT) || ! isValidYPosition(yDeltaT)
	 */
	public double[] getJumpStep(double time) throws IllegalPositionException {	
		double direction = this.getDirection();
		double v0 = this.getV0();
		double v0x = v0 * Math.cos(direction);
		double v0y = v0 * Math.sin(direction);
		
		double xDeltaT =  this.getXPosition() + v0x * time;
		double yDeltaT = this.getYPosition() + v0y * time - 0.5* g* Math.pow(time, 2);
		
		if (! isValidXPosition(xDeltaT))
			throw new IllegalPositionException(xDeltaT, 'x');
		if (! isValidYPosition(yDeltaT))
			throw new IllegalPositionException(yDeltaT, 'y');
		
		double[] result = {xDeltaT,yDeltaT};
		return result;
	}
	
	
	// helper functions for the (derived) jump function
	/**
	 * Checks whether this movable game object is able to jump
	 * 
	 * @return true if the remaining action points are positive and this worm is oriented upwards
	 * 			| (this.getRemainingActionPoints() > 0) && (this.getDirection() < Math.PI) 
	 */
	public boolean isAbleToJump() {
		boolean result = true;
		if (this.getDirection() > Math.PI)
			result = false;
		return result;
	}
	
	/**
	 * Returns V0, as specified in the assignment.
	 * @return 	v0, the beginning velocity of this movable object: |F/(Mass*2)|
	 * 			| v0 == Math.abs(this.getForce()/this.getMass()*0.5
	 */
	protected double getV0() {
		double mass = this.getMass();
		double F = this.getForce();
		double v0 = F/ mass *0.5;
		return Math.abs(v0);
	}

}
