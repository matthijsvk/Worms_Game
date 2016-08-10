package worms.model.part3;

import worms.gui.game.IActionHandler;
import worms.model.Worm;
import worms.model.part3.Expression;

public class TwoArguments extends Expression<Type> {
	
	// for ideotic reasons.
	public TwoArguments(){}
	
	public TwoArguments(Expression<? extends Type> argumentOne, Expression<? extends Type> argumentTwo) {
		this.argumentOne = argumentOne;
		this.argumentTwo = argumentTwo;
		System.out.println("construction TWO ARGS-------");
	}
	
	public TwoArguments(Expression<? extends Type> argumentOne, Expression<? extends Type> argumentTwo, int line) {
		this.argumentOne = argumentOne;
		this.argumentTwo = argumentTwo;
		this.line= line;
		System.out.println("construction TWO ARGS-------");
		System.out.print(argumentOne + " " + argumentTwo);
	}
	
	private int line;
	public int getLine(){
		return this.line;
	}
	private Expression<? extends Type> argumentOne;
	public Expression<? extends Type> getArgumentOne(){
		return argumentOne;
	}
	private Expression<? extends Type> argumentTwo;
	public Expression<? extends Type> getArgumentTwo(){
		return argumentTwo;
	} 

	@Override
	public Type function(int line) {
		return null;
	}	
	
	@Override
	public String toString(){
		return "Superclass TwoArguments";
	}
	
	@Override
	public void setAppropriateValues(Worm worm, IActionHandler handler){
		this.argumentOne.setAppropriateValues(worm, handler);
		this.argumentTwo.setAppropriateValues(worm, handler);
		System.out.println("values are set, 2Arg");
	}
	
	// ------------------- nested classes --------------------------
	
	
	//------------------ And -----------------------------
	public class And extends TwoArguments{ 
		
		public And(Expression<Boolean> argumentOne, Expression<Boolean> argumentTwo,int line){
			super(argumentOne,argumentTwo,line);
		}
		
		@Override
		public Boolean function(int line) {
			if ( (! getArgumentOne().getReturnType().equals(Boolean.class)) || (! getArgumentTwo().getReturnType().equals(Boolean.class)) )
				throw new IllegalArgumentException("you tried to And some not-Booleans");
			return new Boolean(((Boolean)getArgumentOne().function(line)).getValue() && ((Boolean)getArgumentTwo().function(line)).getValue()); 
		}

		@Override
		public Class<Boolean> getReturnType() {
			return Boolean.class;
		}
		
		@Override
		public String toString(){
			return "and( "+getArgumentOne().toString()+" , "+getArgumentTwo().toString()+" )";
		}
	}
	
	//------------------ Or -----------------------------
	public class Or extends TwoArguments{ 
		
		public Or(Expression<Boolean> argumentOne, Expression<Boolean> argumentTwo,int line){
			super(argumentOne,argumentTwo,line);
		}
		
		@Override
		public Boolean function(int line) {
			if ( (! getArgumentOne().getReturnType().equals(Boolean.class)) || (! getArgumentTwo().getReturnType().equals(Boolean.class)) )
				throw new IllegalArgumentException("you tried to Or some not-Booleans");
			return new Boolean(((Boolean)getArgumentOne().function(line)).getValue() || ((Boolean)getArgumentTwo().function(line)).getValue());
		}

		@Override
		public Class<Boolean> getReturnType() {
			return Boolean.class;
		}
		
		@Override
		public String toString(){
			return "or( "+getArgumentOne().toString()+" , "+getArgumentTwo().toString()+" )";
		}
	}
	
	//------------------Equals -----------------------------
	public class Equals extends TwoArguments{
		
		public Equals(Expression<Type> argumentOne, Expression<Type> argumentTwo, int line){
			super(argumentOne,argumentTwo, line);
		}
		
		@Override
		public Boolean function(int line) {
			if ( (getArgumentOne().getReturnType() == null) || (getArgumentTwo().getReturnType() == null)){
				return new Boolean ( getArgumentOne().function(line) == getArgumentTwo().function(line) );
			}
			else if ( ( getArgumentOne().getReturnType().equals(Double.class)) && (getArgumentTwo().getReturnType().equals(Double.class)) ){
				return new Boolean( ((Double)getArgumentOne().function(line)).equals(((Double)getArgumentTwo().function(line))) );
			}
			else if ( ( getArgumentOne().getReturnType().equals(Boolean.class)) && (getArgumentTwo().getReturnType().equals(Boolean.class)) ){
				return new Boolean( ((Boolean)getArgumentOne().function(line)).equals((Boolean)getArgumentTwo().function(line)) ); 
			}
			else if ( ( getArgumentOne().getReturnType().equals(getArgumentTwo().getReturnType())) ){
				return new Boolean( ((Integer)(getArgumentOne().function(line).hashCode())).equals((Integer)getArgumentTwo().function(line).hashCode()) );
			}
			else throw new IllegalArgumentException ("you tried to Equals two Expressions of different classes");
		}

