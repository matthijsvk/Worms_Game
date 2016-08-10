package worms.model;

import java.util.LinkedList;
import java.util.Random;
import worms.model.exceptions.IllegalNameException;
import worms.model.exceptions.IllegalPositionException;

/**
 * A class that implements a world in which a Worms game can take place
 * 
 * @date 21april2014
 * @author Matthijs Van keirsbilck, Bob Vanhoof
 * @version 1.2
 * 
 * @invar 	the world contains at most one object of the class Projectile at a time.
 * @invar	The world contains each game object at most once.
 * @invar	All objects in this world must lie fully within the world.
 *
 */
public class World {
	
	/**
	 * Variable used to make the GUI go faster.
	 */
	protected static int testScalar= 1; // 1 used for testing, to make it faster use 5
	
	/**
	 * Constructor for this World
	 * 
	 * @param 	width
	 * 			The Width of this world in meters (double)
	 * @param 	height
	 * 			The Height of this world in meters (double)
	 * @param 	impassablePositions
	 * 			The map with impassable positions of this world (boolean matrix)
	 * @param 	random
	 * 			a random object of the class Random, used to calculate Random numbers
	 * @pre		| World.isValidWidth()
	 * @pre		| World.isValidHeight()
	 * @effect	| new.getWidth() == width
	 * @effect	| new.getHeight() == height
	 * @effect 	| new.getImpassablePositions() == impassablePositions
	 * @effect 	| new.getActiveProjectile() == null
	 * @effect	| new.getPixelWidth() == width/(double)impassablePositions[0].length
	 * @effect	| new.getPixelHeight() == height/(double)impassablePositions.length;
	 */
	public World(double width, double height, boolean[][] impassablePositions,Random random){
		assert isValidWidth(width);
		this.width = width;
		
		assert isValidHeight(height);
		this.height = height;
		
		this.pixelwidth = width/(double)impassablePositions[0].length ;  // # meters per pixel (width)
		this.pixelheight = height/(double)impassablePositions.length; 	 // # meters per pixel (height)
		//isGameFinished is automatically initialized on false
		this.setImpassablePositions(impassablePositions);
		this.ActiveProjectile = null;
	}
	
	
	// ----------- constants -------------------
	/**
	 * Variables to declare the max Width and Height
	 */
	public static final double maxWidth= Double.MAX_VALUE;
	public static final double maxHeight= Double.MAX_VALUE;
	
	/**
	 * Variables to declare the width and height of one pixel (in meters)
	 */
	private final double pixelwidth;
	private final double pixelheight;
	
	/**
	 * Returns the width of one pixel in this world
	 */
	public double getPixelwidth() {
		return this.pixelwidth;
	}
	
	/**
	 * Returns the height of one pixel in this world
	 */
	public double getPixelheight() {
		return this.pixelheight;
	}
	
	
	// ----------- WIDTH ------------------------
	/**
	 * Variable holds the Width of this world
	 */
	private final double width;
	
	/**
	 * Returns the Width of this world
	 */
	public double getWidth() {
		return this.width;
	}
	
	/**
	 * Checks if the given width is valid
	 * @param 	width
	 * 			the width to be checked
	 * @return	| width < maxWidth && width> 0
	 */
	public static boolean isValidWidth(double width) {
		return width < maxWidth && width> 0;
	}

	
	// ----------- HEIGHT -----------------------
	/**
	 * Variable holds the Height of this world
	 */
	private final double height;
	
	/**
	 * Returns the Height of this world
	 */
	public double getHeight() {
		return this.height;
	}
	
	/**
	 * Checks if the given height is valid
	 * @param 	height
	 * 			the height to be checked
	 * @return	| height < maxHeight && height> 0
	 */
	public static boolean isValidHeight(double height) {
		return height < maxHeight && height> 0;
	}

	
	// ----------- IMPASSABLE POSITIONS ---------
	/**
	 * Variable holds the map of impassable positions of this world
	 */
	private boolean[][] impassablePositions;
	
