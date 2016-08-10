/**
 *  practicum Object Oriented Programming with Java
 * deel 3
 * 
 * =========
 * 
 * --------------------------------------------------------------------------------------
 * | Bob Vanhoof 				| r0385580 |	Elektrotechniek - Computerwetenschappen |
 * | Matthijs Van keirsbilck 	| r0364010 |	Computerwetenschappen - Elektrotechniek |
 * --------------------------------------------------------------------------------------
 * 
 * =========
 * 
 * https://github.com/r0385580/Worms/
 * (note: this is a private repo!)
 * when you send us your email address or user name, we'll be happy to add you
 * 
 * =========
 * 
 * DO NOT FORGET TO ENABLE ASSERTIONS IN THE VM, otherwise,
 * 		the testsuite will fail and you would be able to do weird things 
 * 		in the nominally worked out methods
 * 
 * SET ALL STEP VALUES (in Worm, World, Movable) TO THEIR APPROPRIATE VALUES
 * 							|		|		|
 * 				     step (line 72) | 	timeMultiplier (line 21)
 * 								testScalar (line 25)
 * see in their doc-comment for proper values
 * 
 * We added 4 lines in ProgramParser to be able to acces globals during compilation of the worms-program 
 * (see Todo marks in ProgramParser.java)
 * 
 */

package worms.model;
import be.kuleuven.cs.som.annotate.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;

import worms.model.exceptions.IllegalActionPointException;
import worms.model.exceptions.IllegalNameException;
import worms.model.exceptions.IllegalPositionException;
import worms.model.exceptions.IllegalRadiusException;
import worms.util.Util;

/**
 * A class that implements a worm of the game Worms
 * @date 23April2014
 * 
 * @author Matthijs Van keirsbilck, Bob Vanhoof
 * @version 1.8
 * 
 * @invar	The radius of a worm is a valid radius
 * 			| isValidRadius(this.getRadius())
 * @invar 	Remaining action points of worms are larger or equal to zero, and smaller than or equal tothe maximum action points
 * 			| getActionPoints() <= 0 && getActionPoints() <= getMaximumActionPoints()
 * @invar 	Remaining hit points of (alive) worms are larger than zero, and smaller than or equal to the maximum hit points
 * 			| getHitPoints() > 0  &&  getHitPoints() <= getMaximumHitPoints
 * @invar 	The name of a worm is a valid name
 * 			| isValidName(this.getName())
 * 
 */
public class Worm extends Movable{
	
	/** variable to modify the accuracy ( and speed) of move() and fall()
	 * Default_Epsilon is for testing;
	 * set to this for playing the game: this.getRadius()/10.0;
	 */
	private double step= Util.DEFAULT_EPSILON;  //for playing the game:  this.getRadius()/10.0;
	
	/**
	 * Variable to enlarge the fall damage, so you can see that falling damages Worms.
	 */
	private final int fallDamageModifier = 1;
	
	/** Create a Worm object that has a world, an x and y position, a direction, a radius, and a name
	 * 
	 * @param 	name
	 * 			the name of this worm to be initialized (string)
	 * @effect	The name of this new worm is equal to the given name
	 * 			| new.getName() == name;
	 * @effect	the worm has as weapons the Rifle and the Bazooka, the current weapon is set to the Rifle
	 * 			| this.getWeponList().contains(Rifle) == true; this.getWeaponList().contains(Bazooka) == true
	 * 			| this.getCurrentWeapon() == Rifle
	 * @effect	the worm's action points and hit points are set to their maximum values
	 * 			| new.getRemainingActionPoints() == new.getMaximumActionPoints()
	 * 			| new.getRemainingHitPoints() == new.getMaximumHitPoints()
	 * @throws IllegalNameException
	 * 			if the name is not valid
	 * 			| ! isValidName(name)
	 */
	public Worm(World world, double xPosition,double yPosition,double direction, double radius, String name) 
			throws IllegalPositionException, IllegalRadiusException, IllegalNameException 
		{
		super(world, xPosition, yPosition, direction, radius);
		world.addWorm(this);
		this.setName(name);
		this.currentWeapon = Guns.Rifle;
		this.weaponList.add(Guns.Rifle);
		this.weaponList.add(Guns.Bazooka);
		// derived attributes
		this.remainingActionPoints = this.getMaxActionPoints(); //derived from mass (and thus from radius)
		this.remainingHitPoints = this.getMaxHitPoints(); //derived from mass (and thus from radius)
	}
	
	@Raw
	public Worm(World world, double xPosition,double yPosition,double direction, double radius, String name, Program program) 
			throws IllegalPositionException, IllegalRadiusException, IllegalNameException 
		{
		this(world, xPosition, yPosition, direction, radius, name);
		this.program = program;
		//Connection worms: master
		this.getProgram().setWorm(this);
		// here, you set in the structure of program all objects to the right worm/handler
		this.getProgram().setAppropriateValues();
		}
	

	
	// #############################################################
	// ##################### SIMPLE METHODS ########################
	// #############################################################
	// --------------------- program ----------------------------------
	/**
	 * This variable holds the Program that this worm executes (if it is a computer-controlled worm)
	 */
	private Program program = null;
	
	/**
	 * Returns true if this worm is computer-controlled
	 */
	public boolean isComputerControlled(){
		return this.getProgram() != null;
	}
	