		@Override
		public Class<Boolean> getReturnType() {
			return Boolean.class;
		}
		
		@Override
		public String toString(){
			return "equals( "+getArgumentOne().toString()+" , "+getArgumentTwo().toString()+" )";
		}
	}
	
	//------------------ notEquals-----------------------------
	public  class NotEquals extends TwoArguments{ 
		
		public NotEquals(Expression<Type> argumentOne, Expression<Type> argumentTwo, int line){
			super(argumentOne,argumentTwo, line);
		}
		
		@Override
		public Boolean function(int line) {
			// if one of the arguments in null
			if ( (getArgumentOne().getReturnType() == null) || (getArgumentTwo().getReturnType() == null)){
				return new Boolean ( getArgumentOne().function(line) == getArgumentTwo().function(line) );
			}
			// Doubles
			else if ( ( getArgumentOne().getReturnType().equals(Double.class)) && (getArgumentTwo().getReturnType().equals(Double.class)) ){
				return new Boolean(! ((Double)getArgumentOne().function(line)).equals((Double)getArgumentTwo().function(line)));
			}
			// Booleans
			else if ( ( getArgumentOne().getReturnType().equals(Boolean.class)) && (getArgumentTwo().getReturnType().equals(Boolean.class)) ){
				return new Boolean(! ((Boolean)getArgumentOne().function(line)).equals((Boolean)getArgumentTwo().function(line)) );
			}
			// others
			else if ( ( getArgumentOne().getReturnType().equals(getArgumentTwo().getReturnType())) ){
				return new Boolean(! ((Integer)getArgumentOne().function(line).hashCode()).equals((Integer)getArgumentTwo().function(line).hashCode()));
			}
			else throw new IllegalArgumentException ("you tried to Not Equals two Expressions of different classes");
		}

		@Override
		public Class<Boolean> getReturnType() {
			return Boolean.class;
		}
		
		@Override
		public String toString(){
			return "notEquals( "+getArgumentOne().toString()+" , "+getArgumentTwo().toString()+" )";
		}
	}
	
	//------------------ BiggerThan -----------------------------
	public  class BiggerThan extends TwoArguments{ 
		
		public BiggerThan(Expression<Boolean> argumentOne, Expression<Boolean> argumentTwo, int line){
			super(argumentOne, argumentTwo, line);
		}
		
		@Override
		public Boolean function(int line) {
			if ( (! getArgumentOne().getReturnType().equals(Double.class)) || (! getArgumentTwo().getReturnType().equals(Double.class)) )
				throw new IllegalArgumentException("you tried to BiggerThan some not-Doubles");
			return new Boolean( ((Double)getArgumentOne().function(line)).getValue() > ((Double)getArgumentTwo().function(line)).getValue() );
		}

		@Override
		public Class<Boolean> getReturnType() {
			return Boolean.class;
		}
		
		@Override
		public String toString(){
			return "BiggerThan( "+getArgumentOne().toString()+" , "+getArgumentTwo().toString()+" )";
		}
	}
	
	//------------------ BiggerOrEquals -----------------------------
	public class BiggerOrEquals extends TwoArguments{ 
		
		public BiggerOrEquals(Expression<Double> argumentOne, Expression<Double> argumentTwo, int line){
			super(argumentOne, argumentTwo, line);
		}
		
		@Override
		public Boolean function(int line) {
			
			if ( (! getArgumentOne().getReturnType().equals(Double.class)) || (! getArgumentTwo().getReturnType().equals(Double.class)) )
				throw new IllegalArgumentException("you tried to BiggerOrEqual some not-Doubles");
			return new Boolean( ((Double)getArgumentOne().function(line)).getValue() >= ((Double)getArgumentTwo().function(line)).getValue() );
		}

		@Override
		public Class<Boolean> getReturnType() {
			return Boolean.class;
		}
		
		@Override
		public String toString(){
			return "biggerOrEquals( "+getArgumentOne().toString()+" , "+getArgumentTwo().toString()+" )";
		}
	}
	
	//------------------SmallerThan -----------------------------
	public  class SmallerThan extends TwoArguments{ 
		
		public SmallerThan(Expression<Double> argumentOne, Expression<Double> argumentTwo, int line){
			super(argumentOne, argumentTwo, line);
		}
		
		@Override
		public Boolean function(int line) {
			if ( (! getArgumentOne().getReturnType().equals(Double.class)) || (! getArgumentTwo().getReturnType().equals(Double.class)) )
				throw new IllegalArgumentException("you tried to SmallerThan some not-Doubles");
			return new Boolean( ((Double)getArgumentOne().function(line)).getValue() < ((Double)getArgumentTwo().function(line)).getValue() );
		}

