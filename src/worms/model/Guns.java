package worms.model;
import be.kuleuven.cs.som.annotate.*;

@Value
public enum Guns {
	/**
	 * The Gun objects, as specified in the assignment
	 */
	Rifle(0.01,20,10),Bazooka(0.3,80,50);
	
	/**
	 * The constructor for the guns objects
	 * @param 	mass
	 * 			the mass of a bullet of this gun
	 * @param 	hitpointsDamage
	 * 			the damage of a bullet of this gun to a worm
	 * @param 	ActionPointsConsumption
	 * 			the consumption of firing a bullet of this gun for a worm
	 * @post	| this.getMass() == mass
	 * @post	| this.getHitpointsDamage == hitpointsDamage
	 * @post	| this.getActionpointsConsumption == ActionPointsConsumption
	 */
	@Raw
	private Guns(double mass, int hitpointsDamage, int ActionPointsConsumption){
		this.mass = mass;
		this.hitpointsDamage = hitpointsDamage;
		this.actionpointsConsumption = ActionPointsConsumption;	
	}
	
	
	// --- Mass ---
	/**
	 * Variable stores the mass of a bullet of this gun
	 */
	private double mass;
	
	/**
	 * Returns the mass of a bullet off this gun
	 */
	public double getMass(){
		return this.mass;
	}
	
	// --- HitpointsDamage ---
	/**
	 * Variable stores the hitpointsDamage of a projectile of this gun
	 */
	private int hitpointsDamage;
	
	/**
	 * Returns the Hitpointsdamage of a projectile of this gun
	 */
	public int getHitpointsDamage(){
		return this.hitpointsDamage;
	}
	
	
	// --- ActionPointsConsumption ---
	/**
	 * Variable stores the ActionPointsConsumption of this gun
	 */
	private int actionpointsConsumption;
	
	/**
	 * Returns the ActionPointsConsumption of firing this gun
	 */
	public int getActionPointsConsumption(){
		return this.actionpointsConsumption;
	}
	
	
	// --- Force ---
	/**
	 * Returns the force to a bullet, as fired with this gun
	 * @param 	propulsion
	 * 			The propulsion of what it is fired with
	 * @return	| if this == Rifle
	 * 			|	result == 1.5
	 * 			| if this == Bazooka
	 * 			|	result == (2.5 + (propulsion * 7.0/100.0))
	 */
	public double getForce(int propulsion){
		switch (this){
			case Rifle: return 1.5;
			case Bazooka: return (2.5 + (propulsion * 7.0/100.0));
			default: return 0;
		}
	}
}