	/**
	 * Returns the map of impassable positions of this world
	 */
	public boolean[][] getImpassablePositions() {
		return this.impassablePositions;
	}

	/**
	 * Sets the map of impassable positions 
	 * @param 	impassablePositions
	 * 			the map of impassable positions to be set
	 * @post	| new.getImpassablePositions() == impassablePositions
	 */
	public void setImpassablePositions(boolean[][] impassablePositions) {
		this.impassablePositions = impassablePositions;
	}

	
	// -------------  GAME STATUS ------------- 
	/**
	 * Variable holds whether or not the game is finished in this world
	 */
	private boolean isGameFinished = false;
	
	/**
	 * Returns whether or not the game in this world is finished
	 */
	public boolean isGameFinished() {
		return this.isGameFinished;
	}
	
	/**
	 * Sets the finishing status of this world to the given status
	 * @param 	isGameFinished
	 * 			the status of the game in this world to be set
	 * @post	| new.isGameFinished() == isGameFinished
	 */
	public void setGameFinished(boolean isGameFinished) {
		this.isGameFinished = isGameFinished;
	}
	
	/** 
	 * This method returns the winning team of this world
	 * @return	| if team.nbWormsthisTeam > maxNbWorms
	 * 			|	winningTeam == team
	 */
	public Team getWinningTeam() {
		int maxNbWorms = 0;
		int nbWormsThisTeam = 0;
		Team winningTeam = null;
		for (Team team: this.getAllTeams()) {
			nbWormsThisTeam = team.getAllTeamMembers().size();
			if (nbWormsThisTeam > maxNbWorms){
				winningTeam= team;
				maxNbWorms = nbWormsThisTeam;
			}
		}
		return winningTeam; 
	}
	
	/** 
	 * This method returns the winning worm
	 * @return	| if worm.getRemainingHitPoints > maxHitPoints
	 * 			|	winningWorm == worm
	 */
	public Worm getWinningWorm() {
		int maxHitPoints = 0;
		Worm winningWorm = null;
		for (Worm worm:this.getAllWorms()) {
			if (worm.getRemainingHitPoints() > maxHitPoints){
				winningWorm = worm;
				maxHitPoints = worm.getRemainingHitPoints();
			}
		}
		return winningWorm;
	}
	
	/** 
	 * This method starts the game.
	 * @effect  | if ! this.getAllWorms().size() <=1
	 * 			|	new.getGameFinished() == false
	 * @effect	| if  this.getAllWorms().size() <=1
	 * 			|	new.getGameFinished() == true
	 */
	public void startGame() {
		this.setGameFinished(false);
		this.setActiveWorm(getAllWormsProtected().getFirst());// set as active worm the first worm in the wormList 
		if (this.getAllWorms().size() <=1 ) {
			endGame();
		}
		if (this.getActiveWorm().isComputerControlled()){
			this.getActiveWorm().getProgram().allExec();
			//System.out.println("the active worm is computer controlled, start next turn after having executed all");
			startNextTurn();
		}
	}
	
	/** 
	 * This method ends the game.
	 * @effect	| new.isGameFinished() == true
	 */
	public void endGame() {
		this.setGameFinished(true);
	}
	
	/**
	 * End the game if the game is over, else start the turn of the next worm in this world.
	 * @effect	| if (!isGameOver)
	 * @effect	| else
	 * 			|	endGame
	 */
	public void startNextTurn() {
		if (isGameOver()){
			endGame();
		}
		else{
			nextWormTurn();
		}
	}
	
