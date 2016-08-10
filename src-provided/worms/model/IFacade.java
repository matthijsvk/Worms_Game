package worms.model;

import java.util.Collection;
import java.util.Random;

import worms.gui.game.IActionHandler;
import worms.model.programs.ParseOutcome;

/**
 * Implement this interface to connect your code to the user interface.
 * 
 * <ul>
 * <li>For separating the code that you wrote from the code that was provided to you,
 * put <b>your code in the <code>src</code> folder.</b>
 * The code that is provided to you stays in the <code>src-provided</code> folder.
 * If you modify the provided code, it's advised to move the modified code to the <code>src</code> folder as well,
 * so that your changes are not accidentally overwritten by an update.
 * </li>
 * 
 * <li>You should at least add the following classes to the package <code>worms.model</code>:
 * <ul>
 * <li>a class <code>World</code> for representing the world</li>
 * <li>a class <code>Worm</code> for representing a worm</li>
 * <li>a class <code>Food</code> for representing a food ration</li>
 * <li>a class <code>Projectile</code> for representing a projectile</li>
 * <li>a class <code>Program</code> for representing a program</li>
 * <li>a class <code>Facade</code> that implements this interface (<code>IFacade</code>).</li>
 * </ul>
 * 
 * <li>
 *  The header of that Facade class should look as follows:<br>
 * <code>class Facade implements IFacade { ... }</code><br>
 *  Consult the <a href=
 * "http://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html">
 * Java tutorial</a> for more information on interfaces, if necessary.
 * </li>
 * 
 * <li>Each method defined in the interface <code>IFacade</code> must be
 * implemented by the class <code>Facade</code>. For example, the implementation
 * of <code>getX</code> should call a method of the given <code>worm</code> to
 * retrieve its x-coordinate.</li>
 * 
 * <li>Methods in this interface are allowed to throw only
 * <code>ModelException</code>. No other exception types are allowed. This
 * exception can be thrown only if calling a method of your <code>Worm</code>
 * class with the given parameters would violate a precondition or if the method
 * of your <code>Worm</code> class throws an exception (if so, wrap the
 * exception in a <code>ModelException</code>). <b>ModelException should not be
 * used anywhere outside of your Facade implementation.</b></li>
 * 
 * <li>The rules described above and the documentation described below for each
 * method apply only to the class implementing IFacade. Your class for
 * representing worms should follow the rules described in the assignment.</li>
 * 
 * <li>Do not modify the signatures of the methods defined in this interface.
 * You can however add additional methods, as long as these additional methods
 * do not overload the existing ones. Each additional method must be implemented
 * in your class <code>Facade</code>.</li>
 * 
 * <li>Your <code>Facade</code> class should offer a default constructor.</li>
 * </ul>
 */
public interface IFacade {

	/**
	 * Create and add an empty team with the given name to the given world.
	 * 
	 * (For single-student groups that do not implement teams, this method should have no effect)
	 */
	void addEmptyTeam(World world, String newName);

	/**
	 * Create and add a new food ration to the given world.
	 * The food must be placed at a random adjacent location.
	 * 
	 * (For single-student groups that do not implement food, this method should have no effect)
	 */
	void addNewFood(World world);

	/**
	 * Create and add a new worm to the given world.
	 * The new worm must be placed at a random adjacent location.
	 * The new worm can have an arbitrary (but valid) radius and direction.
	 * The new worm may (but isn't required to) have joined a team.
	 * The worm must behave according to the provided program, or is controlled by the player if the given program is null.
	 */
	void addNewWorm(World world, Program program);

	/**
	 * Returns whether or not the given worm can fall down
	 */
	boolean canFall(Worm worm);

	/**
	 * Returns whether or not the given worm is allowed to move.
	 */
	boolean canMove(Worm worm);

	/**
	 * Returns whether or not the given worm can turn by the given angle.
	 */
	boolean canTurn(Worm worm, double angle);

