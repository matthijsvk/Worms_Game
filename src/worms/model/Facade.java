package worms.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Random;

import worms.gui.game.IActionHandler;
import worms.model.exceptions.IllegalActionPointException;
import worms.model.exceptions.IllegalNameException;
import worms.model.exceptions.IllegalPositionException;
import worms.model.exceptions.IllegalRadiusException;
import worms.model.ModelException;
import worms.model.part3.Expression;
import worms.model.part3.ProgramFactoryImplementation;
import worms.model.part3.Statement;
import worms.model.part3.Type;
import worms.model.programs.ParseOutcome;
import worms.model.programs.ProgramParser;

/**
* This Facade implements the IFacade
* and thus connects Worms to the GUI and all
*/
public class Facade implements IFacade{

	/**
	 * 			ONLY MODELEXCEPTIONS ARE ALLOWED
	 * 				> catchers for the Illegal**Exceptions...
	 * 				( yes, this Illegal**Exceptions have to stay,
	 * 				  we are not allowed to use ModelException outside
	 * 				  this facade)
	 */
	
	// ###########################################################################################################################################################
	// ########## WORMS ##########################################################################################################################################
	// ###########################################################################################################################################################
	
	
	// Done!
	public boolean canMove(Worm worm) {
		return worm.isAbleToMove();
	}
	
	// Done!
	public void move(Worm worm) {
		try {
			worm.move();
		}
		catch (IllegalPositionException exc) {
			throw new ModelException("You can't move there!!");
		}
		catch (IllegalActionPointException exc){
			throw new ModelException("Not enough action points to move!");
		}
		catch (IllegalAccessException  | InvocationTargetException | NoSuchMethodException | SecurityException exc){
			exc.printStackTrace();
			throw new ModelException("Random exception");
		}
	}
	
	// Done!
	public boolean canTurn(Worm worm, double angle) {
		return worm.isAbleToTurn(angle);
	}
	
	// Done!
	public void turn(Worm worm, double angle) {
		try{
			worm.turn(angle);
		}
		catch (AssertionError ae){
			throw new ModelException("You violenced preconditions!!");
		}
	}
	
	// Done
	public void jump(Worm worm, double timeStep) throws ModelException {
		if (! worm.isAbleToJump())
			throw new ModelException("Not Able to jump");
		try{
			worm.jump(timeStep);
		}
		catch (IllegalPositionException exc) {
			throw new ModelException("Not Able to jump");
		}
	}
	
	// Done!
	public double getJumpTime(Worm worm, double timeStep) throws ModelException{
		try{
			return worm.getJumpTime(timeStep);
		}
		catch (IllegalPositionException exc) {
			throw new ModelException("Illegal Jump end position");
		}
	}
	
	// Done!
	public double[] getJumpStep(Worm worm, double t) throws ModelException {
		if (! worm.isAbleToJump())
			throw new ModelException("Not possible to jump");
		try{
			return worm.getJumpStep(t);
		}
		catch (IllegalPositionException exc) {
			throw new ModelException("Illegal Jump end position");
		}
	}
	
	// Done!
	public double getX(Worm worm) {
		return worm.getXPosition();
	}
	
	// Done!
	public double getY(Worm worm) {
		return worm.getYPosition();
	}
	
	// Done!
	public double getOrientation(Worm worm) {
		return worm.getDirection();
	}
	
	// Done!
	public double getRadius(Worm worm) {
		return worm.getRadius();
	}
	
	// Done!
	public void setRadius(Worm worm, double newRadius) throws ModelException
	{
		try {
			worm.setRadius(newRadius);
		}
		catch (IllegalRadiusException exc){
			throw new ModelException("Invalid radius");
		}
	}
	
	// Done!
	public double getMinimalRadius(Worm worm) {
		return Worm.getMinRadius();
	}
	
	// Done!
	public int getActionPoints(Worm worm) {
		return worm.getRemainingActionPoints();
	}
	
