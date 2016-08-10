package worms.model.part3;

import java.util.LinkedList;
import worms.model.GameObject;
import worms.model.Worm;
import worms.model.programs.ProgramFactory.ForeachType;
import worms.gui.game.IActionHandler;

public class Control extends Statement {
	
	//Useless constructor
	public Control(){}
	
	//Useless function
	public void function(){}
	
	Statement s;
	private int line;
	@Override
	public int getLine(){
		return this.line;
	}
	
	private Worm worm;
	public Worm getWorm(){
		return this.worm;
	}
	
	private Control(Statement statement, int line){
		this.s = statement;
		this.line = line;
	}
	
	@Override
	public void setAppropriateValues(Worm worm, IActionHandler handler){
		s.setAppropriateValues(worm, handler);
		this.worm=worm;
	}
	
	@Override
	public String toString(){
		return "Superclass control";
	}
	

	// ---- Nested classes -------

	public class If extends Control{
		
		Expression<Boolean> c;
		Statement o;
		
		public If(Expression<Boolean> condition, Statement then, Statement otherwise, int line){
			super(then,line);
			c = condition;
			o = otherwise;
		}
		
		@Override
		public void function(int line){
			if ( line == this.getLine()){
				System.out.print("entered IF- ELSE function; ");
				//execute this function
				if( ((Boolean)c.function(line)).getValue() ){
					System.out.println("entered if");
					//execute the body statements
					int sequenceLength= s.getLength();				
					int sequenceNb = 0; //the line number in this while loop
						//restart this while loop while condition OK; break if (!  programCounter OK|| action points OK )
					while(sequenceNb < sequenceLength && this.getWorm().getProgram().programCounterOk() && this.getWorm().getRemainingActionPoints() > 10){ 
						s.function(sequenceNb+ (line+1));
						this.getWorm().getProgram().setProgramLine(sequenceNb +(line+1)); //save the currently executed line
						sequenceNb +=1;
					}
				}
				else {
					System.out.println("entered else");
					//execute the body statements
					int sequenceLength= o.getLength();
					int sequenceNb = 0; //the line number in this while loop
					//restart this while loop while condition OK; break if (!  programCounter OK|| action points OK )
					while(sequenceNb < sequenceLength && this.getWorm().getProgram().programCounterOk() && this.getWorm().getRemainingActionPoints() > 10){ 
						o.function(sequenceNb+ (line+ s.getLength()+1+1+1)); //+1 omdat je pas ����n onder de if{ moet  beginnen, nog een +1 omdat er een } staat onder de if, nog een +1 omdat de else{} er staat
						this.getWorm().getProgram().setProgramLine(sequenceNb +(line+ s.getLength()+1+1+1)); //save the currently executed line
						sequenceNb +=1;
					}
				}
				this.getWorm().getProgram().programCounterIncrement(); 	
			}
			this.getWorm().getProgram().setProgramLine(line+ this.getLength()); // continue with the next statements.  
		}
		
		@Override
		public void setAppropriateValues(Worm worm, IActionHandler handler){
			System.out.println("If-condition is set.");
			c.setAppropriateValues(worm, handler);
			super.setAppropriateValues(worm, handler);
			o.setAppropriateValues(worm, handler);
		}
		
		@Override
		public String toString(){
			String result = "if ("+c.toString()+")\n";
			result += "\t"+s.toString()+"\n";
			result += "else \n";
			result += "\t"+o.toString();
			return result;
		}
		
		@Override
		public int getLength() {
			int result;
			if (o.getLength() != 0){
				result= (1+ s.getLength()+ 1) + (1 + o.getLength()+ 1);  //+1 for opening if, closing if, opening else, closing else
			}
			else {
				result= 1+ s.getLength()+ 1;
			}
//			System.out.print("the if at line "); System.out.print(this.getLine());System.out.print(" has length: "); System.out.println(result);
			return result;
		}
		
		@Override
		public boolean checkForLoop(){
			System.out.println("Checking If-Else");
			return s.checkForLoop() && o.checkForLoop();
		}
		
		@Override
		public boolean hasAction(){
			System.out.println("action in if?");
			return s.hasAction() || o.hasAction();
		}
	}
	
	public class While extends Control{
		
		Expression<Boolean> c;
		
		public While(Expression<Boolean> condition, Statement statement, int line){
			super(statement, line);
			c = condition;
		}
		
		@Override
		public void function(int line){
		
			if ( line == this.getLine()){
				//execute the body statements
				int sequenceLength= s.getLength();
				
				while( ((Boolean) c.function(line)).getValue() && this.getWorm().getProgram().programCounterOk() && this.getWorm().getRemainingActionPoints() > 10 ){
					System.out.print("WHILE loop restarted; LENGTH: "); System.out.println(this.getLength());
//					System.out.println("programCounter: "+this.getWorm().getProgram().getProgramCounter());
					int sequenceNb = 0; //the line number in this while loop
					
					//restart this while loop while condition OK; break if (!  programCounter OK|| action points OK )
					while(sequenceNb < sequenceLength && ((Boolean) c.function(line)).getValue() && this.getWorm().getProgram().programCounterOk() && this.getWorm().getRemainingActionPoints() > 10){ 
						s.function(sequenceNb+ (line+1));
						this.getWorm().getProgram().setProgramLine(sequenceNb +(line+1)); //save the currently executed line
						sequenceNb += 1;
					}
					this.getWorm().getProgram().setProgramLine(line + 1); // go back to the first statement of the while loop
				}
				this.getWorm().getProgram().setProgramLine(line + this.getLength()); // the while loop is finished, continue with the next statements.
				this.getWorm().getProgram().programCounterIncrement(); 
			}
			else{
				System.out.print("the line does not correspond to the While-line");
				System.out.println("programline: "+line + " / while-line: "+ this.getLine());
			}
			this.getWorm().getProgram().setProgramLine(line+ this.getLength()); // continue with the next statements.
		}
		