	/**
	 * Returns whether the game is over or not.
	 * @effect	removes all empty teams from this world
	 *			| if team.getAllTeamMembers.size()==0
	 * 			| 	this.removeTeam()
	 * 			|	team.terminate()
	 * @return	true if true if there is only one team left and all worms left in the world are in this team.
	 * 			| (nbTeams == 1) &&  (wormListSize == this.getAllTeams().get(0).getAllTeamMembers().size()) 
	 *@return	true if there are no teams left and only one worm.
	 *			| nbTeams <=0 && wormListSize <=1
	 * 
	 */
	protected boolean isGameOver() {
		//check if any team has no members left. If so, remove it
		for (Team team: this.getAllTeams()) { 
			if (team.getAllTeamMembers().size() == 0) {
				this.removeTeam(team);
				team.terminate();
			}
		}
		int wormListSize= this.getAllWormsProtected().size();
		int nbTeams= this.getAllTeamsProtected().size();
		
		if (nbTeams > 0) { // teams still exist
			// no team has won yet
			if ((nbTeams == 1) &&  (wormListSize == this.getAllTeams().get(0).getAllTeamMembers().size() ) ){
				return true;
			}
			return false;
		}
		// no teams left, and no more than one worm left
		else if (wormListSize <= 1) {
			return true; 
		}
		return false;
	}

	/**
	 * This sets the next worm to the active Worm.
	 * This sets the remaining actionPoints of the active worm to the max action points of the active worm
	 * | new activeworm.getRemainingActionPoints() == new activeWorm.getMaxActionPoints()
	 * This adds 10 to the remaining hit points of the active worm
	 * | new activeworm.getRemaingHitPoints() == aciveworm.getRemaingHitPoints()+10
	 */
	private void nextWormTurn() {
		int newActiveWormIndex= (this.getActiveWormIndex()+ 1) % this.getAllWorms().size(); //+1 and go back to 0 if end of list.
		setActiveWorm(this.getAllWormsProtected().get(newActiveWormIndex));
		Worm worm = this.getActiveWorm();
		worm.setRemainingActionPoints(worm.getMaxActionPoints());
		worm.setRemainingHitPoints(worm.getRemainingHitPoints() + 10);
		if (worm.isComputerControlled()){
			worm.getProgram().allExec();
			this.startNextTurn();
		}
	}

		
	// --------------- Active items --------------------------------------------------------------------
	
	//-----------------active Projectile-----------------
	/**
	 * Variable holds the active projectile of this world
	 */
	private Projectile ActiveProjectile;
	
	/**
	 * Return the active projectile of this world
	 */
	protected Projectile getActiveProjectile() {
		return this.ActiveProjectile;
	}
	
	/**
	 * set the active projectile of this world to the given projectile
	 * @param 	projectile
	 * 			the projectile to be set as active projectile
	 * @post	| new.getActiveProjectile() == projectile
	 */
	protected void setActiveProjectile(Projectile projectile){
		this.ActiveProjectile = projectile;
	}
	
	//--------------- active Worm ----------------------
	/**
	 * The currently active worm in this world
	 */
	private Worm activeWorm;
	/**
	 * Returns the active worm in the given world (i.e., the worm whose turn it is).
	 * WARNING: this gives you the opportunity to modify the worm.
	 */
	public Worm getActiveWorm() {
		int index = getActiveWormIndex();
		if (index < 0){
			return null;
		}
		return this.getAllWorms().get(index);
	}

	private void setActiveWorm(Worm worm){
		this.activeWorm = worm;
	}
	/**
	 * returns the index of the active worm in the list of worms of this world
	 */
	private int getActiveWormIndex() {
		return this.getAllWormsProtected().indexOf(this.activeWorm);
	}
	