	/**
	 * Returns 	the program of this worm 
	 * @return	this.program
	 */
	public Program getProgram(){
		return this.program;
	}
	
	// --------------------- radius ----------------------------------
	/**
	 * This overrides the setter for the new radius of this worm
	 * @param radius
	 * 			the new radius to be set to this worm (double)
	 * @post 	the new radius of this worm is set to the given radius
	 * 			| new.getRadius() == radius;
	 * @post	if the old action points are larger than the new maximum allowed action points, the new action points are set to their maximum value.
	 * 			| if (this.getRemainingActionPoints() > this.getMaxActionPoints)
	 * 			| 	this.setRemainingActionPoints(this.getMaxActionPoints);
	 * @throws IllegalRadiusException
	 * 			if the radius is not valid
	 * 			| ! isValidRadius(radius)
	 */
	@Override
	public void setRadius(double radius) throws IllegalRadiusException{
		if (! isValidRadius(radius)){
			throw new IllegalRadiusException(radius);
		}
		super.setRadius(radius);
		int maxActionPoints = this.getMaxActionPoints(); //set remaining action points to their maximum value if they get larger than the maximum value.
		if (this.getRemainingActionPoints() > maxActionPoints)
			this.setRemainingActionPoints(maxActionPoints);
	}
	
	/**
	 * Overridden checker for the radius
	 * @return	true if and only if the given radius is larger than minRadius and smaller than maxRadius (30)
	 * 			| (radius >= this.getMinRadius()) && (Movable.isValidRadius(radius))
	 */
	@Override
	public boolean isValidRadius(double radius) {
		return (radius >= Worm.getMinRadius()) && (super.isValidRadius(radius));
	}
	
		
	// ---------------- minimum radius ------------------------------
	/**
	 *  A variable that stores the minimum radius of any Worm object. Could be changed in later versions
	 */
	private static double minRadius = 0.25;
	
	/**
	 * Return the minimum radius of this worm
	 */
	@Basic
	public static double getMinRadius(){
		return Worm.minRadius;
	}
	
	// --------------------- Action points --------------------------------
	/**
	 * This variable stores the remaining action points
	 */
	private int remainingActionPoints;
	
	/**
	 * Return the remaining action points of this worm
	 */
	@Basic
	public int getRemainingActionPoints(){
		return this.remainingActionPoints;
	}
	
	/**
	 * This sets the remaining action points of this worm
	 * @param 	newRemainingActionPoints
	 * 			The new remaining action points to be assigned to this worm (int)
	 * @post	if the given action points are larger than this.getMaxActionPoints(), the new remaining action points are set to the maximum action points
	 * 			| if (newRemaingActionPoints > this.getMaxActionPoints())
				| 		new.getRemaingActionPoints() == this.getMaxActionPoints();
	 * @post	if the given action points are smaller than this.getMaxActionPoints() and larger than 0, the new remaining action points are set to the given action points
	 * 			| if (newRemaingActionPoints >= 0)
				|		new.getRemainingActionPoints() == newRemainingActionPoints;
	 * @post	if the given action points are equal to 0, the next worm's turn is started
	 * 			| if (newRemaingActionPoints == 0)
				|		new.getWorld().getActiveWorm() != this;
	 */			
	@Raw
	public void setRemainingActionPoints(int newRemainingActionPoints){
		int maxActionPoints = this.getMaxActionPoints();
		if (newRemainingActionPoints > maxActionPoints)
			newRemainingActionPoints = maxActionPoints;
		if (newRemainingActionPoints > 0)
			this.remainingActionPoints = newRemainingActionPoints;
		if (newRemainingActionPoints == 0) {
			this.remainingActionPoints = 0;
			this.getWorld().startNextTurn();
		}
		// if newRemainingActionPoints < 0, do nothing
	}
	
	/**
	 * Return the maximum amount of action points for this worm
	 * @return the maximum amount of action points, rounded to the next integer
	 * 			| Math.round(this.getMass())
	 */
	public int getMaxActionPoints(){
		return (int) Math.round(this.getMass());
	}
	
	
	// --------------------- Hitpoints --------------------------------
	/**
	 * This variable stores the remaining hit points
	 */
	private int remainingHitPoints;
	
	/**
	 * Return the remaining hit points of this worm
	 */
	@Basic
	public int getRemainingHitPoints(){
		return this.remainingHitPoints;
	}
	
	/**
	 * This sets the remaining hit points of this worm
	 * @param 	newRemainingHitPoints
	 * 			The new remaining hit points to be assigned to this worm (int)
	 * @post	if the given hit points are larger than this.getMaxHitPoints(), the new remaining hit points are set to the maximum hit points
	 * 			| if (newRemaingHitPoints > this.getMaxHitPoints())
				| 		new.getRemaingHitPoints() == this.getMaxHitPoints();
	 * @post	if the given hit points are smaller than this.getMaxHitPoints() and larger than 0, the new remaining hit points are set to the given hit points
	 * 			| if (newRemaingHitPoints >= 0)
				|		new.getRemainingHitPoints() == newRemainingHitPoints;
	 * @post	if the given hit points are equal to 0, this worm is terminated
	 * 			| if (newRemaingHitPoints == 0)
				|		new.isTerminated() == true;
	 */			
	@Basic 
	public void setRemainingHitPoints(int newRemainingHitPoints){
		int maxHitPoints = this.getMaxHitPoints();
		if (newRemainingHitPoints > maxHitPoints)
			newRemainingHitPoints = maxHitPoints;
		if (newRemainingHitPoints > 0)
			this.remainingHitPoints = newRemainingHitPoints;
		if (newRemainingHitPoints <= 0){
//			System.out.println("Worm "+ this+ " is now being terminated...");
			this.terminate();
		}
	}
	