	/**
	 * Create a new food ration that is positioned at the given location in the given world.
	 * 
	 * @param world
	 * The world in which to place the created food ration
	 * @param x
	 * The x-coordinate of the position of the new food ration (in meter)
	 * @param y
	 * The y-coordinate of the position of the new food ration (in meter)
	 * 
	 * (For single-student groups that do not implement food, this method should have no effect)
	 */
	Food createFood(World world, double x, double y);

	/**
	 * Creates a new world.
	 * 
	 * @param width The width of the world (in meter)
	 * 
	 * @param height The height of the world (in meter)
	 * 
	 * @param passableMap A rectangular matrix indicating which parts of the terrain are passable and impassable.
	 *  This matrix is derived from the transparency of the pixels in the image file of the terrain.
	 *  passableMap[r][c] is true if the location at row r and column c is passable, and false if that location is impassable.
	 *  The elements in the first row (row 0) represent the pixels at the top of the terrain (i.e., largest y-coordinates).
	 *  The elements in the last row (row passableMap.length-1) represent pixels at the bottom of the terrain (smallest y-coordinates).
	 *  The elements in the first column (column 0) represent the pixels at the left of the terrain (i.e., smallest x-coordinates).
	 *  The elements in the last column (column passableMap[0].length-1) represent the pixels at the right of the terrain (i.e., largest x-coordinates).     
	 * 
	 * @param random A random number generator, seeded with the value obtained from the command line or from GUIOptions,
	 *  that can be used to randomize aspects of the world in a repeatable way.
	 * 
	 * @return The world.
	 */
	public World createWorld(double width, double height,
			boolean[][] passableMap, Random random);

	/**
	 * Create a new worm that is positioned at the given location in the given world,
	 * looks in the given direction, has the given radius and the given name.
	 * 
	 * @param world
	 * The world in which to place the created worm  
	 * @param x
	 * The x-coordinate of the position of the new worm (in meter)
	 * @param y
	 * The y-coordinate of the position of the new worm (in meter)
	 * @param direction
	 * The direction of the new worm (in radians)
	 * @param radius 
	 * The radius of the new worm (in meter)
	 * @param name
	 * The name of the new worm
	 * @param program
	 * The program that defines this worm's behavior, or null if this worm is controlled by the player
	 */
	Worm createWorm(World world, double x, double y, double direction,
			double radius, String name, Program program);

	/**
	 * Makes the given worm fall down until it rests on impassable terrain again.
	 */
	void fall(Worm worm);

	/**
	 * Returns the current number of action points of the given worm.
	 */
	int getActionPoints(Worm worm);

	/**
	 * Returns the active projectile in the world, or null if no active projectile exists.
	 */
	public Projectile getActiveProjectile(World world);

	/**
	 * Returns the active worm in the given world (i.e., the worm whose turn it is).
	 */
	Worm getCurrentWorm(World world);

	/**
	 * Returns all the food rations in the world
	 * 
	 * (For single-student groups that do not implement food, this method must always return an empty collection)
	 */
	Collection<Food> getFood(World world);

	/**
	 * Returns the current number of hit points of the given worm.
	 */
	int getHitPoints(Worm worm);

	/**
	 * Returns the location on the jump trajectory of the given projectile after a
	 * time t.
	 * 
	 * @return An array with two elements, with the first element being the
	 *         x-coordinate and the second element the y-coordinate
	 */
	double[] getJumpStep(Projectile projectile, double t);

	/**
	 * Returns the location on the jump trajectory of the given worm after a
	 * time t.
	 * 
	 * @return An array with two elements, with the first element being the
	 *         x-coordinate and the second element the y-coordinate
	 */
	double[] getJumpStep(Worm worm, double t);