	//------------------ Positions (e.g. isImpassable, isAdjacent, getFreePosition) ------------------------
	/**
	 * Checks if the object with given x, y and radius stands on an impassable position
	 * 
	 * @param 	x
	 * 			the x-coordinate to be checked (center point, in meters) (double)
	 * @param 	y
	 * 			the y-coordinate to be checked (center point, in meters) (double)
	 * @param 	radius
	 * 			the radius to be checked (double)
	 * @return	true if any pixel inside the radius of the object is impassable
	 * 			| if ( (isFulllyInworld(x,y,radius) && ! ! isPassablePixel( xInPixels, yInPixels)
	 * 			|	|| ( (pythagorianDistance(checkPositionInMeters, oldPositionInMeters) <= radius) && (! isPassablePixel(checkPosition[0], checkPosition[1])) )
	 * 			|	|| ( isCenterInWorld(x, y, xInPixels, yInPixels) ) && (!isPassablePixel(xInPixels, yInPixels))
	 * 			|		true
	 * @return 	| else
	 * 			|	false
	 */
	public boolean isImpassablePosition(double x, double y, double radius){
		int xInPixels =  (int)((x )/this.pixelwidth);
		int yInPixels =  (int)((y )/this.pixelheight);
		double[] oldPositionInMeters= {x, y}; // used to calculate the distance
		int radiusInPixels = (int) (radius/ this.pixelwidth - this.pixelwidth/10.0 );// );  //convert radius to pixels, -pixelWidth/10.0 because we want the edge not to touch the next position. (barely touching impassable positions is not a problem)
	
		if (isFullyInWorld(x, y, radius)) {

			if (! isPassablePixel( xInPixels, yInPixels)){
//				System.out.println("the center is impassable, so impassable");
				return true;
			}
			
			int step= 1*testScalar;  // pixels per step
			for (int i= -radiusInPixels; i<= radiusInPixels -step +1; i+= step) {// go from left to right   
				for (int j = -radiusInPixels; j<= radiusInPixels -step +1; j+= step) { //go from bottom to up
					int[] checkPosition= {Math.abs(xInPixels +i), Math.abs(yInPixels +j)};
					double[] checkPositionInMeters= {x + i*pixelwidth, y + j*pixelheight};
					if ( (pythagorianDistance(checkPositionInMeters, oldPositionInMeters) <= radius) && (! isPassablePixel(checkPosition[0], checkPosition[1])) )  {// if in the circle and impassable; i INVERETED the order
//						System.out.println("impassable found in radius, so impassable");
						return true;
					}
				}
			}
//			System.out.println("No impassable found in radius, so passable");
			return false;
		}
		if ( isCenterInWorld(x, y, xInPixels, yInPixels) ){ //is the center in the world?
					
			if (!isPassablePixel(xInPixels, yInPixels)) {
//				System.out.println("center in world, and impassable");
				return true;
			}
			else {
//				System.out.println("center in world, and passable");
				return false;
			}
		}
//		System.out.println("center not in world, passable");
		return false;
	}
	
	/**
	 * Checks if the center of the object (given in isImpassablePosition()) is in the world.
	 * @param 	x
	 * 			the x-coordinate of the object to be checked
	 * @param 	y
	 * 			the y-coordinate of the object to be checked
	 * @param 	xInPixels
	 * 			the x-position of the impassable-positions map
	 * @param 	yInPixels
	 * 			the y-position of the impassable-positions map
	 * @return	| x>=0 && y>=0 && x< this.getWidth() && y< this.getHeight()  
				|  &&  xInPixels>=0 && yInPixels>=0 && xInPixels< getImpassablePositions()[0].length 
				|  && yInPixels<= this.getImpassablePositions().length
	 */
	private boolean isCenterInWorld(double x, double y, int xInPixels,
			int yInPixels) {
		return x>=0 && y>=0 && x< this.getWidth() && y< this.getHeight()  
				&&  xInPixels>=0 && yInPixels>=0 && xInPixels< getImpassablePositions()[0].length 
				&& yInPixels<= this.getImpassablePositions().length;
	}

