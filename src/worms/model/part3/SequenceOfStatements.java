package worms.model.part3;

import java.util.List;

import worms.gui.game.IActionHandler;
import worms.model.Worm;

public class SequenceOfStatements extends Statement {
	
	public SequenceOfStatements(List<Statement> statements, int line){
		this.statements = statements;
		this.line = line;
	}
	
	private int line;
	@Override
	public int getLine(){
		return this.line;
	}
	private List<Statement> statements;
	private Worm worm;
	public Worm getWorm(){
		return this.worm;
	}
	
	@Override
	public int getLength(){
		if ( (this.statements != null) && (statements.size()!=0) &&(this.statements.get(0) != null) ){
				if (this.statements.get(1) != null)
					return this.statements.get(0).getLength() + this.statements.get(1).getLength();
				int result = this.statements.get(0).getLength();
				return result;
		}		
		return 0;
	}

	@Override
	public void function(int line) {
		if(line <= this.getWorm().getProgram().getProgramLastLine()){
			for(Statement s: statements){
				s.function(this.getWorm().getProgram().getProgramLine());
			}
		}
	}
	
	@Override
	public void setAppropriateValues(Worm worm, IActionHandler handler){
		this.worm= worm;
		for(Statement s: statements){
			s.setAppropriateValues(worm, handler);
		}
	}
	
	@Override
	public String toString(){
		//String result = "Sequence:  \n";
		String result = "";
		for(Statement s: statements){
			result += s.toString()+"\n";
		}
		return result;
	}
	
	@Override
	public boolean checkForLoop(){
		for(Statement s: statements){
			if(! s.checkForLoop())
				return false;
		}
		return true;
	}
	
	@Override
	public boolean hasAction(){
		for(Statement s: statements){
			if (s.hasAction())
				return true;
		}
		return false;
	}

}
