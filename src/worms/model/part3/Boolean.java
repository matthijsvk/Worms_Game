package worms.model.part3;

public class Boolean extends Type {
	
	private boolean b;
	
	public Boolean(boolean b){
		this.b = b;
	}
	
	public boolean getValue(){
		return this.b;
	}
	
	public boolean equals(Boolean first, Boolean second){
		return first.getValue() == second.getValue();
	}

}