	/**
	 * This method returns the cartesian distance between 2 points
	 * @param 	firstPoint
	 * 			the first point to be checked (x on 0 an y in 1)
	 * @param 	secondPoint
	 * 			the second point to be checked (x on 0 an y in 1)
	 * @return  | Math.sqrt(Math.pow(firstPoint[0] - secondPoint[0], 2) + Math.pow(firstPoint[1] - secondPoint[1], 2))
	 */
	protected static double pythagorianDistance(double[] firstPoint, double[] secondPoint) {
		double result = Math.sqrt(Math.pow(firstPoint[0] - secondPoint[0], 2) + Math.pow(firstPoint[1] - secondPoint[1], 2));
		result= (double)Math.round(result * 100000) / 100000.0;
		return result;
	}
	
	/**  TOTALLY
	 * This method returns True if the position (x,y) is a passable position.
	 * @return  | this.getImpassablePositions()[this.getImpassablePositions().length- yInPixels -1][xInPixels ]
	 */
	private boolean isPassablePixel(int xInPixels, int yInPixels) {  
		return this.getImpassablePositions()[this.getImpassablePositions().length- yInPixels -1][xInPixels ];  
		// y are the rows, x are the columns, so we had to INVERSE this !!!!!
		// because we count from bottom to up, and the matrix goes from up to down, we have to invert the y-axis.
		// The -1 is necessary if we want the first position down left to be (0,0)
	}
	
	/**
	 * This method returns true if there is an impassable pixel within the ring (radius, 1.1*radius)
	 * 						and if there are no impassable pixels inside the radius
	 * @param 	x
	 * 			the x position of the object to be checked (center point, in meters) (double)
	 * @param 	y
	 * 			the y position of the object to be checked (center point, in meters) (double)
	 * @param	radius
	 * 			the radius of the object to be checked (double)
	 * @return 	true if there is an impassable pixel within the ring (radius, 1.1*radius)
	 *			| if ( for_one (position i): (radius<=(pythagoriandistance(i,this.getPosition())<= 1.1*radius && this.isImpassablePosition(i) )
	 *			| 	true
	 * @return 	| else
	 * 			|	false
	 */
	public boolean isAdjacentPosition(double x, double y, double radius) {
		if (!isFullyInWorld(x, y, radius)) {
//			System.out.println("not fully in world, not adjacent");
			return false;
		}
		
		if ( (this.isImpassablePosition(x, y, radius)) ) {
//			System.out.println("impassable in radius found, not adjacent");
			return false;
		}
		
		double largeRadius= radius * 1.1;
		// look right, up, left and down.
		for (double angle = 0; angle < 2*Math.PI; angle+= Math.PI/2.0) { 
			for (double delta=radius; delta<= largeRadius; delta+= this.getPixelheight()*(testScalar/20.0)) {
				
				double xAdd = delta* Math.cos(angle); 
				double yAdd = delta* Math.sin(angle);
				if (isImpassablePosition(x+ xAdd, y+ yAdd, radius) ) {
//					System.out.println("impassable in ring found, adjacent");
					return true;
				}
			}
		}
		
		// look at other angles too. (for worms larger than 1 pixel)
		double angle_incrementer = Math.atan(this.getPixelheight()/largeRadius)*testScalar;
		for (double angle = 0; angle < 2*Math.PI; angle+= angle_incrementer) { 
			for (double delta=radius; delta<= largeRadius; delta+= this.getPixelheight()*(testScalar/20.0)) {
				
				double xAdd = delta* Math.cos(angle); 
				double yAdd = delta* Math.sin(angle);
				if (isImpassablePosition(x+ xAdd, y+ yAdd, radius) ) {
//					System.out.println("impassable in ring found, adjacent");
					return true;
				}
			}
		}
//		System.out.println("no impassable in ring found, not adjacent");
		return false;
	}

