package worms.model.part3;

import worms.gui.game.IActionHandler;
import worms.model.Worm;

public class ZeroArguments<T> extends Expression<T> {
	
	public ZeroArguments(){}
	
	public ZeroArguments(int line){
		this.line = line;
	}
	
	private int line;
	public int getLine(){
		return this.line;
	}
	
	@Override
	public String toString(){
		return "Superclass ZeroArguments.";
	}
	
	// ----------------------- nested classes --------------------------------------------------
	 public  class Variable extends ZeroArguments<Type> {
		
		private String name;
		
		private Type type;
		
		public Variable(String name, Type type, int line) {
			super(line);
			this.name = name;
			this.type = type;
		}
		
		private Worm worm;
		
		@Override
		public void setAppropriateValues(Worm worm, IActionHandler handler){
			this.worm = worm;
//			System.out.println("Variable,");
//			System.out.println(this.worm.getProgram().getGlobals());
//			System.out.println(this.name);
//			System.out.println(this.function());
		}
		
		@Override
		public Type function(int line){
			Type result = this.worm.getProgram().getGlobals().get(this.name);
			return result;
		}
		
		public Class<?> getReturnType() {
			//Type t = this.worm.getProgram().getGlobals().get(this.name);
			Type t = this.type;
			if (t instanceof Entity){
				return Entity.class;
			}
			else if (t instanceof Double){
				return Double.class;
			}
			else if (t instanceof Boolean){
				return Boolean.class;
			}
			else if (t == null){
				return null;
			}
			System.out.print("Shit happened @ variable.getReturnType()"); System.out.println(t);
			return null;
		}
		
		@Override
		public String toString(){
			if (this.worm == null)
				return "Variable " + this.name;
			else
				return "Variable " + this.name + " = " +this.worm.getProgram().getGlobals().get(this.name);
		}
	}
	 
	
	 public  class Null extends ZeroArguments<T> {
		
		public Null(int line) {
			super(line);
		}
		
		@Override
		public T function(int line) {
			return null;
		}

		@Override
		public Class<?> getReturnType() {
			return null;
		}
		
		@Override
		public String toString(){
			return "null";
		}
		
	}

	 public  class Self extends ZeroArguments<Worm> {
		
		public Self(int line) {
			super(line);
		}
		
		private Worm w;
		
		@Override
		public Worm function(int line) {
			return this.w;
		}

		@Override
		public Class<?> getReturnType() {
			return Entity.class;
		}
		
		@Override
		public void setAppropriateValues(Worm worm, IActionHandler handler){
			this.w = worm;
			System.out.print("Self: SetappVal worm is set to:");
			System.out.println(this.w);
		}
		
		@Override
		public String toString(){
			return "Self:"+ this.w;
		}
		
		
	}
	
	 public  class True extends ZeroArguments<Boolean> {
		
		public True(int line){
			super(line);
			this.t = new Boolean(true);
		}
		
		private Boolean t;
		
		@Override
		public Boolean function(int line) {
			return this.t;
		}

		@Override
		public Class<?> getReturnType() {
			return Boolean.class;
		}
		
		@Override
		public String toString(){
			return "True";
		}
		
	}
	
	 public  class False extends ZeroArguments<Boolean> {
		
		public False(int line) {
			super(line);
			this.t = new Boolean(false);
		}
		
		private Boolean t;
		
		@Override
		public Boolean function(int line) {
			return this.t;
		}

		@Override
		public Class<?> getReturnType() {
			return Boolean.class;
		}
		
		@Override
		public String toString(){
			return "False";
		}
	}
	
	public  class Constant extends ZeroArguments<Double> {
		
		public Constant(double c, int line){
			super(line);
			this.c = new Double(c);
		}
		
		private Double c;
		
		@Override
		public Double function(int line) {
			return this.c;
		}
		
		public Class<?> getReturnType(){
			return Double.class;
		}
		
		@Override
		public String toString(){
			return c.toString();
		}
	}
}
