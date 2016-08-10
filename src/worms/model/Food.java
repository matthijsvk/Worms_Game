package worms.model;

import worms.model.exceptions.IllegalPositionException;
import worms.model.exceptions.IllegalRadiusException;

public class Food extends GameObject {

	/**
	 * Constructor for a food object
	 * @effect | this.getRadius == 0.2
	 */
	public Food(World world, double xPosition, double yPosition) throws IllegalPositionException, IllegalRadiusException {
		super(world, xPosition, yPosition, 0.2);
	}
	
	
	// --------------------- Terminate -------------------------------
	/**
	 * This method terminates a Food object (f.e. if it has been eaten)
	 * @post | ! (new this.getWorld()).getAllFood().contains(this)
	 * @post | new.getWorld() == null
	 */
	public void terminate() {
		super.terminate();
		// Removing bindings
		this.getWorld().removeFood(this);
		this.setWorld(null);
	}
}