	/**
	 * This method returns the x and y position of a random free location in the world where a new object can be placed.
	 * @return	| if this.isAdjacentPosition(freePos[0], freePos[1], radius)
	 * 			|	this.freepos
	 * @throws	IllegalArgumentException
	 * 			| if nbTries > 100
	 */
	private double[] getFreePosition(double radius) throws IllegalArgumentException {
		int nbTries= 0;
		int nbTriesLimit= 1000;
		double[] freePos= new double[2];
		freePos[0] = (radius+1) + (this.width - 2*radius)* Math.random();
		freePos[1] = ((radius+1) + (this.height - 2*radius)* Math.random());
		
		// if not fully in the world, or if not adjacent, or if not passable for this object, 
		//		then generate a new position
		while ( (!isFullyInWorld(freePos[0], freePos[1], radius)) || (!this.isAdjacentPosition(freePos[0], freePos[1], radius)) ) {  
			if (nbTries> nbTriesLimit){
				throw new IllegalArgumentException("no adjacent position found");
			}
			freePos[0] = (radius+1) + (this.width - 2*radius)* Math.random(); 
			freePos[1] = ((radius+1) + (this.height - 2*radius)* Math.random());
			nbTries++;
		}
		return freePos;
	}
	
	/**
	 * Checks if the given object is fully in this world
	 * @param 	x
	 * 			the x-coordinate of the object to be checked (double)
	 * @param 	y
	 * 			the y-coordinate of the object to be checked (double)
	 * @param 	radius
	 * 			the radius of the object to be checked (double)
	 * @return	| (x >= radius) && (y >= radius) 
	 * 			|	&& (x <= this.getWidth() - radius )	&& (y <= this.getHeight() - radius );
	 */
	protected boolean isFullyInWorld(double x, double y, double radius){
		boolean result= (x >= radius) 
					&& (y >= radius) 
					&& (x <= this.getWidth() - radius ) 
					&& (y <= this.getHeight() - radius );
		return result;
	}

	
	//------------------------ FOOD ------------------------
	/**
	 * Create a new food ration that is positioned at the given location in the given world.
	 * 
	 * @param 	world
	 * 			The world in which to place the created food ration
	 * @param 	x
	 * 			The x-coordinate of the position of the new food ration (in meter)
	 * @param 	y
	 * 			The y-coordinate of the position of the new food ration (in meter)
	 * @effect  | new.foodList.contains(food)
	 * @return	| food
	 * @throws 	IllegalPositionException
	 * 			| if (! isValidXposition(x) || ! isValidYposition(y) )
	 */
	Food createFood(double x, double y) throws IllegalPositionException {  
		Food food = new Food(this, x, y);
		this.foodList.add(food);
		return food;
	}
	
	/**
	 * Create and add a new food ration to the given world.
	 * The food must be placed at a random adjacent location.
	 * @effect  | new.foodList.contains(food)
	 */
	public void addNewFood() {
		double[] freePosition= this.getFreePosition(0.2);
		this.createFood(freePosition[0], freePosition[1]);
	}
	
	/**
	 * Returns a copy of the list of all food objects in this World
	 */
	public LinkedList<Food> getAllFood(){
		return (LinkedList<Food>) this.foodList.clone(); //warning because of clone()
	}
	
	/**
	 * Returns the list of all food objects in this World
	 */
	protected LinkedList<Food> getAllFoodProtected(){
		return this.foodList;
	}
	
	/**
	 * This method removes a Food object from this world
	 * @param food
	 * @effect if the given Food object exists in this world, remove it.
	 * 			| if (this.getAllFood().indexOf(food)>= 0
	 * 			|  then this.wormList.remove(food);
	 * @throws	IllegalArgumentException
	 * 			| if !this.getFoodList.contains(food)
	 */
	public void removeFood(Food food) {
		int index= this.getAllFood().indexOf(food);
		if (index >=0 ) {
			this.foodList.remove(index);
		}
		else {
			throw new IllegalArgumentException("no such food in this world");
		}
	}
	
