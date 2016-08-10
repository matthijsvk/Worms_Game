package worms.model.part3;

import worms.model.GameObject;

public class Entity extends Type {
	
	private GameObject e;
	
	public Entity(){}
	
	public Entity(GameObject e){
		this.e = e;
	}
	
	public GameObject getValue(){
		return this.e;
	}
	
	public boolean equals(Entity first, Entity second){
		return first.getValue() == second.getValue();
	}
}
