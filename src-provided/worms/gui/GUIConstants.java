package worms.gui;

public final class GUIConstants {

	/**
	 * Default width of the window, when not running in full-screen, in pixels
	 */
	public static final int DEFAULT_WINDOW_WIDTH = 1024;

	/**
	 * Default height of the window, when not running in full-screen, in pixels
	 */
	public static final int DEFAULT_WINDOW_HEIGHT = 768;

	/**
	 * Framerate at which to re-draw the screen, in frames per (real) second
	 */
	public static final int FRAMERATE = 15; // fps

	/**
	 * Time (in worm-seconds) that elapses in 1 real second
	 */
	public static final double TIME_SCALE = 0.7;

	/**
	 * Minimal angle to turn when pressing the 'turn' key a single time
	 */
	public static final double MIN_TURN_ANGLE = Math.PI / 120.0;

	/**
	 * Angle that is turned per (real) second while keeping the 'turn' keys
	 * pressed.
	 */
	public static final double ANGLE_TURNED_PER_SECOND = Math.PI;

	/**
	 * Duration of the move animation for a single step (in worm-seconds)
	 */
	public static final double MOVE_DURATION = 0.1;

	/**
	 * Time to display messages on the screen (in real seconds)
	 */
	public static final double MESSAGE_DISPLAY_TIME = 1.5;

	/**
	 * Default velocity with which worms fall down (in worm-meter per worm-seconds) 
	 */
	public static final double FALL_VELOCITY = 5.0;
	
	/**
	 * Time step to use when calculating jump positions
	 */
	public static final double JUMP_TIME_STEP = 1e-4;

	/* disable instantiations */
	private GUIConstants() {
	}
}