	/**
	 * Return the maximum amount of hit points for this worm
	 * @return the maximum amount of hit points, rounded to the next integer
	 * 			| (int)Math.round(this.getMass())
	 */
	public int getMaxHitPoints(){
		return (int) Math.round(this.getMass());
	}
	
	
	// --------------------- Name ----------------------------------------
	/**
	 * This variable stores the name of this worm
	 */
	private String name;	
	
	/**
	 * Return the name of this worm
	 */
	@Basic
	public String getName(){
		return this.name;
	}
	
	/**
	 * This sets the name of this worm to the given name 
	 * @param 	name
	 * 			The new name of the worm (String)
	 * @post	The new name of this worm is set to the given name
	 * 			| new.getName() == name
	 * @throws IllegalNameException
	 * 			if the name is not valid
	 * 			| ! isValidName(name)
	 */
	@Basic
	public void setName(String name) throws IllegalNameException { 
		if (! isValidName(name))
			throw new IllegalNameException(name);
		this.name = name;
	}
	
	/**
	 * Check if the given name is a valid name
	 * @param	name
	 * 			The name to be checked (String)
	 * @return	True if ( (the length of the given name is larger than or equal to 2) AND (the first character is a capital letter)  
	 * 					AND (if all characters are allowed characters) )
	 * 			|  return (name.length >= 2) && (Character.toString(name[0]).matches("[A-Z]+")) && strName.matches(allowedCharacters)
	 */
	public boolean isValidName(String name){
		boolean validLength = name.length() >= 2;
		if (! validLength)
			return false;
		String firstLetter= Character.toString(name.charAt(0));
		return (firstLetter.matches("[A-Z]+")) && name.matches(allowedCharacters);
	}
		
	/**
	 * A static variable that stores the allowed characters for the name of any Worm object. Could be changed in later versions.
	 */
	public static String allowedCharacters = "[A-Za-z0-9\'\" ]+";
	
	
	// --------------------- Mass/Density --------------------------------
	/**
	 * A static variable that stores the density of a Worm object. Cannot be changed in later versions.
	 */
	public static final int density = 1062;
	
	/**
	 * Return the mass of this worm
	 * @returns mass of the spherical worm
	 * 			| (density* (Math.pow(this.getRadius(),3) * (4/3) * Math.PI) )
	 */
	public double getMass(){
		return (density* (Math.pow(this.getRadius(),3) * 4.0/3.0 * Math.PI) );
	}
	
	
	
	// ##########################################################################
	// ##################### MORE COMPLEX METHODS ###############################
	// ##########################################################################
	
	
	// --------------------------- Turn ------------------------------------
	/**
	 * This method turns this worm with the given angle (angle in radiant)
	 * if angle > 0 then rotation is counterclockwise
	 * @param 	angle
	 * 			the new angle to be set (in radiant) (double)
	 * @pre 	The the worm must have enough remaining action points to turn this angle
	 * 			| this.isAbleToTurn(angle)
	 * @effect 	the direction is set to the old direction + the given angle 	
	 * 			| new.getDirection() == this.getDirection() + angle
	 * @effect 	the number of action points is decreased by (30/Math.PI)*abs(angle)
	 * 			| new.getRemainingActionPoints() == this.getRemainingActionPoints() - ((30/Math.PI)*Math.abs(angle))
	 */
	//  given: h = 2*pi/f
	// > f = 2*pi/h
	//  given: p = 60/f
	// > p = 60/(2*pi/h) = (30/pi)*h
	public void turn(double angle) {
		assert isAbleToTurn(angle);
		double newdir = (this.getDirection() + angle) % (2*Math.PI);
		if (newdir < 0){
			newdir += 2*Math.PI;
		}
		this.setDirection( newdir );
		this.setRemainingActionPoints((int) (this.getRemainingActionPoints() - ((30/Math.PI)* ( Math.abs(angle) ))) );
	}
	
	/**
	 * This method checks whether this worm is able to turn
	 * @param angle
	 * 			The angle to be checked (double)
	 * @return the remaining action points after turning must be positive
	 * 			| ( this.getRemainingActionPoints() - ((30*Math.abs(angle))/Math.PI) ) > 0
	 */
	public boolean isAbleToTurn(double angle) {
		return ( this.getRemainingActionPoints() - ((30* (Math.abs(angle) ) )/Math.PI) ) > 0;
	}
	
