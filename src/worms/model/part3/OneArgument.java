package worms.model.part3;

import java.util.LinkedList;

import worms.model.Facade;
import worms.model.Worm;
import worms.model.Food;
import worms.model.GameObject;
import worms.model.exceptions.IllegalPositionException;
import worms.model.part3.Type;
import worms.gui.game.IActionHandler;


public class OneArgument extends Expression<Type> {
	
	// for ideotic reasons.
	public OneArgument(){}
	
	private OneArgument(Expression<? extends Type> argument, int line){
		this.line = line;
		this.argument = argument;
	}
	
	private Expression<? extends Type> argument;
	public Expression<? extends Type> getArgument(){
		return argument;
	}
	private int line;
	public int getLine(){
		return this.line;
	}
	
	private Facade facade= new Facade();
	
	@Override
	public String toString(){
		return "Superclass OneArgument "+argument.toString();
	}
	
	@Override
	public void setAppropriateValues(Worm worm, IActionHandler handler){
		super.setAppropriateValues(worm, handler);
		System.out.println("Set values, OneArg");
		this.argument.setAppropriateValues(worm, handler);
	}
	
	// ------------------------------------ nested classes ---------------------------------------
	
	public class SearchObj extends OneArgument {
		
		public SearchObj(Expression<Double> argument, int line) {
			super(argument, line);
		}
		
		private Worm worm;
		
		@Override
		public Type function(int line) {
			double dir = worm.getDirection() + ((Double) this.getArgument().function(line)).getValue();
			LinkedList<GameObject> ListOfObjectsToCheck = new LinkedList<>();
			for(Worm w: worm.getWorld().getAllWorms()){
				ListOfObjectsToCheck.add((GameObject) w);
			}
			try{
				int i = 1;
				while (true){
					double[] pos = {worm.getXPosition() + i*Math.cos(dir),  worm.getYPosition() + i*Math.sin(dir)};
					GameObject g = worm.getWorld().overlapsWith(pos, 0.2, ListOfObjectsToCheck);
					if (g != null){
						return g;
					}
					else {
						i++;
					}
				}
			}
			catch (IllegalPositionException e){
				return null;
			}
		}
		
		@Override
		public Class<?> getReturnType() {
			return Entity.class;
		}
		
		@Override
		public String toString(){
			return "SearchObj( "+getArgument().toString()+" )";
		}
		
		@Override
		public void setAppropriateValues(Worm worm, IActionHandler handler){
			System.out.print("SearchObj is now set: ");
System.out.print("SearchObj is now set: ");
			this.worm = worm;
			super.setAppropriateValues(worm, handler);
			System.out.println(this.worm);
		}
	}
	
	public class Not extends OneArgument {
		
		public Not(Expression<Boolean> argument, int line) {
			super(argument, line);
		}
		
		@Override
		public Boolean function(int line) throws IllegalArgumentException {
			Class<?> type = getArgument().getReturnType();
			if (type == Boolean.class){
				if (((Boolean) getArgument().function(line)).getValue())
					return new Boolean(false);
				else
					return new Boolean(true);
			}
			else throw new IllegalArgumentException("You tried to not some not-Booleans");
		}

		@Override
		public Class<?> getReturnType() {
			return Boolean.class;
		}
		
		@Override
		public String toString(){
			return "not( "+getArgument().toString()+" )";
		}
	}
	
	public class SameTeam extends OneArgument {
		
		public SameTeam(Expression<Entity> argument, int line) {
			super(argument, line);
		}
		
		private Worm worm;
		
		@Override
		public Boolean function(int line) throws IllegalArgumentException {
			Class<?> type = getArgument().getReturnType();
			if (type != Entity.class  && type != null){
				throw new IllegalArgumentException("You tried to sameteam some non-Entity");
			}	
			if ( getArgument().function(line) == null || ((Worm) getArgument().function(line)).getTeam() == null || worm.getTeam() == null)
				return new Boolean(false);
			else if ( ((Worm) getArgument().function(line)).getTeam() == worm.getTeam() ){
					return new Boolean(true);
				}
			return new Boolean(false);
		}
		
		@Override
		public void setAppropriateValues(Worm worm, IActionHandler handler){
			this.worm = worm;
			super.setAppropriateValues(worm, handler);
			System.out.println("SameTeam. Worm is set to: ");
			System.out.println(this.worm);
		}

		@Override
		public Class<?> getReturnType() {
			return Boolean.class;
		}
		
		@Override
		public String toString(){
			return "SameTeam( "+getArgument().toString()+" )";
		}
	}
	
	public class IsWorm extends OneArgument {
		
		public IsWorm(Expression<Entity> argument, int line) {
			super(argument, line);
		}
		