	/**
	 *  A list that stores food.
	 */
	private LinkedList<Food> foodList = new LinkedList<Food>();
		
		
	// ---------------------------------- WORM  ---------------------------------------------	
	/**
	 * Create a new worm that is positioned at the given location in the given world,
	 * looks in the given direction, has the given radius and the given name.
	 * 
	 * @param 	x
	 * 			The x-coordinate of the position of the new worm (in meter)
	 * @param 	y
	 * 			The y-coordinate of the position of the new worm (in meter)
	 * @param 	direction
	 * 			The direction of the new worm (in radians)
	 * @param 	radius 
	 * 			The radius of the new worm (in meter)
	 * @param 	name
	 * 			The name of the new worm
	 * @effect	| new.getWormList.contains(worm)
	 */
	public Worm createWorm(double x, double y, double direction, double radius, String name, Program program) {
		Worm worm;
		if (program == null){
			worm = new Worm(this, x, y, direction, radius, name);
		}
		else {
			worm = new Worm(this, x, y, direction, radius, name, program);
		}
		this.setActiveWorm(worm);// if a worm is created, set the active worm temporarily to that worm to have a value for the begin worm.
		return worm;
	}
	
	/**
	 * Add a worm to this world
	 * @param 	worm
	 * 			the worm to be added to this world
	 * @effect	new.getWormList.contains(worm)
	 */
	public void addWorm(Worm worm) {
		this.wormList.add(worm);
	}
		
	/**
	 * Create and add a new worm to the given world.
	 *	@effect	| new.getWormList.contains(worm)
	 *	@throws	IllegalArgumentException
	 *			| if this.getWidth() < 0.6 || this.getHeight() < 0.6
	 */
	void addNewWorm(Program program)  throws IllegalArgumentException{
		double radius = 0.6;
		double[] freePosition= this.getFreePosition(radius);
		Worm worm = this.createWorm(freePosition[0], freePosition[1], 0, radius,"RandomWorm"+ this.getAllWorms().size(),program);  //the name is completely arbitrary /
		if (this.getAllTeams().size() > 0) {
			worm.addToTeam(this.getAllTeams().get(this.getAllTeams().size()-1));
		}
	}
	
	/**
	 * Returns a copy of the list of all worm objects in this World
	 */
	public LinkedList<Worm> getAllWorms(){
		return (LinkedList<Worm>) this.wormList.clone(); //warning because of clone()
	}
	
	/**
	 * Returns the list of all worm objects in this World
	 */
	protected LinkedList<Worm> getAllWormsProtected(){
		return this.wormList;
	}
	
	/**
	 * This method removes a Worm object from this world
	 * @param 	worm
	 * 			the worm to be removed from this world
	 * @effect 	if the Worm object exists in this world, remove it.
	 * 			| if (this.getAllWorms().indexOf(worm)>= 0
	 * 			|  then this.wormList.remove(worm);
	 * @throws 	IllegalArgumentException
	 * 			| if ! this.getAllWorms.contains(worm)
	 */
	public void removeWorm(Worm worm) throws IllegalArgumentException {
		int index= this.getAllWormsProtected().indexOf(worm);
		if (index >=0 ) {
			this.wormList.remove(index);
		}
		else {
			throw new IllegalArgumentException("no such worm in this world");
		}
	}
	
	/**
	 *  A list that stores the worms of this world
	 */
	private LinkedList<Worm> wormList = new LinkedList<>(); 	
	
	//--------------------------------- TEAMS  -------------------------------------------
	/**
	 * This method adds an empty team to this world. The team has the given name.
	 * @param 	newName
	 * 			The new name of the team
	 * @effect	(this.getAllTeams()).contains(newTeam) && newTeam.getName() == newName
	 * @throws 	IllegalArgumentException
	 * 			| this.getAllTeams.size() > 10
	 */
	public Team addEmptyTeam(String newName) throws IllegalArgumentException, IllegalNameException {
		if (this.getAllTeams().size() > 10){
			throw new IllegalArgumentException("Too many teams!");
		}
		Team team = new Team(newName);
		this.teamList.add(team);
		return team;
	}
	
