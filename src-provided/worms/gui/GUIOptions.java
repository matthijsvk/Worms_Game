package worms.gui;

public class GUIOptions {
	
	/**
	 * Disable full screen mode
	 * Default: false
	 * 
	 * Full screen can also be disabled from the command line by providing the -window argument
	 */
	public boolean disableFullScreen = true;
	
	/**
	 * Random seed for the game
	 * Default: 3
	 * 
	 * Can also be set from the command line with the -seed argument
	 */
	public long randomSeed = 3;

	/**
	 * Enable quick worm selection by clicking the mouse
	 * Default: false
	 *  
	 * Can also be enabled from the command line with the -clickselect argument
	 */
	public boolean enableClickToSelect = false;
	
	/**
	 * The program that is executed by computer-controlled worms.
	 * Default: "programs/program.txt"
	 * 
	 * Can also be set from the command line with the -program argument
	 */
	public String programFile = "programs/program.txt";
}