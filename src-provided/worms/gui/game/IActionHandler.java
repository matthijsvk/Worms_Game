package worms.gui.game;

import worms.model.Worm;

/**
 * <p>
 * An action handler executes the actions of a worm as if they were commanded by
 * a user by pressing the corresponding keys.
 * </p>
 * 
 * <p>
 * An action, when executed through an action handler,
 * <ul>
 * <li>shows periodic updates on the GUI (such as jump animations)
 * <li>eventually calls the corresponding facade methods, exactly like what
 * happens with a human player
 * <li>returns true if the action has been completed successfully; false
 * otherwise
 * </ul>
 * </p>
 * <p>
 * Execution is blocked until the action has been entirely performed.
 * </p>
 */
public interface IActionHandler {

	public boolean turn(Worm worm, double angle);

	public boolean move(Worm worm);

	public boolean jump(Worm worm);

	public boolean fire(Worm worm, int propulsion);

	public boolean toggleWeapon(Worm worm);

	/**
	 * Print a message on the screen for a short amount of time.
	 * 
	 * This is a utility method, and not an action. You are not required to use
	 * this method for printing messages; you should only use it if you want the
	 * given message to appear on the screen while playing.
	 * 
	 * The method may return before the message has been removed from the screen
	 * again.
	 */
	public void print(String message);
}
