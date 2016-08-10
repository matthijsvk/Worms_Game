package worms.model;

import java.util.Map;
import worms.gui.game.IActionHandler;
import worms.model.part3.Statement;
import worms.model.part3.Type;

public class Program {
	
	/**
	 * Constructor for this program
	 * @param 	globals
	 * 			the global variables of this program (Map<String, Type>)
	 * @param 	statement
	 * 			the statement of this program (Statement)
	 * @param 	actionHandler
	 * 			the IActionHandler interface.
	 */
	public Program(Map<String,Type> globals, Statement statement, IActionHandler actionHandler){
		this.globals = globals;
		this.staticGlobals = globals;
		this.statement = statement;
		this.actionHandler = actionHandler;
		
		this.programLine= statement.getLine();
		System.out.println("statement.getLine: "+this. programLine);
		this.programLastLine= statement.getLine() + statement.getLength() - 1; // -1 because getLength() counts the first line of the statement
		System.out.println("programLastLine: "+this. programLastLine);
	}
	
	// -------------------- Program Lines --------------------------
	/**
	 * A variable that stores the line of the program where execution is at the moment.
	 */
	private int programLine;
	
	/**
	 * A variable that stores the amount of lines that this program contains.
	 */
	private int programLastLine;
	
	/**
	 * returns the current program line.
	 */
	public int getProgramLine(){
		return this.programLine;
	}
	
	/**
	 * Sets the program line to the given value
	 * @param line
	 * 			the line of the program to be set (int)
	 * @post  new.getProgramLine() == line
	 */
	public void setProgramLine(int line){ 
		if ( line > this.programLastLine){
			this.globals = this.staticGlobals;
			this.programLine = statement.getLine(); //start again at the beginning of the program
		}
		else
			this.programLine= line;
	}
	
	/**
	 * returns the last line of this program
	 */
	public int getProgramLastLine(){
		return this.programLastLine;
	}
	
	/**
	 * increments the programline with one.
	 * @effect new.getProgramLine() == this.getProgramLine()+1
	 */
	public void programLineIncrement(){
		this.setProgramLine(this.getProgramLine()+1);;
	}
	
	// -------------------- Program Counter ------------------------
	
	/**
	 * A variable that stores the amount of lines the program has executed in the current turn.
	 */
	private int programCounter;
	
	/**
	 * returns the programcounter for this program
	 */
	public int getProgramCounter(){
		return this.programCounter;
	}
	
	/**
	 * Increments the programcounter with one.
	 * @post new.getProgramCounter() == this.getProgramCounter()+1
	 */
	public void programCounterIncrement(){
		this.programCounter +=1;
	}
	
	/**
	 * Resets the programcounter to zero
	 * @post new.getProgramCounter() == 0
	 */
	public void programCounterReset(){
		this.programCounter = 0;
	}
	
	/**
	 * Checks if the programcounter is ok
	 * @return true if this.getProgramCounter < 1000
	 * @return false elsewhere.
	 */
	public boolean programCounterOk(){
		if (this.getProgramCounter() >= 1000)
			return false;
		else
			return true;
	}
	
	// --------------------- Globals --------------------------------
	/**
	 * variable holds the global variables of this program
	 */
	private Map<String, Type> globals;
	
	/**
	 * variable holds the static global variables of this program (as initialised)
	 */
	private Map<String, Type> staticGlobals;
	
	/**
	 * returns the global variables of this program
	 */
	public Map<String, Type> getGlobals(){
		return this.globals;
	}
	
	// ---------------------- Statement ------------------------------
	/**
	 * variable holds the statement of this program
	 */
	private Statement statement;
	
	/**
	 * returns the statment of this program
	 */
	public Statement getStatement() {
		return this.statement;
	}
	
	// ----------------------- Action Handler ------------------------
	/**
	 * Variable holds the action handler interface of this program
	 */
	private IActionHandler actionHandler; //is set at construction of the program

	/**
	 * returns the action handler interface of this program
	 */
	public IActionHandler getActionHandler() {
		return this.actionHandler;
	}
	
	// ------------------------- WORM --------------------------------
	// CONNECTION: slave
	/**
	 * variable holds the bound worm of this program
	 */
	private Worm worm;
	
	/**
	 * returns the worm of this program
	 */
	public Worm getWorm() {
		return this.worm;
	}
	
	/**
	 * sets the worm of this program to the given worm
	 * @param worm
	 */
	public void setWorm(Worm worm) {
		this.worm = worm;
	}
	
	/**
	 * sets the correct worm and action handler interface to the statement (and substatements) of this program
	 */
	public void setAppropriateValues(){
		System.out.println("#############################    Program Appropriate values set is started     #############################");
		//via worm, the statements can access the globals of this program (statement.getWorm.getProgram.getGlobals() )
		this.statement.setAppropriateValues(this.worm, this.actionHandler); 
		System.out.println("#############################    Program Appropriate values set is done    ################################");
	}
	
	//--------------------------- Execution --------------------------
	/**
	 * executes one first-level statement of this program.
	 */
	public void nextExec(){
		if (this.programCounterOk()){
			int line = this.getProgramLine();
			if (line == 47){
				return;
			}
			System.out.print("next line started:");System.out.println(line);
			this.statement.function(line);
		}
	}
	
	/**
	 * executes 1000 statements of this program.
	 */
	public void allExec(){
		System.out.println("Started allExec.");
		this.programCounterReset(); 
		while(this.programCounterOk() && this.getWorm().getRemainingActionPoints() > 10){ 
			//System.out.print("programCounter:");System.out.println(this.getProgramCounter());
			try{
				this.nextExec();
			}
			catch (NullPointerException exc){
				//System.out.println(exc.getMessage());
				exc.printStackTrace();
				System.out.println("this PC worm terminated itself");
				break;
			}
		}
		System.out.println("the pc game finished its turn");
	}
	
	// ----------------------------- Termination ------------------------------------------
	/**
	 * terminates this program
	 */
	public void terminate(){
		this.worm= null;
		this.globals = null;
		this.statement = null;
		this.actionHandler = null;
		this.isTerminated = true;
	}
	
	/**
	 * variable holds if this program is terminated
	 */
	private boolean isTerminated;
	
	/**
	 * returns if this program is terminated
	 */
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
	// ---------------------------- valid program -------------------------------
		
	public boolean isValidProgram(){
		System.out.println("xxxxxxxxxxxxxxxxxxxxx VP begin xxxxxxxxxxxxxxxxxxxxxxx");
		boolean result = statement.checkForLoop();
		System.out.println("xxxxxxxxxxxxxxxxxxxxx VP end  xxxxxxxxxxxxxxxxxxxxxxxxx");
		return result;
	}
	
}