		@Override
		public String toString(){
			String result = "while ("+c.toString()+")\n";
			result += "\t"+s.toString()+"\n";
			return result;
		}
		
		@Override
		public void setAppropriateValues(Worm worm, IActionHandler handler){
			System.out.println("Set App Val while");
			c.setAppropriateValues(worm, handler);
			super.setAppropriateValues(worm, handler);
		}
		
		@Override
		public int getLength() {
			int result = 1+ s.getLength() +1 ; //2* +1: startline and ending bracket
//			System.out.print("the while at line "); System.out.print(this.getLine());System.out.print(" has length: "); System.out.println(result);
			return result;
		}
		
		@Override
		public boolean checkForLoop(){
			System.out.println("checking While");
			return s.checkForLoop();
		}
		
		@Override
		public boolean hasAction(){
			return s.hasAction();
		}
		
	}
	
	public class ForEach extends Control{
				
		LinkedList<GameObject> l= new LinkedList<>();
		
		private ForeachType forEachType;
		private String variableName;
		
		public ForEach(ForeachType type, String variableName, Statement statement, int line) {
			super(statement, line);
			this.forEachType= type;
			this.variableName = variableName;
			
		}
		
		@Override
		public void function(int line){
			
			if ( line == this.getLine()){
				System.out.print("entered FOR EACH; ");  System.out.print("The looplist :"); System.out.println(l);
				
				int sequenceLength= s.getLength(); //get the lenght of the (sequence of) statements of the for loop
				
				
				//loop through the body statements for all elements in this.l
				for (GameObject variable: l){
					
//					this.getWorm().getProgram().setProgramLine(line + 1); //you have to set the counter to 'line +1' because it has been incremented by going through the sequence of statements. the new loop needs to start at the beginline of the for-loop
//					// the +1 is because you have to start execution at the next line (one after the while(){ line)
//					s.function(line + 1);
					
					this.getWorm().getProgram().getGlobals().put(this.variableName, variable);
					
					System.out.print("next variable : ");System.out.println(variable);
					System.out.println("programCounter: "+this.getWorm().getProgram().getProgramCounter());
					
					int sequenceNb = 0; //the line number in this for loop
					
					//re- execute the statements in this for loop for all elements in l;  break if (!  programCounter OK|| action points OK )
					while(sequenceNb < sequenceLength && this.getWorm().getProgram().programCounterOk() && this.getWorm().getRemainingActionPoints() > 10){ 
						s.function(sequenceNb+ (line+1));
//						this.getWorm().getProgram().programCounterIncrement();  // is done inside the other statements
						this.getWorm().getProgram().setProgramLine(sequenceNb +(line+1)); //save the currently executed line
						sequenceNb +=1;
					}
					this.getWorm().getProgram().setProgramLine(line + 1); // go back to the first statement of the for loop
				}
				this.getWorm().getProgram().programCounterIncrement(); 
				this.getWorm().getProgram().setProgramLine(line+ this.getLength());
				System.out.println("programLine at end of for-loop: "+this.getWorm().getProgram().getProgramLine());
				System.out.println("for-each done");
			}	
			this.getWorm().getProgram().setProgramLine(line+ this.getLength()); 
		}
		
		@Override
		public void setAppropriateValues(Worm worm, IActionHandler handler){
			System.out.println("SetAppVal ForEach");
			super.setAppropriateValues(worm, handler); // statement.setAppropriateValues(..., ...)
			
			switch (this.forEachType){
			case WORM:
				l.addAll(getWorm().getWorld().getAllWorms());
				System.out.print("THE WORMLIST= l: ");System.out.println(l);
				break;
			case FOOD:
				l.addAll(getWorm().getWorld().getAllFood());
				break;
			case ANY:
				l.addAll(getWorm().getWorld().getAllWorms());
				l.addAll(getWorm().getWorld().getAllFood());
				break;
			default: //do nothing
			}
			
			;
		}
		
		@Override
		public String toString(){
			String result = "for each ("+forEachType+" "+variableName+")\n";
			result += "\t"+s.toString()+"\n";
			return result;
		}
		
		@Override
		public int getLength() {
			int result= 1+ s.getLength() + 1;
//			System.out.print("foreach on line "); System.out.print(this.getLine()); System.out.print(" has length: ");System.out.println(result);
			return result;
		}
		
		@Override
		public boolean checkForLoop(){
			System.out.println("Checking for");
			return ! s.hasAction();
		}
		
		@Override
		public boolean hasAction(){
			return s.hasAction();
		}
	}
}