	// Done!
	public int getMaxActionPoints(Worm worm) {
		return worm.getMaxActionPoints();
	}
	
	// Done!
	public String getName(Worm worm) {
		return worm.getName().toString();
	}
	
	// Done!
	public void rename(Worm worm, String newName) throws ModelException
	{
		try {
			worm.setName(newName);
		}
		catch (IllegalNameException exc){
			throw new ModelException("Invalid name");
		}
	}
	
	// Done!
	public double getMass(Worm worm) {
		return worm.getMass();
	}
	
	// Done!
	public String getTeamName(Worm worm){
		if (worm.getTeam() == null)
			return "";
		else
			return worm.getTeam().getName();
	}
	
	//Done
	public int getHitPoints(Worm worm){
		return worm.getRemainingHitPoints();
	}
	
	//Done
	public int getMaxHitPoints(Worm worm){
		return worm.getMaxHitPoints();
	}
	
	public boolean canFall(Worm worm){
		return worm.isAbleToFall();
	}
	
	//Done
	public void fall(Worm worm){
		worm.fall();
	}
	
	//Done
	public void selectNextWeapon(Worm worm){
		worm.selectNextWeapon();
	}
	
	//Done
	public void shoot(Worm worm, int yield) {
		try {
			worm.shoot(yield);
		}
		catch (IllegalActionPointException exc) {
			throw new ModelException("not enough action point to shoot");
		}
	}
	
	//Done
	public String getSelectedWeapon(Worm worm){
		return worm.getCurrentWeapon().toString();
	}
	
	@Override
	public void addNewWorm(World world, Program program) {
		world.addNewWorm(program);
	}

	@Override
	public Worm createWorm(World world, double x, double y, double direction, double radius, String name, Program program) {
		if (program == null) {
			return this.createWorm(world, x, y, direction, radius, name);
		}
		else
			return world.createWorm(x, y, direction, radius, name, program);
	}
	
	@Override
	public boolean hasProgram(Worm worm) {
		return (worm.getProgram() != null);
	}
	
	// ###########################################################################################################################################################
	// ########## WORLD ##########################################################################################################################################
	// ###########################################################################################################################################################
	
	//Done
	public boolean isAdjacent(World world, double x, double y, double radius){
		return world.isAdjacentPosition(x, y, radius);
	}
	
	//Done
	public boolean isImpassable(World world, double x, double y, double radius){
		return world.isImpassablePosition(x, y, radius);
	}
	
	//Done
	public Projectile getActiveProjectile(World world){
		return world.getActiveProjectile();
	}
	
	//Done
	public void addEmptyTeam(World world, String newName){
		try{
			world.addEmptyTeam(newName);
		}
		catch(IllegalArgumentException exc){
			throw new ModelException("Too many teams!");
		}
		catch(IllegalNameException exc){
			throw new ModelException("Name is not valid.");
		}
	}
	
	//Done
	public void addNewFood(World world){
		world.addNewFood();
	}
	
	//Done
	public Food createFood(World world, double x, double y){
		return world.createFood(x, y);
	}
	
	public World createWorld(double width, double height, boolean[][] passableMap, Random random){
		return new World(width, height, passableMap, random);
	}
	
	//  Why do we have 2 createWorm methods ??
	public Worm createWorm(World world, double x, double y, double direction, double radius, String name) throws ModelException {
		try {
			if (direction < 0) { 
				direction = (direction + 2*Math.PI) % (2*Math.PI); //get in in the right place
			}
			return new Worm(world, x, y, direction, radius, name);
		}
		catch(IllegalPositionException exc) {
			if (exc.getType() == 'x')
				throw new ModelException("Illegal x-position");
			else if (exc.getType() == 'y')
				throw new ModelException("Illegal y-position");
			else
				throw new ModelException("something with the positions went really wrong");
		}
		catch(IllegalRadiusException exc) {
			throw new ModelException("This radius is illegal");
		}
		catch(IllegalNameException exc) {
			throw new ModelException("This name is illegal");
		}
	}
	