	//-----------------   eat   -------------------------
		/**
		 * This method makes this worm eat a food object and grow if it overlaps with that food object
		 * @effect	if a food object overlaps with this worm and the new radius is valid, the new radius is set to the old radius * 1.1
		 * 			| if ( (this.getWorld.overlapsWith(this, allFoodObjects) != null) && (new.isValidRadius(this.getRadius() * 1.1) ) )
		 * 			|	new.getRadius() == this.getRadius() * 1.1
		 * @effect  if a food object overlaps with this worm, the food object is terminated
		 * 			| if ( (this.getWorld.overlapsWith(this, allFoodObjects) != null)
		 * 			|	food.isTerminated() == true
		 */
		public void eat() {
			double radiusMultiplier = 1.1;
			
			LinkedList<GameObject> allFoodObjects = new LinkedList<>();  // cast all food to GameObjects to use the general method overlapsWith
			for (Food food: this.getWorld().getAllFoodProtected()) {
				allFoodObjects.add((GameObject)food);
			}
			double[] pos =  {this.getXPosition(), this.getYPosition()};
			GameObject food = this.getWorld().overlapsWith(pos, this.getRadius(), allFoodObjects);
			
			// if this worm overlaps with food and still fits in the world
			if( (food != null) && (! food.isTerminated())){
				try{ // try to increase the radius, throws exception if worm too big
					this.setRadius(this.getRadius()*radiusMultiplier);  //grow
					food.terminate();
				}
				catch (IllegalRadiusException exc) {;// do nothing}
				}
			}
		}
			
	
	// --------------------------- Move ------------------------------------
	/**
	 * This method moves this worm
	 *
	 * @param 	steps
	 * 			The amount of steps to be moved (long)
	 * 
	 * @effect	the new x position is set to the x-position of the best adjacent position that is maximum one radius away from the current position
	 * 			| new.getXPosition() == preferredPosition[0] && preferredPositionScore== max(allSearchedPositions.getAllScores())
	 * @effect	the new y position is set to the y-position of the best adjacent position that is maximum one radius away from the current position
	 * 			| new.getXPosition() == preferredPosition[1] && preferredPositionScore== max(allSearchedPositions.getAllScores())
	 * @effect 	the action points of this worm are decreased by 1 for each radius-distance moved in the x-direction, and by 4 for each radius-distance moved in the y-direction
	 * 			| new.getRemainingActionPoints() == ((int) (this.getRemainingActionPoints() - (Math.abs(Math.cos(preferredS)) + 4 *Math.abs(Math.sin(preferredS)) )) );
	 * 
	 * @throws 	IllegalPositionException
	 * 			if the computed x or y position is invalid
	 * 			| ! isValidXPosition(newXPosition) || ! isValidYPosition(newYPosition)
	 * @throws 	SecurityException || NoSuchMethodException || InvocationTargetException || IllegalAccessException
	 * 			are never thrown except if you delete isAdjacent or isImpassable from World.java
	 * 			| if false
	 * @throws 	IllegalActionPointException 
	 * 			if this worm is not able to move
	 * 			| ! this.isAbleToMove()
	 */
	public void move() throws IllegalPositionException, IllegalAccessException, IllegalActionPointException, InvocationTargetException, NoSuchMethodException, SecurityException	
	{
		if (! this.isAbleToMove()) {
			throw new IllegalActionPointException(this.getRemainingActionPoints()); //Not enough action points
		}
		double radius= this.getRadius();
		double x = this.getXPosition();
		double y = this.getYPosition();  // removed the -radius, otherwise our isAdjacent() in the while loop would not work
		double[] oldPosition = {x, y};
		double[] preferredPosition = {x, y};
		double distanceStep = step;
		double preferredDistanceToStart = 0.001;  // not zero, otherwise division by zero in if().
		
		double direction = this.getDirection();
		double preferredS= (direction + 0.7875) % (2.0*Math.PI);  // the old preferred direction, initialize to the maximum divergence value
		double upper = (direction + 0.7875) % (2.0*Math.PI); 
		double lower = direction - 0.7875;
		double angleStep = 0.0175;
		
		
		//get the right upper and lower bounds of the angles to check (s'es)
		if (lower >= 3/2.0*Math.PI)
			upper += 2.0*Math.PI;
		
		if (lower< 0){
			lower += 2* Math.PI;
			upper += 2* Math.PI;
		}
		
		// search for the best adjacent position in all s-directions between lower and upper.
		double[] saveResult= getPreferredPositionAllS(radius, x, y, oldPosition, preferredPosition,
				distanceStep, preferredDistanceToStart, direction, preferredS,
				angleStep, upper, lower);
		preferredPosition[0]= saveResult[0]; //save the results in the proper variables
		preferredPosition[1]= saveResult[1];
		preferredS= saveResult[2];
		preferredDistanceToStart= World.pythagorianDistance(oldPosition, preferredPosition) ;
		
		//if an adjacent position was found that is in the direction of one of the s'es that is not farther than radius and farther than 0.1
		if ( (preferredDistanceToStart >= 0.1) && (preferredDistanceToStart <= radius) ){ 
			this.setXPosition(preferredPosition[0]); //go to the found adjacent position
			this.setYPosition(preferredPosition[1]);
		}
		
		// if no adjacent position in any s-direction could be found, search for a passable position in this worm's direction.
		else { 
			preferredPosition= getPreferredPositionThisDirection(radius, x, y, oldPosition,
					distanceStep, direction);
			preferredDistanceToStart= World.pythagorianDistance(oldPosition, preferredPosition) ;
			preferredS= direction;
			
			//if a passable position was found that is in this worm's direction and not farther than radius and farther than 0.1
			if ( (preferredDistanceToStart >= 0.1) && (preferredDistanceToStart <= radius) ){ 
				this.setXPosition(preferredPosition[0]); 
				this.setYPosition(preferredPosition[1]);
			}
		}
		// eat() and set the remaining action points
		this.eat();
		this.setRemainingActionPoints((int) (this.getRemainingActionPoints() - (Math.abs(Math.cos(preferredS)) + 4 *Math.abs(Math.sin(preferredS)) )) );
	}