		@Override
		public Boolean function(int line) {
			Class<?> type = getArgument().getReturnType();
			if (type != Entity.class && type != null){ // because you can have a null value
				throw new IllegalArgumentException("You tried to isWorm some non-Entity");
			}
			if (getArgument().equals(Worm.class))
				return new Boolean(true);
			else
				return new Boolean(false);
		}

		@Override
		public Class<?> getReturnType() {
			return Boolean.class;
		}
		
		@Override
		public String toString(){
			return "isWorm( "+getArgument().toString()+" )";
		}
	}
	
	public class IsFood extends OneArgument {
		
		public IsFood(Expression<Entity> argument, int line) {
			super(argument, line);
		}
		
		@Override
		public Boolean function(int line) {
			Class<?> type = getArgument().getReturnType();
			if (type != Entity.class && type != null){ // you can hava a null value
				throw new IllegalArgumentException("You tried to isFood some non-entity");
			}	
			if (getArgument().getClass().equals(Food.class))
				return new Boolean(true);
			else
				return new Boolean(false);
		}

		@Override
		public Class<?> getReturnType() {
			return Boolean.class;
		}
		
		@Override
		public String toString(){
			return "isFood( "+getArgument().toString()+" )";
		}
	}
	
	public class Sqrt extends OneArgument {
		
		public Sqrt(Expression<Double> argument, int line) {
			super(argument, line);
		}
		
		@Override
		public Type function(int line) {
			Class<?> type = getArgument().getReturnType();
			if (type != Double.class){
				throw new IllegalArgumentException("You tried to sqrt some non-double");
			}
			double a = ((Double) getArgument().function(line)).getValue();
			double s = Math.sqrt(a);
			return new Double(s);
		}
		
		public Class<?> getReturnType(){
			return Double.class;
		}
		
		@Override
		public String toString(){
			return "sqrt( "+getArgument().toString()+" )";
		}
	}
	
	public class Sin extends OneArgument {
		
		public Sin(Expression<Double> argument, int line) {
			super(argument, line);
		}
		
		@Override
		public Double function(int line) {
			Class<?> type = getArgument().getReturnType();
			if (type != Double.class){
				throw new IllegalArgumentException("You tried to sin some non-Double");
			}
			double a = ((Double) getArgument().function(line)).getValue();
			return new Double(Math.sin(a));
		}

		@Override
		public Class<?> getReturnType() {
			return Double.class;
		}
		
		@Override
		public String toString(){
			return "sin( "+getArgument().toString()+" )";
		}
	}
	
	public class Cos extends OneArgument {
		
		public Cos(Expression<Double> argument, int line) {
			super(argument, line);
		}
		
		@Override
		public Double function(int line) {
			Class<?> type = getArgument().getReturnType();
			if (type != Double.class){
				throw new IllegalArgumentException("You tried to cos some non-Double");
			}
			double a = ((Double) getArgument().function(line)).getValue();
			return new Double(Math.cos(a));
		}

		@Override
		public Class<?> getReturnType() {
			return Double.class;
		}
		
		@Override
		public String toString(){
			return "cos( "+getArgument().toString()+" )";
		}
	}
	
	public class Getx extends OneArgument {
		
		public Getx(Expression<Entity> argument, int line) {
			super(argument, line);
		}
		
		@Override
		public Double function(int line) {
			Class<?> type = getArgument().getReturnType();
			if (! ((Entity.class).isAssignableFrom(type)) ){ //check if not an Entity or subclass of Entity 
				throw new IllegalArgumentException("You tried to getx some non-entity");
			}
			
			GameObject e = (GameObject)(getArgument().function(line));  //removed the getValue because it caused nullPointers
			if (e instanceof Food){
				return new Double(facade.getX((Food)e)); 
			}
			else if (e instanceof Worm){
				return new Double(facade.getX((Worm)e));
			}
			throw new IllegalArgumentException("getX is not available on the given Type.");
		}

		@Override
		public Class<?> getReturnType() {
			return Double.class;
		}
		
		@Override
		public String toString(){
			return "GetX( "+getArgument().toString()+" )";
		}
	}
	
	public class Gety extends OneArgument {

		public Gety(Expression<Entity> argument, int line) {
			super(argument, line);
		}
		
		@Override
		public Double function(int line) {
			Class<?> type = getArgument().getReturnType();
			if (! ((Entity.class).isAssignableFrom(type)) ){
				throw new IllegalArgumentException("You tried to gety some non-entity");
			}
			GameObject e = ((GameObject) getArgument().function(line));
			if (e instanceof Food){
				return new Double(facade.getY((Food)e));
			}
			else if (e instanceof Worm){
				return new Double(facade.getY((Worm)e));
			}
			throw new IllegalArgumentException("getY is not available on the given Type.");
		}

