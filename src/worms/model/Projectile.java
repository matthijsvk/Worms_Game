package worms.model;

import java.util.*;

import worms.model.exceptions.IllegalPositionException;
import worms.model.exceptions.IllegalRadiusException;

/**
 * A class that implements a projectile of the game Worms
 * @date 21April2014
 * 
 * @author Matthijs Van keirsbilck, Bob Vanhoof
 * @version 1.4
 *
 */
public class Projectile extends Movable {
	
	/**
	 * This is the constructor for a projectile.
	 * 
	 * @param 	propulsion
	 * 			gives the rate (in %) of how hard this projecitle is fired.
	 * @param 	weapon
	 * 			the type of weapon of which this projectile is.
	 * @post 	| new.propulsion == propulsion
	 * @post 	| new.weapon == weapon
	 * @effect	| new.getRadius() == Math.pow( ((3.0*this.weapon.getMass())/(4.0*Math.PI*this.DENSITY)), (1/3) )
	 */
	public Projectile(World world, double xPosition, double yPosition, double direction, int propulsion, Guns weapon) 
			throws IllegalPositionException, IllegalRadiusException {
		super(world, xPosition, yPosition, direction, 0.01); 
		//radius is temporary set to 0.01. in the end of the constructor, it is set to the right value
		this.propulsion = propulsion;
		world.setActiveProjectile(this);
		this.weapon = weapon;
		this.setProjectileRadius(); //needs this.weapon, so set this last.
	}
	
	/**
	 * Returns the mass of this projectile
	 */
	public double getMass(){
		return this.weapon.getMass();
	}
	
	/**
	 * Variable that holds the weapon of which this projectile is
	 */
	private Guns weapon;
	
	/**
	 * Variable holds the propulsion yield of this projectile
	 */
	private int propulsion;
	
	/**
	 * Variable holds the density of all projectiles
	 */
	public static final double DENSITY = 7800;
	
	
	// --- jump ---
	/**
	 * Let this projectile fly in its parabolic-shaped direction (overridden)
	 * @effect	| if overlappedWorm != null	
	 * 			|	overlappedWorm.setRemainingActionPoints(overlappedWorm.getRemainingActionPoints - weapon.getHitPointsDamage())
	 */
	@Override
	public void jump(double timeStep){
		super.jump(timeStep);
		if (! this.isTerminated()){
			// check if a projectile hit a worm
			// first create list, so the general function would accept it's arguments
			LinkedList<GameObject> wormList = new LinkedList<>();
			for (Worm worm: this.getWorld().getAllWormsProtected()){
				wormList.add((GameObject) worm);
			}
			// return the worm that is hit and reduce its hitpoints
			double[] pos =  {this.getXPosition(), this.getYPosition()};
			Worm overlappedWorm = (Worm) this.getWorld().overlapsWith(pos, this.getRadius()*10, wormList); // *10 because radius of projectile is really small
			if (overlappedWorm != null){
				overlappedWorm.setRemainingHitPoints(overlappedWorm.getRemainingHitPoints() - weapon.getHitpointsDamage());
			}
		}
	}
	
	
	//----------------- Terminate -------------------------- 
	/**
	 * Terminates this projectile (overridden)
	 * @effect	| (new this.getWorld()).getActiveProjectile == null
	 * @effecct | new.getWorld == null
	 */
	@Override
	public void terminate(){
		super.terminate();
		this.getWorld().setActiveProjectile(null);
		this.setWorld(null);
	}
	
	
	//------------------- helper Methods---------------------
	/**
	 * Returns the force, of which this projectile is fired with (overridden)
	 * @return	| weapon.getForce(this.propulsion)
	 */
	@Override
	public double getForce() {
		return weapon.getForce(this.propulsion);
	}
	
	/**
	 * Sets the radius of this projectile to the radius, as specified in the assignment
	 * @post	| this.getRadius() == Math.pow( ((3.0*this.weapon.getMass())/(4.0*Math.PI*this.DENSITY)), (1/3) )
	 */
	public void setProjectileRadius() {
		double normalRadius = Math.pow( ((3.0 * this.weapon.getMass()) / (4.0 * Math.PI * Projectile.DENSITY)), (1.0/3) ); 
		super.setRadius(normalRadius);
	}
	
}