	/**
	 * This method removes a Team object from this world
	 * @param team
	 * @effect if the Team object exists in this world, remove it.
	 * 			| if (this.getAllteams().indexOf(team)>= 0
	 * 			|  then this.teamList.remove(team);
	 * @throws	IllegalArgumentException
	 * 			| if ! this.getAllTeams.contains(team)
	 */
	private void removeTeam(Team team) {
		int index= this.getAllTeams().indexOf(team);
		if (index >=0 ) {
			this.teamList.remove(index);
		}
		else {
			throw new IllegalArgumentException("no such team in this world");
		}
	}
	
	/**
	 * Returns a copy of the list of all team objects in this World
	 */
	public LinkedList<Team> getAllTeams(){
		return (LinkedList<Team>) this.teamList.clone();  //warning because of clone()
	}
	
	/**
	 * Returns the list of all team objects in this World
	 * @return
	 */
	protected  LinkedList<Team> getAllTeamsProtected(){
		return this.teamList;
	}
	
	/** 
	 * A list that stores the currently existing teams of this World
	 */
	private LinkedList<Team> teamList = new LinkedList<>();
	

	
	//------------------------------  GET THE OBJECTS  --------------------------------------
	/**
	 * This method returns all objects of this world of the given type.
	 * @param 	type
	 * 			the type of the objects to be returned (String)
	 * @return	| if type == food
	 * 			|	this.getAllFood
	 * @return	| if type == worm
	 * 			|	this.getAllWorms
	 * @return	| if type == projectile
	 * 			|	this.getActiveProjectile
	 */
	public LinkedList<GameObject> getAllWorldObjects(String type) {  
		LinkedList<GameObject> allWorldObjects = new LinkedList<>(); 
		if ( (type.toLowerCase()).contains("food") ) {  
			for (Food food: this.getAllFood())
				allWorldObjects.add(food);
		}
		if ( (type.toLowerCase()).contains("worm") ) {
			for (Worm worm: this.getAllWorms())
				allWorldObjects.add(worm);
		}
		if ( (type.toLowerCase()).contains("projectile") ) {
			allWorldObjects.add(this.getActiveProjectile());
		}
		return allWorldObjects;
	}
	
	/**
	 * Returns all the world objects
	 * @return this.getAllFood+this.getAllWorms+this.getActiveProjectile
	 */
	public LinkedList<GameObject> getAllWorldObjects() {
		LinkedList<GameObject> allWorldObjects= new LinkedList<>();
		allWorldObjects.addAll(this.getAllWorms() );
		allWorldObjects.addAll(this.getAllFood() );
		allWorldObjects.add(this.getActiveProjectile() );
		return allWorldObjects;
	}
	
	// ----------------------- Overlapped items ---------------------------------------------
	/**
	 * This method checks if a game object overlaps with a given list of game objects
	 * @param 	objectToCheck
	 * 			the game object that could overlap with the given list
	 * @param 	ListOfObjectsToCheck
	 * 			the given list of objects that could overlap with the given object
	 * @return	| for item: listOfObjectsToCheck:
	 * 			|	if pythagorianDistance(objectToCheckPos, itemPos) <= (objectToCheckRad + itemRad))
	 * 			|		true
	 * @return	| else
	 * 			|	false
	 */
		public GameObject overlapsWith(double[] XYPos, double radius, LinkedList<GameObject> ListOfObjectsToCheck){
		double[] base = XYPos;
		double baseR = radius;
		double[] against = {-1,-1}; // arbitrary
		double againstR = -1;		// arbitrary
		for (GameObject object: ListOfObjectsToCheck){ //check all objects in the given list
			against[0] = object.getXPosition();
			against[1] = object.getYPosition();
			againstR = object.getRadius();
			if (World.pythagorianDistance(base, against) <= (baseR + againstR)){ // if they overlap
				return object;
			}
		}
		return null;
	}
	
}