		@Override
		public Class<?> getReturnType() {
			return Double.class;
		}
		
		@Override
		public String toString(){
			return "GetY( "+getArgument().toString()+" )";
		}
	}
	
	public class Getradius extends OneArgument {

		public Getradius(Expression<Entity> argument, int line) {
			super(argument, line);
		}
		
		@Override
		public Double function(int line) {
			Class<?> type = getArgument().getReturnType();
			if (! ((Entity.class).isAssignableFrom(type)) ){
				throw new IllegalArgumentException("You tried to getradius some non-entity");
			}
			GameObject e = ((GameObject) getArgument().function(line));
			if (e instanceof Food){
				return new Double(facade.getRadius((Food)e));
			}
			else if (e instanceof Worm){
				return new Double(facade.getRadius((Worm)e));
			}
			throw new IllegalArgumentException("getRadius is not available on the given Type.");
		}
		
		@Override
		public Class<?> getReturnType() {
			return Double.class;
		}
		
		@Override
		public String toString(){
			return "GetRadius( "+getArgument().toString()+" )";
		}
	}

	public class Getdirection extends OneArgument {

		public Getdirection(Expression<Entity> argument, int line) {
			super(argument, line);
		}
		
		@Override
		public Double function(int line) {
			Class<?> type = getArgument().getReturnType();
			if (! ((Entity.class).isAssignableFrom(type)) ){
				throw new IllegalArgumentException("You tried to getdirection some non-entity");
			}
			GameObject e = ((GameObject) getArgument().function(line));
			Worm w = (Worm) e;
			return new Double(facade.getOrientation(w));
		}
		
		@Override
		public Class<?> getReturnType() {
			return Double.class;
		}
		
		@Override
		public String toString(){
			return "GetDirection( "+getArgument().toString()+" )";
		}
	}
	
	public class Getap extends OneArgument {

		public Getap(Expression<Entity> argument, int line) {
			super(argument, line);
		}
		
		@Override
		public Double function(int line) {
			Class<?> type = getArgument().getReturnType();
			if (! ((Entity.class).isAssignableFrom(type)) ){
				throw new IllegalArgumentException("You tried to getap some non-entity");
			}
			GameObject e = (GameObject) getArgument().function(line);
			Worm w = (Worm) e;
			return new Double(facade.getActionPoints(w));
		}
		
		@Override
		public Class<?> getReturnType() {
			return Double.class;
		}
		
		@Override
		public String toString(){
			return "GetAP( "+getArgument().toString()+" )";
		}
	}
	
	public class Getmaxap extends OneArgument {

		public Getmaxap(Expression<Entity> argument, int line) {
			super(argument, line);
		}
		
		@Override
		public Double function(int line) {
			Class<?> type = getArgument().getReturnType();
			if (! ((Entity.class).isAssignableFrom(type)) ){
				throw new IllegalArgumentException("You tried to getmaxap some non-entity");
			}
			GameObject e = ((GameObject) getArgument().function(line));
			Worm w = (Worm) e;
			return new Double(facade.getMaxActionPoints(w));
		}
		
		@Override
		public Class<?> getReturnType() {
			return Double.class;
		}
		
		@Override
		public String toString(){
			return "GetMaxAP( "+getArgument().toString()+" )";
		}
	}
	
	public class Gethp extends OneArgument {

		public Gethp(Expression<Entity> argument, int line) {
			super(argument, line);
		}
		
		@Override
		public Double function(int line) {
			Class<?> type = getArgument().getReturnType();
			if (! ((Entity.class).isAssignableFrom(type)) ){
				throw new IllegalArgumentException("You tried to gethp some non-entity");
			}
			GameObject e = ((GameObject) getArgument().function(line));
			Worm w = (Worm) e;
			return new Double(facade.getHitPoints(w));
		}
		
		@Override
		public Class<?> getReturnType() {
			return Double.class;
		}
		
		@Override
		public String toString(){
			return "GetHP( "+getArgument().toString()+" )";
		}
	}
	
	public class Getmaxhp extends OneArgument {

		public Getmaxhp(Expression<Entity> argument, int line) {
			super(argument, line);
		}
		
		@Override
		public Double function(int line) {
			Class<?> type = getArgument().getReturnType();
			if (! ((Entity.class).isAssignableFrom(type)) ){
				throw new IllegalArgumentException("You tried to getmaxhp some non-entity");
			}
			GameObject e = ((GameObject) getArgument().function(line));
			Worm w = (Worm) e;
			return new Double(facade.getMaxHitPoints(w));
		}
		
		@Override
		public Class<?> getReturnType() {
			return Double.class;
		}
		
		@Override
		public String toString(){
			return "GetMaxHP( "+getArgument().toString()+" )";
		}
	}

}