	//Done
	public boolean isAlive(Worm worm){
		return ! worm.isTerminated();
	}
	
	//Done
	public Worm getCurrentWorm(World world){
		return world.getActiveWorm();
	}
	
	
	//Done
	public boolean isGameFinished(World world){
		return world.isGameFinished();
	}
	
	//Done
	public void startGame(World world){
		world.startGame();
	}
	
	//Done
	public void startNextTurn(World world){
		world.startNextTurn();
	}
	
	//Done
	public String getWinner(World world){
		Team winningTeam = world.getWinningTeam();
		if (winningTeam != null)
			return winningTeam.getName();
		try{
			return world.getWinningWorm().getName();
		}
		catch (NullPointerException exc) {
			return "Bob & Matthijs";
		}
	}
	
	//Done
	public Collection<Worm> getWorms(World world){
		return world.getAllWorms();
	}
	
	// ###########################################################################################################################################################
	// ########## PROJECTILE #####################################################################################################################################
	// ###########################################################################################################################################################
	
	//Done
	public double getX(Projectile projectile){
		return projectile.getXPosition();
	}
	
	//Done
	public double getY(Projectile projectile){
		return projectile.getYPosition();
	}
	
	//Done
	public double getRadius(Projectile projectile){
		return projectile.getRadius();
	}
	
	//Done
	public boolean isActive(Projectile projectile){
		if (projectile != null && projectile.getWorld() != null)
			return projectile.getWorld().getActiveProjectile() != projectile;
		return false;
	}
	
	//Done
	public void jump(Projectile projectile, double timeStep){
		try{
			projectile.jump(timeStep);
		}
		catch (IllegalPositionException exc) {
			throw new ModelException("Not Able to shoot");
		}
	}
	
	//Done
	public double[] getJumpStep(Projectile projectile, double t){
		try{
			return projectile.getJumpStep(t);
		}
		catch (IllegalPositionException exc) {
			throw new ModelException("Not Able to shoot");
		}
	}
	
	//Done
	public double getJumpTime(Projectile projectile, double timeStep){
		try{
			return projectile.getJumpTime(timeStep);
		}
		catch (IllegalPositionException exc) {
			throw new ModelException("Not Able to shoot");
		}
	}
	
	
	// ###########################################################################################################################################################
	// ########## FOOD ###########################################################################################################################################
	// ###########################################################################################################################################################
	
	//Done
	public Collection<Food> getFood(World world){
		return world.getAllFood();
	}
	
	//Done
	public double getX(Food food){
		return food.getXPosition();
	}
	
	//Done
	public double getY(Food food){
		return food.getYPosition();
	}
	
	//Done
	public double getRadius(Food food){
		return food.getRadius();
	}
	
	//Done
	public boolean isActive(Food food){
		return ! food.isTerminated();
	}
	
	// ###########################################################################################################################################################
	// ########## PROGRAMS #######################################################################################################################################
	// ###########################################################################################################################################################
		
	//Done
	@Override
	public ParseOutcome<?> parseProgram(String programText, IActionHandler handler) {
		ProgramFactoryImplementation factory = new ProgramFactoryImplementation();
		ProgramParser<Expression<Type>, Statement, Type> parser = new ProgramParser<Expression<Type>, Statement, Type>(factory);
		factory.setParser(parser);
		try{
			parser.parse(programText);
		}
		catch(IllegalArgumentException e){
			throw new ModelException("Program has illegal Type in Assignment.");
		}
		if (parser.getErrors().size() > 0){
			return ParseOutcome.failure(parser.getErrors());
		}
		else{
			Program program = new Program(parser.getGlobals(),parser.getStatement(), handler);
			return ParseOutcome.success(program);
		}
	}

	//Done
	@Override
	public boolean isWellFormed(Program program) {
		return program.isValidProgram();
	}
	
	
}
