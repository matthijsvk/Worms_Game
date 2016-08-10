package worms.model.part3;

public class Double extends Type {
	
	private double d;
	
	public Double(double d){
		this.d = d;
	}
	
	public double getValue(){
		return this.d;
	}

	public int intValue() {
		return (int) this.d;
	}
	
	public boolean equals(Double first, Double second){
		return first.getValue() == second.getValue();
	}

}