	/**
	 * This method searches for the farthest passable position in the given direction within this worm's radius. returns the given position if no better position can be found.
	 */
	private double[] getPreferredPositionThisDirection(double radius, double x,
			double y, double[] oldPosition, double distanceStep,
			double direction) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		
		double[] nextPositionThisS;
		double[] currentBestPositionThisS;
		
		currentBestPositionThisS = oldPosition; //start at the old position
		nextPositionThisS = checkNextPosition(radius, x, y, currentBestPositionThisS, distanceStep, direction, "isImpassablePosition"); //look if the next position in this s-direction is passable
		double newCheckDistance = World.pythagorianDistance(oldPosition, nextPositionThisS) ;
		double currentBestDistance = World.pythagorianDistance(oldPosition, currentBestPositionThisS);
		
		//while currentBestDistance < nextCheckDistance <= radius, look for a better position. 
		while ( (newCheckDistance <= radius) && (newCheckDistance > currentBestDistance) )  { 
			currentBestPositionThisS = nextPositionThisS ;
			nextPositionThisS = checkNextPosition(radius, x, y, currentBestPositionThisS, distanceStep, direction, "isImpassablePosition");
			newCheckDistance = World.pythagorianDistance(oldPosition, nextPositionThisS) ;
			currentBestDistance = World.pythagorianDistance(oldPosition, currentBestPositionThisS);
		}
		return currentBestPositionThisS;
	}

	/**
	 * This method returns the best(farthest) adjacent position and its direction, in any s- direction between lower and upper, while minimizing the divergence. 
	 * returns the given position if no better position can be found.
	 */
	private double[] getPreferredPositionAllS(double radius, double x, double y,
			double[] oldPosition, double[] preferredPosition,
			double distanceStep, double preferredDistanceToStart,
			double direction, double preferredS, double step, double upper,
			double lower) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		
		double[] nextPositionThisS;
		double[] currentBestPositionThisS;
		double preferredScore = 0.0;  //the ratio (deltaL/ divergence); larger= better. start at 0.
		
		// for all directions s, look for an adjacent position within the radius. Maximize the distance and minimize the divergence(=abs(direction -s))
		for (double s= lower- step; s<= upper+ step; s += step ){  
			s = roundToCircleQuarters(s); //if very close to 0, Pi/2, Pi, 3*Pi/2, set s to that angle (to cover rounding errors)
			currentBestPositionThisS = oldPosition; //start at the old position
			nextPositionThisS = checkNextPosition(radius, x, y, currentBestPositionThisS, distanceStep, s, "isAdjacentPosition"); //look if the next position in this s-direction is passable
			double newCheckDistance = World.pythagorianDistance(oldPosition, nextPositionThisS) ;
			double currentBestDistance = World.pythagorianDistance(oldPosition, currentBestPositionThisS);
			
			//while currentBestDistance < nextCheckDistance <= radius, look for a better position.
			while ( (newCheckDistance <= radius) && (newCheckDistance > currentBestDistance) )  { // within radius and farther than previous
				currentBestPositionThisS = nextPositionThisS ;
				nextPositionThisS = checkNextPosition(radius, x, y, currentBestPositionThisS, distanceStep, s, "isAdjacentPosition");
				newCheckDistance = World.pythagorianDistance(oldPosition, nextPositionThisS) ;
				currentBestDistance = World.pythagorianDistance(oldPosition, currentBestPositionThisS);
			}
			
			// save the best position of this s if it's better than the current best position of all s'es
			double scoreThisS= getScoreThisS(direction, s, currentBestDistance);
			if ( scoreThisS > preferredScore && (currentBestDistance <= radius)) 
			{ 
				preferredPosition[0] = currentBestPositionThisS[0];
				preferredPosition[1] = currentBestPositionThisS[1];
				preferredS= s; 
				preferredScore = scoreThisS;
			}
		}
		double[] result= {preferredPosition[0], preferredPosition[1],preferredS};
		return result;
	}

	/**
	 * This method is used to minimize rounding errors
	 */
	private double roundToCircleQuarters(double s) { 
		if (s<= Util.DEFAULT_EPSILON || (Math.abs(s - 2*Math.PI)) < Util.DEFAULT_EPSILON) {
			s=0.0;
		}
		if (Math.abs(s- Math.PI) <= Util.DEFAULT_EPSILON) {
			s=Math.PI;
		}
		if (Math.abs(s- 3.0/2.0*Math.PI) <= Util.DEFAULT_EPSILON) {
			s=3.0/2.0*Math.PI;
		}
		if (Math.abs(s- 1.0/2.0*Math.PI) <= Util.DEFAULT_EPSILON) {
			s=1.0/2.0*Math.PI;
		}
		return s;
	}

	/**
	 * This method calculates the score for the best position in a direction s based on the distance and the divergence with the worm's direction
	 */
	private double getScoreThisS(	double direction, double s, double currentBestDistance) { 
		double result= (currentBestDistance /  (0.0001+ angleDifference(direction, s)));  
		result= (double)Math.round(result * 100000) / 100000;
		return result;
	}
	
	/** 
	 * This method returns the difference in angles between two angles.
	 */
	private double angleDifference (double angle1, double angle2) {
		if (angle1 > Math.PI) {
			angle1 -= 2*Math.PI;
			angle1 = Math.abs(angle1);
		}
		if (angle2 > Math.PI) {
			angle2 -= 2*Math.PI;
			angle2 = Math.abs(angle2);
		}
		return Math.abs(angle1- angle2) ;
	}
	
	/**
	 * This method returns the next position in the direction s if that position isAdjacent or Passable  (the method to be used is given as an argument) 
	 */
	private double[] checkNextPosition(double radius, double x, double y, double[] bestPositionThisS, double distanceStep, double s, String methodName) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException { 
		Class<?>[] cArg = {double.class, double.class, double.class};
		Method method = (World.class).getMethod(methodName, cArg);
		
		double[] newPosition = {bestPositionThisS[0] + distanceStep* Math.cos(s) , bestPositionThisS[1] + distanceStep* Math.sin(s)};
		newPosition[0]= (double)Math.round(newPosition[0] * 100000) / 100000.0; //round to 5 digits
		newPosition[1]= (double)Math.round(newPosition[1] * 100000) / 100000.0;
		
		// if you're looking out of the world, stop the function
		if ( (! this.isValidXPosition(newPosition[0])) || (! this.isValidYPosition(newPosition[1])) ){
//			System.out.println("invalid positions, breaking...");
			return bestPositionThisS; 
		}
		if (methodName == "isAdjacentPosition") { //return the new position if it's adjacent
			if ( (boolean)method.invoke(this.getWorld(), newPosition[0], newPosition[1], radius)) {
				return newPosition;
			}
		}
		else if (methodName == "isImpassablePosition"){//return the new position if it's passable
			if ( !(boolean)method.invoke(this.getWorld(), newPosition[0], newPosition[1], radius)) {
				return newPosition;
			}
		}
		return bestPositionThisS; //if not, return the old position
	}
	
	
	/**
	 *  This method checks whether this worm is able to move
	 *  
	 *  @return	The remaining action points after moving the given number of steps must be positive
	 *  		| (this.getRemainingActionPoints() >= steps * (Math.abs(Math.cos(this.getDirection())) + 4 * Math.abs(Math.sin(this.getDirection())))
	 */
	public boolean isAbleToMove(){
		return (this.getRemainingActionPoints() >= (Math.abs(Math.cos(this.getDirection())) + 4 * Math.abs(Math.sin(this.getDirection()))))
				&& (this.getRemainingActionPoints() >= (Math.abs(Math.cos(this.getDirection() - 0.7875)) + 4 * Math.abs(Math.sin(this.getDirection() - 0.7875))))
				&& (this.getRemainingActionPoints() >= (Math.abs(Math.cos(this.getDirection() + 0.7875)) + 4 * Math.abs(Math.sin(this.getDirection() + 0.7875))));	
	}
	
	
	// --------------------------- Jump ------------------------------------
	
	/**
	 * This method launches this worm in the direction of the given angle with a force depending on the remaining action points, eat()s at the landing position, and sets its remaining action points to 0.
	 * @param	timeStep
	 * 			the accuracy with which to determine the jump trajectory
	 * @effect 	if the worm survives the jump,  and if there is a Food object at the landing position of this jump, the Worm eat()s it
	 * 			| if (for_one(this.getWorld().getAllFood().overlapsWith(getJumpStep(getJumpTime(timeStep))[0], getJumpStep(getJumpTime(timeStep))[1], this.getRadius())
	 * 			|  then food.isTerminated() == true
	 * @effect if the worm survives the jump, the remaining action points are zero
	 * 		| this.getRemainingActionPoints() == 0
	 */
	@Override
	public void jump(double timestep){
		if (this.isAbleToJump()) {
			super.jump(timestep);
			if (! this.isTerminated()){ //if the worm still lives after the jump
				this.eat();
				this.setRemainingActionPoints(0);
			}
		}
		
	}
	
	/**
	 * Checks whether the worm is able to jump
	 * @return true if the remaining action points are positive and the current position of the worm is a passable position
	 * 			| return (this.getRemainingActionPoints() > 0) && (! this.getWorld().isImpassablePosition(this.getXPosition(), this.getYPosition(), this.getRadius()) )
	 */
	@Override
	public boolean isAbleToJump() {
		boolean result = true;
		if (this.getRemainingActionPoints() <= 0 )
			result = false;
		if (this.getWorld().isImpassablePosition(this.getXPosition(), this.getYPosition(), this.getRadius()) )
			result = false;
		return result;
	}
	
	/** Gets the force with which this worm jumps.
	 * returns the mass * the earth's gravitational accelleration + 5 * the remaining action points of this worm
	 * 		| 5*this.getRemainingActionPoints + this.getMass()*g
	 */
	@Override
	public double getForce(){
		double mass = this.getMass();
		double F = 5* this.getRemainingActionPoints() + mass* g;
		return F;
	}
	
	
	//-------------------------- Fall  --------------------------
	/**
	 * This method makes this worm to fall until it hits impassable terrain or falls out of the world
	 * @effect	if this worm fell out of the world, this worm is terminated and the next worm's turn is started
	 * 			| if (for_all i>0 : this.getWorld().isAdjacentPosition{this.getXPosition, this.getYPosition() - i} == false
	 * 			|	then (new.isTerminated()) == true && (new this.getWorld()).getActiveWorm() != this
	 * @effect 	if this worm did not fall out of the world, the x position doesn't change
	 * 			| if if (for_one i>0 : this.getWorld().isAdjacentPosition{this.getXPosition, this.getYPosition() - i} == true
	 * 			|	new.getXPosition() = this.getXPosition()
	 * @effect 	if this worm did not fall out of the world, the y position is set to the first adjacent position under the startposition of this fall
	 * 			| if (for_one i>0 : this.getWorld().isAdjacentPosition{this.getXPosition, this.getYPosition() - i} == true
	 * 			| 	new.getYPosition() == this.getYPosition() - i
	 * @effect 	if this worm survives the fall,  and if there is a Food object at the landing position of this fall, the Worm eat()s it
	 * 			| if (for_one(this.getWorld().getAllFood().overlapsWith(fallEndPosition[0], fallEndPosition[1], this.getRadius()) 
	 * 			|  then food.isTerminated() == true
	 * 
	 * @throws	IllegalAccessError
	 * 			if this worm is not able to fall
	 * 			|  if (! this.isAbleToFall())
	 * 			| 	then throw new IllegalAccessError("This Worm is not able to fall");
	 */
	public void fall() throws IllegalAccessError {
		if (! this.isAbleToFall())
			throw new IllegalAccessError("This Worm is not able to fall");
		double step = this.step;
		double fallenMeters = 0;
		double[] fallEndPosition = {this.getXPosition(), this.getYPosition()} ; 
		double radius = this.getRadius();
		World world = this.getWorld();
		
		while ( (!world.isAdjacentPosition(fallEndPosition[0], fallEndPosition[1], radius)) ) {													
				fallEndPosition[1] -= step;
				fallenMeters += step;
				if (!world.isFullyInWorld(fallEndPosition[0],fallEndPosition[1],radius)){ //if the worm left the world 
					this.terminate();
					return;
				}
		}
		this.setYPosition(fallEndPosition[1]);	
		this.eat();
		this.setRemainingHitPoints(this.getRemainingHitPoints() - (int)fallenMeters* fallDamageModifier);   //it's possible you die here, so put this last.
	}
	
	/**
	 * Returns whether this worm is able to fall from its current position
	 * @return true if the current position is not adjacent to impassable terrain.
	 */
	public boolean isAbleToFall(){
		return (! this.getWorld().isAdjacentPosition(this.getXPosition(), this.getYPosition(), this.getRadius()));
	}
	
	
	// -------------------- Team ------------------------------------
	// CONNECTION: MASTER
	/**
	 * Variable containing a team
	 */
	private Team team = null;
	
	/**
	 * Returns whether this worm is a valid possible member of a team
	 * @return	true if this worm is not terminated and if this worm does not yet belong to a team.
	 * 			| true if (this.isTerminated() == false) && (this.getTeam() != null)
	 */
	protected boolean isValidTeamMember(){
		return (this.isTerminated() == false) && (this.getTeam() != null);
	}
	
	/**
	 * Returns the team to which this worm belongs
	 * @return this.team
	 */
	public Team getTeam(){
		return this.team;
	}
	
	/**
	 * Adds this worm to the given team
	 * @param team
	 * 			The team to which this worm should be added
	 * @effect 	if given team is a valid team, this worm is added to the given team
	 * 			|  if (this.isValidTeam(team))
	 * 			|		new.getTeam() == team;
	 * 			| 		(new team).getAllTeamMembers().contains(this)
	 * @throws 	IllegalArgumentException
	 * 			if the given team is not a valid team
	 * 			| if ( !this.isValidTeam(team))
	 */
	public void addToTeam(Team team) throws IllegalArgumentException {
		if (! this.isValidTeam(team)){
			throw new IllegalArgumentException("Not a valid team / Already in a team");
		}
		team.addTeamMember(this);
		this.team = team;
	}
	
	/**
	 * This method checks whether this worm can be added to the given team (so it checks whether the worm is already in a team)
	 * @param team
	 * 			The team to be checked
	 * @return
	 * 			true if this worm does not belong to a team yet and if the given team exists
	 * 			|true if ( (! this.isMemberOfATeam()) && (team != null) )
	 */
	private boolean isValidTeam(Team team) {
		if (this.isValidTeamMember())
			return false;
		return (team != null);
	}
	
	// ---------------------- World ------------------------------------
	//CONNECTION : SLAVE
	// 	*see GameObject*
	
	//---------------------- Terminate -------------------------------------	
	/**
	 * This method terminates a Worm object (e.g. if it has been killed) (overridden method)
	 * @effect	the team bindings are broken
	 * 			| ! (new (this.getTeam()).getAllTeamMembers().contains(this) )
	 * 			| new.getTeam() == null
	 * @effect	the world bindings are broken
	 * 			| ! new (this.getWorld()).getAllWorms().contains(this)
	 * 			| new.getWorld() == null
	 */
	@Override
	public void terminate() {
		boolean wasActiveWorm= this.getWorld().getActiveWorm() == this;
		// -remove from team
		Team team= this.getTeam();
		if (team != null) {
			team.removeTeamMember(this);
			this.team = null;
		}
		
		if (this.getProgram() != null){  //if this worm has a program, remove the bindings
			this.getProgram().terminate();
			this.program = null;
		}
		
		//set this.terminated = true
		super.terminate(); 
				
		// -remove from World
		World world = this.getWorld();
		this.getWorld().removeWorm(this);
		this.setWorld(null);
				
		if (wasActiveWorm) {//if this worm was the active worm (if not, a worm has shot and killed another, and the turn of the killer worm should not end)	 
			world.startNextTurn();
		}
		else if (world.isGameOver()){ //if the game is over because of the termination of this worm, end the game.
				world.endGame();
		}
		
	}
	
	
	//----------------------- Violence -------------------------------------
	/**
	 * Variable contains current weapon
	 */
	private Guns currentWeapon;
	
	/** Sets the current weapon of this worm to the given weapon
	 * 
	 * @param 	weapon
	 * 			weapon to be set as current for this worm
	 * @effect 	the current weapon of this worm is set to the given weapon
	 * 			| new.getCurrentWeapon() == weapon
	 */
	public void setCurrentWeapon(Guns weapon) {
		this.currentWeapon = weapon;
	}
	
	/** Gets the current weapon of this worm
	 * @return the currently equipped weapon of this worm
	 */
	public Guns getCurrentWeapon() {
		return this.currentWeapon;
	}
	
	/**
	 * variable contains all weapons this worm possesses.
	 */
	private LinkedList<Guns> weaponList= new LinkedList<>();
	
	/** This gives all the possessed weapons of this worm in a linked list
	 * @return a linked list with all the posessed weapons of this worm
	 */
	public LinkedList<Guns> getAllPosessedWeapons() {
		return this.weaponList;
	}
	
	/** Selects the next weapon for this Worm
	 * @effect	the next weapon of the worm's posessed weapons is selected
	 * 			if the last weapon is the currently selected weapon, the next selected weapon is the first weapon. 
	 * 			else, it is the next weapon in the list.
	 * 			| if( this.getAllPosessedWeapons().indexOf(this.getCurrentWeapon()) +1 >= this.weaponList.size())
	 * 			|	new.getCurrentWeapon() == this.getAllPosessedWeapons().getFirst() 
	 *			| else
	 *			|	new.getCurrentWeapon() == this.getAllPosessedWeapons().get(this.getAllPosessedWeapons().indexOf(this.getCurrentWeapon()) +1);
	 */
	public void selectNextWeapon(){
		LinkedList<Guns> weaponList = this.getAllPosessedWeapons();
		int currentIndex = weaponList.indexOf(this.getCurrentWeapon());
		if( currentIndex +1 >= weaponList.size()){
			this.setCurrentWeapon( weaponList.getFirst() );
		}
		else
			this.setCurrentWeapon( weaponList.get(currentIndex +1) );
	}
	
	/**
	 * Makes the given worm shoot its active weapon with the given propulsion yield.
	 * @effect 	a projectile is created 
	 * 			| p.getXPosition() == this.this.getXPosition() + radius * Math.cos(direction)
	 * 			| p.getYPosition() == this.this.getYPosition() + radius * Math.sin(direction) + radius
	 * 			| p.getDirection() == this.getDirection
	 * 			| this.getWorld.getActiveProjectile() == p
	 * @effect 	a projectile is shot with the given propulsion.
	 * 			| p.getXPosition() == p.getJumpStep(p.getnJumpTime())[0]
	 * 			| p.getYPosition() == p.getJumpStep(p.getnJumpTime())[1]
	 * @effect	if the projectile's landing position overlaps with a worm, that worm's hitpoints are decreased with the damage of this weapon.
	 * 			| if (p.getWorld().OverlapsWith(p,wormList) != null)
	 * 			|	overlappedWorm.getRemainingHitPoints() == worm.getRemainingHitPoints()- this.getCurrentWeapon.getHitPointsDamage()
	 * @effect	the projectile is terminated.
	 * 			| p.isTerminated == true
	 * 			| this.getWorld.getActiveProjectile() == null
	 * @effect 	the action points of this worm are decreased with the action points consumption of this weapon.
	 * 			| new.getRemainingActionPoints == this.getRemainingActionPoints - this.getCurrentWeapon().getActionPointsConsumption
	 * 
	 * @throws	IllegalActionPointException
	 * 			if this worm is not able to shoot
	 * 			| if (! this.isAbleToShoot() )
	 */
	public void shoot(int propulsion) throws IllegalActionPointException{
		if (! this.isAbleToShoot())
			throw new IllegalActionPointException(this.getRemainingActionPoints());	
		double direction = this.getDirection();
		double radius = this.getRadius();
		double x = this.getXPosition() + 1.1*radius * Math.cos(direction);
		double y = this.getYPosition() + 1.1*radius * Math.sin(direction);
		new Projectile(this.getWorld(), x, y, direction, propulsion, this.getCurrentWeapon());
		this.setRemainingActionPoints(this.getRemainingActionPoints()- this.getCurrentWeapon().getActionPointsConsumption());
	}
	
	/** This method checks whether this worm is able to shoot
	 * Returns true if the worm has enough action points to shoot
	 * 			| result == (this.getCurrentWeapon.getActionPointsConsumption() <= this.getRemainingActionPoints() )
	 */
	public boolean isAbleToShoot(){
		if (this.getCurrentWeapon().getActionPointsConsumption() > this.getRemainingActionPoints())
			return false;
		else
			return true;
	}
}