	/**
	 * Determine the time that the given projectile can jump until it hits the terrain, hits a worm, or leaves the world.
	 * The time should be determined using the given elementary time interval.
	 * 
	 * @param projectile The projectile for which to calculate the jump time.
	 * 
	 * @param timeStep An elementary time interval during which you may assume
	 *                 that the projectile will not completely move through a piece of impassable terrain.
	 *                 
	 * @return The time duration of the projectile's jump.
	 */
	double getJumpTime(Projectile projectile, double timeStep);

	/**
	 * Determine the time that the given worm can jump until it hits the terrain or leaves the world.
	 * The time should be determined using the given elementary time interval.
	 * 
	 * @param worm The worm for which to calculate the jump time.
	 * 
	 * @param timeStep An elementary time interval during which you may assume
	 *                 that the worm will not completely move through a piece of impassable terrain.
	 *                 
	 * @return The time duration of the worm's jump.
	 */
	double getJumpTime(Worm worm, double timeStep);

	/**
	 * Returns the mass of the given worm.
	 */
	double getMass(Worm worm);

	/**
	 * Returns the maximum number of action points of the given worm.
	 */
	int getMaxActionPoints(Worm worm);

	/**
	 * Returns the maximum number of hit points of the given worm.
	 */
	int getMaxHitPoints(Worm worm);

	/**
	 * Returns the minimal radius of the given worm.
	 */
	double getMinimalRadius(Worm worm);

	/**
	 * Returns the name the given worm.
	 */
	String getName(Worm worm);

	/**
	 * Returns the current orientation of the given worm (in radians).
	 */
	double getOrientation(Worm worm);

	/**
	 * Returns the radius of the given food ration
	 * 
	 * (For single-student groups that do not implement food, this method may return any value)
	 */
	double getRadius(Food food);

	/**
	 * Returns the radius of the given projectile.
	 */
	public double getRadius(Projectile projectile);

	/**
	 * Returns the radius of the given worm.
	 */
	double getRadius(Worm worm);

	
	/**
	 * Returns the name of the weapon that is currently active for the given worm,
	 * or null if no weapon is active.
	 */
	String getSelectedWeapon(Worm worm);

	/**
	 * Returns the name of the team of the given worm, or returns null if this
	 * worm is not part of a team.
	 * 
	 * (For single-student groups that do not implement teams, this method should always return null)
	 */
	String getTeamName(Worm worm);

	/**
	 * Returns the name of a single worm if that worm is the winner, or the name
	 * of a team if that team is the winner. This method should null if there is no winner.
	 * 
	 * (For single-student groups that do not implement teams, this method should always return the name of the winning worm, or null if there is no winner)
	 */
	String getWinner(World world);

	/**
	 * Returns all the worms in the given world
	 */
	Collection<Worm> getWorms(World world);

	/**
	 * Returns the x-coordinate of the given food ration
	 * 
	 * (For single-student groups that do not implement food, this method may return any value)
	 */
	double getX(Food food);

	/**
	 * Returns the x-coordinate of the given projectile.
	 */
	public double getX(Projectile projectile);

	/**
	 * Returns the x-coordinate of the current location of the given worm.
	 */
	double getX(Worm worm);

	/**
	 * Returns the y-coordinate of the given food ration
	 * 
	 * (For single-student groups that do not implement food, this method may return any value)
	 */
	double getY(Food food);

	/**
	 * Returns the y-coordinate of the given projectile.
	 */
	public double getY(Projectile projectile);

	/**
	 * Returns the y-coordinate of the current location of the given worm.
	 */
	double getY(Worm worm);

	/**
	 * Returns whether or not the given food ration is alive (active), i.e., not eaten.
	 * 
	 * (For single-student groups that do not implement food, this method should always return false)
	 */
	boolean isActive(Food food);
	
	/**
	 * Returns whether the given projectile is still alive (active).
	 */
	boolean isActive(Projectile projectile);