		@Override
		public Class<Boolean> getReturnType() {
			return Boolean.class;
		}
		
		@Override
		public String toString(){
			return "smallerThan( "+getArgumentOne().toString()+" , "+getArgumentTwo().toString()+" )";
		}
	}
	
	//------------------SmallerOrEquals -----------------------------
	public  class SmallerOrEquals extends TwoArguments{ 
		
		public SmallerOrEquals(Expression<Double> argumentOne, Expression<Double> argumentTwo, int line){
			super(argumentOne, argumentTwo, line);
		}
		
		@Override
		public Boolean function(int line) {
			if ( (! getArgumentOne().getReturnType().equals(Double.class)) || (! getArgumentTwo().getReturnType().equals(Double.class)) )
				throw new IllegalArgumentException("you tried to SmallerOrEqual some not-Doubles");
			return new Boolean( ((Double)getArgumentOne().function(line)).getValue() <= ((Double)getArgumentTwo().function(line)).getValue() );
		}

		@Override
		public Class<Boolean> getReturnType() {
			return Boolean.class;
		}
		
		@Override
		public String toString(){
			return "smallerOrEquals( "+getArgumentOne().toString()+" , "+getArgumentTwo().toString()+" )";
		}
	}
	
	//------------------Add -----------------------------
	public  class Add extends TwoArguments{ 
		
		public Add(Expression<Double> argumentOne, Expression<Double> argumentTwo, int line){
			super(argumentOne, argumentTwo, line);
		}
		
		@Override
		public Double function(int line) {
			if ( (! getArgumentOne().getReturnType().equals(Double.class)) || (! getArgumentTwo().getReturnType().equals(Double.class)) )
				throw new IllegalArgumentException("you tried to Add some not-Doubles");
			return new Double( ((Double)getArgumentOne().function(line)).getValue() + ((Double)getArgumentTwo().function(line)).getValue() );
		}

		@Override
		public Class<Double> getReturnType() {
			return Double.class;
		}
		
		@Override
		public String toString(){
			return "add( "+getArgumentOne().toString()+" , "+getArgumentTwo().toString()+" )";
		}
	}
	
	//------------------ Substract -----------------------------
	public  class Substract extends TwoArguments{ 
		
		public Substract(Expression<Double> argumentOne, Expression<Double> argumentTwo, int line){
			super(argumentOne, argumentTwo, line);
			System.out.println("construction SUBSTRACT");
		}
		
		@Override
		public Double function(int line) {
			
			if ( (! getArgumentOne().getReturnType().equals(Double.class)) || (! getArgumentTwo().getReturnType().equals(Double.class)) )
				throw new IllegalArgumentException("you tried to Substract some not-Doubles");
			return new Double( ((Double)getArgumentOne().function(line)).getValue() - ((Double)getArgumentTwo().function(line)).getValue() );
		}

		@Override
		public Class<Double> getReturnType() {
			return Double.class;
		}
		
		@Override
		public String toString(){
			return "substract( "+getArgumentOne().toString()+" , "+getArgumentTwo().toString()+" )";
		}
	}
	
	//------------------ Multiply-----------------------------
	public  class Multiply extends TwoArguments{ 
		
		public Multiply(Expression<Double> argumentOne, Expression<Double> argumentTwo, int line){
			super(argumentOne, argumentTwo, line);
			System.out.println("construction MULTIPLY");
		}
		
		@Override
		public Double function(int line) {
			if ( (! getArgumentOne().getReturnType().equals(Double.class)) || (! getArgumentTwo().getReturnType().equals(Double.class)) )
				throw new IllegalArgumentException("you tried to Multiply some not-Doubles");
			return new Double( ((Double)getArgumentOne().function(line)).getValue() * ((Double)getArgumentTwo().function(line)).getValue() );
		}

		@Override
		public Class<Double> getReturnType() {
			return Double.class;
		}
		
		@Override
		public String toString(){
			return "multiply( "+getArgumentOne().toString()+" , "+getArgumentTwo().toString()+" )";
		}
	}
	
	//------------------ Divide -----------------------------
	public  class Divide extends TwoArguments{ 
		
		public Divide(Expression<Double> argumentOne, Expression<Double> argumentTwo, int line){
			super(argumentOne, argumentTwo, line);
		}
		
		@Override
		public Double function(int line) {
			if ( (! getArgumentOne().getReturnType().equals(Double.class)) || (! getArgumentTwo().getReturnType().equals(Double.class)) )
				throw new IllegalArgumentException("you tried to Divide some not-Doubles");
			return new Double( ((Double)getArgumentOne().function(line)).getValue() / ((Double)getArgumentTwo().function(line)).getValue() );
		}

		@Override
		public Class<Double> getReturnType() {
			return Double.class;
		}
		
		@Override
		public String toString(){
			return "devide( "+getArgumentOne().toString()+" , "+getArgumentTwo().toString()+" )";
		}
	}
}