	/**
	 * Checks whether the given circular region of the given world,
	 * defined by the given center coordinates and radius,
	 * is passable and adjacent to impassable terrain. 
	 * 
	 * @param world The world in which to check adjacency
	 * @param x The x-coordinate of the center of the circle to check  
	 * @param y The y-coordinate of the center of the circle to check
	 * @param radius The radius of the circle to check
	 * 
	 * @return True if the given region is passable and adjacent to impassable terrain, false otherwise.
	 */
	boolean isAdjacent(World world, double x, double y, double radius);

	/**
	 * Returns whether the given worm is alive
	 */
	boolean isAlive(Worm worm);

	/**
	 * Returns whether the game in the given world has finished.
	 */
	boolean isGameFinished(World world);

	/**
	 * Checks whether the given circular region of the given world,
	 * defined by the given center coordinates and radius,
	 * is impassable. 
	 * 
	 * @param world The world in which to check impassability 
	 * @param x The x-coordinate of the center of the circle to check  
	 * @param y The y-coordinate of the center of the circle to check
	 * @param radius The radius of the circle to check
	 * 
	 * @return True if the given region is impassable, false otherwise.
	 */
	boolean isImpassable(World world, double x, double y, double radius);

	/**
	 * Make the given projectile jump to its new location.
	 * The new location should be determined using the given elementary time interval. 
	 *  
	 * @param projectile The projectile that needs to jump
	 * 
	 * @param timeStep An elementary time interval during which you may assume
	 *                 that the projectile will not completely move through a piece of impassable terrain.
	 */
	void jump(Projectile projectile, double timeStep);

	/**
	 * Make the given worm jump to its new location.
	 * The new location should be determined using the given elementary time interval. 
	 *  
	 * @param worm The worm that needs to jump
	 * 
	 * @param timeStep An elementary time interval during which you may assume
	 *                 that the worm will not completely move through a piece of impassable terrain.
	 */
	void jump(Worm worm, double timeStep);

	/**
	 * Moves the given worm according to the rules in the assignment.
	 */
	void move(Worm worm);

	/**
	 * Renames the given worm.
	 */
	void rename(Worm worm, String newName);

	/**
	 * Activates the next weapon for the given worm
	 */
	void selectNextWeapon(Worm worm);

	/**
	 * Sets the radius of the given worm to the given value.
	 */
	void setRadius(Worm worm, double newRadius);

	/**
	 * Makes the given worm shoot its active weapon with the given propulsion yield.
	 */
	void shoot(Worm worm, int yield);

	/**
	 * Starts a game in the given world.
	 */
	void startGame(World world);

	/**
	 * Starts the next turn in the given world
	 */
	void startNextTurn(World world);

	/**
	 * Turns the given worm by the given angle.
	 */
	void turn(Worm worm, double angle);
	
	/**
	 * Try to parse the given program.
	 * You can use an instance of the worms.model.programs.ProgramParser.
	 * 
	 * When the program is executed, the execution of an action statement
	 * must call the corresponding method of the given action handler.
	 * This executes the action as if a human player has initiated it, and
	 * will eventually call the corresponding method on the facade. 
	 * 
	 * @param programText The program text to parse
	 * @param handler The action handler on which to execute commands
	 * 
	 * @return the outcome of parsing the program, which can be a success or a failure.
	 * You can create a ParseOutcome object by means of its two static methods, success and failure. 
	 */
	ParseOutcome<?> parseProgram(String programText, IActionHandler handler);
	
	/**
	 * Returns whether or not the given worm is controlled by a program. 
	 * 
	 * @return true if the given worm is controlled by a program, false otherwise
	 */
	public boolean hasProgram(Worm worm);
	
	/**
	 * Returns whether or not the given program is well-formed.
	 * A program is well-formed if a for-each statement does not (directly or
	 * indirectly) contain one or more action statements.
	 * 
	 * @param program The program to check
	 * 
	 * @return true if the program is well-formed; false otherwise 
	 */
	public boolean isWellFormed(Program program);
}
